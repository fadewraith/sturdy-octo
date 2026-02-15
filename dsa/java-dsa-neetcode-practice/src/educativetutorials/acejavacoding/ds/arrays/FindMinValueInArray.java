package educativetutorials.acejavacoding.ds.arrays;

import java.util.Arrays;

public class FindMinValueInArray {

    /**
     * bruteForce soln is sort the array and return the first element - O(n * logn)
     * Given an array of integers, this function returns the minimum element in the array.
     * The function uses an optimized approach to find the minimum element in the array.
     * It works by initializing the minimum element with the first element of the array and then iterating through the array to find the minimum element.
     *
     * @param arr The input array of integers
     * @return The minimum element in the array
     * O(n) time and O(1) space
     */
    private static int optimized(int[] arr) {
        int n = arr.length;
        int minVal = arr[0];

        for(int i = 0; i < n; i++) {
            if(minVal > arr[i]) minVal = arr[i];
        }

        return minVal;
    }

    public static void main(String args[]) {

        int[][] inputs = {
                {9},
                {-1, -5, -10, -2, -4},
                {4, 3, 2, 1},
                {2, 3, 3, -1, -1},
                {100, 50, 75, 25, 400},

        };

        for (int i = 0; i < inputs.length; ++i) {
            System.out.print((i + 1) + ".\tInput list: ");
            System.out.println(Arrays.toString(inputs[i]));

            int result = optimized(inputs[i]);

            System.out.print("\n\tMinimum element: ");
            System.out.println(result);
            System.out.println(new String(new char[100]).replace('\0', '-'));

        }
    }
}
