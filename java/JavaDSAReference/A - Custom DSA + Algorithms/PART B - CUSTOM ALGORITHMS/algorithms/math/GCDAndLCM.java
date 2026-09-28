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
    public static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    // Formula for LCM
    public static int lcm(int a, int b) {
        return (a * b) / gcd(a, b);
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
