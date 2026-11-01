package algorithms.math;

/**
 * GCD & LCM (Euclidean Algorithm)
 * 
 * WHAT IT IS:
 * GCD: Greatest Common Divisor (also called Highest Common Factor).
 * LCM: Least Common Multiple.
 * 
 * STRATEGY (Euclidean Algorithm):
 * The GCD of two numbers doesn't change if the larger number is replaced by its 
 * difference with the smaller number.
 * Better yet, we can use MODULO to skip many subtraction steps!
 * `gcd(a, b) = gcd(b, a % b)`
 * 
 * Once you have the GCD, the LCM is trivial:
 * `LCM(a, b) = (a * b) / GCD(a, b)`
 * 
 * COMPLEXITY:
 * Time: O(log(min(a, b)))
 * Space: O(log(min(a, b))) for recursion stack, or O(1) if written iteratively.
 */
public class GCDAndLCM {

    // Recursive Euclidean Algorithm for GCD
    // GCD is defined as non-negative, so the result is made positive
    // (Java's % keeps the sign of the dividend, e.g. gcd(-4, -6) would give -2).
    public static int gcd(int a, int b) {
        if (b == 0) {
            return Math.abs(a);
        }
        return gcd(b, a % b);
    }

    // Formula for LCM. Divide BEFORE multiplying: a * b can overflow int
    // even when the LCM itself fits.
    public static int lcm(int a, int b) {
        if (a == 0 || b == 0) return 0;
        return Math.abs(a / gcd(a, b) * b);
    }

    public static void main(String[] args) {
        System.out.println("--- GCD & LCM DEMO ---");
        
        int a = 15;
        int b = 20;
        
        System.out.println("Numbers: " + a + " and " + b);
        System.out.println("GCD: " + gcd(a, b)); // Expected: 5
        System.out.println("LCM: " + lcm(a, b)); // Expected: 60
    }
}
