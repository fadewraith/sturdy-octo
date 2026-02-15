package educativetutorials.acejavacoding.ds.arrays;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class FindTwoNumbersThatAddUptoK {

    // T - O(n*n)
    // S - O(1)

    private static int[] bruteForce(int[] nums, int k) {

        for(int i = 0; i < nums.length; i++) {
            for(int j = i + 1; j < nums.length; j++) {
                if(nums[i] + nums[j] == k) return new int[] {i, j};
            }
        }
        return new int[0];
    }

    private static int[] twoSumTwoPointer(int[] nums, int k) {
        // ⚠️ ONLY works if array is SORTED
        int i = 0;
        int j = nums.length - 1;

        while (i < j) {
            int sum = nums[i] + nums[j];

            if (sum == k) {
                return new int[] {i, j};
            } else if (sum < k) {
                i++;
            } else {
                j--;
            }
        }

        return new int[0];
    }

    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];

            if (map.containsKey(complement)) {
                return new int[] {map.get(complement), i};
            }

            map.put(nums[i], i);
        }

        return new int[0];
    }

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 3, 4},
                {1, 2},
                {2, 2},
                {-4, -1, -9, 1, -7},
                {25, 50, 75, 100, 400}};

        int[] k = {5, 3, 4, -3, 425};

        for (int i = 0; i < inputs.length; i++) {
            System.out.println((i + 1) + ".\tArray: " + Arrays.toString(inputs[i]));
            System.out.println("\tk: " + k[i]);

            int[] result = bruteForce(inputs[i], k[i]);
            System.out.println("\n\tResult: " + Arrays.toString(result));
            System.out.println('-' + String.join("", Collections.nCopies(100, "-")) + '\n');
        }
    }

}
