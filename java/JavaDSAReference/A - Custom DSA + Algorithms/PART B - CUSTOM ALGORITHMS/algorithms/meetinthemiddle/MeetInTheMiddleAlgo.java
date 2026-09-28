package algorithms.meetinthemiddle;

import java.util.Arrays;

/**
 * MEET IN THE MIDDLE
 * 
 * WHAT IT IS:
 * An incredibly powerful technique built ON TOP of Backtracking to drastically 
 * reduce time complexity. 
 * 
 * WHEN TO USE THIS:
 * - When you face an exponential O(2^N) problem (like finding if any subset sums 
 *   to a target) where N is too large for pure brute force (e.g., N = 40), 
 *   but small enough that N/2 is tractable (2^20 is only ~1 million operations).
 * 
 * STRATEGY:
 * 1. Split the array perfectly in half.
 * 2. Run Backtracking to find all possible subset sums of the LEFT half (2^(N/2) sums).
 * 3. Run Backtracking to find all possible subset sums of the RIGHT half (2^(N/2) sums).
 * 4. Sort one of the halves.
 * 5. Iterate through the other half, and use Binary Search (or Two Pointers/HashMap) 
 *    to see if there is a complementary sum!
 * 
 * COMPLEXITY:
 * Time: Drops from O(2^N) down to O(2^(N/2) * log(2^(N/2))).
 * Space: O(2^(N/2)) to store the half-sums.
 */
public class MeetInTheMiddleAlgo {

    /**
     * Problem: Determine if ANY subset of the array sums exactly to the target.
     * Uses Meet in the Middle to solve in O(2^(N/2)) instead of O(2^N).
     */
    public static boolean hasSubsetSum(int[] arr, int target) {
        int n = arr.length;
        if (n == 0) return target == 0;

        int mid = n / 2;
        
        // 1. Split into two halves
        int[] leftArr = Arrays.copyOfRange(arr, 0, mid);
        int[] rightArr = Arrays.copyOfRange(arr, mid, n);

        // 2. Generate all 2^(N/2) subset sums for both halves
        int[] leftSums = new int[1 << leftArr.length];
        int[] rightSums = new int[1 << rightArr.length];
        
        generateSums(leftArr, 0, 0, leftSums, new int[]{0});
        generateSums(rightArr, 0, 0, rightSums, new int[]{0});

        // 3. Sort the right sums so we can binary search it
        Arrays.sort(rightSums);

        // 4. For every sum in the left half, binary search for the required remainder in the right half
        for (int lSum : leftSums) {
            int remainder = target - lSum;
            if (binarySearch(rightSums, remainder)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Standard recursive backtracking to generate all subset sums.
     */
    private static void generateSums(int[] arr, int index, int currentSum, int[] results, int[] resIndex) {
        if (index == arr.length) {
            results[resIndex[0]++] = currentSum;
            return;
        }
        
        // Exclude
        generateSums(arr, index + 1, currentSum, results, resIndex);
        // Include
        generateSums(arr, index + 1, currentSum + arr[index], results, resIndex);
    }

    private static boolean binarySearch(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) return true;
            if (arr[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println("--- MEET IN THE MIDDLE DEMO ---");
        
        // N = 6 (Too small to normally require MitM, but perfect for a demo)
        int[] arr = {45, 34, 4, 12, 5, 2};
        int target = 41; // Can be made with 34 + 5 + 2
        
        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("Target Sum: " + target);
        
        boolean result = hasSubsetSum(arr, target);
        System.out.println("Has Subset Sum? " + result); // Expected: true
    }
}
