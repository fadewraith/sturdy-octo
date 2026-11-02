package math.integration;

import java.util.function.DoubleUnaryOperator;

/**
 * NUMERICAL INTEGRATION (Trapezoid, Simpson, Adaptive Simpson, Romberg, Gauss-Legendre)
 *
 * WHAT IT IS:
 * Approximates the definite integral of f from a to b (the area under the curve)
 * when there is no closed-form antiderivative (e.g. exp(-x^2), the bell curve) or
 * f is only available as a function/black box.
 *
 * WHEN TO USE THIS:
 * - Probabilities from densities, physics simulations (work, energy), finance
 *   (option pricing), computer graphics (light transport), engineering.
 *
 * METHODS (n = number of sub-intervals):
 * 1. TRAPEZOID: replace f on each sub-interval by a straight line.
 *      h * (f0/2 + f1 + ... + f(n-1) + fn/2).  Error O(h^2) - halving h divides it by 4.
 * 2. SIMPSON: replace f on each PAIR of sub-intervals by a parabola.
 *      h/3 * (f0 + 4f1 + 2f2 + 4f3 + ... + fn)   (n even).  Error O(h^4) - halving
 *      h divides it by 16; exact for cubics.
 * 3. ADAPTIVE SIMPSON: compute Simpson on [a,b] and on its two halves; if they agree
 *      to the tolerance, accept (plus a Richardson correction (S2 - S1)/15), else
 *      recurse on each half with half the tolerance. Spends effort only where f
 *      wiggles - far fewer evaluations for spiky functions.
 * 4. ROMBERG: run the trapezoid rule with h, h/2, h/4, ... and EXTRAPOLATE
 *      (Richardson) the sequence to h -> 0. Each column of the table cancels the
 *      next error term: very fast convergence for smooth f.
 * 5. GAUSS-LEGENDRE (5 points): instead of equally spaced points, evaluate f at 5
 *      cleverly chosen nodes with weights; exact for polynomials up to degree 9
 *      with only 5 evaluations. Applied per sub-interval (composite).
 *
 * COMPLEXITY:
 * Trapezoid / Simpson / Gauss: O(n) evaluations. Adaptive: depends on f and the
 * tolerance. Romberg with k levels: O(2^k) evaluations, O(k^2) table.
 */
public class NumericalIntegration {

    public static double trapezoid(DoubleUnaryOperator f, double a, double b, int n) {
        double h = (b - a) / n, sum = (f.applyAsDouble(a) + f.applyAsDouble(b)) / 2;
        for (int i = 1; i < n; i++) sum += f.applyAsDouble(a + i * h);
        return sum * h;
    }

    public static double simpson(DoubleUnaryOperator f, double a, double b, int n) {
        if (n % 2 != 0) n++;                                   // Simpson needs an even count
        double h = (b - a) / n, sum = f.applyAsDouble(a) + f.applyAsDouble(b);
        for (int i = 1; i < n; i++) sum += f.applyAsDouble(a + i * h) * (i % 2 == 1 ? 4 : 2);
        return sum * h / 3;
    }

    public static double adaptiveSimpson(DoubleUnaryOperator f, double a, double b, double tolerance) {
        double fa = f.applyAsDouble(a), fb = f.applyAsDouble(b), fm = f.applyAsDouble((a + b) / 2);
        double whole = (b - a) / 6 * (fa + 4 * fm + fb);
        return adaptive(f, a, b, fa, fm, fb, whole, tolerance, 50);
    }

    private static double adaptive(DoubleUnaryOperator f, double a, double b, double fa, double fm, double fb,
                                   double whole, double tol, int depth) {
        double m = (a + b) / 2, lm = (a + m) / 2, rm = (m + b) / 2;
        double flm = f.applyAsDouble(lm), frm = f.applyAsDouble(rm);
        double left = (m - a) / 6 * (fa + 4 * flm + fm);
        double right = (b - m) / 6 * (fm + 4 * frm + fb);
        double diff = left + right - whole;
        if (depth <= 0 || Math.abs(diff) <= 15 * tol) return left + right + diff / 15; // Richardson correction
        return adaptive(f, a, m, fa, flm, fm, left, tol / 2, depth - 1)
             + adaptive(f, m, b, fm, frm, fb, right, tol / 2, depth - 1);
    }

    public static double romberg(DoubleUnaryOperator f, double a, double b, int levels) {
        double[][] r = new double[levels][levels];
        double h = b - a;
        r[0][0] = h / 2 * (f.applyAsDouble(a) + f.applyAsDouble(b));
        for (int i = 1; i < levels; i++) {
            h /= 2;
            double sum = 0;
            for (int k = 1; k < (1 << i); k += 2) sum += f.applyAsDouble(a + k * h); // only the NEW midpoints
            r[i][0] = r[i - 1][0] / 2 + h * sum;
            for (int j = 1; j <= i; j++) {                                          // Richardson extrapolation
                double pow = Math.pow(4, j);
                r[i][j] = (pow * r[i][j - 1] - r[i - 1][j - 1]) / (pow - 1);
            }
        }
        return r[levels - 1][levels - 1];
    }

    private static final double[] GL_NODES = {0, -0.5384693101056831, 0.5384693101056831, -0.9061798459386640, 0.9061798459386640};
    private static final double[] GL_WEIGHTS = {0.5688888888888889, 0.4786286704993665, 0.4786286704993665, 0.2369268850561891, 0.2369268850561891};

    public static double gaussLegendre5(DoubleUnaryOperator f, double a, double b, int panels) {
        double total = 0, h = (b - a) / panels;
        for (int p = 0; p < panels; p++) {
            double lo = a + p * h, mid = lo + h / 2, half = h / 2;
            for (int i = 0; i < 5; i++) total += GL_WEIGHTS[i] * f.applyAsDouble(mid + half * GL_NODES[i]) * half; // map [-1,1] to [lo,lo+h]
        }
        return total;
    }

    public static void main(String[] args) {
        System.out.println("--- NUMERICAL INTEGRATION DEMO ---");
        DoubleUnaryOperator sin = Math::sin;
        System.out.println("Integral of sin(x) on [0, pi] (exact 2):");
        for (int n : new int[]{4, 8, 16}) {
            System.out.printf("  n=%2d  trapezoid error %.2e   Simpson error %.2e%n", n,
                    Math.abs(trapezoid(sin, 0, Math.PI, n) - 2), Math.abs(simpson(sin, 0, Math.PI, n) - 2));
        }
        // trapezoid error /4 per halving, Simpson /16
        System.out.printf("  Romberg (6 levels, 33 evaluations) error %.2e%n", Math.abs(romberg(sin, 0, Math.PI, 6) - 2));
        System.out.printf("  Gauss-Legendre (1 panel, 5 evaluations) error %.2e%n", Math.abs(gaussLegendre5(sin, 0, Math.PI, 1) - 2));

        DoubleUnaryOperator bell = x -> Math.exp(-x * x);
        System.out.printf("%nIntegral of exp(-x^2) on [-10, 10] = %.12f (sqrt(pi) = %.12f)%n", adaptiveSimpson(bell, -10, 10, 1e-12), Math.sqrt(Math.PI));
        int[] calls = {0};
        double spike = adaptiveSimpson(x -> { calls[0]++; return 1 / (1e-4 + x * x); }, -1, 1, 1e-8);
        System.out.printf("Sharp spike 1/(1e-4 + x^2) on [-1, 1] = %.8f (exact %.8f) using %d evaluations%n",
                spike, 2 * Math.atan(1 / 1e-2) / 1e-2, calls[0]);
    }
}
