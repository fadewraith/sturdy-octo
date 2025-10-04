package neetcodepracticesets.arraysandhashing.medium;

import java.util.Arrays;

public class AppendCharacters {

    public int twoPointers(String s, String t) {
        // O(n + m)
        // O(1)
        int i = 0, j = 0;
        while(i < s.length() && j < t.length()) {
            if(s.charAt(i) == t.charAt(j)) {
                i++;
                j++;
            } else {
                i++;
            }
        }

        return t.length() - j;
    }

    public int indexJumpingDp(String s, String t) {
        // O(n + m)
        // O(n)
        int n = s.length(), m = t.length();
        int[][] store = new int[n][26];
        for (int[] row : store) {
            Arrays.fill(row, n + 1);
        }
        store[n - 1][s.charAt(n - 1) - 'a'] = n - 1;

        for (int i = n - 2; i >= 0; i--) {
            store[i] = store[i + 1].clone();
            store[i][s.charAt(i) - 'a'] = i;
        }

        int i = 0, j = 0;
        while (i < n && j < m) {
            if (store[i][t.charAt(j) - 'a'] == n + 1) {
                break;
            }
            i = store[i][t.charAt(j) - 'a'] + 1;
            j++;
        }

        return m - j;
    }
}
