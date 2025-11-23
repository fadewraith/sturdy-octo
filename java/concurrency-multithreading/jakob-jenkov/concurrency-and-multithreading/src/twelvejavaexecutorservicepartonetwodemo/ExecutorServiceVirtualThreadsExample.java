package twelvejavaexecutorservicepartonetwodemo;

import java.util.concurrent.*;

public class ExecutorServiceVirtualThreadsExample {

    /**
     * Example of using a virtual thread per task executor service.
     * This example shows how to submit a Runnable and a Callable to the executor service,
     * and how to retrieve the result of the Callable.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

        executor.submit(() -> {
            System.out.println("This is a Runnable that is executed by a virtual thread");
        });

//        Callable<String> callable = new Callable<>() {
//            @Override
//            public String call() throws Exception {
//                System.out.println("This is a Callable that is executed by a virtual thread");
//                return "Callable result";
//            }
//        };

        Callable<String> callable = () -> {
            System.out.println("This is a Callable that is executed by a virtual thread");
            return "Callable result";
        };

        Future<String> futureResult = executor.submit(callable);

        try {
            System.out.println("Future result: " + futureResult.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        executor.shutdown();

        try {
            executor.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
