package math.numbertheory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * WHAT IT IS: A space-optimized version of the Sieve of Eratosthenes that finds primes in a specific range [L, R] or when N is large.
 * STRATEGY: Generate small primes up to sqrt(R). Use these small primes to mark their multiples in the range [L, R] in segments to save memory.
 * TIME/SPACE COMPLEXITY: O((R-L+1) log(log R) + sqrt(R) log(log(sqrt(R)))) time. Space is O(sqrt(R) + (R-L+1)).
 * REAL-WORLD ANALOGY / USE CASE: Finding prime ranges when memory is constrained.
 * WHEN TO USE / COMBINATION: When L and R are large (e.g., 10^12) but R-L is small (e.g., 10^5).
 * 
 * PSEUDOCODE:
 * 1. Find all primes up to sqrt(R) using simple sieve.
 * 2. Create boolean array for range [L, R].
 * 3. For each prime p from step 1:
 *      Find first multiple of p >= L.
 *      Mark all subsequent multiples in [L, R] as false.
 * 4. Remaining true elements are primes.
 */
public class SegmentedSieve {
    private static List<Integer> simpleSieve(int limit) {
        boolean[] isPrime = new boolean[limit + 1];
        Arrays.fill(isPrime, true);
        List<Integer> primes = new ArrayList<>();
        for (int p = 2; p * p <= limit; p++) {
            if (isPrime[p]) {
                for (int i = p * p; i <= limit; i += p) {
                    isPrime[i] = false;
                }
            }
        }
        for (int p = 2; p <= limit; p++) {
            if (isPrime[p]) primes.add(p);
        }
        return primes;
    }

    public static List<Long> segmentedSieve(long L, long R) {
        int limit = (int) Math.floor(Math.sqrt(R)) + 1;
        List<Integer> primes = simpleSieve(limit);

        boolean[] isPrime = new boolean[(int) (R - L + 1)];
        Arrays.fill(isPrime, true);

        for (int p : primes) {
            long start = Math.max((long) p * p, (L + p - 1) / p * p);
            for (long j = start; j <= R; j += p) {
                isPrime[(int) (j - L)] = false;
            }
        }

        if (L == 1) {
            isPrime[0] = false;
        }

        List<Long> result = new ArrayList<>();
        for (int i = 0; i < isPrime.length; i++) {
            if (isPrime[i]) {
                result.add(L + i);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        long L = 10, R = 50;
        System.out.println("Primes between " + L + " and " + R + ": " + segmentedSieve(L, R));
        
        L = 100000000000L; R = 100000000100L;
        System.out.println("Primes between " + L + " and " + R + ": " + segmentedSieve(L, R));
    }
}
