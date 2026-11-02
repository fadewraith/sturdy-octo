package net.ratelimiting;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * RATE LIMITERS (Fixed Window, Sliding Window Log, Sliding Window Counter, Token Bucket)
 *
 * WHAT IT IS:
 * "Allow at most LIMIT requests per WINDOW" - e.g. 100 API calls per minute per user.
 * Every API gateway (Nginx, Envoy, Stripe, GitHub) uses one of these four designs.
 *
 * 1. FIXED WINDOW COUNTER
 *    Cut time into aligned windows [0, W), [W, 2W), ... and count per window.
 *    + O(1) memory, trivial.  - BOUNDARY BURST: LIMIT requests at the end of one
 *    window plus LIMIT at the start of the next = 2x LIMIT within a short span.
 *
 * 2. SLIDING WINDOW LOG
 *    Keep the timestamp of every accepted request; drop those older than W.
 *    Accept if fewer than LIMIT remain. EXACT: no span of length W ever holds more
 *    than LIMIT accepted requests.  - O(LIMIT) memory per user.
 *
 * 3. SLIDING WINDOW COUNTER (Cloudflare's approach)
 *    Keep only two counters: previous window and current window. Estimate the
 *    sliding count as  current + previous * (fraction of the previous window that
 *    still overlaps the sliding window). Assumes the previous window's requests
 *    were spread evenly.  + O(1) memory, smooths the boundary burst.
 *    - An approximation (good in practice).
 *
 * 4. TOKEN BUCKET (time-based)
 *    A bucket holds up to CAPACITY tokens and refills at RATE tokens per second.
 *    A request takes one token. Allows short bursts (up to CAPACITY) while the
 *    long-run average stays at RATE. Refill is computed lazily from the elapsed
 *    time on each call - no background timer needed.
 *    (TokenBucket.java in trafficshaping is the step-based packet-shaping version.)
 *
 * TIME IS PASSED IN (nowMillis) instead of reading the clock, so the behaviour is
 * deterministic and testable. A real limiter would pass System.currentTimeMillis().
 * Per-user limiting = one limiter per key (see KeyedRateLimiter).
 *
 * COMPLEXITY (per request):
 * Fixed window, sliding counter, token bucket: O(1) time, O(1) space.
 * Sliding log: O(1) amortized time, O(LIMIT) space.
 */
public class RateLimiters {

    public interface RateLimiter {
        /** True if the request at time nowMillis is allowed (and counts it). */
        boolean tryAcquire(long nowMillis);
    }

    // ---------------- 1. Fixed window ----------------
    public static class FixedWindowCounter implements RateLimiter {
        private final int limit;
        private final long windowMillis;
        private long windowStart = Long.MIN_VALUE;
        private int count = 0;

        public FixedWindowCounter(int limit, long windowMillis) {
            this.limit = limit;
            this.windowMillis = windowMillis;
        }

        @Override
        public boolean tryAcquire(long now) {
            long start = Math.floorDiv(now, windowMillis) * windowMillis; // aligned window
            if (start != windowStart) {
                windowStart = start;
                count = 0;
            }
            if (count < limit) {
                count++;
                return true;
            }
            return false;
        }
    }

    // ---------------- 2. Sliding window log ----------------
    public static class SlidingWindowLog implements RateLimiter {
        private final int limit;
        private final long windowMillis;
        private final ArrayDeque<Long> log = new ArrayDeque<>();

        public SlidingWindowLog(int limit, long windowMillis) {
            this.limit = limit;
            this.windowMillis = windowMillis;
        }

        @Override
        public boolean tryAcquire(long now) {
            // the window is (now - W, now]: forget requests at or before now - W
            while (!log.isEmpty() && log.peekFirst() <= now - windowMillis) log.pollFirst();
            if (log.size() < limit) {
                log.addLast(now);
                return true;
            }
            return false;
        }
    }

    // ---------------- 3. Sliding window counter ----------------
    public static class SlidingWindowCounter implements RateLimiter {
        private final int limit;
        private final long windowMillis;
        private long currentStart = Long.MIN_VALUE;
        private int currentCount = 0, previousCount = 0;

        public SlidingWindowCounter(int limit, long windowMillis) {
            this.limit = limit;
            this.windowMillis = windowMillis;
        }

        @Override
        public boolean tryAcquire(long now) {
            long start = Math.floorDiv(now, windowMillis) * windowMillis;
            if (start != currentStart) {
                // moved on: the old current becomes previous (or zero if we skipped a window)
                previousCount = (start - currentStart == windowMillis) ? currentCount : 0;
                currentCount = 0;
                currentStart = start;
            }
            double overlap = (double) (windowMillis - (now - start)) / windowMillis; // share of previous window still inside
            double estimate = currentCount + previousCount * overlap;
            if (estimate < limit) {
                currentCount++;
                return true;
            }
            return false;
        }
    }

    // ---------------- 4. Token bucket ----------------
    public static class TokenBucketLimiter implements RateLimiter {
        private final double capacity;
        private final double tokensPerMilli;
        private double tokens;
        private long lastRefill;
        private boolean started = false;

        public TokenBucketLimiter(int capacity, double tokensPerSecond) {
            this.capacity = capacity;
            this.tokensPerMilli = tokensPerSecond / 1000.0;
            this.tokens = capacity; // start full
        }

        @Override
        public boolean tryAcquire(long now) {
            if (!started) { lastRefill = now; started = true; }
            if (now > lastRefill) {
                tokens = Math.min(capacity, tokens + (now - lastRefill) * tokensPerMilli);
                lastRefill = now;
            }
            if (tokens >= 1.0) {
                tokens -= 1.0;
                return true;
            }
            return false;
        }
    }

    // ---------------- Per-key limiting ----------------
    public static class KeyedRateLimiter {
        private final Map<String, RateLimiter> perKey = new HashMap<>();
        private final Supplier<RateLimiter> factory;

        public KeyedRateLimiter(Supplier<RateLimiter> factory) { this.factory = factory; }

        public boolean tryAcquire(String key, long now) {
            return perKey.computeIfAbsent(key, k -> factory.get()).tryAcquire(now);
        }
    }

    private static String run(RateLimiter limiter, long[] times) {
        StringBuilder sb = new StringBuilder();
        for (long t : times) sb.append(limiter.tryAcquire(t) ? "Y" : "n");
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("--- RATE LIMITERS DEMO (limit 3 per 1000 ms) ---");
        // 3 requests just before the 1000 ms boundary, 3 just after it
        long[] burst = {900, 950, 990, 1000, 1010, 1050};
        System.out.println("Requests at 900, 950, 990, 1000, 1010, 1050:");
        System.out.println("  Fixed window:    " + run(new FixedWindowCounter(3, 1000), burst)
                + "  <- 6 accepted within 150 ms (boundary burst)");
        System.out.println("  Sliding log:     " + run(new SlidingWindowLog(3, 1000), burst)
                + "  <- exact: never more than 3 in any 1000 ms");
        System.out.println("  Sliding counter: " + run(new SlidingWindowCounter(3, 1000), burst)
                + "  <- estimate 3 + 0 at 1000 ms, 2.97 at 1010 ms (approximation lets one through)");
        System.out.println("  Token bucket:    " + run(new TokenBucketLimiter(3, 3.0), burst)
                + "  <- burst of 3, then ~3 per second");

        System.out.println("\nToken bucket (capacity 3, 3/s), one request every 200 ms:");
        long[] steady = new long[12];
        for (int i = 0; i < steady.length; i++) steady[i] = i * 200L;
        System.out.println("  " + run(new TokenBucketLimiter(3, 3.0), steady)); // burst first, then ~3 of every 5

        KeyedRateLimiter perUser = new KeyedRateLimiter(() -> new SlidingWindowLog(2, 1000));
        System.out.println("\nPer-user limit 2/s: alice " + perUser.tryAcquire("alice", 0) + " " + perUser.tryAcquire("alice", 1)
                + " " + perUser.tryAcquire("alice", 2) + ", bob " + perUser.tryAcquire("bob", 2)); // true true false, bob true
    }
}
