package algorithms.dp;

import java.util.Arrays;

/**
 * DIGIT DP (counting numbers in a range by their digits)
 *
 * WHAT IT IS:
 * Answers questions like "how many numbers in [L, R] have digit sum 10?" or
 * "... have no two equal adjacent digits?" for R up to 10^18 - far too many numbers
 * to loop over - by building the numbers DIGIT BY DIGIT.
 *
 * WHEN TO USE THIS:
 * - Any "count / sum over x in [L, R] of some property of x's digits": digit sums,
 *   forbidden digits or patterns, divisibility by k, "lucky numbers", palindromic
 *   structure, numbers with exactly k non-zero digits. A competitive-programming
 *   staple and a nice example of DP over a combinatorial structure.
 *
 * STRATEGY:
 * Count over [0, N]; a range is f(R) - f(L - 1).
 * Walk N's digits from the most significant. State:
 *   pos     - which digit we are placing
 *   tight   - are all digits so far EQUAL to N's prefix? Then this digit can only go
 *             up to N's digit here; otherwise 0..9 are all allowed.
 *   started - have we placed a non-zero digit yet? (leading zeros aren't digits)
 *   + whatever the property needs (running digit sum, previous digit, remainder...)
 * Only NON-tight, started states repeat across branches, so only they are memoised:
 * memo[pos][extra] = number of ways to fill the remaining digits freely.
 * Tight states form a single path, so they need no memo.
 *
 * COMPLEXITY:
 * O(digits * extraStates * 10) per query: for N = 10^18 and digit sums up to 162,
 * about 19 * 163 * 10 = 31,000 steps instead of 10^18.
 */
public class DigitDP {

    private static int[] digitsOf(long n) {
        char[] c = Long.toString(n).toCharArray();
        int[] d = new int[c.length];
        for (int i = 0; i < c.length; i++) d[i] = c[i] - '0';
        return d;
    }

    // ---------------- 1. digit sum equals S ----------------
    /** How many x in [0, n] have digit sum exactly s. */
    public static long countDigitSum(long n, int s) {
        if (n < 0 || s < 0) return 0;
        int[] d = digitsOf(n);
        long[][] memo = new long[d.length + 1][s + 1];
        for (long[] row : memo) Arrays.fill(row, -1);
        return digitSum(d, 0, s, true, memo);
    }

    private static long digitSum(int[] d, int pos, int remaining, boolean tight, long[][] memo) {
        if (remaining < 0) return 0;
        if (pos == d.length) return remaining == 0 ? 1 : 0;
        if (!tight && memo[pos][remaining] >= 0) return memo[pos][remaining];
        int limit = tight ? d[pos] : 9;
        long count = 0;
        for (int digit = 0; digit <= limit; digit++) {
            count += digitSum(d, pos + 1, remaining - digit, tight && digit == limit, memo);
        }
        if (!tight) memo[pos][remaining] = count;  // leading zeros don't change a sum, so no "started" flag needed
        return count;
    }

    public static long countDigitSumInRange(long lo, long hi, int s) {
        return countDigitSum(hi, s) - countDigitSum(lo - 1, s);
    }

    // ---------------- 2. no two equal adjacent digits ----------------
    /** How many x in [0, n] have no two equal neighbouring digits (0 counts; 121 yes, 1223 no). */
    public static long countNoAdjacentEqual(long n) {
        if (n < 0) return 0;
        int[] d = digitsOf(n);
        long[][] memo = new long[d.length + 1][11];
        for (long[] row : memo) Arrays.fill(row, -1);
        return noAdjacent(d, 0, 10, true, memo);  // prev = 10 means "no digit placed yet"
    }

    private static long noAdjacent(int[] d, int pos, int prev, boolean tight, long[][] memo) {
        if (pos == d.length) return 1;           // the number 0 is the all-leading-zeros path
        if (!tight && memo[pos][prev] >= 0) return memo[pos][prev];
        int limit = tight ? d[pos] : 9;
        long count = 0;
        for (int digit = 0; digit <= limit; digit++) {
            boolean leadingZero = (prev == 10 && digit == 0);
            if (!leadingZero && digit == prev) continue;               // equal neighbours: forbidden
            count += noAdjacent(d, pos + 1, leadingZero ? 10 : digit, tight && digit == limit, memo);
        }
        if (!tight) memo[pos][prev] = count;
        return count;
    }

    // ---------------- 3. sum of all digit sums ----------------
    /**
     * Sum of the digit sums of every number 0..n (e.g. n = 12: 1+2+...+9+1+2+3 = 51).
     * The result grows like 4.5 * digits * n, so it overflows a long for n beyond ~10^17.
     */
    public static long totalDigitSum(long n) {
        if (n < 0) return 0;
        int[] d = digitsOf(n);
        long[][] memo = new long[d.length + 1][];
        return total(d, 0, true, memo)[1];
    }

    // returns {how many numbers, total of their digit sums} for the suffix starting at pos
    private static long[] total(int[] d, int pos, boolean tight, long[][] memo) {
        if (pos == d.length) return new long[]{1, 0};
        if (!tight && memo[pos] != null) return memo[pos];
        int limit = tight ? d[pos] : 9;
        long count = 0, sum = 0;
        for (int digit = 0; digit <= limit; digit++) {
            long[] sub = total(d, pos + 1, tight && digit == limit, memo);
            count += sub[0];
            sum += sub[1] + digit * sub[0];      // this digit appears in every number of the branch
        }
        long[] res = {count, sum};
        if (!tight) memo[pos] = res;
        return res;
    }

    public static void main(String[] args) {
        System.out.println("--- DIGIT DP DEMO ---");
        System.out.println("Numbers in [0, 100] with digit sum 10: " + countDigitSum(100, 10));   // 9 (19, 28, ..., 91)
        System.out.println("Numbers in [1, 10^18] with digit sum 100: " + countDigitSumInRange(1, 1_000_000_000_000_000_000L, 100));
        System.out.println("Numbers in [0, 100] with no equal neighbours: " + countNoAdjacentEqual(100)); // 101 - 9 (11..99) - 1 (100) = 91
        System.out.println("Total digit sum of 0..12: " + totalDigitSum(12));                     // 51
        System.out.println("Total digit sum of 0..10^15 - 1: " + totalDigitSum(999_999_999_999_999L)); // 15 digits * 45 * 10^14 = 67,500,000,000,000,000
    }
}
