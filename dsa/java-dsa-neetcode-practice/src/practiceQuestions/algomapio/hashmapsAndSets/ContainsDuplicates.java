package practiceQuestions.algomapio.hashmapsAndSets;

import java.util.HashSet;
import java.util.Set;

public class ContainsDuplicates {

    private static boolean bruteForce(int[] nums) {
        int n = nums.length;
        for(int i = 0; i < n; i++) {
            for(int j = i + 1; j < n; j++) {
                if(nums[i] == nums[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean optimal(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        for(int num: nums) {
            if(seen.contains(num)) {
                return true;
            }
            seen.add(num);
        }
        return false;
    }
}
