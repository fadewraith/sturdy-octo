package practiceQuestions.gfg160problems.arrayspractice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NextPermutation {

    /**
     * Generates the next lexicographically greater permutation of the given array.
     * If the given array is the last possible permutation, it rearranges the elements to form the lowest possible order (i.e., sorted in ascending order).
     * This method first generates all permutations of the given array, then sorts them lexicographically.
     * Finally, it finds the current permutation index and rearranges the elements of the array to form the next permutation.
     * If the current permutation is the last permutation, it rearranges the elements to form the lowest possible order.
     * Time complexity: O(n! * n) where n is the length of the array.
     * Space complexity: O(n! * n)
     *
     * @param arr the array to find the next permutation of.
     */
    private static void naiveNextPermutation(int[] arr) {
        List<List<Integer>> res = new ArrayList<>();

        // generate all permutations
        naiveGeneratePermutations(res, arr, 0);

        // sort all permutation lexicographically
        Collections.sort(res, (a, b) -> {
            for(int i = 0; i < a.size(); i++) {
                if(!a.get(i).equals(b.get(i))) {
                    return a.get(i) - b.get(i);
                }
            }
            return 0;
        });

        // find the current permutation index
        for(int i = 0; i < res.size(); i++) {
            // current permutation matches input
            boolean match = true;
            for(int j = 0; j < arr.length; j++) {
                if(arr[j] != res.get(i).get(j)) {
                    match = false;
                    break;
                }
            }

            if(match) {
                // if its not the last permutation
                if(i < res.size() - 1) {
                    for(int j = 0; j < arr.length; j++) {
                        arr[j] = res.get(i + 1).get(j);
                    }
                } else {
                    // if its the last permutation
                    for(int j = 0; j < arr.length; j++) {
                        arr[j] = res.get(0).get(j);
                    }
                }

                break;
            }
        }

    }

    /**
     * Generates all permutations of the given array by swapping elements starting from the given index.
     * This method uses backtracking to generate all permutations of the array.
     * Time complexity: O(n! * n) where n is the length of the array.
     * Space complexity: O(n! * n)
     *
     * @param res the list to store all permutations
     * @param arr the array to generate permutations of
     * @param idx the starting index to generate permutations from
     */
    private static void naiveGeneratePermutations(List<List<Integer>> res, int[] arr, int idx) {
        // Base case: if idx reaches the end of array
        if (idx == arr.length - 1) {
            List<Integer> temp = new ArrayList<>();
            for (int x : arr) temp.add(x);
            res.add(temp);
            return;
        }

        // Generate all permutations by swapping
        for (int i = idx; i < arr.length; i++) {
            swap(arr, idx, i);

            // Recur for the next index
            naiveGeneratePermutations(res, arr, idx + 1);

            // Backtrack to restore original array
            swap(arr, idx, i);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = {2, 4, 1, 7, 5, 0};

        // O(n! * n) Time and O(n! * n) Space
        // naiveNextPermutation(arr);

        // O(n) Time and O(1) Space
        optimalNextPermutation(arr);

        for (int x : arr) {
            System.out.print(x + " ");
        }
    }

    /**
     * Generates the next lexicographically greater permutation of the given array.
     * If the given array is the last possible permutation, it rearranges the elements to form the lowest possible order (i.e., sorted in ascending order).
     * This method first finds the pivot index, then swaps the pivot with the element from the right that is greater than it.
     * Finally, it reverses the elements from pivot + 1 to the end.
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * @param arr the array to find the next permutation of.
     */
    private static void optimalNextPermutation(int[] arr) {
        int n = arr.length;

        // find the pivot index
        int pivot = -1;
        for(int i = n - 2; i >= 0; i--) {
            if(arr[i] < arr[i + 1]) {
                pivot = i;
                break;
            }
        }

        // if pivot point doesn't exist, reverse the whole array
        if(pivot == -1) {
            reverseArray(arr, 0, n - 1);
            return;
        }

        // Find the element from the right that is greater than pivot
        for (int i = n - 1; i > pivot; i--) {
            if (arr[i] > arr[pivot]) {
                swap(arr, i, pivot);
                break;
            }
        }

        // Reverse the elements from pivot + 1 to the end
        reverseArray(arr, pivot + 1, n - 1);

    }

/* <<<<<<<<<<<<<<  ✨ Windsurf Command ⭐ >>>>>>>>>>>>>>>> */
    /**
     * Reverses the elements of the given array from the start index to the end index.
     * This method iterates through the array and swaps elements at the start and end indices,
     * incrementing the start index and decrementing the end index until they meet in the middle.
     *
     * @param arr The array containing the elements to reverse.
     * @param start The starting index of the elements to reverse.
     * @param end The ending index of the elements to reverse.
     */
/* <<<<<<<<<<  7476ed92-d7f5-44d5-ad7f-5e161f4cb58e  >>>>>>>>>>> */
    private static void reverseArray(int[] arr, int start, int end) {
        while(start < end) {
            swap(arr, start++, end--);
        }
    }
}

/**
 * Given an array of integers arr[] representing a permutation (i.e., all elements are unique and arranged in some order), find the next lexicographically greater permutation by rearranging the elements of the array.
 * If such a permutation does not exist (i.e., the array is the last possible permutation), rearrange the elements to form the lowest possible order (i.e., sorted in ascending order).
 *
 * Lexicographical Order Explained
 * Think of numbers as words in a dictionary. We compare elements from left to right:
 *
 * First Digit Comparison:
 * [1,2,3] vs [1,3,2] → First digits (1=1), so move to next digit
 * Second digits: 2 < 3 → [1,2,3] comes before [1,3,2]
 * Next Comparison:
 * [1,3,2] vs [2,1,3] → First digits: 1 < 2 → [1,3,2] comes before [2,1,3]
 * Final Comparison:
 * [2,1,3] vs [3,2,1] → First digits: 2 < 3 → [2,1,3] comes before [3,2,1]
 *
 * [1,2,3] < [1,3,2] < [2,1,3] < [3,2,1]
 * */