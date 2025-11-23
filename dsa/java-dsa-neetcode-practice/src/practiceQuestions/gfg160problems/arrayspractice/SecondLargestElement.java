package practiceQuestions.gfg160problems.arrayspractice;

import java.util.Arrays;

public class SecondLargestElement {

    /**
     * Finds the second largest element in an array.
     * This method first sorts the array and then iterates through the array from the end, returning the second largest element it finds.
     * Time complexity: O(n*log(n))
     * Space complexity: O(1)
     * @param arr the array to find the second largest element in
     * @return the second largest element in the array, or -1 if no such element exists
     */
    private static int bruteForce(int[] arr) {
        // O(n*log(n))
        // O(1)
        int n = arr.length;

        Arrays.sort(arr);

        for(int i = n - 2; i >= 0; i--) {
            if(arr[i] != arr[n - 1]) return arr[i];
        }
        return -1;
    }

    /**
     * Returns the second largest element in the given array.
     * This method makes two passes through the array and uses two variables to keep track of the largest and second largest elements.
     * Time complexity: O(n)
     * Space complexity: O(1)
     * @param arr the array to find the second largest element in
     * @return the second largest element in the array, or -1 if no such element exists
     */
    private static int optimalTwoPass(int[] arr) {
        int largest = -1, secondLargest = -1;
        int n = arr.length;

        for(int i = 0; i < n; i++) if(arr[i] > largest) largest = arr[i];

        for(int i = 0; i < n; i++) if(arr[i] != largest && arr[i] > secondLargest) secondLargest = arr[i];


        return secondLargest;
    }


    /**
     * Returns the second largest element in the given array.
     * This method makes a single pass through the array and uses two variables to keep track of the largest and second largest elements.
     * Time complexity: O(n)
     * Space complexity: O(1)
     * @param arr the array to find the second largest element in
     * @return the second largest element in the array, or -1 if no such element exists
     */
    private static int optimalOnePass(int[] arr) {
        int n = arr.length;
        int largest = -1, secondLargest = -1;

        for(int i = 0; i < n; i++) {
            if(arr[i] > largest) {
                secondLargest = largest;
                largest = arr[i];
            } else if(arr[i] < largest && arr[i] > secondLargest) {
                secondLargest = arr[i];
            }
        }

        return secondLargest;
    }
}
