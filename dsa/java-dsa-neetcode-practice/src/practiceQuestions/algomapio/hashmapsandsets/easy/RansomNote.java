package practiceQuestions.algomapio.hashmapsAndSets.easy;

import java.util.HashMap;
import java.util.Map;

public class RansomNote {

    public static boolean bruteForce(String ransomNote, String magazine) {

        /**
         * Brute Force
         * Understand the Problem:
         * We are given two strings: ransomNote and magazine.
         * We need to determine if we can use the letters from magazine to form the ransomNote.
         * Each letter in the ransomNote must appear in magazine with the same frequency or higher.
         * Loop Through RansomNote:
         * For every letter in ransomNote, check if it exists in magazine.
         * Find the Letter in Magazine:
         * If the letter exists in magazine, remove it by slicing the string (we assume each letter can only be used once).
         * If a letter is not found, return False immediately.
         * Return True:
         * If all letters in the ransomNote can be found and removed from magazine, return True.
         * */

        // Time: O(R * M)
        // Space: O(1) (new strings are created each time due to immutability)

        for(int i = 0; i < ransomNote.length(); i++) {
            char letter = ransomNote.charAt(i);
            int index = magazine.indexOf(letter);
            if(index != - 1) {
                magazine = magazine.substring(0, index) + magazine.substring(index + 1);
            } else {
                return false;
            }
        }
        return true;
    }

    private static boolean optimal(String ransomNote, String magazine) {

        // Time Complexity: O(m + n)  -> m = len(ransomNote), n = len(magazine)
        // Space Complexity: O(n)     -> we're using a hashmap

        Map<Character, Integer> hashMap = new HashMap<>();

        for(char ch : magazine.toCharArray()) {
            hashMap .put(ch, hashMap.getOrDefault(ch, 0) + 1);
        }

        for(char ch : ransomNote.toCharArray()) {
            if(hashMap.getOrDefault(ch, 0) > 0) {
                hashMap.put(ch, hashMap.get(ch) - 1);
            } else {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        String ransomNote = "aa";
        String magazine = "aab";
        System.out.println(bruteForce(ransomNote, magazine));
        System.out.println(optimal(ransomNote, magazine));
    }
}

/**
 * Detailed Explanation
 * Understanding the Problem: Ransom Note
 * The “Ransom Note” problem is a common string manipulation task. You are given two strings: ransomNote and magazine. The goal is to determine whether you can construct the ransomNote by using letters from the magazine string. Each letter in magazine can only be used once.
 *
 * For example:
 *
 * Input: ransomNote = "a", magazine = "b" → Output: false
 * Input: ransomNote = "aa", magazine = "aab" → Output: true
 * Why This Problem Matters
 * This problem is important because it reflects real-world scenarios like checking resource availability, verifying data integrity, or solving subset containment problems. It also introduces essential concepts like character counting, frequency analysis, and hash maps — all of which are frequently used in string and array processing tasks.
 *
 * Brute Force Approach
 * The brute-force method involves checking, for every character in ransomNote, whether it exists in magazine. If it does, we remove that character from magazine (to simulate using it once). If any character is not found, we return false.
 *
 * This approach leads to poor performance because searching for a character in a string is O(M), and we do this for each character in ransomNote. The total time complexity becomes O(R × M), which is inefficient for longer strings.
 *
 * Optimized Solution: Use a Hash Map (Counter)
 * The optimal way to solve this problem is by using a hash map (or Python’s collections.Counter) to count how many times each character appears in magazine. This allows for fast and efficient lookups while processing ransomNote.
 *
 * Step-by-Step Plan
 * Count Frequency of Characters in Magazine:
 * Loop through each character in magazine and record how many times it appears. This takes O(M) time.
 * Check Characters in Ransom Note:
 * For every character in ransomNote, check if it exists in the hash map and whether its count is positive.
 * Update the Hash Map:
 * If the character is available, decrease its count. If it's not available or the count is zero, return false.
 * Return True:
 * If all characters in ransomNote are accounted for, return true.
 * Example Walkthrough
 * Input: ransomNote = "aab", magazine = "baa"
 *
 * Build frequency map from magazine: {'b': 1, 'a': 2}
 * Check 'a' → available → decrement count → {'b': 1, 'a': 1}
 * Check 'a' → available → decrement → {'b': 1, 'a': 0}
 * Check 'b' → available → decrement → {'b': 0, 'a': 0}
 * All characters found → return true
 * Time and Space Complexity
 * Time Complexity: O(M + R), where M is the length of magazine and R is the length of ransomNote.
 * Space Complexity: O(1), because the number of characters is limited (only lowercase English letters).
 *
 * Edge Cases to Consider
 * Empty ransomNote → always return true
 * Empty magazine but non-empty ransomNote → always return false
 * Case sensitivity matters → "A" and "a" are different characters
 * Conclusion
 * The “Ransom Note” problem teaches how to efficiently manage and compare frequencies between two data sources. While the brute-force method demonstrates a naïve solution, the optimized version using a hash map offers a significant performance boost and is widely applicable to many real-world problems.
 * */