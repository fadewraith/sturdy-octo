package educativetutorials.codingpatterns.threeslidingwindow.medium;

import java.util.*;

public class RepeatedDnaSequences {

    private static List<String> findRepeatedDnaSequences(String s) {
        // Map to count occurrences of each 10-letter substring
        Map<String, Integer> seen = new HashMap<>();
        // Result list to store repeated sequences
        List<String> result = new ArrayList<>();
        // Sliding window of size 10 across the string
        for(int i = 0; i <= s.length() - 10; i++) {
            // Extract the current 10-letter substring
            String substring = s.substring(i, i + 10);
            // Track how many times we've seen this substring
            seen.put(substring, seen.getOrDefault(substring, 0) + 1);
            // Add to result only when seen exactly twice (avoid duplicates)
            if(seen.get(substring) == 2) {
                result.add(substring);
            }
        }
        // Return all repeated 10-letter sequences
        return result;
    }

    public static void main(String[] args) {
        List<String> testCases = Arrays.asList(
                "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT",
                "AAAAAAAAAAAAA",
                "ACGTACGTACGTACGTACGTACGTACGTACGT",
                "GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG",
                "GTACGTACGTACGCCCCCCCCGGGGG"
        );

        for (int i = 0; i < testCases.size(); i++) {
            System.out.println((i + 1) + ".\tInput: \"" + testCases.get(i) + "\"");

            List<String> result = findRepeatedDnaSequences(testCases.get(i));
            System.out.println("\n\tOutput: " + result);
            System.out.println("-" + new String(new char[100]).replace('\0', '-') + "\n");
        }
    }
}

/**
 * Repeated DNA Sequences
 *
 * Statement:
 * A DNA sequence consists of nucleotides represented by the letters
 * 'A', 'C', 'G', and 'T' only.
 *
 * Example: "ACGAATTCCG"
 *
 * Given a string s representing a DNA sequence, return all the
 * 10-letter-long substrings that appear more than once.
 *
 * Constraints:
 * 1 ≤ s.length ≤ 10^3
 * s[i] ∈ {'A', 'C', 'G', 'T'}
 *
 * --------------------------------------------------
 * Solution Approaches
 * --------------------------------------------------
 *
 * 1. Naive Approach
 * - Generate all substrings of length 10
 * - Use a set to track seen substrings
 * - If a substring repeats, add to result
 *
 * Time Complexity:
 * O((n - k) * k)
 *
 * Space Complexity:
 * O((n - k) * k)
 *
 * --------------------------------------------------
 * 2. Optimized Approach (Sliding Window + Rolling Hash)
 * --------------------------------------------------
 *
 * Instead of recomputing substrings, use rolling hash.
 *
 * Rolling Hash Steps:
 * 1. Compute hash of first window
 * 2. Slide window forward
 * 3. Update hash using:
 *
 *    newHash = (oldHash * 4)
 *              - (leftChar * 4^10)
 *              + newChar
 *
 * --------------------------------------------------
 * Step-by-Step Construction
 * --------------------------------------------------
 *
 * Step 1: Encode Characters
 * A → 0
 * C → 1
 * G → 2
 * T → 3
 *
 * Convert string into numeric array for faster computation.
 *
 * --------------------------------------------------
 * Step 2: Compute Initial Hash
 *
 * Polynomial Rolling Hash:
 *
 * hash = c1 * 4^9 + c2 * 4^8 + ... + c10 * 4^0
 *
 * Where:
 * - ci = encoded character
 * - base = 4
 * - k = 10
 *
 * Compute first window hash and store multiplier (4^10).
 *
 * --------------------------------------------------
 * Step 3: Sliding Window + Hash Update
 *
 * For each step:
 * - Remove leftmost character contribution
 * - Add new character contribution
 *
 * Formula:
 * h = (h * 4)
 *     - (leftChar * 4^10)
 *     + newChar
 *
 * Use a set to track seen hashes.
 * If hash repeats → add substring to result.
 *
 * --------------------------------------------------
 * Summary
 * --------------------------------------------------
 *
 * - Encode DNA characters into numbers
 * - Compute rolling hash for first window
 * - Slide window and update hash efficiently
 * - Track hashes using a set
 * - Collect repeated sequences
 *
 * --------------------------------------------------
 * Time Complexity
 * --------------------------------------------------
 *
 * - Encoding: O(n)
 * - Initial hash: O(1)
 * - Sliding window: O(n)
 * - Hash operations: O(1)
 *
 * Total: O(n)
 *
 * --------------------------------------------------
 * Space Complexity
 * --------------------------------------------------
 *
 * - Encoded array: O(n)
 * - Seen hashes: O(n)
 * - Result storage: O(n)
 *
 * Total: O(n)
 */
