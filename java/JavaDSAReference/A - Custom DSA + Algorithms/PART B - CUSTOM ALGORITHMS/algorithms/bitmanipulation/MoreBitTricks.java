package algorithms.bitmanipulation;

/**
 * MORE BIT MANIPULATION TRICKS
 * 
 * 1. IS POWER OF TWO
 * Problem: Check if a given positive integer is a power of 2.
 * Trick: A power of 2 in binary ALWAYS has exactly one '1' bit (e.g., 2 is 10, 
 * 4 is 100, 8 is 1000). If you subtract 1, all bits invert (e.g., 8 is 1000, 7 is 0111).
 * If you do `n & (n - 1)`, it will evaluate to 0 ONLY if it was a power of 2!
 * 
 * 2. SWAP WITHOUT TEMP VARIABLE (XOR SWAP)
 * Problem: Swap two integers without allocating a `temp` variable.
 * Trick: XOR logic! A XOR A = 0. A XOR 0 = A.
 * a = a ^ b
 * b = a ^ b  (This evaluates to: (a ^ b) ^ b = a ^ (b ^ b) = a ^ 0 = a)
 * a = a ^ b  (This evaluates to: (a ^ b) ^ a = b ^ (a ^ a) = b ^ 0 = b)
 * 
 * COMPLEXITY:
 * Time: O(1) for both.
 * Space: O(1) for both.
 */
public class MoreBitTricks {

    public static boolean isPowerOfTwo(int n) {
        // Must be positive, and the bitwise trick must evaluate to 0
        return n > 0 && (n & (n - 1)) == 0;
    }

    public static void swapXOR() {
        int a = 15;
        int b = 42;
        
        System.out.println("Before Swap: a = " + a + ", b = " + b);
        
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
        
        System.out.println("After Swap:  a = " + a + ", b = " + b);
    }

    public static void main(String[] args) {
        System.out.println("--- MORE BIT MANIPULATION DEMO ---");
        
        System.out.println("Is 16 a power of 2? " + isPowerOfTwo(16)); // true
        System.out.println("Is 18 a power of 2? " + isPowerOfTwo(18)); // false
        
        System.out.println();
        swapXOR();
    }
}
