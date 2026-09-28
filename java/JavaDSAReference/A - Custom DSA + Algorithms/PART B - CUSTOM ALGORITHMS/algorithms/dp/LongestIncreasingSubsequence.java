package algorithms.dp;

import java.util.Arrays;

/**
 * LONGEST INCREASING SUBSEQUENCE (LIS)
 * 
 * WHAT IT IS:
 * Finds the length of the longest subsequence of a given array such that all elements 
 * of the subsequence are sorted in strictly increasing order.
 * 
 * VARIATIONS:
 * 1. Standard DP: O(N^2) Time. For every element `i`, check all previous elements `j`.
 *    If `arr[i] > arr[j]`, `dp[i] = max(dp[i], dp[j] + 1)`.
 * 2. Optimized Binary Search: O(N log N) Time. (Implemented below).
 * 
 * STRATEGY (O(N log N) Binary Search):
 * We maintain an array `tails` where `tails[i]` stores the SMALLEST tail of all 
 * increasing subsequences of length `i+1`.
 * As we iterate through the original array:
 * - If the current element is larger than the last element in `tails`, we append it 
 *   (the LIS grows!).
 * - Otherwise, we use Binary Search to find the first element in `tails` that is 
 *   >= the current element, and we REPLACE it. (This keeps the tails as small as 
 *   possible, maximizing the chances of extending the sequence later!).
 * 
 * COMPLEXITY:
 * Time: O(N log N)
 * Space: O(N)
 */
public class LongestIncreasingSubsequence {

    public static int lengthOfLIS(int[] nums) {
        int[] tails = new int[nums.length];
        int size = 0;
        
        for (int x : nums) {
            int i = 0, j = size;
            
            // Binary Search to find the insertion point
            while (i != j) {
                int m = (i + j) / 2;
                if (tails[m] < x) {
                    i = m + 1;
                } else {
                    j = m;
                }
            }
            
            // Overwrite the value at the found index
            tails[i] = x;
            
            // If we appended to the end, the LIS size increases!
            if (i == size) {
                size++;
            }
        }
        
        return size;
    }

    public static void main(String[] args) {
        System.out.println("--- LONGEST INCREASING SUBSEQUENCE DEMO ---");
        int[] arr = {10, 9, 2, 5, 3, 7, 101, 18};
        
        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("Length of LIS: " + lengthOfLIS(arr));
        // Expected: 4 (The sequence is [2, 3, 7, 101] or [2, 5, 7, 101])
    }
}
