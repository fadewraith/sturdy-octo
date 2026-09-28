package algorithms.strings;

/**
 * NAIVE PATTERN MATCHING
 * 
 * WHAT IT IS:
 * The simplest approach to finding a substring (pattern) within a larger string (text).
 * It simply slides the pattern over the text one by one and checks for a match.
 * 
 * WHEN TO USE THIS:
 * - When the strings are very small.
 * - When you want zero memory overhead (no preprocessing arrays like KMP or Z-Algorithm).
 * - When you don't care about worst-case O(N * M) performance.
 * 
 * DATA STRUCTURE:
 * Strings / Character Arrays.
 * 
 * PSEUDOCODE:
 * for i from 0 to (N - M):
 *     for j from 0 to M - 1:
 *         if text[i + j] != pattern[j]: break
 *     if j == M: print "Pattern found at i"
 * 
 * COMPLEXITY:
 * Time: O(N * M) worst case (e.g., Text="AAAAAB", Pattern="AAB"). Best case is O(N).
 * Space: O(1)
 */
public class NaivePatternMatching {

    public static void search(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();

        if (m == 0 || n < m) return;

        // A loop to slide pattern[] one by one
        for (int i = 0; i <= n - m; i++) {
            int j;

            // For current index i, check for pattern match
            for (j = 0; j < m; j++) {
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    break;
                }
            }

            // If j reached the end of the pattern, it's a full match!
            if (j == m) {
                System.out.println("Pattern found at index " + i);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("--- NAIVE PATTERN MATCHING DEMO ---");
        String text = "AABAACAADAABAABA";
        String pattern = "AABA";
        
        System.out.println("Text: " + text);
        System.out.println("Pattern: " + pattern);
        
        search(text, pattern);
        // Expected: found at 0, 9, 12
    }
}
