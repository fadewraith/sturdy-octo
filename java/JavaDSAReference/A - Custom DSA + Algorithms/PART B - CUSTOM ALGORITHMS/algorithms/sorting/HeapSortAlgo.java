package algorithms.sorting;

import java.util.Arrays;

/**
 * HEAP SORT
 * 
 * WHEN TO USE THIS:
 * - When you need a guaranteed O(N log N) sort (unlike QuickSort's worst case O(N^2)).
 * - When you are severely memory constrained so you cannot use MergeSort (which needs O(N) space).
 * - Heap sort is completely IN-PLACE.
 * 
 * DATA STRUCTURE:
 * Operates on Arrays. Uses an implicit Binary Max-Heap represented directly inside the array.
 * 
 * COMBINATION USAGE:
 * - Heap Sort = Binary Heap data structure + Array Traversal.
 * 
 * PSEUDOCODE:
 * heapSort(arr):
 *     buildMaxHeap(arr)  // O(N) time!
 *     for i from n-1 down to 1:
 *         swap(arr[0], arr[i])
 *         heapifyDown(arr, i, 0)
 * 
 * COMPLEXITY TABLE:
 * Algorithm      | Best Time | Avg Time | Worst Time | Space | Stable? | In-Place?
 * --------------------------------------------------------------------------------
 * Heap Sort      | O(N log N)| O(N log N)| O(N log N) | O(1)  | No      | Yes
 */
public class HeapSortAlgo {

    public static void sort(int[] arr) {
        int n = arr.length;

        // 1. Build Max-Heap in O(N) time.
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapifyDown(arr, n, i);
        }

        // 2. Extract elements one by one
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);
            heapifyDown(arr, i, 0);
        }
    }

    private static void heapifyDown(int[] arr, int heapSize, int index) {
        int largest = index; 
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;

        if (leftChild < heapSize && arr[leftChild] > arr[largest]) largest = leftChild;
        if (rightChild < heapSize && arr[rightChild] > arr[largest]) largest = rightChild;

        if (largest != index) {
            swap(arr, index, largest);
            heapifyDown(arr, heapSize, largest);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        System.out.println("--- HEAP SORT DEMO ---");
        int[] arr = {12, 11, 13, 5, 6, 7};
        System.out.println("Original array: " + Arrays.toString(arr));
        sort(arr);
        System.out.println("Sorted array:   " + Arrays.toString(arr));
    }
}
