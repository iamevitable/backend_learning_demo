package insights.Time;

import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * @Desc * @param
 * @return
 * @date:
 * @Author
 */
public class TimerWheel {
    private volatile long startTime;

    private Slot[] wheel;

    private Ticker ticker;

    private AtomicBoolean started = new AtomicBoolean(false);

    private CountDownLatch startTimeLatch;

    private final ExecutorService executor;
    public TimerWheel() {
        this.started = new AtomicBoolean(false);
        this.ticker = new Ticker();
        this.wheel = new Slot[10];
        startTimeLatch = new CountDownLatch(1);
        executor = Executors.newFixedThreadPool(6);
        for (int i = 0; i < 10; i++) {
            wheel[i] = new Slot();
        }
    }

    private void start() {
        if (started.compareAndSet(false, true)) {
            ticker.start();
        }
        try {
            startTimeLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void addDelayTask(Runnable runnable, long delayMs) {
        start();
        DelayTask task = new DelayTask(runnable, delayMs);
        int index = Math.toIntExact((task.deadline - startTime) / 100 % wheel.length);
        wheel[index].pushDelayTask(task);
    }


    public void stop() {
        if (started.compareAndSet(true, false)) {
            LockSupport.unpark(ticker);
        }
    }

    public static class DelayTask {
        private final Runnable runnable;
        private long deadline;
        private DelayTask next;
        private DelayTask prev;
        public DelayTask(Runnable runnable, long delayMs) {
            this.runnable = runnable;
            this.deadline = System.currentTimeMillis() + delayMs;
        }
    }

    public class Slot {

        DelayTask head;
        DelayTask tail;
        /**
         * 启动一个slot里面应该执行的任务
         * @param tickTime 是时钟时间，恒增长
         */
        public void runWithDeadline(long tickTime) {
            DelayTask current = head;
            while (current != null) {
                DelayTask next = current.next;
                if (current.deadline <= tickTime) {
                    removeTask(current);
                    executor.execute(current.runnable);
                }
                current = next;
            }
        }

        /**
         * 将延时任务加入slot
         * @param delayTask
         */
        public void pushDelayTask(DelayTask delayTask) {
            if (head == null) {
                head = tail = delayTask;
            } else {
                tail.next= delayTask;
                delayTask.prev = tail;
                tail = delayTask;
            }
        }

        public void removeTask(DelayTask current) {
            if (current.prev != null) {
                current.prev.next = current.next;
            }
            if (current.next != null) {
                current.next.prev = current.prev;
            }
            if (current == head) {
                head = current.next;
            }
            if (current == tail) {
                tail = current.prev;
            }
            current.prev = null;
            current.next = null;
        }
    }

    public class Ticker extends Thread {

        int tickCount = 0;
        /**
         * 启动计时器，对startTime进行赋值，然后去进行slot数组的扫描
         */
        @Override
        public void run() {
            startTime = System.currentTimeMillis();
            startTimeLatch.countDown();
            while (started.get()) {
                long tickTime = startTime + (tickCount + 1) * 100L;
                while (System.currentTimeMillis() <= tickTime) {
                    LockSupport.parkUntil(tickTime);
                    if (started.get() == false) {
                        return;
                    }
                }
                int index = tickCount % wheel.length;
                Slot slot = wheel[index];
                slot.runWithDeadline(tickTime);
                tickCount++;
            }
        }
    }
}
