package algorithms.twopointers;

import java.util.Arrays;

/**
 * 3SUM and 3SUM CLOSEST (Sort + Two Pointers)
 *
 * WHAT IT IS:
 * - 3Sum: find all UNIQUE triplets [a, b, c] in the array with a + b + c == 0
 *   (or any target).
 * - 3Sum Closest: the triplet sum closest to a target.
 *
 * WHEN TO USE THIS:
 * The standard way to extend the 2-pointer pair-sum (OppositeDirection.java) to
 * triplets; 4Sum adds one more outer loop on top of this.
 *
 * STRATEGY:
 * 1. Sort. (O(N log N) - cheaper than the O(N^2) that follows.)
 * 2. Fix the first element nums[i]. In the rest of the array, run the 2-pointer
 *    pair search for target - nums[i]:
 *      sum too small -> left++   sum too big -> right--   equal -> record it.
 * 3. SKIP DUPLICATES so each triplet appears once:
 *      - skip i if nums[i] == nums[i-1] (same first element as last round)
 *      - after a match, move left past equal values (and right too).
 * Early exit for target 0: once nums[i] > 0, every sum is positive.
 * Sums are done in long so three large ints can't overflow.
 *
 * COMPLEXITY:
 * Time: O(N^2)
 * Space: O(1) extra besides the output (O(log N) for the sort).
 */
public class ThreeSum {

    /** All unique triplets summing to target, each sorted ascending, in sorted order. */
    public static int[][] threeSum(int[] input, long target) {
        int[] nums = input.clone();
        Arrays.sort(nums);
        int n = nums.length;
        int[][] result = new int[8][];
        int count = 0;

        for (int i = 0; i < n - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue; // same first element -> same triplets
            int left = i + 1, right = n - 1;
            while (left < right) {
                long sum = (long) nums[i] + nums[left] + nums[right];
                if (sum < target) left++;
                else if (sum > target) right--;
                else {
                    if (count == result.length) result = Arrays.copyOf(result, count * 2);
                    result[count++] = new int[]{nums[i], nums[left], nums[right]};
                    while (left < right && nums[left] == nums[left + 1]) left++;   // skip duplicates
                    while (left < right && nums[right] == nums[right - 1]) right--;
                    left++;
                    right--;
                }
            }
        }
        return Arrays.copyOf(result, count);
    }

    public static int[][] threeSum(int[] nums) {
        return threeSum(nums, 0);
    }

    /** The triplet sum closest to target (needs at least 3 numbers). */
    public static long threeSumClosest(int[] input, long target) {
        if (input.length < 3) throw new IllegalArgumentException("need at least 3 numbers");
        int[] nums = input.clone();
        Arrays.sort(nums);
        long best = (long) nums[0] + nums[1] + nums[2];
        for (int i = 0; i < nums.length - 2; i++) {
            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                long sum = (long) nums[i] + nums[left] + nums[right];
                if (Math.abs(sum - target) < Math.abs(best - target)) best = sum;
                if (sum < target) left++;
                else if (sum > target) right--;
                else return sum; // can't get closer than exact
            }
        }
        return best;
    }

    public static void main(String[] args) {
        System.out.println("--- 3SUM DEMO ---");
        int[] nums = {-1, 0, 1, 2, -1, -4};
        System.out.println("3Sum of " + Arrays.toString(nums) + ": " + Arrays.deepToString(threeSum(nums)));
        // [[-1, -1, 2], [-1, 0, 1]]
        System.out.println("3Sum of [0, 0, 0, 0]: " + Arrays.deepToString(threeSum(new int[]{0, 0, 0, 0}))); // [[0, 0, 0]]
        System.out.println("Closest to 1 in [-1, 2, 1, -4]: " + threeSumClosest(new int[]{-1, 2, 1, -4}, 1)); // 2
    }
}
