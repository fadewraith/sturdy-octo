package geometry.bresenham;

import java.util.ArrayList;
import java.util.List;

/**
 * BRESENHAM'S LINE ALGORITHM
 * 
 * WHAT IT IS:
 * A classic computer graphics algorithm used to draw a line between two points on a 
 * discrete pixel grid using ONLY integer arithmetic (no floating-point, no trigonometry).
 * 
 * APPLIES TO:
 * - Rasterization (converting a mathematical vector line into pixels on a screen).
 * 
 * STRATEGY:
 * To draw a line from (x0, y0) to (x1, y1), we want to step x from x0 to x1, and at 
 * each step, decide whether to keep y the same, or increment y. 
 * We maintain an `error` accumulator. At each x-step, we add the line's slope to `error`.
 * Once `error` exceeds a threshold, we step y and reset `error`.
 * 
 * The genius of Bresenham's algorithm is that it multiplies out all the fractional 
 * slope math so that it ONLY uses integer addition, subtraction, and bit-shifting!
 * 
 * COMPLEXITY:
 * Time: O(L) where L is the length of the line in pixels.
 * Space: O(L) to store the result points.
 * 
 * PSEUDOCODE:
 * dx = abs(x1 - x0)
 * dy = abs(y1 - y0)
 * sx = x0 < x1 ? 1 : -1
 * sy = y0 < y1 ? 1 : -1
 * err = dx - dy
 * 
 * loop:
 *   plot(x0, y0)
 *   if (x0 == x1 && y0 == y1) break
 *   e2 = 2 * err
 *   if (e2 > -dy) { err -= dy; x0 += sx; }
 *   if (e2 < dx)  { err += dx; y0 += sy; }
 */
public class BresenhamsLine {

    static class Point {
        int x, y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

    public static List<Point> drawLine(int x0, int y0, int x1, int y1) {
        List<Point> linePoints = new ArrayList<>();
        
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        
        // Step directions
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        
        // Initial error
        int err = dx - dy;

        while (true) {
            linePoints.add(new Point(x0, y0));
            
            // Reached the destination
            if (x0 == x1 && y0 == y1) {
                break;
            }
            
            int e2 = 2 * err; // Double the error to avoid floating point division by 2
            
            // Adjust X
            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }
            // Adjust Y
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }
        
        return linePoints;
    }

    public static void main(String[] args) {
        System.out.println("--- BRESENHAM'S LINE ALGORITHM DEMO ---");
        
        int startX = 0, startY = 0;
        int endX = 5, endY = 3;
        
        System.out.println("Drawing line from (0,0) to (5,3):");
        List<Point> line = drawLine(startX, startY, endX, endY);
        
        for (Point p : line) {
            System.out.println(p);
        }
        // Expected: (0, 0), (1, 1), (2, 1), (3, 2), (4, 2), (5, 3)
    }
}
