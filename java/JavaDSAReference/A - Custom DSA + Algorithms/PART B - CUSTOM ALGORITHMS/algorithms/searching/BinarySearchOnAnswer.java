package algorithms.searching;

/**
 * BINARY SEARCH ON ANSWER (Monotonic Search Space)
 * 
 * WHAT IT IS:
 * Instead of binary searching for a specific target element in an array, we binary 
 * search the "Answer Space" itself. We define a condition that behaves MONOTONICALLY 
 * (e.g., False, False, False, True, True, True). We use Binary Search to find the 
 * exact boundary where the condition flips from False to True.
 * 
 * WHEN TO USE THIS:
 * - When the problem asks for a "Minimum of Maximums" or "Maximum of Minimums" 
 *   (e.g., Koko Eating Bananas, Capacity to Ship Packages Within D Days).
 * - When the search space isn't a simple sorted array, but a function that evaluates 
 *   to True/False for any given guess.
 * - This specific file demonstrates the classic "Find Minimum in Rotated Sorted Array".
 * 
 * DATA STRUCTURE:
 * Arrays, or virtual numerical ranges (e.g., guessing speeds from 1 to 100,000,000).
 * 
 * COMPLEXITY:
 * Time: O(log N) or O(log(Max - Min))
 * Space: O(1)
 */
public class BinarySearchOnAnswer {

    /**
     * Problem: Find the Minimum Element in a Rotated Sorted Array.
     * Example: [4, 5, 6, 7, 0, 1, 2] -> The array was sorted, but rotated.
     * 
     * The Monotonic Condition:
     * If arr[mid] > arr[right], it means the "break point" (and the minimum) 
     * MUST be to the right. Otherwise, it is at mid or to the left.
     */
    public static int findMinInRotatedArray(int[] arr) {
        if (arr == null || arr.length == 0) throw new IllegalArgumentException("Array is empty");
        
        int left = 0;
        int right = arr.length - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid] > arr[right]) {
                // The left side is completely sorted, meaning the sudden drop (minimum)
                // has to be somewhere in the right half.
                left = mid + 1;
            } else {
                // The right side is sorted, so mid itself could be the minimum, 
                // or the minimum is to the left.
                right = mid;
            }
        }
        
        // When left == right, we have pinned down the exact boundary!
        return arr[left];
    }

    public static void main(String[] args) {
        System.out.println("--- BINARY SEARCH ON ANSWER DEMO ---");
        
        int[] rotatedArray = {4, 5, 6, 7, 0, 1, 2};
        System.out.println("Array: [4, 5, 6, 7, 0, 1, 2]");
        
        int min = findMinInRotatedArray(rotatedArray);
        System.out.println("Minimum element is: " + min); // Expected: 0
        
        int[] rotatedArray2 = {11, 13, 15, 17}; // (Not actually rotated)
        System.out.println("\nArray: [11, 13, 15, 17]");
        System.out.println("Minimum element is: " + findMinInRotatedArray(rotatedArray2)); // Expected: 11
    }
}
