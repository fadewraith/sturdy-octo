package twelvejavaexecutorservicepartonetwodemo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorServiceExample6 {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        List<Callable<String>> callables = new ArrayList<>();
        callables.add(newCallable("Task 1.1"));
        callables.add(newCallable("Task 1.2"));
        callables.add(newCallable("Task 1.3"));

        try {
//            List<Future<String>> results = executorService.invokeAll((Collection<Callable<String>>) callables);
            List<Future<String>> results = executorService.invokeAll(callables);
            for (Future<String> result : results) {
                System.out.println(result.get());
            }
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }

    private static Callable<String> newCallable(String msg) {

        return () -> {
            String completeMsg = "Thread.currentThread().getName() -> " + Thread.currentThread().getName() + ": " + msg ;
//            System.out.println(completeMsg);
            return completeMsg;
        };
    }
}
