package practiceQuestions.gfg160problems.arrayspractice;

public class RotateArrayByDClockwise {

    /**
     * Rotates an array by d positions in a clockwise direction.
     *
     * This method uses the brute force approach to rotate the array.
     * It shifts the elements of the array d positions to the right,
     * with the last d elements being moved to the front of the array.
     *
     * Time complexity: O(n*d)
     * Space complexity: O(1)
     *
     * @param arr the array to rotate
     * @param d   the number of positions to rotate the array
     * @return the rotated array
     */
    private static int[] bruteForce(int[] arr, int d) {
        int n = arr.length;

        for(int i = 0; i < d; i++) {
            int first = arr[0];
            for(int j = 0; j < n - 1; j++) {
                arr[j] = arr[j + 1];
            }
            arr[n - 1] = first;
        }
        return arr;
    }

    /**
     * Rotates an array by d positions in a clockwise direction using an optimal solution.
     *
     * This method first calculates the effective number of positions to rotate the array (d % n),
     * then copies the last n - d elements to the front of a temporary array, and
     * finally copies the first d elements to the back of the temporary array.
     * The final rotated array is obtained by copying the elements of the temporary array into the original array.
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     * @param arr the array to rotate
     * @param d   the number of positions to rotate the array
     * @return the rotated array
     */
    private static int[] optimalSolution(int[] arr, int d) {
        int n = arr.length;

        // d > n
        d %= n;

        int[] temp =  new int[n];

        // Copy last n - d elements to the front of temp
        for(int i = 0; i < n - d; i++) temp[i] = arr[d + i];

        // Copy the first d elements to the back of temp
        for(int i = 0; i < d; i++) temp[n - d + i] = arr[i];

        // Copying the elements of temp in arr to get the final rotated array
        for(int i = 0; i < n; i++) arr[i] = temp[i];

        return arr;
    }

    /**
     * Rotates an array by d positions in a clockwise direction.
     *
     * This method first calculates the effective number of positions to rotate the array (d % n),
     * then reverses the first d elements of the array, reverses the elements from index d to n - 1,
     * and finally reverses the entire array.
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * @param arr the array to rotate
     * @param d   the number of positions to rotate the array
     * @return the rotated array
     */
    private static int[] rotateArr(int[] arr, int d) {
        int n = arr.length;

        d %= n;

        // Reverse the first d elements
        reverse(arr, 0, d - 1);

        // Reverse the remaining n-d elements
        reverse(arr, d, n - 1);

        // Reverse the entire array
        reverse(arr, 0, n - 1);

        return arr;
    }

    private static void reverse(int[] arr, int start, int end) {
        while(start < end) {
            swap(arr, start++, end--);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
