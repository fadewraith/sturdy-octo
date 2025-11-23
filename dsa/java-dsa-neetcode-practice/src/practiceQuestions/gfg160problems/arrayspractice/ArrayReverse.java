package practiceQuestions.gfg160problems.arrayspractice;

import java.util.Arrays;

public class ArrayReverse {

    /**
     * Reverses an array using the brute force method.
     * This method creates a new array and copies the elements from the input array in reverse order.
     * Time complexity: O(n)
     * Space complexity: O(n)
     * @param arr the array to reverse
     * @return the reversed array
     */
    private static int[] bruteForce(int[] arr) {
        int n = arr.length;
        int[] ans = new int[n];
        for(int i = 0; i < n; i++) {
            ans[i] = arr[n - 1 - i];
        }
        return ans;
    }

    /**
     * Reverses an array using two pointers in a single pass.
     * This method uses two pointers, one at the start of the array and one at the end,
     * and swaps the elements at the pointers until they meet in the middle.
     * Time complexity: O(n)
     * Space complexity: O(1)
     * @param arr the array to reverse
     * @return the reversed array
     */
    private static int[] twoPointerOne(int[] arr) {
        int i = 0;
        int j = arr.length - 1;
        while(i < j) {
            swap(arr, i++, j--);
        }

        return arr;
    }

    /**
     * Swaps two elements in an array.
     *
     * @param arr the array containing the elements to swap
     * @param i   the index of the first element
     * @param j   the index of the second element
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * Reverses an array using two pointers.
     * This method makes two passes through the array, swapping elements at the start and end of the array.
     * Time complexity: O(n)
     * Space complexity: O(1)
     * @param arr the array to reverse
     * @return the reversed array
     */
    private static int[] twoPointerTwo(int[] arr) {
        int n = arr.length;
        for(int i = 0; i < n / 2; i++) {
            swap(arr, i, n - i - 1);
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        System.out.println(Arrays.toString(twoPointerTwo(arr)));
    }
}
