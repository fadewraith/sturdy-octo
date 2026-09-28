package algorithms.recursion;

import java.util.Arrays;

/**
 * PERMUTATIONS (Backtracking)
 * 
 * WHAT IT IS:
 * Finding all possible rearrangements of a set of items.
 * 
 * WHEN TO USE BACKTRACKING GENERALLY:
 * - Problems requiring exploring ALL combinations/paths with the ability to "undo" a choice.
 * - Key signals: "find all ways", "generate all", "all possible valid configurations".
 * 
 * STRATEGY:
 * We iterate through the array. We "Choose" an element by swapping it into the 
 * current fixed position. We "Explore" by recursively calling the function on the 
 * remaining elements. Then we "Un-Choose" (Backtrack) by swapping it BACK to its 
 * original position to explore the next branch!
 * 
 * COMPLEXITY:
 * Time: O(N * N!) (There are N! permutations, and printing/copying takes O(N))
 * Space: O(N) for the recursion stack
 */
public class Permutations {

    // Simple custom list for string building to avoid java.util
    public static void generatePermutations(char[] arr, int index) {
        // Base case: If we've locked in a choice for every position, print it!
        if (index == arr.length - 1) {
            System.out.println(Arrays.toString(arr));
            return;
        }

        for (int i = index; i < arr.length; i++) {
            // 1. CHOOSE
            swap(arr, index, i);
            
            // 2. EXPLORE
            generatePermutations(arr, index + 1);
            
            // 3. UN-CHOOSE (Backtrack to restore the array for the next loop iteration)
            swap(arr, index, i);
        }
    }

    private static void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        System.out.println("--- PERMUTATIONS DEMO ---");
        char[] chars = {'A', 'B', 'C'};
        System.out.println("All permutations of [A, B, C]:");
        generatePermutations(chars, 0);
        // Expected: 6 outputs (3!)
    }
}
