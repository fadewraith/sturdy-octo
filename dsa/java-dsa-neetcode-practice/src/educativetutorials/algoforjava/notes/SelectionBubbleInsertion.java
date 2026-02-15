package educativetutorials.algoforjava.notes;

import educativetutorials.codingpatterns.commonutilities.Helper;

public class SelectionBubbleInsertion {


    /**
     * Sorts an array using the Selection Sort algorithm.
     * Selection Sort is an in-place comparison-based algorithm
     * in which the list is divided into two parts: the sorted
     * subarray and the unsorted subarray. Initially, the
     * sorted subarray is empty while the unsorted subarray
     * contains all the elements of the array. The smallest
     * (or largest, depending on the ordering) element is
     * selected from the unsorted array and swapped with the
     * leftmost element, and that element becomes a part of
     * the sorted subarray. This process continues moving the
     * boundary of the unsorted region closer.
     *
     * @param arr the array to be sorted
     * @param arrSize the size of the array
     *
     * The algorithm divides the input array into two parts: the sublist of already-sorted elements, which is built up from left to right, and the sublist of the remaining elements that occupy the rest of the list and need to be sorted.
     *
     * Initially, the sorted sublist is empty and the unsorted sublist is the entire input list. The algorithm proceeds by finding the smallest (or largest, depending on sorting order) element in the unsorted sublist, exchanging (swapping) it with the leftmost unsorted element (putting it in sorted order), and moving the sublist boundaries one element to the right.
     *
     * In other words, this algorithm works by iterating over the array and swapping each element with the minimum element found in the rest of the array.
     */
    public static void selectionSort(int[] arr, int arrSize) {
        int minInd;
        //traverse through all elements of the array
        for (int i = 0; i < arrSize; i++) {
            //find the minimum element in the unsorted array
            minInd = Helper.findMin(arr, i, arrSize - 1);
            //Swap the found minimum element with the leftmost unsorted element
            int temp = arr[i];
            arr[i] = arr[minInd];
            arr[minInd] = temp;
        }
    }


    /**
     * Sorts an array using the Bubble Sort algorithm.
     *
     * Bubble Sort is an algorithm which is similar to Insertion Sort, but instead of shifting the next smallest element into position, it iterates through the array one element at a time, comparing adjacent elements and swapping them if the element on the left is greater than the element on the right.
     *
     * @param arr the array to be sorted
     * @param arrSize the size of the array
     * This is another famous sorting algorithm also known as “sinking sort”. It works by comparing adjacent pairs of elements and swapping them if they are in the wrong order. This is repeated until the array is sorted.
     *
     * Think of it this way: a bubble passes over the array and “catches” the maximum/minimum element and brings it over to the right side.
     */
    public static void bubbleSort(int arr[], int arrSize) {
        int i, j, temp;
        //Traverse through all array elements
        for (i = 0; i < arrSize - 1; i++)
            // Last i elements are already in place
            for (j = 0; j < arrSize - i - 1; j++)
                //Traverse the array from 0 to size of array[i-1]
                //Swap if the element found is greater than the next element
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
    }



    /**
     * Sorts an array using the Insertion Sort algorithm.
     *
     * Insertion Sort is a simple sorting algorithm that works the way we sort playing cards in our hands.
     * The array is virtually split into a sorted and an unsorted region.
     * Values from the unsorted part are picked and placed at the correct position in the sorted part.
     *
     * @param arr the array to be sorted
     * @param arrSize the size of the array
     *
     * Insertion sort is another very famous sorting algorithm, and it works the way you would naturally sort items in real life.
     *
     * It iterates over the given array, figures out what the correct position of every element is, and inserts each element in its place.
     */
    public static void insertionSort(int[] arr, int arrSize) {
        int ele, j;
        //Traverse through 1 to size of the array
        for (int i = 1; i < arrSize; i++) {
            ele = arr[i]; // Element to be inserted
            j = i - 1;

            //shifts elements back to create space for the element to be inserted
            while (j >= 0 && arr[j] > ele) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = ele;
        }
    }


}
