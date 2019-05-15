package kz.pashim.threads;

public class Monitor {

    public synchronized void hello() {
        System.out.println("Hello");
    }

    public synchronized void hold() {
        try {
            Thread.sleep(5000);
        } catch (Exception e) {}
    }
}
