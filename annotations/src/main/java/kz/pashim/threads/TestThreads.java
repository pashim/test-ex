package kz.pashim.threads;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Function;

public class TestThreads {

    public void run() throws Exception {
//        testThread();
//        testWaitNotify();
//        testInterrupt();
//        testMonitor();
//        testMonitorNotify();
//        testPriorityMonitorInNotify();
//        testLocks();
//        testAtomic();
//        testCallable();
        testExecutor();
    }

    private void testThread() {
        Thread tr = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("tr1: sleeping...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("tr1: WTF???");
                    return;
                }
            }
        });
        Thread tr2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("tr2: sleeping...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("tr2: WTF???");
                    return;
                }
            }
        });
        tr2.setPriority(Thread.MAX_PRIORITY); // Low priority threads executes when more important threads are on pause
        System.out.println(tr.getPriority());
        System.out.println(tr2.getPriority());

        tr.start();
        tr2.start();

        try {
            tr.join(3000);
            if (tr.isAlive()) {
                System.out.println("trm: KEY WAKE UP!!!");
                tr.interrupt();
                tr2.interrupt();
            }
        } catch (InterruptedException e) {
            System.out.println("asd");
        }

        while (!tr.isAlive() || !tr2.isAlive()) {
            try {
                Thread.sleep( 100);
            } catch (Exception e) {}
        }
    }

    private void testWaitNotify() {
        System.out.println("--------------------");
        Shop shop = new Shop();

        Thread shotr = new Thread(() -> {
            while (true) {
                System.out.println("Shop receives new IPhone!!!");
                shop.addIPhone();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
            }
        });
        shotr.start();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for(;;) {
                    int r = new Random().nextInt(10) + 1;
                    shop.buyPhone("user" + r);
                    try {
                        Thread.sleep(1000 * r);
                    } catch (InterruptedException e) {
                        System.out.println("I (user" + r + " have enough iphones") ;
                        return;
                    }
                }
            }).start();
        }

        try {
            Thread.sleep(15 * 1000);
        } catch (Exception e) {}

        shotr.interrupt();
    }

    private void testInterrupt() {
        Runnable task = () -> {
            while(!Thread.currentThread().isInterrupted()) {
                System.out.println("asd bkb");
            }
            System.out.println("Finished");
        };
        Thread thread = new Thread(task);
        thread.start();
        try {
            Thread.sleep(100);
        } catch (Exception e) {}
        thread.interrupt();
    }

    private void testMonitor() {
        Monitor monitor = new Monitor();
        Thread tr = new Thread(() -> {
            monitor.hold();
        });
        Thread tr2 = new Thread(() -> {
            monitor.hello();
        });
        tr.start();
        tr2.start();

    }

    private void testMonitorNotify() {
        MonitorWaitNotify monitor = new MonitorWaitNotify();
        Thread tr = new Thread(() -> {
            monitor.hold();
        });
        Thread tr2 = new Thread(() -> {
            monitor.hold();
        });
        tr.start();
        tr2.start();

        Thread t3 = new Thread(() -> {
            monitor.notify();
        });
        t3.start();
    }

    private void testPriorityMonitorInNotify() throws InterruptedException {
        Object lock = new Object();
        Thread tr = new Thread(() -> {
            synchronized (lock) {
                try {
                    System.out.println("wait");
                    lock.wait();
                } catch(InterruptedException e) {
                    System.out.println("interrupted");
                }

                for (int i = 1; i < 5; i++) {
                    try {
                        Thread.sleep(1000);
                        System.out.println("In first thread");
                    } catch(InterruptedException e) {
                        System.out.println("interrupted");
                    }
                }
            }
        });
        tr.start();
        Thread.sleep(1000);
        synchronized (lock) {
            lock.notify();
            for (int i = 1; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                    System.out.println("In second thread");
                } catch(InterruptedException e) {
                    System.out.println("interrupted");
                }
            }

        }
    }

    private void testLocks() throws InterruptedException {
        Lock lock = new ReentrantLock();
        Runnable task = () -> {
            lock.lock();
            System.out.println("Thread");
            lock.unlock();
        };
        lock.lock();

        Thread th = new Thread(task);
        th.start();
        System.out.println("main");
        Thread.currentThread().sleep(2000);
        lock.unlock();

        Semaphore semaphore = new Semaphore(0);
        try {
            System.out.println("In Semaphore");
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Hello, World!");
    }

    Integer noVol = 0;
    volatile Integer vol = 0;
    AtomicInteger atom = new AtomicInteger(0);

    private void testAtomic() {
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> { noVol++; }).start();
            new Thread(() -> { vol++; }).start();
            new Thread(() -> { atom.incrementAndGet(); }).start();
        }

        System.out.println(noVol);
        System.out.println(vol);
        System.out.println(atom);
    }

    private void testCallable() throws Exception {
        Callable task = () -> {
            Thread.sleep(1000);
            return "Hello, World!";
        };
        CompletableFuture<String> supplier;
        supplier = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
//            System.out.println("supply " + Thread.currentThread().getName());
            return "Значение";
        });
        FutureTask<String> future = new FutureTask<>(task);
        new Thread(future).start();
        try {
            System.out.println(supplier.get());
            System.out.println(future.get());
        } catch (Exception e) {}

        AtomicLong longValue = new AtomicLong(0);
        Runnable tas = () -> longValue.set(new Date().getTime());
        Function<Long, Date> dateConverter = (longvalue) -> new Date(longvalue);
        Consumer<Date> printer = date -> {
            System.out.println(date);
            System.out.flush();
        };
        // CompletableFuture computation
        CompletableFuture.runAsync(tas)
                .thenApply((v) -> longValue.get())
                .thenApply(dateConverter)
                .thenAccept(printer);

    }

    private void testExecutor() throws Exception {
        Callable<String> task = () -> Thread.currentThread().getName();
        ExecutorService service = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 5; i++) {
            Future result = service.submit(task);
            System.out.println(result.get());
            Thread.sleep(500);
        }
        service.shutdown();

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4);
        Runnable task2 = () -> {
            System.out.println(Thread.currentThread().getName());
        };
        scheduledExecutorService.scheduleAtFixedRate(task2, 1,2, TimeUnit.SECONDS);
        Thread.sleep(10000);
        scheduledExecutorService.shutdown();
    }
}
