package educativetutorials.codingpatterns.threeslidingwindow.medium;

public class NumOfSubstringsContainingAll3Characters {

    public static int bruteForce(String s) {
        int n = s.length();
        int count = 0;

        // Try all possible starting points
        for (int i = 0; i < n; i++) {

            // Track presence of a, b, c for current substring
            int[] freq = new int[3]; // 0->a, 1->b, 2->c

            // Extend substring from i to j
            for (int j = i; j < n; j++) {

                // Update frequency of current character
                freq[s.charAt(j) - 'a']++;

                // Check if all three characters exist
                if (freq[0] > 0 && freq[1] > 0 && freq[2] > 0) {
                    count++; // valid substring
                }
            }
        }

        return count;
    }

    public static int optimized(String s) {

        int n = s.length();

        // Stores count of 'a', 'b', 'c' in current window
        int[] freq = new int[3];

        int left = 0;   // start of window
        int count = 0;  // final answer

        // Expand window using 'right'
        for (int right = 0; right < n; right++) {

            // STEP 1: Include current character into window
            // Why: we are expanding the window to explore new substrings
            freq[s.charAt(right) - 'a']++;

            // STEP 2: Check if current window is valid
            // Valid = contains at least one 'a', 'b', and 'c'
            while (freq[0] > 0 && freq[1] > 0 && freq[2] > 0) {

                // STEP 3: Count all valid substrings ending at 'right'
                // Why (IMPORTANT):
                // If current window [left ... right] is valid,
                // then all substrings starting from left → n-1 and ending at right are valid
                // Count them in one go instead of iterating

                count += (n - right);

                // STEP 4: Shrink window from left
                // Why:
                // Try to find next smaller valid window
                // This ensures we count all possible valid windows efficiently
                freq[s.charAt(left) - 'a']--;
                left++;
            }
        }

        return count;
    }
}
