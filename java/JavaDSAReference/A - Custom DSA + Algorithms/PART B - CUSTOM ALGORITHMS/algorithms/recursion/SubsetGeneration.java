package algorithms.recursion;

/**
 * SUBSET GENERATION (Power Set)
 * 
 * WHAT IT IS:
 * Generating all 2^N possible subsets of an array.
 * 
 * VARIATIONS:
 * 1. Recursive Include/Exclude (Backtracking)
 * 2. Iterative Bitmasking
 * 
 * STRATEGY:
 * - Recursive: At every element, we make a branch where we INCLUDE it, and a 
 *   branch where we EXCLUDE it.
 * - Bitmasking: Since there are exactly 2^N subsets, we can count from 0 to (2^N - 1) 
 *   in binary. The binary representation perfectly maps to a subset! 
 *   (e.g., Number 5 is binary 101, which means Include index 0, Exclude index 1, Include index 2).
 * 
 * COMPLEXITY:
 * Time: O(N * 2^N) for both approaches.
 * Space: O(N) for recursive stack, O(1) for iterative bitmask logic.
 */
public class SubsetGeneration {

    /**
     * 1. RECURSIVE Include/Exclude approach
     */
    public static void generateSubsetsRecursive(int[] arr, int index, String currentSubset) {
        // Base case: we have made a decision for every element
        if (index == arr.length) {
            System.out.println("[" + currentSubset.trim() + "]");
            return;
        }

        // Branch 1: EXCLUDE the current element
        generateSubsetsRecursive(arr, index + 1, currentSubset);

        // Branch 2: INCLUDE the current element
        generateSubsetsRecursive(arr, index + 1, currentSubset + arr[index] + " ");
    }

    /**
     * 2. ITERATIVE Bitmask approach
     */
    public static void generateSubsetsBitmask(int[] arr) {
        int n = arr.length;
        int totalSubsets = 1 << n; // 2^n

        // Loop from 0 to 2^n - 1
        for (int mask = 0; mask < totalSubsets; mask++) {
            System.out.print("[");
            // Check every bit of the current mask
            for (int i = 0; i < n; i++) {
                // If the i-th bit is set to 1, include arr[i] in this subset
                if ((mask & (1 << i)) != 0) {
                    System.out.print(arr[i] + " ");
                }
            }
            System.out.println("]");
        }
    }

    public static void main(String[] args) {
        System.out.println("--- SUBSET GENERATION DEMO ---");
        int[] arr = {1, 2, 3};
        
        System.out.println("Recursive (Include/Exclude):");
        generateSubsetsRecursive(arr, 0, "");
        
        System.out.println("\nIterative (Bitmask):");
        generateSubsetsBitmask(arr);
        // Both expect 8 subsets (2^3)
    }
}
