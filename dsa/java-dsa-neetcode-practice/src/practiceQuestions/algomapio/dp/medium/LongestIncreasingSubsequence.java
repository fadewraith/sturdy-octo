package practiceQuestions.algomapio.dp.medium;

public class LongestIncreasingSubsequence {
    /**
     * Step-by-Step Thought Process
     * Understand the problem: Find the length of the longest strictly increasing subsequence in an array of integers.
     * Initialize a DP array dp of size n (length of nums) with all elements set to 1, as each number is a subsequence of length 1.
     * Iterate through each index i from 1 to n-1.
     * For each i, iterate through each previous index j from 0 to i-1.
     * If nums[i] is greater than nums[j], update dp[i] to the maximum of dp[i] and dp[j] + 1, extending the subsequence ending at j.
     * Return the maximum value in dp as the length of the longest increasing subsequence.
     * */

    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        java.util.Arrays.fill(dp, 1);

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        int maxLength = 0;
        for (int length : dp) {
            maxLength = Math.max(maxLength, length);
        }

        return maxLength;
    }
}

/**
 * Detailed Explanation
 * Understanding the Longest Increasing Subsequence Problem
 * The Longest Increasing Subsequence (LIS) problem asks you to find the length of the longest subsequence in an array where the elements are strictly increasing. A subsequence does not need to be contiguous but must maintain the relative order of elements. This classic dynamic programming challenge is useful for understanding sequence optimization and has real-world applications in fields like bioinformatics, stock analysis, and data compression.
 *
 * Dynamic Programming Strategy
 * The optimal solution for LIS uses a bottom-up dynamic programming approach. The key idea is to determine, for each position in the array, the length of the longest increasing subsequence that ends at that position. To accomplish this, we initialize a DP array where each index starts with 1, since any single number alone is a valid increasing subsequence.
 *
 * We then iterate through the array, and for each element at index i, we look back at all previous elements j (where j < i). If nums[i] > nums[j], it means the increasing sequence can be extended. We update dp[i] to the maximum of its current value and dp[j] + 1. This ensures we are always storing the best length for sequences ending at index i.
 *
 * Time and Space Complexity
 * Time Complexity: O(n²), since we use two nested loops to evaluate each pair of indices (i, j).
 * Space Complexity: O(n), where n is the length of the input array, to store the dynamic programming table.
 * Optimizing Further with Binary Search
 * For larger inputs, an O(n²) solution may be too slow. A more efficient approach uses binary search with patience sorting and achieves O(n log n) time complexity. Instead of storing the actual lengths, we maintain a list that represents the smallest possible tail of all increasing subsequences of different lengths. For each number in the input:
 *
 * If it can extend the current sequence (greater than all tails), append it to the list.
 * If not, use binary search to find the smallest element in the list that is greater than or equal to it and replace that element.
 * The final length of this list is the length of the longest increasing subsequence.
 *
 * Conclusion
 * The Longest Increasing Subsequence problem provides a foundational understanding of dynamic programming and sequence analysis. Starting from a simple O(n²) approach and progressing to an O(n log n) solution offers valuable insights into algorithmic optimization and binary search strategies. Mastering this problem is a key step in becoming proficient in competitive programming and technical interviews.
 * */