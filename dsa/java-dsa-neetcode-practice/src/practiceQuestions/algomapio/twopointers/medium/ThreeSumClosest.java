package practiceQuestions.algomapio.twopointers.medium;

import java.util.Arrays;

public class ThreeSumClosest {

    /**
     * Step-by-Step Thought Process
     * Understand the problem: Find three numbers in an array whose sum is closest to a given target.
     * Sort the array to enable efficient two-pointer traversal and skip duplicates.
     * Initialize closest_sum to infinity to track the sum closest to the target.
     * Iterate through each index i from 0 to n-3.
     * Skip duplicate values at i to avoid redundant triplets.
     * Use two pointers, lo (i+1) and hi (n-1), to find pairs that sum with nums[i].
     * Compute the current sum as nums[i] + nums[lo] + nums[hi].
     * If the absolute difference between current sum and target is less than that of closest_sum and target, update closest_sum.
     * If current sum equals target, return it immediately.
     * If current sum is less than target, increment lo; otherwise, decrement hi.
     * Return closest_sum after the loop.
     * */

    private static int solution(int[] nums, int target) {
        int n = nums.length;
        // Sort the array to enable efficient two-pointer traversal and skip duplicates.
        Arrays.sort(nums);
        int closestSum = Integer.MAX_VALUE;

        for(int i = 0; i < n; i++) {
            // Skip duplicate values at i to avoid redundant triplets.
            if(i > 0 && nums[i] == nums[i - 1]) continue;

            // Use two pointers, low (i+1) and high (n-1), to find pairs that sum with nums[i].
            int low = i + 1, high = n - 1;
            while(low < high) {
                // Compute the current sum as nums[i] + nums[low] + nums[high].
                int currentSum = nums[i] + nums[low] + nums[high];

                // If the absolute difference between current sum and target is less than that of closest_sum and target, update closest_sum.
                if(Math.abs(currentSum - target) < Math.abs(closestSum - target)) {
                    closestSum = currentSum;
                }


                // If current sum is less than target, increment lo; otherwise, decrement hi.
                if(currentSum == target) {
                    return currentSum;
                } else if(currentSum < target) {
                    low++;
                } else {
                    high--;
                }
            }
        }

        return closestSum;
    }

    public static void main(String[] args) {
        // Test case 1: Basic test case
        int[] nums1 = {-1, 2, 1, -4};
        int target1 = 1;
        System.out.println("Test case 1: " + solution(nums1, target1) + " (Expected: 2)");

        // Test case 2: Exact match exists
        int[] nums2 = {0, 0, 0};
        int target2 = 1;
        System.out.println("Test case 2: " + solution(nums2, target2) + " (Expected: 0)");

        // Test case 3: All positive numbers
        int[] nums3 = {1, 2, 3, 4, 5};
        int target3 = 6;
        System.out.println("Test case 3: " + solution(nums3, target3) + " (Expected: 6)");

        // Test case 4: All negative numbers
        int[] nums4 = {-5, -4, -3, -2, -1};
        int target4 = -8;
        System.out.println("Test case 4: " + solution(nums4, target4) + " (Expected: -8)");

        // Test case 5: Large array
        int[] nums5 = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        int target5 = 0;
        System.out.println("Test case 5: " + solution(nums5, target5) + " (Expected: 3)");
    }
}


/**
 * Detailed Explanation
 * Understanding the 3Sum Closest Problem
 * The "3Sum Closest" problem asks you to find a triplet in an array of integers such that the sum of the three numbers is closest to a given target value. This problem is a variation of the classic 3Sum problem and is frequently encountered in technical interviews because it tests your ability to combine sorting and two-pointer techniques to achieve efficient solutions.
 *
 * Efficient Strategy Using Sorting and Two Pointers
 * To solve this problem efficiently, we start by sorting the array in ascending order. Sorting allows us to use a two-pointer technique, which helps reduce the time complexity from O(n³) in a brute-force approach to O(n²).
 *
 * We fix one element at a time using a loop, and for the remaining subarray, we apply the two-pointer method. We initialize two pointers: one starting just after the fixed element (left) and one at the end of the array (right). At each step, we calculate the sum of the three elements. If this sum is exactly equal to the target, we return it immediately, as we can’t get any closer. Otherwise, we update the closest sum if the current one is closer than our previous best.
 *
 * Depending on whether the current sum is less than or greater than the target, we adjust our pointers accordingly. Moving the left pointer forward increases the sum, while moving the right pointer backward decreases it. This process continues until the left and right pointers meet.
 *
 * Time and Space Complexity
 * Time Complexity: O(n²), where n is the length of the array. Sorting takes O(n log n), and the two-pointer scan for each element adds O(n²) total complexity.
 * Space Complexity: O(1), since we use only constant extra space aside from the input array.
 * Why This Approach is Optimal
 * By combining sorting with two-pointer traversal, this approach avoids unnecessary repeated computation of all triplet sums, which would be inefficient for large arrays. Sorting provides a structure that enables intelligent pointer movement based on the sum comparison with the target. This ensures that we examine only the most relevant combinations of triplets, significantly improving performance.
 *
 * Conclusion
 * The 3Sum Closest problem demonstrates how classical algorithm techniques like sorting and two pointers can be leveraged to build efficient solutions for combination-based problems. Mastering this approach not only improves performance but also strengthens your ability to solve related optimization and search problems with confidence.
 * */