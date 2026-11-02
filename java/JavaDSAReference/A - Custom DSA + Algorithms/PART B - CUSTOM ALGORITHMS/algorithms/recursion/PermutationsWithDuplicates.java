package algorithms.recursion;

import java.util.Arrays;

/**
 * PERMUTATIONS and SUBSETS WITH DUPLICATES (Backtracking without repeated results)
 *
 * WHAT IT IS:
 * - Unique permutations of [1, 1, 2] -> [1,1,2], [1,2,1], [2,1,1]  (3, not 3! = 6)
 * - Unique subsets of [1, 2, 2]      -> [], [1], [1,2], [1,2,2], [2], [2,2]  (6, not 8)
 * Permutations.java and SubsetGeneration.java handle DISTINCT items; with repeated
 * values they would print the same result several times.
 *
 * WHEN TO USE THIS:
 * Any "generate all arrangements/combinations" question where the input may repeat
 * (anagrams of a word with repeated letters, Combination Sum II, ...).
 *
 * STRATEGY - SORT, THEN SKIP EQUAL SIBLINGS:
 * Sorting puts equal values next to each other. Then at each level of the recursion
 * we refuse to try the SAME VALUE twice in the same position:
 *
 * Permutations (used[] marks items already placed):
 *   skip i if used[i], or if nums[i] == nums[i-1] and !used[i-1].
 *   Meaning: copies of a value are always used in their original left-to-right
 *   order, so the copies can't be swapped to make a "different" duplicate result.
 *
 * Subsets (choose which index comes next, from `start` on):
 *   skip i if i > start and nums[i] == nums[i-1].
 *   Meaning: at this level, the first copy of a value already covered every subset
 *   that starts with that value here.
 *
 * COMPLEXITY:
 * Permutations: O(N * N!) worst case (all distinct), fewer with duplicates.
 * Subsets:      O(N * 2^N) worst case.
 * Space: O(N) recursion depth (plus the output).
 */
public class PermutationsWithDuplicates {

    private static int[][] results;
    private static int count;

    private static void add(int[] item) {
        if (count == results.length) results = Arrays.copyOf(results, results.length * 2);
        results[count++] = item;
    }

    /** All distinct permutations, in lexicographic order. */
    public static int[][] permuteUnique(int[] input) {
        int[] nums = input.clone();
        Arrays.sort(nums);
        results = new int[8][];
        count = 0;
        permute(nums, new boolean[nums.length], new int[nums.length], 0);
        return Arrays.copyOf(results, count);
    }

    private static void permute(int[] nums, boolean[] used, int[] current, int depth) {
        if (depth == nums.length) {
            add(current.clone());
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) continue;
            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) continue; // same value, same position
            used[i] = true;
            current[depth] = nums[i];
            permute(nums, used, current, depth + 1);
            used[i] = false;
        }
    }

    /** All distinct subsets (each sorted ascending), in DFS order starting with []. */
    public static int[][] subsetsWithDup(int[] input) {
        int[] nums = input.clone();
        Arrays.sort(nums);
        results = new int[8][];
        count = 0;
        subsets(nums, 0, new int[nums.length], 0);
        return Arrays.copyOf(results, count);
    }

    private static void subsets(int[] nums, int start, int[] current, int size) {
        add(Arrays.copyOf(current, size)); // every node of the tree is a subset
        for (int i = start; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i - 1]) continue; // same value at this level
            current[size] = nums[i];
            subsets(nums, i + 1, current, size + 1);
        }
    }

    public static void main(String[] args) {
        System.out.println("--- PERMUTATIONS / SUBSETS WITH DUPLICATES DEMO ---");
        System.out.println("Unique permutations of [1, 1, 2]: " + Arrays.deepToString(permuteUnique(new int[]{1, 1, 2})));
        // [[1, 1, 2], [1, 2, 1], [2, 1, 1]]
        System.out.println("Unique subsets of [1, 2, 2]:      " + Arrays.deepToString(subsetsWithDup(new int[]{1, 2, 2})));
        // [[], [1], [1, 2], [1, 2, 2], [2], [2, 2]]
        System.out.println("Permutations of [1, 1, 2, 2, 2]: " + permuteUnique(new int[]{1, 1, 2, 2, 2}).length
                + " (= 5! / (2! * 3!) = 10)");
    }
}
