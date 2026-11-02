package distributed.failuredetection;

import java.util.ArrayDeque;

/**
 * FAILURE DETECTORS (Heartbeat Timeout and Phi Accrual)
 *
 * WHAT IT IS:
 * Nodes send periodic HEARTBEATS. A failure detector decides when a silent node
 * should be SUSPECTED dead. In an asynchronous network you can never be sure
 * (a slow node looks exactly like a dead one), so detectors trade detection speed
 * against false alarms.
 *
 * WHEN TO USE THIS:
 * Cluster membership and leader election in every distributed database and
 * orchestrator (Cassandra, Akka Cluster, Kubernetes node health, Consul/SWIM).
 *
 * 1. HEARTBEAT TIMEOUT (fixed threshold)
 *    Suspect the node if no heartbeat arrived for T ms. Simple, but T must be tuned
 *    by hand: too small -> false alarms when the network is slow or jittery,
 *    too large -> slow detection.
 *
 * 2. PHI ACCRUAL (Hayashibara et al., used by Cassandra and Akka)
 *    Instead of a yes/no answer, output a SUSPICION LEVEL phi that keeps growing
 *    while the node is silent. It LEARNS the normal heartbeat rhythm:
 *      - keep a sliding window of recent inter-arrival times (mean mu, std dev sigma)
 *      - P_later(t) = probability that a heartbeat comes MORE than t ms after the
 *        last one, assuming intervals ~ Normal(mu, sigma)
 *      - phi(t) = -log10(P_later(t))
 *    phi = 1 means a 10% chance we're wrong to suspect it, phi = 2 -> 1%,
 *    phi = 8 -> 0.000001%. The application picks a threshold (Cassandra: 8).
 *    A node with steady 1 s heartbeats gets suspected quickly after ~1 s of
 *    silence; a node on a jittery link automatically gets more slack.
 *    The normal CDF is approximated with the logistic formula Akka uses:
 *      e = exp(-y * (1.5976 + 0.070566 * y^2)),  y = (t - mu) / sigma
 *
 * COMPLEXITY:
 * Heartbeat: O(1) (running sums over the window). phi(): O(1).
 * Space: O(window size).
 */
public class FailureDetectors {

    // ---------------- 1. Fixed timeout ----------------
    public static class HeartbeatTimeoutDetector {
        private final long timeoutMillis;
        private long lastHeartbeat = Long.MIN_VALUE;

        public HeartbeatTimeoutDetector(long timeoutMillis) { this.timeoutMillis = timeoutMillis; }

        public void heartbeat(long now) { lastHeartbeat = now; }

        public boolean isAlive(long now) {
            return lastHeartbeat != Long.MIN_VALUE && now - lastHeartbeat <= timeoutMillis;
        }
    }

    // ---------------- 2. Phi accrual ----------------
    public static class PhiAccrualDetector {
        private final int windowSize;
        private final double minStdDevMillis;
        private final double threshold;
        private final ArrayDeque<Long> intervals = new ArrayDeque<>();
        private double sum = 0, sumSquares = 0;
        private long lastHeartbeat = Long.MIN_VALUE;

        /**
         * @param windowSize       how many recent intervals to learn from (Akka: 1000)
         * @param minStdDevMillis  floor for sigma, so a perfectly regular sender isn't
         *                         suspected after 1 ms of lateness (Akka: 100 ms)
         * @param threshold        phi at which the node counts as down (Cassandra: 8)
         */
        public PhiAccrualDetector(int windowSize, double minStdDevMillis, double threshold) {
            this.windowSize = windowSize;
            this.minStdDevMillis = minStdDevMillis;
            this.threshold = threshold;
        }

        public void heartbeat(long now) {
            if (lastHeartbeat != Long.MIN_VALUE) {
                long interval = now - lastHeartbeat;
                intervals.addLast(interval);
                sum += interval;
                sumSquares += (double) interval * interval;
                if (intervals.size() > windowSize) {
                    long old = intervals.pollFirst();
                    sum -= old;
                    sumSquares -= (double) old * old;
                }
            }
            lastHeartbeat = now;
        }

        public double mean() { return intervals.isEmpty() ? 0 : sum / intervals.size(); }

        public double stdDev() {
            if (intervals.isEmpty()) return minStdDevMillis;
            double m = mean();
            double variance = Math.max(0, sumSquares / intervals.size() - m * m);
            return Math.max(Math.sqrt(variance), minStdDevMillis);
        }

        /** Suspicion level at time now (0 if no history yet). */
        public double phi(long now) {
            if (intervals.isEmpty()) return 0.0;
            double elapsed = now - lastHeartbeat;
            double y = (elapsed - mean()) / stdDev();
            double e = Math.exp(-y * (1.5976 + 0.070566 * y * y));
            // P(next heartbeat comes later than `elapsed`), written to stay accurate in both tails
            double pLater = elapsed > mean() ? e / (1.0 + e) : 1.0 - 1.0 / (1.0 + e);
            return Math.max(0.0, -Math.log10(Math.max(pLater, Double.MIN_VALUE))); // MIN_VALUE: avoid log(0) = infinity
        }

        public boolean isAvailable(long now) { return phi(now) < threshold; }

        public long lastHeartbeat() { return lastHeartbeat; }
    }

    public static void main(String[] args) {
        System.out.println("--- FAILURE DETECTORS DEMO ---");

        // Node A: steady heartbeats every 1000 ms. Node B: jittery link, 500..2500 ms.
        PhiAccrualDetector steady = new PhiAccrualDetector(100, 50, 8.0);
        PhiAccrualDetector jittery = new PhiAccrualDetector(100, 50, 8.0);
        java.util.Random rng = new java.util.Random(5);
        long t = 0, tj = 0;
        for (int i = 0; i < 100; i++) {
            steady.heartbeat(t);
            t += 1000;
            jittery.heartbeat(tj);
            tj += 500 + rng.nextInt(2001);
        }
        System.out.printf("Learned: steady mean %.0f ms (sd %.0f), jittery mean %.0f ms (sd %.0f)%n",
                steady.mean(), steady.stdDev(), jittery.mean(), jittery.stdDev());
        System.out.println("Silence (ms) | phi steady | phi jittery");
        for (long silence : new long[]{500, 1000, 1200, 1500, 2000, 3000, 5000}) {
            System.out.printf("%12d | %10.2f | %11.2f%n", silence, steady.phi(steady.lastHeartbeat() + silence), jittery.phi(jittery.lastHeartbeat() + silence));
        }
        // The steady node crosses phi = 8 after ~1.3 s of silence; the jittery one is
        // given several seconds - same threshold, different learned behaviour.

        HeartbeatTimeoutDetector fixed = new HeartbeatTimeoutDetector(1500);
        fixed.heartbeat(0);
        System.out.println("\nFixed 1500 ms timeout: alive at 1400? " + fixed.isAlive(1400) + ", at 1600? " + fixed.isAlive(1600));
    }
}
