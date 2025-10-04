package practiceQuestions.algomapio.slidingwindow.medium;

import java.sql.Time;

public class LongestRepeatingCharacterReplacement {

    /**
     * Step-by-Step Thought Process
     * Understand the problem: Find the length of the longest substring that can be made to have all the same character by replacing at most k characters.
     * Initialize longest to 0 to track the longest valid substring and left pointer l to 0.
     * Create an array counts of size 26 to track the frequency of each uppercase letter.
     * Iterate through each right index r from 0 to the length of s.
     * Increment the count of s[r] in counts using its ASCII value (ord(s[r]) - 65).
     * While the window size (r - l + 1) minus the maximum frequency in counts exceeds k, decrement the count of s[l] and increment l to shrink the window.
     * Update longest to the maximum of longest and the current window size (r - l + 1).
     * Return longest after the loop.
     * */

    	// Time Complexity: O(n)
        // Space Complexity: O(1)

    public int solution(String s, int k) {
        int[] counts = new int[26];
        int l = 0, longest = 0;
        int maxCount = 0;

        for (int r = 0; r < s.length(); r++) {
            maxCount = Math.max(maxCount, ++counts[s.charAt(r) - 'A']);

            while ((r - l + 1) - maxCount > k) {
                counts[s.charAt(l) - 'A']--;
                l++;
            }

            longest = Math.max(longest, r - l + 1);
        }

        return longest;
    }

}

/**
 * Detailed Explanation
 * Understanding the Problem: Longest Substring Without Repeating Characters
 * The “Longest Substring Without Repeating Characters” problem challenges you to find the length of the longest substring in a given string that contains only unique characters. A substring is a contiguous sequence of characters within the original string, and in this case, the substring must not contain any character more than once.
 *
 * For example, in the string "abcabcbb", the longest substring without repeating characters is "abc", which has a length of 3. The goal is not to return the substring itself, but rather the length of the longest such substring.
 *
 * Why This Problem Is Important
 * This is a foundational problem in the category of sliding window techniques. It's widely used in software interviews to assess a candidate’s ability to manipulate pointers and use auxiliary data structures to solve real-time string-processing tasks efficiently.
 *
 * Optimal Approach: Sliding Window with a Set
 * Instead of checking all possible substrings—which would be inefficient—we can use a two-pointer approach known as a sliding window to maintain a range of characters that are all unique. As we move through the string, we expand the window by adding one character at a time from the right. If we encounter a character that already exists in our current window, we shrink the window from the left until the duplicate character is removed.
 *
 * To implement this efficiently, we use a set to store the characters in the current window. As we iterate through the string, we check if the current character is already in the set. If it is, we remove characters from the start of the window (moving the left pointer forward) until the set no longer contains that character. Then we add the current character to the set and update the length of the longest valid substring found so far.
 *
 * Time and Space Complexity
 * The sliding window technique ensures that each character is added to and removed from the set at most once. This results in a time complexity of O(n), where n is the length of the string. The space complexity is also O(n), since the set may store up to n unique characters in the worst case.
 *
 * Edge Cases to Consider
 * You should handle empty strings by returning zero. If the string contains all unique characters, the result is the length of the entire string. If all characters are the same, the result is one, since each individual character is the only valid substring without repetition.
 *
 * Conclusion
 * The “Longest Substring Without Repeating Characters” problem is an elegant demonstration of how sliding window techniques can be used to reduce time complexity from cubic to linear. By keeping track of the characters dynamically and efficiently adjusting the window, we achieve both clarity and performance. This makes the solution highly scalable and applicable to real-time input processing such as streaming text or log data.
 * */