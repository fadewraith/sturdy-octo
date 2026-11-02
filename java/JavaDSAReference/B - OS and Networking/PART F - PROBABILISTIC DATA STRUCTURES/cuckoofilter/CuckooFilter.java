package probabilistic.cuckoofilter;

import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * CUCKOO FILTER (Fan, Andersen, Kaminsky, Mitzenmacher 2014)
 *
 * WHAT IT IS:
 * A set-membership filter like a Bloom filter ("definitely not present" /
 * "probably present"), but it SUPPORTS DELETE and uses less space than a Bloom
 * filter for false-positive rates below ~3%.
 *
 * WHEN TO USE THIS:
 * - Same jobs as a Bloom filter (Part A BloomFilter.java): skip disk lookups for
 *   absent keys, dedupe URLs, cache admission - when items also need to be REMOVED.
 * - Used in network switches, Redis (CF.* commands), some databases.
 *
 * STRUCTURE:
 * An array of buckets, each holding 4 small FINGERPRINTS (here 8..16 bits of the
 * item's hash; 0 means "empty slot"). Only the fingerprint is stored, not the item.
 *
 * PARTIAL-KEY CUCKOO HASHING (the clever part):
 * Each item has two candidate buckets:
 *   i1 = hash(item)
 *   i2 = i1 XOR hash(fingerprint)
 * Because XOR is its own inverse, i1 = i2 XOR hash(fingerprint) too. So when a
 * fingerprint must be KICKED OUT of its bucket, its alternate bucket can be computed
 * from the fingerprint alone - the original item isn't needed. (Bucket count must
 * be a power of two so XOR stays in range.)
 *
 * OPERATIONS:
 * insert: put fp in i1 or i2 if either has a free slot. Otherwise evict a random
 *         fp from one of them, put ours there, and move the victim to ITS alternate
 *         bucket - repeating up to MAX_KICKS times. If that fails the table is
 *         full; the last homeless fingerprint is kept in a one-slot "victim" stash
 *         so no inserted item is ever lost (no false negatives).
 * contains: is fp in bucket i1 or i2 (or the stash)?
 * delete:   remove ONE copy of fp from i1 or i2. Only delete items that were
 *           inserted - deleting an absent item might remove another item's
 *           matching fingerprint.
 *
 * FALSE POSITIVE RATE ~ 2 * 4 / 2^f for f-bit fingerprints (8 bits -> ~3%,
 * 12 bits -> ~0.2%, 16 bits -> ~0.01%). Load factor up to ~95% with 4-slot buckets.
 *
 * COMPLEXITY:
 * contains / delete: O(1) - two buckets of 4 slots.
 * insert: O(1) expected, O(MAX_KICKS) worst case.
 * Space: about (f + 3) / loadFactor bits per item.
 */
public class CuckooFilter {

    private static final int SLOTS = 4;
    private static final int MAX_KICKS = 500;

    private final short[] table;      // numBuckets * SLOTS fingerprints, 0 = empty
    private final int bucketMask;
    private final int fingerprintBits;
    private final Random random;
    private int count = 0;
    private short victimFp = 0;       // stash for one fingerprint that found no home
    private int victimBucket;

    public CuckooFilter(int capacity, int fingerprintBits, long seed) {
        if (fingerprintBits < 4 || fingerprintBits > 16) throw new IllegalArgumentException("fingerprint bits must be 4..16");
        int buckets = Integer.highestOneBit(Math.max(1, (int) Math.ceil(capacity / (SLOTS * 0.95))));
        if (buckets * SLOTS * 0.95 < capacity) buckets <<= 1; // round UP to a power of two
        this.table = new short[buckets * SLOTS];
        this.bucketMask = buckets - 1;
        this.fingerprintBits = fingerprintBits;
        this.random = new Random(seed);
    }

    // 64-bit FNV-1a + murmur3 finalizer
    private static long hash64(byte[] data) {
        long h = 0xcbf29ce484222325L;
        for (byte b : data) { h ^= b & 0xff; h *= 0x100000001b3L; }
        return mix(h);
    }

    private static long mix(long h) {
        h ^= h >>> 33; h *= 0xff51afd7ed558ccdL;
        h ^= h >>> 33; h *= 0xc4ceb9fe1a85ec53L;
        h ^= h >>> 33;
        return h;
    }

    private short fingerprint(long h) {
        int fp = (int) ((h >>> 32) & ((1 << fingerprintBits) - 1));
        return (short) (fp == 0 ? 1 : fp); // 0 is reserved for "empty"
    }

    private int altBucket(int bucket, short fp) {
        return (int) ((bucket ^ mix(fp & 0xffff)) & bucketMask);
    }

    private boolean tryPut(int bucket, short fp) {
        int base = bucket * SLOTS;
        for (int s = 0; s < SLOTS; s++) {
            if (table[base + s] == 0) { table[base + s] = fp; return true; }
        }
        return false;
    }

