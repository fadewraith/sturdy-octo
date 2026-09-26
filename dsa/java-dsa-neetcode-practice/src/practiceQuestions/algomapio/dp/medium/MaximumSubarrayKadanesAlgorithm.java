package practiceQuestions.algomapio.dp.medium;

public class MaximumSubarrayKadanesAlgorithm {

    /**
     * Step-by-Step Thought Process
     * Understand the problem: Find the contiguous subarray with the largest sum in an array of integers.
     * Initialize max_sum to negative infinity to track the maximum sum found.
     * Initialize curr_sum to 0 to track the sum of the current subarray.
     * Iterate through each number in the array.
     * Add the current number to curr_sum.
     * Update max_sum to the maximum of max_sum and curr_sum.
     * If curr_sum becomes negative, reset curr_sum to 0 to start a new subarray.
     * Return max_sum as the result.
     * */

    public int maxSubArray(int[] nums) {
        int maxSum = Integer.MIN_VALUE;
        int currSum = 0;

        for (int num : nums) {
            currSum += num;
            maxSum = Math.max(maxSum, currSum);

            if (currSum < 0) {
                currSum = 0;
            }
        }

        return maxSum;
    }
}

/**
 * Detailed Explanation
 * Problem Overview: Maximum Subarray
 * The Maximum Subarray problem is one of the most well-known dynamic programming challenges in algorithm interviews and competitive coding. Given an array of integers, the task is to find the contiguous subarray with the highest possible sum. This problem is often encountered in real-world scenarios such as analyzing profit fluctuations, temperature variations, or signal processing.
 *
 * Understanding the Strategy: Kadane's Algorithm
 * Kadane's Algorithm is an efficient method to solve the Maximum Subarray problem in linear time. The core idea is to iterate through the array while maintaining two variables: current subarray sum and maximum sum found so far. At each step, we determine whether to extend the existing subarray or start a new one beginning at the current element. This decision is made by comparing the current number against the sum of the current number and the ongoing subarray.
 *
 * This strategy avoids redundant computations and allows you to find the optimal solution in a single pass, making it ideal for performance-critical applications.
 *
 * Algorithm Walkthrough
 * Start by initializing two variables: max_sum to negative infinity and curr_sum to 0. Then iterate through the array:
 *
 * At each index, update curr_sum to the maximum of the current element alone or the sum of the current element and the existing curr_sum.
 * Update max_sum to the maximum of itself and the current curr_sum.
 * This technique ensures that the best possible subarray sum is always recorded while maintaining constant space.
 *
 * Time and Space Complexity
 * Time Complexity: O(n), where n is the number of elements in the input array. The algorithm completes in a single pass.
 * Space Complexity: O(1), as it uses only two variables regardless of the input size.
 * Conclusion
 * Kadane’s Algorithm is the optimal solution for the Maximum Subarray problem, combining simplicity with efficiency. It’s a must-know pattern for technical interviews and a valuable tool in any developer’s toolkit. Mastering this approach will also help with solving similar dynamic programming problems involving sliding windows, sequences, and segment-based calculations.
 * */