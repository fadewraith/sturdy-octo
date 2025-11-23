package twelvejavaexecutorservicepartonetwodemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceExample2 {

    public static void main(String[] args) {
        // has same impact as the below one
        // ExecutorService executorService = Executors.newFixedThreadPool(1);

        ExecutorService executorService = Executors.newFixedThreadPool(1);

        executorService.execute(newRunnable("Task 1.1"));
        executorService.execute(newRunnable("Task 1.2"));
        executorService.execute(newRunnable("Task 1.3"));

        executorService.shutdown();
    }

    private static Runnable newRunnable(String msg) {
        return () -> {
            String completeMsg = "Thread.currentThread().getName() -> " + Thread.currentThread().getName() + ": " + msg ;
            System.out.println(completeMsg);
        };
    }

}
