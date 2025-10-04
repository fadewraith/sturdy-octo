package practiceQuestions.algomapio.slidingwindow.medium;

import java.sql.Time;

public class MinimumSizeSubarraySum {

    // Time: O(n)
    // Space: O(1)
    public int minSubArrayLen(int target, int[] nums) {
        int minLength = Integer.MAX_VALUE;
        int sum = 0;
        int l = 0;

        for (int r = 0; r < nums.length; r++) {
            sum += nums[r];
            while (sum >= target) {
                minLength = Math.min(minLength, r - l + 1);
                sum -= nums[l];
                l++;
            }
        }

        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }
}


/**
 * Detailed Explanation
 * Understanding the Problem: Minimum Size Subarray Sum
 * The “Minimum Size Subarray Sum” problem challenges you to find the length of the smallest contiguous subarray in a given array of positive integers such that the sum of its elements is greater than or equal to a specified target value. If no such subarray exists, the function should return zero. This problem emphasizes not just identifying a valid range of elements, but doing so with the shortest possible window.
 *
 * For instance, given an array [2,3,1,2,4,3] and a target sum of 7, the shortest subarray with a sum equal to or greater than 7 is [4,3], which has a length of 2. A brute-force solution would involve checking all possible subarrays, but this approach becomes inefficient for larger inputs.
 *
 * Optimal Approach: Sliding Window Technique
 * A more efficient solution leverages the sliding window strategy. The idea is to maintain a window that expands to the right as long as the current sum is less than the target, and contracts from the left once the sum meets or exceeds the target. This allows us to dynamically adjust the window size in a single pass through the array.
 *
 * To implement this, we initialize two pointers: one at the beginning of the window and one that iterates through the array. As each new element is added to the window, we update the running sum. When this sum becomes greater than or equal to the target, we check whether the current window is smaller than the previously recorded shortest length. If it is, we update the minimum. After checking, we attempt to shrink the window from the left to see if a smaller valid window can be formed.
 *
 * This method ensures that we examine each element only once or twice at most—once when expanding the window and potentially again when contracting it—resulting in excellent time efficiency.
 *
 * Time and Space Complexity
 * The sliding window solution runs in linear time, or O(n), where n is the number of elements in the input array. This is because each element is added and removed from the window at most once. The space complexity is constant, or O(1), as we only use a few variables to maintain state, regardless of the input size.
 *
 * Edge Cases to Consider
 * It’s important to handle cases where no subarray can meet the target sum. For example, if the array consists of small values that never add up to the target, the function should correctly return zero. Additionally, single-element arrays should be handled gracefully, especially when the only value is either below or equal to the target.
 *
 * Conclusion
 * The “Minimum Size Subarray Sum” problem is a textbook example of how the sliding window technique can be used to optimize brute-force solutions. By intelligently growing and shrinking a window while maintaining the sum of elements inside it, we achieve a solution that is both efficient and easy to understand. This pattern is broadly applicable in many real-world problems involving cumulative totals and range-based queries.
 * */