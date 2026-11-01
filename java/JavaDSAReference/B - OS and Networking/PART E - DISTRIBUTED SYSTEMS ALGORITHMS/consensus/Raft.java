package distributed.consensus;

import java.util.ArrayList;
import java.util.List;

/**
 * Raft Consensus Algorithm Simulation (Leader Election + Log Replication)
 *
 * Raft is more understandable than Paxos and is used in systems like etcd and Consul.
 * Nodes can be in one of three states: Follower, Candidate, or Leader.
 *
 * This simulation runs RPCs as direct method calls (no real network/timers) but keeps
 * the rules that make Raft SAFE - these are the parts interviewers ask about:
 *
 * 1. TERMS: any message with a higher term makes the receiver adopt it and step down
 *    to follower. Messages with a lower term are rejected.
 * 2. ONE VOTE PER TERM: votedFor is remembered per term.
 * 3. ELECTION RESTRICTION: a node only votes for a candidate whose log is AT LEAST AS
 *    UP-TO-DATE as its own (compare last log term first, then last log index).
 *    => a node that missed committed entries can never become leader.
 * 4. LOG MATCHING (AppendEntries consistency check): the leader sends prevLogIndex and
 *    prevLogTerm. The follower rejects unless its log has that exact entry. On reject
 *    the leader backs up nextIndex and retries; conflicting entries on the follower
 *    are deleted and overwritten. => all logs converge to the leader's log.
 * 5. COMMIT RULE: the leader only commits an entry from its CURRENT term once it is
 *    on a majority (older-term entries are committed indirectly with it).
 *
 * Log indexes are 1-based like the Raft paper (index 0 = empty log, term 0).
 */
public class Raft {

    enum State { FOLLOWER, CANDIDATE, LEADER }

    static class LogEntry {
        final int term;
        final String command;

        LogEntry(int term, String command) {
            this.term = term;
            this.command = command;
        }

        @Override
        public String toString() {
            return command + "(t" + term + ")";
        }
    }

    static class AppendReply {
        final int term;
        final boolean success;
        final int matchIndex;

        AppendReply(int term, boolean success, int matchIndex) {
            this.term = term;
            this.success = success;
            this.matchIndex = matchIndex;
        }
    }

    static class Node {
        int id;
        State state = State.FOLLOWER;
        int currentTerm = 0;
        Integer votedFor = null;
        List<LogEntry> log = new ArrayList<>(); // log.get(i) holds index i + 1
        int commitIndex = 0;
        boolean up = true; // false = crashed / unreachable

        // Leader-only bookkeeping (index per node id)
        int[] nextIndex;
        int[] matchIndex;

        // Simulating other nodes for network communication
        List<Node> cluster;

        public Node(int id) {
            this.id = id;
        }

        public void setCluster(List<Node> cluster) {
            this.cluster = cluster;
        }

        int lastLogIndex() {
            return log.size();
        }

        int termAt(int index) {
            return index == 0 ? 0 : log.get(index - 1).term;
        }

        int majority() {
            return cluster.size() / 2 + 1;
        }

        // Rule 1: a higher term always wins
        void observeTerm(int term) {
            if (term > currentTerm) {
                currentTerm = term;
                votedFor = null;
                state = State.FOLLOWER;
            }
        }

        // ---------------- Leader election ----------------

        public boolean startElection() {
            if (!up) return false;
            state = State.CANDIDATE;
            currentTerm++;
            votedFor = id;
            int votes = 1; // Votes for self
            System.out.println("Node " + id + " starts election for term " + currentTerm
                    + " (last log term " + termAt(lastLogIndex()) + ", index " + lastLogIndex() + ")");

            for (Node node : cluster) {
                if (node.id == id || !node.up) continue;
                if (node.requestVote(currentTerm, id, lastLogIndex(), termAt(lastLogIndex()))) votes++;
                if (node.currentTerm > currentTerm) { // someone is ahead: give up
                    observeTerm(node.currentTerm);
                    return false;
                }
            }

            if (state == State.CANDIDATE && votes >= majority()) {
                System.out.println("  -> Node " + id + " became LEADER for term " + currentTerm + " with " + votes + " votes");
                state = State.LEADER;
                nextIndex = new int[cluster.size() + 1];
                matchIndex = new int[cluster.size() + 1];
                for (Node n : cluster) nextIndex[n.id] = lastLogIndex() + 1; // optimistic start
                replicate(); // first (possibly empty) AppendEntries = heartbeat
                return true;
            }
            System.out.println("  -> Node " + id + " lost the election (" + votes + " votes). Back to FOLLOWER.");
            state = State.FOLLOWER;
            return false;
        }

        // RPC: Request Vote
        public boolean requestVote(int term, int candidateId, int candLastIndex, int candLastTerm) {
            observeTerm(term);
            if (term < currentTerm) return false;

            // Rule 3: election restriction - is the candidate's log at least as up-to-date as mine?
            int myLastTerm = termAt(lastLogIndex());
            boolean upToDate = candLastTerm > myLastTerm
                    || (candLastTerm == myLastTerm && candLastIndex >= lastLogIndex());

            boolean canVote = votedFor == null || votedFor == candidateId; // Rule 2
            if (canVote && upToDate) {
                votedFor = candidateId;
                System.out.println("  Node " + id + " votes for " + candidateId);
                return true;
            }
            System.out.println("  Node " + id + " refuses " + candidateId
                    + (upToDate ? " (already voted this term)" : " (candidate's log is behind mine)"));
            return false;
        }

