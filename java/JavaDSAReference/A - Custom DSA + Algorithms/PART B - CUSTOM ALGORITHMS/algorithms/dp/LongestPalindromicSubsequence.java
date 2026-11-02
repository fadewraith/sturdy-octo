package algorithms.dp;

/**
 * LONGEST PALINDROMIC SUBSEQUENCE (LPS)
 *
 * WHAT IT IS:
 * The longest SUBSEQUENCE (characters kept in order, gaps allowed) that reads the
 * same forwards and backwards. "bbbab" -> "bbbb" (length 4).
 * Not to be confused with the longest palindromic SUBSTRING (contiguous) - for that
 * see ManachersAlgorithm.java.
 *
 * WHEN TO USE THIS:
 * - "Minimum insertions (or deletions) to make a string a palindrome" = n - LPS.
 * - A classic example of INTERVAL DP (answers for s[i..j] built from shorter ranges).
 *
 * STRATEGY:
 * dp[i][j] = LPS length of s[i..j].
 *   - one character:            dp[i][i] = 1
 *   - s[i] == s[j]:             dp[i][j] = dp[i+1][j-1] + 2   (wrap the inner answer)
 *   - otherwise:                dp[i][j] = max(dp[i+1][j], dp[i][j-1])  (drop one end)
 * Fill by increasing range length (or i from right to left), so the shorter ranges
 * are ready. To get the actual palindrome, walk the table from dp[0][n-1] using the
 * same decisions, building the left half and mirroring it.
 *
 * ALTERNATIVE VIEW: LPS(s) = LCS(s, reverse(s)).
 *
 * COMPLEXITY:
 * Time: O(N^2)
 * Space: O(N^2) for the table (O(N) is possible for the length alone).
 */
public class LongestPalindromicSubsequence {

    private static int[][] table(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        for (int i = n - 1; i >= 0; i--) {
            dp[i][i] = 1;
            for (int j = i + 1; j < n; j++) {
                if (s.charAt(i) == s.charAt(j)) dp[i][j] = dp[i + 1][j - 1] + 2; // (inner is 0 when j == i+1)
                else dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
            }
        }
        return dp;
    }

    public static int length(String s) {
        if (s.isEmpty()) return 0;
        return table(s)[0][s.length() - 1];
    }

    /** One longest palindromic subsequence. */
    public static String reconstruct(String s) {
        int n = s.length();
        if (n == 0) return "";
        int[][] dp = table(s);
        char[] result = new char[dp[0][n - 1]];
        int left = 0, right = result.length - 1;
        int i = 0, j = n - 1;
        while (i <= j) {
            if (i == j) {                       // the middle character of an odd palindrome
                result[left] = s.charAt(i);
                break;
            }
            if (s.charAt(i) == s.charAt(j)) {   // both ends belong to the answer
                result[left++] = s.charAt(i);
                result[right--] = s.charAt(j);
                i++;
                j--;
            } else if (dp[i + 1][j] >= dp[i][j - 1]) {
                i++;
            } else {
                j--;
            }
        }
        return new String(result);
    }

    /** Fewest characters to insert anywhere to make s a palindrome. */
    public static int minInsertionsToPalindrome(String s) {
        return s.length() - length(s);
    }

    public static void main(String[] args) {
        System.out.println("--- LONGEST PALINDROMIC SUBSEQUENCE DEMO ---");
        String[] words = {"bbbab", "cbbd", "character", "a"};
        for (String w : words) {
            System.out.println(w + ": length " + length(w) + ", e.g. \"" + reconstruct(w)
                    + "\", insertions to make a palindrome: " + minInsertionsToPalindrome(w));
        }
        // bbbab: 4 "bbbb"; cbbd: 2 "bb"; character: 5 "carac"; a: 1
    }
}
