package distributed.gossip;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GossipProtocol {

    static class Node {
        String id;
        boolean infected;
        List<Node> peers;

        public Node(String id) {
            this.id = id;
            this.infected = false;
            this.peers = new ArrayList<>();
        }

        public void addPeer(Node peer) {
            this.peers.add(peer);
        }

        public void infect() {
            this.infected = true;
        }

        public void gossip(int k) {
            if (!infected || peers.isEmpty()) return;
            
            Random rand = new Random();
            for (int i = 0; i < k; i++) {
                Node randomPeer = peers.get(rand.nextInt(peers.size()));
                if (!randomPeer.infected) {
                    System.out.println("Node " + this.id + " infecting Node " + randomPeer.id);
                    randomPeer.infect();
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Gossip Protocol Simulation ===");
        int numNodes = 10;
        int gossipFactorK = 2; // How many peers to contact per round
        
        List<Node> network = new ArrayList<>();
        for (int i = 0; i < numNodes; i++) {
            network.add(new Node("N" + i));
        }

        // Fully connected network for simplicity, excluding self
        for (Node n : network) {
            for (Node peer : network) {
                if (n != peer) n.addPeer(peer);
            }
        }

        // Infect the first node
        System.out.println("Infecting Node N0 to start the gossip...");
        network.get(0).infect();

        int round = 0;
        boolean allInfected = false;

        while (!allInfected && round < 10) {
            round++;
            System.out.println("\n--- Round " + round + " ---");
            
            // Nodes that are ALREADY infected at the start of the round will gossip
            List<Node> infectedThisRound = new ArrayList<>();
            for (Node n : network) {
                if (n.infected) infectedThisRound.add(n);
            }

            for (Node n : infectedThisRound) {
                n.gossip(gossipFactorK);
            }

            // Check completion
            allInfected = true;
            int infectedCount = 0;
            for (Node n : network) {
                if (!n.infected) allInfected = false;
                else infectedCount++;
            }
            System.out.println("Total infected after round " + round + ": " + infectedCount + "/" + numNodes);
        }

        if (allInfected) {
            System.out.println("\nGossip complete! All nodes received the information in " + round + " rounds.");
        } else {
            System.out.println("\nGossip stalled. Not all nodes were infected.");
        }
    }
}
