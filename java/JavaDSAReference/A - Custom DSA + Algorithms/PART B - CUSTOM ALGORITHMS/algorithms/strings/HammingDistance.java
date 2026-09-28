package algorithms.strings;

/**
 * HAMMING DISTANCE
 * 
 * WHAT IT IS:
 * The Hamming distance between two EQUAL-LENGTH strings is the number of positions 
 * at which the corresponding symbols are different. 
 * In other words, it measures the minimum number of SUBSTITUTIONS required to change 
 * one string into the other.
 * 
 * APPLIES TO:
 * - Fixed-length strings.
 * - Binary data (comparing integers using XOR).
 * - Error detection/correction in telecommunications.
 * 
 * STRATEGY:
 * - A simple one-pass loop over the length of the strings.
 * - For integers, it is exactly equivalent to `countSetBits(x ^ y)`.
 * 
 * COMPLEXITY:
 * Time: O(N)
 * Space: O(1)
 */
public class HammingDistance {

    /**
     * String-based Hamming Distance.
     * Throws an exception if strings are not equal length.
     */
    public static int getDistance(String s1, String s2) {
        if (s1.length() != s2.length()) {
            throw new IllegalArgumentException("Strings must be of equal length!");
        }

        int distance = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                distance++;
            }
        }
        return distance;
    }

    /**
     * Integer (Binary) Hamming Distance.
     * Often asked as "Find the Hamming distance between two integers".
     */
    public static int getBinaryDistance(int x, int y) {
        int xor = x ^ y;
        int distance = 0;
        
        // Brian Kernighan's trick to count set bits
        while (xor != 0) {
            xor = xor & (xor - 1);
            distance++;
        }
        return distance;
    }

    public static void main(String[] args) {
        System.out.println("--- HAMMING DISTANCE DEMO ---");
        
        String str1 = "karolin";
        String str2 = "kathrin";
        System.out.println("String 1: " + str1);
        System.out.println("String 2: " + str2);
        System.out.println("String Hamming Distance: " + getDistance(str1, str2));
        // Expected: 3 (r->t, o->h, l->r)
        
        System.out.println();
        
        int a = 1; // 0001
        int b = 4; // 0100
        System.out.println("Int A: " + a + " (Binary: 0001)");
        System.out.println("Int B: " + b + " (Binary: 0100)");
        System.out.println("Binary Hamming Distance: " + getBinaryDistance(a, b));
        // Expected: 2 (Two bits are different)
    }
}
