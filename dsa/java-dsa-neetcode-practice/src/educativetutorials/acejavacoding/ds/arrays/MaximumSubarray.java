package educativetutorials.acejavacoding.ds.arrays;

import java.util.Arrays;

public class MaximumSubarray {

    private static int bruteForce(int[] nums) {
        int n = nums.length;
        int maxSum = Integer.MIN_VALUE;

        for(int i = 0; i < n; i++) {
            int currentSum = 0;
            for(int j = i; j < n; j++) {
                currentSum += nums[j];
                maxSum = Math.max(maxSum, currentSum);
            }
        }

        return maxSum;
    }

    private static int kadane(int[] nums) {
        if(nums.length == 0) return 0;

        int maxSum = nums[0];
        int currentSum = nums[0];

        for(int i = 1; i < nums.length; i++) {
            currentSum = Math.max(nums[i], currentSum + nums[i]);
            maxSum = Math.max(maxSum, currentSum);
        }

        return maxSum;
    }


    public static void main(String[] args) {
        int[][] inputs = {
                {1, 2, 2, 3, 3, 1, 4},
                {2, 2, 1},
                {4, 1, 2, 1, 2},
                {-4, -1, -2, -1, -2},
                {-4, 2, -5, 1, 2, 3, 6, -5, 1},
                {25}
        };

        for (int i = 0; i < inputs.length; i++) {
            System.out.println((i + 1) + ".\tArray: " + Arrays.toString(inputs[i]));
            System.out.println("\tMaximum Sum: " + bruteForce(inputs[i]));
            System.out.println(new String(new char[75]).replace('\0', '-'));
        }
    }
}
