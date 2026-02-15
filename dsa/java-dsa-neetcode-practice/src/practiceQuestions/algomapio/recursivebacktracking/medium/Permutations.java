package practiceQuestions.algomapio.recursivebacktracking.medium;

import java.util.ArrayList;
import java.util.List;

public class Permutations {

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> sol = new ArrayList<>();
        boolean[] used = new boolean[nums.length];

        backtrack(nums, ans, sol, used);
        return ans;
    }

    private void backtrack(int[] nums, List<List<Integer>> ans, List<Integer> sol, boolean[] used) {
        if (sol.size() == nums.length) {
            ans.add(new ArrayList<>(sol));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (used[i]) continue;

            sol.add(nums[i]);
            used[i] = true;
            backtrack(nums, ans, sol, used);
            sol.remove(sol.size() - 1);
            used[i] = false;
        }
    }

}


/**
 *Understanding the Problem: Generating Permutations
 * The “Permutations” problem requires generating all possible arrangements of a given list of distinct integers. Unlike subsets, where elements can be included or excluded, permutations demand that we arrange all elements in every possible order.
 *
 * For example, given the input [1, 2, 3], the output should be: [[1,2,3], [1,3,2], [2,1,3], [2,3,1], [3,1,2], [3,2,1]]. The number of permutations of an array of length n is n! (n factorial).
 *
 * Backtracking Approach
 * Backtracking is a natural fit for this problem. The idea is to build each permutation step-by-step by choosing one unused element at a time. At each stage of the recursion, we explore one possibility by appending a number to the current solution list. Once a complete permutation is formed—i.e., when the current list's length equals the input list's length—we add a copy of it to the results list.
 *
 * After each recursive call, we backtrack: removing the last added number to try the next available number. This allows us to explore all paths in the solution tree without repeating elements within a permutation.
 *
 * Time and Space Complexity
 * The time complexity is O(n!) because there are n! permutations for n distinct numbers, and each permutation takes O(n) time to build or copy. Therefore, the overall complexity is O(n × n!).
 *
 * The space complexity is also O(n × n!) to store all the permutations, plus O(n) for the recursive call stack and the temporary solution list.
 *
 * Alternative Techniques
 * Another approach is to use in-place swapping. You recursively fix one number at each index and swap it with every number after it. This allows permutations to be generated directly in the input array, often reducing memory usage. However, it can be trickier to implement and debug compared to the set-based backtracking approach.
 * ===============================
 * Step-by-Step Thought Process
 * Understand the problem: Generate all possible permutations of a given array of distinct integers.
 * Initialize an empty list ans to store all permutations and a list sol to build the current permutation.
 * Define a backtrack function:
 * If the length of sol equals the length of nums, append a copy of sol to ans.
 * For each number x in nums, if x is not in sol, append x to sol, call backtrack, and pop x to backtrack.
 * Call backtrack and return ans.
 * */