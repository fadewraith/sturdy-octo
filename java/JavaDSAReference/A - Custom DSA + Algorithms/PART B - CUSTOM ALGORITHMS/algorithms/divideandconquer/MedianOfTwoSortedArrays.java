package algorithms.divideandconquer;

/**
 * MEDIAN OF TWO SORTED ARRAYS
 * 
 * WHAT IT IS:
 * Given two sorted arrays of size M and N, find the median of the two sorted arrays.
 * 
 * WHEN TO USE THIS:
 * - This is a legendary hard interview question.
 * - Brute force is O(M + N) by merging the two arrays.
 * - Divide & Conquer pushes this down to O(log(min(M, N)))!
 * 
 * STRATEGY (Binary Search on Partition):
 * We don't need to merge the arrays; we just need to find a "Partition Line" that 
 * cuts both arrays such that:
 * 1. The total number of elements on the left side of the line equals the right side.
 * 2. Every element on the left side is <= every element on the right side.
 * 
 * We use Binary Search on the SMALLER array to find exactly where to draw this line!
 * 
 * COMPLEXITY:
 * Time: O(log(min(M, N)))
 * Space: O(1)
 */
public class MedianOfTwoSortedArrays {

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // Ensure we always binary search on the smaller array for efficiency
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }
        
        int x = nums1.length;
        int y = nums2.length;
        
        int low = 0;
        int high = x;
        
        while (low <= high) {
            int partitionX = (low + high) / 2;
            int partitionY = (x + y + 1) / 2 - partitionX; // +1 handles both even and odd total lengths
            
            // Edge cases: If partition is at the extreme edge, use MIN/MAX value
            int maxLeftX = (partitionX == 0) ? Integer.MIN_VALUE : nums1[partitionX - 1];
            int minRightX = (partitionX == x) ? Integer.MAX_VALUE : nums1[partitionX];
            
            int maxLeftY = (partitionY == 0) ? Integer.MIN_VALUE : nums2[partitionY - 1];
            int minRightY = (partitionY == y) ? Integer.MAX_VALUE : nums2[partitionY];
            
            // Check if we found the perfect partition line
            if (maxLeftX <= minRightY && maxLeftY <= minRightX) {
                // If total length is EVEN
                if ((x + y) % 2 == 0) {
                    return ((double) Math.max(maxLeftX, maxLeftY) + Math.min(minRightX, minRightY)) / 2;
                } 
                // If total length is ODD
                else {
                    return (double) Math.max(maxLeftX, maxLeftY);
                }
            }
            // We are too far right in nums1, move left
            else if (maxLeftX > minRightY) {
                high = partitionX - 1;
            }
            // We are too far left in nums1, move right
            else {
                low = partitionX + 1;
            }
        }
        
        throw new IllegalArgumentException("Arrays are not sorted!");
    }

    public static void main(String[] args) {
        System.out.println("--- MEDIAN OF TWO SORTED ARRAYS DEMO ---");
        int[] nums1 = {1, 3};
        int[] nums2 = {2};
        System.out.println("Median of [1,3] and [2]: " + findMedianSortedArrays(nums1, nums2)); // Expected 2.0
        
        int[] nums3 = {1, 2};
        int[] nums4 = {3, 4};
        System.out.println("Median of [1,2] and [3,4]: " + findMedianSortedArrays(nums3, nums4)); // Expected 2.5
    }
}
