package compression.arithmetic;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

/**
 * ARITHMETIC CODING (integer implementation, static frequency model)
 *
 * WHAT IT IS:
 * Encodes a whole message as ONE number in [0, 1). Start with the interval [0, 1);
 * for each symbol, shrink the interval to that symbol's slice, sized by its
 * probability. Any number inside the final interval identifies the message.
 * More likely symbols shrink the interval less -> cost fewer bits.
 *
 * WHY IT BEATS HUFFMAN:
 * Huffman (HuffmanCoding.java) must spend a WHOLE number of bits per symbol, so a
 * symbol with probability 0.99 still costs 1 bit (ideal: 0.0145 bits). Arithmetic
 * coding spends fractional bits and gets within a few bits of the ENTROPY limit
 *   H = - sum p_i log2 p_i   bits per symbol, for the whole message.
 * Used in JPEG (optional), H.264/H.265 video (CABAC), zstd/LZMA-style range
 * coders, and modern neural compressors.
 *
 * PRACTICAL VERSION (Witten, Neal & Cleary 1987):
 * Real-number intervals would need unbounded precision, so we use 32-bit integers
 * [low, high] and RENORMALISE as we go:
 *   - both in the lower half  -> the next output bit must be 0; emit it, double
 *   - both in the upper half  -> emit 1, subtract half, double
 *   - straddling the middle in [1/4, 3/4) -> we can't decide yet: remember a
 *     PENDING bit (opposite of the next decided bit), subtract 1/4, double
 *     (the "underflow" / E3 case)
 * The decoder mirrors the encoder exactly, reading bits into a `value` register.
 * The model here is STATIC (counts of each byte, sent along with the data);
 * ADAPTIVE models update counts as they go and need no header.
 *
 * COMPLEXITY:
 * Encode/decode: O(n * 256) here (linear search for the symbol's slice); O(n log 256)
 * with a cumulative-frequency tree. Output: within ~2 bits of n * H.
 */
public class ArithmeticCoding {

    private static final int STATE_BITS = 32;
    private static final long FULL = 1L << STATE_BITS, HALF = FULL >>> 1, QUARTER = HALF >>> 1;
    private static final long MASK = FULL - 1;
    private static final int MAX_TOTAL = 1 << 30;   // keeps every slice at least 1 wide

    public static final class Encoded {
        public final byte[] bits;
        public final long bitCount;
        public final int[] freq;       // the static model (would be stored in a header)
        public final int length;       // number of symbols
        Encoded(byte[] bits, long bitCount, int[] freq, int length) { this.bits = bits; this.bitCount = bitCount; this.freq = freq; this.length = length; }
    }

    /** Byte counts (every present byte >= 1), scaled down if the total is too large. */
    public static int[] frequencies(byte[] data) {
        long[] counts = new long[256];
        for (byte b : data) counts[b & 0xff]++;
        int[] freq = new int[256];
        long total = data.length;
        for (int i = 0; i < 256; i++) {
            if (counts[i] == 0) continue;
            freq[i] = (int) Math.max(1, total > MAX_TOTAL / 2 ? counts[i] * (MAX_TOTAL / 2) / total : counts[i]);
        }
        return freq;
    }

    private static long[] cumulative(int[] freq) {
        long[] cum = new long[257];
        for (int i = 0; i < 256; i++) cum[i + 1] = cum[i] + freq[i];
        if (cum[256] > MAX_TOTAL) throw new IllegalArgumentException("frequency total too large");
        return cum;
    }

    private static final class BitWriter {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        int current = 0, filled = 0;
        long count = 0;
        void write(int bit) {
            current = (current << 1) | bit;
            count++;
            if (++filled == 8) { out.write(current); current = 0; filled = 0; }
        }
        byte[] finish() {
            if (filled > 0) out.write(current << (8 - filled));
            return out.toByteArray();
        }
    }

