package educativetutorials.codingpatterns.threeslidingwindow.medium;

import java.util.Arrays;

public class FrequencyOfMostFrequentElement {

    private static int maxFrequency(int[] nums, int k) {

        // Sort the array to facilitate the sliding window approach
        Arrays.sort(nums);

        // 1. 'left' = Left pointer of the window
        // 2. 'maxFreq' = Stores the maximum frequency found
        int left = 0, maxFreq = 0;

        // Sum of elements within the current window
        long windowSum = 0;

        // Expand the window by moving the right pointer
        for(int right = 0; right < nums.length; right++) {

            // Target element to make frequent
            long target = nums[right];

            // Update the sum of elements in the window
            windowSum += target;

            // Check if the total required increments exceed k
            while((right - left + 1) * target > (windowSum + k)) {

                // Remove the leftmost element
                windowSum -= nums[left];

                // Shrink the window from the left
                left++;
            }

            // Update the maximum frequency found
            maxFreq = Math.max(maxFreq, right - left + 1);
        }

        return maxFreq;
    }

    public static void main(String[] args) {
        int[][] testCases = {
                {1, 2, 4},
                {1, 4, 8, 13},
                {3, 6, 9},
                {2, 3, 5},
                {1, 1, 2},
                {4, 6, 8, 10},
                {10, 12, 5, 1, 15, 20, 13, 4, 7, 3, 9, 14, 2, 8, 6, 16, 11, 18, 19, 17},
                {5, 5, 5, 5, 6, 7, 8, 9, 10, 10, 10, 10, 10, 11, 12, 13, 14, 15, 16, 17}
        };
        int[] kValues = {5, 5, 2, 3, 2, 7, 50, 30};

        for (int i = 0; i < testCases.length; i++) {
            System.out.println((i + 1) + ".\tnums = " + Arrays.toString(testCases[i]));
            System.out.println("\tk = " + kValues[i]);
            System.out.println("\n\tOutput = " + maxFrequency(testCases[i], kValues[i]));
            System.out.println("-" + new String(new char[100]).replace('\0', '-') + "\n");
        }
    }
}


/**
You are given an integer array, nums, and an integer k, representing the maximum number of operations you can perform. In each operation, you may select any index in nums and increment its value by 1.

Your task is to determine the maximum possible frequency of a single element in the final array after performing at most k operations. You can choose to increase any elements in a way that results in one particular element appearing as often as possible (within k operations). For example, if nums = [2, 2, 3] and k = 4, you can increment the first and the second element, 2, once to match the third element, 3, achieving a maximum frequency of 3.

Return the highest frequency that can be achieved for any element in nums after at most k operations.

The frequency of an element is the number of times it appears in an array.

Constraints:

1 ≤ nums.length ≤ 10^3

1 ≤ nums[i] ≤ 10^3

1 ≤ k ≤ 10^3
*/

/**
 * Solution
 *
 * The best way to solve this problem is to convert smaller numbers into the largest possible number
 * while using the least number of operations. To achieve this, we use the sliding window technique,
 * which maintains a group of numbers that can be adjusted to match a target value (the largest number
 * in the window). The window size is increased or decreased dynamically based on the sum of its elements,
 * allowing us to quickly determine whether we can increase all numbers in the window to match the target
 * value within the allowed k operations. The largest window where this condition holds gives us the
 * maximum possible frequency.
 *
 * In this solution, we first sort nums so that we always transform smaller numbers first, which requires
 * fewer operations than modifying larger ones. This makes it easier to maintain a valid window where all
 * elements can be equal to the rightmost (largest) element.
 *
 * For any particular window, we decide to expand it further or shrink it based on the following condition:
 *
 * window_size * target_value <= window_sum + k
 *
 * This means that for a window to be valid, the total sum (if all elements were target) should not exceed
 * the actual sum of elements in the current window plus k allowed operations.
 *
 * If the condition is true and the window is valid, we add the next element of nums to the current window.
 *
 * Otherwise, if the condition is false, we shrink the window.
 */

/**
 * Algorithm Steps
 *
 * 1. Sort the array nums.
 *
 * 2. Initialize the following variables:
 *    - left = 0 (left pointer of the sliding window).
 *    - maxFreq = 0 (stores the maximum frequency found so far).
 *    - windowSum = 0 (keeps track of the sum of elements within the current window).
 *
 * 3. Iterate through nums using a sliding window (right pointer expands the window):
 *
 *    - Calculate the target value:
 *      target = nums[right] (rightmost element in the current window).
 *
 *    - Expand the window:
 *      Add nums[right] to windowSum.
 *
 *    - Check if the current window is valid using the condition:
 *      (right - left + 1) * target > windowSum + k
 *
 *      If this condition is true, it means more than k operations are needed to make all
 *      elements equal to the target. So, shrink the window:
 *
 *        - Subtract nums[left] from windowSum
 *        - Increment left to move the window forward
 *
 *    - After adjusting the window, update maxFreq.
 *
 * 4. Once the iteration is complete, return maxFreq.
 *
 * --------------------------------------------------
 * Code Explanation
 *
 * The algorithm uses sorting and a sliding window approach to efficiently find the
 * maximum frequency of an element after at most k increments.
 *
 * --------------------------------------------------
 * Time Complexity
 *
 * - Sorting the array takes O(n log n), where n is the length of nums.
 *
 * - The sliding window traversal takes O(n) time because the right pointer moves
 *   from 0 to n - 1.
 *
 * - The left pointer only moves forward and processes each element at most once,
 *   contributing another O(n) in the worst case.
 *
 * - Overall time complexity:
 *   O(n) + O(n log n) = O(n log n)
 *
 * --------------------------------------------------
 * Space Complexity
 *
 * - Java's sort() method typically requires O(log n) space.
 *
 * - Additional variables use constant space.
 *
 * - Overall space complexity:
 *   O(log n)
 */