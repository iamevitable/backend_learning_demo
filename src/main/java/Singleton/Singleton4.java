package Singleton;

public class Singleton4 {
    public Singleton4() {

    }
    private volatile Singleton4 INSTANCE = null;
    public Singleton4 getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        synchronized (Singleton4.class) {
            if (INSTANCE != null) {
                return INSTANCE;
            }
            INSTANCE = new Singleton4();
            return INSTANCE;
        }
    }
}
