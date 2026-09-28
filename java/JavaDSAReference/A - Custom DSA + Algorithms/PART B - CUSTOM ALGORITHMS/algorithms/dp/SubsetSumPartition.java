package algorithms.dp;

/**
 * SUBSET SUM / PARTITION EQUAL SUBSET SUM
 * 
 * WHAT IT IS:
 * Subset Sum: Given a set of non-negative integers, and a value sum, determine 
 * if there is a subset of the given set with sum equal to given sum.
 * Partition: Determine if an array can be partitioned into two subsets such that 
 * the sum of elements in both subsets is the same. (This is identical to Subset Sum 
 * where the target is exactly half of the total array sum!).
 * 
 * STRATEGY (Dynamic Programming):
 * `dp[i][j]` will be true if a subset of elements `arr[0...i-1]` has a sum equal to `j`.
 * If we don't include the item: `dp[i-1][j]`
 * If we do include the item: `dp[i-1][j - arr[i-1]]`
 * 
 * COMPLEXITY:
 * Time: O(N * Sum)
 * Space: O(Sum) using 1D optimized array.
 */
public class SubsetSumPartition {

    public static boolean isSubsetSum(int[] arr, int sum) {
        int n = arr.length;
        // dp[j] will be true if there is a subset with sum equal to j
        boolean[] dp = new boolean[sum + 1];
        
        // Base case: sum 0 is always achievable (empty subset)
        dp[0] = true;

        for (int i = 0; i < n; i++) {
            // We iterate backwards to avoid using the same item multiple times!
            for (int j = sum; j >= arr[i]; j--) {
                dp[j] = dp[j] || dp[j - arr[i]];
            }
        }
        return dp[sum];
    }

    public static boolean canPartition(int[] arr) {
        int totalSum = 0;
        for (int num : arr) {
            totalSum += num;
        }

        // If total sum is odd, we absolutely cannot split it into two equal integer halves!
        if (totalSum % 2 != 0) return false;

        // Otherwise, it's just a Subset Sum problem where target = totalSum / 2
        return isSubsetSum(arr, totalSum / 2);
    }

    public static void main(String[] args) {
        System.out.println("--- SUBSET SUM / PARTITION DEMO ---");
        
        int[] arr1 = {3, 34, 4, 12, 5, 2};
        int target = 9;
        System.out.println("Has subset with sum 9? " + isSubsetSum(arr1, target)); // Expected: true (4 + 5)
        
        int[] arr2 = {1, 5, 11, 5};
        System.out.println("Can partition equally? " + canPartition(arr2)); 
        // Expected: true (Subset 1: [11], Subset 2: [1, 5, 5])
    }
}
