package algorithms.strings;

/**
 * Z-ALGORITHM
 * 
 * WHAT IT IS:
 * An algorithm that finds all occurrences of a pattern in a text in strictly linear time. 
 * It works by constructing a Z-Array, where Z[i] stores the length of the longest 
 * substring starting from `i` that is also a prefix of the whole string.
 * 
 * WHEN TO USE THIS:
 * - Similar use cases to KMP (finding all exact matches in linear time).
 * - It is often preferred over KMP in competitive programming because the logic for 
 *   computing the Z-Array is slightly more intuitive and shorter to write than 
 *   computing the LPS array.
 * 
 * STRATEGY:
 * We concatenate the Pattern and the Text together with a special delimiter character 
 * (like '$') in between: "PATTERN$TEXT". 
 * Then we compute the Z-array for this combined string. 
 * If any value in the Z-array exactly equals the length of the Pattern, it means we 
 * found a match in the Text!
 * 
 * COMPLEXITY:
 * Time: O(N + M)
 * Space: O(N + M) for the combined string and the Z-array.
 */
public class ZAlgorithm {

    public static void search(String text, String pattern) {
        if (pattern.length() == 0 || text.length() < pattern.length()) return;

        // Create concatenated string "Pattern$Text"
        String concat = pattern + "$" + text;
        int l = concat.length();

        // Construct Z array
        int[] Z = new int[l];
        getZArray(concat, Z);

        // Loop through Z array to find matches
        for (int i = 0; i < l; ++i) {
            // If Z[i] (longest prefix match length) equals pattern length...
            if (Z[i] == pattern.length()) {
                // We subtract pattern.length() and 1 (for the '$' char) to get the original index
                System.out.println("Pattern found at index " + (i - pattern.length() - 1));
            }
        }
    }

    /**
     * Core Z-Algorithm logic to compute the Z-array.
     * Maintains a "window" [L, R] which is the interval with max R 
     * such that concat[L...R] is a prefix of concat.
     */
    private static void getZArray(String str, int[] Z) {
        int n = str.length();
        int L = 0, R = 0;

        for (int i = 1; i < n; ++i) {
            // If i > R, nothing is mapped yet, we compute it manually
            if (i > R) {
                L = R = i;
                while (R < n && str.charAt(R - L) == str.charAt(R)) {
                    R++;
                }
                Z[i] = R - L;
                R--;
            } else {
                // i <= R. We are inside a matching window! 
                // We can use previously computed Z values to avoid redundant work.
                int k = i - L;

                // If Z[k] is less than remaining interval, Z[i] will be equal to Z[k]
                if (Z[k] < R - i + 1) {
                    Z[i] = Z[k];
                } else {
                    // Otherwise it might extend past R, so we must manually check
                    L = i;
                    while (R < n && str.charAt(R - L) == str.charAt(R)) {
                        R++;
                    }
                    Z[i] = R - L;
                    R--;
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Z-ALGORITHM DEMO ---");
        String text = "GEEKS FOR GEEKS";
        String pattern = "GEEK";
        
        System.out.println("Text: " + text);
        System.out.println("Pattern: " + pattern);
        
        search(text, pattern);
        // Expected: found at 0, 10
    }
}
