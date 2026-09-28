package algorithms.strings.similarity;

/**
 * BITAP ALGORITHM (Shift-Or / Shift-And)
 * 
 * WHAT IT IS:
 * A bit-parallel approximate string matching algorithm. 
 * Used internally by Unix tools like `agrep`.
 * 
 * STRATEGY:
 * It builds a bitmask for each character in the alphabet.
 * It uses bitwise Shift and OR operations to maintain a state machine of matches.
 * Unlike Levenshtein DP which takes O(M * N), Bitap with small patterns (<= 64 chars) 
 * can perform approximate matching in virtually O(N) by doing 64 parallel checks 
 * per bitwise operation!
 */
public class Bitap {
    
    public static int searchExact(String text, String pattern) {
        int m = pattern.length();
        if (m > 64) throw new IllegalArgumentException("Pattern too long for 64-bit Bitap");
        
        long[] patternMask = new long[256];
        // Initialize all masks to 1s
        for (int i = 0; i <= 255; i++) patternMask[i] = ~0;
        
        // Clear the bit corresponding to the character's position in the pattern
        for (int i = 0; i < m; i++) {
            patternMask[pattern.charAt(i)] &= ~(1L << i);
        }
        
        long state = ~0; // All 1s
        
        for (int i = 0; i < text.length(); i++) {
            // Shift state left by 1, clear the 0th bit, and OR with the pattern mask
            state = (state << 1) | patternMask[text.charAt(i)];
            
            // If the m-th bit is 0, we found a match!
            if ((state & (1L << (m - 1))) == 0) {
                return i - m + 1; // Return start index
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println("--- BITAP (EXACT SEARCH) DEMO ---");
        String text = "hello world";
        String pattern = "world";
        System.out.println("Found '" + pattern + "' at index: " + searchExact(text, pattern));
    }
}
