package algorithms.searching;

/**
 * ================================================================
 * Algorithm: Interpolation Search
 * ================================================================
 * Approach:
 * - Similar to Binary Search, but instead of always picking the 
 *   middle element, it estimates the position of the target based 
 *   on the values at the bounds (assumes uniformly distributed data).
 * - Formula: probe = lo + ((target - arr[lo]) * (hi - lo)) / (arr[hi] - arr[lo])
 *
 * Time Complexity:
 * - Average Case: O(log log N) for uniformly distributed data.
 * - Worst Case: O(N) if data is highly skewed.
 *
 * Space Complexity:
 * - O(1) iterative approach.
 *
 * Real-world Use Case:
 * - Searching in telephone directories or dictionaries where the 
 *   data is uniformly sorted.
 * ================================================================
 */
public class InterpolationSearch {
    
    public static int search(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        
        int lo = 0;
        int hi = arr.length - 1;
        
        while (lo <= hi && target >= arr[lo] && target <= arr[hi]) {
            if (lo == hi) {
                if (arr[lo] == target) return lo;
                return -1;
            }
            
            int pos = lo + (((target - arr[lo]) * (hi - lo)) / (arr[hi] - arr[lo]));
            
            if (arr[pos] == target) {
                return pos;
            }
            
            if (arr[pos] < target) {
                lo = pos + 1;
            } else {
                hi = pos - 1;
            }
        }
        return -1;
    }
    
    public static void main(String[] args) {
        System.out.println("--- Interpolation Search Tests ---");
        int[] empty = {};
        System.out.println("Empty array: " + search(empty, 5)); // -1
        
        int[] duplicates = {1, 2, 2, 2, 3, 4, 5};
        System.out.println("Duplicates (first hit): " + search(duplicates, 2)); 
        
        int[] uniform = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
        System.out.println("Element at lower boundary: " + search(uniform, 10)); // 0
        System.out.println("Element at upper boundary: " + search(uniform, 100)); // 9
        System.out.println("Middle element: " + search(uniform, 60)); // 5
        System.out.println("Not present: " + search(uniform, 25)); // -1
    }
}
