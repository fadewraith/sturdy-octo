package bitset;

/**
 * BIT SET
 *
 * What it is:
 * A set of non-negative integers stored as ONE BIT each: bit i is 1 if i is in the set.
 * 64 numbers fit in one long, so a set of numbers below 1,000,000 needs only ~122 KB
 * (a boolean[] would need 1 MB; a HashSet<Integer> far more). Same idea as java.util.BitSet.
 *
 * Approach/Strategy:
 * long[] words; number i lives in words[i / 64] at bit position i % 64:
 *   word index = i >>> 6        (divide by 64)
 *   bit mask   = 1L << (i & 63) (i mod 64; Java's << on long already uses only the low 6 bits)
 *   set:   words[w] |=  mask       clear: words[w] &= ~mask
 *   flip:  words[w] ^=  mask       get:   (words[w] & mask) != 0
 * Whole-set operations (and / or / xor / andNot) work on 64 bits per instruction.
 * The array grows automatically when you set a bit beyond the current size.
 *
 * Time/Space Complexity (W = number of 64-bit words = maxBit / 64):
 * Operation                       | Time
 * --------------------------------------------------------
 * set / clear / flip / get        | O(1)
 * cardinality (count of 1s)       | O(W) with hardware popcount (Long.bitCount)
 * and / or / xor / andNot         | O(W)
 * nextSetBit / nextClearBit       | O(W) worst case, usually much less
 * Space                           | maxBit / 8 bytes
 *
 * Used for: Sieve of Eratosthenes, Bloom filters, visited-flags in huge graphs, database
 * bitmap indexes, permission flags, "which seats are taken".
 */
public class MyBitSet implements Iterable<Integer> {

    private long[] words;

    public MyBitSet() {
        this(64);
    }

    public MyBitSet(int initialBits) {
        if (initialBits < 0) throw new IllegalArgumentException("initialBits must be >= 0");
        words = new long[Math.max(1, (initialBits + 63) >>> 6)];
    }

    private static void checkIndex(int i) {
        if (i < 0) throw new IndexOutOfBoundsException("bit index must be >= 0: " + i);
    }

    private void ensureWord(int wordIndex) {
        if (wordIndex >= words.length) {
            long[] bigger = new long[Math.max(words.length * 2, wordIndex + 1)];
            System.arraycopy(words, 0, bigger, 0, words.length);
            words = bigger;
        }
    }

    // ---------------- single bits ----------------

    public void set(int i) {
        checkIndex(i);
        ensureWord(i >>> 6);
        words[i >>> 6] |= 1L << i;
    }

    public void set(int i, boolean value) {
        if (value) set(i);
        else clear(i);
    }

    /** Sets every bit in [from, to). */
    public void set(int from, int to) {
        checkIndex(from);
        if (to < from) throw new IllegalArgumentException("to < from");
        for (int i = from; i < to; i++) set(i); // simple; a word-at-a-time version is a nice exercise
    }

    public void clear(int i) {
        checkIndex(i);
        if ((i >>> 6) < words.length) words[i >>> 6] &= ~(1L << i);
    }

    public void flip(int i) {
        checkIndex(i);
        ensureWord(i >>> 6);
        words[i >>> 6] ^= 1L << i;
    }

    public boolean get(int i) {
        checkIndex(i);
        int w = i >>> 6;
        return w < words.length && (words[w] & (1L << i)) != 0;
    }

    public void clear() {
        for (int w = 0; w < words.length; w++) words[w] = 0;
    }

    // ---------------- whole-set queries ----------------

    /** Number of 1 bits. Long.bitCount is one CPU instruction (POPCNT) on modern hardware. */
    public int cardinality() {
        int count = 0;
        for (long w : words) count += Long.bitCount(w);
        return count;
    }

    public boolean isEmpty() {
        for (long w : words) if (w != 0) return false;
        return true;
    }

    /** Index of the highest set bit + 1 (0 if empty) - same meaning as java.util.BitSet.length(). */
    public int length() {
        for (int w = words.length - 1; w >= 0; w--) {
            if (words[w] != 0) return w * 64 + (64 - Long.numberOfLeadingZeros(words[w]));
        }
        return 0;
    }

