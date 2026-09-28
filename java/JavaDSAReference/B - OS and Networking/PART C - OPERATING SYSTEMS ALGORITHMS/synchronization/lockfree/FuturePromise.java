package os.synchronization.lockfree;

import java.util.concurrent.CompletableFuture;

/**
 * FUTURE / PROMISE PATTERN
 * 
 * WHAT IT IS:
 * A concurrency pattern where a "Future" acts as a read-only placeholder for a 
 * result that does not yet exist. The "Promise" is the writable handle used to 
 * eventually supply that result.
 * 
 * WHY IT MATTERS:
 * It allows you to write non-blocking asynchronous code. Instead of waiting for 
 * a thread to finish, you attach callbacks to the Future.
 */
public class FuturePromise {

    public static void main(String[] args) throws Exception {
        System.out.println("--- FUTURE / PROMISE PATTERN ---");
        
        // CompletableFuture acts as both the Future (getter) and the Promise (setter)
        CompletableFuture<String> promise = new CompletableFuture<>();

        // 1. Thread waiting for the Future (Non-blocking callback attached)
        promise.thenAccept(result -> {
            System.out.println("Callback Triggered! Future yielded: " + result);
        });

        System.out.println("Main thread is doing other work while waiting...");

        // 2. Another thread acting as the Promise fulfiller
        new Thread(() -> {
            try { Thread.sleep(500); } catch (Exception e) {}
            System.out.println("Promise Fulfiller: Completing the task now!");
            promise.complete("Hello from the Future!"); // Fulfills the Promise
        }).start();

        // Prevent main thread from exiting before async thread finishes
        Thread.sleep(1000); 
    }
}
