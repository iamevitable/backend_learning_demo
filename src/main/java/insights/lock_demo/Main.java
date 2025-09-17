package insights.lock_demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Desc * @param
 * @return
 * @date:
 * @Author
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        int[] count = new int[]{1000};
        MyLock lock = new MyLock();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            threads.add(new Thread(() -> {
                lock.lock();
                for (int i1 = 0; i1 < 10; i1++) {
                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    count[0]--;
                }
                lock.unlock();
            }));
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println(count[0]);
    }
}
