package practiceQuestions.algomapio.dp.medium;

import java.util.HashMap;
import java.util.Map;

public class JumpGame {

    /**
     * Solution 1: Simple Recursion
     * Understand the Goal
     * We need to determine if we can reach the last index starting from index 0, where each index i allows a jump of up to nums[i] steps.
     * Model the Problem Recursively
     * At any index i, we can jump to indices i+1, i+2, ..., up to i+nums[i].
     * Define a function can_reach(i) that returns True if we can reach the last index from i, otherwise False.
     * Define the Base Case
     * If we are at the last index (i == n-1), we’ve reached the target, so return True.
     * Recursive Step
     * For the current index i, try all possible jumps (from 1 to nums[i]).
     * For each jump, recursively call can_reach(i+jump). If any jump leads to the last index (returns True), return True.
     * If no jump works, return False.
     * Start the Process
     * Call can_reach(0) to check if we can reach the last index from the start.
     * Recognize Limitations
     * This approach recalculates results for the same indices multiple times, leading to high time complexity (O(max(nums) ^ n)).
     * */

    public boolean canJump(int[] nums) {
        int n = nums.length;
        return canReach(0, nums, n);
    }

    private boolean canReach(int i, int[] nums, int n) {
        if (i == n - 1) return true;

        int maxJump = nums[i];
        for (int jump = 1; jump <= maxJump && i + jump < n; jump++) {
            if (canReach(i + jump, nums, n)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Top-Down Dynamic Programming (Memoization)
     * Identify Inefficiency in Recursive Solution
     * The recursive solution repeats computations for the same indices, causing exponential time complexity.
     * Introduce Memoization
     * Use a dictionary memo to store whether each index can reach the last index (True or False).
     * Initialize memo[n-1] = True since the last index is the target.
     * Modify the Recursive Function
     * Define can_reach(i) to check if index i is in memo. If so, return the cached result.
     * If not, try all jumps from 1 to nums[i] and recursively call can_reach(i+jump).
     * If any jump returns True, store memo[i] = True and return True.
     * If no jump works, store memo[i] = False and return False.
     * Execute the Solution
     * Call can_reach(0) to start from the first index.
     * Analyze Improvement
     * Memoization ensures each index is computed once, reducing time complexity to O(n²) since each index may check up to n jumps.
     * Space complexity is O(n) for the memo dictionary and recursion stack.
     * */

    public boolean canJump1(int[] nums) {
        int n = nums.length;
        Map<Integer, Boolean> memo = new HashMap<>();
        memo.put(n - 1, true);

        return canReach1(0, nums, memo);
    }

    private boolean canReach1(int i, int[] nums, Map<Integer, Boolean> memo) {
        if (memo.containsKey(i)) return memo.get(i);

        int maxJump = nums[i];
        for (int jump = 1; jump <= maxJump && i + jump < nums.length; jump++) {
            if (canReach1(i + jump, nums, memo)) {
                memo.put(i, true);
                return true;
            }
        }

        memo.put(i, false);
        return false;
    }

    /**
     * Greedy Solution
     * Rethink the Approach
     * Instead of trying all jumps from each index, determine if we can reach the starting index by working backward from the last index.
     * Define the Target
     * Initialize target as the last index (n-1), the goal we need to reach.
     * Work Backward
     * Iterate from the second-to-last index (n-2) to 0.
     * For each index i, check if it can reach the current target by verifying if i + nums[i] >= target.
     * If true, update target to i, as we now need to reach index i to get to the last index.
     * Check the Result
     * After the loop, if target == 0, it means we can reach the last index starting from index 0.
     * If target != 0, it’s impossible to reach the last index.
     * */

    public boolean canJump2(int[] nums) {
        int n = nums.length;
        int target = n - 1;

        for (int i = n - 1; i >= 0; i--) {
            if (i + nums[i] >= target) {
                target = i;
            }
        }

        return target == 0;
    }
}


/**
 * Detailed Explanation
 * Understanding the Jump Game Problem
 * The Jump Game is a popular algorithmic problem that asks whether it's possible to reach the last index of an array starting from the first index. Each element in the array represents your maximum jump length from that position. This problem tests your understanding of dynamic programming and greedy algorithms, and is frequently featured in technical interviews.
 *
 * Naive Approach: Recursive Backtracking
 * The brute-force method for solving the Jump Game is to simulate every possible jump path recursively. From each index, you attempt all jumps from 1 up to nums[i]. If any path eventually leads to the last index, the function returns true.
 *
 * While intuitive, this approach has poor performance due to redundant calculations and an exponential number of paths. It becomes impractical for large input arrays.
 *
 * Why the Brute-Force Approach Is Inefficient
 * Time Complexity: O(2n), as each index may lead to many recursive calls based on jump size.
 * Space Complexity: O(n), due to the recursion call stack.
 * Drawback: Recomputing the same subproblems leads to excessive time and memory usage.
 * Greedy Strategy: Optimal and Efficient
 * A far more efficient approach to the Jump Game is to use a greedy algorithm. Instead of exploring all paths, you keep track of the leftmost position from which the last index can be reached. You iterate backward through the array and update this "target" index whenever you find a position from which the target is reachable.
 *
 * If by the end of the loop the target reaches index 0, it means the first index can reach the end.
 *
 * Greedy Algorithm Breakdown
 * Start with target = n - 1 (the last index).
 * Loop from i = n - 2 down to 0.
 * At each step, check if i + nums[i] >= target. If so, set target = i.
 * After the loop, if target == 0, return true; else, return false.
 * Time and Space Complexity
 * Time Complexity: O(n) – Single backward pass through the array.
 * Space Complexity: O(1) – No extra space needed beyond simple variables.
 * Conclusion
 * The Jump Game demonstrates the power of greedy algorithms in optimizing problems that would otherwise involve exponential backtracking. By scanning backward and greedily updating the reachable index, we eliminate unnecessary computation and deliver an optimal O(n) solution. Mastering this pattern can help tackle a wide range of reachability and dynamic programming challenges.
 * */