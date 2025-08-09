package practiceQuestions.algomapio.hashmapsAndSets.hard;

import java.util.HashSet;
import java.util.Set;

public class LongestConsecutiveSequence {

    private static int solution(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for(int num : nums) {
            set.add(num);
        }

        int longest = 0;
        for(int num : set) {
            // This ensures we only begin from the beginning of a potential sequence.
            if(!set.contains(num - 1)) {
                int length = 1;
                int nextNum = num + 1;
                while(set.contains(nextNum)) {
                    length++;
                    nextNum++;
                }
                longest = Math.max(longest, length);
            }
        }
        return longest;
    }

    public static void main(String[] args) {
        // → Output: 4 (the sequence is [1, 2, 3, 4])
        int[] nums = {100, 4, 200, 1, 3, 2};
        System.out.println(solution(nums));
        // Output: 9 (the sequence is [0–8])
        int[] nums1 = {0, 3, 7, 2, 5, 8, 4, 6, 0, 1};
        System.out.println(solution(nums1));

    }
}


/**
 * Detailed Explanation
 * Understanding the Problem: Longest Consecutive Sequence
 * The “Longest Consecutive Sequence” problem asks us to find the length of the longest sequence of consecutive integers that can be formed from a given array of integers. The numbers in the sequence do not need to appear in order in the array, and duplicates should be ignored.
 *
 * For example:
 *
 * Input: [100, 4, 200, 1, 3, 2] → Output: 4 (the sequence is [1, 2, 3, 4])
 * Input: [0, 3, 7, 2, 5, 8, 4, 6, 0, 1] → Output: 9 (the sequence is [0–8])
 * Why This Problem Matters
 * This problem is commonly used to test your ability to optimize brute-force logic and efficiently track patterns using hash-based data structures. It teaches techniques in sequence construction, set-based lookup, and one-pass logic to eliminate redundancy.
 *
 * Efficient Approach Using a Set
 * The brute-force solution using sorting would require O(n log n) time. However, the optimal solution leverages a set for O(1) lookups, allowing us to solve the problem in O(n) time.
 *
 * Steps:
 * Convert the input array into a set to eliminate duplicates and allow constant-time lookups.
 * Initialize a variable longest to track the length of the longest sequence found.
 * Loop through each number num in the set:
 * Only start a sequence from num if num - 1 is not in the set. This ensures we only begin from the beginning of a potential sequence.
 * Initialize currentLength = 1 and currentNum = num + 1.
 * While currentNum is in the set, increment currentLength and currentNum.
 * Update longest to the maximum of itself and currentLength.
 * After the loop, return longest.
 * Example Walkthrough
 * Input: [100, 4, 200, 1, 3, 2]
 *
 * Converted set: {1, 2, 3, 4, 100, 200}
 * Start at 1 → check 2, 3, 4 → length = 4
 * 100 and 200 are standalone → no longer sequence found
 * Return: 4
 * Time and Space Complexity
 * Time Complexity: O(n), where n is the number of elements in the array. Each element is processed at most twice (once in the loop, once in the inner sequence).
 * Space Complexity: O(n), due to storing the elements in a set.
 *
 * Edge Cases to Consider
 * Empty array → return 0
 * Array with one number → return 1
 * Array with all duplicates → still count as a sequence of 1
 * Already sorted input → works efficiently without extra sorting
 * Conclusion
 * The “Longest Consecutive Sequence” problem is a great example of replacing brute-force logic with a hash set to achieve optimal time complexity. It rewards careful iteration and teaches the power of only initiating work when it is necessary — a valuable lesson in writing efficient algorithms.
 * */