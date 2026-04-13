package educativetutorials.codingpatterns.threeslidingwindow.medium;

public class BinarySubarraysWithSum {

    private static int numSubarraysWithSum(int[] nums, int goal) {
        int start = 0;
        int prefixZeros = 0;
        int currentSum = 0;
        int totalCount = 0;

        for(int i = 0; i < nums.length; i++) {
            currentSum += nums[i];

            // 1. Shrink window: If sum exceeds goal, contract from left
            while(start <= i && currentSum > goal) {
                currentSum -= nums[start];
                start++;
                prefixZeros = 0; // Reset count since we moved past the relevant segment
            }

            // 2. Count prefix zeros: If sum matches goal, count leading zeros
            // These zeros allow for multiple valid subarrays ending at 'end'
            while(start < i && nums[start] == 0 && currentSum == goal) {
                prefixZeros++;
                currentSum -= nums[start]; // Removing 0 doesn't change sum
                start++;
            }

            // 3. Accumulate count if window is valid
            if(start <= i && currentSum == goal) {
                totalCount = totalCount + 1 + prefixZeros;
            }
        }

        return  totalCount;
    }

    public static void main(String[] args) {

        int[][] arrays = {
                {1,0,1,0,1},
                {0,0,0,0,0},
                {1,1,1},
                {0,1,0,1,0,1},
                {1}
        };

        int[] goals = {2, 0, 2, 2, 1};

        for (int i = 0; i < arrays.length; i++) {
            int res = numSubarraysWithSum(arrays[i], goals[i]);
            System.out.println((i+1) + ".\tnums: " + java.util.Arrays.toString(arrays[i]));
            System.out.println("\tgoal: " + goals[i]);
            System.out.println("\tNumber of subarrays with sum = " + goals[i] + " are " + res + ".");
            System.out.println("-".repeat(100));
        }
    }
}


/**
 * Binary Subarrays With Sum
 *
 * Problem:
 * Given a binary array nums and an integer goal, return the number of non-empty
 * subarrays with a sum equal to goal.
 *
 * Key Idea (Sliding Window):
 * Since nums contains only 0s and 1s, we can use a sliding window approach.
 * The sum increases predictably, making it easy to expand and shrink the window.
 *
 * Important Insight:
 * Leading zeros do not change the sum but increase the number of valid subarrays.
 *
 * Algorithm:
 * 1. Initialize:
 *    - start = 0 (left boundary)
 *    - currentSum = 0
 *    - prefixZeros = 0 (count of leading zeros)
 *    - totalCount = 0 (result)
 *
 * 2. Iterate with end (right boundary):
 *    - Add nums[end] to currentSum
 *
 * 3. If currentSum > goal:
 *    - Shrink window from left:
 *      while currentSum > goal:
 *          subtract nums[start] from currentSum
 *          start++
 *      - Reset prefixZeros = 0
 *
 * 4. If currentSum == goal:
 *    - Count leading zeros:
 *      while nums[start] == 0:
 *          prefixZeros++
 *          start++
 *    - Add (1 + prefixZeros) to totalCount
 *
 * 5. Continue until end reaches the array end.
 *
 * 6. Return totalCount.
 *
 * Time Complexity: O(n)
 * Each element is processed at most twice.
 *
 * Space Complexity: O(1)
 * Only constant extra space is used.
 */