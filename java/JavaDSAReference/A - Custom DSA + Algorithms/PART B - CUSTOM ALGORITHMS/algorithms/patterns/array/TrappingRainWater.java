package algorithms.patterns.array;

import java.util.Arrays;

/**
 * TRAPPING RAIN WATER
 * 
 * WHAT IT IS:
 * Given an array of non-negative integers representing an elevation map where the 
 * width of each bar is 1, compute how much water it can trap after raining.
 * 
 * COMBINATION USAGE:
 * - Builds on the Two Pointers pattern (Part B, item 3).
 * 
 * VARIATIONS:
 * 1. Prefix-Max/Suffix-Max Arrays: O(N) space. Precomputes the highest wall to the 
 *    left and right of every index. The water at index i is min(leftMax, rightMax) - height[i].
 * 2. Two-Pointer Approach (Implemented here): O(1) space. The ultimate optimization.
 * 
 * STRATEGY (Two Pointers):
 * - Maintain `left` and `right` pointers, and `left_max` and `right_max` values.
 * - If `height[left] < height[right]`, it means the water level at `left` is strictly 
 *   bottlenecked by `left_max` (because `right_max` is guaranteed to be even higher!). 
 *   So we can safely calculate trapped water at `left` and advance the left pointer.
 * - Otherwise, do the same for the right pointer.
 * 
 * COMPLEXITY:
 * Time: O(N)
 * Space: O(1)
 */
public class TrappingRainWater {

    public static int trap(int[] height) {
        if (height == null || height.length < 3) return 0;

        int left = 0;
        int right = height.length - 1;
        
        int leftMax = 0;
        int rightMax = 0;
        int totalWater = 0;

        while (left < right) {
            // The bottleneck is on the left side
            if (height[left] < height[right]) {
                if (height[left] >= leftMax) {
                    leftMax = height[left]; // Update highest wall seen so far on the left
                } else {
                    // Water is trapped! Calculate it.
                    totalWater += leftMax - height[left];
                }
                left++;
            } 
            // The bottleneck is on the right side
            else {
                if (height[right] >= rightMax) {
                    rightMax = height[right]; // Update highest wall seen on the right
                } else {
                    totalWater += rightMax - height[right];
                }
                right--;
            }
        }

        return totalWater;
    }

    public static void main(String[] args) {
        System.out.println("--- TRAPPING RAIN WATER DEMO ---");
        
        int[] elevation = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        System.out.println("Elevation Map: " + Arrays.toString(elevation));
        System.out.println("Trapped Water: " + trap(elevation)); 
        // Expected: 6
    }
}
