package distributed.leaderelection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Ring Algorithm Simulation
 * 
 * Used for Leader Election in systems organized in a logical ring.
 * Strategy: When a node detects leader failure, it creates an ELECTION message with its own ID,
 * and passes it to the next active neighbor in the ring. As the message traverses the ring, 
 * each active node adds its ID to the list. When the message returns to the initiator, 
 * it selects the highest ID from the list as the leader, and sends out a COORDINATOR message
 * around the ring.
 * 
 * Message Complexity: O(N) in best case (no concurrent elections), O(N^2) if all start at once.
 * Use case: Token ring networks or DHTs (Distributed Hash Tables) like Chord, where nodes
 * natively have a ring topology.
 */
public class RingAlgorithm {

    static class Node {
        int id;
        boolean active;
        int leaderId;
        Node nextNeighbor;

        public Node(int id) {
            this.id = id;
            this.active = true;
            this.leaderId = -1;
        }

        public void startElection() {
            if (!active) return;
            System.out.println("\nNode " + id + " detects leader failure, starting election.");
            List<Integer> activeList = new ArrayList<>();
            activeList.add(this.id);
            
            Node next = findNextActiveNeighbor();
            if (next != null) {
                System.out.println("Node " + id + " passing election list " + activeList + " to Node " + next.id);
                next.receiveElection(activeList, this.id);
            }
        }

        public void receiveElection(List<Integer> activeList, int initiatorId) {
            if (!active) return;

            if (this.id == initiatorId) {
                // Message has circulated the ring
                int newLeader = Collections.max(activeList);
                System.out.println("Node " + id + " received full list back. Highest ID is " + newLeader + ".");
                this.leaderId = newLeader;
                
                Node next = findNextActiveNeighbor();
                if (next != null && next.id != this.id) {
                    next.receiveCoordinator(newLeader, this.id);
                }
            } else {
                activeList.add(this.id);
                Node next = findNextActiveNeighbor();
                if (next != null) {
                    System.out.println("Node " + id + " passing election list " + activeList + " to Node " + next.id);
                    next.receiveElection(activeList, initiatorId);
                }
            }
        }

        public void receiveCoordinator(int newLeaderId, int initiatorId) {
            if (!active || this.id == initiatorId) return;
            
            System.out.println("Node " + id + " recognizes new leader: " + newLeaderId);
            this.leaderId = newLeaderId;
            
            Node next = findNextActiveNeighbor();
            if (next != null && next.id != initiatorId) {
                next.receiveCoordinator(newLeaderId, initiatorId);
            }
        }

        private Node findNextActiveNeighbor() {
            Node curr = this.nextNeighbor;
            while (curr != this && !curr.active) {
                curr = curr.nextNeighbor;
            }
            return curr == this ? null : curr;
        }
    }

    public static void main(String[] args) {
        Node n1 = new Node(10);
        Node n2 = new Node(20);
        Node n3 = new Node(30);
        Node n4 = new Node(40);
        Node n5 = new Node(50);

        // Form the ring
        n1.nextNeighbor = n2;
        n2.nextNeighbor = n3;
        n3.nextNeighbor = n4;
        n4.nextNeighbor = n5;
        n5.nextNeighbor = n1;

        System.out.println("Initial State: Node 50 is the leader.");
        n1.leaderId = 50; n2.leaderId = 50; n3.leaderId = 50; n4.leaderId = 50; n5.leaderId = 50;

        System.out.println("\n--- Node 50 Crashes ---");
        n5.active = false;

        System.out.println("\n--- Node 20 detects failure and starts election ---");
        n2.startElection();
    }
}
