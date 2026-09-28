package algorithms.misc;

import java.util.Arrays;

/**
 * KADANE'S ALGORITHM
 * 
 * WHAT IT IS:
 * Finds the maximum contiguous subarray sum in an array of integers (which may 
 * contain negative numbers).
 * 
 * COMBINATION USAGE:
 * - Can be viewed as a form of Dynamic Programming or Sliding Window.
 * 
 * STRATEGY:
 * We iterate through the array, maintaining a `currentSum`.
 * If `currentSum` ever drops below 0, it is actively dragging down any future sums! 
 * So, we instantly reset `currentSum` to 0.
 * At every step, we update our `maxSum` with the `currentSum`.
 * 
 * COMPLEXITY:
 * Time: O(N) single pass.
 * Space: O(1) in-place.
 */
public class KadanesAlgorithm {

    public static int maxSubArraySum(int[] arr) {
        int maxSum = Integer.MIN_VALUE;
        int currentSum = 0;

        for (int i = 0; i < arr.length; i++) {
            currentSum += arr[i];
            
            // Update global max
            if (currentSum > maxSum) {
                maxSum = currentSum;
            }
            
            // If current sum drops below 0, discard the entire prefix!
            if (currentSum < 0) {
                currentSum = 0;
            }
        }
        return maxSum;
    }

    public static void main(String[] args) {
        System.out.println("--- KADANE'S ALGORITHM DEMO ---");
        int[] arr = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        
        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("Maximum contiguous sum is: " + maxSubArraySum(arr));
        // Expected: 6 (The subarray [4, -1, 2, 1] sums to 6)
    }
}
