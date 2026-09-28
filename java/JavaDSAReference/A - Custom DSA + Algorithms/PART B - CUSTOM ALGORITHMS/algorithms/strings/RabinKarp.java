package algorithms.strings;

/**
 * RABIN-KARP ALGORITHM (Rolling Hash)
 * 
 * WHAT IT IS:
 * A string searching algorithm that uses HASHING to find any one of a set of pattern 
 * strings in a text. 
 * 
 * WHEN TO USE THIS:
 * - Extremely powerful when searching for MULTIPLE patterns of the same length simultaneously 
 *   (e.g., Plagiarism Detection, where you want to see if any 10-word phrase matches a database).
 * 
 * HOW IT WORKS (Rolling Hash):
 * Instead of recalculating the hash of every M-length substring from scratch, it subtracts 
 * the leading character's math and adds the trailing character's math. This drops the hash 
 * calculation time from O(M) down to O(1) per step!
 * 
 * COMPLEXITY:
 * Time: O(N + M) average case. O(N * M) worst case (if hash collisions happen frequently).
 * Space: O(1)
 */
public class RabinKarp {

    // d is the number of characters in the input alphabet (256 for ASCII)
    private final static int d = 256;
    
    // A prime number to use for the modulo math to prevent integer overflow
    private final static int q = 101; 

    public static void search(String text, String pattern) {
        int m = pattern.length();
        int n = text.length();
        int i, j;
        int p = 0; // hash value for pattern
        int t = 0; // hash value for text window
        int h = 1;

        if (m == 0 || n < m) return;

        // The value of h would be "pow(d, m-1)%q"
        for (i = 0; i < m - 1; i++) {
            h = (h * d) % q;
        }

        // Calculate the initial hash value of pattern and first window of text
        for (i = 0; i < m; i++) {
            p = (d * p + pattern.charAt(i)) % q;
            t = (d * t + text.charAt(i)) % q;
        }

        // Slide the pattern over text one by one
        for (i = 0; i <= n - m; i++) {

            // Check if the hash values match
            if (p == t) {
                // If hash matches, we MUST check characters one by one (to prevent hash collision false-positives)
                for (j = 0; j < m; j++) {
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        break;
                    }
                }
                if (j == m) {
                    System.out.println("Pattern found at index " + i);
                }
            }

            // Calculate hash value for the next window of text (Rolling Hash math!)
            if (i < n - m) {
                t = (d * (t - text.charAt(i) * h) + text.charAt(i + m)) % q;
                
                // We might get negative value of t, converting it to positive
                if (t < 0) {
                    t = (t + q);
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("--- RABIN-KARP DEMO ---");
        String text = "GEEKS FOR GEEKS";
        String pattern = "GEEK";
        
        System.out.println("Text: " + text);
        System.out.println("Pattern: " + pattern);
        
        search(text, pattern);
        // Expected: found at 0, 10
    }
}
