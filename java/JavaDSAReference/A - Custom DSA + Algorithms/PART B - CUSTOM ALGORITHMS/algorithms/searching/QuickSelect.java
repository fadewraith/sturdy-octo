package algorithms.searching;

import java.util.Random;

/**
 * QUICKSELECT (Hoare's Selection Algorithm)
 * 
 * WHAT IT IS:
 * A selection algorithm to find the K-th smallest (or K-th largest) element in an 
 * UNSORTED array. It runs in expected O(N) time.
 * 
 * WHEN TO USE THIS:
 * - When a problem asks to "find the K-th element" and you DON'T need the rest of 
 *   the array fully sorted.
 * - Why is this better than sorting? Sorting takes O(N log N). QuickSelect takes O(N) average.
 * - Alternate approach: A Min/Max Heap can do this in O(N log K), but QuickSelect is 
 *   O(N) and strictly in-place (no extra memory).
 * 
 * COMBINATION USAGE:
 * - It directly borrows the exact `partition()` logic from QuickSort (Part B, Item 1).
 * - However, unlike QuickSort (which recurses down BOTH the left and right halves), 
 *   QuickSelect looks at where the pivot landed, compares it to K, and ONLY recurses 
 *   into the ONE side that contains the K-th element. This is why it is O(N) instead of O(N log N).
 * 
 * COMPLEXITY:
 * Time: O(N) Average, O(N^2) Worst-Case (if a bad pivot is chosen consistently).
 * Space: O(1) iterative or O(log N) recursive stack.
 */
public class QuickSelect {

    private static final Random RAND = new Random();

    /**
     * Finds the K-th smallest element in the array.
     * Note: K is 1-based (e.g., k=1 means the absolute smallest).
     */
    public static int findKthSmallest(int[] arr, int k) {
        if (k < 1 || k > arr.length) throw new IllegalArgumentException("K is out of bounds");
        
        // Convert k to 0-based index
        int targetIndex = k - 1;
        return quickSelect(arr, 0, arr.length - 1, targetIndex);
    }

    /**
     * Finds the K-th largest element.
     * The K-th largest is simply the (N - K + 1)-th smallest element.
     */
    public static int findKthLargest(int[] arr, int k) {
        if (k < 1 || k > arr.length) throw new IllegalArgumentException("K is out of bounds");
        
        int targetIndex = arr.length - k;
        return quickSelect(arr, 0, arr.length - 1, targetIndex);
    }

    private static int quickSelect(int[] arr, int left, int right, int targetIndex) {
        if (left == right) {
            return arr[left]; // If array contains only one element, return it
        }

        // 1. Pick a random pivot to avoid O(N^2) worst case
        int pivotIndex = left + RAND.nextInt(right - left + 1);
        
        // 2. Partition the array around the pivot
        pivotIndex = partition(arr, left, right, pivotIndex);

        // 3. The magic of QuickSelect: Only recurse into the necessary half!
        if (targetIndex == pivotIndex) {
            return arr[targetIndex]; // We found the exact spot!
        } else if (targetIndex < pivotIndex) {
            return quickSelect(arr, left, pivotIndex - 1, targetIndex); // Look left
        } else {
            return quickSelect(arr, pivotIndex + 1, right, targetIndex); // Look right
        }
    }

    /**
     * Standard Lomuto partition scheme (borrowed directly from QuickSort).
     */
    private static int partition(int[] arr, int left, int right, int pivotIndex) {
        int pivotValue = arr[pivotIndex];
        // Move pivot to the very end temporarily
        swap(arr, pivotIndex, right);
        
        int storeIndex = left;
        for (int i = left; i < right; i++) {
            if (arr[i] < pivotValue) {
                swap(arr, storeIndex, i);
                storeIndex++;
            }
        }
        
        // Move pivot to its final sorted place
        swap(arr, storeIndex, right);
        return storeIndex;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        System.out.println("--- QUICKSELECT DEMO ---");
        
        int[] arr = {10, 4, 5, 8, 6, 11, 26};
        System.out.println("Unsorted Array: [10, 4, 5, 8, 6, 11, 26]");
        // If sorted: [4, 5, 6, 8, 10, 11, 26]
        
        int k = 3;
        System.out.println("\nFinding the " + k + "rd SMALLEST element...");
        int kthSmallest = findKthSmallest(arr.clone(), k); 
        System.out.println("Result: " + kthSmallest); // Expected: 6
        
        System.out.println("\nFinding the " + k + "rd LARGEST element...");
        int kthLargest = findKthLargest(arr.clone(), k);
        System.out.println("Result: " + kthLargest); // Expected: 10
    }
}
