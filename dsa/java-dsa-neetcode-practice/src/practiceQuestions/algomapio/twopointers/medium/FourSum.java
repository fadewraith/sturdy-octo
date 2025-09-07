package practiceQuestions.algomapio.twopointers.medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FourSum {

    /**
     * Step-by-Step Thought Process
     * Understand the problem: Find all unique quadruplets in an array that sum to a given target.
     * Sort the array to facilitate two-pointer traversal and handle duplicates.
     * Initialize an empty list to store the quadruplets.
     * Iterate through index i from 0 to n-4.
     * Skip duplicate values at i to avoid redundant quadruplets.
     * For each i, iterate through index j from i+1 to n-3.
     * Skip duplicate values at j to avoid redundant quadruplets.
     * Use two pointers, lo (j+1) and hi (n-1), to find pairs that sum with nums[i] and nums[j] to the target.
     * Compute the sum as nums[i] + nums[j] + nums[lo] + nums[hi].
     * If the sum equals the target, add the quadruplet to the answer list, increment lo, decrement hi, and skip duplicates for lo and hi.
     * If the sum is less than target, increment lo; otherwise, decrement hi.
     * Return the list of quadruplets.
     * */

    private static List<List<Integer>> solution(int[] nums, int target) {
        List<List<Integer>> answer = new ArrayList<>();
        Arrays.sort(nums);
        int n = nums.length;

        for(int i = 0; i < n; i++) {
            if(i > 0 && nums[i] == nums[i - 1]) continue;

            for(int j = i + 1; j < n; j++) {
                if(j > i + 1 && nums[j] == nums[j - 1]) continue;

                int low = j + 1, high = n - 1;
                while(low < high) {
                    long sum = (long) nums[i] + nums[j] + nums[low] + nums[high];
                    if(sum == target) {
                        answer.add(Arrays.asList(nums[i], nums[j], nums[low], nums[high]));
                        low++;
                        high--;
                        while(low < high && nums[low] == nums[low - 1]) low++;
                        while(low < high && nums[high] == nums[high + 1]) high++;
                    } else if(sum < target) {
                        low++;
                    } else {
                        high--;
                    }
                }
            }
        }

        return answer;
    }


    public static void main(String[] args) {
        // Test case 1: Basic test case with a valid quadruplet
        int[] nums1 = {1, 0, -1, 0, -2, 2};
        int target1 = 0;
        System.out.println("Test case 1: " + solution(nums1, target1) + " (Expected: [[-2, -1, 1, 2], [-2, 0, 0, 2], [-1, 0, 0, 1]])");

        // Test case 2: No valid quadruplet
        int[] nums2 = {1, 2, 3, 4, 5};
        int target2 = 100;
        System.out.println("Test case 2: " + solution(nums2, target2) + " (Expected: [])");

        // Test case 3: All zeros with target 0
        int[] nums3 = {0, 0, 0, 0};
        int target3 = 0;
        System.out.println("Test case 3: " + solution(nums3, target3) + " (Expected: [[0, 0, 0, 0]])");

        // Test case 4: Large numbers with potential overflow
        int[] nums4 = {1000000000, 1000000000, 1000000000, 1000000000};
        int target4 = -294967296;
        System.out.println("Test case 4: " + solution(nums4, target4) + " (Expected: [])");

        // Test case 5: Multiple valid quadruplets with duplicates
        int[] nums5 = {2, 2, 2, 2, 2, 2, 2, 2};
        int target5 = 8;
        System.out.println("Test case 5: " + solution(nums5, target5) + " (Expected: [[2, 2, 2, 2]])");
    }
}

/**
 * Detailed Explanation
 * Understanding the 4Sum Problem
 * The 4Sum problem challenges you to find all unique quadruplets in an array that sum to a specific target. Unlike the simpler 2Sum and 3Sum problems, this version requires considering four elements, making efficiency and duplicate handling critical. The key is to reduce the time complexity from O(n⁴) using nested loops to a more optimal combination of sorting and two-pointer strategies.
 *
 * Efficient Strategy with Sorting and Two Pointers
 * To solve the 4Sum problem effectively, begin by sorting the input array. Sorting enables us to identify and skip duplicate combinations easily, which is crucial for generating unique quadruplets. After sorting, we fix two numbers using two nested loops, and for each such pair, we use the two-pointer approach to find the remaining two numbers that complete the quadruplet.
 *
 * Specifically, we fix the first index i and the second index j. Then, we initialize two pointers: lo to j + 1 and hi to the end of the array. We compute the current sum of the four selected elements. If it matches the target, we add the combination to the result and skip over duplicates. If the sum is less than the target, we move the lo pointer forward. If it's greater, we move the hi pointer backward.
 *
 * To avoid repeating the same quadruplets, we skip over duplicate values for i, j, lo, and hi. This ensures the result only includes unique combinations, as required by the problem.
 *
 * Time and Space Complexity
 * Time Complexity: O(n³), where n is the number of elements in the input array. Although we use four indices, the two-pointer technique reduces one loop, making it significantly more efficient than O(n⁴).
 * Space Complexity: O(1) additional space, assuming the result list is not counted; otherwise, it grows with the number of valid quadruplets.
 * Why This Method Works
 * This strategy works because sorting brings structure to the problem, allowing the use of the two-pointer method, which is highly efficient in reducing unnecessary computations. It also simplifies the logic for skipping duplicates. The overall flow is systematic, making it both efficient and easy to implement once understood.
 *
 * Conclusion
 * Mastering the 4Sum problem reinforces essential algorithmic techniques such as nested iteration control, two-pointer traversal, and duplicate handling. These strategies are crucial for solving high-complexity problems in interviews and real-world applications involving search and combination generation in sorted arrays.
 * */