package algorithms.math;

import java.util.Arrays;

/**
 * SIEVE OF ERATOSTHENES
 * 
 * WHAT IT IS:
 * An incredibly efficient, ancient algorithm for finding all prime numbers up 
 * to any given limit N.
 * 
 * STRATEGY:
 * 1. Create a boolean array of size N+1, initially assuming all numbers are prime.
 * 2. Start from the first prime number, p = 2.
 * 3. Mark all multiples of p (p*p, p*(p+1), etc.) as NOT prime.
 * 4. Find the next number greater than p that is still marked as prime, and repeat.
 * 5. You only need to loop up to sqrt(N)!
 * 
 * COMPLEXITY:
 * Time: O(N * log(log(N))) which is practically O(N).
 * Space: O(N) for the boolean array.
 */
public class SieveOfEratosthenes {

    public static void sieve(int n) {
        boolean[] prime = new boolean[n + 1];
        Arrays.fill(prime, true);

        // 0 and 1 are not primes
        prime[0] = false;
        prime[1] = false;

        // Start from 2, up to sqrt(n)
        for (int p = 2; p * p <= n; p++) {
            
            // If prime[p] is not changed, then it is a prime
            if (prime[p]) {
                // Update all multiples of p
                for (int i = p * p; i <= n; i += p) {
                    prime[i] = false;
                }
            }
        }

        // Print all prime numbers
        System.out.print("Primes up to " + n + ": ");
        for (int i = 2; i <= n; i++) {
            if (prime[i]) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("--- SIEVE OF ERATOSTHENES DEMO ---");
        sieve(30);
        // Expected: 2 3 5 7 11 13 17 19 23 29
    }
}
