package educativetutorials.codingpatterns.threeslidingwindow.medium;

public class PermutationInString {

    private static boolean checkInclusion(String s1, String s2) {
        int n1 = s1.length();
        int n2 = s2.length();

        // If s1 is longer than s2, a permutation of s1 cannot be a substring of s2
        if(n1 > n2) {
            return false;
        }

        // Frequency arrays for 'a'..'z'
        int[] s1Counts = new int[26];
        int[] windowCounts = new int[26];

        // Populate s1Counts with character frequencies from s1
        for(int i = 0; i < n1; i++) {
            s1Counts[s1.charAt(i) - 'a']++;
        }

        // Populate windowCounts for the initial sliding window (first n1 characters of s2)
        for(int i = 0; i < n1; i++) {
            windowCounts[s2.charAt(i) - 'a']++;
        }

        // Check if the initial window is a permutation
        if(arraysEqual(s1Counts, windowCounts)) {
            return true;
        }

        // Slide the window across the rest of s2
        for (int i = n1; i < n2; i++) {
            // Character entering the window (at index i)
            windowCounts[s2.charAt(i) - 'a']++;

            // Character leaving the window (at index i - n1)
            windowCounts[s2.charAt(i - n1) - 'a']--;

            // After updating the window, check if the frequencies match
            if (arraysEqual(s1Counts, windowCounts)) return true;
        }

        // If no permutation is found after checking all windows
        return false;
    }

    // Helper to compare two int[26] arrays quickly
    private static boolean arraysEqual(int[] a, int[] b) {
        for(int i = 0; i < 26; i++) {
            if(a[i] != b[i]) return false;
        }
        return true;
    }


    public static void main(String[] args) {
        String[][] testCases = new String[][]{
                {"abc", "okbacof"},
                {"abc", "okbancof"},
                {"adc", "dcda"},
                {"xyz", "x"},
                {"hello", "ooolleoooleh"}
        };

        int i = 1;
        for (String[] tc : testCases) {
            String s1 = tc[0], s2 = tc[1];
            boolean result = checkInclusion(s1, s2);

            System.out.println(i++ + ".\ts1 = \"" + s1 + "\"");
            System.out.println("\ts2 = \"" + s2 + "\"");
            System.out.println("\n\tOutput: " + result);
            System.out.println("-".repeat(100));
        }
    }

}


/**
 * Permutation in String
 * Statement
 * Given two strings, s1 and s2, determine whether any permutation of s1 appears as a contiguous substring within s2. In other words, return TRUE if s2 contains a substring that uses the same characters as s1, with the same frequencies, but possibly in a different order. Otherwise, FALSE.
 *
 * A permutation of a string is any possible arrangement of all its characters. For example, for the string “ab”, the permutations are: “ab” and “ba”.
 *
 * Constraints:
 *
 * 1 ≤ s1.length, s2.length ≤ 10^4
 *
 * s1 and s2 consist of lowercase English letters.
 *
 * Solution
 * A naive approach would be to generate all possible rearrangements of s1 and check whether any appear in s2. But that would quickly become inefficient for longer strings, because the number of permutations grows extremely fast. For a three-letter string like "abc", there are already six permutations; for "abcdef", there are hundreds. So, instead of trying to rearrange s1, we look for an efficient alternative.
 *
 * Two strings are permutations of each other if and only if they contain the same characters the same number of times. For example, "abc" and "bca" are permutations because both have one a, one b, and one c. This means that if we find any substring of s2 that is the same length as s1 and has the same character counts, then that substring must be a permutation of s1.
 *
 * Quick recall
 *
 * A substring is a contiguous sequence of characters within a larger string. So, if s1 = "ab" and s2 = "acb", even though both strings contain the same counts of letters a and b, there’s no two-letter block in s2 that contains both together so that the result would be FALSE.
 *
 * The same idea is used in this solution, keeping track of the character counts of s1 inside s2. To do this efficiently, the algorithm uses a sliding window over s2. At any given moment, this window represents a substring of s2 that’s the same length as s1 and could potentially be one of its permutations. To check that, compare the frequency of each character in s1 with the frequency of the characters inside this window.
 *
 * If the counts match, it means the current window contains the same letters as s1, just possibly in a different order, so a valid permutation is found. In that case, return TRUE. If the counts don’t match, and there are still characters left to examine in s2, slide the window forward by one character. That means one new character from the right side of s2 is added to the window, and one old character from the left side is removed. This is important because the window must always stay the same length as s1. Keep doing this until either a match is found or the end of s2 is reached. If all possible windows have been checked and none match the character frequencies of s1, then return FALSE.
 *
 * Let's look at the algorithm steps:
 *
 * Store the lengths of s1 and s2 in n1 and n2.
 *
 * If n1 > n2, return FALSE, as a longer string can’t fit as a substring in some other string.
 *
 * Create two arrays, s1Counts and windowCounts, of length 26. The former stores the frequencies of characters in s1, and the latter stores the frequencies in the current window of s2.
 *
 * If the two frequency arrays are identical at this point, s1Counts == windowCounts, the function returns TRUE because the first window is already a permutation of s1.
 *
 * If they do not match, the window begins sliding across s2 one character at a time. For each position i from n1 to n2-1 in s2:
 *
 * Add the count of characters at the right end of s2 (s2[i]) in the current window.
 *
 * Remove the count of characters at the right end of s2 (s2[i - n1]) from the current window.
 *
 * If s1Counts == windowCounts, return TRUE.
 *
 * Once the window has moved across the entire s2, and no match is found, return FALSE.
 *
 * Once the window has moved across the entire s2, and no match is found, return FALSE.
 *
 * Let’s look at the code for this solution below:
 *
 * Time complexity
 * The function runs in linear time because it builds character-frequency counts once, then slides a fixed-size window across s2 while doing only constant work per step. Each step contributes to the time complexity as follows:
 *
 * Counting frequencies in s1 takes O(n1) time (one pass over s1).
 *
 * Building the first window in s2 (size of s1.length) takes O(n1) time.
 *
 * Sliding the window across s2 takes O(n2). This is because each slide:
 *
 * Adds one character and removes one character (O(1) updates).
 *
 * Compares two frequency arrays of size 26 (O(1), as 26 is constant).
 *
 * This makes the total time complexity O(n1 + n2). As the length of s2 is greater than or equal to that of s1 in all valid cases, the overall time complexity is dominated by the length of s2, with a special O(1) case when s1 is longer than s2. Therefore, the final time complexity is O(n2).
 *
 * Space complexity
 * The space complexity of this solution is O(1), meaning it uses constant extra memory. This is because it only maintains two fixed-size arrays (of size 26) to store letter frequencies and a few simple integer variables.
 */