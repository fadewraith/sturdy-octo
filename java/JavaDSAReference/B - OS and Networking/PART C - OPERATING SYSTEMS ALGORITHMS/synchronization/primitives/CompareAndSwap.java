package os.synchronization.primitives;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * COMPARE-AND-SWAP (CAS) PRIMITIVE
 * 
 * WHAT IT IS:
 * A more advanced atomic hardware instruction than Test-and-Set.
 * It compares the contents of a memory location with a given value and, 
 * ONLY if they are the same, modifies the contents to a new given value.
 */
public class CompareAndSwap {

    // Using AtomicInteger to simulate the hardware-level CAS instruction
    private AtomicInteger hardwareValue = new AtomicInteger(0);

    /**
     * Simulated hardware compare_and_swap instruction
     */
    public boolean compareAndSwap(int expectedValue, int newValue) {
        // Atomically checks if current value == expectedValue. 
        // If yes, sets to newValue and returns true. Otherwise returns false.
        return hardwareValue.compareAndSet(expectedValue, newValue);
    }

    public static void main(String[] args) {
        System.out.println("--- COMPARE-AND-SWAP (CAS) SIMULATION ---");
        CompareAndSwap cas = new CompareAndSwap();
        
        System.out.println("Hardware value is initially 0.");
        
        boolean success = cas.compareAndSwap(0, 10);
        System.out.println("Thread 1 tries to swap 0 -> 10. Success? " + success);
        
        boolean fail = cas.compareAndSwap(0, 20);
        System.out.println("Thread 2 tries to swap 0 -> 20. Success? " + fail + " (Because value is no longer 0!)");
    }
}
