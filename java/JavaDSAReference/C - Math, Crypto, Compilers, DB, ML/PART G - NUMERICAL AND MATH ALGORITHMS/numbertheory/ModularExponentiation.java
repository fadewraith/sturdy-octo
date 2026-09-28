package math.numbertheory;

/**
 * WHAT IT IS: An efficient algorithm to compute (base^exponent) % mod.
 * STRATEGY: Exponentiation by squaring. We halve the exponent at each step, squaring the base.
 * TIME/SPACE COMPLEXITY: O(log(exponent)) time, O(1) space.
 * REAL-WORLD ANALOGY / USE CASE: Cryptography (Diffie-Hellman, RSA).
 * WHEN TO USE / COMBINATION: Whenever you need to calculate large powers modulo a number without overflowing.
 * 
 * PSEUDOCODE:
 * function power(base, exp, mod):
 *   res = 1
 *   base = base % mod
 *   while exp > 0:
 *     if exp % 2 == 1:
 *       res = (res * base) % mod
 *     exp = exp >> 1
 *     base = (base * base) % mod
 *   return res
 */
public class ModularExponentiation {
    public static long modPow(long base, long exp, long mod) {
        long res = 1;
        base = base % mod;
        
        if (base == 0) return 0;
        
        while (exp > 0) {
            if ((exp & 1) == 1) {
                res = (res * base) % mod;
            }
            exp = exp >> 1;
            base = (base * base) % mod;
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println("2^10 % 1000 = " + modPow(2, 10, 1000));
        System.out.println("3^100 % 10^9+7 = " + modPow(3, 100, 1000000007));
        System.out.println("0^5 % 10 = " + modPow(0, 5, 10)); // Edge case
        System.out.println("5^0 % 10 = " + modPow(5, 0, 10)); // Edge case
    }
}
