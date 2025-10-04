package arraysandhashing.ez;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class TwoSum {

    public static int[] bruteForce(int[] nums, int target) {
        for(int i = 0; i < nums.length - 1; i++) {
            for(int j = i + 1; j < nums.length; j++) {
                if(target == nums[i] + nums[j]) {
                    return new int[] {i, j};
                }
            }
        }
        return null;
    }

    public static int[] sorting(int[] nums, int target) {
        int[][] A = new int[nums.length][2];
        for (int i = 0; i < nums.length; i++) {
            A[i][0] = nums[i];
            A[i][1] = i;
        }

        Arrays.sort(A, Comparator.comparingInt(a -> a[0]));

        int i = 0, j = nums.length - 1;
        while (i < j) {
            int cur = A[i][0] + A[j][0];
            if (cur == target) {
                return new int[]{Math.min(A[i][1], A[j][1]),
                        Math.max(A[i][1], A[j][1])};
            } else if (cur < target) {
                i++;
            } else {
                j--;
            }
        }
        return new int[0];
    }

    public static int[] twoPass(int[] nums, int target) {
        Map<Integer, Integer> indices = new HashMap<>();  // val -> index

        for (int i = 0; i < nums.length; i++) {
            indices.put(nums[i], i);
        }

        for (int i = 0; i < nums.length; i++) {
            int diff = target - nums[i];
            if (indices.containsKey(diff) && indices.get(diff) != i) {
                return new int[]{i, indices.get(diff)};
            }
        }

        return new int[0];
    }

    public static int[] onePass(int[] nums, int target) {
        Map<Integer, Integer> keyValue = new HashMap<>();
        for(int i = 0; i < nums.length; i++) {
            if(keyValue.containsKey(target - nums[i])) {
                return new int[] { keyValue.get(target - nums[i]), i };
            }
            keyValue.put(nums[i], i);
        }
        return new int[]{};
    }

    public static void main(String[] args) {
        int[] nums = { 3,4,5,6 };
        int target = 7;
//        int[] twoSum = twoSum(nums, target);
        int[] twoSum = twoPass(nums, target);
        System.out.println(Arrays.toString(twoSum));
    }
}
