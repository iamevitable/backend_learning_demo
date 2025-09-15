package juc;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Desc * @param
 * @return
 * @date:
 * @Author
 */
public class ThreadPoolTest {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        Runnable runnable = new Runnable() {
            public void run() {
                int a = 1 / 0;
            }
        };
        Future<String> submit = threadPool.submit(runnable, "已完成");
        try {
            String s = submit.get();
            System.out.println(s);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        threadPool.shutdown();
    }
}
