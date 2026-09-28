package os.synchronization;

import java.util.LinkedList;
import java.util.Queue;

/**
 * PRODUCER-CONSUMER PROBLEM
 * 
 * Uses our CustomSemaphore to manage a bounded buffer.
 */
public class ProducerConsumer {

    private static Queue<Integer> buffer = new LinkedList<>();
    private static final int MAX_SIZE = 5;

    // Mutex protects the buffer itself
    private static CustomSemaphore mutex = new CustomSemaphore(1);
    // Tracks empty slots
    private static CustomSemaphore empty = new CustomSemaphore(MAX_SIZE);
    // Tracks filled slots
    private static CustomSemaphore full = new CustomSemaphore(0);

    static class Producer extends Thread {
        public void run() {
            try {
                for (int i = 1; i <= 5; i++) {
                    empty.acquire(); // Wait for an empty slot
                    mutex.acquire(); // Lock buffer
                    
                    System.out.println("Produced: " + i);
                    buffer.add(i);
                    
                    mutex.release(); // Unlock buffer
                    full.release();  // Signal that a slot is filled
                    
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    static class Consumer extends Thread {
        public void run() {
            try {
                for (int i = 1; i <= 5; i++) {
                    full.acquire();  // Wait for a filled slot
                    mutex.acquire(); // Lock buffer
                    
                    int item = buffer.poll();
                    System.out.println("Consumed: " + item);
                    
                    mutex.release(); // Unlock buffer
                    empty.release(); // Signal that a slot is empty
                    
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    public static void main(String[] args) {
        System.out.println("--- PRODUCER-CONSUMER USING CUSTOM SEMAPHORES ---");
        new Producer().start();
        new Consumer().start();
    }
}
