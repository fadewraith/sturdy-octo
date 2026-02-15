package educativetutorials.acejavacoding.ds.arrays;

import java.util.Arrays;

public class FindSecondMaxValueInArray {

    /**
     * O(n) time and O(1) space
     * Finds the second maximum element in an array.
     *
     * <p>This method first finds the maximum element in the array, and then finds the second maximum element by iterating through the array again and checking for elements that are not equal to the maximum element and are greater than the current second maximum element.</p>
     *
     * @param arr The input array of integers
     * @return The second maximum element in the array
     */
    private static int bruteForce(int[] arr) {
        int firstMax = Integer.MIN_VALUE;
        int secondMax = Integer.MIN_VALUE;
        int n = arr.length;

        for(int i = 0; i < n; i++) {
            if(arr[i] > firstMax) {
                firstMax = arr[i];
            }
        }

        for(int i = 0; i < n; i++) {
            if(arr[i] != firstMax && arr[i] > secondMax) {
                secondMax = arr[i];
            }
        }
        return secondMax;
    }

    /**
     * Finds the second maximum element in an array in O(n) time and O(1) space.
     *
     * <p>This method iterates through the array once and keeps track of the maximum and second maximum elements. If an element is greater than the current maximum, the current maximum is updated to be the second maximum and the current element is updated to be the maximum. If an element is not equal to the current maximum and is greater than the current second maximum, the current second maximum is updated to be the current element.</p>
     *
     * @param arr The input array of integers
     * @return The second maximum element in the array
     */
    private static int optimized(int[] arr) {
        int firstMax = Integer.MIN_VALUE;
        int secondMax = Integer.MIN_VALUE;

        for(int item : arr) {
            if(item > firstMax) {
                secondMax = firstMax;
                firstMax = item;
            } else if (item != firstMax && item > secondMax) {
                secondMax = item;
            }
        }

        return secondMax;
    }

    public static void main(String[] args) {
        int[][] inputs = {
                {9, 2, 3, 6},
                {1, 2},
                {-2, 2},
                {-4, -1, -9, 1, -7},
                {25, 50, 75, 100, 100}
        };

        for (int i = 0; i < inputs.length; i++) {
            System.out.println((i + 1) + ".\tArray: " + Arrays.toString(inputs[i]));
            System.out.println("\n\tSecond maximum value: " + bruteForce(inputs[i]));
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
}
