package net.clocksync;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Why Distributed Systems Need Clock Synchronization:
 * - Consistency: Without accurate clocks, multi-node databases (like Cassandra or Spanner) struggle 
 *   to determine the exact order of data mutations (Last-Write-Wins requires precise clocks).
 * - Coordination: Tasks that must be run simultaneously across nodes, or token expirations, 
 *   fail if node clocks drift apart.
 * - Fault tolerance: Leader election often relies on leases which are bound by time.
 */

public class BerkeleyAlgorithm {
    
    static class Node {
        String name;
        long localTime; // Simulated local time

        Node(String name, long localTime) {
            this.name = name;
            this.localTime = localTime;
        }

        void adjustTime(long offset) {
            localTime += offset;
            System.out.println(name + " adjusted its time by " + offset + " to: " + localTime);
        }
    }

    static class TimeDaemon {
        long daemonTime;

        TimeDaemon(long initialTime) {
            this.daemonTime = initialTime;
        }

        void synchronize(List<Node> nodes) {
            System.out.println("TimeDaemon starting synchronization cycle...");
            long sumDifferences = 0;
            List<Long> differences = new ArrayList<>();

            // 1. Daemon polls all nodes for their time and calculates the difference
            for (Node node : nodes) {
                long diff = node.localTime - daemonTime;
                differences.add(diff);
                sumDifferences += diff;
                System.out.println("Daemon perceives " + node.name + " difference as: " + diff);
            }

            // 2. Compute the average difference (including the daemon itself, which has diff 0)
            long avgDifference = sumDifferences / (nodes.size() + 1);
            System.out.println("Calculated Average Difference: " + avgDifference);

            // 3. Send adjustment commands to all nodes
            for (int i = 0; i < nodes.size(); i++) {
                // The adjustment is how far the node needs to move to reach (daemonTime + avgDifference)
                // NewTime = NodeTime + adjustment
                // NodeTime = daemonTime + diff
                // We want NewTime = daemonTime + avgDifference
                // Therefore, adjustment = avgDifference - diff
                long offset = avgDifference - differences.get(i);
                nodes.get(i).adjustTime(offset);
            }

            // 4. Daemon adjusts its own time
            daemonTime += avgDifference;
            System.out.println("TimeDaemon adjusted its own time to: " + daemonTime);
        }
    }

    public static void main(String[] args) {
        // Assume ideal current time is around 1,000,000. Nodes have drifted.
        TimeDaemon daemon = new TimeDaemon(1000000);
        Node n1 = new Node("Node1", 1000020); // Fast
        Node n2 = new Node("Node2", 999950);  // Slow
        Node n3 = new Node("Node3", 1000010); // Fast
        
        List<Node> networkNodes = Arrays.asList(n1, n2, n3);
        daemon.synchronize(networkNodes);
        
        // Verification: all times should be equal
        System.out.println("--- Post Sync Verification ---");
        System.out.println("Daemon: " + daemon.daemonTime);
        for(Node n : networkNodes) {
            System.out.println(n.name + ": " + n.localTime);
        }
    }
}
