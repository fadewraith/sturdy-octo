package neetcode.advanced.arrays.slidingwindow;

public class SlidingWindowVariable {

    // find the length of the longest subarray, with the same value in each position O(n)
    public static int longestSubarray(int[] nums) {
        int length = 0;
        int left = 0;

        for(int right = 0; right < nums.length; right++) {
            if(nums[left] != nums[right]) {
                left = right;
            }
            length = Math.max(length, right - left + 1);
        }
        return length;
    }

    // Find length of minimum size subarray where the sum is
    // greater than or equal to the target: O(n). Assume all values are positive
    public static int shortestSubarray(int[] nums, int target) {
        int length = Integer.MAX_VALUE;
        int left = 0;
        int total = 0;

        for(int right = 0; right < nums.length; right++) {
            total += nums[right];
            while(total >= target) {
                length = Math.min(length, right - left + 1);
                total -= nums[left++];
            }
        }
        return length == Integer.MAX_VALUE ? 0 : length;
    }


    public static void main(String[] args) {
        // write test case for longestSubarray method
        int[] nums = {1, 2, 3, 1, 1, 1, 2, 2};
        System.out.println("Longest Subarray: " + longestSubarray(nums)); // 3

        // write test case for shortestSubarray method
        int[] nums2 = {2, 3, 1, 2, 4, 3};
        int target = 7;
        System.out.println("Shortest Subarray: " + shortestSubarray(nums2, target)); // 2
    }


}
