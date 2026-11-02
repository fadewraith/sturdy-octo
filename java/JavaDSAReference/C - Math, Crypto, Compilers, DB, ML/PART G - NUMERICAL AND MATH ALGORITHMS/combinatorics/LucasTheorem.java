package math.combinatorics;

/**
 * BINOMIAL COEFFICIENTS MODULO A PRIME (Factorial tables + Lucas' theorem)
 *
 * WHAT IT IS:
 * Computes C(n, k) mod p for a prime p - even when n is astronomically large
 * (n = 10^18) and the exact value would have billions of digits.
 *
 * WHEN TO USE THIS:
 * - Counting problems in competitive programming (answers "mod 10^9 + 7"),
 *   combinatorics in probability, coding theory, and the classic fact that
 *   Pascal's triangle mod 2 is the Sierpinski triangle.
 *
 * METHOD 1 - n < p: factorial tables.
 *   C(n, k) = n! / (k! (n-k)!). Division mod p = multiplying by the modular
 *   inverse; precompute fact[i] and invFact[i] (Fermat: a^-1 = a^(p-2) mod p) once
 *   in O(N), then every query is O(1). Breaks when n >= p, because then n! is 0 mod p.
 *
 * METHOD 2 - LUCAS' THEOREM (any n, small prime p):
 *   Write n and k in base p:  n = n_m ... n_1 n_0,  k = k_m ... k_1 k_0. Then
 *     C(n, k) = product over i of C(n_i, k_i)   (mod p)
 *   with C(n_i, k_i) = 0 when k_i > n_i. Each digit pair uses the factorial table
 *   (digits are < p). So a 10^18 query needs only ~log_p(n) table lookups.
 *   Corollary: C(n, k) is ODD exactly when every bit of k is also set in n
 *   ((n & k) == k) - Lucas with p = 2.
 *
 * COMPLEXITY:
 * Table: O(p) (or O(N) for method 1). Query: O(1) for method 1, O(log_p n) for Lucas.
 */
public class LucasTheorem {

    private final long p;
    private final long[] fact, invFact;

    /** Precomputes factorials 0..p-1 modulo the prime p (keep p modest, e.g. < 10^7). */
    public LucasTheorem(int p) {
        if (p < 2 || !java.math.BigInteger.valueOf(p).isProbablePrime(30)) throw new IllegalArgumentException("p must be prime");
        this.p = p;
        fact = new long[p];
        invFact = new long[p];
        fact[0] = 1;
        for (int i = 1; i < p; i++) fact[i] = fact[i - 1] * i % p;
        invFact[p - 1] = power(fact[p - 1], p - 2, p);                // Fermat's little theorem
        for (int i = p - 1; i > 0; i--) invFact[i - 1] = invFact[i] * i % p; // 1/(i-1)! = i / i!
    }

    static long power(long base, long exp, long mod) {
        long r = 1;
        base %= mod;
        while (exp > 0) {
            if ((exp & 1) == 1) r = r * base % mod;
            base = base * base % mod;
            exp >>= 1;
        }
        return r;
    }

    /** C(n, k) mod p for 0 <= n < p (direct factorial formula). */
    public long smallBinomial(int n, int k) {
        if (k < 0 || k > n) return 0;
        if (n >= p) throw new IllegalArgumentException("n must be < p; use binomial() (Lucas) instead");
        return fact[n] * invFact[k] % p * invFact[n - k] % p;
    }

    /** C(n, k) mod p for any n, k >= 0 via Lucas' theorem. */
    public long binomial(long n, long k) {
        if (k < 0 || k > n) return 0;
        long result = 1;
        while (n > 0 || k > 0) {
            int ni = (int) (n % p), ki = (int) (k % p);   // current base-p digits
            if (ki > ni) return 0;                         // a zero factor
            result = result * smallBinomial(ni, ki) % p;
            n /= p;
            k /= p;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println("--- LUCAS' THEOREM DEMO ---");
        LucasTheorem mod13 = new LucasTheorem(13);
        System.out.println("C(10, 3) mod 13 = " + mod13.smallBinomial(10, 3) + " (120 mod 13 = 3)");
        System.out.println("C(1000, 300) mod 13 = " + mod13.binomial(1000, 300)
                + " (BigInteger check: " + exact(1000, 300).mod(java.math.BigInteger.valueOf(13)) + ")");
        System.out.println("C(10^18, 10^9) mod 13 = " + mod13.binomial(1_000_000_000_000_000_000L, 1_000_000_000L));

        LucasTheorem mod2 = new LucasTheorem(2);
        System.out.println("\nPascal's triangle mod 2 (Sierpinski triangle):");
        for (int n = 0; n < 16; n++) {
            StringBuilder row = new StringBuilder(" ".repeat(16 - n));
            for (int k = 0; k <= n; k++) row.append(mod2.binomial(n, k) == 1 ? "* " : "  ");
            System.out.println(row);
        }
    }

    private static java.math.BigInteger exact(int n, int k) {
        java.math.BigInteger r = java.math.BigInteger.ONE;
        for (int i = 0; i < k; i++) r = r.multiply(java.math.BigInteger.valueOf(n - i)).divide(java.math.BigInteger.valueOf(i + 1));
        return r;
    }
}
