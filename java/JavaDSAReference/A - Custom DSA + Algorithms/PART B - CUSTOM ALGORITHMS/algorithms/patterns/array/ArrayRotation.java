package algorithms.patterns.array;

import java.util.Arrays;

/**
 * ARRAY ROTATION (Cyclic, In-Place)
 * 
 * WHAT IT IS:
 * Rotating an array to the right by `k` steps.
 * Example: arr = [1,2,3,4,5,6,7], k = 3  ->  [5,6,7,1,2,3,4]
 * 
 * VARIATIONS:
 * 1. Extra Array: Simple. output[(i + k) % n] = input[i]. Uses O(N) space.
 * 2. Juggling Algorithm: Uses GCD (Greatest Common Divisor) to move elements 
 *    in sets. In-place O(1) space, but complicated math.
 * 3. Reversal Algorithm (Implemented below): The most elegant and common interview 
 *    solution. In-place O(1) space, highly intuitive.
 * 
 * STRATEGY (Reversal Algorithm):
 * To rotate right by k:
 * 1. Reverse the entire array.
 * 2. Reverse the first k elements.
 * 3. Reverse the rest of the array (n - k elements).
 * 
 * COMPLEXITY:
 * Time: O(N) (We reverse the array, visiting each element twice).
 * Space: O(1)
 */
public class ArrayRotation {

    public static void rotate(int[] nums, int k) {
        int n = nums.length;
        if (n == 0) return;
        
        k = k % n; // Ensure k is within bounds (e.g. rotating by n is same as rotating 0)
        
        // 1. Reverse the whole array
        reverse(nums, 0, n - 1);
        
        // 2. Reverse the first k elements
        reverse(nums, 0, k - 1);
        
        // 3. Reverse the remaining elements
        reverse(nums, k, n - 1);
    }

    private static void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }

    public static void main(String[] args) {
        System.out.println("--- ARRAY ROTATION (REVERSAL ALGO) DEMO ---");
        
        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        int k = 3;
        
        System.out.println("Original: " + Arrays.toString(arr));
        System.out.println("Rotate right by " + k + " steps...");
        rotate(arr, k);
        System.out.println("Rotated:  " + Arrays.toString(arr));
        // Expected: [5, 6, 7, 1, 2, 3, 4]
    }
}
