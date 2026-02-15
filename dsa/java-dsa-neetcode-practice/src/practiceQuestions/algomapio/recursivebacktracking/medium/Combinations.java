package practiceQuestions.algomapio.recursivebacktracking.medium;

import java.util.ArrayList;
import java.util.List;

public class Combinations {

    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> sol = new ArrayList<>();
        backtrack(n, k, sol, ans);
        return ans;
    }

    private void backtrack(int x, int k, List<Integer> sol, List<List<Integer>> ans) {
        if (sol.size() == k) {
            ans.add(new ArrayList<>(sol));
            return;
        }

        if (x > k - sol.size()) {
            backtrack(x - 1, k, sol, ans);
        }

        sol.add(x);
        backtrack(x - 1, k, sol, ans);
        sol.remove(sol.size() - 1);
    }

}


/**
 * Understanding the Problem: Combinations
 * The “Combinations” problem asks us to generate all possible sets of k distinct numbers selected from the range [1, n]. Unlike permutations, order does not matter in combinations. For example, [1, 2] is the same as [2, 1], so only one of them is included in the result.
 *
 * As an example, for n = 4 and k = 2, the valid combinations are: [[1, 2], [1, 3], [1, 4], [2, 3], [2, 4], [3, 4]].
 *
 * Backtracking Strategy
 * This problem is best solved using a recursive backtracking approach. We begin by building combinations from the highest number (n) down to 1. At each recursive call, we make two choices: either include the current number or skip it.
 *
 * We maintain a temporary list (commonly named sol) that holds the current combination. If its length reaches k, we add a copy to the final list ans. After each recursive call, we backtrack by removing the last number to explore other options.
 *
 * One optimization is to stop recursion early if the remaining numbers are not enough to complete a valid combination. This pruning reduces unnecessary computation.
 *
 * Time and Space Complexity
 * The time complexity is O(C(n, k) × k), where C(n, k) is the number of combinations (binomial coefficient), and k is the time to copy each combination.
 *
 * The space complexity is also O(C(n, k) × k) to store all the combinations in memory, plus O(k) for the recursion stack and temporary list.
 *
 * Alternative Approaches
 * An iterative solution using stacks or queues is possible but significantly more complex to implement cleanly. The backtracking approach is not only concise and readable but also performs very well with proper pruning.
 * =====================================
 * Step-by-Step Thought Process
 * Understand the problem: Generate all possible combinations of k numbers chosen from the range [1, n].
 * Initialize an empty list (ans) to store all combinations and a list (sol) to build the current combination.
 * Define a backtracking function that takes the current number x to consider.
 * If the current combination length equals k, append a copy of it to ans and return.
 * Calculate the numbers still needed (k minus current length) and the numbers left (x).
 * If more numbers are left than needed, recursively call backtrack excluding x (x-1).
 * Append x to the current combination and recursively call backtrack with x-1.
 * Pop x from the current combination to backtrack.
 * Start backtracking from n and return ans.
 * */