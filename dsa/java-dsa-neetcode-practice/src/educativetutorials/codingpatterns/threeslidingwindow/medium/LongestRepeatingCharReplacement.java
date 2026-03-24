package educativetutorials.codingpatterns.threeslidingwindow.medium;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LongestRepeatingCharReplacement {

    private static int longestRepeatingCharacterReplacement(String s, int k) {
        // initialize variables
        int stringLength = s.length();
        int lengthOfMaxSubstring = 0;
        int start = 0;
        Map<Character, Integer> charFreq = new HashMap<>();
        int mostFreqChar = 0;

        // iterate over the input string
        for(int end = 0; end < stringLength; ++end) {
            char currentChar = s.charAt(end);

            // if the new character is not in the hash map, add it, else increment its frequency
            charFreq.put(currentChar, charFreq.getOrDefault(currentChar, 0) + 1);

            // update the most frequent char
            mostFreqChar = Math.max(mostFreqChar, charFreq.get(currentChar));

            // if the number of replacements in the current window have exceeded the limit, slide the window
            if((end - start + 1 - mostFreqChar) > k) {
                charFreq.put(s.charAt(start), charFreq.get(s.charAt(start)) - 1);
                start += 1;
            }

            // if this window is the longest so far, update the length of max substring
            lengthOfMaxSubstring = Math.max(lengthOfMaxSubstring, end - start + 1);
        }

        // return the length of the max substring with same characters after replacement(s)
        return lengthOfMaxSubstring;

    }


    public static void main(String[] args) {
        List<String> inputStrings = Arrays.asList("AABCCBB", "ABBCB", "ABCCDE", "ABBCAB", "BBBBBBBBB");
        List<Integer> k = Arrays.asList(2, 1, 1, 2, 4);

        for (int i = 0; i < inputStrings.size(); ++i) {
            System.out.println((i + 1) + ".\tInput String: '" + inputStrings.get(i) + "'");
            System.out.println("\tk: " + k.get(i));
            System.out.println("\tLength of the longest substring with repeating characters: " + longestRepeatingCharacterReplacement(inputStrings.get(i), k.get(i)));
            System.out.println(new String(new char[100]).replace("\0", "-"));
        }
    }
}


/**
 * Naive approach
 * A brute force method checks every possible substring to see if it can be transformed into a string of identical characters with at most k replacements. Here’s how it works:
 *
 * Consider every substring starting from that character for each character in the string.
 *
 * For each substring, use a nested loop to count the number of replacements needed to make all its characters identical.
 *
 * If the number of replacements is within the allowed limit k and the substring is longer than any previously found valid substring, update the longest length.
 *
 * This method uses three nested loops—one to choose the starting point, one to choose the ending point, and one to calculate the required replacements—resulting in a time complexity of O(n*n*n), where n is the length of the input string.
 * */

/**
Optimized approach using sliding window
We have to find the maximum length of a substring where we can replace up to k characters to make all characters in the substring identical. To solve this efficiently, we use the sliding window approach. It involves two pointers marking the start and end of a segment that moves across the main string. This method helps to quickly adjust the segment by expanding and contracting the window without repeatedly examining the same characters.

The process starts by expanding the window one character at a time and using a hash map to record the frequency of each character within the window. It also tracks the count of the most frequent characters in the window to determine how many characters need to be replaced to make the window uniform. If the difference between the window size and this highest frequency exceeds the allowed number of replacements, the window is contracted from the start until it becomes valid again. Throughout this process, the maximum valid window size is continuously updated, and finally, the length of the longest valid substring is returned.

Here are the detailed steps of the algorithm that we have just discussed:

We start by initializing the data structures and variables required to track the sliding window clearly:

lengthOfMaxSubstring will track the length of the longest valid substring found; this is initially set to 0.

start pointer marks the left boundary of the sliding window; this is initially set to 0.

We create a frequency map, charFreq, that keeps track of how many times each character appears within our current sliding window.

A counter, mostFreqChar, tracks the maximum frequency of any character within our current window; this is initially set to 0.

Next, we iterate over the string using a pointer, end, starting from 0 to stringLength - 1:

First, we take the character at the current position end.

Then, we update the frequency count of this character in the charFreq map:

If the character does not exist in charFreq, we initialize its frequency to 1.

If the character is already present, we increment its frequency by 1.

After updating this frequency, we compare the updated count of this character with the current mostFreqChar. If the new frequency is greater, we set mostFreqChar equal to this new higher frequency.

After each character addition, we check if the current window is valid. Specifically, we check if the number of replacements needed (calculated as (end - start + 1) - mostFreqChar) exceeds k:

If replacements exceed k, we reduce the window size by:

Decreasing the character frequency at position start in the charFreq map by 1 because this character will no longer be inside our window.

Moving the start pointer forward by one position, contracting the window to restore validity.

Finally, we compare the length of our current window (end - start + 1) with lengthOfMaxSubstring. If the current window length is larger, we update lengthOfMaxSubstring to this new value.

Once the whole string has been traversed, we return the recorded value of lengthOfMaxSubstring, representing the length of the longest substring that can be converted into a uniform substring by replacing at most k characters.
*/