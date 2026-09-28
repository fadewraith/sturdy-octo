package math.numbertheory;

/**
 * WHAT IT IS: The Euclidean algorithm is an efficient method for computing the greatest common divisor (GCD) of two integers.
 * STRATEGY: GCD(a, b) = GCD(b, a % b). Repeat until b is 0. The non-zero remainder is the GCD.
 * TIME/SPACE COMPLEXITY: O(log(min(a, b))) time. O(1) space for iterative, O(log(min(a, b))) for recursive.
 * REAL-WORLD ANALOGY / USE CASE: Simplifying fractions, cryptography (RSA), dividing things into equal segments.
 * WHEN TO USE / COMBINATION: Used as a fundamental building block in number theory, often combined with modular arithmetic.
 * 
 * PSEUDOCODE:
 * function gcd(a, b):
 *   while b != 0:
 *     temp = b
 *     b = a % b
 *     a = temp
 *   return a
 */
public class EuclideanAlgorithm {
    public static long gcdRecursive(long a, long b) {
        if (b == 0) return a;
        return gcdRecursive(b, a % b);
    }

    public static long gcdIterative(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public static long lcm(long a, long b) {
        if (a == 0 || b == 0) return 0;
        return (Math.abs(a) / gcdIterative(a, b)) * Math.abs(b);
    }

    public static void main(String[] args) {
        System.out.println("GCD Recursive (48, 18): " + gcdRecursive(48, 18));
        System.out.println("GCD Iterative (48, 18): " + gcdIterative(48, 18));
        System.out.println("LCM (48, 18): " + lcm(48, 18));
        System.out.println("GCD (101, 10): " + gcdIterative(101, 10)); // Coprime
        System.out.println("GCD (0, 5): " + gcdIterative(0, 5)); // Edge case 0
    }
}
