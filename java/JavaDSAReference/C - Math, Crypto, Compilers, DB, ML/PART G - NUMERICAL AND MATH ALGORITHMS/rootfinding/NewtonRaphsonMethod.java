package math.rootfinding;

/**
 * WHAT IT IS: Newton-Raphson Method. Root finding algorithm using derivatives.
 * STRATEGY: Use tangent line at current guess to find x-intercept for next guess.
 * TIME/SPACE COMPLEXITY: Time: Quadratic convergence, Space: O(1).
 * REAL-WORLD ANALOGY / USE CASE: Optimization problems, inverse kinematics.
 * WHEN TO USE / COMBINATION: When function is differentiable and derivative is easy to compute.
 * 
 * PSEUDOCODE:
 * x = guess
 * while |f(x)| > tol:
 *   x = x - f(x)/f'(x)
 */
public class NewtonRaphsonMethod {
    public static double findRoot(double guess, double tol) {
        double x = guess;
        while (Math.abs(f(x)) > tol) {
            x = x - f(x) / df(x);
        }
        return x;
    }
    
    private static double f(double x) {
        return x * x - 4; // root at 2, -2
    }
    
    private static double df(double x) {
        return 2 * x;
    }
    
    public static void main(String[] args) {
        System.out.println("Root (guess 10): " + findRoot(10, 1e-5));
    }
}
