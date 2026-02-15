package practiceQuestions.algomapio.recursivebacktracking.medium;

import java.util.ArrayList;
import java.util.List;

public class Subsets {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> sol = new ArrayList<>();
        backtrack(nums, 0, sol, res);
        return res;
    }

    private void backtrack(int[] nums, int start, List<Integer> sol, List<List<Integer>> res) {
        res.add(new ArrayList<>(sol));
        for (int i = start; i < nums.length; i++) {
            sol.add(nums[i]);
            backtrack(nums, i + 1, sol, res);
            sol.remove(sol.size() - 1);
        }
    }
}


/**
 *Understanding the Problem: Generating Subsets
 * The “Subsets” problem is a classic example of combinatorial generation. Given an array of distinct integers, the task is to return all possible subsets (also known as the power set). This includes the empty subset and the full array itself.
 *
 * For instance, given the input [1, 2, 3], the output should be [[], [1], [2], [3], [1,2], [1,3], [2,3], [1,2,3]]. The number of subsets for an array of length n is 2ⁿ, since each element can either be included or excluded from a subset.
 * ========================
 * Backtracking Approach
 * The most intuitive and scalable way to generate all subsets is through backtracking. This recursive method explores every decision point: whether to include or exclude the current element. For each index in the input array, we branch into two paths—one that includes the element and one that skips it.
 *
 * A temporary list (commonly named sol) is used to build the current subset, and another list ans collects all valid subsets. At every recursive call, once we reach the end of the input array, we add a copy of sol to ans. This ensures that each possible combination is explored and recorded.
 * =======================
 * Time and Space Complexity
 * This algorithm has a time complexity of O(2ⁿ × n), where n is the length of the input array. The 2ⁿ factor comes from the number of possible subsets, and the extra n factor comes from the cost of copying subsets.
 *
 * The space complexity is also O(2ⁿ × n) to store the final list of subsets. The recursion depth reaches up to n, and the call stack uses O(n) space.
 * =======================
 * Alternative Approaches
 * An iterative solution is also possible: start with [[]], and for each number in the input array, iterate through the existing subsets and append a new subset that includes the current number. This approach avoids recursion but builds the power set in a similar way.
 * =======================
 *  Step-by-Step Thought Process
 * Understand the problem: Generate all possible subsets of a given array of distinct integers.
 * Get the length n of the input array nums.
 * Initialize two empty lists: ans to store all subsets and sol to build the current subset.
 * Define a backtrack function that takes an index i, starting from 0.
 * If i equals n, append a copy of sol to ans as a valid subset.
 * Choose not to include nums[i] and recursively call backtrack(i + 1).
 * Choose to include nums[i] by appending it to sol, recursively call backtrack(i + 1), and pop nums[i] from sol to backtrack.
 * Call backtrack(0) to start the process.
 * Return ans as the list of all subsets.
 * */