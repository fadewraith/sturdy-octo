package twelvejavaexecutorservicepartonetwodemo;

import java.util.concurrent.*;

public class ExecutorServiceExample1_1 {

    /**
     * ExecutorService is an interface, in order to use it we need to create an instance of a class that implements it.
     * Java has 2 implementations of ExecutorService interface:
     * 1. ThreadPoolExecutor
     * 2. ScheduledThreadPoolExecutor
     * */

    public static void main(String[] args) {

        int corePoolSize = 10;
        int maxPoolSize = 20;
        long keepAliveTime = 3000;

        ExecutorService threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(128));

        threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);

        // ExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(corePoolSize);
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(corePoolSize);

        scheduledExecutorService = Executors.newScheduledThreadPool(10);
    }

    private static Runnable newRunnable(String msg) {
        return () -> {
            String completeMsg = "Thread.currentThread().getName() -> " + Thread.currentThread().getName() + ": " + msg ;
            System.out.println(completeMsg);
        };
    }
}
