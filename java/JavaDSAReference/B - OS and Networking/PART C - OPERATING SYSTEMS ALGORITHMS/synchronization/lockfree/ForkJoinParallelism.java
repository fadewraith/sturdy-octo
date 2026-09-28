package os.synchronization.lockfree;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * FORK-JOIN PARALLELISM & WORK-STEALING ALGORITHM
 * 
 * WHAT IT IS:
 * Fork-Join is a parallel design pattern for divide-and-conquer algorithms.
 * A massive task is "Forked" into smaller subtasks, and then "Joined" when done.
 * 
 * WORK-STEALING:
 * Inside Java's ForkJoinPool, every thread has its own deque (double-ended queue) of tasks.
 * If a thread finishes all its tasks, it doesn't sit idle! It actively "steals" 
 * tasks from the back of another busy thread's deque to maximize CPU utilization!
 */
public class ForkJoinParallelism {

    // A task that sums an array of numbers using Divide-and-Conquer
    static class SumTask extends RecursiveTask<Long> {
        private static final int THRESHOLD = 1000;
        private int[] arr;
        private int start, end;

        public SumTask(int[] arr, int start, int end) {
            this.arr = arr; this.start = start; this.end = end;
        }

        @Override
        protected Long compute() {
            int length = end - start;
            
            // Base case: small enough to compute directly
            if (length <= THRESHOLD) {
                long sum = 0;
                for (int i = start; i < end; i++) sum += arr[i];
                return sum;
            } 
            
            // Divide and Conquer! (FORK)
            int mid = start + (length / 2);
            SumTask leftTask = new SumTask(arr, start, mid);
            SumTask rightTask = new SumTask(arr, mid, end);
            
            leftTask.fork(); // Asynchronously execute in another pool thread
            
            // Compute right task directly, then JOIN the left task
            long rightResult = rightTask.compute();
            long leftResult = leftTask.join();
            
            return leftResult + rightResult;
        }
    }

    public static void main(String[] args) {
        System.out.println("--- FORK-JOIN & WORK-STEALING ---");
        
        int[] data = new int[5000];
        for (int i = 0; i < data.length; i++) data[i] = i;

        // The ForkJoinPool implements the Work-Stealing Algorithm natively!
        ForkJoinPool pool = new ForkJoinPool();
        SumTask rootTask = new SumTask(data, 0, data.length);
        
        long totalSum = pool.invoke(rootTask);
        System.out.println("Total Sum Computed asynchronously: " + totalSum);
    }
}
