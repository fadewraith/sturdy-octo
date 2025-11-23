package twentytwoforkjoinpooldemo;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;


public class JavaForkJoinPoolExample {

    public static void main(String[] args) {
        ForkJoinPool fJp1 = ForkJoinPool.commonPool();
        ForkJoinPool fJp2 = new ForkJoinPool(4);

        MyRecursiveAction myRecursiveAction = new MyRecursiveAction(123);
//        fJp1.invoke(myRecursiveAction);

        MyRecursiveTask myRecursiveTask = new MyRecursiveTask(123);
//        long result = fJp1.invoke(myRecursiveTask);

        ForkJoinTask<Long> forkJoinTask = fJp1.submit(myRecursiveTask);
        try {
            Long result = forkJoinTask.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        fJp1.getParallelism();
        fJp1.setParallelism(10);
        fJp1.getPoolSize();
        fJp1.getQueuedSubmissionCount();
        fJp1.getRunningThreadCount();
        fJp1.isShutdown();
        fJp1.isTerminated();
        fJp1.isTerminating();

        fJp1.shutdown();
        fJp1.shutdownNow();

//        System.out.println("Result: " + result);
        sleep(1000);
    }

    private static void sleep(long millis ) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
