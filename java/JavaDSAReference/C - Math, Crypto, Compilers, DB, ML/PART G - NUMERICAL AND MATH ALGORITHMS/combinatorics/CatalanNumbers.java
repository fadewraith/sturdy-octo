package math.combinatorics;

import java.math.BigInteger;

/**
 * WHAT IT IS: A sequence of natural numbers that occur in various counting problems (e.g. BSTs, balanced parentheses).
 * STRATEGY: Use the formula C_n = (2n)! / ((n+1)! * n!) or dynamic programming.
 * TIME/SPACE COMPLEXITY: DP is O(n^2) time, O(n) space. Formula is O(n) time, O(1) space.
 * REAL-WORLD ANALOGY / USE CASE: Counting valid combinations of parenthesis, number of distinct binary trees.
 * WHEN TO USE / COMBINATION: Combinatorial counting problems often turn out to be Catalan numbers.
 * 
 * PSEUDOCODE:
 * dp = array of size n+1
 * dp[0] = 1, dp[1] = 1
 * for i from 2 to n:
 *     for j from 0 to i-1:
 *         dp[i] += dp[j] * dp[i - j - 1]
 * return dp[n]
 */
public class CatalanNumbers {
    public static BigInteger catalanDP(int n) {
        if (n <= 1) return BigInteger.ONE;
        BigInteger[] dp = new BigInteger[n + 1];
        dp[0] = BigInteger.ONE;
        dp[1] = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            dp[i] = BigInteger.ZERO;
            for (int j = 0; j < i; j++) {
                dp[i] = dp[i].add(dp[j].multiply(dp[i - j - 1]));
            }
        }
        return dp[n];
    }
    
    public static BigInteger catalanFormula(int n) {
        return nCr(2 * n, n).divide(BigInteger.valueOf(n + 1));
    }

    private static BigInteger factorial(int n) {
        BigInteger res = BigInteger.ONE;
        for (int i = 2; i <= n; i++) res = res.multiply(BigInteger.valueOf(i));
        return res;
    }

    private static BigInteger nCr(int n, int r) {
        return factorial(n).divide(factorial(r).multiply(factorial(n - r)));
    }

    public static void main(String[] args) {
        System.out.println("Catalan DP for 4 (Expected 14): " + catalanDP(4));
        System.out.println("Catalan Formula for 5 (Expected 42): " + catalanFormula(5));
    }
}
