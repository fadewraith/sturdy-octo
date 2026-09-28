package os.synchronization.primitives;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * TEST-AND-SET PRIMITIVE
 * 
 * WHAT IT IS:
 * An atomic hardware instruction provided by CPUs. 
 * It writes a `1` (true) to a memory location and returns its OLD value, 
 * in one uninterruptible step.
 * 
 * NOTE ON JAVA BUILT-INS:
 * Pure Test-And-Set is a hardware mechanism. In Java, we simulate it using 
 * the thread-safe `AtomicBoolean.getAndSet()` method, which leverages native 
 * CPU instructions (sun.misc.Unsafe) underneath.
 */
public class TestAndSet {

    // Simulating the hardware lock variable
    private AtomicBoolean hardwareLock = new AtomicBoolean(false);

    /**
     * Simulated hardware test_and_set instruction
     * @return the original value before it was set to true
     */
    public boolean testAndSet() {
        // Atomically sets the value to true and returns the old value
        return hardwareLock.getAndSet(true);
    }

    public void unlock() {
        hardwareLock.set(false);
    }

    public static void main(String[] args) {
        System.out.println("--- TEST-AND-SET SIMULATION ---");
        TestAndSet tas = new TestAndSet();
        
        System.out.println("Lock is initially free (false).");
        boolean oldVal = tas.testAndSet();
        System.out.println("Thread 1 calls testAndSet(). Old value was: " + oldVal + ". Lock is now TRUE.");
        
        boolean oldVal2 = tas.testAndSet();
        System.out.println("Thread 2 calls testAndSet(). Old value was: " + oldVal2 + ". (Thread 2 fails to get lock).");
    }
}
