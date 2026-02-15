package educativetutorials.algoforjava.codingproblems;

import educativetutorials.algoforjava.notes.QuickSort;

public class FindTwoNumbersAddToN {

    /**
     * This function takes an array of integers and an integer n, and returns an array containing two numbers that add up to n.
     * The function uses a brute force approach, checking every pair of elements in the array to see if their sum equals n.
     * If such a pair is found, the function returns an array containing the two numbers. If not, it returns the original array.
     *
     * @param arr The input array of integers
     * @param n The target sum
     * @return An array containing two numbers that add up to n, or the original array if no such pair is found
     * O(n^2)
     */
    public static int[] bruteForce(int[] arr, int n) {
        int[] result = new int[2];
        //traversing the array
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                //checking if sum of two values equals n
                if (arr[i] + arr[j] == n) {
                    result[0] = arr[i];
                    result[1] = arr[j];
                    return result; // containing the two numbers
                }
            }
        }
        return arr;
    }


    public static int[] findSum(int[] arr, int n) //Returns 2 elements of arr that sum to the given value
    {

        QuickSort quickSort = new QuickSort();
        //Helper sort function that uses the Quicksort Algorithm
        quickSort.partition(arr, 0, arr.length - 1); //Sort the array in Ascending Order
//        quickSort(arr, 0, arr.length - 1);

        int Pointer1 = 0; //Pointer 1 -> At Start
        int Pointer2 = arr.length - 1; //Pointer 2 -> At End

        int[] result = new int[2];
        int sum = 0;

        while (Pointer1 != Pointer2) {

            sum = arr[Pointer1] + arr[Pointer2]; //Calulate Sum of Pointer 1 and 2

            if (sum < n)
                Pointer1++; //if sum is less than given value => Move Pointer 1 to Right
            else if (sum > n)
                Pointer2--;
            else {
                result[0] = arr[Pointer1];
                result[1] = arr[Pointer2];
                return result; // containing 2 number
            }
        }
        return arr;
    }
}
