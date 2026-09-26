package practiceQuestions.algomapio.dp.medium;

public class LongestCommonSubsequence {
    /**
     * Step-by-Step Thought Process
     * Understand the problem: Find the length of the longest common subsequence (LCS) between two strings, where a subsequence is formed by deleting some characters without changing the order.
     * Get the lengths m and n of text1 and text2, respectively.
     * Create a 2D array dp of size (m+1) x (n+1) initialized with zeros to store the LCS lengths for prefixes of text1 and text2.
     * Iterate through indices i from 1 to m and j from 1 to n to fill the dp array.
     * For each (i, j), if text1[i-1] equals text2[j-1], set dp[i][j] to dp[i-1][j-1] + 1, including the matching character.
     * Otherwise, set dp[i][j] to the maximum of dp[i-1][j] (excluding text1[i-1]) and dp[i][j-1] (excluding text2[j-1]).
     * Return dp[m][n] as the length of the longest common subsequence.
     * */

    public int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[m][n];
    }
}

/**
 * Detailed Explanation
 * What is the Longest Common Subsequence (LCS)?
 * The Longest Common Subsequence problem is a classic example of dynamic programming. It asks us to find the longest sequence of characters that appear left-to-right (not necessarily contiguously) in both input strings. For example, the LCS of "abcde" and "ace" is "ace" with a length of 3. This problem is fundamental in text comparison, DNA sequence alignment, and file differencing tools.
 *
 * Dynamic Programming Approach to LCS
 * To solve the LCS problem efficiently, we use a 2D dynamic programming table. Suppose the input strings are text1 and text2 of lengths m and n respectively. We create a table dp where dp[i][j] stores the length of the LCS between text1[0..i-1] and text2[0..j-1].
 *
 * We initialize a matrix of size (m+1) x (n+1) with zeros. Then we iterate through the characters of both strings. If the characters at current positions match, we extend the LCS by 1; otherwise, we take the maximum LCS length from the previous row or column.
 *
 * Time and Space Complexity
 * Time Complexity: O(m × n), where m and n are the lengths of the two input strings. We fill a matrix of size m×n.
 * Space Complexity: O(m × n), due to the 2D array storing intermediate LCS lengths. This can be optimized to O(min(m, n)) using a rolling array.
 * Optimization Tip
 * If memory is a concern, you can reduce the space usage by only storing the previous row of the DP matrix. Since each cell dp[i][j] only depends on dp[i-1][j], dp[i][j-1], and dp[i-1][j-1], a single row or two rows suffice.
 *
 * Conclusion
 * The Longest Common Subsequence is a cornerstone problem in computer science education. Mastering its dynamic programming solution provides a strong foundation for tackling more complex string processing challenges. Whether you're preparing for coding interviews or implementing text comparison tools, the LCS algorithm is essential knowledge.
 * */