package practiceQuestions.algomapio.slidingwindow.easy;

public class MaximumAverageSubarrayOne {

    /**
     * Brute Force
     * Understand the problem: Find the contiguous subarray of length k in an array of integers that has the maximum average value.
     * Define a helper function to compute the average of a subarray by summing its elements and dividing by its length.
     * Initialize max_avg to the average of the first k elements to handle cases with negative numbers.
     * Iterate through each possible subarray of length k from index 0 to n-k (where n is the array length).
     * Compute the average of the current subarray and update max_avg if the current average is higher.
     * Return max_avg as the maximum average value.
     * */

    /**
     *  # Note, setting max_avg to any constant numeric value
     *         # here is incorrect!!!
     *         # Say 0, if nums = [-1, -2, -3, -4], k = 2,
     *         # the output would be incorrect
     * */

    private static double bruteForce(int[] nums, int k) {

        double maxAvg = 0;
        // setting maxAvg to any constant numeric value
        // here is incorrect!!!
        // so initialize it with some values from the input
        for(int i = 0; i < k; i++) {
            maxAvg += nums[i];
        }
        maxAvg /= k;

        for(int i = 0; i <= nums.length - k; i++) {
            double avg = 0;
            for(int j = i; j < i + k; j++) {
                avg += nums[j];
            }
            avg /= k;
            maxAvg = Math.max(maxAvg, avg); // avg is O(k)
        }

        return maxAvg;

    }

    /**
     * Why the Brute-Force Solution is Inefficient
     * The brute-force solution computes the sum for each subarray of length k, leading to:
     *
     * Time Complexity: O(n * k), where n is the length of the array, as it iterates through n-k+1 subarrays and computes each sum in O(k).
     * Space Complexity: O(1), as it only uses a constant amount of extra space.
     * Performance Issue: Repeatedly summing k elements for each subarray is inefficient, especially for large k, as it recalculates sums unnecessarily.
     * Optimal Solution
     * The optimal solution uses a sliding window to compute subarray sums efficiently, achieving O(n) time complexity:
     *
     * Compute the sum of the first k elements and initialize max_avg as this sum divided by k.
     * Iterate from index k to n-1, sliding the window by adding the next element and subtracting the element k positions before.
     * Compute the average of the current window and update max_avg if the current average is higher.
     * Return max_avg as the maximum average value.
     * */

    private static double optimal(int[] nums, int k) {
        // Time Complexity: O(n)
        // Space Complexity: O(1)

        int n = nums.length;
        double curSum = 0;

        for (int i = 0; i < k; i++) {
            curSum += nums[i];
        }

        double maxAvg = curSum / k;

        for (int i = k; i < n; i++) {
            curSum += nums[i];
            curSum -= nums[i - k];

            double avg = curSum / k;
            maxAvg = Math.max(maxAvg, avg);
        }

        return maxAvg;

    }
}


/**
 * Detailed Explanation
 * Understanding the Problem: Maximum Average Subarray I
 * The “Maximum Average Subarray I” problem asks us to find the maximum average value among all contiguous subarrays of a fixed length k in a given array of integers. This means we need to evaluate every possible sequence of k consecutive elements and determine which one has the highest average. The return value should be the average itself, not the subarray.
 *
 * For example, in the input nums = [1, 12, -5, -6, 50, 3] and k = 4, there are several subarrays of length 4: [1, 12, -5, -6], [12, -5, -6, 50], and [-5, -6, 50, 3]. Among these, the last one has the highest average of 10.5, so that is the result.
 *
 * Why This Problem Matters
 * This type of problem is a great introduction to the sliding window technique, which is commonly used in problems involving fixed-size sequences in arrays or strings. It also teaches how to optimize time complexity by avoiding repeated work.
 *
 * Optimal Approach: Sliding Window Technique
 * Instead of calculating the sum of each subarray from scratch, which would involve summing k elements every time, the sliding window approach keeps track of the current total of the window and adjusts it as the window moves forward. This way, we avoid redundant computations and achieve linear time efficiency.
 *
 * To begin, we compute the sum of the first k elements. This serves as our initial window. We then initialize a variable to track the maximum sum seen so far, starting with this initial sum. Next, we move the window forward one element at a time by adding the next element in the array and subtracting the first element of the previous window. At each step, we update the maximum sum if the new window's total is greater than the current maximum.
 *
 * After sliding through the entire array, the largest sum found across all windows is divided by k to return the maximum average. Since each element is only added and removed once from the window, the algorithm runs in linear time relative to the length of the array.
 *
 * Time and Space Complexity
 * The time complexity of the optimized solution is O(n), where n is the number of elements in the array. This is because we only traverse the array once, updating the window sum in constant time. The space complexity is O(1) since we only store a few variables regardless of the input size.
 *
 * Conclusion
 * The “Maximum Average Subarray I” problem is a perfect demonstration of how a straightforward brute-force approach can be transformed into a highly efficient solution using the sliding window technique. By avoiding unnecessary recalculations and reusing previous results, we achieve optimal performance without sacrificing accuracy. This method is widely applicable in real-world scenarios such as signal processing, moving averages in finance, and performance analysis over time windows.
 * */