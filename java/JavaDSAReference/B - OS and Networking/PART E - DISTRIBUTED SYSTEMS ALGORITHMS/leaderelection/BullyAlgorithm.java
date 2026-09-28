package distributed.leaderelection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Bully Algorithm Simulation
 * 
 * Used for Leader Election in distributed systems.
 * Assumption: Nodes have unique IDs and communication is reliable.
 * Strategy: The node with the highest ID always wins. If a node detects the leader is down, 
 * it broadcasts an ELECTION message to all nodes with HIGHER IDs. If no higher ID responds, 
 * it wins and broadcasts a VICTORY/COORDINATOR message to lower IDs.
 * 
 * Message Complexity: O(N^2) worst case.
 * Use case: Static topologies where node IDs correlate with resource capacity or priority.
 */
public class BullyAlgorithm {

    static class Node {
        int id;
        boolean active;
        int leaderId;
        List<Node> cluster;

        public Node(int id) {
            this.id = id;
            this.active = true;
            this.leaderId = -1;
        }

        public void setCluster(List<Node> cluster) {
            this.cluster = cluster;
        }

        public void startElection() {
            if (!active) return;
            System.out.println("Node " + id + " is initiating an election...");
            
            boolean higherResponded = false;
            for (Node node : cluster) {
                if (node.id > this.id && node.active) {
                    System.out.println("Node " + id + " sends ELECTION to Node " + node.id);
                    // In a real system, this would be an async message. Here we call it directly.
                    node.receiveElection(this.id);
                    higherResponded = true;
                }
            }

            if (!higherResponded) {
                // I am the highest active node!
                System.out.println("Node " + id + " received no responses from higher nodes. Declaring victory!");
                declareVictory();
            } else {
                System.out.println("Node " + id + " steps down, higher node responded.");
            }
        }

        public void receiveElection(int fromId) {
            if (!active) return;
            System.out.println("Node " + id + " received ELECTION from Node " + fromId + ". Replying OK.");
            // Because I received an election message from a lower node, I should start my own election
            // if I haven't already.
            startElection();
        }

        public void declareVictory() {
            this.leaderId = this.id;
            for (Node node : cluster) {
                if (node.id < this.id && node.active) {
                    node.receiveVictory(this.id);
                }
            }
        }

        public void receiveVictory(int newLeaderId) {
            if (!active) return;
            this.leaderId = newLeaderId;
            System.out.println("Node " + id + " recognizes new leader: " + leaderId);
        }
    }

    public static void main(String[] args) {
        List<Node> cluster = new ArrayList<>();
        cluster.add(new Node(10));
        cluster.add(new Node(20));
        cluster.add(new Node(30));
        cluster.add(new Node(40));
        cluster.add(new Node(50));

        for (Node node : cluster) {
            node.setCluster(cluster);
        }

        System.out.println("Initial State: Node 50 is the leader.");
        for(Node n : cluster) n.leaderId = 50;

        System.out.println("\n--- Node 50 Crashes ---");
        cluster.get(4).active = false; // Node 50 fails

        System.out.println("\n--- Node 20 detects failure and starts election ---");
        cluster.get(1).startElection();

        System.out.println("\nFinal Leaders Array:");
        for (Node node : cluster) {
            if (node.active) {
                System.out.println("Node " + node.id + " -> Leader is " + node.leaderId);
            }
        }
    }
}
