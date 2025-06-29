package neetcode.advanced.arrays;

import java.util.ArrayList;
import java.util.List;

public class PrefixSum {

    List<Integer> prefix;

    // given an array of values, design a ds that can query the sum of a subarray of the values

    public PrefixSum(int[] nums) {
        prefix = new ArrayList<>();
        int total = 0;
        for (int n : nums) {
            total += n;
            prefix.add(total);
        }
    }

    public int rangeSum(int left, int right) {
        int preRight = prefix.get(right);
        int preLeft = left > 0 ? prefix.get(left - 1) : 0;
        return (preRight - preLeft);
    }

    public static void main(String[] args) {
        // write test case
        int[] nums = {1, 2, 3, 4, 5};
        PrefixSum ps = new PrefixSum(nums);
        System.out.println(ps.rangeSum(0, 2)); // should return 6 (1 + 2 + 3)
    }
}