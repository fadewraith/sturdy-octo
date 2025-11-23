package twelvejavaexecutorservicepartonetwodemo;


import java.util.concurrent.*;

public class ExecutorServiceExample4 {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Future future = executorService.submit(newCallable("Task 1.1"));

        System.out.println("future.isDone(): " + future.isDone());

        try {
            String result = (String) future.get();
            System.out.println(result);
        } catch (InterruptedException e) {

        } catch (ExecutionException e) {

        }

        System.out.println("future.isDone(): " + future.isDone());

        executorService.shutdown();
    }

    private static Callable newCallable(String msg) {

        return () -> {
            String completeMsg = "Thread.currentThread().getName() -> " + Thread.currentThread().getName() + ": " + msg ;
            System.out.println(completeMsg);
            return completeMsg;
        };


//        return new Callable() {
//            @Override
//            public Object call() throws Exception {
//                String completeMsg = "Thread.currentThread().getName() -> " + Thread.currentThread().getName() + ": " + msg ;
//                System.out.println(completeMsg);
//                return completeMsg;
//            }
//        };
    }
}
