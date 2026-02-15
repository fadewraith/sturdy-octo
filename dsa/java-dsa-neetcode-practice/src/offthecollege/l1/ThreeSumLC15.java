package offthecollege.l1;

import java.util.*;

public class ThreeSumLC15 {

    private static List<List<Integer>> threeSum1(int[] nums) {
        Set<List<Integer>> set = new HashSet<>();
        int n = nums.length;

        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        List<Integer> triplet = Arrays.asList(nums[i], nums[j], nums[k]);
                        Collections.sort(triplet); // ensure consistent order
                        set.add(triplet);
                    }
                }
            }
        }

        return new ArrayList<>(set);
    }


    private static List<List<Integer>> threeSum2(int[] nums) {
        // O(n*n), O(1)
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        int n = nums.length;

        for (int i = 0; i < n - 2; i++) {

            // Skip duplicate fixed element
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            int left = i + 1;
            int right = n - 1;

            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];

                if (sum == 0) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));

                    // Skip duplicates for left
                    while (left < right && nums[left] == nums[left + 1]) left++;

                    // Skip duplicates for right
                    while (left < right && nums[right] == nums[right - 1]) right--;

                    left++;
                    right--;
                }
                else if (sum < 0) {
                    left++;
                }
                else {
                    right--;
                }
            }
        }

        return result;
    }


    public static void main(String[] args) {
        System.out.println(threeSum1(new int[] {-1,0,1,2,-1,-4}));
        System.out.println(threeSum1(new int[] {0,1,1}));
        System.out.println(threeSum1(new int[] {0,0,0}));
        System.out.println();
        System.out.println(threeSum2(new int[] {-1,0,1,2,-1,-4}));
        System.out.println(threeSum2(new int[] {0,1,1}));
        System.out.println(threeSum2(new int[] {0,0,0}));
    }

}