        // ---------------- Log replication ----------------

        /** Client request. Only the leader accepts it; returns true once committed. */
        public boolean proposeCommand(String command) {
            if (state != State.LEADER || !up) {
                System.out.println("Node " + id + " is not leader, cannot accept client command.");
                return false;
            }
            System.out.println("Leader " + id + " received command: " + command);
            log.add(new LogEntry(currentTerm, command));
            int before = commitIndex;
            replicate();
            boolean committed = commitIndex >= lastLogIndex();
            System.out.println(committed
                    ? "  Command '" + command + "' COMMITTED (commitIndex " + before + " -> " + commitIndex + ")"
                    : "  Command '" + command + "' NOT committed - no majority reachable");
            return committed;
        }

        /** Sends AppendEntries to every reachable follower, retrying with an earlier nextIndex on conflicts. */
        void replicate() {
            for (Node f : cluster) {
                if (f.id == id || !f.up || state != State.LEADER) continue;
                while (true) {
                    int prevIndex = nextIndex[f.id] - 1;
                    List<LogEntry> entries = new ArrayList<>(log.subList(prevIndex, log.size()));
                    AppendReply reply = f.appendEntries(currentTerm, id, prevIndex, termAt(prevIndex), entries, commitIndex);
                    if (reply.term > currentTerm) { // a newer leader exists: step down
                        observeTerm(reply.term);
                        return;
                    }
                    if (reply.success) {
                        matchIndex[f.id] = reply.matchIndex;
                        nextIndex[f.id] = reply.matchIndex + 1;
                        break;
                    }
                    nextIndex[f.id]--; // Rule 4: back up and retry
                    System.out.println("  Node " + f.id + " rejected (log mismatch at index " + prevIndex
                            + "), leader backs up nextIndex to " + nextIndex[f.id]);
                }
            }
            advanceCommitIndex();
            // Second round so followers learn the new commitIndex (a real leader does this on the next heartbeat)
            for (Node f : cluster) {
                if (f.id != id && f.up && state == State.LEADER) {
                    f.appendEntries(currentTerm, id, lastLogIndex(), termAt(lastLogIndex()), new ArrayList<>(), commitIndex);
                }
            }
        }

        // Rule 5: commit the highest index N stored on a majority whose term is the CURRENT term
        void advanceCommitIndex() {
            for (int n = lastLogIndex(); n > commitIndex; n--) {
                if (termAt(n) != currentTerm) break;
                int count = 1; // the leader itself
                for (Node f : cluster) if (f.id != id && matchIndex[f.id] >= n) count++;
                if (count >= majority()) {
                    commitIndex = n;
                    break;
                }
            }
        }

        // RPC: Append Entries
        public AppendReply appendEntries(int term, int leaderId, int prevLogIndex, int prevLogTerm,
                                         List<LogEntry> entries, int leaderCommit) {
            observeTerm(term);
            if (term < currentTerm) return new AppendReply(currentTerm, false, 0);
            state = State.FOLLOWER; // a valid leader exists for this term

            // Rule 4: consistency check
            if (prevLogIndex > lastLogIndex() || termAt(prevLogIndex) != prevLogTerm) {
                return new AppendReply(currentTerm, false, 0);
            }

            // Append, deleting any conflicting suffix first
            int index = prevLogIndex;
            for (LogEntry e : entries) {
                index++;
                if (index <= lastLogIndex() && termAt(index) != e.term) {
                    System.out.println("  Node " + id + " deletes conflicting entries from index " + index
                            + ": " + log.subList(index - 1, log.size()));
                    log.subList(index - 1, log.size()).clear();
                }
                if (index > lastLogIndex()) log.add(e);
            }
            if (leaderCommit > commitIndex) commitIndex = Math.min(leaderCommit, index);
            return new AppendReply(currentTerm, true, index);
        }
    }

    static void printLogs(List<Node> cluster) {
        for (Node n : cluster) {
            System.out.println("  Node " + n.id + (n.up ? "" : " [DOWN]") + " term=" + n.currentTerm
                    + " commit=" + n.commitIndex + " log=" + n.log);
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
        Node n1 = cluster.get(0), n2 = cluster.get(1), n5 = cluster.get(4);

        System.out.println("--- 1. Normal operation ---");
        n1.startElection();
        n1.proposeCommand("x=1");

        System.out.println("\n--- 2. Node 1 is cut off with only Node 5: its write cannot commit ---");
        for (Node n : cluster) n.up = (n == n1 || n == n5);
        n1.proposeCommand("x=bad");

        System.out.println("\n--- 3. Majority side (2, 3, 4) elects a new leader ---");
        for (Node n : cluster) n.up = !(n == n1 || n == n5);
        n2.startElection();
        n2.proposeCommand("x=2");
        printLogs(cluster);

        System.out.println("\n--- 4. Network heals. Node 5 (stale log) tries to become leader ---");
        for (Node n : cluster) n.up = true;
        n5.startElection(); // must LOSE: election restriction

        System.out.println("\n--- 5. An up-to-date node wins and repairs the stale logs ---");
        n2.startElection();
        n2.proposeCommand("x=3");
        printLogs(cluster);
    }
}
