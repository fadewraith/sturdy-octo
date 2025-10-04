package practiceQuestions.algomapio.arraysandstrings.medium;

import java.util.Arrays;

public class ProductOfArrayExceptSelf {

    private static int[] bruteForce(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        // Time: O(n^2)
        // Space: O(n)

        for(int i = 0; i < n; i++) {
            int prod = 1;
            for(int j = 0; j < n; j++) {
                if(i != j) {
                    prod *= nums[j];
                }
            }
            ans[i] = prod;
        }

        return ans;
    }

    private static int[] optimal(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];

        int left = 1, right = 1;
        for(int i = 0; i < n; i++) {
            res[i] = left;
            left *= nums[i];
        }

        for(int i = n - 1; i >= 0; i--) {
            res[i] *= right;
            right *= nums[i];
        }

        return res;
    }

    public static void main(String[] args) {
        int[] nums = { 1, 2, 3, 4 };
        System.out.println(Arrays.toString(optimal(nums)));
    }
}

/**
 * Optimal Solution: Prefix and Suffix Products
 * The optimal approach avoids division and nested loops by breaking the task into two linear passes:
 *
 * In the first pass, we compute the prefix product for every element, which is the product of all the elements to the left of that index.
 * In the second pass, we compute the suffix product while traversing from the right, multiplying it with the prefix product stored earlier to get the final result.
 * The trick is to re-use the result array to avoid extra space: we fill it with left products first, and during the right pass, we keep a running right product that multiplies into the result.
 *
 * Step-by-Step Example
 * Input: [1, 2, 3, 4]
 *
 * Step 1: Compute prefix products
 * We initialize res = [1, _, _, _]
 *
 * res[1] = res[0] * nums[0] = 1 * 1 = 1
 * res[2] = res[1] * nums[1] = 1 * 2 = 2
 * res[3] = res[2] * nums[2] = 2 * 3 = 6
 * Now res = [1, 1, 2, 6]
 * Step 2: Compute suffix products and combine
 * Initialize right = 1 and go from right to left:
 *
 * res[3] = res[3] * right = 6 * 1 = 6, then right = right * nums[3] = 1 * 4 = 4
 * res[2] = res[2] * right = 2 * 4 = 8, then right = 4 * 3 = 12
 * res[1] = res[1] * right = 1 * 12 = 12, then right = 12 * 2 = 24
 * res[0] = res[0] * right = 1 * 24 = 24
 * Final result: [24, 12, 8, 6]
 * */