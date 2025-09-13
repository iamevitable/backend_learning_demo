package demo;

/**
 * @Desc * @param
 * @return
 * @date:
 * @Author
 */
public class AlternatePrintingThreeThreads {
    /**
     * 当前打印的数字
     */
    private int currentNumber = 1;
    /**
     * 锁
     */
    private final Object lock = new Object();
    /**
     * 控制线程打印数字的标识
     */
    private int turn = 0;

    public static void main(String[] args) {
        AlternatePrintingThreeThreads ap = new AlternatePrintingThreeThreads();

        // 创建并启动三个线程
        Thread t1 = new Thread(() -> ap.printNumber(0));
        Thread t2 = new Thread(() -> ap.printNumber(1));
        Thread t3 = new Thread(() -> ap.printNumber(2));

        t1.start();
        t2.start();
        t3.start();
    }

    private void printNumber(int offset) {
        while (currentNumber <= 100) {
            synchronized (lock) {
                while ((turn % 3 != offset)) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (currentNumber <= 100) {
                    System.out.println("Thread " + (offset + 1) + " printed: " + currentNumber);
                    currentNumber++;
                    turn = (turn + 1) % 3;
                    lock.notifyAll();
                }
            }
        }
    }
}
