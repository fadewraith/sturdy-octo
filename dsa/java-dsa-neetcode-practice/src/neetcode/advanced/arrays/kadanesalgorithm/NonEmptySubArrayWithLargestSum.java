package neetcode.advanced.arrays.kadanesalgorithm;

import java.util.Arrays;

public class NonEmptySubArrayWithLargestSum {

    // Brute Force: O(n^2)
    public static int bruteForce(int[] arr) {
       int maxSum = arr[0];
       for(int i = 0; i < arr.length; i++) {
           int currentSum = 0;
           for(int j = i; j < arr.length; j++) {
               currentSum += arr[j];
               maxSum = Math.max(maxSum, currentSum);
           }
       }
       return maxSum;
    }

    // Kadane's Algorithm: O(n)
    public static int kadane(int[] arr) {
        int maxSum = arr[0];
        int currentSum = 0;

        for(int i = 0; i < arr.length; i++) {
            currentSum = Math.max(currentSum, 0);
            currentSum += arr[i];
            maxSum = Math.max(maxSum, currentSum);
        }
        return maxSum;
    }

    // Optimized Kadane's Algorithm: O(n)
    // return the left and right index of the max subarray sum
    // assuming there's exactly one result (no ties).
    // sliding window variation of Kadane's algorithm
    public static int[] slidingWindow(int[] arr) {
        int maxSum = arr[0], currentSum = 0, maxL = 0, maxR = 0, left = 0;

        for(int right = 0; right < arr.length; right++) {
            if(currentSum < 0) {
                currentSum = 0;
                left = right;
            }

            currentSum += arr[right];
            if(currentSum > maxSum) {
                maxSum = currentSum;
                maxL = left;
                maxR = right;
            }
        }

        return new int[] {maxL, maxR};
    }

    public static void main(String[] args) {
        int[] arr = {-2,1,-3,4,-1,2,1,-5,4};
        System.out.println(bruteForce(arr)); // Output: 6
        System.out.println(kadane(arr)); // Output: 6
        System.out.println(Arrays.toString(slidingWindow(arr))); // Output: [3, 6]
    }
}
