package practiceQuestions.algomapio.recursivebacktracking.medium;

import java.util.ArrayList;
import java.util.List;

public class CombinationSum {

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> sol = new ArrayList<>();
        backtrack(candidates, target, 0, 0, sol, res);
        return res;
    }

    private void backtrack(int[] candidates, int target, int start, int curSum, List<Integer> sol, List<List<Integer>> res) {
        if (curSum == target) {
            res.add(new ArrayList<>(sol));
            return;
        }
        if (curSum > target || start == candidates.length) {
            return;
        }

        // Skip the current candidate
        backtrack(candidates, target, start + 1, curSum, sol, res);

        // Include the current candidate
        sol.add(candidates[start]);
        backtrack(candidates, target, start, curSum + candidates[start], sol, res);
        sol.remove(sol.size() - 1);
    }
}


/**
 *
 * The "Combination Sum" problem asks us to find all unique combinations of numbers from a given list of positive integers called candidates that add up to a specific target value. A key twist is that each number in candidates may be used an unlimited number of times in each combination.
 *
 * For instance, given candidates = [2, 3, 6, 7] and target = 7, a valid result would be [[2, 2, 3], [7]]. Here, [2, 2, 3] sums to 7 and uses the number 2 more than once.
 *
 * Step-by-Step Thought Process
 * Understand the problem: Find all unique combinations of candidates that sum to a target, where each candidate can be used unlimited times.
 * Initialize an empty list res to store all valid combinations and an empty list sol to build the current combination.
 * Get the length n of the candidates array and store candidates as nums.
 * Define a backtrack function that takes index i and current sum cur_sum.
 * In backtrack, if cur_sum equals target, append a copy of sol to res and return.
 * If cur_sum exceeds target or i equals n, return to stop exploring this path.
 * Make two choices: skip the current candidate by calling backtrack(i+1, cur_sum).
 * Include the current candidate by appending nums[i] to sol, calling backtrack(i, cur_sum + nums[i]), and then popping nums[i] from sol to backtrack.
 * Start the backtracking process by calling backtrack(0, 0).
 * Return res as the list of all valid combinations.
 *
 * ==================
 *
 * The time complexity is difficult to quantify precisely due to the branching factor and pruning, but in the worst case, it is exponential, roughly O(2^T) where T is the target value. Each recursive call explores paths that build up to the target.
 *
 * The space complexity is O(T) for the recursion stack and the space used to store intermediate combinations.
 *
 *
 * */
