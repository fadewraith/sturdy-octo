package algorithms.strings;

/**
 * MANACHER'S ALGORITHM
 * 
 * WHAT IT IS:
 * An incredibly clever algorithm used strictly to find the Longest Palindromic Substring 
 * in a string in strictly O(N) linear time.
 * 
 * WHEN TO USE THIS:
 * - When asked to find the Longest Palindrome in a String.
 * - Why? The naive "expand around center" approach takes O(N^2) time. Manacher's 
 *   algorithm optimizes this to O(N) by using previously computed palindrome radii 
 *   to avoid re-checking identical sub-palindromes.
 * 
 * STRATEGY (The trick):
 * Palindromes can be even length (e.g. "abba") or odd length (e.g. "aba"). 
 * Checking both cases dynamically is a headache. Manacher's trick is to insert a 
 * bogus character (like '#') between every single character, turning ALL palindromes 
 * into odd-length palindromes with a distinct center!
 * "abba" -> "#a#b#b#a#" (center is the middle #)
 * "aba"  -> "#a#b#a#"   (center is the b)
 * 
 * COMPLEXITY:
 * Time: O(N)
 * Space: O(N) for the preprocessed string and the radii array.
 */
public class ManachersAlgorithm {

    public static String longestPalindrome(String s) {
        if (s == null || s.length() == 0) return "";
        
        // 1. Transform string to handle both even and odd length palindromes seamlessly
        char[] T = preProcess(s);
        int n = T.length;
        
        // P[i] will store the radius of the longest palindrome centered at T[i]
        int[] P = new int[n]; 
        
        int center = 0; // Center of the palindrome that extends furthest to the right
        int rightBoundary = 0; // The rightmost boundary of that palindrome
        
        for (int i = 1; i < n - 1; i++) {
            // Find the corresponding letter in the palindrome mirroring `center`
            int mirror = 2 * center - i;
            
            // If we are still within the boundary, we can borrow the previously computed 
            // radius from the mirror, bounded by the distance to the right edge.
            if (i < rightBoundary) {
                P[i] = Math.min(rightBoundary - i, P[mirror]);
            }
            
            // Attempt to expand palindrome centered at i
            while (T[i + (1 + P[i])] == T[i - (1 + P[i])]) {
                P[i]++;
            }
            
            // If palindrome centered at i expands past rightBoundary,
            // adjust center and rightBoundary based on expanded palindrome.
            if (i + P[i] > rightBoundary) {
                center = i;
                rightBoundary = i + P[i];
            }
        }
        
        // 2. Find the maximum element in P
        int maxLen = 0;
        int centerIndex = 0;
        for (int i = 1; i < n - 1; i++) {
            if (P[i] > maxLen) {
                maxLen = P[i];
                centerIndex = i;
            }
        }
        
        // 3. Extract the original substring
        int startIndex = (centerIndex - 1 - maxLen) / 2;
        return s.substring(startIndex, startIndex + maxLen);
    }

    /**
     * Inserts '#' boundaries.
     * Adds '^' at the start and '$' at the end to prevent bounds checking in the while loop.
     * Example: "aba" -> "^#a#b#a#$"
     */
    private static char[] preProcess(String s) {
        int n = s.length();
        char[] t = new char[n * 2 + 3];
        t[0] = '^';
        t[n * 2 + 2] = '$';
        int index = 1;
        for (char c : s.toCharArray()) {
            t[index++] = '#';
            t[index++] = c;
        }
        t[index] = '#';
        return t;
    }

    public static void main(String[] args) {
        System.out.println("--- MANACHER'S ALGORITHM DEMO ---");
        String s1 = "babad";
        System.out.println("String: " + s1);
        System.out.println("Longest Palindrome: " + longestPalindrome(s1)); // "bab" or "aba"
        
        String s2 = "cbbd";
        System.out.println("\nString: " + s2);
        System.out.println("Longest Palindrome: " + longestPalindrome(s2)); // "bb"
    }
}
