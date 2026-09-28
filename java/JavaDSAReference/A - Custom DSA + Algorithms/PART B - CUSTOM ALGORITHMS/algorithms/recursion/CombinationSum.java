package algorithms.recursion;

import java.util.Arrays;

/**
 * COMBINATION SUM
 * 
 * WHAT IT IS:
 * Finding all combinations of elements that sum up to a specific target.
 * 
 * COMBINATION USAGE:
 * - Backtracking + Pruning
 * 
 * STRATEGY:
 * - We sort the array first. This allows for crucial PRUNING!
 * - As we recursively build a combination, we track the `currentSum`.
 * - If `currentSum > target`, we immediately stop exploring this branch (Pruning).
 * - Because the array is sorted, if one element exceeds the target, all elements 
 *   to its right will also exceed it, allowing us to break out of the loop instantly!
 * 
 * VARIATIONS:
 * - With Reuse: Pass `i` into the recursive call so the same number can be chosen again.
 * - Without Reuse: Pass `i + 1` into the recursive call so a number is only used once.
 * 
 * COMPLEXITY:
 * Time: O(2^Target) loosely, but heavily optimized by pruning.
 * Space: O(Target) for recursion stack.
 */
public class CombinationSum {

    /**
     * WITH REUSE of elements.
     * Note: We use arrays to simulate lists since we can't use java.util.ArrayList.
     */
    public static void findCombinations(int[] arr, int target) {
        Arrays.sort(arr); // Crucial for Pruning!
        
        int[] currentCombo = new int[target + 1]; // Max possible length if target is reached by adding 1s
        backtrack(arr, target, 0, currentCombo, 0, 0);
    }

    private static void backtrack(int[] arr, int target, int currentSum, int[] currentCombo, int comboSize, int startIndex) {
        // Base case: Hit the exact target!
        if (currentSum == target) {
            printCombo(currentCombo, comboSize);
            return;
        }

        for (int i = startIndex; i < arr.length; i++) {
            // PRUNING: Because array is sorted, if this element busts the target, 
            // all subsequent elements will ALSO bust the target. Stop entirely!
            if (currentSum + arr[i] > target) {
                break; 
            }

            // Choose
            currentCombo[comboSize] = arr[i];
            
            // Explore
            // Notice we pass `i` instead of `i+1`. This allows REUSE of the same element!
            // (If the problem forbade reuse, we would pass `i+1`).
            backtrack(arr, target, currentSum + arr[i], currentCombo, comboSize + 1, i);
            
            // Un-Choose (Handled implicitly by overwriting the array index in the next loop)
        }
    }

    private static void printCombo(int[] combo, int size) {
        System.out.print("[");
        for (int i = 0; i < size; i++) {
            System.out.print(combo[i] + (i == size - 1 ? "" : ", "));
        }
        System.out.println("]");
    }

    public static void main(String[] args) {
        System.out.println("--- COMBINATION SUM (WITH REUSE) DEMO ---");
        
        int[] candidates = {2, 3, 6, 7};
        int target = 7;
        
        System.out.println("Candidates: " + Arrays.toString(candidates));
        System.out.println("Target: " + target);
        System.out.println("Valid Combinations:");
        
        findCombinations(candidates, target);
        // Expected: [2, 2, 3] and [7]
    }
}
