package algorithms.patterns.array;

import java.util.Arrays;

/**
 * NEXT PERMUTATION (+ Previous Permutation, K-th Permutation)
 *
 * WHAT IT IS:
 * Rearrange numbers into the next permutation in LEXICOGRAPHIC (dictionary) order,
 * in place. [1, 2, 3] -> [1, 3, 2] -> [2, 1, 3] -> ... -> [3, 2, 1] -> wraps to [1, 2, 3].
 * This is what C++ std::next_permutation does. Works with duplicates too.
 *
 * WHEN TO USE THIS:
 * - Enumerating permutations in order without recursion (and without duplicates,
 *   even when values repeat).
 * - "Next greater number with the same digits".
 *
 * STRATEGY (next permutation):
 * 1. From the right, find the first index i with a[i] < a[i+1]. Everything after i
 *    is non-increasing - already the LAST arrangement of that suffix.
 *    If there is no such i, the whole array is the last permutation: reverse it to
 *    get the first one and report the wrap-around.
 * 2. From the right, find the first j with a[j] > a[i] (the smallest bigger value
 *    in the suffix) and swap a[i] and a[j]. The suffix is still non-increasing.
 * 3. Reverse the suffix after i to make it the smallest (increasing) arrangement.
 * Previous permutation is the mirror image (swap < and >).
 *
 * K-TH PERMUTATION (of 1..n, k is 1-based) - factorial number system:
 * There are (n-1)! permutations starting with each first digit, so the first digit
 * is the ((k-1) / (n-1)!)-th unused number. Remove it, k = (k-1) % (n-1)! + 1, repeat.
 *
 * COMPLEXITY:
 * Next / previous: Time O(N), Space O(1).
 * K-th permutation: Time O(N^2) (removing from an array), Space O(N).
 */
public class NextPermutation {

    /** Rearranges a into its next permutation. Returns false if it wrapped to the first one. */
    public static boolean nextPermutation(int[] a) {
        int i = a.length - 2;
        while (i >= 0 && a[i] >= a[i + 1]) i--;       // 1. find the pivot
        if (i < 0) {
            reverse(a, 0, a.length - 1);               // last permutation -> first one
            return false;
        }
        int j = a.length - 1;
        while (a[j] <= a[i]) j--;                      // 2. smallest bigger value on the right
        swap(a, i, j);
        reverse(a, i + 1, a.length - 1);               // 3. smallest suffix
        return true;
    }

    /** Rearranges a into its previous permutation. Returns false if it wrapped to the last one. */
    public static boolean previousPermutation(int[] a) {
        int i = a.length - 2;
        while (i >= 0 && a[i] <= a[i + 1]) i--;
        if (i < 0) {
            reverse(a, 0, a.length - 1);
            return false;
        }
        int j = a.length - 1;
        while (a[j] >= a[i]) j--;
        swap(a, i, j);
        reverse(a, i + 1, a.length - 1);
        return true;
    }

    /** The k-th (1-based) permutation of 1..n in lexicographic order. */
    public static int[] kthPermutation(int n, long k) {
        long[] fact = new long[n + 1];
        fact[0] = 1;
        for (int i = 1; i <= n; i++) fact[i] = fact[i - 1] * i;
        if (n > 20) throw new IllegalArgumentException("n! overflows a long for n > 20");
        if (k < 1 || k > fact[n]) throw new IllegalArgumentException("k out of range");

        int[] unused = new int[n];
        for (int i = 0; i < n; i++) unused[i] = i + 1;
        int remaining = n;
        int[] result = new int[n];
        k--; // 0-based from here on
        for (int pos = 0; pos < n; pos++) {
            long block = fact[n - 1 - pos];         // permutations per choice of this position
            int idx = (int) (k / block);
            k %= block;
            result[pos] = unused[idx];
            for (int t = idx; t < remaining - 1; t++) unused[t] = unused[t + 1]; // remove it
            remaining--;
        }
        return result;
    }

    private static void swap(int[] a, int i, int j) { int t = a[i]; a[i] = a[j]; a[j] = t; }

    private static void reverse(int[] a, int lo, int hi) {
        while (lo < hi) swap(a, lo++, hi--);
    }

    public static void main(String[] args) {
        System.out.println("--- NEXT PERMUTATION DEMO ---");
        int[] a = {1, 2, 3};
        System.out.print("All permutations of [1, 2, 3] in order: " + Arrays.toString(a));
        while (nextPermutation(a)) System.out.print(" " + Arrays.toString(a));
        System.out.println();

        int[] dups = {1, 1, 2};
        System.out.print("With duplicates [1, 1, 2]: " + Arrays.toString(dups));
        while (nextPermutation(dups)) System.out.print(" " + Arrays.toString(dups));
        System.out.println(); // only 3, no repeats

        int[] b = {1, 5, 8, 4, 7, 6, 5, 3, 1};
        nextPermutation(b);
        System.out.println("Next after 158476531: " + Arrays.toString(b)); // [1, 5, 8, 5, 1, 3, 4, 6, 7]
        previousPermutation(b);
        System.out.println("And back:             " + Arrays.toString(b)); // [1, 5, 8, 4, 7, 6, 5, 3, 1]

        System.out.println("3rd permutation of 1..3: " + Arrays.toString(kthPermutation(3, 3))); // [2, 1, 3]
        System.out.println("9th permutation of 1..4: " + Arrays.toString(kthPermutation(4, 9))); // [2, 3, 1, 4]
    }
}
