package com.multithreading.sectionfivecopncurrency;

public class RaceCondition {

    public static void main(String[] args) throws InterruptedException {
        InventoryCounter inventoryCounter = new InventoryCounter();
        IncrementingThread incrementingThread = new IncrementingThread(inventoryCounter);
        DecrementingThread decrementingThread = new DecrementingThread(inventoryCounter);
        incrementingThread.start();
        decrementingThread.start();
        incrementingThread.join();
        decrementingThread.join();
        System.out.println("Final value of items: " + inventoryCounter.getItems());
    }

    public static class DecrementingThread extends Thread {
        private InventoryCounter inventoryCounter;

        public DecrementingThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                inventoryCounter.decrement();
            }
        }
    }

    public static class IncrementingThread extends Thread {
        private InventoryCounter inventoryCounter;

        public IncrementingThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                inventoryCounter.increment();
            }
        }
    }

    private static class InventoryCounter {
        private int items = 0;

        // synchronized - lock
        Object lock = new Object();

        // synchronized - monitor
//        public synchronized void increment() {
//            items++;
//        }
//
//        public synchronized void decrement() {
//            items--;
//        }
//
//        public synchronized int getItems() {
//            return items;
//        }

        // critical section
        public void increment() {
            synchronized(this.lock) {
                items++;
            }
        }

        // critical section
        public void decrement() {
            synchronized(this.lock) {
                items--;
            }
        }

        public int getItems() {
            synchronized (this.lock) {
                return items;
            }
        }
    }
}


/**
 * In your `RaceCondition` class, you've implemented a custom lock mechanism using `synchronized` blocks. Here's how it works:
 *
 * ### Your Current Implementation
 * ```java
 * private static class InventoryCounter {
 *     private int items = 0;
 *     Object lock = new Object();  // Your custom lock object
 *
 *     public void increment() {
 *         synchronized(this.lock) {  // Acquires lock on 'lock' object
 *             items++;
 *         }  // Releases lock here
 *     }
 *
 *     public void decrement() {
 *         synchronized(this.lock) {  // Same lock object
 *             items--;
 *         }
 *     }
 *
 *     public int getItems() {
 *         synchronized (this.lock) {  // Same lock object
 *             return items;
 *         }
 *     }
 * }
 * ```
 *
 * ### How It Works
 *
 * 1. **Lock Object**:
 *    - You've created a dedicated `lock` object (`Object lock = new Object()`) to use as the monitor.
 *    - This is better than using `synchronized` methods (which lock on `this`) because it gives you more explicit control.
 *
 * 2. **Synchronized Blocks**:
 *    - Each method uses `synchronized(this.lock)` to create a critical section.
 *    - Only one thread can execute any of these blocks at a time because they all use the same lock object.
 *
 * 3. **Thread Safety**:
 *    - The `lock` ensures that only one thread can modify `items` at a time.
 *    - The [getItems()](cci:1://file:///C:/Users/User/Desktop/multithread-concurrency-michael-pogrebinsky/src/main/java/com/multithreading/sectionfiveconcurrency/RaceCondition.java:40:8-44:9) method also uses the same lock, ensuring you get a consistent value.
 *
 * ### Key Points
 *
 * - **Visibility**: The lock ensures changes to `items` are visible to all threads.
 * - **Atomicity**: Each operation ([increment](cci:1://file:///C:/Users/User/Desktop/multithread-concurrency-michael-pogrebinsky/src/main/java/com/multithreading/sectionfiveconcurrency/RaceCondition.java:26:8-31:9), [decrement](cci:1://file:///C:/Users/User/Desktop/multithread-concurrency-michael-pogrebinsky/src/main/java/com/multithreading/sectionfiveconcurrency/RaceCondition.java:33:8-38:9), [getItems](cci:1://file:///C:/Users/User/Desktop/multithread-concurrency-michael-pogrebinsky/src/main/java/com/multithreading/sectionfiveconcurrency/RaceCondition.java:40:8-44:9)) is atomic.
 * - **Reentrancy**: The same thread can re-enter these blocks (Java locks are reentrant).
 *
 * ### Example Scenario
 * If Thread A is in [increment()](cci:1://file:///C:/Users/User/Desktop/multithread-concurrency-michael-pogrebinsky/src/main/java/com/multithreading/sectionfiveconcurrency/RaceCondition.java:26:8-31:9), other threads will block when trying to enter any `synchronized(lock)` block until Thread A exits its block.
 *
 * ### Suggestion
 * Make the `lock` field `final` to prevent accidental reassignment:
 * ```java
 * private final Object lock = new Object();
 * ```
 *
 * This ensures thread safety by preventing the lock reference from changing after initialization.
 *
 *
 *
 * =========================
 *
 * Let me clarify how the lock mechanism works in your code:
 *
 * ### The Magic Behind `synchronized`
 *
 * When you write:
 * ```java
 * synchronized(lock) {
 *     // critical section
 * }
 * ```
 *
 * Here's what happens under the hood:
 *
 * 1. **Lock Object's Monitor**:
 *    - Every Java object has an internal **monitor** (also called an **intrinsic lock** or **monitor lock**).
 *    - When a thread enters a `synchronized` block, it attempts to acquire this monitor.
 *
 * 2. **Acquiring the Lock**:
 *    - The first thread to execute `synchronized(lock)` acquires the monitor.
 *    - The monitor maintains a count of how many times the thread has acquired it (reentrancy).
 *
 * 3. **Lock State**:
 *    - **Locked**: When a thread holds the monitor, other threads must wait.
 *    - **Unlocked**: When no thread holds the monitor, it's available.
 *
 * ### Your Code Example
 *
 * ```java
 * Object lock = new Object();  // Creates a new object with its own monitor
 *
 * // Thread 1
 * synchronized(lock) {  // Acquires the monitor of 'lock'
 *     items++;          // Only one thread can be here at a time
 * }                     // Releases the monitor
 *
 * // Thread 2
 * synchronized(lock) {  // Waits here if Thread 1 is in the block
 *     items--;
 * }
 * ```
 *
 * ### Key Points
 *
 * - The lock object (`lock`) is just a regular object. The magic is in the `synchronized` keyword.
 * - The monitor is part of the JVM's internal implementation.
 * - All objects in Java have this monitor capability, but it's only used with `synchronized`.
 *
 * ### Visual Representation
 *
 * ```
 * Thread 1: synchronized(lock) ──┐
 *     ┌──────────────────────────┘
 *     ▼
 * [ LOCKED ]───┐  Thread 2 tries to enter:
 *     │        │  synchronized(lock) ──┐
 *     │        │  (blocks and waits)   │
 *     │        │                       ▼
 *     │        │                 [ WAITING ]
 *     │        │
 *     ▼        │
 * [CRITICAL]   │
 *     │        │
 *     ▼        │
 * [UNLOCKED]◄──┘  Thread 2 can now proceed
 *     ▲
 *     └──────────────────────────┐
 *                                │
 *                         Thread 2 enters critical section
 * ```
 *
 * ### Common Misconception
 *
 * You don't need to "do" anything special with the lock object. The JVM handles the locking mechanism internally. The object just serves as a unique identifier for the lock.
 *
 * Would you like me to explain any part of this in more detail?
 * */