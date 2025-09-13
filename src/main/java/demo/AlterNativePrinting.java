package demo;

/**
 * @Desc * @param
 * @return
 * @date:
 * @Author
 */
public class AlterNativePrinting {
    private int currentNumber = 1;
    private final Object lock = new Object();

    public static void main(String[] args) {
        AlterNativePrinting ap = new AlterNativePrinting();

        // 创建奇数打印线程
        Thread oddPrinter = new Thread(() -> {
            ap.printNumber(true);
        });
        oddPrinter.start();

        // 创建偶数打印线程
        Thread evenPrinter = new Thread(() -> {
            ap.printNumber(false);
        });
        evenPrinter.start();
    }
    /***
     * @Desc
     * @param  isOdd == true 的时候 代表为奇数
     * @return
     * @date 2025/9/13
     * @Author 86183
     */
    private void printNumber(boolean isOdd) {
        while (currentNumber <= 100) {
            synchronized (lock) {
                while ((isOdd && currentNumber % 2 == 0) || (!isOdd && currentNumber % 2 != 0)) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (currentNumber <= 100) {
                    System.out.println("Thread " + (isOdd ? "Odd " : "Even") + ": " + currentNumber);
                    currentNumber++;
                    lock.notifyAll();
                }
            }
        }
    }

}
