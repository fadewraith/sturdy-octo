package math.numbertheory;

import java.math.BigInteger;

/**
 * WHAT IT IS: A fast multiplication algorithm that reduces the multiplication of two n-digit numbers to at most n^1.58 single-digit multiplications.
 * STRATEGY: Divide and conquer. Split x and y in halves, compute z0, z1, z2, and combine them.
 * TIME/SPACE COMPLEXITY: O(n^(log_2 3)) approx O(n^1.585) time, O(log n) space.
 * REAL-WORLD ANALOGY / USE CASE: Arbitrary-precision arithmetic libraries (e.g., GMP) use this for large numbers.
 * WHEN TO USE / COMBINATION: Multiplying very large integers where standard O(n^2) multiplication is too slow.
 * 
 * PSEUDOCODE:
 * if x < 10 or y < 10: return x * y
 * n = max(length(x), length(y))
 * m = ceil(n / 2)
 * x1, x0 = split(x, m)
 * y1, y0 = split(y, m)
 * z0 = karatsuba(x0, y0)
 * z2 = karatsuba(x1, y1)
 * z1 = karatsuba(x1 + x0, y1 + y0) - z2 - z0
 * return z2 * 10^(2m) + z1 * 10^m + z0
 */
public class KaratsubaAlgorithm {
    public static BigInteger karatsuba(BigInteger x, BigInteger y) {
        int n = Math.max(x.bitLength(), y.bitLength());
        if (n <= 2000) { // Base case threshold can be optimized
            return x.multiply(y);
        }
        n = (n / 2) + (n % 2);
        
        BigInteger x1 = x.shiftRight(n);
        BigInteger x0 = x.subtract(x1.shiftLeft(n));
        BigInteger y1 = y.shiftRight(n);
        BigInteger y0 = y.subtract(y1.shiftLeft(n));
        
        BigInteger z0 = karatsuba(x0, y0);
        BigInteger z2 = karatsuba(x1, y1);
        BigInteger z1 = karatsuba(x1.add(x0), y1.add(y0)).subtract(z2).subtract(z0);
        
        return z2.shiftLeft(2 * n).add(z1.shiftLeft(n)).add(z0);
    }

    public static void main(String[] args) {
        BigInteger x = new BigInteger("123456789012345678901234567890");
        BigInteger y = new BigInteger("987654321098765432109876543210");
        System.out.println("Result: " + karatsuba(x, y));
        System.out.println("Expected: " + x.multiply(y));
    }
}
