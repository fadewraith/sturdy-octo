package algorithms.dp;

import java.util.Arrays;

/**
 * HOUSE ROBBER
 * 
 * WHAT IT IS:
 * You are a robber planning to rob houses along a street. Each house has a certain 
 * amount of money stashed. The only constraint: Adjacent houses have connected 
 * security systems and it will automatically contact the police if two adjacent 
 * houses were broken into on the same night. Determine the maximum money you can rob.
 * 
 * STRATEGY:
 * `dp[i]` represents the max money you can steal up to house `i`.
 * For every house, you have two choices:
 * 1. Rob it: You get its money + the max money from `i-2` (since you can't rob `i-1`).
 * 2. Skip it: You get the max money from `i-1`.
 * `dp[i] = max(nums[i] + dp[i-2], dp[i-1])`
 * 
 * COMPLEXITY:
 * Time: O(N)
 * Space: O(1) (You only need to track the last two variables, similar to Fibonacci!)
 */
public class HouseRobber {

    public static int rob(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        
        int prev2 = 0; // max money if we robbed up to i-2
        int prev1 = 0; // max money if we robbed up to i-1
        
        for (int num : nums) {
            int current = Math.max(num + prev2, prev1);
            prev2 = prev1;
            prev1 = current;
        }
        
        return prev1;
    }

    public static void main(String[] args) {
        System.out.println("--- HOUSE ROBBER DEMO ---");
        
        int[] houses = {2, 7, 9, 3, 1};
        System.out.println("House Values: " + Arrays.toString(houses));
        System.out.println("Max money to rob: " + rob(houses));
        // Expected: 12 (Rob house 1 (val 2), house 3 (val 9), house 5 (val 1) -> 2 + 9 + 1 = 12)
    }
}
