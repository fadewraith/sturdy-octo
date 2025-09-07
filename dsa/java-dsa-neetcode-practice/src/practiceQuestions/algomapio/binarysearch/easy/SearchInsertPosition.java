package practiceQuestions.algomapio.binarysearch.easy;

public class SearchInsertPosition {

    /**
     * Step-by-Step Thought Process
     * Understand the problem: Find the index where a target value should be inserted in a sorted array to maintain order, or return the index if it exists.
     * Get the length n of the array nums.
     * Initialize two pointers, l to 0 and r to n-1, for binary search.
     * While l is less than or equal to r, compute the middle index m as (l + r) // 2.
     * If nums[m] is less than target, adjust l to m + 1 to search the right half.
     * If nums[m] is greater than target, adjust r to m - 1 to search the left half.
     * If nums[m] equals target, return m.
     * After the loop, check if nums[m] is less than target: if true, return m + 1; otherwise, return m as the insertion point.
     * */

    private static int solution(int[] nums, int target) {
        int left = 0, right = nums.length - 1;

        while(left <= right) {
            int mid = (left + right) / 2;

            if(nums[mid] < target) {
                left = mid + 1;
            } else if(nums[mid] > target) {
                right = mid - 1;
            } else {
                return mid;
            }
        }

        return left;
    }
}


/**
 * Detailed Explanation
 * Understanding the Problem: Search Insert Position
 * The “Search Insert Position” problem asks us to find the index at which a given target value should be inserted into a sorted array. If the target is already present, return its current index. If not, return the index where it should be inserted to maintain the array's sorted order.
 *
 * Example:
 *
 * Input: nums = [1, 3, 5, 6], target = 5 → Output: 2
 * Input: nums = [1, 3, 5, 6], target = 2 → Output: 1
 * Input: nums = [1, 3, 5, 6], target = 7 → Output: 4
 * Why This Problem Matters
 * This problem is a common application of binary search. It appears frequently in interviews and is useful in practical scenarios like autocomplete systems, ordered data insertion, and maintaining sorted arrays in real time. Understanding how to return an insertion point makes binary search more versatile.
 *
 * Optimal Approach: Binary Search for Insertion Point
 * Since the input array is sorted, we can use binary search to find the target or determine its correct insertion index. Unlike standard binary search that returns -1 if the target is not found, here we use the final low pointer to return the insertion position.
 *
 * Steps:
 * Initialize two pointers: left = 0 and right = nums.length - 1.
 * While left <= right:
 * Compute the middle index: mid = Math.floor((left + right) / 2).
 * If nums[mid] == target, return mid (target found).
 * If nums[mid] < target, move left to mid + 1.
 * If nums[mid] > target, move right to mid - 1.
 * Return left as the correct insertion position (either where the target exists or where it would be placed).
 * Example Walkthrough
 * Input: nums = [1, 3, 5, 6], target = 2
 *
 * left = 0, right = 3 → mid = 1 → nums[1] = 3
 * Since 2 < 3 → move right = 0
 * mid = 0 → nums[0] = 1
 * Since 2 > 1 → move left = 1
 * left = 1, right = 0 → exit loop
 * Return left = 1 (target should be inserted at index 1)
 * Time and Space Complexity
 * Time Complexity: O(log n), where n is the number of elements in the array.
 * Space Complexity: O(1), since we are using constant extra space.
 *
 * Edge Cases to Consider
 * Empty array → insert at index 0
 * Target smaller than all elements → insert at index 0
 * Target greater than all elements → insert at the end of the array
 * Target already exists → return its current index
 * Conclusion
 * The “Search Insert Position” problem is a great demonstration of how binary search can be adapted beyond finding exact matches. By returning the low pointer when the search ends, we efficiently find where a value should be placed in a sorted structure — a useful pattern in many real-world systems that maintain ordered data.
 * */