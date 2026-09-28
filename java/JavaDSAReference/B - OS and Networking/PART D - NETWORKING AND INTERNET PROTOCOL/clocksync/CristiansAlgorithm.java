package net.clocksync;

/*
 * Why Distributed Systems Need Clock Synchronization:
 * 1. Event Ordering: Essential for determining the sequence of events across multiple machines 
 *    (e.g., "happens-before" relationships, resolving database write conflicts).
 * 2. Coordination: Timeouts, distributed leases, and scheduled jobs require nodes to agree on the time.
 * 3. Security: Protocols like Kerberos rely on timestamps to prevent replay attacks.
 * 4. Logging/Debugging: Consolidating logs from different machines requires accurate synchronized timestamps.
 */

public class CristiansAlgorithm {
    
    static class TimeServer {
        long getCurrentTime() {
            try {
                // Simulate network/processing delay at the server
                Thread.sleep(15); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            // Let's pretend the server is 5 seconds ahead of the actual system time
            return System.currentTimeMillis() + 5000;
        }
    }

    static class Client {
        long localTimeOffset = 0;

        void synchronize(TimeServer server) {
            System.out.println("Client initiating Cristian's Algorithm synchronization...");
            long t1 = System.currentTimeMillis();
            
            // Fetch time from server
            long serverTime = server.getCurrentTime();
            
            long t2 = System.currentTimeMillis();

            // Calculate Round Trip Time (RTT)
            long rtt = t2 - t1;
            
            // Estimate the server's time at the exact moment of T2
            long estimatedServerTime = serverTime + (rtt / 2);
            
            // Adjust the local clock offset
            localTimeOffset = estimatedServerTime - System.currentTimeMillis();

            System.out.println("T1 (Send Request): " + t1);
            System.out.println("T2 (Receive Reply): " + t2);
            System.out.println("Round Trip Time (RTT): " + rtt + " ms");
            System.out.println("Server Time Received: " + serverTime);
            System.out.println("Estimated Server Time at T2: " + estimatedServerTime);
            System.out.println("Calculated Local Offset: " + localTimeOffset + " ms");
        }

        long getAdjustedTime() {
            return System.currentTimeMillis() + localTimeOffset;
        }
    }

    public static void main(String[] args) {
        TimeServer server = new TimeServer();
        Client client = new Client();
        
        System.out.println("Client time BEFORE sync: " + System.currentTimeMillis());
        client.synchronize(server);
        System.out.println("Client time AFTER sync:  " + client.getAdjustedTime());
    }
}
