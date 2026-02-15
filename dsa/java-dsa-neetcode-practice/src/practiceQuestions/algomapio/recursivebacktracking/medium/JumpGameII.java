package practiceQuestions.algomapio.recursivebacktracking.medium;

public class JumpGameII {

    public int bruteForce(int[] nums) {
        int n = nums.length;
        int[] smallest = {Integer.MAX_VALUE};

        backtrack(0, 0, nums, n, smallest);
        return smallest[0];
    }

    private void backtrack(int i, int jumps, int[] nums, int n, int[] smallest) {
        if (i == n - 1) {
            smallest[0] = Math.min(smallest[0], jumps);
            return;
        }

        int maxJump = nums[i];
        int maxReachableIndex = Math.min(i + maxJump, n - 1);

        for (int newIndex = maxReachableIndex; newIndex > i; newIndex--) {
            backtrack(newIndex, jumps + 1, nums, n, smallest);
        }
    }

    /**
     * Consider nums = [2, 3, 1, 1, 4]. Initially, far = 0 and end = 0. On the first jump, you can go up to index 2. On the second jump, from indices 1 or 2, you can reach the end (index 4). So, the answer is 2 jumps.
     *
     * Step-by-Step Algorithm
     * Initialize three variables: jumps to count the number of jumps, end to mark the range of the current jump, and far to track the farthest index reachable in the next jump.
     * Iterate through the array from index 0 to n - 2 (we stop before the last index since we aim to reach it).
     * At each step i, calculate far = max(far, i + nums[i]) to find the furthest index we can jump to from the current segment.
     * If i equals end, we’ve reached the boundary of the current jump level, so we increment jumps and update end = far.
     * Example
     * Consider nums = [2, 3, 1, 1, 4]. Initially, far = 0 and end = 0. On the first jump, you can go up to index 2. On the second jump, from indices 1 or 2, you can reach the end (index 4). So, the answer is 2 jumps.
     *
     * The greedy strategy works because we always choose the jump that allows us to reach the farthest possible point. This mimics a level-order traversal in BFS and guarantees the fewest jumps needed. We don’t need to explore all paths, only the farthest reachable range from each position.
     *
     * Time Complexity: O(n), where n is the length of the array. Each index is visited once.
     *
     * Space Complexity: O(1), since we use only a constant number of variables.
     * */
    public int optimal(int[] nums) {
        int smallest = 0;
        int n = nums.length;
        int end = 0;
        int far = 0;

        for (int i = 0; i < n - 1; i++) {
            far = Math.max(far, i + nums[i]);

            if (i == end) {
                smallest++;
                end = far;
            }
        }

        return smallest;
    }
}

/**
 * Fix with Optimal Solution
 * The optimal solution uses a greedy approach to achieve O(n) time complexity. Instead of exploring all possible jumps, it maintains two pointers: end (the farthest index reachable with the current number of jumps) and far (the farthest index reachable with one more jump). Here’s how it improves:
 *
 * Greedy Traversal: Iterate through the array, updating far as the maximum reachable index from the current position (i + nums[i]). When the current index i reaches end, increment the jump count and update end to far, effectively choosing the farthest reachable point as the next jump’s boundary.
 * Efficiency: This processes each index exactly once, resulting in O(n) time complexity. It avoids redundant exploration by making a single pass and greedily selecting the optimal jump point at each step.
 * Space: Only a few variables are used, maintaining O(1) space complexity.
 * Why It’s Better: The greedy approach leverages the fact that to minimize jumps, we should always jump as far as possible within
 * */


/**
 * Step-by-Step Thought Process
 * Brute Force
 * The problem requires finding the minimum number of jumps needed to reach the last index of an array, where each element nums[i] indicates the maximum jump length from index i.
 *
 * The brute-force solution uses a recursive backtracking approach to explore all possible jump sequences. Here’s how it works:
 *
 * Initialization: We use a list smallest to store the minimum number of jumps found, initialized to infinity.
 * Backtracking Function: The backtrack(i, jumps) function takes the current index i and the number of jumps taken:
 * If i equals n-1 (the last index), update smallest[0] with the minimum of its current value and jumps.
 * Otherwise, compute the maximum jump distance max_jump = nums[i] and the farthest reachable index max_reachable_index = min(i + max_jump, n-1).
 * For each possible next index new_index from max_reachable_index down to i+1, recursively call backtrack(new_index, jumps+1).
 * Execution and Result: Call backtrack() starting from index 0 with 0 jumps, and return smallest[0].
 * Why This Works: The backtracking explores every possible sequence of jumps, ensuring that the minimum number of jumps to reach the last index is found. By updating smallest only when the last index is reached, we track the best solution across all paths.
 * Time and Space Complexity: The solution explores up to nums[i] choices at each index, leading to an exponential time complexity of O(k^n) in the worst case, where k is the maximum jump length and n is the array length. The space complexity is O(n) due to the recursion stack.
 *
 * Inefficiency in Brute-Force
 * The brute-force solution is highly inefficient because it explores all possible jump combinations recursively, leading to an exponential time complexity of O(k^n). For each index, it tries up to nums[i] possible next indices, and this branching repeats for each subsequent index, resulting in a combinatorial explosion for large arrays or large jump values.
 *
 * Additionally, the solution recomputes paths that may overlap, redundantly exploring the same indices multiple times, which wastes computational effort. This makes it impractical for large inputs.
 * */