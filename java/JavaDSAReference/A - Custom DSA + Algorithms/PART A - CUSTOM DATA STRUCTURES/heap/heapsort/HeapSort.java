package heap.heapsort;

import java.util.Arrays;

/**
 * HEAP SORT
 * 
 * What it is:
 * An incredibly efficient, in-place comparison-based sorting algorithm. 
 * It conceptually divides its input into a sorted and an unsorted region, and 
 * it iteratively shrinks the unsorted region by extracting the largest element 
 * and moving it to the sorted region.
 * 
 * Approach/Strategy (Ascending Order):
 * We use an implicit Max-Heap within the array itself.
 * 1. Build a Max-Heap from the unsorted array. We do this by calling `heapifyDown` 
 *    starting from the last non-leaf node up to the root. This takes O(N) time!
 * 2. Once it's a Max-Heap, the maximum element is at index 0.
 * 3. We swap the root (index 0) with the last element of the unsorted region.
 * 4. We reduce the considered size of the heap by 1 (locking the max element in place).
 * 5. We call `heapifyDown` on the root to restore the Max-Heap property.
 * 6. Repeat until the heap size is 1.
 * 
 * Time/Space Complexity:
 * Operation      | Time Complexity | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * Build Heap     | O(N)            | O(1)             | Fast build from bottom up
 * Sort           | O(N log N)      | O(1)             | Extract max N times
 * Total          | O(N log N)      | O(1) in-place    | Not stable, but memory efficient
 * 
 * WHEN TO USE THIS:
 * When you need a guaranteed O(N log N) sort (unlike QuickSort's worst case O(N^2)) 
 * AND you are severely memory constrained so you cannot use MergeSort (which needs O(N) space).
 */
public class HeapSort {

    /**
     * Sorts the array in-place in ascending order.
     */
    public static void sort(int[] arr) {
        int n = arr.length;

        // 1. Build Max-Heap in O(N) time.
        // We start from the last non-leaf node: (n / 2) - 1
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapifyDown(arr, n, i);
        }

        // 2. Extract elements one by one
        for (int i = n - 1; i > 0; i--) {
            // Move current max (at root 0) to the end of the array
            swap(arr, 0, i);

            // Call heapifyDown on the reduced heap
            heapifyDown(arr, i, 0);
        }
    }

    /**
     * Sinks a node down to restore Max-Heap properties.
     * @param arr The array backing the heap
     * @param heapSize The current active size of the heap (ignoring the sorted tail)
     * @param index The index to sink down
     */
    private static void heapifyDown(int[] arr, int heapSize, int index) {
        int largest = index; 
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;

        // If left child is larger than root
        if (leftChild < heapSize && arr[leftChild] > arr[largest]) {
            largest = leftChild;
        }

        // If right child is larger than largest so far
        if (rightChild < heapSize && arr[rightChild] > arr[largest]) {
            largest = rightChild;
        }

        // If largest is not root
        if (largest != index) {
            swap(arr, index, largest);

            // Recursively heapify the affected sub-tree
            // (Note: in an iterative version, we would just loop)
            heapifyDown(arr, heapSize, largest);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- HEAP SORT DEMO ---");
        
        int[] arr = {12, 11, 13, 5, 6, 7};
        System.out.println("Original array: " + Arrays.toString(arr));
        
        HeapSort.sort(arr);
        
        System.out.println("Sorted array:   " + Arrays.toString(arr));
        // Expected: [5, 6, 7, 11, 12, 13]
    }
}
