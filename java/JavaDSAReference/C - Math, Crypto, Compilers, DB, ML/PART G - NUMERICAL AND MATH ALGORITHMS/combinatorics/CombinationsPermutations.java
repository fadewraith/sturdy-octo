package math.combinatorics;

import java.math.BigInteger;

/**
 * WHAT IT IS: Calculating permutations (nPr) and combinations (nCr) using factorials.
 * STRATEGY: Use DP or BigInteger factorial math. nCr = n! / (r! * (n-r)!), nPr = n! / (n-r)!
 * TIME/SPACE COMPLEXITY: O(n) time, O(1) space if computing incrementally.
 * REAL-WORLD ANALOGY / USE CASE: Probability, counting possible combinations (e.g. lottery tickets).
 * WHEN TO USE / COMBINATION: Whenever combinations or arrangements matter without replacement.
 * 
 * PSEUDOCODE:
 * factorial(n): res = 1; for i in 1..n: res *= i; return res
 * nCr(n, r) = factorial(n) / (factorial(r) * factorial(n-r))
 * nPr(n, r) = factorial(n) / factorial(n-r)
 */
public class CombinationsPermutations {
    public static BigInteger factorial(int n) {
        BigInteger res = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            res = res.multiply(BigInteger.valueOf(i));
        }
        return res;
    }

    public static BigInteger nCr(int n, int r) {
        if (r < 0 || r > n) return BigInteger.ZERO;
        return factorial(n).divide(factorial(r).multiply(factorial(n - r)));
    }

    public static BigInteger nPr(int n, int r) {
        if (r < 0 || r > n) return BigInteger.ZERO;
        return factorial(n).divide(factorial(n - r));
    }

    public static void main(String[] args) {
        System.out.println("5C2 (Expected 10): " + nCr(5, 2));
        System.out.println("5P2 (Expected 20): " + nPr(5, 2));
    }
}
