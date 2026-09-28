package math.numbertheory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * WHAT IT IS: An ancient, highly efficient algorithm for finding all prime numbers up to any given limit.
 * STRATEGY: Iteratively mark the multiples of each prime starting from 2. The unmarked numbers are primes.
 * TIME/SPACE COMPLEXITY: O(N log(log N)) time, O(N) space.
 * REAL-WORLD ANALOGY / USE CASE: Filtering out composite materials in a manufacturing line, generating primes for cryptographic keys.
 * WHEN TO USE / COMBINATION: When you need ALL primes up to a limit N (N <= 10^7). Use Segmented Sieve for larger limits.
 * 
 * PSEUDOCODE:
 * boolean array is_prime of size N+1, initialized to true
 * is_prime[0] = is_prime[1] = false
 * for p from 2 to sqrt(N):
 *   if is_prime[p]:
 *     for i from p*p to N step p:
 *       is_prime[i] = false
 */
public class SieveOfEratosthenes {
    public static List<Integer> sieve(int n) {
        boolean[] isPrime = new boolean[n + 1];
        Arrays.fill(isPrime, true);
        isPrime[0] = false;
        if (n > 0) isPrime[1] = false;

        for (int p = 2; p * p <= n; p++) {
            if (isPrime[p]) {
                for (int i = p * p; i <= n; i += p) {
                    isPrime[i] = false;
                }
            }
        }

        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            if (isPrime[i]) primes.add(i);
        }
        return primes;
    }

    public static void main(String[] args) {
        System.out.println("Primes up to 30: " + sieve(30));
        System.out.println("Primes up to 10: " + sieve(10));
        System.out.println("Primes up to 2: " + sieve(2));
        System.out.println("Primes up to 0: " + sieve(0)); // Edge case
    }
}
