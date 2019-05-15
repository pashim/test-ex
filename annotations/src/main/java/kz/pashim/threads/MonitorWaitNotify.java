package kz.pashim.threads;

public class MonitorWaitNotify {

    public synchronized void hello() {
        System.out.println("In Hello");
        notify();
    }

    public synchronized void hold() {
        System.out.println("Before Wait");
        try {
            Thread.sleep(1000);
            wait();
        } catch (Exception e) {
        }
        System.out.println("After Wait");
    }

    public static void staticHello() {
        synchronized (MonitorWaitNotify.class) {
            System.out.println("In Hello");
        }
    }

    public static synchronized void staticHold() {
        System.out.println("Before Wait");
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
        }
        System.out.println("After Wait");
    }

}
