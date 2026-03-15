package educativetutorials.codingpatterns.onetwopointers.medium;

import java.util.*;

public class ThreeSum {

    /**
     * Time → O(n³)
     *
     * Space → O(n) for set
     * */
    private static List<List<Integer>> bruteForce(int[] nums) {
        Set<List<Integer>> set = new HashSet<>();

        for(int i = 0; i < nums.length; i++) {
            for(int j = i + 1; j < nums.length; j++) {
                for(int k = j + 1; k < nums.length; k++) {

                    if(nums[i] + nums[j] + nums[k] == 0) {
                        List<Integer> triplet = Arrays.asList(nums[i], nums[j], nums[k]);
                        Collections.sort(triplet);
                        set.add(triplet);
                    }
                }
            }
        }

        return new ArrayList<>(set);
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        // Sort the input array in ascending order
        Arrays.sort(nums);

        // Create an empty array to store the unique triplets
        List<List<Integer>> result = new ArrayList<>();

        // Store the length of the array in a variable
        int n = nums.length;

        // Iterate over the array till n - 2
        for (int i = 0; i < n - 2; ++i) {
            // If the current number is greater than 0, break the loop
            if (nums[i] > 0)
                break;

            // The current number is either the first element or not a duplicate of the previous element
            if (i == 0 || nums[i] != nums[i - 1]) {
                // Initialize two pointers
                int low = i + 1, high = n - 1;

                // Run a loop as long as low is less than high
                while (low < high) {
                    // Calculate the sum of the triplet
                    int sum = nums[i] + nums[low] + nums[high];

                    // If the sum is less than 0, move the low pointer forward
                    if (sum < 0)
                        ++low;

                        // If the sum is greater than 0, move the high pointer backward
                    else if (sum > 0)
                        --high;

                    else {
                        // Add the triplet to result as this triplet sums to 0
                        result.add(Arrays.asList(nums[i], nums[low], nums[high]));

                        // Move both pointers to the next distinct values to avoid duplicate triplets
                        ++low;
                        --high;
                        while (low < high && nums[low] == nums[low - 1])
                            ++low;
                        while (low < high && nums[high] == nums[high + 1])
                            --high;
                    }
                }
            }
        }

        // Return result, which contains all unique triplets that sum to zero
        return result;
    }

    // Driver code
    public static void main(String[] args) {
        int[][] numsArrs = {
                {-1, 0, 1, 2, -1, -4},
                {1, 2, 3, 4, 5},
                {0, 0, 0, 0},
                {-4, -1, -1, 0, 1, 2, 2},
                {-10, -7, -3, -1, 0, 3, 7, 10},
                {-3, -5, -7, -9}
        };

        for (int i = 0; i < numsArrs.length; i++) {
            int[] nums = numsArrs[i];
            System.out.println((i + 1) + ".\tnums: [" + Arrays.toString(nums));
            List<List<Integer>> result = threeSum(nums);
            System.out.print("\n\tTriplets: ");
            System.out.println(result);
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }


}
