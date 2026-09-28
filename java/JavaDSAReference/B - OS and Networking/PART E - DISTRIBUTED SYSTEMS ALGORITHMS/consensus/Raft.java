package distributed.consensus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Raft Consensus Algorithm Simulation (Leader Election + Log Replication)
 * 
 * Raft is more understandable than Paxos and is used in systems like etcd and Consul.
 * Nodes can be in one of three states: Follower, Candidate, or Leader.
 */
public class Raft {

    enum State { FOLLOWER, CANDIDATE, LEADER }

    static class Node {
        int id;
        State state;
        int currentTerm;
        Integer votedFor;
        List<String> log;
        
        // Simulating other nodes for network communication
        List<Node> cluster;

        public Node(int id) {
            this.id = id;
            this.state = State.FOLLOWER;
            this.currentTerm = 0;
            this.votedFor = null;
            this.log = new ArrayList<>();
        }

        public void setCluster(List<Node> cluster) {
            this.cluster = cluster;
        }

        // Leader Election Phase
        public void startElection() {
            this.state = State.CANDIDATE;
            this.currentTerm++;
            this.votedFor = this.id;
            int votes = 1; // Votes for self

            System.out.println("Node " + id + " starting election for term " + currentTerm);

            for (Node node : cluster) {
                if (node.id != this.id) {
                    if (node.requestVote(this.currentTerm, this.id)) {
                        votes++;
                    }
                }
            }

            int majority = (cluster.size() / 2) + 1;
            if (votes >= majority) {
                System.out.println("Node " + id + " became LEADER for term " + currentTerm);
                this.state = State.LEADER;
                sendHeartbeats();
            } else {
                System.out.println("Node " + id + " failed election. Reverting to FOLLOWER.");
                this.state = State.FOLLOWER;
            }
        }

        // RPC: Request Vote
        public boolean requestVote(int term, int candidateId) {
            if (term > this.currentTerm) {
                this.currentTerm = term;
                this.state = State.FOLLOWER;
                this.votedFor = candidateId;
                System.out.println("Node " + id + " voted for " + candidateId + " in term " + term);
                return true;
            }
            return false;
        }

        // Leader sending heartbeats (empty AppendEntries RPC)
        public void sendHeartbeats() {
            if (this.state != State.LEADER) return;
            System.out.println("Node " + id + " sending heartbeats to cluster.");
            for (Node node : cluster) {
                if (node.id != this.id) {
                    node.receiveHeartbeat(this.currentTerm, this.id);
                }
            }
        }

        public void receiveHeartbeat(int term, int leaderId) {
            if (term >= this.currentTerm) {
                this.currentTerm = term;
                this.state = State.FOLLOWER;
                // Heartbeat resets the election timeout in actual implementation
                System.out.println("Node " + id + " received heartbeat from Leader " + leaderId);
            }
        }

        // Log Replication
        public void proposeCommand(String command) {
            if (this.state == State.LEADER) {
                System.out.println("Leader " + id + " received command: " + command);
                this.log.add(command);
                
                int successfulReplications = 1;
                for (Node node : cluster) {
                    if (node.id != this.id) {
                        if (node.appendEntries(this.currentTerm, command)) {
                            successfulReplications++;
                        }
                    }
                }
                
                if (successfulReplications >= (cluster.size() / 2) + 1) {
                    System.out.println("Command '" + command + "' committed across majority!");
                }
            } else {
                System.out.println("Node " + id + " is not leader, cannot accept client command.");
            }
        }

        public boolean appendEntries(int term, String command) {
            if (term >= this.currentTerm) {
                this.log.add(command);
                System.out.println("Node " + id + " replicated command: " + command);
                return true;
            }
            return false;
        }
    }

    public static void main(String[] args) {
        List<Node> cluster = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            cluster.add(new Node(i));
        }
        for (Node node : cluster) {
            node.setCluster(cluster);
        }

        System.out.println("--- Starting Raft Cluster ---");
        // Simulate a timeout triggering an election
        Node candidate = cluster.get(new Random().nextInt(cluster.size()));
        candidate.startElection();

        // Simulate client requesting a command
        Node leader = null;
        for (Node node : cluster) {
            if (node.state == State.LEADER) {
                leader = node;
                break;
            }
        }
        
        if (leader != null) {
            System.out.println("\n--- Simulating Log Replication ---");
            leader.proposeCommand("SET x = 42");
        }
    }
}
