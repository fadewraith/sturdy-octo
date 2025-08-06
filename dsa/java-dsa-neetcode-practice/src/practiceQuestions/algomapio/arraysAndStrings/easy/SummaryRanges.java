package practiceQuestions.algomapio.arraysAndStrings.easy;

import java.util.ArrayList;
import java.util.List;

public class SummaryRanges {
    /**
     * Step-by-Step Thought Process
     * Understand the problem: Given a sorted array of unique integers, return a list of strings representing ranges of consecutive numbers.
     * Initialize an empty list ans to store the range strings.
     * Initialize an index i to 0 to traverse the array.
     * While i is less than the length of nums, set start to nums[i] as the beginning of a range.
     * While i is less than length-1 and nums[i] + 1 equals nums[i+1], increment i to extend the range.
     * If start equals nums[i], append str(nums[i]) to ans as a single-number range. If start is not equal to nums[i], append the range string str(start) + "->" + str(nums[i]) to ans.
     * Increment i to start the next range.
     * Return ans as the list of range strings.
     * */

    private static List<String> solution(int[] nums) {
        List<String> ans = new ArrayList<>();
        int n = nums.length;
        int i = 0;
        while(i < n) {
            int start = nums[i];
            while(i < n - 1 && nums[i] + 1 == nums[i + 1]) {
                i++;
            }
            if(start != nums[i]) {
                ans.add(start + " -> " + nums[i]);
            } else {
                ans.add(String.valueOf(nums[i]));
            }
            i++;
        }
        return ans;
    }

}

/**
 * Detailed Explanation
 * Understanding the Problem: Summary Ranges
 * The “Summary Ranges” problem asks us to process a sorted array of unique integers and return a compact list of string representations of all consecutive ranges. Each range should be expressed in the form "a->b" if a sequence exists, or just "a" if it stands alone.
 *
 * For example, given the input [0, 1, 2, 4, 5, 7], the output should be ["0->2", "4->5", "7"]. This output represents three distinct segments of consecutive numbers compacted into human-readable strings.
 *
 * Why This Problem Matters
 * This problem tests your ability to group data based on patterns (in this case, numerical consecutiveness). It is a simplified introduction to interval problems, sequence merging, and streaming data summarization, which appear in time-series data processing, memory management, and log compression.
 *
 * Approach: Brute-Force Consecutive Range Detection
 * A straightforward way to solve this problem is by scanning through the list from left to right, identifying the start of a new range, and then continuing as long as the next number is consecutive. Once the consecutive run ends, we finalize the current range and add it to our output.
 *
 * This approach works because the input is guaranteed to be sorted in strictly increasing order with no duplicates.
 *
 * Step-by-Step Algorithm
 * Initialize an empty list ans to hold the formatted result strings.
 * Set a pointer i = 0 to iterate through the nums array.
 * While i is less than the length of the array:
 * Record start = nums[i] as the beginning of a potential range.
 * While i < len(nums) - 1 and nums[i] + 1 == nums[i+1], increment i to extend the range.
 * After the loop:
 * If start == nums[i], this was a single number. Append str(start).
 * Otherwise, append the formatted range: str(start) + "->" + str(nums[i]).
 * Increment i to start a new range.
 * Return the list ans after completing the loop.
 * Example Walkthrough
 * Input: [0, 2, 3, 4, 6, 8, 9]
 * Let’s analyze the ranges:
 *
 * Start at 0 → no following consecutive numbers → add "0".
 * Start at 2 → 3, 4 are consecutive → add "2->4".
 * Start at 6 → no continuation → add "6".
 * Start at 8 → 9 follows → add "8->9".
 * Final output: ["0", "2->4", "6", "8->9"].
 *
 * Alternative Approach: Two-Pointer Technique
 * You can also solve this with a two-pointer technique, where one pointer marks the start of a range, and another pointer moves forward to find where the range ends. Once you detect a break in continuity, record the range and reset the twopointers.
 *
 * This method can help when adapting the logic for a streaming or chunked input, where you might need to buffer input as it arrives and summarize it in real time.
 *
 * Time and Space Complexity
 * Time Complexity: O(n), where n is the number of elements in the input list. Every element is visited at most twice (once to detect the start, once to find the end of a range).
 * Space Complexity: O(1) extra space (excluding the output list), as we only use a few twopointers and intermediate variables.
 *
 * Edge Cases to Consider
 * If the input array is empty, return an empty list.
 * If the array contains only one number, return a list with that number as a string.
 * If there are no consecutive numbers, return each number as an individual range.
 * Conclusion
 * The “Summary Ranges” problem is a great introduction to working with intervals and sequences. It teaches you how to recognize and group contiguous segments efficiently, using either iteration or pointer-based scanning. Once you understand how to track ranges and construct readable output, you're well on your way to solving more advanced problems like merging intervals, range coverage, and stream summarization.
 * */
