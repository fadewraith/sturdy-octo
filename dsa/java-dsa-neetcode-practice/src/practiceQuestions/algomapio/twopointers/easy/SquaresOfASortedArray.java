package practiceQuestions.algomapio.twopointers.easy;

import java.util.Arrays;

public class SquaresOfASortedArray {

    private static int[] bruteForce(int[] nums) {
        // Time: O(n log n)
        // Space: O(1) (not counting output space)

        int n = nums.length;
        for(int i = 0; i < n; i++) {
            nums[i] = nums[i] * nums[i];
        }
        Arrays.sort(nums);
        return nums;
    }


    /**
     * Optimal Solution
     * The optimal solution uses two twopointers to build the result in O(n) time by comparing absolute values from both ends:
     *
     * Initialize two twopointers: left to 0 and right to the last index of nums.
     * Initialize an empty list result to store the squared values.
     * While left is less than or equal to right, compare the absolute values of nums[left] and nums[right].
     * If abs(nums[left]) is greater than abs(nums[right]), append nums[left] ** 2 to result and increment left.
     * Otherwise, append nums[right] ** 2 to result and decrement right.
     * Reverse the result list to get the squares in non-decreasing order.
     * Return result as the sorted array of squares.
     * */
    private static int[] optimal(int[] nums) {
        int left = 0, right = nums.length - 1;
        int[] res = new int[nums.length];
        int index = nums.length - 1;

        while(left <= right) {
            if(Math.abs(nums[left]) > Math.abs(nums[right])) {
                res[index--] = nums[left] * nums[left];
                left++;
            } else {
                res[index--] = nums[right] * nums[right];
                right--;
            }
        }

        return res;
    }

    public static void main(String[] args) {
        int[] nums = { -7, -3, 2, 3, 11 };
        System.out.println(Arrays.toString(optimal(nums)));
    }
}
