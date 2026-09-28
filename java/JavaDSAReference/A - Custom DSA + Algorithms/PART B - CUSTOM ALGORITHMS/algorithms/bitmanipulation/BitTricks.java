package algorithms.bitmanipulation;

/**
 * BIT MANIPULATION TRICKS
 * 
 * 1. SINGLE NUMBER (XOR TRICK)
 * Problem: Given an array where every element appears twice EXCEPT for one, find it.
 * Trick: A XOR A = 0. A XOR 0 = A. XOR is commutative.
 * Result: If you XOR every number in the array together, all duplicates cancel out to 0, 
 * leaving ONLY the single number remaining! O(N) time, O(1) space.
 * 
 * 2. NUMBER OF 1 BITS (HAMMING WEIGHT)
 * Problem: Count the number of '1' bits in an integer.
 * Trick (Brian Kernighan's Algorithm): `n = n & (n - 1)`.
 * Result: This operation flips the LOWEST set '1' bit in a number to '0'. 
 * If you loop this operation and count how many times you can do it before the number 
 * becomes 0, you get the exact count of '1' bits in O(K) time, where K is the number of 1s!
 * 
 * 3. POWER SET REFERENCE
 * See `algorithms.recursion.SubsetGeneration.java` for the Bitmasking implementation 
 * of finding all subsets of an array.
 */
public class BitTricks {

    public static int singleNumber(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result ^= num; // XOR operation
        }
        return result;
    }

    public static int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            // Brian Kernighan's trick: clears the lowest set bit
            n = n & (n - 1); 
            count++;
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println("--- BIT MANIPULATION DEMO ---");
        
        int[] nums = {4, 1, 2, 1, 2};
        System.out.println("Array: [4, 1, 2, 1, 2]");
        System.out.println("Single Number: " + singleNumber(nums)); // Expected: 4
        
        int n = 11; // Binary: 1011
        System.out.println("\nNumber: " + n + " (Binary: " + Integer.toBinaryString(n) + ")");
        System.out.println("Hamming Weight (1 bits): " + hammingWeight(n)); // Expected: 3
    }
}
