package demo;

import java.security.spec.RSAOtherPrimeInfo;

public class print_abc {
    public static void main(String[] args) {
        PrintNum pn = new PrintNum(1, 5);
        new Thread(() -> {
            pn.print("a",1,2);
        }).start();
        new Thread(() -> {
            pn.print("b",2,3);
        }).start();
        new Thread(() -> {
            pn.print("c",3,1);
        }).start();
    }
}
class PrintNum {
    private int flag;
    private int loopNumber;
    public PrintNum(int flag, int loopNumber) {
        this.flag = flag;
        this.loopNumber = loopNumber;
    }
    public void print(String str, int waitFlag, int nextFlag) {
        for (int i = 0; i < loopNumber; i++) {
            synchronized (this) {
                while (flag != waitFlag) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(str);
                flag = nextFlag;
                this.notifyAll();
            }
        }
    }
}
