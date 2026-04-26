package educativetutorials.codingpatterns.threeslidingwindow.hard;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.stream.Stream;

public class SlidingWindowMaximum {

    // function to clean up the window
    public static Deque<Integer> cleanUp(int i, Deque<Integer> currentWindow, int[] nums) {
        while(currentWindow.size() != 0 && nums[i] >= nums[currentWindow.getLast()]) {
            currentWindow.removeLast();
        }
        return currentWindow;
    }

    // function to find the maximum in all possible windows
    public static int[] findMaxSlidingWindow(int[] nums, int w) {
        if(nums.length == 1) return nums;

        int output[] = new int[nums.length - w + 1];
        Deque<Integer> currentWindow = new ArrayDeque<>();
        for(int i = 0; i < w; i++) {
            currentWindow = SlidingWindowMaximum.cleanUp(i, currentWindow, nums);
            currentWindow.add(i);
        }

        output[0] = nums[currentWindow.getFirst()];
        for(int i = w; i < nums.length; i++) {
            cleanUp(i, currentWindow, nums);
            if (!currentWindow.isEmpty() && currentWindow.getFirst() <= (i - w)) {
                currentWindow.removeFirst();
            }
            currentWindow.add(i);
            output[i - w + 1] = nums[currentWindow.getFirst()];
        }
        return output;
    }

    // driver code
    public static void main(String args[]) {
        int windowSizes [] = {3, 3, 3, 3, 2, 4, 3, 2, 3, 6};
        int [][] numLists = {
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                {10, 9, 8, 7, 6, 5, 4, 3, 2, 1},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
                {1, 5, 8, 10, 10, 10, 12, 14, 15, 19, 19, 19, 17, 14, 13, 12, 12, 12, 14, 18, 22, 26, 26, 26, 28, 29, 30},
                {10, 6, 9, -3, 23, -1, 34, 56, 67, -1, -4, -8, -2, 9, 10, 34, 67},
                {4, 5, 6, 1, 2, 3},
                {9, 5, 3, 1, 6, 3},
                {2, 4, 6, 8, 10, 12, 14, 16},
                {-1, -1, -2, -4, -6, -7},
                {4, 4, 4, 4, 4, 4}
        };

        for (int i = 0; i < numLists.length; i++) {
            System.out.println(i + 1 + ".\tInput array:\t" + Arrays.toString(numLists[i]));
            System.out.println("\tWindow size:\t" + windowSizes[i]);
            System.out.println("\n\tMaximum in each sliding window:\t" + Arrays.toString(findMaxSlidingWindow(numLists[i], windowSizes[i])));
            Stream.generate(() -> "-").limit(100).forEach(System.out::print);
            System.out.println();
        }
    }
}


/**
 * Statement:
 * You are given an array of integers nums and a sliding window of size w
 * that moves from left to right across the array, shifting one position at a time.
 * Your task is to find the maximum value within the current window at each step
 * and return it.
 *
 * Constraints:
 * 1 ≤ nums.length ≤ 10^3
 * -10^4 ≤ nums[i] ≤ 10^4
 * 1 ≤ w ≤ nums.length
 *
 * Solution:
 *
 * Naive Approach:
 * - Slide the window across the array and find the maximum in each window.
 * - For each window, scan all elements linearly.
 * - Time Complexity: O(n * w)
 * - Space Complexity: O(w)
 *
 * Optimized Approach (Sliding Window + Deque):
 * - Use a deque to store indices of useful elements.
 * - Maintain elements in decreasing order of values.
 * - The front always stores the index of the maximum element.
 *
 * Steps:
 * 1. Remove smaller elements from the back of the deque.
 * 2. Remove the front element if it is outside the current window.
 * 3. Add current index to the deque.
 * 4. The front of the deque gives the maximum for the window.
 *
 * Explanation:
 * - The deque stores indices of elements that can be maximum.
 * - Smaller elements are removed since they cannot be future maximums.
 * - The largest element always stays at the front.
 *
 * Time Complexity Analysis:
 * - Each element is added and removed at most once.
 * - Works efficiently for:
 *   • Increasing arrays
 *   • Decreasing arrays
 *   • Constant arrays
 *   • Mixed arrays
 * - Overall Time Complexity: O(n)
 *
 * Space Complexity:
 * - O(w), where w is the window size.
 */