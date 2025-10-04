package practiceQuestions.algomapio.hashmapsandsets.easy;

import java.util.HashMap;
import java.util.Map;

public class MajorityElement {

    /**
     * Brute Force
     * Understand the problem: Find the majority element in an array, which appears more than n/2 times.
     * Initialize an empty dictionary counter to store the frequency of each number.
     * Iterate through each number in nums, incrementing its count in counter or initializing it to 1 if unseen.
     * Initialize max_count to -1 and ans to -1 to track the highest frequency and corresponding number.
     * Iterate through counter, updating max_count and ans if the current count is greater than max_count.
     * Return ans as the majority element.
     * */

    private static int bruteForce(int[] nums) {
        Map<Integer, Integer> counter = new HashMap<>();

        for(int num : nums) {
            counter.put(num, counter.getOrDefault(num, 0) + 1);
        }

        int maxCount = -1;
        int ans = -1;
        for(int key : counter.keySet()) {
            if(counter.get(key) > maxCount) {
                maxCount = counter.get(key);
                ans = key;
            }
        }

        return ans;
    }

    /**
     * Optimal Solution
     * The optimal solution uses Boyer-Moore Voting Algorithm, achieving O(n) time and O(1) space:
     *
     * Initialize candidate to None and count to 0.
     * Iterate through each number in nums.
     * If count is 0, set candidate to the current number.
     * Increment count if the current number equals candidate; otherwise, decrement count.
     * Return candidate, as it is guaranteed to be the majority element.
     * */
    private static int optimal(int[] nums) {
        int candidate = nums[0];
        int count = 0;

        for(int num : nums) {
            if(count == 0) {
                candidate = num;
            }
            count += (candidate == num) ? 1 : -1;
        }

        return candidate;
    }

    public static void main(String[] args) {
        int[] nums = {2, 2, 1, 1, 1, 2, 2};
        System.out.println(bruteForce(nums));
        System.out.println(optimal(nums));
    }
}


/**
 * Detailed Explanation
 * Understanding the Problem: Majority Element
 * The “Majority Element” problem asks us to find the element in an array that appears more than ⌊n / 2⌋ times, where n is the length of the array. It is guaranteed that such an element always exists.
 *
 * For example:
 *
 * Input: [3, 2, 3] → Output: 3
 * Input: [2, 2, 1, 1, 1, 2, 2] → Output: 2
 * The goal is to return the majority element — the one that occurs more than half the time.
 * Why This Problem Matters
 * This problem highlights techniques in frequency counting and vote-based tracking. It’s a stepping stone to more advanced voting or consensus algorithms and teaches you how to efficiently identify dominant elements in linear time and constant space.
 *
 * Common Approach: Frequency Counting with a Hash Map
 * A straightforward way to solve the problem is by using a hash map (or dictionary) to count the frequency of each number in the array.
 *
 * Steps:
 * Initialize an empty dictionary to track the count of each number.
 * Iterate through the array and increment the count for each number.
 * Check each number’s count — if it exceeds n / 2, return it as the majority element.
 * This approach runs in linear time but requires O(n) space in the worst case, which is not optimal given the constraints.
 *
 * Optimized Solution: Boyer-Moore Voting Algorithm
 * A more elegant and space-efficient method is the Boyer-Moore Voting Algorithm. This algorithm is based on the fact that the majority element appears more than all other elements combined, so it can “cancel out” minority votes.
 *
 * Steps:
 * Initialize two variables: candidate = null and count = 0.
 * Iterate through the array:
 * If count == 0, set candidate = current number.
 * If current number == candidate, increment count.
 * Otherwise, decrement count.
 * After the loop, return candidate. Since a majority element is guaranteed to exist, no second verification is needed.
 * Example Walkthrough
 * Input: [2, 2, 1, 1, 1, 2, 2]
 * Tracking steps:
 *
 * candidate = 2, count = 1
 * 2 again → count = 2
 * 1 → count = 1
 * 1 → count = 0 → new candidate = 1
 * 1 → count = 1
 * 2 → count = 0 → new candidate = 2
 * 2 → count = 1
 * Final candidate is 2, which is the majority element.
 *
 * Time and Space Complexity
 * Time Complexity: O(n), where n is the number of elements in the array.
 * Space Complexity: O(1), since the Boyer-Moore algorithm only uses two variables.
 *
 * Edge Cases to Consider
 * All elements are the same → return that element
 * Majority appears at the start or end → still valid
 * Only one element → return it (it’s trivially the majority)
 * Conclusion
 * The “Majority Element” problem is a powerful lesson in identifying dominant patterns with minimal memory. The Boyer-Moore Voting Algorithm transforms what seems like a counting problem into a clever linear scan using a vote-counter technique. It's one of the most elegant examples of reducing space complexity without sacrificing performance.
 * */