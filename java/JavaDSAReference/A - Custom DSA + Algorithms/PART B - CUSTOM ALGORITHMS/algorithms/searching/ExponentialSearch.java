package algorithms.searching;

/**
 * ================================================================
 * Algorithm: Exponential Search
 * ================================================================
 * Approach:
 * 1. If the target is at the first position, return 0.
 * 2. Find a range where the target might reside by repeatedly
 *    doubling the index (1, 2, 4, 8...) until the element at the
 *    index is greater than the target or we go out of bounds.
 * 3. Perform a Binary Search within the found range.
 *
 * Time Complexity:
 * - O(log i) where 'i' is the index of the element being searched.
 * - Worst case: O(log N)
 *
 * Space Complexity:
 * - O(1) iterative binary search.
 *
 * Real-world Use Case:
 * - Searching in unbounded/infinite arrays or streams.
 * - Works better than Binary Search when the target is closer to 
 *   the beginning of the array.
 * ================================================================
 */
public class ExponentialSearch {
    
    public static <T extends Comparable<T>> int search(T[] arr, T target) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        
        if (arr[0].compareTo(target) == 0) {
            return 0;
        }
        
        int n = arr.length;
        int bound = 1;
        
        while (bound < n && arr[bound].compareTo(target) <= 0) {
            bound *= 2;
        }
        
        return binarySearch(arr, target, bound / 2, Math.min(bound, n - 1));
    }
    
    private static <T extends Comparable<T>> int binarySearch(T[] arr, T target, int left, int right) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int cmp = arr[mid].compareTo(target);
            
            if (cmp == 0) {
                return mid;
            } else if (cmp < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
    
    public static void main(String[] args) {
        System.out.println("--- Exponential Search Tests ---");
        Integer[] empty = {};
        System.out.println("Empty array: " + search(empty, 5)); // -1
        
        Integer[] single = {10};
        System.out.println("Single element (found): " + search(single, 10)); // 0
        System.out.println("Single element (not found): " + search(single, 5)); // -1
        
        Integer[] arr = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20};
        System.out.println("First element: " + search(arr, 2)); // 0
        System.out.println("Last element: " + search(arr, 20)); // 9
        System.out.println("Middle element: " + search(arr, 12)); // 5
        System.out.println("Not present: " + search(arr, 15)); // -1
    }
}
