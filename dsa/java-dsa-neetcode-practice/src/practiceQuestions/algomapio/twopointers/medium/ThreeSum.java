package practiceQuestions.algomapio.twopointers.medium;

import java.util.*;

public class ThreeSum {

    private static List<List<Integer>> bruteForce(int[] nums) {
        Set<List<Integer>> result = new HashSet<>();
        Map<Integer, Integer> indexMap = new HashMap<>();
        int n = nums.length;

        // Build the index map
        for (int i = 0; i < n; i++) {
            indexMap.put(nums[i], i);
        }

        // Iterate over each pair
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int desired = -nums[i] - nums[j];
                if (indexMap.containsKey(desired) && indexMap.get(desired) != i && indexMap.get(desired) != j) {
                    List<Integer> triplet = Arrays.asList(nums[i], nums[j], desired);
                    Collections.sort(triplet);
                    result.add(triplet);
                }
            }
        }

        return new ArrayList<>(result);
    }

    public List<List<Integer>> optimalPreferred(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        List<List<Integer>> answer = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (nums[i] > 0) {
                break;
            }
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int lo = i + 1, hi = n - 1;
            while (lo < hi) {
                int sum = nums[i] + nums[lo] + nums[hi];
                if (sum == 0) {
                    answer.add(Arrays.asList(nums[i], nums[lo], nums[hi]));
                    lo++;
                    hi--;
                    while (lo < hi && nums[lo] == nums[lo - 1]) lo++;
                    while (lo < hi && nums[hi] == nums[hi + 1]) hi--;
                } else if (sum < 0) {
                    lo++;
                } else {
                    hi--;
                }
            }
        }

        return answer;
    }
}

/**
 * Optimal Solution (Preferred): Two Pointers
 * Sort the Array
 * Sorting helps simplify duplicate checking and allows use of the two-pointer technique.
 * nums.sort()
 * Iterate Through the Array
 * Loop through the array with index i from 0 to n - 1.
 * For each index, treat nums[i] as the first element of the triplet.
 * Break Early If Current Number Is Positive
 * If nums[i] > 0, break the loop since the array is sorted and no triplet can sum to 0.
 * Skip Duplicate Elements
 * If nums[i] is the same as nums[i - 1], skip it to avoid duplicate triplets.
 * Use Two Pointers to Find Remaining Two Elements
 * Initialize two twopointers: lo = i + 1 and hi = n - 1.
 * While lo < hi, compute the sum of nums[i] + nums[lo] + nums[hi].
 * Check the Sum
 * If the sum is 0:
 * Add the triplet to the result list.
 * Move both lo and hi twopointers inward.
 * Skip any duplicate values using while loops.
 * If the sum is less than 0, increment lo.
 * If the sum is greater than 0, decrement hi.
 * Return the Result
 * After the loop ends, return the answer list containing all unique triplets.
 * Time and Space Complexity
 * Time: O(n²) – Outer loop runs in O(n), inner two-pointer loop in O(n).
 * Space: O(n) – Excluding the result, the space is constant, but output may take O(n) in the worst case.
 * */
