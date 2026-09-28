package algorithms.twopointers;

import java.util.Arrays;

/**
 * TWO POINTERS: OPPOSITE-DIRECTION
 * 
 * WHAT IT IS:
 * An algorithmic pattern where two pointers start at opposite ends of a linear 
 * data structure (usually an array) and move inward toward each other until they meet.
 * 
 * WHEN TO USE THIS:
 * - When searching for pairs (or triplets) in a SORTED array that meet a condition (e.g., Target Sum).
 * - When reversing an array or string in-place.
 * - When calculating bounds (e.g., Container With Most Water).
 * 
 * DATA STRUCTURE:
 * Arrays or Strings. (The array MUST be sorted for pair-sum problems).
 * 
 * COMBINATION USAGE:
 * - 3Sum = Sorting + Opposite-Direction Two Pointers. 
 *   (You iterate a base index 'i', and then run Opposite-Direction pointers on the remaining array).
 * 
 * COMPLEXITY:
 * Time: O(N) because each pointer traverses the array at most once.
 * Space: O(1) strictly in-place.
 */
public class OppositeDirection {

    /**
     * Problem 1: Pair Sum (Two Sum on a Sorted Array)
     * Finds if any two numbers in a sorted array add up to exactly the target.
     */
    public static boolean hasPairSum(int[] sortedArr, int target) {
        int left = 0;
        int right = sortedArr.length - 1;

        while (left < right) {
            int currentSum = sortedArr[left] + sortedArr[right];

            if (currentSum == target) {
                return true; // Found the pair!
            } else if (currentSum < target) {
                left++; // We need a bigger sum, so move the left pointer up
            } else {
                right--; // We need a smaller sum, so move the right pointer down
            }
        }
        return false;
    }

    /**
     * Problem 2: Reverse an Array In-Place
     */
    public static void reverseArray(int[] arr) {
        int left = 0;
        int right = arr.length - 1;

        while (left < right) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            
            left++;
            right--;
        }
    }

    /**
     * Problem 3: Container With Most Water
     * Given an array of heights, find two lines that form a container holding the most water.
     */
    public static int maxArea(int[] heights) {
        int left = 0;
        int right = heights.length - 1;
        int maxArea = 0;

        while (left < right) {
            int width = right - left;
            int currentHeight = Math.min(heights[left], heights[right]);
            int currentArea = width * currentHeight;
            
            if (currentArea > maxArea) {
                maxArea = currentArea;
            }

            // Move the pointer pointing to the shorter line inward, 
            // hoping to find a taller line to compensate for the lost width.
            if (heights[left] < heights[right]) {
                left++;
            } else {
                right--;
            }
        }
        return maxArea;
    }

    public static void main(String[] args) {
        System.out.println("--- OPPOSITE-DIRECTION TWO POINTERS DEMO ---");
        
        int[] sortedArr = {1, 2, 4, 6, 8, 9, 14, 15};
        System.out.println("Sorted Array: " + Arrays.toString(sortedArr));
        System.out.println("Has pair sum 13? (4+9) -> " + hasPairSum(sortedArr, 13)); // true
        System.out.println("Has pair sum 20? -> " + hasPairSum(sortedArr, 20)); // false
        
        int[] reverseMe = {1, 2, 3, 4, 5};
        reverseArray(reverseMe);
        System.out.println("\nReversed Array: " + Arrays.toString(reverseMe)); // [5, 4, 3, 2, 1]
        
        int[] heights = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        System.out.println("\nContainer Heights: " + Arrays.toString(heights));
        System.out.println("Max Water Area: " + maxArea(heights)); // 49
    }
}
