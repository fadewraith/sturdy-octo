package math.numbertheory;

import java.math.BigInteger;
import java.util.Random;

/**
 * WHAT IT IS: Algorithms to determine if a number is prime. Trial division is deterministic but slow. Fermat's and Miller-Rabin are probabilistic but fast.
 * STRATEGY: 
 *   - Trial Division: Check divisibility up to sqrt(n).
 *   - Fermat: If n is prime, a^(n-1) = 1 (mod n) for all 1 <= a < n.
 *   - Miller-Rabin: Advanced probabilistic test based on Fermat's with stronger checks.
 * TIME/SPACE COMPLEXITY: Trial: O(sqrt(n)). Fermat/Miller-Rabin: O(k log^3 n) where k is number of iterations. Space: O(1).
 * REAL-WORLD ANALOGY / USE CASE: RSA key generation, cryptography.
 * WHEN TO USE / COMBINATION: Miller-Rabin is standard for large numbers (e.g., cryptographic keys).
 * 
 * PSEUDOCODE:
 * (Miller-Rabin)
 * Write n-1 as 2^s * d
 * Loop k times:
 *   Pick random a in [2, n-2]
 *   x = a^d mod n
 *   if x == 1 or x == n-1, continue loop
 *   repeat s-1 times:
 *     x = x^2 mod n
 *     if x == n-1, continue loop
 *   return Composite
 * return Probably Prime
 */
public class PrimalityTesting {
    public static boolean trialDivision(long n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        for (long i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) return false;
        }
        return true;
    }

    public static boolean fermatTest(BigInteger n, int k) {
        if (n.compareTo(BigInteger.ONE) <= 0) return false;
        if (n.equals(BigInteger.valueOf(2)) || n.equals(BigInteger.valueOf(3))) return true;
        
        Random rand = new Random();
        for (int i = 0; i < k; i++) {
            BigInteger a;
            do {
                a = new BigInteger(n.bitLength(), rand);
            } while (a.compareTo(BigInteger.ONE) <= 0 || a.compareTo(n.subtract(BigInteger.ONE)) >= 0);
            
            if (!a.modPow(n.subtract(BigInteger.ONE), n).equals(BigInteger.ONE)) {
                return false;
            }
        }
        return true;
    }

    public static boolean millerRabinTest(BigInteger n, int k) {
        return n.isProbablePrime(k);
    }

    public static void main(String[] args) {
        long testNum = 999999937L;
        System.out.println("Trial Division (" + testNum + "): " + trialDivision(testNum));
        System.out.println("Fermat Test (" + testNum + "): " + fermatTest(BigInteger.valueOf(testNum), 10));
        System.out.println("Miller-Rabin (" + testNum + "): " + millerRabinTest(BigInteger.valueOf(testNum), 10));
        
        System.out.println("Trial Division (1): " + trialDivision(1)); // Edge case
        System.out.println("Trial Division (4): " + trialDivision(4)); // Composite
    }
}
