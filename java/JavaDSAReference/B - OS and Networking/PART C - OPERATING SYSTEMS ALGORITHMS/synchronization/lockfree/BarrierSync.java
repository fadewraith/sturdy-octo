package os.synchronization.lockfree;

import java.util.concurrent.CyclicBarrier;

/**
 * BARRIER SYNCHRONIZATION
 * 
 * WHAT IT IS:
 * A synchronization primitive that forces a group of threads to stop at a certain 
 * execution point (the "barrier") and wait until ALL threads have reached it before 
 * any of them can proceed.
 */
public class BarrierSync {

    public static void main(String[] args) {
        System.out.println("--- BARRIER SYNCHRONIZATION ---");
        
        int numThreads = 3;
        // Barrier waits for 3 threads to reach it
        CyclicBarrier barrier = new CyclicBarrier(numThreads, () -> {
            System.out.println(">> ALL THREADS REACHED THE BARRIER! Moving to phase 2...");
        });

        Runnable task = () -> {
            try {
                long id = Thread.currentThread().getId();
                System.out.println("Thread " + id + " is doing Phase 1 work...");
                Thread.sleep((long) (Math.random() * 500));
                
                System.out.println("Thread " + id + " reached barrier. Waiting...");
                barrier.await(); // Block here until all 3 reach this point
                
                System.out.println("Thread " + id + " is doing Phase 2 work...");
            } catch (Exception e) {}
        };

        for (int i = 0; i < numThreads; i++) {
            new Thread(task).start();
        }
    }
}
