package os.synchronization.lockfree;

/**
 * DOUBLE-CHECKED LOCKING PATTERN
 * 
 * WHAT IT IS:
 * A software design pattern used to reduce the overhead of acquiring a lock by 
 * testing the locking criterion before acquiring the lock.
 * Commonly used for lazy initialization of Singleton objects in multi-threading.
 * 
 * THE FIX:
 * The `volatile` keyword is absolutely mandatory in Java. Without it, the CPU 
 * could reorder instructions, causing another thread to see a partially constructed 
 * object before initialization finishes!
 */
public class DoubleCheckedLocking {

    // Volatile prevents instruction reordering
    private static volatile DoubleCheckedLocking instance = null;

    private DoubleCheckedLocking() {
        System.out.println("Instance initialized.");
    }

    public static DoubleCheckedLocking getInstance() {
        // First check (no lock, fast!)
        if (instance == null) {
            
            // Lock only on the very first creation
            synchronized (DoubleCheckedLocking.class) {
                
                // Second check (inside lock, safe!)
                if (instance == null) {
                    instance = new DoubleCheckedLocking();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        System.out.println("--- DOUBLE-CHECKED LOCKING ---");
        
        // Multiple threads trying to get the Singleton simultaneously
        Runnable task = () -> {
            DoubleCheckedLocking.getInstance();
        };
        
        new Thread(task).start();
        new Thread(task).start();
        new Thread(task).start();
    }
}
