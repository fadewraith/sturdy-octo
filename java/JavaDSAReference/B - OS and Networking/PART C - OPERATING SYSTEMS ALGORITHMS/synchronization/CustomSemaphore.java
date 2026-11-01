package os.synchronization;

/**
 * CUSTOM SEMAPHORE
 * 
 * WHAT IT IS:
 * An OS-level synchronization primitive that controls access to a common resource 
 * by multiple processes in a concurrent system.
 * 
 * NOTE: relies on Java's built-in wait() and notify() because true thread blocking 
 * cannot be simulated natively without hardware/JVM hooks!
 */
public class CustomSemaphore {
    
    private int permits;

    public CustomSemaphore(int initialPermits) {
        if (initialPermits < 0) throw new IllegalArgumentException();
        this.permits = initialPermits;
    }

    // Equivalent to P() or wait() or down()
    public synchronized void acquire() throws InterruptedException {
        while (permits == 0) {
            wait(); // Block the thread until a permit is available
        }
        permits--;
    }

    // Equivalent to V() or signal() or up()
    public synchronized void release() {
        permits++;
        notify(); // Wake up one waiting thread
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("--- Custom Semaphore (2 permits, 5 threads) ---");
        CustomSemaphore semaphore = new CustomSemaphore(2);
        int[] inside = {0};
        int[] maxInside = {0};
        Thread[] threads = new Thread[5];
        for (int i = 0; i < threads.length; i++) {
            final int id = i;
            threads[i] = new Thread(() -> {
                try {
                    semaphore.acquire();
                    synchronized (inside) {
                        inside[0]++;
                        maxInside[0] = Math.max(maxInside[0], inside[0]);
                        System.out.println("Thread " + id + " entered (inside now: " + inside[0] + ")");
                    }
                    Thread.sleep(50);
                    synchronized (inside) {
                        inside[0]--;
                    }
                    semaphore.release();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            threads[i].start();
        }
        for (Thread t : threads) t.join();
        System.out.println("Max threads inside at once: " + maxInside[0] + " (never more than 2)");
    }
}
