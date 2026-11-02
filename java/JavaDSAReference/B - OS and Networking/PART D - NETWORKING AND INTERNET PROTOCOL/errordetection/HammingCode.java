package net.errordetection;

import java.util.Arrays;

/**
 * HAMMING CODE (SEC-DED: Single Error Correction, Double Error Detection)
 *
 * WHAT IT IS:
 * An error-CORRECTING code. Checksum/CRC (in this package) only DETECT errors;
 * Hamming code also finds WHICH bit flipped and fixes it. With one extra overall
 * parity bit (SEC-DED) it also detects (but can't fix) two flipped bits.
 *
 * WHEN TO USE THIS:
 * - ECC memory (server RAM uses SEC-DED Hamming codes on every 64-bit word).
 * - Cheap links and storage where retransmission is impossible or slow.
 *
 * STRATEGY (classic Hamming, positions numbered from 1):
 * - Positions that are powers of two (1, 2, 4, 8, ...) hold PARITY bits; every
 *   other position holds a data bit.
 * - Parity bit p covers every position whose number has bit p set
 *   (p=1 covers 1,3,5,7,...; p=2 covers 2,3,6,7,...; p=4 covers 4-7, 12-15, ...),
 *   and makes the XOR of its group 0.
 * - On receive, recompute each group's XOR. The failing parity bits, read as a
 *   binary number, are the SYNDROME = the position of the flipped bit. 0 = no error.
 * SEC-DED: add position 0 = XOR of the whole codeword.
 *   syndrome == 0, overall OK        -> no error
 *   overall parity WRONG             -> exactly one flip (at `syndrome`, or bit 0
 *                                       itself if syndrome == 0) -> fix it
 *   syndrome != 0, overall OK        -> two flips -> uncorrectable, report it
 *
 * SIZE: k data bits need r parity bits with 2^r >= k + r + 1.
 *   4 data -> 3 parity (Hamming(7,4)), 64 data -> 7 parity (+1 overall = 72 bits).
 *
 * COMPLEXITY:
 * Encode / decode: O(n log n) as written (each of log n parity bits scans the word);
 * O(n) with the "XOR the positions of all 1 bits" trick used in syndrome().
 */
public class HammingCode {

    public enum Status { OK, CORRECTED, DOUBLE_ERROR }

    public static class DecodeResult {
        public final int[] data;
        public final Status status;
        public final int errorPosition; // corrected position, or -1
        DecodeResult(int[] data, Status status, int errorPosition) {
            this.data = data; this.status = status; this.errorPosition = errorPosition;
        }
        @Override public String toString() {
            return status + (errorPosition >= 0 ? " (bit " + errorPosition + ")" : "") + " data=" + Arrays.toString(data);
        }
    }

    /** Number of parity bits (excluding the overall bit) for k data bits. */
    public static int parityBits(int k) {
        int r = 0;
        while ((1 << r) < k + r + 1) r++;
        return r;
    }

    private static boolean isPowerOfTwo(int x) { return x > 0 && (x & (x - 1)) == 0; }

    /** Encodes 0/1 data bits into a SEC-DED codeword: index 0 = overall parity, 1..n = Hamming positions. */
    public static int[] encode(int[] data) {
        int k = data.length, r = parityBits(k), n = k + r;
        int[] code = new int[n + 1];
        // 1. place data bits in the non-power-of-two positions
        for (int pos = 1, d = 0; pos <= n; pos++) {
            if (!isPowerOfTwo(pos)) code[pos] = data[d++] & 1;
        }
        // 2. each parity bit p makes the XOR over "positions with bit p set" zero
        for (int p = 1; p <= n; p <<= 1) {
            int x = 0;
            for (int pos = 1; pos <= n; pos++) if ((pos & p) != 0 && pos != p) x ^= code[pos];
            code[p] = x;
        }
        // 3. overall parity over positions 1..n
        int all = 0;
        for (int pos = 1; pos <= n; pos++) all ^= code[pos];
        code[0] = all;
        return code;
    }

    /** XOR of the positions (1..n) holding a 1 = the syndrome. */
    private static int syndrome(int[] code) {
        int s = 0;
        for (int pos = 1; pos < code.length; pos++) if (code[pos] == 1) s ^= pos;
        return s;
    }

    /** Decodes a (possibly corrupted) codeword back to k data bits. Does not modify the input. */
    public static DecodeResult decode(int[] received, int k) {
        int[] code = received.clone();
        int s = syndrome(code);
        int overall = 0;
        for (int b : code) overall ^= b;          // includes bit 0: should be 0

        Status status;
        int errorPos = -1;
        if (s == 0 && overall == 0) {
            status = Status.OK;
        } else if (overall == 1) {
            errorPos = s;                         // s == 0 means the overall bit itself flipped
            if (errorPos >= code.length) return new DecodeResult(extract(code, k), Status.DOUBLE_ERROR, -1); // impossible single error -> 3+ flips
            code[errorPos] ^= 1;
            status = Status.CORRECTED;
        } else {
            status = Status.DOUBLE_ERROR;         // syndrome says "error" but parity is even
        }
        return new DecodeResult(extract(code, k), status, errorPos);
    }

    private static int[] extract(int[] code, int k) {
        int[] data = new int[k];
        for (int pos = 1, d = 0; pos < code.length && d < k; pos++) {
            if (!isPowerOfTwo(pos)) data[d++] = code[pos];
        }
        return data;
    }

    public static void main(String[] args) {
        System.out.println("--- HAMMING CODE (SEC-DED) DEMO ---");
        int[] data = {1, 0, 1, 1};
        int[] code = encode(data);
        System.out.println("Data " + Arrays.toString(data) + " -> codeword " + Arrays.toString(code)
                + " (" + parityBits(4) + " parity + 1 overall bit)");

        int[] oneFlip = code.clone();
        oneFlip[5] ^= 1;
        System.out.println("Flip bit 5:      " + decode(oneFlip, 4)); // CORRECTED (bit 5), data restored

        int[] twoFlips = code.clone();
        twoFlips[3] ^= 1;
        twoFlips[6] ^= 1;
        System.out.println("Flip bits 3, 6:  " + decode(twoFlips, 4)); // DOUBLE_ERROR

        System.out.println("64 data bits need " + parityBits(64) + " parity bits + 1 overall = 72-bit ECC word");
    }
}
