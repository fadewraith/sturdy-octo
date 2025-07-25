package practiceQuestions.algomapio.pointers;

import java.sql.Time;

public class ReverseString {

    private static void reverseString(char[] s) {
        // Time: O(n)
        // Space: O(n)
        int n = s.length;
        char[] T = new char[n];
        for(int i = n - 1; i >= 0; i--) {
            T[n - 1 - i] = s[i];
        }
        for(int i = 0; i < n; i++) {
            s[i] = T[i];
        }
    }

    /**
     * Optimal Solution
     * The optimal solution uses two pointers to reverse the array in-place with O(1) space complexity:
     *
     * Get the length n of the array s.
     * Initialize two pointers: l at index 0 and r at index n-1.
     * While l is less than r:
     * Swap the characters at s[l] and s[r].
     * Increment l and decrement r to move the pointers inward.
     * */

    private static void optimal(char[] s) {
        // Time Complexity: O(n)
        // Space Complexity: O(1)
        int l = 0;
        int r = s.length - 1;
        while(l < r) {
            char temp = s[l];
            s[l] = s[r];
            s[r] = temp;
            l++;
            r--;
        }
    }


}