    public static Encoded encode(byte[] data) {
        int[] freq = frequencies(data);
        long[] cum = cumulative(freq);
        long total = cum[256];
        long low = 0, high = MASK;
        int pending = 0;
        BitWriter w = new BitWriter();
        for (byte b : data) {
            int s = b & 0xff;
            long range = high - low + 1;
            high = low + range * cum[s + 1] / total - 1;   // narrow to the symbol's slice
            low = low + range * cum[s] / total;
            while (true) {
                if (high < HALF) {                          // both in lower half: bit 0
                    w.write(0);
                    for (; pending > 0; pending--) w.write(1);
                } else if (low >= HALF) {                   // both in upper half: bit 1
                    w.write(1);
                    for (; pending > 0; pending--) w.write(0);
                    low -= HALF;
                    high -= HALF;
                } else if (low >= QUARTER && high < HALF + QUARTER) { // straddling the middle
                    pending++;
                    low -= QUARTER;
                    high -= QUARTER;
                } else {
                    break;
                }
                low <<= 1;
                high = (high << 1) | 1;
            }
        }
        // flush: two more bits pin down a number inside the final interval
        pending++;
        if (low < QUARTER) { w.write(0); for (; pending > 0; pending--) w.write(1); }
        else { w.write(1); for (; pending > 0; pending--) w.write(0); }
        long bits = w.count;
        return new Encoded(w.finish(), bits, freq, data.length);
    }

    public static byte[] decode(Encoded e) {
        long[] cum = cumulative(e.freq);
        long total = cum[256];
        byte[] out = new byte[e.length];
        long low = 0, high = MASK, value = 0;
        long[] bitPos = {0};
        for (int i = 0; i < STATE_BITS; i++) value = (value << 1) | readBit(e, bitPos);
        for (int k = 0; k < e.length; k++) {
            long range = high - low + 1;
            long scaled = ((value - low + 1) * total - 1) / range;  // where value falls in [0, total)
            int s = 0;
            while (cum[s + 1] <= scaled) s++;                       // the slice containing it
            out[k] = (byte) s;
            high = low + range * cum[s + 1] / total - 1;
            low = low + range * cum[s] / total;
            while (true) {
                if (high < HALF) {
                    // nothing to subtract
                } else if (low >= HALF) {
                    low -= HALF; high -= HALF; value -= HALF;
                } else if (low >= QUARTER && high < HALF + QUARTER) {
                    low -= QUARTER; high -= QUARTER; value -= QUARTER;
                } else {
                    break;
                }
                low <<= 1;
                high = (high << 1) | 1;
                value = (value << 1) | readBit(e, bitPos);
            }
        }
        return out;
    }

    private static int readBit(Encoded e, long[] pos) {
        long p = pos[0]++;
        if (p >= (long) e.bits.length * 8) return 0;                 // past the end: zeros
        return (e.bits[(int) (p >>> 3)] >> (7 - (int) (p & 7))) & 1;
    }

    /** Shannon entropy of the byte distribution, in bits per byte. */
    public static double entropy(byte[] data) {
        int[] c = new int[256];
        for (byte b : data) c[b & 0xff]++;
        double h = 0;
        for (int x : c) if (x > 0) { double p = (double) x / data.length; h -= p * Math.log(p) / Math.log(2); }
        return h;
    }

    public static void main(String[] args) {
        System.out.println("--- ARITHMETIC CODING DEMO ---");
        String[] samples = {
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab".repeat(20),   // very skewed: Huffman's worst case
            "the quick brown fox jumps over the lazy dog. ".repeat(20),
        };
        for (String s : samples) {
            byte[] data = s.getBytes(StandardCharsets.UTF_8);
            Encoded e = encode(data);
            double ideal = entropy(data) * data.length;
            boolean ok = java.util.Arrays.equals(data, decode(e));
            System.out.printf("%d bytes: entropy bound %.1f bits, arithmetic coded %d bits (Huffman needs >= %d), round trip %b%n",
                    data.length, ideal, e.bitCount, data.length, ok);
        }
        // Skewed input: ~141 bits instead of Huffman's >= 1000 (1 bit per symbol minimum)
    }
}
