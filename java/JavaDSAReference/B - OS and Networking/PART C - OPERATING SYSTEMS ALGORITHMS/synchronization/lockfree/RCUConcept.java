package os.synchronization.lockfree;

import java.util.concurrent.atomic.AtomicReference;

/**
 * READ-COPY-UPDATE (RCU) CONCEPT
 * 
 * WHAT IT IS:
 * Extremely fast synchronization mechanism common in the Linux Kernel.
 * Optimized for Read-Heavy workloads. 
 * Readers NEVER block. Writers make a COPY of the data, update the copy, and 
 * atomically swap the pointer. Old versions are garbage collected when all 
 * current readers are done.
 */
public class RCUConcept {

    static class DataConfig {
        final int configValue;
        DataConfig(int val) { this.configValue = val; }
    }

    // Atomic reference acts as the single point of truth
    private AtomicReference<DataConfig> currentConfig = new AtomicReference<>(new DataConfig(100));

    // READERS NEVER BLOCK
    public void read() {
        DataConfig conf = currentConfig.get();
        System.out.println(Thread.currentThread().getName() + " read config: " + conf.configValue);
    }

    // WRITERS COPY AND SWAP
    public void write(int newValue) {
        System.out.println(Thread.currentThread().getName() + " wants to write " + newValue);
        
        // 1. Read
        DataConfig oldConf = currentConfig.get();
        
        // 2. Copy & Update
        DataConfig newConf = new DataConfig(newValue); 
        
        // 3. Atomically Swap (Publish)
        currentConfig.compareAndSet(oldConf, newConf);
        
        System.out.println(Thread.currentThread().getName() + " successfully updated via RCU!");
        // Old oldConf is naturally garbage collected when readers finish with it
    }

    public static void main(String[] args) throws Exception {
        System.out.println("--- READ-COPY-UPDATE (RCU) ---");
        RCUConcept rcu = new RCUConcept();
        
        Thread reader1 = new Thread(() -> rcu.read(), "Reader-1");
        Thread reader2 = new Thread(() -> rcu.read(), "Reader-2");
        Thread writer = new Thread(() -> rcu.write(500), "Writer-1");
        
        reader1.start();
        writer.start();
        Thread.sleep(10); // Guarantee writer finishes swap
        reader2.start(); // Sees new value!
    }
}
