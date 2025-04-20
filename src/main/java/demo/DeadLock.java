package demo;

public class DeadLock {
    private static Object resource1 = new Object();
    private static Object resource2 = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
           synchronized (resource1) {
               System.out.println("get resource 1");
               try {
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               System.out.println("waiting for resource 2");
               synchronized (resource2) {
                   System.out.println("get resource 2");
               }
           }
        },"thread 1").start();
        new Thread(() -> {
            synchronized (resource2) {
                System.out.println("get resource 2");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("waiting for resource 1");
                synchronized (resource1) {
                    System.out.println("get resource 1");
                }
            }
        },"thread 2").start();
    }
}
