package math.numbertheory;

/**
 * WHAT IT IS: The Chinese Remainder Theorem (CRT) states that if one knows the remainders of the Euclidean division of an integer n by several integers, then one can determine uniquely the remainder of the division of n by the product of these integers, under the condition that the divisors are pairwise coprime.
 * STRATEGY: Use the extended Euclidean algorithm to find modular inverses and combine the remainders.
 * TIME/SPACE COMPLEXITY: O(k log N) time, O(k) space, where k is the number of congruences and N is the product of moduli.
 * REAL-WORLD ANALOGY / USE CASE: Cryptography (RSA decryption optimization), distributed computing.
 * WHEN TO USE / COMBINATION: When solving systems of simultaneous congruences with pairwise coprime moduli.
 * 
 * PSEUDOCODE:
 * prod = product of all moduli
 * result = 0
 * for each (remainder, modulus) in congruences:
 *     p = prod / modulus
 *     result = (result + remainder * modInverse(p, modulus) * p) % prod
 * return result
 */
public class ChineseRemainderTheorem {
    public static long gcdExtended(long a, long b, long[] xAndY) {
        if (a == 0) {
            xAndY[0] = 0;
            xAndY[1] = 1;
            return b;
        }
        long[] temp = new long[2];
        long gcd = gcdExtended(b % a, a, temp);
        xAndY[0] = temp[1] - (b / a) * temp[0];
        xAndY[1] = temp[0];
        return gcd;
    }

    public static long modInverse(long a, long m) {
        long[] xAndY = new long[2];
        gcdExtended(a, m, xAndY);
        return (xAndY[0] % m + m) % m;
    }

    public static long solveCRT(long[] num, long[] rem) {
        long prod = 1;
        for (long n : num) prod *= n;
        long result = 0;
        for (int i = 0; i < num.length; i++) {
            long pp = prod / num[i];
            result = (result + rem[i] * modInverse(pp, num[i]) * pp) % prod;
        }
        return (result + prod) % prod;
    }

    public static void main(String[] args) {
        long[] num = {3, 4, 5};
        long[] rem = {2, 3, 1};
        System.out.println("Expected: 11, Got: " + solveCRT(num, rem));
    }
}
