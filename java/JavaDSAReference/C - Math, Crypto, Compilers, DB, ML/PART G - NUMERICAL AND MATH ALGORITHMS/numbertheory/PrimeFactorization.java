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

    public static long pollardRho(long n) {
        if (n % 2 == 0) return 2;
        long x = 2, y = 2, d = 1;
        long c = new Random().nextLong() % (n - 1) + 1;
        while (d == 1) {
            x = (x * x % n + c + n) % n;
            y = (y * y % n + c + n) % n;
            y = (y * y % n + c + n) % n;
            d = gcd(Math.abs(x - y), n);
            if (d == n) return pollardRho(n);
        }
        return d;
    }

    public static void main(String[] args) {
        System.out.println("Trial Division of 315: " + trialDivision(315));
        System.out.println("Pollard Rho factor of 8051: " + pollardRho(8051));
    }
}
