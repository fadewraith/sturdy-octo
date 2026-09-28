package algorithms.math;

/**
 * FAST EXPONENTIATION (Binary Exponentiation)
 * 
 * WHAT IT IS:
 * Calculates `x^n` (x to the power of n) extremely fast.
 * 
 * WHEN TO USE THIS:
 * - When `n` is massive.
 * - Brute force is O(N). Fast Exponentiation is O(log N).
 * - E.g. To calculate 2^1000, brute force takes 1000 multiplications. 
 *   Fast Exponentiation takes just 10!
 * 
 * STRATEGY:
 * If `n` is even: x^n = (x^2)^(n/2). We square the base and halve the exponent!
 * If `n` is odd: x^n = x * (x^2)^((n-1)/2). We multiply by `x` once, then it becomes even!
 * 
 * COMPLEXITY:
 * Time: O(log N)
 * Space: O(1) for iterative approach.
 */
public class FastExponentiation {

    public static long power(long base, long exp) {
        long res = 1;
        
        while (exp > 0) {
            // If exp is odd, multiply base with result
            // (exp & 1) is a fast bitwise way to check if exp is odd
            if ((exp & 1) == 1) {
                res = res * base;
            }
            
            // exp must be even now (or we just extracted the 1 from it)
            // Square the base, and halve the exponent
            exp = exp >> 1; // Bitwise fast divide by 2
            base = base * base;
        }
        
        return res;
    }

    public static void main(String[] args) {
        System.out.println("--- FAST EXPONENTIATION DEMO ---");
        
        long base = 3;
        long exp = 10;
        
        System.out.println(base + "^" + exp + " = " + power(base, exp));
        // Expected: 59049 (3^10)
    }
}
