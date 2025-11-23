package twelvejavaexecutorservicepartonetwodemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceExample {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        executorService.execute(newRunnnable("Task 1"));
        executorService.execute(newRunnnable("Task 2"));
        executorService.execute(newRunnnable("Task 3"));

        executorService.shutdown();
    }

    private static Runnable newRunnnable(String msg) {

//        return new Runnable() {
//            public void run() {
//                String completeMsg = "Thread.currentThread().getName() -> " + Thread.currentThread().getName() + ": " + msg ;
//                System.out.println(completeMsg);
//            }
//        };


        return () -> {
            String completeMsg = "Thread.currentThread().getName() -> " + Thread.currentThread().getName() + ": " + msg ;
            System.out.println(completeMsg);
        };
    }

}
