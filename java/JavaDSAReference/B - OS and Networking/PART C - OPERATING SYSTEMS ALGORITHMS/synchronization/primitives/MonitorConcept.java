package os.synchronization.primitives;

/**
 * MONITOR CONCEPT
 * 
 * WHAT IT IS:
 * A Monitor is a high-level synchronization construct provided by programming languages.
 * It is an object whose methods are mutually exclusive. 
 * Only ONE thread can execute any of its methods at a time.
 * 
 * IN JAVA:
 * Java natively supports Monitors! Every single object in Java has a hidden, built-in 
 * lock (an intrinsic lock or monitor lock).
 * The `synchronized` keyword literally just means "Acquire this object's Monitor".
 */
public class MonitorConcept {

    // This entire object acts as a Monitor!
    static class CounterMonitor {
        private int count = 0;

        // The synchronized keyword ensures mutual exclusion
        public synchronized void increment() {
            count++;
        }

        public synchronized int getCount() {
            return count;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("--- MONITOR CONCEPT (JAVA NATIVE MONITOR) ---");
        
        CounterMonitor monitor = new CounterMonitor();
        
        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) monitor.increment();
        };
        
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        
        t1.start(); t2.start();
        t1.join(); t2.join();
        
        System.out.println("Final Count (Should safely be 2000): " + monitor.getCount());
    }
}
