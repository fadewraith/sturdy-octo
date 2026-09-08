package educativetutorials.codingpatterns.onetwopointers.medium;

import java.util.Arrays;
import java.util.List;

/**
 * ============================================================================
 * PROBLEM: LeetCode 443 - String Compression (Run-Length Encoding In-Place)
 * ============================================================================
 *
 * GOAL:
 * Given an array of characters `chars`, compress it in-place using Run-Length
 * Encoding:
 *   - Consecutive duplicate characters are replaced by the character followed
 *     by the count of repetitions.
 *   - If a group's count is 1, only write the character (do NOT write '1').
 *   - If a count is >= 10, write each digit as an individual character (e.g. 12 -> '1', '2').
 *   - Return the new length of the compressed array.
 *   - MUST use O(1) extra memory (modify the input array directly).
 *
 * ----------------------------------------------------------------------------
 * THE MENTAL MODEL (How to think about this without memorizing):
 * ----------------------------------------------------------------------------
 * Think of this as a "Chunk-by-Chunk Stream Processing" with two roles:
 *   1. READER: Scans the raw stream to find consecutive blocks of identical characters.
 *   2. WRITER: Writes the condensed version at the front of the same array.
 *
 * WHY IS IN-PLACE MODIFICATION GUARANTEED SAFE? (The Key Invariant):
 * You might wonder: "Won't the writer overwrite characters the reader hasn't seen yet?"
 * NO! Because compressed data is ALWAYS <= original data:
 *   - Run of 1: 'a'        -> 'a'          (1 char written for 1 char read)
 *   - Run of 2: 'a','a'    -> 'a','2'      (2 chars written for 2 chars read)
 *   - Run of 3: 'a','a','a'-> 'a','3'      (2 chars written for 3 chars read -> saves space!)
 *   - Run of 12: 12 'a's   -> 'a','1','2'  (3 chars written for 12 chars read)
 * Therefore: `write_pointer <= read_pointer` at all times! The write pointer
 * will NEVER overtake the read pointer.
 *
 * ----------------------------------------------------------------------------
 * THE 3 POINTER ROLES:
 * ----------------------------------------------------------------------------
 *   - `i` (Read Start) : Anchors the beginning of the current character group.
 *   - `j` (Read End)   : Scans forward to find where the current group ends.
 *   - `w` (Write)      : Position where the next compressed character/digit is written.
 *
 * ----------------------------------------------------------------------------
 * COMPLEXITY:
 * ----------------------------------------------------------------------------
 *   - Time Complexity  : O(N)
 *     Even though there is a while-loop inside a while-loop, pointer `j` only
 *     moves forward and visits each element at most once. Outer pointer `i`
 *     jumps to `j`. Thus, total character reads = N.
 *   - Space Complexity : O(1) auxiliary space (in-place).
 *
 * ----------------------------------------------------------------------------
 * COMMON BEGINNER PITFALLS:
 * ----------------------------------------------------------------------------
 *   1. Appending '1' for single characters: e.g. ['a'] -> ['a', '1'] (WRONG).
 *      Problem rule says: if count == 1, do NOT append '1'.
 *   2. Multi-digit counts: Writing count 12 as `(char) 12` instead of splitting
 *      into characters '1' and '2'.
 *   3. Thinking nested loops mean O(N^2): Here `j` does not reset to 0; it only
 *      advances forward, so it's strictly O(N).
 * ============================================================================
 */
public class StringCompression {

    public static int compress(char[] chars) {
        int n = chars.length;

        // Pointer 'w' tracks the write position for compressed output.
        // It always satisfies: w <= i (write never overtakes read).
        int w = 0;

        // Pointer 'i' tracks the start of the current run of identical characters.
        int i = 0;

        while (i < n) {
            // STEP 1: Find the extent of the current run of identical characters.
            // Start scanning from 'i' using forward pointer 'j'.
            int j = i;
            while (j < n && chars[j] == chars[i]) {
                j++;
            }
            // At this point:
            //   - chars[i ... j-1] all equal chars[i]
            //   - j is either out of bounds (n) or points to a different character

            // STEP 2: Calculate how many times chars[i] was repeated.
            int count = j - i;

            // STEP 3: Write the character itself at the write pointer.
            chars[w++] = chars[i];

            // STEP 4: If repeated more than once, write the count as digits.
            // (Note: If count == 1, the problem specification requires NO number).
            if (count > 1) {
                // If count is multi-digit (e.g. 12), write each digit individually ('1', then '2')
                for (char c : String.valueOf(count).toCharArray()) {
                    chars[w++] = c;
                }
            }

            // STEP 5: Move 'i' to 'j' to process the next distinct character group.
            i = j;
        }

        // 'w' represents the length of the compressed array.
        // The valid compressed elements reside in chars[0 ... w-1].
        return w;
    }

    public static void main(String[] args) {
        List<char[]> testCases = List.of(
                new char[]{'a','a','b','b','c','c','c'},                   // multiple small runs
                new char[]{'a'},                                           // single char
                new char[]{'a','b','b','b','b','b','b','b','b','b','b','b','b'}, // 12 b's
                new char[]{'x','y','z'},                                   // all distinct
                new char[]{'a','b','b','c','c','c','c','c','c','c','c','c','c','c'} // long c-run
        );

        int idx = 1;
        for (char[] chars : testCases) {
            System.out.println("\n" + idx++ + ".\tInput = " + Arrays.toString(chars));
            int result = compress(chars);
            System.out.println("\n\tCompressed Length = " + result);
            System.out.println("\tCompressed Array  = " + Arrays.toString(Arrays.copyOf(chars, result)));
            System.out.println("-".repeat(100));
        }
    }
}


