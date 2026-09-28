package algorithms.sorting;

/**
 * MERGE SORT
 * 
 * WHEN TO USE THIS:
 * - When you need a guaranteed O(N log N) time complexity (unlike QuickSort's worst case).
 * - When you need a STABLE sort (meaning equal elements retain their original relative order).
 * - When sorting Linked Lists (MergeSort is O(1) space for Linked Lists!).
 * - When dealing with massive datasets that don't fit in memory (External Sorting).
 * 
 * DATA STRUCTURE:
 * Operates on Arrays (requires O(N) extra space) or Linked Lists (requires O(1) extra space).
 * 
 * COMBINATION USAGE:
 * - TimSort = Merge Sort + Insertion Sort. 
 * - Count Inversions: Merge sort logic is the optimal way to count inversions in an array.
 * 
 * PSEUDOCODE:
 * mergeSort(arr, left, right):
 *     if left < right:
 *         mid = left + (right - left) / 2
 *         mergeSort(arr, left, mid)
 *         mergeSort(arr, mid + 1, right)
 *         merge(arr, left, mid, right)
 * 
 * COMPLEXITY TABLE:
 * Algorithm      | Best Time | Avg Time | Worst Time | Space | Stable? | In-Place?
 * --------------------------------------------------------------------------------
 * Merge Sort     | O(N log N)| O(N log N)| O(N log N) | O(N)  | Yes     | No
 */
public class MergeSort {

    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        // Allocate the temp array ONCE to avoid O(N log N) space overhead 
        // that happens if you allocate it inside the recursive merge step.
        int[] temp = new int[arr.length];
        mergeSortRec(arr, temp, 0, arr.length - 1);
    }

    private static void mergeSortRec(int[] arr, int[] temp, int left, int right) {
        if (left >= right) return;
        
        int mid = left + (right - left) / 2;
        
        // Sort left half
        mergeSortRec(arr, temp, left, mid);
        // Sort right half
        mergeSortRec(arr, temp, mid + 1, right);
        
        // Merge the two halves
        merge(arr, temp, left, mid, right);
    }

    private static void merge(int[] arr, int[] temp, int left, int mid, int right) {
        // Copy data to temp array
        for (int i = left; i <= right; i++) {
            temp[i] = arr[i];
        }

        int i = left;      // Pointer for left half
        int j = mid + 1;   // Pointer for right half
        int k = left;      // Pointer for main array

        // Merge back to main array
        while (i <= mid && j <= right) {
            if (temp[i] <= temp[j]) { // <= guarantees STABILITY
                arr[k] = temp[i];
                i++;
            } else {
                arr[k] = temp[j];
                j++;
            }
            k++;
        }

        // Copy remaining elements of left half
        // (No need to copy right half, they are already in place in arr)
        while (i <= mid) {
            arr[k] = temp[i];
            k++;
            i++;
        }
    }

    public static void main(String[] args) {
        System.out.println("--- MERGE SORT DEMO ---");
        int[] arr = {38, 27, 43, 3, 9, 82, 10};
        
        System.out.print("Original: [");
        for (int i : arr) System.out.print(i + " ");
        System.out.println("]");
        
        sort(arr);
        
        System.out.print("Sorted:   [");
        for (int i : arr) System.out.print(i + " ");
        System.out.println("]");
    }
}
