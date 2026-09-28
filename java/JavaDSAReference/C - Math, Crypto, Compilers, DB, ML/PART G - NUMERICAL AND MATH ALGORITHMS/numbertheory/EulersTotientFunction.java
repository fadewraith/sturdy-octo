package math.numbertheory;

/**
 * WHAT IT IS: Euler's totient function counts the positive integers up to a given integer n that are relatively prime to n.
 * STRATEGY: Use the prime factorization formula: n * (1 - 1/p1) * ... * (1 - 1/pk).
 * TIME/SPACE COMPLEXITY: O(sqrt(n)) time, O(1) space.
 * REAL-WORLD ANALOGY / USE CASE: RSA algorithm relies heavily on this function for key generation.
 * WHEN TO USE / COMBINATION: Cryptography, group theory, finding number of generators in cyclic groups.
 * 
 * PSEUDOCODE:
 * result = n
 * for i from 2 to sqrt(n):
 *     if n % i == 0:
 *         while n % i == 0: n /= i
 *         result -= result / i
 * if n > 1: result -= result / n
 * return result
 */
public class EulersTotientFunction {
    public static long totient(long n) {
        long result = n;
        for (long p = 2; p * p <= n; ++p) {
            if (n % p == 0) {
                while (n % p == 0)
                    n /= p;
                result -= result / p;
            }
        }
        if (n > 1) {
            result -= result / n;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println("Totient of 9 (Expected 6): " + totient(9));
        System.out.println("Totient of 10 (Expected 4): " + totient(10));
    }
}
