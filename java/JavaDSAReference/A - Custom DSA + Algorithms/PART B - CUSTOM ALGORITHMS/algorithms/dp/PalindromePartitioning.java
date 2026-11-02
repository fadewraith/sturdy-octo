package algorithms.dp;

import java.util.Arrays;

/**
 * PALINDROME PARTITIONING (all partitions + minimum cuts)
 *
 * WHAT IT IS:
 * Cut a string into pieces so that EVERY piece is a palindrome.
 *   - Partitioning I:  list every such partition ("aab" -> [a,a,b], [aa,b]).
 *   - Partitioning II: the minimum number of cuts ("aab" -> 1, i.e. "aa|b").
 *
 * WHEN TO USE THIS:
 * - Partitioning I is a classic BACKTRACKING problem (choose a prefix, recurse).
 * - Partitioning II is a classic "split a sequence" DP, like Word Break.
 *
 * STEP 0 - PALINDROME TABLE (shared):
 * isPal[i][j] = s[i] == s[j] && (j - i < 2 || isPal[i+1][j-1]).
 * O(N^2) once, then every "is s[i..j] a palindrome?" check is O(1).
 *
 * STRATEGY - ALL PARTITIONS (backtracking):
 * From position `start`, try every end j where s[start..j] is a palindrome, take
 * that piece, recurse from j+1, then undo the choice.
 *
 * STRATEGY - MIN CUTS (DP):
 * cuts[j] = fewest cuts for the prefix s[0..j].
 *   if s[0..j] is itself a palindrome: cuts[j] = 0
 *   else cuts[j] = min over i in 1..j with s[i..j] palindrome of cuts[i-1] + 1
 *   (cut right before i, and s[i..j] is the last piece).
 *
 * COMPLEXITY:
 * All partitions: Time O(N * 2^N) worst case (e.g. "aaaa...": every cut works).
 * Min cuts:       Time O(N^2), Space O(N^2) for the table.
 */
public class PalindromePartitioning {

    private static boolean[][] palindromeTable(String s) {
        int n = s.length();
        boolean[][] isPal = new boolean[n][n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                isPal[i][j] = s.charAt(i) == s.charAt(j) && (j - i < 2 || isPal[i + 1][j - 1]);
            }
        }
        return isPal;
    }

    /** Fewest cuts so that every piece is a palindrome. */
    public static int minCut(String s) {
        int n = s.length();
        if (n == 0) return 0;
        boolean[][] isPal = palindromeTable(s);
        int[] cuts = new int[n];
        for (int j = 0; j < n; j++) {
            if (isPal[0][j]) { cuts[j] = 0; continue; }
            int best = j; // worst case: cut between every character
            for (int i = 1; i <= j; i++) {
                if (isPal[i][j]) best = Math.min(best, cuts[i - 1] + 1);
            }
            cuts[j] = best;
        }
        return cuts[n - 1];
    }

    // Growable result list, since java.util collections are off-limits here
    private static String[][] results;
    private static int resultCount;

    /** Every way to split s into palindromes, in the order backtracking finds them. */
    public static String[][] allPartitions(String s) {
        results = new String[4][];
        resultCount = 0;
        if (s.isEmpty()) return new String[][]{{}};
        backtrack(s, 0, palindromeTable(s), new String[s.length()], 0);
        return Arrays.copyOf(results, resultCount);
    }

    private static void backtrack(String s, int start, boolean[][] isPal, String[] pieces, int count) {
        if (start == s.length()) {
            if (resultCount == results.length) results = Arrays.copyOf(results, results.length * 2);
            results[resultCount++] = Arrays.copyOf(pieces, count);
            return;
        }
        for (int end = start; end < s.length(); end++) {
            if (!isPal[start][end]) continue;
            pieces[count] = s.substring(start, end + 1); // choose
            backtrack(s, end + 1, isPal, pieces, count + 1);
            // (no explicit undo needed: pieces[count] is overwritten by the next choice)
        }
    }

    public static void main(String[] args) {
        System.out.println("--- PALINDROME PARTITIONING DEMO ---");
        System.out.println("All partitions of \"aab\": " + Arrays.deepToString(allPartitions("aab")));
        // [[a, a, b], [aa, b]]
        System.out.println("Min cuts \"aab\":        " + minCut("aab"));        // 1
        System.out.println("Min cuts \"banana\":     " + minCut("banana"));     // 1 (b | anana)
        System.out.println("Min cuts \"racecar\":    " + minCut("racecar"));    // 0
        System.out.println("Min cuts \"abcde\":      " + minCut("abcde"));      // 4
    }
}
