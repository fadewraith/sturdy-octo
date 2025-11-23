package twelvejavaexecutorservicepartonetwodemo;

import java.util.concurrent.*;

public class ExecutorServiceExample8 {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Future future = executorService.submit(newCallable("Task 1.1."));

        System.out.println("future.isDone(): " + future.isDone());

//        boolean mayInterrupt = true;
        boolean mayInterrupt = false;
        boolean wasCancelled = future.cancel(mayInterrupt);
        System.out.println("wasCancelled = future.cancel(mayInterrupt): " + wasCancelled);

        try {
            String result = (String) future.get();
            System.out.println(result);
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
        } catch (CancellationException e) {
            String msg = "Cannot call Future.get() since task was cancelled";
            System.out.println(msg);
        }

        System.out.println("future.isDone(): " + future.isDone());
        System.out.println("future.isCancelled(): " + future.isCancelled());

        executorService.shutdown();

    }

    private static Callable newCallable(String msg) {

        return () -> {
            String completeMsg = "Thread.currentThread().getName() -> " + Thread.currentThread().getName() + ": " + msg ;
//            System.out.println(completeMsg);
            return completeMsg;
        };


    }
}
