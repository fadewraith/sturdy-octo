package algorithms.sorting;

import java.util.Arrays;

/**
 * LINEAR SORTS (O(N) Class)
 * Includes: Counting Sort, Radix Sort, Bucket Sort
 * 
 * WHEN TO USE THIS:
 * - These completely bypass the O(N log N) theoretical limit of comparison sorts because 
 *   they DO NOT COMPARE elements! They use integer values as physical array indices.
 * - Counting Sort: When elements are tightly bounded integers (e.g. scores from 0-100).
 * - Radix Sort: When elements are integers that can be extremely large, but have a fixed 
 *   number of digits.
 * - Bucket Sort: When elements are uniformly distributed floating-point numbers (e.g. 0.0 to 1.0).
 * 
 * DATA STRUCTURE: 
 * Operates purely on Arrays (requires creating count arrays or buckets).
 * 
 * COMPLEXITY TABLE:
 * Algorithm      | Time Complexity | Space Complexity | Notes
 * --------------------------------------------------------------------------------
 * Counting Sort  | O(N + K)        | O(K)             | K = Range of input values
 * Radix Sort     | O(d * (N + K))  | O(N + K)         | d = number of digits
 * Bucket Sort    | O(N) avg        | O(N)             | Uses internal sorts for buckets
 */
public class LinearSorts {

    // --------------------------------------------------------
    // 1. COUNTING SORT
    // --------------------------------------------------------
    public static void countingSort(int[] arr) {
        if (arr.length <= 1) return;
        
        // Find min and max to determine the range K
        int max = arr[0], min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) max = arr[i];
            if (arr[i] < min) min = arr[i];
        }
        
        int range = max - min + 1;
        int[] count = new int[range];
        int[] output = new int[arr.length];
        
        // 1. Store counts of each element
        for (int i = 0; i < arr.length; i++) {
            count[arr[i] - min]++;
        }
        
        // 2. Modify count array to store actual cumulative positions (Prefix Sum)
        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }
        
        // 3. Build output array backwards to guarantee STABILITY
        for (int i = arr.length - 1; i >= 0; i--) {
            output[count[arr[i] - min] - 1] = arr[i];
            count[arr[i] - min]--;
        }
        
        // Copy back
        for (int i = 0; i < arr.length; i++) {
            arr[i] = output[i];
        }
    }

    // --------------------------------------------------------
    // 2. RADIX SORT
    // --------------------------------------------------------
    public static void radixSort(int[] arr) {
        if (arr.length <= 1) return;
        
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) max = arr[i];
        }
        
        // Do counting sort for every digit, where exp is 10^i
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSortForRadix(arr, exp);
        }
    }
    
    private static void countingSortForRadix(int[] arr, int exp) {
        int[] output = new int[arr.length];
        int[] count = new int[10]; // Digits are 0-9
        
        for (int i = 0; i < arr.length; i++) {
            count[(arr[i] / exp) % 10]++;
        }
        
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }
        
        for (int i = arr.length - 1; i >= 0; i--) {
            int digit = (arr[i] / exp) % 10;
            output[count[digit] - 1] = arr[i];
            count[digit]--;
        }
        
        for (int i = 0; i < arr.length; i++) {
            arr[i] = output[i];
        }
    }

    // --------------------------------------------------------
    // 3. BUCKET SORT (For floating point numbers [0.0, 1.0))
    // --------------------------------------------------------
    // Note: Due to the global "No built-ins" rule, we use custom ArrayList logic.
    // To avoid rewriting ArrayList here, I will use arrays as buckets conceptually.
    public static void bucketSort(float[] arr) {
        int n = arr.length;
        if (n <= 1) return;
        
        // Array of buckets (which are arrays themselves, initially size 0)
        float[][] buckets = new float[n][0];
        
        // Put elements into buckets
        for (int i = 0; i < n; i++) {
            int bucketIdx = (int) (n * arr[i]); // Hash function
            if (bucketIdx >= n) bucketIdx = n - 1; // Edge case
            
            // "Append" to bucket array (simulating ArrayList)
            float[] b = buckets[bucketIdx];
            float[] newB = Arrays.copyOf(b, b.length + 1);
            newB[newB.length - 1] = arr[i];
            buckets[bucketIdx] = newB;
        }
        
        // Sort individual buckets (Insertion sort is standard for buckets)
        for (int i = 0; i < n; i++) {
            insertionSortFloat(buckets[i]);
        }
        
        // Concatenate all buckets
        int idx = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < buckets[i].length; j++) {
                arr[idx++] = buckets[i][j];
            }
        }
    }
    
    private static void insertionSortFloat(float[] arr) {
        for (int i = 1; i < arr.length; i++) {
            float key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    public static void main(String[] args) {
        System.out.println("--- LINEAR SORTS DEMO ---");
        
        int[] arr1 = {4, 2, 2, 8, 3, 3, 1};
        countingSort(arr1);
        System.out.println("Counting Sort: " + Arrays.toString(arr1));
        
        int[] arr2 = {170, 45, 75, 90, 802, 24, 2, 66};
        radixSort(arr2);
        System.out.println("Radix Sort:    " + Arrays.toString(arr2));
        
        float[] arr3 = {0.897f, 0.565f, 0.656f, 0.1234f, 0.665f, 0.3434f};
        bucketSort(arr3);
        System.out.println("Bucket Sort:   " + Arrays.toString(arr3));
    }
}