    /**
     * Smallest set bit >= from, or -1. Skips whole zero words, and inside a word uses
     * numberOfTrailingZeros to jump straight to the lowest 1.
     */
    public int nextSetBit(int from) {
        checkIndex(from);
        int w = from >>> 6;
        if (w >= words.length) return -1;
        long word = words[w] & (-1L << from); // ignore bits below 'from' in the first word
        while (true) {
            if (word != 0) return w * 64 + Long.numberOfTrailingZeros(word);
            if (++w == words.length) return -1;
            word = words[w];
        }
    }

    /** Smallest clear bit >= from (bits beyond the array are clear). */
    public int nextClearBit(int from) {
        checkIndex(from);
        int w = from >>> 6;
        if (w >= words.length) return from;
        long word = ~words[w] & (-1L << from);
        while (true) {
            if (word != 0) return w * 64 + Long.numberOfTrailingZeros(word);
            if (++w == words.length) return w * 64;
            word = ~words[w];
        }
    }

    // ---------------- set algebra (modify THIS set) ----------------

    /** this = this AND other (intersection). */
    public void and(MyBitSet other) {
        for (int w = 0; w < words.length; w++) words[w] &= w < other.words.length ? other.words[w] : 0;
    }

    /** this = this OR other (union). */
    public void or(MyBitSet other) {
        ensureWord(other.words.length - 1);
        for (int w = 0; w < other.words.length; w++) words[w] |= other.words[w];
    }

    /** this = this XOR other (symmetric difference). */
    public void xor(MyBitSet other) {
        ensureWord(other.words.length - 1);
        for (int w = 0; w < other.words.length; w++) words[w] ^= other.words[w];
    }

    /** this = this AND NOT other (difference). */
    public void andNot(MyBitSet other) {
        int n = Math.min(words.length, other.words.length);
        for (int w = 0; w < n; w++) words[w] &= ~other.words[w];
    }

    public MyBitSet copy() {
        MyBitSet c = new MyBitSet(0);
        c.words = words.clone();
        return c;
    }

    /** Set bits in increasing order: for (int i : bits) ... */
    @Override
    public java.util.Iterator<Integer> iterator() {
        return new java.util.Iterator<Integer>() {
            private int next = nextSetBit(0);

            @Override
            public boolean hasNext() {
                return next >= 0;
            }

            @Override
            public Integer next() {
                if (next < 0) throw new java.util.NoSuchElementException();
                int current = next;
                next = current == Integer.MAX_VALUE ? -1 : nextSetBit(current + 1);
                return current;
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MyBitSet)) return false;
        MyBitSet other = (MyBitSet) o;
        int n = Math.max(words.length, other.words.length);
        for (int w = 0; w < n; w++) {
            long a = w < words.length ? words[w] : 0, b = w < other.words.length ? other.words[w] : 0;
            if (a != b) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        long h = 1234; // ignore trailing zero words so equal sets hash equally
        for (int w = words.length - 1; w >= 0; w--) h ^= words[w] * (w + 1);
        return (int) ((h >> 32) ^ h);
    }

    /** Same format as java.util.BitSet: {1, 5, 64} */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (int i = nextSetBit(0); i >= 0; i = nextSetBit(i + 1)) {
            if (sb.length() > 1) sb.append(", ");
            sb.append(i);
        }
        return sb.append("}").toString();
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- BIT SET DEMO ---");
        // Sieve of Eratosthenes on a bitset: bit i set = i is composite
        int limit = 100;
        MyBitSet composite = new MyBitSet(limit + 1);
        for (int p = 2; p * p <= limit; p++) {
            if (!composite.get(p)) {
                for (int m = p * p; m <= limit; m += p) composite.set(m);
            }
        }
        StringBuilder primes = new StringBuilder();
        for (int i = composite.nextClearBit(2); i <= limit; i = composite.nextClearBit(i + 1)) primes.append(i).append(' ');
        System.out.println("Primes up to 100: " + primes);

        MyBitSet a = new MyBitSet(), b = new MyBitSet();
        for (int i : new int[]{1, 3, 5, 64, 200}) a.set(i);
        for (int i : new int[]{3, 4, 5, 200, 1000}) b.set(i);
        MyBitSet and = a.copy(); and.and(b);
        MyBitSet or = a.copy(); or.or(b);
        MyBitSet xor = a.copy(); xor.xor(b);
        System.out.println("A = " + a + ", B = " + b);
        System.out.println("A AND B = " + and + ", A OR B = " + or + ", A XOR B = " + xor);
        System.out.println("|A OR B| = " + or.cardinality() + ", length = " + or.length());
    }
}
