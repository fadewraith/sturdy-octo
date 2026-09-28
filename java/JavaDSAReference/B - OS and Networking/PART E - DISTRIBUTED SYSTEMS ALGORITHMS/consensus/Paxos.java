package distributed.consensus;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Basic Single-Decree Paxos implementation.
 * 
 * Paxos is a consensus algorithm that ensures a single value is chosen among a group of nodes,
 * even in the presence of failures (crash faults, message delays/drops).
 * 
 * The algorithm has two phases:
 * Phase 1: Prepare/Promise
 * Phase 2: Accept/Accepted
 */
public class Paxos {

    static class Node {
        int id;
        int maxProposalIdSeen = -1;
        int acceptedProposalId = -1;
        String acceptedValue = null;

        public Node(int id) {
            this.id = id;
        }

        // Phase 1: Prepare
        // A proposer sends a Prepare message with a proposal ID (n)
        public Promise onPrepare(int n) {
            System.out.println("Node " + id + " received Prepare(" + n + ")");
            if (n > maxProposalIdSeen) {
                maxProposalIdSeen = n;
                return new Promise(true, acceptedProposalId, acceptedValue);
            }
            return new Promise(false, -1, null);
        }

        // Phase 2: Accept
        // A proposer sends an Accept message with a proposal ID (n) and value (v)
        public boolean onAccept(int n, String value) {
            System.out.println("Node " + id + " received Accept(" + n + ", " + value + ")");
            if (n >= maxProposalIdSeen) {
                maxProposalIdSeen = n;
                acceptedProposalId = n;
                acceptedValue = value;
                return true;
            }
            return false;
        }
    }

    static class Promise {
        boolean ack;
        int prevAcceptedId;
        String prevAcceptedValue;

        public Promise(boolean ack, int prevAcceptedId, String prevAcceptedValue) {
            this.ack = ack;
            this.prevAcceptedId = prevAcceptedId;
            this.prevAcceptedValue = prevAcceptedValue;
        }
    }

    public static void main(String[] args) {
        Node[] cluster = {new Node(1), new Node(2), new Node(3), new Node(4), new Node(5)};
        int majority = (cluster.length / 2) + 1;

        // Proposer attempts to propose a value
        int proposalId = new Random().nextInt(100) + 1; // Needs to be globally unique in practice
        String proposedValue = "VALUE_A";

        System.out.println("--- Phase 1: Prepare ---");
        int promises = 0;
        int highestPrevAcceptedId = -1;
        String valueToPropose = proposedValue;

        for (Node node : cluster) {
            Promise p = node.onPrepare(proposalId);
            if (p.ack) {
                promises++;
                if (p.prevAcceptedId > highestPrevAcceptedId) {
                    highestPrevAcceptedId = p.prevAcceptedId;
                    valueToPropose = p.prevAcceptedValue;
                }
            }
        }

        System.out.println("--- Phase 2: Accept ---");
        if (promises >= majority) {
            System.out.println("Majority promise achieved. Proceeding to Accept phase with value: " + valueToPropose);
            int accepts = 0;
            for (Node node : cluster) {
                if (node.onAccept(proposalId, valueToPropose)) {
                    accepts++;
                }
            }
            if (accepts >= majority) {
                System.out.println("Consensus reached! Value chosen: " + valueToPropose);
            } else {
                System.out.println("Failed to reach consensus in Accept phase.");
            }
        } else {
            System.out.println("Failed to get majority promise.");
        }
    }
}
