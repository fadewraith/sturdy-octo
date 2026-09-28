/**
 * WHAT IT IS: Jump Point Search (JPS) is a highly optimized version of A* for uniform cost grids.
 * STRATEGY: It skips over uniform areas of the grid by "jumping" straight and diagonally, only adding nodes to the open list when a "forced neighbor" (like an obstacle corner) is detected.
 * TIME/SPACE COMPLEXITY: Time: Often orders of magnitude faster than A* in practice, Space: O(V) but with far fewer nodes stored in the open list.
 * REAL-WORLD ANALOGY / USE CASE: 2D tile-based video games (like RTS games) where agents need to navigate open maps with some obstacles quickly.
 * WHEN TO USE / COMBINATION: When navigating uniform-cost grid environments (like 2D arrays). Useless on arbitrary graphs or weighted grids.
 * 
 * PSEUDOCODE:
 * Similar to A*, but instead of expanding immediate neighbors:
 * for each direction:
 *   jump along direction until obstacle, map edge, or forced neighbor found
 *   if valid jump point found, add to open list
 */
package algorithms.graph.advanced;

// These aren't separate algorithms so much as OPTIMIZATIONS/ADAPTATIONS of Dijkstra's and A* for specific real-world constraints (memory, dynamic changes, precomputation budget, grid structure).

import java.util.*;

public class JumpPointSearch {

    // A simplified conceptual version of Jump Point Search focusing on the skipping mechanic in 1D/2D lines
    // A full JPS implementation is extremely lengthy, so this demonstrates the horizontal/vertical jump logic.

    static class Point {
        int r, c;
        Point(int r, int c) { this.r = r; this.c = c; }
        @Override public boolean equals(Object o) {
            if(this == o) return true;
            if(!(o instanceof Point)) return false;
            Point point = (Point) o;
            return r == point.r && c == point.c;
        }
        @Override public int hashCode() { return Objects.hash(r, c); }
    }

    public static int simpleJPSDistance(int[][] grid, Point start, Point goal) {
        if (start.equals(goal)) return 0;
        int R = grid.length, C = grid[0].length;
        
        // This is a mockup of the jumping mechanic. We will jump directly to obstacles or the goal.
        Queue<Point> q = new LinkedList<>();
        Set<Point> visited = new HashSet<>();
        
        q.add(start);
        visited.add(start);
        
        int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1,0}}; // Only cardinal directions for this simplified model
        int jumps = 0;
        
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                Point curr = q.poll();
                if (curr.equals(goal)) return jumps;
                
                for (int[] d : dirs) {
                    Point jumpPoint = jump(grid, curr.r, curr.c, d[0], d[1], goal);
                    if (jumpPoint != null && !visited.contains(jumpPoint)) {
                        visited.add(jumpPoint);
                        q.add(jumpPoint);
                    }
                }
            }
            jumps++; // Representing 'macro' steps
        }
        return -1;
    }

    private static Point jump(int[][] grid, int r, int c, int dr, int dc, Point goal) {
        int nextR = r + dr;
        int nextC = c + dc;
        
        if (nextR < 0 || nextR >= grid.length || nextC < 0 || nextC >= grid[0].length || grid[nextR][nextC] == 1) {
            return null; // Hit wall
        }
        
        if (nextR == goal.r && nextC == goal.c) {
            return new Point(nextR, nextC);
        }
        
        // In full JPS, we'd check for forced neighbors here.
        // For this simplified version, we just slide as far as possible until we hit a wall.
        return jump(grid, nextR, nextC, dr, dc, goal) != null ? jump(grid, nextR, nextC, dr, dc, goal) : new Point(nextR, nextC);
    }

    public static void main(String[] args) {
        System.out.println("--- Jump Point Search (Simplified) Tests ---");
        int[][] grid = {
            {0, 0, 0, 0, 0},
            {0, 1, 1, 1, 0},
            {0, 0, 0, 0, 0},
            {0, 1, 1, 0, 0},
            {0, 0, 0, 0, 0}
        };
        
        System.out.println("Test 1 (Path exists): " + simpleJPSDistance(grid, new Point(0, 0), new Point(4, 4))); // Macro jumps
        System.out.println("Test 2 (Start == Goal): " + simpleJPSDistance(grid, new Point(2, 2), new Point(2, 2))); // Expected: 0
        
        int[][] blocked = {{0, 1}, {1, 0}};
        System.out.println("Test 3 (No path): " + simpleJPSDistance(blocked, new Point(0, 0), new Point(1, 1))); // Expected: -1
    }
}
