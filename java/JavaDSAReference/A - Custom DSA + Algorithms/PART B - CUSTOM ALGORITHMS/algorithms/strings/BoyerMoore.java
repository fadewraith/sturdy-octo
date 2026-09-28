package algorithms.strings;

/**
 * BOYER-MOORE STRING SEARCH
 * 
 * WHAT IT IS:
 * Widely considered the standard benchmark for practical string search literature. 
 * Unlike KMP which scans left-to-right, Boyer-Moore compares characters from 
 * RIGHT-TO-LEFT inside the pattern window. 
 * 
 * WHEN TO USE THIS:
 * - It is the absolute fastest algorithm in practice for large alphabets and long patterns.
 * - Because it compares right-to-left, when a mismatch occurs, it can use the 
 *   "Bad Character Heuristic" to shift the pattern window massively to the right, 
 *   literally skipping huge sections of the text!
 * 
 * IMPORTANT NAME COLLISION:
 * Do not confuse this with the "Boyer-Moore Voting Algorithm" (Part B, Item 15). 
 * They were invented by the same authors (Robert Boyer and J Strother Moore), but they 
 * are completely unrelated algorithms! (This one is for strings, the other is for arrays).
 * 
 * HEURISTICS:
 * 1. Bad Character Heuristic: Shift pattern until the mismatched character matches.
 * 2. Good Suffix Heuristic: Shift pattern to align with a matching suffix.
 * (This implementation focuses on the highly effective Bad Character Heuristic for clarity).
 * 
 * COMPLEXITY:
 * Time: Best case is sub-linear O(N / M) because it skips chars! Worst case O(N * M).
 * Space: O(Alphabet Size) for the bad character table.
 */
public class BoyerMoore {

    private static final int NO_OF_CHARS = 256;

    /**
     * Preprocesses the pattern to build the Bad Character table.
     * It stores the rightmost index of every character in the pattern.
     */
    private static void badCharHeuristic(String str, int size, int[] badChar) {
        // Initialize all occurrences as -1
        for (int i = 0; i < NO_OF_CHARS; i++) {
            badChar[i] = -1;
        }

        // Fill the actual value of last occurrence of a character
        for (int i = 0; i < size; i++) {
            badChar[(int) str.charAt(i)] = i;
        }
    }

    public static void search(String text, String pattern) {
        int m = pattern.length();
        int n = text.length();

        if (m == 0 || n < m) return;

        int[] badChar = new int[NO_OF_CHARS];

        // Preprocess pattern
        badCharHeuristic(pattern, m, badChar);

        int s = 0; // Shift of the pattern with respect to text
        
        while (s <= (n - m)) {
            int j = m - 1;

            // Keep reducing index j of pattern while characters match (RIGHT-TO-LEFT)
            while (j >= 0 && pattern.charAt(j) == text.charAt(s + j)) {
                j--;
            }

            // If the pattern is present at current shift
            if (j < 0) {
                System.out.println("Pattern found at index " + s);
                
                // Shift the pattern so that the next character in text aligns 
                // with the last occurrence of it in pattern.
                s += (s + m < n) ? m - badChar[text.charAt(s + m)] : 1;
            } else {
                // Shift the pattern so that the bad character in text aligns 
                // with the last occurrence of it in pattern.
                // We use Math.max to ensure the shift is positive!
                s += Math.max(1, j - badChar[text.charAt(s + j)]);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("--- BOYER-MOORE DEMO ---");
        String text = "ABAAABCD";
        String pattern = "ABC";
        
        System.out.println("Text: " + text);
        System.out.println("Pattern: " + pattern);
        
        search(text, pattern);
        // Expected: found at 4
    }
}
