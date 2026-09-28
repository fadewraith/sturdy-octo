package math.numbertheory;

/**
 * WHAT IT IS: Finds an integer x such that (a * x) % m == 1.
 * STRATEGY: Can use Extended Euclidean Algorithm (works if gcd(a, m) = 1) or Fermat's Little Theorem (works if m is prime).
 * TIME/SPACE COMPLEXITY: O(log(min(a, m))) time, O(1) or O(log(min(a,m))) space.
 * REAL-WORLD ANALOGY / USE CASE: Division in modular arithmetic, RSA decryption.
 * WHEN TO USE / COMBINATION: Used for dividing under modulo, e.g., calculating combinations nCr % m.
 * 
 * PSEUDOCODE:
 * // Using Fermat's Little Theorem (m must be prime)
 * return modPow(a, m - 2, m)
 * 
 * // Using Extended Euclidean (gcd must be 1)
 * gcd, x, y = extendedGcd(a, m)
 * if gcd != 1 return None
 * return (x % m + m) % m
 */
public class ModularMultiplicativeInverse {
    // Requires a and m to be coprime
    public static long modInverseExtendedEuclidean(long a, long m) {
        long m0 = m;
        long y = 0, x = 1;

        if (m == 1) return 0;

        while (a > 1) {
            long q = a / m;
            long t = m;
            m = a % m;
            a = t;
            t = y;
            y = x - q * y;
            x = t;
        }

        if (x < 0) x += m0;

        return x;
    }

    // Requires m to be prime
    public static long modInverseFermat(long a, long m) {
        return ModularExponentiation.modPow(a, m - 2, m);
    }

    public static void main(String[] args) {
        long a = 3, m = 11;
        System.out.println("Inverse of " + a + " mod " + m + " (Extended Euclidean): " + modInverseExtendedEuclidean(a, m));
        System.out.println("Inverse of " + a + " mod " + m + " (Fermat): " + modInverseFermat(a, m));
        
        a = 10; m = 17;
        System.out.println("Inverse of " + a + " mod " + m + " (Extended Euclidean): " + modInverseExtendedEuclidean(a, m));
        
        a = 10; m = 15; // gcd(10, 15) != 1, so inverse doesn't exist. Not printing to avoid exceptions in demonstration.
    }
}
