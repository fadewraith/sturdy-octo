package practiceQuestions.algomapio.slidingwindow.medium;

import java.util.Arrays;

public class PermutationInString {

    /**
     *  Step-by-Step Thought Process
     * Understand the problem: Check if a string s2 contains a permutation of string s1 as a substring.
     * Get the lengths n1 and n2 of s1 and s2; if n1 > n2, return False.
     * Initialize two arrays, s1_counts and s2_counts, of size 26 to store character frequencies for lowercase letters.
     * For the first n1 characters, increment s1_counts for each character in s1 and s2_counts for each character in s2, using ord(char) - 97 as the index.
     * Check if s1_counts equals s2_counts; if true, return True.
     * For each index i from n1 to n2-1:
     * Increment s2_counts for the new character s2[i].
     * Decrement s2_counts for the character s2[i-n1] that left the window.
     * If s1_counts equals s2_counts, return True.
     * Return False if no permutation is found.
     *
     * 	# Time Complexity: O(n)
     * # Space Complexity: O(1)
     * */

    public boolean checkInclusion(String s1, String s2) {
        int n1 = s1.length();
        int n2 = s2.length();

        if (n1 > n2) return false;

        int[] s1Counts = new int[26];
        int[] s2Counts = new int[26];

        for (int i = 0; i < n1; i++) {
            s1Counts[s1.charAt(i) - 'a']++;
            s2Counts[s2.charAt(i) - 'a']++;
        }

        if (Arrays.equals(s1Counts, s2Counts)) return true;

        for (int i = n1; i < n2; i++) {
            s2Counts[s2.charAt(i) - 'a']++;
            s2Counts[s2.charAt(i - n1) - 'a']--;
            if (Arrays.equals(s1Counts, s2Counts)) return true;
        }

        return false;
    }
}


/**
 * Detailed Explanation
 * Understanding the Problem: Permutation in String
 * The “Permutation in String” problem asks whether one string, let's call it s2, contains a permutation of another string s1 as a substring. In other words, we want to know if any substring of s2 has exactly the same characters as s1, just possibly in a different order. This is essentially a sliding window pattern problem with a frequency comparison of character counts.
 *
 * For example, if s1 = "ab" and s2 = "eidbaooo", then s2 contains "ba", which is a valid permutation of s1, so the output should be true. If s2 = "eidboaoo", then there is no such substring that matches a permutation of s1, and the result should be false.
 *
 * Optimal Approach: Sliding Window with Character Frequency Arrays
 * The optimal solution uses a fixed-size sliding window of length equal to the length of s1. As the window slides over s2, we compare the character frequency counts within the window to the frequency counts of s1. If the counts match exactly, it means a valid permutation has been found.
 *
 * To do this efficiently, we use two arrays of length 26 (one for each lowercase English letter). The first array stores the frequency of each character in s1. The second array stores the frequencies of characters in the current window of s2. We begin by populating both arrays with the first n1 characters—where n1 is the length of s1.
 *
 * If the frequency arrays match at the start, we can immediately return true. If not, we begin sliding the window forward one character at a time. For each new character that enters the window, we increment its count in the s2 array. For each character that exits the window (from the left side), we decrement its count. After each shift, we compare the two arrays again. If they match, a valid permutation has been found.
 *
 * Time and Space Complexity
 * The time complexity of this algorithm is O(n2), where n2 is the length of s2. This is because we scan through s2 using a window of fixed size, and at each step we perform a comparison of two fixed-length arrays, which takes constant time. The space complexity is O(1) because both frequency arrays are of fixed size (26), regardless of input size.
 *
 * Edge Cases to Consider
 * If the length of s1 is greater than s2, we can return false immediately since a longer string cannot be a substring of a shorter one. If both strings are empty, or s1 is empty, the problem constraints typically assume this should return true because an empty string is trivially a permutation of any substring.
 *
 * Conclusion
 * The “Permutation in String” problem is a clever application of the sliding window technique combined with frequency counting. It highlights how fixed-size windowed comparisons can lead to extremely efficient algorithms, especially when the solution space is constrained to a known and limited alphabet such as lowercase letters. This pattern reappears in many string and substring detection problems, making it a fundamental approach for any programmer working with text data.
 * */