package neetcodepracticesets.arraysandhashing.ez;

import java.util.Arrays;

public class IsSubsequence {

    public boolean isSubsequence(String s, String t) {
        return rec(s, t, 0, 0);
    }

    private boolean rec(String s, String t, int i, int j) {
        if (i == s.length()) return true;
        if (j == t.length()) return false;
        if (s.charAt(i) == t.charAt(j)) {
            return rec(s, t, i + 1, j + 1);
        }
        return rec(s, t, i, j + 1);
    }

    public static boolean twoPointers(String s, String t) {
        // Time complexity: O(n+m)
        // Space complexity: O(1)
        int i = 0, j = 0;

        while(i < s.length() && j < t.length()) {
            if(s.charAt(i) == t.charAt(j)) {
                i += 1;
            }
            j += 1;
        }

        return i == s.length();
    }


    public static boolean followUpSolution(String s, String t) {
        int n = s.length(), m = t.length();
        if (m == 0) return n == 0;

        int[][] store = new int[m][26];
        for (int i = 0; i < m; i++) {
            Arrays.fill(store[i], m + 1);
        }

        store[m - 1][t.charAt(m - 1) - 'a'] = m - 1;

        for (int i = m - 2; i >= 0; i--) {
            store[i] = store[i + 1].clone();
            store[i][t.charAt(i) - 'a'] = i;
        }

        int i = 0, j = 0;
        while (i < n && j < m) {
            j = store[j][s.charAt(i) - 'a'] + 1;
            if (j > m) return false;
            i++;
        }

        return i == n;
    }

    public boolean dpBottomUp(String s, String t) {
        int n = s.length(), m = t.length();
        boolean[][] dp = new boolean[n + 1][m + 1];

        for (int j = 0; j <= m; j++) {
            dp[n][j] = true;
        }

        for (int i = n - 1; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                if (s.charAt(i) == t.charAt(j)) {
                    dp[i][j] = dp[i + 1][j + 1];
                } else {
                    dp[i][j] = dp[i][j + 1];
                }
            }
        }

        return dp[0][0];
    }

    public boolean dpTopDown(String s, String t) {
        int n = s.length(), m = t.length();
        int[][] memo = new int[n][m];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        return dpTopDownRec(s, t, 0, 0, memo);
    }

    private boolean dpTopDownRec(String s, String t, int i, int j, int[][] memo) {
        if (i == s.length()) return true;
        if (j == t.length()) return false;
        if (memo[i][j] != -1) return memo[i][j] == 1;
        if (s.charAt(i) == t.charAt(j)) {
            memo[i][j] = dpTopDownRec(s, t, i + 1, j + 1, memo) ? 1 : 0;
        } else {
            memo[i][j] = dpTopDownRec(s, t, i, j + 1, memo) ? 1 : 0;
        }
        return memo[i][j] == 1;
    }

    public static void main(String[] args) {
        String s = "abc", t = "ahbgdc";
        String s1 = "axc", t1 = "ahbgdc";
        System.out.println(twoPointers(s, t));
        System.out.println(twoPointers(s1, t1));
    }
}
