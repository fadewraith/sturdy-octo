package educativetutorials.codingpatterns.threeslidingwindow;

import java.util.Hashtable;
import java.util.Map;

public class LongestSubstringWithoutRepeatingCharacters {

    /**
     * When duplicate appears:
     *
     * 1. Calculate current window length
     * 2. Update max
     * 3. Move start after duplicate
     * 4. Update map
     *
     * When loop ends:
     *
     * Check last window
     * */

    private static int findLongestSubstring(String s) {
        // Check the length of input str
        if(s.length() == 0) {
            return 0;
        }

        /**
         * windowStart: The starting index of the current substring.
         * windowLength: The length of the current substring.
         * longest: The length of the longest substring.
         * */

        int n = s.length();
        int winStart = 0, longest = 0;
        int winLength, i;


        Map<Character, Integer> lastSeenAt = new Hashtable<>();

        // Traverse input str to find the longest substring
        // without repeating characters.
        for(i = 0; i < n; i++) {
            // If the current element is not present in the hash map,
            // then store it in the hash map with the current index as the value.
            if(!lastSeenAt.containsKey(s.charAt(i))) {
                lastSeenAt.put(s.charAt(i), i);
            } else {
                // If the current element is present in the hash map,
                // it means that this element may have appeared before.
                // Check if the current element occurs before or after `windowStart`.
                // Check if duplicate is inside current window
                if(lastSeenAt.get(s.charAt(i)) >= winStart) {
                    // Update current window length
                    winLength = i - winStart;
                    // Update longest substring
                    if(longest < winLength) {
                        longest = winLength;
                    }

                    // Move window start
                    winStart = lastSeenAt.get(s.charAt(i)) + 1;
                }

                // Update the last occurrence of
                // the element in the hash table
                // Update latest position
                lastSeenAt.replace(s.charAt(i), i);
            }

        }

        // Update the longest substring's
        // length and starting index.
        /**
         * Why is this needed?
         * Because the longest substring might end at the last character, and we may never encounter another duplicate to trigger the update.
         *
         * Example
         * s = "abcdef"
         *
         * There are no duplicates, so the if block inside the loop never runs.
         *
         * At the end:
         *
         * winStart = 0
         * i = 6
         *
         * Window length:
         *
         * i - winStart = 6
         *
         * So we update:
         *
         * longest = 6
         *
         * Without this final check, the algorithm would incorrectly return 0.
         * */
        if(longest < i - winStart) {
            longest = i - winStart;
        }

        return longest;
    }

    public static void main(String[] arg) {
        String[] inputs = {
                "abcabcbb",
                "pwwkew",
                "bbbbb",
                "ababababa",
                "",
                "ABCDEFGHI",
                "ABCDEDCBA",
                "AAAABBBBCCCCDDDD"
        };
        for (int i = 0; i < inputs.length; i++) {
            int str = findLongestSubstring(inputs[i]);
            System.out.print(i + 1);
            System.out.println("\tInput string: " + inputs[i]);
            System.out.println("\n\tLength of longest substring: " + str);
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }

    }
}
