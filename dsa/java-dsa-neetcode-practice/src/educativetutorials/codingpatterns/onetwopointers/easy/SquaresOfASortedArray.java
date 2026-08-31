package educativetutorials.codingpatterns.onetwopointers.easy;

import java.util.Arrays;

public class SquaresOfASortedArray {
    public static int[] sortedSquares(int[] nums) {
        int n = nums.length;

        int[] res = new int[n];

        int left = 0, right = n - 1;

        int pos = n - 1;

        while (left <= right) {
            if (Math.abs(nums[left]) > Math.abs(nums[right])) {
                res[pos] = nums[left] * nums[left];
                left++;
            } else {
                res[pos] = nums[right] * nums[right];
                right--;
            }

            pos--;
        }

        return res;
    }

    public static void main(String[] args) {
        // 5 test cases
        int[][] testCases = {
                {-4, -1, 0, 3, 10},    // mix of negatives and positives
                {-7, -3, 2, 3, 11},    // another mix
                {0, 1, 2, 3, 4},       // all non-negative
                {-5, -4, -3, -2, -1},  // all negative
                {1}                    // single element
        };

        for (int i = 0; i < testCases.length; i++) {
            System.out.println((i + 1) + ".\tnums = " + Arrays.toString(testCases[i]));
            System.out.println("\tOutput = " + Arrays.toString(sortedSquares(testCases[i])));
            System.out.println("----------------------------------------------------------------------------------------------------");
        }
    }
}