    private boolean bucketHas(int bucket, short fp) {
        int base = bucket * SLOTS;
        for (int s = 0; s < SLOTS; s++) if (table[base + s] == fp) return true;
        return false;
    }

    private boolean removeFrom(int bucket, short fp) {
        int base = bucket * SLOTS;
        for (int s = 0; s < SLOTS; s++) {
            if (table[base + s] == fp) { table[base + s] = 0; return true; }
        }
        return false;
    }

    /**
     * Returns true if the item is now in the filter. When the kick chain gives up, the
     * item is still stored and one displaced fingerprint goes to the stash; after that
     * the filter is FULL and inserts that need kicking return false (item NOT stored).
     */
    public boolean insert(String item) {
        long h = hash64(item.getBytes(StandardCharsets.UTF_8));
        short fp = fingerprint(h);
        int i1 = (int) (h & bucketMask);
        int i2 = altBucket(i1, fp);
        if (tryPut(i1, fp) || tryPut(i2, fp)) { count++; return true; }
        if (victimFp != 0) return false; // full: the stash is already in use

        // both full: start kicking
        int bucket = random.nextBoolean() ? i1 : i2;
        for (int kick = 0; kick < MAX_KICKS; kick++) {
            int slot = bucket * SLOTS + random.nextInt(SLOTS);
            short evicted = table[slot];
            table[slot] = fp;                 // our fingerprint takes the slot
            fp = evicted;                     // and the evicted one needs a new home
            bucket = altBucket(bucket, fp);   // computed from the fingerprint alone
            if (tryPut(bucket, fp)) { count++; return true; }
        }
        victimFp = fp;                        // keep it so nothing is lost
        victimBucket = bucket;
        count++;
        return true;
    }

    public boolean mightContain(String item) {
        long h = hash64(item.getBytes(StandardCharsets.UTF_8));
        short fp = fingerprint(h);
        int i1 = (int) (h & bucketMask);
        int i2 = altBucket(i1, fp);
        if (bucketHas(i1, fp) || bucketHas(i2, fp)) return true;
        return victimFp == fp && (victimBucket == i1 || victimBucket == i2);
    }

    /** Removes one copy of a PREVIOUSLY INSERTED item. */
    public boolean delete(String item) {
        long h = hash64(item.getBytes(StandardCharsets.UTF_8));
        short fp = fingerprint(h);
        int i1 = (int) (h & bucketMask);
        int i2 = altBucket(i1, fp);
        if (removeFrom(i1, fp) || removeFrom(i2, fp)) {
            count--;
            if (victimFp != 0) {              // room was freed: try to re-home the stashed fingerprint
                short v = victimFp;
                victimFp = 0;
                count--;
                reinsertFingerprint(v, victimBucket);
            }
            return true;
        }
        if (victimFp == fp && (victimBucket == i1 || victimBucket == i2)) {
            victimFp = 0;
            count--;
            return true;
        }
        return false;
    }

    private void reinsertFingerprint(short fp, int bucket) {
        if (tryPut(bucket, fp) || tryPut(altBucket(bucket, fp), fp)) { count++; return; }
        for (int kick = 0; kick < MAX_KICKS; kick++) {
            int slot = bucket * SLOTS + random.nextInt(SLOTS);
            short evicted = table[slot];
            table[slot] = fp;
            fp = evicted;
            bucket = altBucket(bucket, fp);
            if (tryPut(bucket, fp)) { count++; return; }
        }
        victimFp = fp;
        victimBucket = bucket;
        count++;
    }

    public int size() { return count; }
    public int capacitySlots() { return table.length; }
    public double loadFactor() { return (double) count / table.length; }

    public static void main(String[] args) {
        System.out.println("--- CUCKOO FILTER DEMO ---");
        CuckooFilter filter = new CuckooFilter(100_000, 12, 1);
        int inserted = 0;
        for (int i = 0; i < 100_000; i++) if (filter.insert("user-" + i)) inserted++;
        System.out.printf("Inserted %d items, load factor %.1f%%%n", inserted, 100 * filter.loadFactor());

        int falseNegatives = 0;
        for (int i = 0; i < 100_000; i++) if (!filter.mightContain("user-" + i)) falseNegatives++;
        int falsePositives = 0, probes = 100_000;
        for (int i = 0; i < probes; i++) if (filter.mightContain("stranger-" + i)) falsePositives++;
        System.out.println("False negatives: " + falseNegatives);                           // 0
        System.out.printf("False positive rate: %.3f%% (theory ~ 8 / 2^12 = 0.2%%)%n", 100.0 * falsePositives / probes);

        for (int i = 0; i < 50_000; i++) filter.delete("user-" + i);
        int stillThere = 0;
        for (int i = 50_000; i < 100_000; i++) if (filter.mightContain("user-" + i)) stillThere++;
        System.out.println("After deleting half: remaining items still found " + stillThere + " / 50000"); // 50000
    }
}
