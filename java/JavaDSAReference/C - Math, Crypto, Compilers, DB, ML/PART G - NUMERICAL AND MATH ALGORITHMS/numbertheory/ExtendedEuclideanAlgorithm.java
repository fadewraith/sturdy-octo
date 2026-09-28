package math.numbertheory;

/**
 * WHAT IT IS: An extension of the Euclidean algorithm that computes the GCD of integers a and b, as well as the coefficients of Bézout's identity.
 * STRATEGY: Maintain variables for the coefficients as we work backwards from the Euclidean algorithm steps.
 * TIME/SPACE COMPLEXITY: O(log(min(a, b))) time, O(log(min(a, b))) space (due to recursion stack).
 * REAL-WORLD ANALOGY / USE CASE: Finding modular multiplicative inverses in cryptography (RSA).
 * WHEN TO USE / COMBINATION: When you need to solve linear Diophantine equations or find modular inverses.
 * 
 * PSEUDOCODE:
 * function extended_gcd(a, b):
 *   if a == 0:
 *     return (b, 0, 1)
 *   gcd, x1, y1 = extended_gcd(b % a, a)
 *   x = y1 - (b / a) * x1
 *   y = x1
 *   return (gcd, x, y)
 */
public class ExtendedEuclideanAlgorithm {
    public static long[] extendedGcd(long a, long b) {
        if (a == 0) {
            return new long[]{b, 0, 1};
        }
        long[] result = extendedGcd(b % a, a);
        long gcd = result[0];
        long x1 = result[1];
        long y1 = result[2];

        long x = y1 - (b / a) * x1;
        long y = x1;

        return new long[]{gcd, x, y};
    }

    public static void main(String[] args) {
        long a = 30;
        long b = 20;
        long[] res = extendedGcd(a, b);
        System.out.println("Extended GCD of " + a + " and " + b + ":");
        System.out.println("GCD: " + res[0] + ", x: " + res[1] + ", y: " + res[2]);
        System.out.println("Verification (a*x + b*y): " + (a * res[1] + b * res[2]));
        
        a = 35; b = 15;
        res = extendedGcd(a, b);
        System.out.println("Extended GCD of " + a + " and " + b + " -> GCD: " + res[0] + ", x: " + res[1] + ", y: " + res[2]);
    }
}
