package geometry.bresenham;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * MIDPOINT CIRCLE ALGORITHM (Bresenham's circle)
 *
 * WHAT IT IS:
 * Chooses which PIXELS to light to draw a circle of integer radius r, using only
 * integer additions and comparisons - no square roots, no floating point, no
 * multiplication in the loop. The circle twin of BresenhamsLine.java.
 *
 * WHEN TO USE THIS:
 * - Raster graphics (classic graphics libraries, embedded/LED displays, fonts,
 *   game engines without a GPU), and filling discs (draw horizontal spans between
 *   the symmetric points).
 *
 * STRATEGY:
 * 8-WAY SYMMETRY: compute only the octant from (0, r) going right until x > y; each
 * point (x, y) gives eight: (+-x, +-y) and (+-y, +-x).
 * In that octant x always steps by 1 and y either stays or drops by 1. Decide by
 * checking whether the MIDPOINT (x + 1, y - 1/2) is inside the circle:
 *   f(x, y) = x^2 + y^2 - r^2   (negative inside)
 *   d = f(x + 1, y - 1/2), kept up to date incrementally:
 *     d < 0  (midpoint inside)  -> keep y;       d += 2x + 3
 *     d >= 0 (midpoint outside) -> y--;          d += 2(x - y) + 5
 *   start: d = 1 - r  (= f(1, r - 1/2) = 5/4 - r rounded for integers)
 * Result: the chosen y is the integer nearest to the true circle in that octant.
 *
 * COMPLEXITY:
 * O(r) time (about r / sqrt(2) loop steps, 8 points each). Space: O(r) for the output.
 */
public class MidpointCircle {

    /** Pixels of a circle of radius r centred at (cx, cy), no duplicates, in generation order. */
    public static List<int[]> circle(int cx, int cy, int r) {
        if (r < 0) throw new IllegalArgumentException("radius must be >= 0");
        Set<Long> seen = new LinkedHashSet<>();
        List<int[]> out = new ArrayList<>();
        int x = 0, y = r, d = 1 - r;
        while (x <= y) {
            int[][] sym = {{x, y}, {y, x}, {-x, y}, {-y, x}, {x, -y}, {y, -x}, {-x, -y}, {-y, -x}};
            for (int[] s : sym) {
                long key = ((long) (cx + s[0]) << 32) ^ ((cy + s[1]) & 0xffffffffL);
                if (seen.add(key)) out.add(new int[]{cx + s[0], cy + s[1]});
            }
            if (d < 0) {
                d += 2 * x + 3;            // midpoint inside: next pixel is east
            } else {
                d += 2 * (x - y) + 5;      // midpoint outside: next pixel is south-east
                y--;
            }
            x++;
        }
        return out;
    }

    public static void main(String[] args) {
        System.out.println("--- MIDPOINT CIRCLE DEMO (r = 8) ---");
        int r = 8;
        List<int[]> pts = circle(0, 0, r);
        char[][] grid = new char[2 * r + 3][2 * r + 3];
        for (char[] row : grid) java.util.Arrays.fill(row, '.');
        for (int[] p : pts) grid[r + 1 - p[1]][p[0] + r + 1] = '#';
        for (char[] row : grid) System.out.println(new String(row).replace("", " ").trim());
        System.out.println(pts.size() + " pixels; every one within half a pixel of the true circle radius " + r);
    }
}
