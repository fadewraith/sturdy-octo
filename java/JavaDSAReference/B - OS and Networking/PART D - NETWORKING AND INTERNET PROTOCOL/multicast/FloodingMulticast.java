package net.multicast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FloodingMulticast {
    
    static class Node {
        int id;
        List<Node> neighbors = new ArrayList<>();
        Set<String> seenMessages = new HashSet<>();

        Node(int id) {
            this.id = id;
        }

        void addNeighbor(Node n) {
            neighbors.add(n);
            n.neighbors.add(this);
        }

        void receive(String msgId, String payload, Node sender) {
            // Discard if we've already seen this message (prevents infinite loops in cycles)
            if (seenMessages.contains(msgId)) {
                return;
            }
            
            seenMessages.add(msgId);
            System.out.println("Node " + id + " received message: " + payload);
            
            // Forward to all neighbors EXCEPT the one who sent it
            for (Node neighbor : neighbors) {
                if (neighbor != sender) {
                    System.out.println("Node " + id + " forwarding to Node " + neighbor.id);
                    neighbor.receive(msgId, payload, this);
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Flooding Multicast Simulation ---");
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        
        // Create a mesh/cycle topology
        n1.addNeighbor(n2);
        n2.addNeighbor(n3);
        n3.addNeighbor(n4);
        n4.addNeighbor(n1);
        n1.addNeighbor(n3);

        System.out.println("Starting multicast from Node 1...");
        n1.receive("msg-001", "Hello via Flooding!", null);
    }
}
