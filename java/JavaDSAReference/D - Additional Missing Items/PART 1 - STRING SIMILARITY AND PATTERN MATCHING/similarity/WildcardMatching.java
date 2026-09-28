package algorithms.strings.similarity;

/**
 * WILDCARD PATTERN MATCHING
 * 
 * WHAT IT IS:
 * Determines if a string matches a pattern containing wildcards:
 * '?' matches any single character.
 * '*' matches any sequence of characters (including empty).
 * 
 * APPLIES TO:
 * Glob-style matching (e.g. "*.txt").
 * 
 * STRATEGY:
 * DP table `dp[i][j]` where i is text length, j is pattern length.
 */
public class WildcardMatching {

    public static boolean isMatch(String s, String p) {
        int m = s.length(), n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        
        dp[0][0] = true;
        
        // Handle patterns starting with '*'
        for (int j = 1; j <= n; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 1];
            }
        }
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (p.charAt(j - 1) == '?' || s.charAt(i - 1) == p.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (p.charAt(j - 1) == '*') {
                    // Match zero chars OR match one more char
                    dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                }
            }
        }
        return dp[m][n];
    }

    public static void main(String[] args) {
        System.out.println("--- WILDCARD MATCHING DEMO ---");
        System.out.println("Matches 'adceb' against '*a*b'? " + isMatch("adceb", "*a*b")); // true
        System.out.println("Matches 'acdcb' against 'a*c?b'? " + isMatch("acdcb", "a*c?b")); // false
    }
}
