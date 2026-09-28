package algorithms.patterns.array;

import java.util.Arrays;

/**
 * DUTCH NATIONAL FLAG ALGORITHM (3-Way Partitioning)
 * 
 * WHAT IT IS:
 * An algorithm invented by Edsger Dijkstra to sort an array containing exactly 
 * THREE distinct values (classically 0, 1, and 2, representing the Red, White, 
 * and Blue of the Dutch flag) in a single linear pass.
 * 
 * WHEN TO USE THIS:
 * - LeetCode 75: "Sort Colors"
 * - Used as the ultimate partition step in an optimized 3-Way QuickSort to handle 
 *   arrays with massive amounts of duplicate elements (partitioning into < pivot, 
 *   == pivot, and > pivot).
 * 
 * STRATEGY:
 * Maintain 3 pointers: `low`, `mid`, and `high`.
 * - Everything to the left of `low` is 0.
 * - Everything to the right of `high` is 2.
 * - `mid` scans the array.
 *   - If mid sees 0, swap with low, increment both.
 *   - If mid sees 1, just increment mid.
 *   - If mid sees 2, swap with high, decrement high (DO NOT increment mid yet, 
 *     because the element swapped in from high hasn't been evaluated!).
 * 
 * COMPLEXITY:
 * Time: O(N) single pass.
 * Space: O(1) in-place.
 */
public class DutchNationalFlag {

    public static void sortColors(int[] nums) {
        int low = 0;
        int mid = 0;
        int high = nums.length - 1;

        while (mid <= high) {
            if (nums[mid] == 0) {
                // Swap with low
                int temp = nums[low];
                nums[low] = nums[mid];
                nums[mid] = temp;
                
                low++;
                mid++; // Safe to increment mid because we know what came from low is a 0 or 1
            } 
            else if (nums[mid] == 1) {
                // 1 is in the right place (the middle), just move mid
                mid++;
            } 
            else { // nums[mid] == 2
                // Swap with high
                int temp = nums[high];
                nums[high] = nums[mid];
                nums[mid] = temp;
                
                high--;
                // Note: we do NOT increment mid here! The element we just swapped 
                // in from `high` could be a 0, 1, or 2, so `mid` must evaluate it next loop!
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("--- DUTCH NATIONAL FLAG DEMO ---");
        
        int[] arr = {2, 0, 2, 1, 1, 0};
        System.out.println("Original: " + Arrays.toString(arr));
        
        sortColors(arr);
        System.out.println("Sorted:   " + Arrays.toString(arr)); 
        // Expected: [0, 0, 1, 1, 2, 2]
    }
}
