package practiceQuestions.algomapio.slidingwindow.medium;

import java.sql.Time;
import java.util.HashSet;

public class LongestSubstringWithoutRepeatingCharacters {

    /**
     * Brute Force
     * Thought Process: To find the longest substring without repeating characters, we can generate all possible substrings and check each for duplicate characters. For each substring, we track its length and update the maximum length if it has no repeats.
     * Development Steps:
     * Initialize max_length to store the length of the longest valid substring.
     * Use two nested loops: outer loop (i) for the start of the substring, inner loop (j) for the end.
     * For each substring s[i:j+1], check if the current character s[j] is already in s[i:j]. If so, break the inner loop as the substring now has a repeat.
     * If no repeat is found, compute the substring length (j - i + 1) and update max_length if larger.
     * Return max_length.
     * Analysis:
     * Time Complexity: O(n³) - Two nested loops generate O(n²) substrings, and checking for repeats in a substring of length up to n takes O(n), leading to O(n³).
     * Space Complexity: O(n) - In Python, slicing s[i:j] creates a new string, which can use up to O(n) space for the largest substring.
     * Why Improve?: The O(n³) time complexity is inefficient for large strings, and the repeated scanning of substrings is redundant. We need a way to track characters more efficiently and avoid generating all substrings.
     * */

    public int bruteForce(String s) {
        // brute force
        // Time Complexity: O(n^3)
        // Space Complexity: O(n)
        int longest = 0;
        int n = s.length();
        for (int i = 0; i < n; i++) {
            for (int substr_len = 1; i + substr_len <= n; ++substr_len) {
                HashSet<Character> seen = new HashSet<>();
                for (int j = i; j < i + substr_len; ++j)
                    seen.add(s.charAt(j));

                if (seen.size() == substr_len)
                    longest = Math.max(longest, substr_len);
            }
        }

        return longest;

        /**
         * Why the Brute-Force Solution is Inefficient
         * The brute-force solution checks every possible substring and validates for repeating characters, leading to:
         *
         * Time Complexity: O(n³), where n is the length of the string, as it uses nested loops to generate substrings (O(n²)) and checks for repeating characters in each substring (O(n)).
         * Space Complexity: O(n), as string slicing may create temporary strings.
         * Performance Issue: The cubic time complexity makes it impractical for large strings, as it redundantly checks many substrings and performs expensive character searches.
         * */
    }

    /**
     * Optimal Solution: Sliding Window with Set
     * To address the inefficiency of redundant calculations, we introduce memoization to store previously computed values.
     *
     * Thought Process: We observe that in the recursive solution, values like F(2) are computed multiple times. By storing results in a dictionary, we can retrieve them in O(1) time instead of recalculating.
     * Development Steps:
     * Create a dictionary memo initialized with base cases {0:0, 1:1}.
     * Define a helper function f(x) that checks if x is in memo. If so, return the stored value; otherwise, compute and store f(x-1) + f(x-2).
     * Call f(n) to get the result.
     * Analysis:
     * Time Complexity: O(n) - Each Fibonacci number is computed once, and lookups are O(1).
     * Space Complexity: O(n) - The dictionary stores n values, and the recursion stack uses O(n) space.
     * */

    public int optimal(String s) {
        // Time Complexity: O(n)
        // Space Complexity: O(n) (but because we're limited to common characters only, it's actually O(1))

        HashSet<Character> set = new HashSet<>();
        int l = 0, longest = 0;

        for (int r = 0; r < s.length(); r++) {
            while (set.contains(s.charAt(r))) {
                set.remove(s.charAt(l));
                l++;
            }

            longest = Math.max(longest, r - l + 1);
            set.add(s.charAt(r));
        }

        return longest;
    }
}

/**
 * Detailed Explanation
 * Understanding the Problem
 * The "Longest Repeating Character Replacement" problem involves finding the length of the longest possible substring in which all characters are the same after performing at most k replacements. Each replacement can change one character to any other uppercase English letter. The goal is to find the maximum length of such a transformed substring.
 *
 * Sliding Window Technique
 * To efficiently solve this problem, we use the sliding window approach. This allows us to examine every potential substring while keeping time complexity linear. The idea is to maintain a window of characters and expand it until more than k characters in the window need to be replaced to make all characters in the window identical.
 *
 * Tracking Character Frequencies
 * Within the sliding window, we track how many times each character appears using a frequency counter (an array of size 26 for the uppercase English alphabet). At each step, we also track the count of the most frequent character in the current window. The number of replacements needed for the current window is equal to the window size minus this maximum frequency.
 *
 * If the required replacements exceed k, we shrink the window from the left. Otherwise, we continue expanding the window to the right.
 *
 * Step-by-Step Algorithm
 * Initialize longest to 0 to track the longest valid substring.
 * Use two pointers l and r to define the sliding window.
 * Use a frequency array counts of size 26 to store the count of each character in the window.
 * For each character at index r in the string:
 * Increment the count of that character in counts.
 * Update the maximum frequency max_count within the current window.
 * If (r - l + 1) - max_count > k, shrink the window from the left:
 * Decrement the count of s[l].
 * Increment l.
 * Update longest as the maximum of its current value and the window size (r - l + 1).
 * Return longest as the final result.
 * Why This Works
 * The sliding window ensures we examine each character only once, making the algorithm highly efficient. By only tracking the maximum frequency and maintaining character counts, we avoid recalculating data unnecessarily. The core insight is recognizing that to make a window valid, we only need to ensure at most k characters differ from the most frequent character.
 *
 * Time and Space Complexity
 * The time complexity of the algorithm is O(n), where n is the length of the string, since each character is visited at most twice (once by the right pointer, once by the left pointer). The space complexity is O(1) as the character frequency array is fixed at size 26, regardless of the input size.
 * */
