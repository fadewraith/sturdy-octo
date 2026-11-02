package distributed.mutualexclusion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * RICART-AGRAWALA DISTRIBUTED MUTUAL EXCLUSION
 *
 * WHAT IT IS:
 * N processes share one critical section (CS) and have NO shared memory and NO
 * central lock server - only messages. Ricart-Agrawala guarantees at most one
 * process is in the CS, using 2(N-1) messages per entry.
 *
 * WHEN TO USE THIS:
 * - The classic message-passing mutex (exam/interview favourite).
 * - In practice a lock service (ZooKeeper, etcd, Redis Redlock) is used instead,
 *   but they rest on the same ideas: a total order of requests and permission.
 *
 * STRATEGY:
 * Every request carries a Lamport timestamp; ties are broken by process id, so
 * all requests are TOTALLY ORDERED by (timestamp, id).
 * To enter: state = WANTED; send REQUEST(ts, me) to everyone; wait for N-1 REPLYs.
 * On REQUEST(ts, j) at process i:
 *   - if i is HELD, or i is WANTED with an EARLIER request (myTs, i) < (ts, j):
 *       DEFER the reply (remember j)
 *   - otherwise: REPLY immediately.
 * On exit: state = RELEASED; send the deferred REPLYs.
 *
 * WHY IT'S SAFE:
 * For two processes to both be in the CS, each must have replied to the other
 * while wanting the CS itself - but the one with the smaller (ts, id) defers.
 * It's also starvation-free: entries happen in (timestamp, id) order.
 * Messages may be delivered in ANY order (no FIFO needed).
 * Weakness: one crashed process blocks everyone (it never replies).
 *
 * COMPLEXITY:
 * 2(N - 1) messages per CS entry: N-1 requests + N-1 replies.
 */
public class RicartAgrawala {

    enum State { RELEASED, WANTED, HELD }
    enum Type { REQUEST, REPLY }

    static class Message {
        final Type type; final int from, to; final long ts;
        Message(Type type, int from, int to, long ts) { this.type = type; this.from = from; this.to = to; this.ts = ts; }
    }

    static class Process {
        final int id;
        long clock = 0;
        State state = State.RELEASED;
        long requestTs;
        int repliesNeeded;
        final boolean[] deferred;
        Process(int id, int n) { this.id = id; this.deferred = new boolean[n]; }

        boolean myRequestIsEarlier(long ts, int j) {
            return requestTs < ts || (requestTs == ts && id < j);
        }
    }

    private final Process[] procs;
    private final List<Message> network = new ArrayList<>(); // in flight, delivered in random order
    private final Random random;
    private final List<long[]> entries = new ArrayList<>();   // {requestTs, id} in CS entry order
    private int messages = 0;

    public RicartAgrawala(int n, Random random) {
        procs = new Process[n];
        for (int i = 0; i < n; i++) procs[i] = new Process(i, n);
        this.random = random;
    }

    private void send(Type type, int from, int to, long ts) {
        network.add(new Message(type, from, to, ts));
        messages++;
    }

    public void requestCS(int i) {
        Process p = procs[i];
        if (p.state != State.RELEASED) return;
        p.state = State.WANTED;
        p.requestTs = ++p.clock;
        p.repliesNeeded = procs.length - 1;
        for (int j = 0; j < procs.length; j++) if (j != i) send(Type.REQUEST, i, j, p.requestTs);
        if (p.repliesNeeded == 0) enter(p);
    }

    private void enter(Process p) {
        p.state = State.HELD;
        entries.add(new long[]{p.requestTs, p.id});
    }

    public void releaseCS(int i) {
        Process p = procs[i];
        if (p.state != State.HELD) return;
        p.state = State.RELEASED;
        p.clock++;
        for (int j = 0; j < procs.length; j++) {
            if (p.deferred[j]) { p.deferred[j] = false; send(Type.REPLY, i, j, p.clock); }
        }
    }

    /** Delivers one random in-flight message. Returns false if none. */
    public boolean deliverRandom() {
        if (network.isEmpty()) return false;
        Message m = network.remove(random.nextInt(network.size()));
        Process p = procs[m.to];
        p.clock = Math.max(p.clock, m.ts) + 1; // Lamport clock rule
        if (m.type == Type.REQUEST) {
            boolean defer = p.state == State.HELD || (p.state == State.WANTED && p.myRequestIsEarlier(m.ts, m.from));
            if (defer) p.deferred[m.from] = true;
            else send(Type.REPLY, p.id, m.from, p.clock);
        } else {
            if (--p.repliesNeeded == 0) enter(p);
        }
        return true;
    }

    public int inCriticalSection() {
        int c = 0;
        for (Process p : procs) if (p.state == State.HELD) c++;
        return c;
    }

    public boolean isHolding(int i) { return procs[i].state == State.HELD; }
    public boolean isIdle(int i) { return procs[i].state == State.RELEASED; }
    public List<long[]> entries() { return entries; }
    public int messageCount() { return messages; }

    public static void main(String[] args) {
        System.out.println("--- RICART-AGRAWALA DEMO ---");
        int n = 4;
        RicartAgrawala ra = new RicartAgrawala(n, new Random(3));
        int maxInside = 0, requests = 0;
        for (int step = 0; step < 2000; step++) {
            int i = ra.random.nextInt(n);
            int action = ra.random.nextInt(10);
            if (action == 0 && ra.isIdle(i) && requests < 20) { ra.requestCS(i); requests++; }
            else if (action == 1 && ra.isHolding(i)) ra.releaseCS(i);
            else ra.deliverRandom();
            maxInside = Math.max(maxInside, ra.inCriticalSection());
        }
        // drain: let everyone finish
        while (ra.deliverRandom() || ra.inCriticalSection() > 0) {
            for (int i = 0; i < n; i++) if (ra.isHolding(i)) ra.releaseCS(i);
        }
        System.out.println("Requests: " + requests + ", CS entries: " + ra.entries().size()
                + ", max processes inside at once: " + maxInside);           // 20, 20, 1
        System.out.println("Messages: " + ra.messageCount() + " = 2(N-1) per entry = " + 2 * (n - 1) * requests);
        StringBuilder order = new StringBuilder();
        for (long[] e : ra.entries()) order.append("(").append(e[0]).append(",P").append(e[1]).append(") ");
        System.out.println("Entry order by (timestamp, id): " + order);
    }
}
