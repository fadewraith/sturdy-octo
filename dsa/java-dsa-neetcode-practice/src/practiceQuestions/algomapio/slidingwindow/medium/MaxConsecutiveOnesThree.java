package practiceQuestions.algomapio.slidingwindow.medium;

public class MaxConsecutiveOnesThree {

    /**
     * Brute Force
     * Understand the problem: Find the maximum number of consecutive 1s in a binary array if at most k zeros can be flipped to 1.
     * Initialize two pointers, l and r, both starting at 0.
     * Maintain a counter num_zeros to track the number of zeros in the current window.
     * Increment r and if nums[r] is 0, increment num_zeros.
     * If num_zeros exceeds k, increment l until num_zeros is at most k.
     * Track the maximum window size during this process.
     * */

    private static int bruteForce(int[] nums, int k) {
        int maxLength = 0;

        // iterate over all sorting points
        for(int i = 0; i < nums.length; i++) {
            // iterate over all possible subarrays starting from i
            for(int j = i; j < nums.length; j++) {
                int zeroCount = 0;
                for(int c = i; c <= j; ++c) if(nums[c] == 0) ++zeroCount;

                // update maxLength if this array is valid
                if(zeroCount <= k) maxLength = Math.max(maxLength, j - i + 1);
            }
        }

        return maxLength;

        /**
         * Why the Brute-Force Solution is Inefficient
         * This sliding window approach is actually optimal, but a true brute-force solution would try all subarrays and count the number of flips, leading to:
         *
         * Time Complexity: O(n²), as it considers all subarrays.
         * Space Complexity: O(1), assuming in-place checks.
         * Performance Issue: Inefficient for large input arrays due to nested iterations.
         * */
    }

    /**
     * Optimal Solution
     * We use a sliding window to maintain at most k zeros in the window:
     *
     * Initialize pointers l = 0 and max_w = 0.
     * Iterate r from 0 to n-1:
     *     - If nums[r] == 0, increment num_zeros.
     *     - While num_zeros > k, move l forward and adjust num_zeros.
     * Calculate window size w = r - l + 1 and update max_w.
     * Return max_w at the end.
     * */

    private static int optimal(int[] nums, int k) {
        int maxLength = 0, zeroCount = 0, left = 0;

        for(int right = 0; right < nums.length; right++) {
            if(nums[right] == 0) zeroCount++;

            while(zeroCount > k) {
                if(nums[left] == 0) zeroCount--;
                left++;
            }

            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }

    public static void main(String[] args) {
        // write test cases using bruteForce and optimal solutions and compare time and space complexity
        int[] nums = {1,1,0,0,1,1,1,0,1};
        int k = 2;
//        System.out.println(bruteForce(nums, k));
//        System.out.println(optimal(nums, k));

        // calculate the time each method takes
        long startTime = System.currentTimeMillis();
        bruteForce(nums, k);
        long endTime = System.currentTimeMillis();
        System.out.println("Brute Force Time: " + (endTime - startTime));

        startTime = System.currentTimeMillis();
        optimal(nums, k);
        endTime = System.currentTimeMillis();
        System.out.println("Optimal Time: " + (endTime - startTime));
    }

}


/**
 * Detailed Explanation
 * Understanding the Problem: Max Consecutive Ones III
 * The “Max Consecutive Ones III” problem challenges us to find the length of the longest contiguous subarray of 1s in a binary array if we are allowed to flip at most k zeros to 1s. This means that we can turn up to k zeros into ones, and we must determine the maximum length of such a sequence in the given array. It’s a classic problem involving the sliding window technique and decision-making based on dynamic window properties.
 *
 * For example, if the array is [1,1,0,0,1,1,1,0,1] and k = 2, we can flip two zeros to ones and get a window like [1,1,1,1,1,1,1], resulting in a maximum length of 7. The key is to figure out where these flips should occur to extend the window as much as possible.
 *
 * Optimal Approach: Sliding Window with Zero Counting
 * The optimal way to solve this problem is to use the sliding window pattern. We maintain a window with two pointers: a left pointer that marks the beginning of the window and a right pointer that expands the window one element at a time. As we move the right pointer forward through the array, we count how many zeros are in the current window. Every time we encounter a zero, we increase a counter tracking the number of flips used so far.
 *
 * If the number of zeros in the window exceeds k, this means we have used up our quota of allowed flips and need to shrink the window. To do that, we move the left pointer forward until the number of zeros in the window is again less than or equal to k. Each time we do this, we maintain a record of the maximum window size encountered that still respects the constraint of flipping at most k zeros.
 *
 * By the time we reach the end of the array, the maximum window size recorded gives us the length of the longest subarray that meets the conditions. This method is highly efficient and avoids re-evaluating the same elements multiple times.
 *
 * Time and Space Complexity
 * The time complexity of this approach is linear, or O(n), where n is the length of the input array. This is because each element is processed at most twice—once when the right pointer advances, and possibly again when the left pointer advances. The space complexity is constant, or O(1), since the solution uses only a few integer variables to maintain the state of the window.
 *
 * Conclusion
 * The “Max Consecutive Ones III” problem elegantly demonstrates how a simple sliding window with careful state tracking can optimize a problem that might otherwise require nested loops. This technique is particularly useful in streaming and online data scenarios, where we need to compute results on the fly without storing or reprocessing large chunks of data. Mastering this pattern opens the door to solving many other real-time windowed computation problems efficiently.
 * */