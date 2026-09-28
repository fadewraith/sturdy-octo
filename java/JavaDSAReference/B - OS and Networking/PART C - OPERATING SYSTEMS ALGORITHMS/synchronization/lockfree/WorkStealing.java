package os.synchronization.lockfree;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * WORK-STEALING ALGORITHM (Explicit Simulation)
 * 
 * WHAT IT IS:
 * In a thread pool, every thread has its own double-ended queue (Deque) of tasks.
 * - A thread PUSHES and POPS tasks from the BOTTOM of its own queue (LIFO / Stack behavior).
 * - If a thread's queue is EMPTY, it becomes a "Thief"!
 * - It STEALS a task from the TOP of another thread's queue (FIFO behavior).
 * 
 * WHY IT MATTERS:
 * This minimizes contention. The owner operates on the bottom, while thieves 
 * operate on the top. It keeps all CPU cores perfectly utilized!
 */
public class WorkStealing {

    static class WorkerThread extends Thread {
        String name;
        Deque<String> deque = new ArrayDeque<>();
        WorkerThread peerToStealFrom; // A reference to another thread's queue

        WorkerThread(String name) {
            this.name = name;
        }

        public void run() {
            while (true) {
                // 1. Try to pop from the BOTTOM of our own queue
                String task = deque.pollLast();
                
                if (task != null) {
                    System.out.println(name + " is processing its own task: " + task);
                    try { Thread.sleep(100); } catch (Exception e) {} // Simulate work
                } else {
                    // 2. We are out of work! Time to steal!
                    if (peerToStealFrom != null && !peerToStealFrom.deque.isEmpty()) {
                        // STEAL FROM THE TOP (pollFirst)
                        String stolenTask = peerToStealFrom.deque.pollFirst();
                        if (stolenTask != null) {
                            System.out.println("  >>> [!] " + name + " is idle! STEALING task from top of " + peerToStealFrom.name + "'s queue: " + stolenTask);
                            try { Thread.sleep(100); } catch (Exception e) {} 
                        }
                    } else {
                        break; // No more work anywhere
                    }
                }
            }
            System.out.println(name + " finished.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("--- WORK-STEALING ALGORITHM EXPLICIT DEMO ---");
        
        WorkerThread worker1 = new WorkerThread("Worker-1 (Overloaded)");
        WorkerThread worker2 = new WorkerThread("Worker-2 (Idle Thief)");
        
        // Setup peer reference so Worker 2 can steal from Worker 1
        worker2.peerToStealFrom = worker1;

        // Worker 1 is heavily loaded with 5 tasks
        worker1.deque.addLast("Task A");
        worker1.deque.addLast("Task B");
        worker1.deque.addLast("Task C");
        worker1.deque.addLast("Task D");
        worker1.deque.addLast("Task E");
        
        // Worker 2 has ZERO tasks!
        
        worker1.start();
        Thread.sleep(50); // Let worker 1 start working on Task E (bottom of queue)
        worker2.start();  // Worker 2 starts, realizes it is empty, and steals Task A (top of queue)!
    }
}
