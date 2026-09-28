package algorithms.misc;

import java.util.Arrays;

/**
 * PREFIX SUM & DIFFERENCE ARRAYS
 * 
 * 1. PREFIX SUM
 * WHAT IT IS: Precomputing a cumulative sum array so you can answer "What is the 
 * sum of elements between index L and R?" in O(1) time!
 * Formula: Sum(L, R) = prefix[R] - prefix[L - 1]
 * 
 * 2. DIFFERENCE ARRAY
 * WHAT IT IS: A trick to perform range UPDATES in O(1) time!
 * E.g., "Add 5 to all elements from index L to R".
 * Formula: Create an array `diff`. Add 5 to `diff[L]`, and SUBTRACT 5 from `diff[R + 1]`.
 * When you later rebuild the array by calculating the prefix sum of `diff`, the +5 
 * propagates through the range and naturally stops exactly after R!
 */
public class PrefixSumAndDifference {

    public static void main(String[] args) {
        System.out.println("--- PREFIX SUM & DIFFERENCE ARRAYS DEMO ---\n");
        
        // --- 1. PREFIX SUM DEMO ---
        int[] arr = {2, 4, 6, 8, 10}; // Size 5
        int[] prefix = new int[arr.length + 1]; // Size 6 (1-indexed for easier math)
        
        for (int i = 0; i < arr.length; i++) {
            prefix[i + 1] = prefix[i] + arr[i];
        }
        
        // Query: Sum of elements from index 1 to 3 ([4, 6, 8]) -> Expected 18
        int L = 1, R = 3;
        int sum = prefix[R + 1] - prefix[L];
        System.out.println("Original Array: " + Arrays.toString(arr));
        System.out.println("Sum from index " + L + " to " + R + ": " + sum);


        // --- 2. DIFFERENCE ARRAY DEMO ---
        System.out.println("\n--- DIFFERENCE ARRAY DEMO ---");
        int[] diff = new int[arr.length + 1]; // Extra space for R+1 boundary
        
        // Query: Add +5 to range [1, 3]
        int addL = 1, addR = 3, val = 5;
        diff[addL] += val;
        diff[addR + 1] -= val;
        
        // Reconstruct the array
        int currentDiff = 0;
        int[] updatedArr = new int[arr.length];
        
        for (int i = 0; i < arr.length; i++) {
            currentDiff += diff[i];
            updatedArr[i] = arr[i] + currentDiff;
        }
        
        System.out.println("Adding " + val + " to range [" + addL + ", " + addR + "]...");
        System.out.println("Updated Array: " + Arrays.toString(updatedArr));
        // Expected: [2, 9, 11, 13, 10]
    }
}
