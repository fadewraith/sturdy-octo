package neetcode.advanced.arrays.slidingwindow;

import java.util.HashSet;

public class SlidingWindowDemo {

    // given an array, return true if there are 2 elements within a window of size k that are equal

    // Check if array contains a pair of duplicate values,
    // where the two duplicates are no farther than k positions from
    // each other (i.e. arr[i] == arr[j] and abs(i - j) + 1 <= k).
    // O(n * k)
    public static boolean bruteForce(int[] nums, int k) {
        for(int left = 0; left < nums.length; left++) {
            for(int right = left + 1; right < Math.min(nums.length, left + k); right++) {
                if(nums[left] == nums[right]) {
                    return true;
                }
            }
        }
        return false;
    }

    // O(n)
    private static boolean slidingWindowFixed(int[] nums, int k) {
        HashSet<Integer> window = new HashSet<>(); // Cur window of size <= k
        int left = 0;
        for(int right = 0; right < nums.length; right++) {
            if((right - left + 1) > k) {
                window.remove(nums[left++]);
            }
            if(window.contains(nums[right])) {
                return true;
            }
            window.add(nums[right]);
        }
        return false;
    }

    public static void main(String[] args) {
        // write test case for slidingWindow method
        int[] nums = {1, 2, 3, 1};
        int k1 = 4;
        int k2 = 2;
        System.out.println("Brute Force: " + bruteForce(nums, k1)); // true
        System.out.println("Sliding Window: " + slidingWindowFixed(nums, k1)); // true
        System.out.println("Brute Force: " + bruteForce(nums, k2)); // true
        System.out.println("Sliding Window: " + slidingWindowFixed(nums, k2)); // true

    }
}
