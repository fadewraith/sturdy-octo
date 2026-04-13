package educativetutorials.codingpatterns.threeslidingwindow.medium;

import java.util.Arrays;

public class MinSizeSubarraySum {

    private static int minSubArrayLen(int target, int[] nums) {
        // Initializing windowSize to a max number
        int windowSize = Integer.MAX_VALUE;
        int currSubArrSize;
        // Initialize start pointer to 0 and sum to 0
        int start = 0;
        int sum = 0;

        // Iterate over the input array
        for(int i = 0; i < nums.length; i++) {
            sum += nums[i];
            // check if we can remove elements from the start of the subarray
             while(sum >= target) {
                 // Finding size of current subarray
                 currSubArrSize = (i + 1) - start;
                 windowSize = Math.min(windowSize, currSubArrSize);
                 sum -= nums[start];
                 start += 1;
             }
        }

        if(windowSize != Integer.MAX_VALUE) {
            return windowSize;
        }
        return 0;
    }

    public static void main(String[] args) {
        int[] target = {7, 4, 11, 10, 5, 15};
        int[][] inputArr = {
                {2, 3, 1, 2, 4, 3},
                {1, 4, 4},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 2, 3, 4},
                {1, 2, 1, 3},
                {5, 4, 9, 8, 11, 3, 7, 12, 15, 44}
        };
        for (int i = 0; i < target.length; i++) {
            int windowSize = minSubArrayLen(target[i], inputArr[i]);
            System.out.print((i + 1) + ".\tInput array: " + Arrays.toString(inputArr[i]));
            System.out.print("\n\tTarget: " + target[i]);
            System.out.println("\n\tMinimum Length of Subarray: " + windowSize);
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
}


/**
 * Problem Statement
 *
 * Given an array of positive integers, nums, and a positive integer, target,
 * find the minimum length of a contiguous subarray whose sum is greater than
 * or equal to the target. If no such subarray is found, return 0.
 *
 * Constraints:
 * - 1 <= target <= 10^4
 * - 1 <= nums.length <= 10^3
 * - 1 <= nums[i] <= 10^3
 *
 * --------------------------------------------------
 * Solution
 *
 * The idea is to traverse the array using a sliding window, calculate the sum
 * of elements in it, and compare the sum with the target value. If the sum is
 * greater than or equal to the target value, store the size of this window.
 * Repeat this process to find the minimum size subarray.
 *
 * --------------------------------------------------
 * Algorithm Steps
 *
 * 1. Initialize:
 *    - windowSize = infinity (to store minimum subarray size)
 *    - sum = 0
 *    - start = 0, end = 0
 *
 * 2. Slide the window:
 *    - Increment end and add nums[end] to sum.
 *
 * 3. While sum >= target:
 *    - Update windowSize = min(windowSize, end - start + 1)
 *    - Subtract nums[start] from sum
 *    - Increment start
 *
 * 4. Repeat until the array is fully traversed.
 *
 * 5. If windowSize is still infinity, return 0.
 *    Otherwise, return windowSize.
 *
 * --------------------------------------------------
 * Time Complexity
 *
 * - O(n), since each element is processed at most twice
 *   (once by end pointer and once by start pointer).
 *
 * --------------------------------------------------
 * Space Complexity
 *
 * - O(1), since no extra space is used.
 */