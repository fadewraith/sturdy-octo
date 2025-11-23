package neetcodepracticesets.arraysandhashing.ez;

import java.util.ArrayList;
import java.util.List;

public class RemoveElement {

    private static int bruteForce(int[] nums, int val) {

        // both O(n)
        List<Integer> temp = new ArrayList<>();
        for(int num : nums) {
            if(num != val) {
                temp.add(num);
            }
        }

        for(int i = 0; i < temp.size(); i++) {
            nums[i] = temp.get(i);
        }

        return temp.size();
    }

    private static int twoPointersOne(int[] nums, int val) {
        // O(n) & O(1)
        int k = 0;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] != val) {
                nums[k++] = nums[i];
            }
        }
        return k;
    }

    private static int twoPointersTwo(int[] nums, int val) {
        // O(n) & O(1)
        int i = 0, n = nums.length;
        while(i < n) {
            if(nums[i] == val) {
                nums[i] = nums[--n];
            } else {
                i++;
            }
        }
        return n;
    }


}
