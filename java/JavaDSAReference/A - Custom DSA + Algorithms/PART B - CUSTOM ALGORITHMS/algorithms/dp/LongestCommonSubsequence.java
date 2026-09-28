package algorithms.dp;

/**
 * LONGEST COMMON SUBSEQUENCE (LCS)
 * 
 * WHAT IT IS:
 * Finds the length of the longest subsequence present in both of two strings.
 * A subsequence appears in the same relative order, but not necessarily contiguous.
 * (e.g., "abc", "abg", "bdf", "aeg", '"ace", "fg", etc. are subsequences of "abcdefg").
 * 
 * WHEN TO USE THIS:
 * - Git Diff tools! (Comparing two text files to see what lines were added/removed 
 *   relies heavily on finding the LCS between the two files).
 * - Bioinformatics (DNA sequence alignment).
 * 
 * STRATEGY:
 * We use a 2D array `dp[i][j]` representing the LCS of string X up to length i 
 * and string Y up to length j.
 * - If X[i-1] == Y[j-1]: `dp[i][j] = 1 + dp[i-1][j-1]` (Match! Add 1 to diagonal).
 * - If X[i-1] != Y[j-1]: `dp[i][j] = max(dp[i-1][j], dp[i][j-1])` (Mismatch. Carry over max from top or left).
 * 
 * COMPLEXITY:
 * Time: O(M * N) where M and N are lengths of the strings.
 * Space: O(M * N) (can be optimized to O(min(M, N))).
 */
public class LongestCommonSubsequence {

    public static int lcs(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        
        int[][] dp = new int[m + 1][n + 1];

        // Build the dp table bottom up
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                
                if (i == 0 || j == 0) {
                    dp[i][j] = 0;
                } 
                else if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } 
                else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        
        return dp[m][n];
    }

    public static void main(String[] args) {
        System.out.println("--- LONGEST COMMON SUBSEQUENCE DEMO ---");
        String s1 = "AGGTAB";
        String s2 = "GXTXAYB";
        
        System.out.println("String 1: " + s1);
        System.out.println("String 2: " + s2);
        System.out.println("Length of LCS is: " + lcs(s1, s2));
        // Expected: 4 (The LCS is "GTAB")
    }
}
