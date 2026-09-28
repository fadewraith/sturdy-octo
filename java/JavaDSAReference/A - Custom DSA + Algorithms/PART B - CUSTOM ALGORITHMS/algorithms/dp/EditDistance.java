package algorithms.dp;

/**
 * EDIT DISTANCE (Levenshtein Distance)
 * 
 * WHAT IT IS:
 * Finds the minimum number of operations (Insert, Delete, Replace) required to 
 * convert String A into String B.
 * 
 * WHEN TO USE THIS:
 * - Spell checkers (suggesting the closest word to a typo).
 * - DNA sequence alignment.
 * 
 * STRATEGY:
 * We use a 2D array `dp[i][j]` representing the edit distance between the first `i` 
 * characters of String A, and the first `j` characters of String B.
 * 
 * Base cases:
 * - `dp[i][0] = i` (Cost to convert a string of length `i` to empty string is `i` deletions).
 * - `dp[0][j] = j` (Cost to convert empty string to length `j` is `j` insertions).
 * 
 * General cases:
 * - If `A[i-1] == B[j-1]`: `dp[i][j] = dp[i-1][j-1]` (Match! No cost).
 * - If they don't match, we take the MINIMUM of the 3 possible operations + 1:
 *   1. Insert: `dp[i][j-1]`
 *   2. Remove: `dp[i-1][j]`
 *   3. Replace: `dp[i-1][j-1]`
 * 
 * COMPLEXITY:
 * Time: O(M * N)
 * Space: O(M * N)
 */
public class EditDistance {

    public static int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                
                // If first string is empty, only option is to insert all characters of second string
                if (i == 0) {
                    dp[i][j] = j; 
                } 
                // If second string is empty, only option is to remove all characters of first string
                else if (j == 0) {
                    dp[i][j] = i; 
                } 
                // If last characters are same, ignore last char and recur for remaining string
                else if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } 
                // If last character are different, consider all possibilities and find minimum
                else {
                    dp[i][j] = 1 + Math.min(dp[i][j - 1],       // Insert
                                   Math.min(dp[i - 1][j],       // Remove
                                            dp[i - 1][j - 1])); // Replace
                }
            }
        }
        return dp[m][n];
    }

    public static void main(String[] args) {
        System.out.println("--- EDIT DISTANCE DEMO ---");
        String word1 = "horse";
        String word2 = "ros";
        
        System.out.println("Word 1: " + word1);
        System.out.println("Word 2: " + word2);
        System.out.println("Minimum Operations: " + minDistance(word1, word2));
        // Expected: 3
        // 1. horse -> rorse (replace 'h' with 'r')
        // 2. rorse -> rose (remove 'r')
        // 3. rose -> ros (remove 'e')
    }
}
