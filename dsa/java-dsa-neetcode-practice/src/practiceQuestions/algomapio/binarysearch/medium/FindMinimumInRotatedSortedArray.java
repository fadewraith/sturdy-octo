package practiceQuestions.algomapio.binarysearch.medium;

public class FindMinimumInRotatedSortedArray {

    /**
     * Brute Force
     * Understand the problem: Find the minimum element in a sorted array that has been rotated an unknown number of times.
     * Initialize min to infinity to track the smallest number.
     * Iterate through each number in the array.
     * If the current number is less than min, update min to the current number.
     * Return min after the loop.
     * */

    private static int bruteForce(int[] nums) {
        // O(n)
        // O(1)
        int min = Integer.MAX_VALUE;
        for(int num : nums) {
            if(num < min) {
                min = num;
            }
        }
        return min;
    }

    /**
     * Optimal Solution
     * The optimal solution uses binary search to find the minimum in O(log n) time complexity:
     *
     * Initialize left pointer to 0 and right pointer to the array length minus 1.
     * While left is less than right, compute the middle index as the average of left and right (integer division).
     * If the middle element is greater than the rightmost element, the minimum lies in the right half, so adjust left to middle + 1.
     * Otherwise, the minimum lies in the left half including the middle, so adjust right to middle.
     * Return the element at the left index after the loop.
     * */

    private static int optimal(int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        while(left < right) {
            int mid = left + (right - left) / 2;

            if(nums[mid] > nums[right]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return nums[left];
    }
}


/**
 * Detailed Explanation
 * Understanding the Problem: Find Minimum in Rotated Sorted Array
 * The “Find Minimum in Rotated Sorted Array” problem involves identifying the smallest element in an array that was initially sorted in ascending order but then rotated at some pivot unknown to you beforehand. The array contains no duplicate elements and is guaranteed to have been rotated at least once.
 *
 * Example:
 *
 * Input: nums = [4,5,6,7,0,1,2] → Output: 0
 * Input: nums = [3,4,5,1,2] → Output: 1
 * Why This Problem Matters
 * This problem is a classic application of binary search under modified conditions. It teaches you to recognize how the sorted property can still be leveraged in rotated arrays to achieve logarithmic time complexity. Such problems are highly relevant in technical interviews and real-world scenarios involving circular buffers, search optimization, and time series analysis.
 *
 * Optimal Approach: Binary Search in a Rotated Array
 * The goal is to find the index of the smallest element using binary search in O(log n) time. Since the array is sorted and rotated, the minimum element is the only "pivot point" where the order breaks. Comparing the middle element with the rightmost element helps decide which side the minimum lies on.
 *
 * Steps:
 * Initialize two pointers: left = 0 and right = nums.length - 1.
 * While left < right:
 * Compute mid = Math.floor((left + right) / 2).
 * If nums[mid] > nums[right], the minimum must be in the right half → set left = mid + 1.
 * Otherwise, the minimum is in the left half (including mid) → set right = mid.
 * Once the loop exits, left will point to the minimum element.
 * Return nums[left].
 * Example Walkthrough
 * Input: nums = [4,5,6,7,0,1,2]
 *
 * left = 0, right = 6 → mid = 3 → nums[3] = 7, nums[6] = 2 → 7 > 2 → left = 4
 * left = 4, right = 6 → mid = 5 → nums[5] = 1, nums[6] = 2 → 1 < 2 → right = 5
 * left = 4, right = 5 → mid = 4 → nums[4] = 0, nums[5] = 1 → 0 < 1 → right = 4
 * left = right = 4 → return nums[4] = 0
 * Time and Space Complexity
 * Time Complexity: O(log n), where n is the number of elements in the array.
 * Space Complexity: O(1), since only constant space is used.
 *
 * Edge Cases to Consider
 * Array is already sorted (not rotated) → minimum is at index 0
 * Minimum is at the last index → properly handles the right half
 * Array has only one element → return that element
 * Conclusion
 * The “Find Minimum in Rotated Sorted Array” problem is a smart application of binary search that demonstrates how to adapt core techniques to new scenarios. Recognizing patterns and utilizing comparisons between midpoints and boundaries allows for an efficient and elegant solution.
 * */