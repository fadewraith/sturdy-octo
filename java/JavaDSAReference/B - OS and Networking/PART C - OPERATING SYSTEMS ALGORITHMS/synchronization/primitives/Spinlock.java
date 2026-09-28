package os.synchronization.primitives;

/**
 * SPINLOCK IMPLEMENTATION
 * 
 * WHAT IT IS:
 * A Spinlock is a lock that uses busy-waiting (spinning in a while loop) 
 * to acquire the lock, utilizing the hardware CAS instruction.
 * 
 * WHY IT MATTERS:
 * Spinlocks avoid OS context switches. If a lock is only held for a few CPU 
 * cycles, spinning is massively faster than putting the thread to sleep!
 */
public class Spinlock {

    private CompareAndSwap casPrimitive = new CompareAndSwap();

    public void lock() {
        // The core of the Spinlock: 
        // Keep looping (spinning) as long as CAS fails to change the lock from 0 to 1!
        while (!casPrimitive.compareAndSwap(0, 1)) {
            // Busy wait! Consumes CPU cycles intentionally!
        }
    }

    public void unlock() {
        // Reset lock to 0
        casPrimitive.compareAndSwap(1, 0);
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("--- SPINLOCK (BUSY-WAIT) DEMO ---");
        Spinlock spinlock = new Spinlock();
        
        Thread t1 = new Thread(() -> {
            spinlock.lock();
            System.out.println("Thread 1 ACQUIRED the spinlock!");
            try { Thread.sleep(500); } catch (Exception e) {}
            System.out.println("Thread 1 RELEASING the spinlock.");
            spinlock.unlock();
        });

        Thread t2 = new Thread(() -> {
            System.out.println("Thread 2 wants the lock (will actively SPIN while waiting)...");
            spinlock.lock();
            System.out.println("Thread 2 ACQUIRED the spinlock!");
            spinlock.unlock();
        });

        t1.start();
        Thread.sleep(10); // Ensure T1 gets it first
        t2.start();
    }
}
