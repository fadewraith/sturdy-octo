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
}
