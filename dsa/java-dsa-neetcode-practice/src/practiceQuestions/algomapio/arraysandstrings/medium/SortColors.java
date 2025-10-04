package practiceQuestions.algomapio.arraysandstrings.medium;

import java.util.Arrays;

public class SortColors {

    private static void solution(int[] nums) {
        // Sort an array of integers representing colors (0 for red, 1 for white, 2 for blue) in-place.
        // Initialize a counts array of size 3 to store the frequency of each color (0, 1, 2).
        int[] counts = new int[3];

        // Iterate through each number (color) in the array nums and increment counts[color].
        for(int color: nums) {
            counts[color]++;
        }

        // Extract the counts for red (R), white (W), and blue (B) from counts.
        int red = counts[0], white = counts[1], blue = counts[2];

        // Modify nums in-place: set nums[0:R] to R zeros, nums[R:R+W] to W ones, and nums[R+W:] to B twos.
        for(int i = 0; i < red; i++) {
            nums[i] = 0;
        }

        for(int i = red; i < red + white; i++) {
            nums[i] = 1;
        }

//        System.out.println(blue);

        for(int i = red + white; i < nums.length; i++) {
            nums[i] = 2;
        }

        System.out.println(Arrays.toString(nums));

    }

    public static void main(String[] args) {
        int nums[] = {2, 0, 2, 1, 1, 0, 1, 2};
        solution(nums);

    }
}


/**
 * Detailed Explanation
 * Understanding the Sort Colors Problem
 * The "Sort Colors" problem is a classic interview question that involves sorting an array of integers where each element represents a color: 0 for red, 1 for white, and 2 for blue. The challenge is to sort the array in-place so that all 0s come first, followed by 1s, then 2s. This is also known as the Dutch National Flag problem, introduced by Edsger W. Dijkstra.
 *
 * Conceptual Strategy
 * A brute-force approach might involve counting the frequency of each color and then writing those counts back to the array. While this works, it takes two passes over the data and isn’t as efficient as possible.
 *
 * A more optimal solution uses the Dutch National Flag algorithm, which utilizes three pointers to sort the array in a single pass, in-place and with constant space.
 *
 * Optimized Solution Using Dutch National Flag Algorithm
 * To solve this problem efficiently, we initialize three pointers: low, mid, and high. These will partition the array into three sections:
 *
 * Indices before low will contain 0s.
 * Indices between low and mid will contain 1s.
 * Indices after high will contain 2s.
 * We iterate through the array with mid. If nums[mid] is 0, we swap it with nums[low] and move both pointers forward. If it’s 2, we swap it with nums[high] and move only high backward. If it’s 1, we simply move mid forward.
 *
 * Time and Space Complexity
 * Time Complexity: O(n), where n is the length of the array. The algorithm only makes a single pass over the array.
 * Space Complexity: O(1), as the sorting is done in-place without using any additional data structures.
 * Why This Approach is Efficient
 * This method is preferred in interviews because it avoids the need for multiple passes or auxiliary storage. By using just a few pointers and conditional logic, the entire array can be sorted with minimal overhead, making it ideal for scenarios with memory or time constraints.
 *
 * Conclusion
 * The Sort Colors problem not only tests your understanding of sorting and in-place algorithms but also demonstrates the power of pointer manipulation in solving classification problems. Mastering this problem can help you tackle a variety of in-place sorting challenges in real-world software development.
 * */