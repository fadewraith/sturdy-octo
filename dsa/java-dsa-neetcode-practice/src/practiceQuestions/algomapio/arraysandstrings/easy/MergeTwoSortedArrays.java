package practiceQuestions.algomapio.arraysandstrings.easy;

public class MergeTwoSortedArrays {

    private static void solution(int[] nums1, int n1, int[] nums2, int n2) {
        // reverse 2 twopointers, below are the 2 twopointers
        // n1 = len(nums1), n2 = len(nums2)
        int p1 = n1 - 1, p2 = n2 - 1;

        for(int i = p1 + p2 - 1; i >= 0; i--) {
            if(p1 < 0) {
                nums1[i] = nums2[p2--];
            } else if(p2 < 0) {
                break;
            } else if(nums1[p1] > nums2[p2]) {
                nums1[i] = nums1[p1--];
            } else {
                nums1[i] = nums2[p2--];
            }
        }
    }
}


/**
 * Step-by-Step Thought Process
 * The problem requires merging two sorted arrays, nums1 (with length m and extra space) and nums2 (with length n), into nums1 in sorted order, modifying nums1 in-place. The extra space in nums1 is sufficient to hold all elements from both arrays.
 *
 * The solution uses a reverse two-pointer technique to merge the arrays from the end, avoiding overwriting elements prematurely. Here’s how it works:
 *
 * Initial Setup: We initialize two twopointers: x at m-1 (last element of nums1’s valid portion) and y at n-1 (last element of nums2). A third pointer, z, starts at m+n-1 (the last position in nums1) to place merged elements.
 * Merging from the End: We iterate backwards, placing the largest remaining element at nums1[z]:
 * If x < 0, nums1 is exhausted, so we copy nums2[y] to nums1[z] and decrement y.
 * If y < 0, nums2 is exhausted, so we stop as the remaining elements in nums1 are already in place.
 * Otherwise, compare nums1[x] and nums2[y]. If nums1[x] > nums2[y], place nums1[x] at nums1[z] and decrement x. Else, place nums2[y] at nums1[z] and decrement y.
 * Decrement z after each placement.
 * Result: After the loop, nums1 contains the merged sorted array.
 * Why This Works: Merging from the end leverages the extra space in nums1, ensuring we don’t overwrite elements before they’re processed. Since both arrays are sorted, comparing the largest remaining elements ensures the merged array is sorted. Handling edge cases (e.g., one array being exhausted) ensures correctness.
 * Time and Space Complexity: The solution processes each element once, resulting in a time complexity of O(m+n). Since it modifies nums1 in-place, the space complexity is O(1).
 *
 *
 * */


/**
 * Detailed Explanation
 * Understanding the Problem
 * The "Merge Sorted Array" problem involves merging two sorted arrays, nums1 and nums2, into a single sorted array in-place within nums1. The array nums1 has a length of m + n, where the first m elements are valid numbers and the remaining n elements are placeholders (typically zeros). The array nums2 has n elements and must be merged into nums1 in sorted order.
 *
 * Optimal Strategy: Reverse Two-Pointer Technique
 * To solve this efficiently without extra space, we use a reverse two-pointer approach. Instead of merging from the front, which would require shifting elements and potentially overwriting data, we merge from the end of the arrays. This takes advantage of the fact that the trailing space in nums1 is unused and can be populated from the back.
 *
 * We initialize three twopointers:
 *
 * x starts at m - 1, pointing to the last valid element in nums1.
 * y starts at n - 1, pointing to the last element in nums2.
 * z starts at m + n - 1, which is the last index in nums1.
 * These twopointers help us compare elements from both arrays and place the larger one at position z, then move backwards.
 * Step-by-Step Merging Logic
 * We iterate while y >= 0:
 *
 * If x >= 0 and nums1[x] > nums2[y], we place nums1[x] at nums1[z] and move x and z back by one.
 * Otherwise, we place nums2[y] at nums1[z] and move y and z back by one.
 * This process continues until all elements from nums2 are placed correctly in nums1.
 * There is no need to explicitly handle the remaining elements of nums1 once nums2 is exhausted, because they are already in the correct position.
 *
 * Example
 * Consider nums1 = [1,2,3,0,0,0] and nums2 = [2,5,6] with m = 3 and n = 3. We compare 3 and 6, place 6 at the end, then continue comparing and inserting values in descending order. The final nums1 becomes [1,2,2,3,5,6].
 *
 * Time and Space Complexity
 * The time complexity is O(m + n), as each element in both arrays is processed exactly once. The space complexity is O(1), as no additional data structures are used—everything is handled in-place using the extra space in nums1.
 *
 * Why This Approach Works
 * This reverse merging strategy is both efficient and safe. By filling nums1 from the back, we eliminate the risk of overwriting any valid data that hasn’t been compared yet. This technique is a classic example of how understanding problem constraints—like sorted order and in-place requirements—can lead to an optimal and elegant solution.
 * */