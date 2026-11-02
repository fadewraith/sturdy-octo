package distributed.idgen;

import java.util.function.LongSupplier;

/**
 * SNOWFLAKE ID GENERATOR (Twitter Snowflake)
 *
 * WHAT IT IS:
 * Generates unique 64-bit IDs on MANY machines at once without talking to each
 * other, and the IDs are roughly sorted by creation time.
 *
 *   | 1 bit | 41 bits                         | 10 bits   | 12 bits  |
 *   |   0   | milliseconds since custom epoch | worker id | sequence |
 *
 * - 41 bits of milliseconds = ~69 years from the chosen epoch.
 * - 10 bits = up to 1024 workers (machines / processes), assigned at deploy time.
 * - 12 bits = up to 4096 IDs per worker per millisecond.
 * - The sign bit stays 0 so IDs are positive longs.
 *
 * WHEN TO USE THIS:
 * - Primary keys in sharded databases (Twitter, Discord, Instagram use variants):
 *   no central counter (single point of failure), and time-sorted IDs keep B-tree
 *   inserts at the "right end" of the index, unlike random UUIDv4.
 * - Compare: UUIDv4 (128-bit random, unsorted), UUIDv7 (time-ordered, 128-bit),
 *   database auto-increment (needs one central writer).
 *
 * STRATEGY:
 *   same millisecond as last ID -> sequence + 1; if it wraps past 4095, spin
 *                                  until the clock reaches the next millisecond
 *   new millisecond             -> sequence = 0
 *   clock went BACKWARDS        -> refuse (duplicates would be possible); real
 *                                  systems wait or alert (NTP can step the clock)
 * Uniqueness: (timestamp, worker, sequence) never repeats, as long as worker ids are
 * unique and the clock never goes backwards.
 *
 * COMPLEXITY:
 * nextId(): O(1) (except the rare spin-wait when 4096 IDs are used in one ms).
 */
public class SnowflakeIdGenerator {

    public static final long DEFAULT_EPOCH = 1288834974657L; // Twitter's epoch (Nov 2010)
    private static final int WORKER_BITS = 10, SEQUENCE_BITS = 12;
    public static final long MAX_WORKER = (1L << WORKER_BITS) - 1;    // 1023
    public static final long MAX_SEQUENCE = (1L << SEQUENCE_BITS) - 1; // 4095

    private final long epoch;
    private final long workerId;
    private final LongSupplier clock;
    private long lastTimestamp = -1;
    private long sequence = 0;

    public SnowflakeIdGenerator(long workerId) {
        this(workerId, DEFAULT_EPOCH, System::currentTimeMillis);
    }

    /** clock is injectable so tests can control time. */
    public SnowflakeIdGenerator(long workerId, long epoch, LongSupplier clock) {
        if (workerId < 0 || workerId > MAX_WORKER) throw new IllegalArgumentException("worker id must be 0.." + MAX_WORKER);
        this.workerId = workerId;
        this.epoch = epoch;
        this.clock = clock;
    }

    public synchronized long nextId() {
        long now = clock.getAsLong();
        if (now < lastTimestamp) {
            throw new IllegalStateException("clock moved backwards by " + (lastTimestamp - now) + " ms");
        }
        if (now == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) {              // 4096 IDs already used this millisecond
                while (now <= lastTimestamp) now = clock.getAsLong();
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = now;
        long millis = now - epoch;
        if (millis < 0 || millis >= (1L << 41)) throw new IllegalStateException("timestamp outside the 41-bit range");
        return (millis << (WORKER_BITS + SEQUENCE_BITS)) | (workerId << SEQUENCE_BITS) | sequence;
    }

    // ---- decoding helpers ----
    public static long timestampOf(long id, long epoch) { return (id >>> (WORKER_BITS + SEQUENCE_BITS)) + epoch; }
    public static long workerOf(long id) { return (id >>> SEQUENCE_BITS) & MAX_WORKER; }
    public static long sequenceOf(long id) { return id & MAX_SEQUENCE; }

    public static void main(String[] args) {
        System.out.println("--- SNOWFLAKE ID DEMO ---");
        SnowflakeIdGenerator gen = new SnowflakeIdGenerator(42);
        long prev = 0;
        boolean increasing = true;
        for (int i = 0; i < 100_000; i++) {
            long id = gen.nextId();
            if (id <= prev) increasing = false;
            prev = id;
        }
        System.out.println("100,000 ids generated, strictly increasing: " + increasing);
        System.out.println("Last id " + prev + " -> worker " + workerOf(prev) + ", sequence " + sequenceOf(prev)
                + ", created at " + java.time.Instant.ofEpochMilli(timestampOf(prev, DEFAULT_EPOCH)));
        System.out.println("As a short URL code: " + Base62.encode(prev));

        long[] fakeTime = {5_000};
        SnowflakeIdGenerator g2 = new SnowflakeIdGenerator(1, 0, () -> fakeTime[0]);
        g2.nextId();
        fakeTime[0] = 4_000; // NTP stepped the clock back
        try {
            g2.nextId();
        } catch (IllegalStateException e) {
            System.out.println("Clock went back: " + e.getMessage());
        }
    }
}
