package algorithms.dp;

/**
 * LONGEST COMMON SUBSTRING
 * 
 * WHAT IT IS:
 * Finds the longest string that is a substring of two or more strings.
 * 
 * CRITICAL DIFFERENCE FROM LONGEST COMMON SUBSEQUENCE (LCS):
 * A substring MUST BE CONTIGUOUS. A subsequence does not have to be.
 * Example: "ABABC", "BABCA"
 * Longest Common Substring: "BABC" (Length 4)
 * Longest Common Subsequence: "BABC" (Length 4, but could also be non-contiguous in other examples).
 * 
 * STRATEGY:
 * We use a 2D array `dp[i][j]` representing the length of the longest common suffix 
 * of the substrings ending at `s1[i-1]` and `s2[j-1]`.
 * - If `s1[i-1] == s2[j-1]`: `dp[i][j] = 1 + dp[i-1][j-1]`.
 * - If they DON'T match: `dp[i][j] = 0`! (Because it must be contiguous, the streak is broken).
 * We track the maximum value seen anywhere in the matrix.
 * 
 * COMPLEXITY:
 * Time: O(M * N)
 * Space: O(M * N) (can be optimized to O(min(M, N)))
 */
public class LongestCommonSubstring {

    public static int longestCommonSubstring(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        
        int[][] dp = new int[m + 1][n + 1];
        int maxLength = 0;

        // Build the dp table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    maxLength = Math.max(maxLength, dp[i][j]);
                } else {
                    // Reset to 0 because the contiguity is broken
                    dp[i][j] = 0;
                }
            }
        }
        
        return maxLength;
    }

    public static void main(String[] args) {
        System.out.println("--- LONGEST COMMON SUBSTRING DEMO ---");
        String s1 = "OldSite:GeeksforGeeks.org";
        String s2 = "NewSite:GeeksQuiz.com";
        
        System.out.println("String 1: " + s1);
        System.out.println("String 2: " + s2);
        System.out.println("Length of Longest Common Substring is: " + longestCommonSubstring(s1, s2));
        // Expected: 10 ("Site:Geeks")
    }
}
