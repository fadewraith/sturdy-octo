package practiceQuestions.algomapio.arraysAndStrings;

public class RemoveDuplicatesFromSortedArray {

    private static int solution(int[] nums) {
        if(nums.length == 0) return 0;
        int j = 1;
        for(int i = 1; i < nums.length; i++) {
            if(nums[i] != nums[i - 1]) {
                nums[j] = nums[i];
                j++;
            }
        }
        return j;
    }
}


/**
 * Step-by-Step Thought Process
 * The problem requires removing duplicates from a sorted array in-place, such that each unique element appears only once, and returning the length of the resulting array. Since the array is sorted, identical elements are adjacent, which we can leverage to identify duplicates efficiently. The solution uses a two-pointer approach to maintain a subarray of unique elements at the start of the array. Here’s how it works:
 *
 * Initial Setup: We initialize a pointer j to 1, representing the position where the next unique element should be placed. The first element at index 0 is always unique, so we start checking from index 1.
 * Iterating and Comparing: We loop through the array starting from index 1 up to the end. For each element at index i, we compare it with the previous element (nums[i-1]). If they differ, nums[i] is a new unique element.
 * Placing Unique Elements: When a unique element is found (i.e., nums[i] != nums[i-1]), we place it at index j by setting nums[j] = nums[i] and increment j. This ensures that the subarray from index 0 to j-1 contains only unique elements in their original order.
 * Result: After the loop, j represents the number of unique elements, and the first j elements of nums contain the unique elements in sorted order. We return j as the new array length.
 * Why This Works: The sorted nature of the array ensures that duplicates are adjacent, so comparing each element with its predecessor is sufficient to identify unique elements. Using a single pointer j to track the position for the next unique element allows us to modify the array in-place efficiently, without needing extra space.
 * Time and Space Complexity: The solution iterates through the array once, resulting in a time complexity of O(n), where n is the array length. Since we modify the array in-place, the space complexity is O(1).
 *
 *
 * */

/**
 * Detailed Explanation
 * Understanding the Problem
 * The goal of this problem is to remove duplicate entries from a sorted array nums such that each element appears only once and return the new length of the modified array. Importantly, this must be done in-place without allocating additional space. Since the array is sorted, duplicates are guaranteed to be adjacent, simplifying the comparison process.
 *
 * Two-Pointer Strategy
 * To solve this efficiently, we use the two-pointer technique. One pointer (i) scans through the array, and another pointer (j) keeps track of the position where the next unique value should be inserted. Initially, j is set to 1, since the first element is always unique by default. We then start iterating from the second element (index 1).
 *
 * During each iteration, if the current element nums[i] is not equal to nums[i - 1], it means we have encountered a new unique value. In that case, we store this new value at the j index and increment j by 1. This ensures that the subarray nums[0..j-1] always contains the unique values in their original relative order.
 *
 * Example
 * For input [1, 1, 2, 2, 3], the pointer i moves through each index, and whenever it finds a new value different from the one before it, that value is placed at position j. At the end of the process, nums will look like [1, 2, 3, _, _], and the function will return 3.
 *
 * Time and Space Complexity
 * This algorithm operates in linear time O(n), where n is the number of elements in the input array. It also satisfies the space constraint, as no additional data structures are used — only pointers within the original array.
 *
 * Why This Approach Works
 * The key advantage of this method is its efficiency in both time and space. The sorted property ensures that all duplicates are adjacent, making it easy to compare current and previous elements. The two-pointer technique enables in-place modification while preserving the input's structure and constraints. It’s a classic example of space-efficient array manipulation commonly asked in technical interviews.
 * */