package algorithms.sorting;

import java.util.Random;

/**
 * QUICK SORT
 * 
 * WHEN TO USE THIS:
 * - Default choice for sorting primitive arrays (used heavily in Java's Arrays.sort(int[])).
 * - When in-place sorting is required (unlike Merge Sort which needs O(N) memory).
 * - When average-case speed is critical (it has smaller constant factors than Merge Sort).
 * 
 * VARIATIONS (Pivot Strategy Explained):
 * - Naive Pivot (Last or First element): Bad. O(N^2) worst case if array is already sorted.
 * - Median of Three: Pick first, middle, and last, and use their median as pivot. Better.
 * - Randomized Pivot: Pick a completely random index. Guaranteed O(N log N) expected time.
 * This implementation uses the RANDOMIZED PIVOT strategy to prevent worst-case adversarial inputs.
 * 
 * DATA STRUCTURE:
 * Arrays. (Not recommended for Linked Lists, use Merge Sort instead).
 * 
 * COMBINATION USAGE:
 * - QuickSelect (Part B, item 2): Uses the exact partition logic of QuickSort to find the Kth 
 *   element without sorting the whole array.
 * 
 * PSEUDOCODE:
 * quickSort(arr, low, high):
 *     if low < high:
 *         pivotIndex = partition(arr, low, high)
 *         quickSort(arr, low, pivotIndex - 1)
 *         quickSort(arr, pivotIndex + 1, high)
 * 
 * COMPLEXITY TABLE:
 * Algorithm      | Best Time | Avg Time | Worst Time | Space | Stable? | In-Place?
 * --------------------------------------------------------------------------------
 * Quick Sort     | O(N log N)| O(N log N)| O(N^2)     | O(logN)| No      | Yes
 */
public class QuickSort {

    private static final Random RAND = new Random();

    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = randomPartition(arr, low, high);
            
            // Recursively sort left and right of pivot
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    /**
     * Swaps a random element with the last element to avoid worst-case O(N^2) on sorted arrays.
     */
    private static int randomPartition(int[] arr, int low, int high) {
        int randomIndex = low + RAND.nextInt(high - low + 1);
        swap(arr, randomIndex, high);
        return partition(arr, low, high);
    }

    /**
     * Standard Lomuto partition scheme.
     */
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high]; // Pivot is now the (randomized) last element
        int i = low - 1; // Index of smaller element

        for (int j = low; j < high; j++) {
            // If current element is smaller than or equal to pivot
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        
        // Put the pivot directly after all smaller elements
        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        System.out.println("--- QUICK SORT DEMO ---");
        int[] arr = {10, 7, 8, 9, 1, 5, 20, 3};
        
        System.out.print("Original: [");
        for (int i : arr) System.out.print(i + " ");
        System.out.println("]");
        
        sort(arr);
        
        System.out.print("Sorted:   [");
        for (int i : arr) System.out.print(i + " ");
        System.out.println("]");
    }
}
