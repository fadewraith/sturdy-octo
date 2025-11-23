package practiceQuestions.gfg160problems.arrayspractice;

public class MoveZeroesToEnd {

    /**
     * This function takes an array of integers as input and
     * returns an array with all zeroes moved to the end of
     * the array.
     *
     * The time complexity of this function is O(n) and the
     * space complexity is O(1).
     *
     * @param arr The input array of integers.
     * @return An array with all zeroes moved to the end.
     */
    private static int[] bruteForce(int[] arr) {
        int n = arr.length;
        int k = 0;
        for(int i = 0; i < n; i++) {
            if(arr[i] != 0) {
                arr[k++] = arr[i];
            }
        }

        while(k < n) arr[k++] = 0;

        return arr;
    }

    /**
     * This function takes an array of integers as input and
     * returns an array with all zeroes moved to the end of
     * the array.
     *
     * The time complexity of this function is O(n) and the
     * space complexity is O(1).
     *
     * This function uses a two-pointer approach to move all zeroes
     * to the end of the array. It iterates through the array and
     * swaps non-zero elements with the element at the current
     * index, incrementing the index as it does so.
     *
     * @param arr The input array of integers.
     * @return An array with all zeroes moved to the end.
     */
    private static int[] optimal(int[] arr) {
        int n = arr.length;
        int k = 0;

        for(int i = 0; i < n; i++) {
            if(arr[i] != 0) {
                swap(arr, i, k++);
            }
        }
        return arr;
    }

    /**
     * Swaps two elements in an array.
     *
     * @param arr The array containing the elements to swap.
     * @param i   The index of the first element.
     * @param j   The index of the second element.
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
