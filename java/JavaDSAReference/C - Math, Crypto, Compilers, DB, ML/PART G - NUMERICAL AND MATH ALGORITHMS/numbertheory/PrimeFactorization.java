package math.numbertheory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * WHAT IT IS: Decomposing a composite number into a product of prime numbers.
 * STRATEGY: Trial division for small primes, and Pollard's Rho for large composite numbers.
 * TIME/SPACE COMPLEXITY: Trial Division: O(sqrt(n)) time. Pollard's Rho: Expected O(n^(1/4)) time. O(1) space.
 * REAL-WORLD ANALOGY / USE CASE: Cryptography (breaking RSA relies on difficulty of prime factorization).
 * WHEN TO USE / COMBINATION: Need to find factors or check primality of large numbers.
 * 
 * PSEUDOCODE:
 * Trial Division:
 * while n % 2 == 0: add 2, n /= 2
 * for i from 3 to sqrt(n) step 2:
 *     while n % i == 0: add i, n /= i
 * if n > 2: add n
 * 
 * Pollard Rho:
 * x = rand(), y = x, c = rand(), d = 1
 * f(x) = (x^2 + c) % n
 * while d == 1:
 *     x = f(x)
 *     y = f(f(y))
 *     d = gcd(|x - y|, n)
 * return d
 */
public class PrimeFactorization {
    public static List<Long> trialDivision(long n) {
        List<Long> factors = new ArrayList<>();
        while (n % 2 == 0) {
            factors.add(2L);
            n /= 2;
        }
        for (long i = 3; i * i <= n; i += 2) {
            while (n % i == 0) {
                factors.add(i);
                n /= i;
            }
        }
        if (n > 2) factors.add(n);
        return factors;
    }

    private static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    /**
     * Returns a non-trivial divisor of n, or n itself if n is prime.
     * Pollard's Rho can only split COMPOSITE numbers - on a prime it never finds
     * a divisor, so we test primality first (otherwise it loops forever).
     */
    public static long pollardRho(long n) {
        if (n < 2) throw new IllegalArgumentException("n must be >= 2");
        if (n % 2 == 0) return 2;
        if (java.math.BigInteger.valueOf(n).isProbablePrime(30)) return n;

        Random rand = new Random();
        while (true) { // each attempt uses a new random constant c
            long c = 1 + Math.floorMod(rand.nextLong(), n - 1);
            long x = 2, y = 2, d = 1;
            while (d == 1) {
                x = f(x, c, n);          // tortoise: 1 step
                y = f(f(y, c, n), c, n); // hare: 2 steps (Floyd cycle detection)
                d = gcd(Math.abs(x - y), n);
            }
            if (d != n) return d;        // d == n means this c failed - retry
        }
    }

    // f(x) = (x^2 + c) mod n
    private static long f(long x, long c, long n) {
        return (mulMod(x, x, n) + c) % n;
    }

    // (a * b) mod m without overflow: x * x overflows a long once n > ~3 * 10^9
    private static long mulMod(long a, long b, long m) {
        long result = 0;
        a %= m;
        while (b > 0) {
            if ((b & 1) == 1) result = (result + a) % m;
            a = (a << 1) % m; // safe while m < 2^62
            b >>= 1;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println("Trial Division of 315: " + trialDivision(315));
        System.out.println("Pollard Rho factor of 8051: " + pollardRho(8051));
    }
}
