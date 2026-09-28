package math.rootfinding;

/**
 * WHAT IT IS: Bisection Method. A root-finding method that repeatedly bisects an interval.
 * STRATEGY: Evaluate function at endpoints. If signs differ, root is inside. Bisect and repeat.
 * TIME/SPACE COMPLEXITY: Time: O(log((b-a)/epsilon)), Space: O(1).
 * REAL-WORLD ANALOGY / USE CASE: Binary search on continuous functions.
 * WHEN TO USE / COMBINATION: Continuous functions with known sign changes.
 * 
 * PSEUDOCODE:
 * while (b - a) > tol:
 *   c = (a + b) / 2
 *   if f(c) == 0 break
 *   if f(a) * f(c) < 0 then b = c else a = c
 */
public class BisectionMethod {
    public static double findRoot(double a, double b, double tol) {
        if (f(a) * f(b) >= 0) {
            System.out.println("No guaranteed root.");
            return Double.NaN;
        }
        double c = a;
        while ((b - a) >= tol) {
            c = (a + b) / 2;
            if (f(c) == 0.0) break;
            else if (f(c) * f(a) < 0) b = c;
            else a = c;
        }
        return c;
    }
    
    private static double f(double x) {
        return x * x * x - x * x + 2; // x^3 - x^2 + 2
    }
    
    public static void main(String[] args) {
        System.out.println("Root: " + findRoot(-200, 300, 1e-5));
    }
}
