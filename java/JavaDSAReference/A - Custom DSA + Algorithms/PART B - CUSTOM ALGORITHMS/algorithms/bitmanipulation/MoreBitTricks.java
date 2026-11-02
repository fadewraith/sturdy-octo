package algorithms.bitmanipulation;

/**
 * MORE BIT MANIPULATION TRICKS
 *
 * 1. IS POWER OF TWO
 * Problem: Check if a given positive integer is a power of 2.
 * Trick: A power of 2 in binary ALWAYS has exactly one '1' bit (e.g., 2 is 10,
 * 4 is 100, 8 is 1000). If you subtract 1, all bits invert (e.g., 8 is 1000, 7 is 0111).
 * If you do `n & (n - 1)`, it will evaluate to 0 ONLY if it was a power of 2!
 * Power of FOUR: a power of two whose single bit sits at an even position
 * (mask 0x55555555 = binary 0101...0101).
 *
 * 2. SWAP WITHOUT TEMP VARIABLE (XOR SWAP)
 * Problem: Swap two integers without allocating a `temp` variable.
 * Trick: XOR logic! A XOR A = 0. A XOR 0 = A.
 * a = a ^ b
 * b = a ^ b  (This evaluates to: (a ^ b) ^ b = a ^ (b ^ b) = a ^ 0 = a)
 * a = a ^ b  (This evaluates to: (a ^ b) ^ a = b ^ (a ^ a) = b ^ 0 = b)
 *
 * 3. SINGLE-BIT OPERATIONS (bit i, counting from 0 = least significant)
 *    get:    (n >> i) & 1        set:    n | (1 << i)
 *    clear:  n & ~(1 << i)       toggle: n ^ (1 << i)
 *    lowest set bit:       n & -n        (two's complement: -n = ~n + 1)
 *    clear lowest set bit: n & (n - 1)   (Brian Kernighan, see BitTricks)
 *
 * 4. COUNTING BITS FOR 0..N (DP over bits)
 *    bits[i] = bits[i >> 1] + (i & 1): i has the same 1s as i/2, plus its last bit.
 *
 * 5. REVERSE BITS: shift the lowest bit of n into the result 32 times.
 *
 * 6. SINGLE NUMBER II (every number appears 3 times except one)
 *    Count how many numbers have each bit set; for every bit, count % 3 is that bit
 *    of the answer (the triples contribute multiples of 3).
 *
 * 7. SINGLE NUMBER III (every number appears twice except TWO numbers a and b)
 *    XOR all -> x = a ^ b. Any set bit of x is a bit where a and b differ, so split
 *    all numbers into two groups by that bit; each group XORs down to one answer.
 *
 * 8. MISSING NUMBER (0..n with one missing): XOR all indices 0..n with all values;
 *    every present number cancels, the missing one is left. No overflow, unlike sums.
 *
 * 9. GRAY CODE: g = i ^ (i >> 1). Consecutive codes differ in exactly ONE bit
 *    (used in rotary encoders and Karnaugh maps). Decoding XORs all shifted prefixes.
 *
 * 10. ADD WITHOUT '+': a ^ b is the sum without carries; (a & b) << 1 are the carries.
 *     Repeat until there is no carry. Works for negatives too (two's complement).
 *
 * 11. GOSPER'S HACK: the next larger number with the SAME number of 1 bits.
 *     Enumerates all k-element subsets of n items in increasing order:
 *       c = x & -x;  r = x + c;  next = (((r ^ x) >>> 2) / c) | r
 *
 * 12. SUBMASK ITERATION: for (s = mask; s > 0; s = (s - 1) & mask) visits every
 *     non-empty submask of `mask` (then handle s = 0 separately). Over all masks
 *     this totals 3^n steps - the basis of many bitmask DPs.
 *
 * COMPLEXITY:
 * Time: O(1) for single-bit ops, swap, power checks, Gray code, Gosper.
 *       O(N) for counting bits / single-number problems / missing number.
 *       O(32) for reverse bits and addition. O(2^k) for submasks of a k-bit mask.
 * Space: O(1), except counting bits which returns an O(N) array.
 */
public class MoreBitTricks {

    public static boolean isPowerOfTwo(int n) {
        // Must be positive, and the bitwise trick must evaluate to 0
        return n > 0 && (n & (n - 1)) == 0;
    }

    public static boolean isPowerOfFour(int n) {
        return isPowerOfTwo(n) && (n & 0x55555555) != 0;
    }

    public static void swapXOR() {
        int a = 15;
        int b = 42;

        System.out.println("Before Swap: a = " + a + ", b = " + b);

        a = a ^ b;
        b = a ^ b;
        a = a ^ b;

        System.out.println("After Swap:  a = " + a + ", b = " + b);
    }

    // --- single-bit operations ---
    public static int getBit(int n, int i)    { return (n >> i) & 1; }
    public static int setBit(int n, int i)    { return n | (1 << i); }
    public static int clearBit(int n, int i)  { return n & ~(1 << i); }
    public static int toggleBit(int n, int i) { return n ^ (1 << i); }
    public static int lowestSetBit(int n)     { return n & -n; }
    public static int clearLowestSetBit(int n) { return n & (n - 1); }

