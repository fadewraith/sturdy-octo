package practiceQuestions.algomapio.binarysearch.medium;

import java.sql.Time;

public class SearchInRotatedSortedArray {

    private static int solution(int[] nums, int target) {
        // Time Complexity: O(log(n))
        // Space Complexity: O(1)

        int n = nums.length; // size
        int left = 0, right = n - 1; // initialize left and right pointers

        while(left < right) {
            int mid = (left + right) / 2;

            if(nums[mid] > nums[right]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        int minIndex = left;

        if(minIndex == 0) {
            left = 0;
            right = n - 1;
        } else if(target >= nums[0] && target <= nums[minIndex - 1]) {
            left = 0;
            right = minIndex - 1;
        } else {
            left = minIndex;
            right = n - 1;
        }

        while(left <= right) {

            int mid = (left + right) / 2;

            if(nums[mid] == target) {
                return mid;
            } else if(nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }


        return -1;
    }

    public static void main(String[] args) {

    }
}


/**
 * Detailed Explanation
 * Understanding the Problem: Search in Rotated Sorted Array
 * The “Search in Rotated Sorted Array” problem asks us to find the index of a target element in a sorted array that has been rotated at an unknown pivot. If the target exists, return its index; otherwise, return -1.
 *
 * This array is guaranteed to have been rotated but contains no duplicates. Each half of the array retains a sorted structure, which makes this an ideal case for binary search with modifications.
 *
 * Example:
 *
 * Input: nums = [4,5,6,7,0,1,2], target = 0 → Output: 4
 * Input: nums = [4,5,6,7,0,1,2], target = 3 → Output: -1
 * Why This Problem Matters
 * This problem teaches how to adapt binary search to handle rotations — a common transformation of sorted data in problems involving circular arrays or incomplete sorting. It's frequently asked in technical interviews due to its balance of logic and binary search manipulation.
 *
 * Optimal Approach: Modified Binary Search
 * The trick to solving this efficiently is recognizing that at least one half of the array is always sorted. By determining which half is sorted at each step, we can decide where to move the search window.
 *
 * Steps:
 * Initialize left = 0 and right = nums.length - 1.
 * While left <= right:
 * Compute mid = Math.floor((left + right) / 2).
 * If nums[mid] == target, return mid.
 * Determine which half is sorted:
 * If nums[left] <= nums[mid]: the left half is sorted.
 * Check if target is in this range:
 * If nums[left] <= target < nums[mid], search left: right = mid - 1.
 * Otherwise, search right: left = mid + 1.
 * Else: the right half is sorted.
 * Check if target is in this range:
 * If nums[mid] < target <= nums[right], search right: left = mid + 1.
 * Otherwise, search left: right = mid - 1.
 * If the loop ends, the target does not exist in the array → return -1.
 * Example Walkthrough
 * Input: nums = [4,5,6,7,0,1,2], target = 0
 *
 * left = 0, right = 6 → mid = 3 → nums[3] = 7
 * Left half [4,5,6,7] is sorted, but 0 is not in this range → search right → left = 4
 * mid = 5 → nums[5] = 1 → right half [0,1,2] is sorted → 0 is in range → right = 4
 * mid = 4 → nums[4] = 0 → match found → return 4
 * Time and Space Complexity
 * Time Complexity: O(log n), where n is the number of elements in the array. Each iteration cuts the search space in half.
 * Space Complexity: O(1), as we use only constant extra memory.
 *
 * Edge Cases to Consider
 * Array with one element → check directly
 * Array not rotated at all → standard binary search still works
 * Target is first or last element → ensure boundaries are handled correctly
 * Conclusion
 * The “Search in Rotated Sorted Array” problem is a brilliant example of how binary search can be extended beyond simple sorted arrays. By cleverly identifying the sorted half at each step, we can zero in on the target efficiently, even after the array has been rotated.
 * */