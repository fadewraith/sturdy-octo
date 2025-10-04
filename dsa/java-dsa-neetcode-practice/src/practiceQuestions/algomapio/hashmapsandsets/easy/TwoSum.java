package practiceQuestions.algomapio.hashmapsAndSets.easy;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {

    private static int[] optimal(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();

        for(int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }

        for(int i = 0; i < nums.length; i++) {
            int y = target - nums[i];
            if(map.containsKey(y) && map.get(y) != i) {
                return new int[] { i, map.get(y) };
            }
        }

        throw new IllegalArgumentException("No solution found!");
    }
}
