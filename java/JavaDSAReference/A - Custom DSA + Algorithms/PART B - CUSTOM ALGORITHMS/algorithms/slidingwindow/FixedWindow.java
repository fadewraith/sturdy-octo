package algorithms.slidingwindow;

import java.util.Arrays;

/**
 * SLIDING WINDOW (Fixed-Size)
 * 
 * WHAT IT IS:
 * A subset of the Two Pointers technique where the two pointers maintain a STRICT, 
 * FIXED distance from each other as they "slide" across the data structure.
 * 
 * WHEN TO USE THIS:
 * - When a problem asks for something contiguous (subarray/substring) of a 
 *   SPECIFIC EXACT LENGTH `k`.
 * - Example: "Find the maximum sum of any contiguous subarray of size k."
 * 
 * DATA STRUCTURE:
 * Arrays or Strings.
 * 
 * STRATEGY:
 * Instead of calculating the sum of every window from scratch (which takes O(N * K) time), 
 * we just subtract the element that fell out of the left side of the window, and 
 * add the new element that entered the right side of the window!
 * 
 * COMPLEXITY:
 * Time: O(N) because each element is added/subtracted exactly once.
 * Space: O(1)
 */
public class FixedWindow {

    /**
     * Problem: Maximum Sum Subarray of Size K
     * Finds the maximum sum of any contiguous subarray of size exactly K.
     */
    public static int maxSumSubarray(int[] arr, int k) {
        if (arr == null || arr.length < k || k <= 0) {
            throw new IllegalArgumentException("Invalid array or window size");
        }

        int maxSum = 0;
        int currentWindowSum = 0;

        // 1. Calculate the sum of the very first window
        for (int i = 0; i < k; i++) {
            currentWindowSum += arr[i];
        }
        maxSum = currentWindowSum;

        // 2. Slide the window across the rest of the array
        for (int i = k; i < arr.length; i++) {
            // Add the new element entering the window on the right
            // Subtract the old element leaving the window on the left
            currentWindowSum += arr[i] - arr[i - k];
            
            if (currentWindowSum > maxSum) {
                maxSum = currentWindowSum;
            }
        }

        return maxSum;
    }

    public static void main(String[] args) {
        System.out.println("--- FIXED-SIZE SLIDING WINDOW DEMO ---");
        
        int[] arr = {2, 1, 5, 1, 3, 2};
        int k = 3;
        
        System.out.println("Array: " + Arrays.toString(arr) + ", K = " + k);
        System.out.println("Max Sum Subarray of size " + k + ": " + maxSumSubarray(arr, k)); 
        // Expected: 9 (from [5, 1, 3])
    }
}
