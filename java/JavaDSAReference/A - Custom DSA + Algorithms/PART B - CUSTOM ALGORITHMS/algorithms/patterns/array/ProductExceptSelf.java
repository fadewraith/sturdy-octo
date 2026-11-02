package algorithms.patterns.array;

import java.util.Arrays;

/**
 * PRODUCT OF ARRAY EXCEPT SELF (Prefix / Suffix products)
 *
 * WHAT IT IS:
 * Return out[i] = product of every element EXCEPT nums[i], WITHOUT division.
 *   [1, 2, 3, 4] -> [24, 12, 8, 6]
 *
 * WHY NO DIVISION?
 * "Total product / nums[i]" breaks as soon as there is a zero (division by zero,
 * and with one zero every other answer is 0 but the zero's own answer is not).
 * The interview also uses it to test the prefix/suffix idea.
 *
 * STRATEGY:
 * out[i] = (product of everything LEFT of i) * (product of everything RIGHT of i)
 * 1. Left-to-right pass: out[i] = running product of nums[0..i-1].
 * 2. Right-to-left pass: multiply out[i] by a running product of nums[i+1..n-1].
 * The output array doubles as the prefix array, so the extra space is O(1).
 * Same idea as PrefixSumAndDifference.java, with * instead of +.
 *
 * COMPLEXITY:
 * Time: O(N) - two passes.
 * Space: O(1) extra (the output array doesn't count).
 */
public class ProductExceptSelf {

    public static int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] out = new int[n];

        int prefix = 1;
        for (int i = 0; i < n; i++) {
            out[i] = prefix;      // product of everything before i
            prefix *= nums[i];
        }

        int suffix = 1;
        for (int i = n - 1; i >= 0; i--) {
            out[i] *= suffix;     // times the product of everything after i
            suffix *= nums[i];
        }
        return out;
    }

    public static void main(String[] args) {
        System.out.println("--- PRODUCT OF ARRAY EXCEPT SELF DEMO ---");
        System.out.println("[1, 2, 3, 4]      -> " + Arrays.toString(productExceptSelf(new int[]{1, 2, 3, 4})));      // [24, 12, 8, 6]
        System.out.println("[-1, 1, 0, -3, 3] -> " + Arrays.toString(productExceptSelf(new int[]{-1, 1, 0, -3, 3}))); // [0, 0, 9, 0, 0]
    }
}
