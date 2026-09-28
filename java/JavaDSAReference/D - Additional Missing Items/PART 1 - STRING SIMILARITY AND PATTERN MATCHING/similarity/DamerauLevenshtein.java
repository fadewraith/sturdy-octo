package algorithms.strings.similarity;

/**
 * DAMERAU-LEVENSHTEIN DISTANCE
 * 
 * WHAT IT IS:
 * Like standard Levenshtein (Edit) distance, but it ALSO allows transposition of 
 * two adjacent characters as a single operation.
 * 
 * APPLIES TO:
 * - Spell-checkers, typo correction (human typos frequently involve swapping two 
 *   adjacent letters, like typing "hte" instead of "the").
 * 
 * COMBINATION:
 * Extends the Levenshtein DP table with one extra check for transpositions.
 * 
 * COMPLEXITY:
 * Time: O(M * N)
 * Space: O(M * N)
 */
public class DamerauLevenshtein {

    public static int getDistance(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int j = 0; j <= n; j++) dp[0][j] = j;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                
                int cost = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;

                // Standard Levenshtein: min of Insert, Delete, or Replace
                dp[i][j] = Math.min(
                    dp[i - 1][j] + 1,                 // Deletion
                    Math.min(dp[i][j - 1] + 1,        // Insertion
                             dp[i - 1][j - 1] + cost) // Substitution
                );

                // Damerau extension: Check for Transposition!
                if (i > 1 && j > 1 && s1.charAt(i - 1) == s2.charAt(j - 2) && s1.charAt(i - 2) == s2.charAt(j - 1)) {
                    dp[i][j] = Math.min(dp[i][j], dp[i - 2][j - 2] + cost);
                }
            }
        }
        return dp[m][n];
    }

    public static void main(String[] args) {
        System.out.println("--- DAMERAU-LEVENSHTEIN DEMO ---");
        String s1 = "caht";
        String s2 = "chat"; // Standard Levenshtein distance is 2 (replace 'a' with 'h', replace 'h' with 'a')
        
        System.out.println("String 1: " + s1);
        System.out.println("String 2: " + s2);
        System.out.println("Damerau-Levenshtein Distance: " + getDistance(s1, s2));
        // Expected: 1 (Because we can just transpose 'a' and 'h' in a single operation!)
    }
}