    /** bits[i] = number of 1 bits in i, for every i in 0..n. O(N) */
    public static int[] countBits(int n) {
        int[] bits = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            bits[i] = bits[i >> 1] + (i & 1);
        }
        return bits;
    }

    /** Reverse the 32 bits of n (bit 0 becomes bit 31). */
    public static int reverseBits(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            result = (result << 1) | (n & 1);
            n >>>= 1; // unsigned shift so negative numbers don't drag in 1s
        }
        return result;
    }

    /** Every number appears three times except one. O(32 N) time, O(1) space. */
    public static int singleNumberII(int[] nums) {
        int result = 0;
        for (int bit = 0; bit < 32; bit++) {
            int count = 0;
            for (int num : nums) count += (num >> bit) & 1;
            if (count % 3 != 0) result |= 1 << bit;
        }
        return result;
    }

    /** Every number appears twice except two. Returns them smaller first. O(N) */
    public static int[] singleNumberIII(int[] nums) {
        int xor = 0;
        for (int num : nums) xor ^= num;      // = a ^ b
        int diffBit = xor & -xor;             // a bit where a and b differ
        int a = 0, b = 0;
        for (int num : nums) {
            if ((num & diffBit) == 0) a ^= num;
            else b ^= num;
        }
        return a < b ? new int[]{a, b} : new int[]{b, a};
    }

    /** nums holds n distinct numbers from 0..n; return the missing one. O(N) */
    public static int missingNumber(int[] nums) {
        int result = nums.length; // index n is never visited in the loop
        for (int i = 0; i < nums.length; i++) {
            result ^= i ^ nums[i];
        }
        return result;
    }

    public static int toGray(int i) {
        return i ^ (i >>> 1);
    }

    public static int fromGray(int g) {
        int n = g;
        for (int shift = 1; shift < 32; shift <<= 1) n ^= n >>> shift;
        return n;
    }

    /** Add two ints using only bit operations (same overflow behaviour as +). */
    public static int addWithoutPlus(int a, int b) {
        while (b != 0) {
            int carry = (a & b) << 1;
            a = a ^ b;
            b = carry;
        }
        return a;
    }

    /** Next larger int with the same number of 1 bits (x > 0). Gosper's hack. */
    public static int nextSameBitCount(int x) {
        int c = x & -x;
        int r = x + c;
        return (((r ^ x) >>> 2) / c) | r;
    }

    /** All submasks of mask, from mask down to 0. */
    public static int[] submasks(int mask) {
        int[] result = new int[1 << Integer.bitCount(mask)];
        int count = 0;
        for (int s = mask; s > 0; s = (s - 1) & mask) result[count++] = s;
        result[count] = 0; // the empty set
        return result;
    }

    public static void main(String[] args) {
        System.out.println("--- MORE BIT MANIPULATION DEMO ---");

        System.out.println("Is 16 a power of 2? " + isPowerOfTwo(16)); // true
        System.out.println("Is 18 a power of 2? " + isPowerOfTwo(18)); // false
        System.out.println("Is 16 a power of 4? " + isPowerOfFour(16)); // true
        System.out.println("Is 8 a power of 4?  " + isPowerOfFour(8));  // false

        System.out.println();
        swapXOR();

        int n = 0b101100; // 44
        System.out.println("\nn = 44 = " + Integer.toBinaryString(n));
        System.out.println("bit 2 = " + getBit(n, 2) + ", set bit 0 -> " + Integer.toBinaryString(setBit(n, 0))
                + ", clear bit 3 -> " + Integer.toBinaryString(clearBit(n, 3))
                + ", toggle bit 1 -> " + Integer.toBinaryString(toggleBit(n, 1)));
        System.out.println("lowest set bit = " + Integer.toBinaryString(lowestSetBit(n))       // 100
                + ", cleared -> " + Integer.toBinaryString(clearLowestSetBit(n)));         // 101000

        System.out.println("\ncountBits(8) = " + java.util.Arrays.toString(countBits(8))); // [0,1,1,2,1,2,2,3,1]
        System.out.println("reverseBits(1) = " + Integer.toBinaryString(reverseBits(1)));  // 1 followed by 31 zeros

        System.out.println("\nSingle Number II  [2,2,3,2]         -> " + singleNumberII(new int[]{2, 2, 3, 2})); // 3
        System.out.println("Single Number III [1,2,1,3,2,5]     -> "
                + java.util.Arrays.toString(singleNumberIII(new int[]{1, 2, 1, 3, 2, 5}))); // [3, 5]
        System.out.println("Missing number    [3,0,1]           -> " + missingNumber(new int[]{3, 0, 1})); // 2

        System.out.print("\nGray code 0..7: ");
        for (int i = 0; i < 8; i++) System.out.print(Integer.toBinaryString(toGray(i)) + " "); // 0 1 11 10 110 111 101 100
        System.out.println("\nfromGray(toGray(5)) = " + fromGray(toGray(5))); // 5

        System.out.println("\naddWithoutPlus(17, -25) = " + addWithoutPlus(17, -25)); // -8

        System.out.print("All 2-element subsets of 4 items (Gosper): ");
        for (int x = 0b0011; x < (1 << 4); x = nextSameBitCount(x)) System.out.print(Integer.toBinaryString(x) + " ");
        // 11 101 110 1001 1010 1100
        System.out.print("\nSubmasks of 1011: ");
        for (int s : submasks(0b1011)) System.out.print(Integer.toBinaryString(s) + " ");
        // 1011 1010 1001 1000 11 10 1 0
        System.out.println();
    }
}
