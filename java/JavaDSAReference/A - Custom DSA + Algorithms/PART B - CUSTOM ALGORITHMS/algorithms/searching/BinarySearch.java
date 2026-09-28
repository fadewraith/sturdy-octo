package algorithms.searching;

/**
 * BINARY SEARCH (Iterative & Recursive)
 * 
 * WHEN TO USE THIS:
 * - When you need to find a target element (or its insertion point) in O(log N) time.
 * - The primary prerequisite is that the data MUST BE SORTED.
 * 
 * DATA STRUCTURE:
 * - Operates almost exclusively on Arrays (or contiguous memory blocks).
 * - Does NOT work well on Linked Lists because O(1) random access is required to jump 
 *   to the `mid` index. (Binary searching a Linked List takes O(N) time due to traversal).
 * 
 * VARIATIONS:
 * - Iterative: Preferred in production code because it uses O(1) space and won't 
 *   cause StackOverflow errors on massive arrays.
 * - Recursive: Easier to conceptually grasp, but uses O(log N) stack space.
 * 
 * COMBINATION USAGE:
 * - Often used after a Sorting algorithm (Sort first -> then Binary Search multiple queries).
 * 
 * PSEUDOCODE (Iterative):
 * left = 0, right = length - 1
 * while left <= right:
 *     mid = left + (right - left) / 2
 *     if arr[mid] == target: return mid
 *     if arr[mid] < target: left = mid + 1
 *     else: right = mid - 1
 * 
 * COMPLEXITY:
 * Time: O(log N) worst/avg, O(1) best (found at mid immediately).
 * Space: O(1) for iterative, O(log N) for recursive.
 */
public class BinarySearch {

    /**
     * ITERATIVE Binary Search (Preferred)
     */
    public static int searchIterative(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            // NOTE: We use `left + (right - left) / 2` instead of `(left + right) / 2`
            // to prevent Integer Overflow if left and right are massive numbers!
            int mid = left + (right - left) / 2;

            if (arr[mid] == target) {
                return mid; // Target found
            }
            
            if (arr[mid] < target) {
                left = mid + 1; // Discard left half
            } else {
                right = mid - 1; // Discard right half
            }
        }
        return -1; // Target not found
    }

    /**
     * RECURSIVE Binary Search
     */
    public static int searchRecursive(int[] arr, int target) {
        return searchRecHelper(arr, target, 0, arr.length - 1);
    }

    private static int searchRecHelper(int[] arr, int target, int left, int right) {
        if (left > right) {
            return -1; // Base case: not found
        }

        int mid = left + (right - left) / 2;

        if (arr[mid] == target) {
            return mid;
        } else if (arr[mid] < target) {
            return searchRecHelper(arr, target, mid + 1, right);
        } else {
            return searchRecHelper(arr, target, left, mid - 1);
        }
    }

    public static void main(String[] args) {
        System.out.println("--- BINARY SEARCH DEMO ---");
        int[] sortedArr = {2, 5, 8, 12, 16, 23, 38, 56, 72, 91};
        System.out.println("Array: [2, 5, 8, 12, 16, 23, 38, 56, 72, 91]");
        
        System.out.println("\nSearching for 23 (Iterative): Index " + searchIterative(sortedArr, 23)); // Expected: 5
        System.out.println("Searching for 72 (Recursive): Index " + searchRecursive(sortedArr, 72)); // Expected: 8
        System.out.println("Searching for 100 (Iterative): Index " + searchIterative(sortedArr, 100)); // Expected: -1
    }
}
