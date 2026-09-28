package algorithms.strings;

/**
 * KMP (Knuth-Morris-Pratt) ALGORITHM
 * 
 * WHAT IT IS:
 * An advanced string searching algorithm that achieves linear time O(N + M) by 
 * never re-evaluating characters in the text that it has already seen.
 * 
 * WHEN TO USE THIS:
 * - When searching for a pattern in a massive text.
 * - When the pattern contains a lot of repeating sub-patterns (e.g., DNA sequences).
 * 
 * HOW IT WORKS (The LPS Array):
 * It pre-processes the pattern to create an LPS (Longest Proper Prefix which is also Suffix) array.
 * If a mismatch occurs, the LPS array tells the algorithm exactly how far to safely 
 * shift the pattern forward WITHOUT having to backtrack the text pointer!
 * 
 * PSEUDOCODE:
 * lps = computeLPS(pattern)
 * i = 0, j = 0
 * while i < N:
 *     if text[i] == pattern[j]: i++, j++
 *     if j == M: found match, j = lps[j-1]
 *     else if text[i] != pattern[j]:
 *         if j != 0: j = lps[j-1]
 *         else: i++
 * 
 * COMPLEXITY:
 * Time: O(N + M) strictly linear.
 * Space: O(M) for the LPS array.
 */
public class KMPAlgorithm {

    public static void search(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();
        if (m == 0 || n < m) return;

        // Create LPS array that will hold the longest prefix suffix values
        int[] lps = new int[m];
        computeLPSArray(pattern, m, lps);

        int i = 0; // index for text
        int j = 0; // index for pattern
        
        while (i < n) {
            if (pattern.charAt(j) == text.charAt(i)) {
                j++;
                i++;
            }
            if (j == m) {
                System.out.println("Pattern found at index " + (i - j));
                j = lps[j - 1]; // Reset j based on LPS to find multiple occurrences
            } else if (i < n && pattern.charAt(j) != text.charAt(i)) {
                // Mismatch after j matches!
                if (j != 0) {
                    // Don't backtrack i. Just shift j using LPS.
                    j = lps[j - 1];
                } else {
                    i = i + 1;
                }
            }
        }
    }

    /**
     * Preprocesses the pattern to build the LPS array in O(M) time.
     */
    private static void computeLPSArray(String pat, int m, int[] lps) {
        int len = 0; // Length of the previous longest prefix suffix
        int i = 1;
        lps[0] = 0; // lps[0] is always 0

        while (i < m) {
            if (pat.charAt(i) == pat.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    // Tricky case: we don't increment i here
                    len = lps[len - 1];
                } else {
                    lps[i] = len;
                    i++;
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("--- KMP ALGORITHM DEMO ---");
        String text = "ABABDABACDABABCABAB";
        String pattern = "ABABCABAB";
        
        System.out.println("Text: " + text);
        System.out.println("Pattern: " + pattern);
        
        search(text, pattern);
        // Expected: found at 10
    }
}
