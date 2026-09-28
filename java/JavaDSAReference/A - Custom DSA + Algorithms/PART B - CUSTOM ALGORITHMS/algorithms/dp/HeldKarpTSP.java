package algorithms.dp;

import java.util.Arrays;

/**
 * HELD-KARP ALGORITHM (Traveling Salesman Problem via DP + Bitmask)
 * 
 * WHAT IT IS:
 * Finds the EXACT optimal shortest route that visits every city exactly once and 
 * returns to the origin (Traveling Salesman Problem).
 * 
 * WHEN TO USE THIS:
 * - Brute force TSP takes O(N!) time. 
 * - Held-Karp reduces this to O(2^N * N^2) using DP and Bitmasking!
 * - It is only tractable for small-to-moderate N (roughly N <= 20).
 * - Contrast with TSP heuristics (like Nearest Neighbor) which are much faster 
 *   but trade exact optimality for speed.
 * 
 * STRATEGY (Bitmask DP):
 * State is represented by two variables: `(mask, u)`.
 * `mask`: An integer where the i-th bit is 1 if city `i` has been visited.
 * `u`: The current city we are standing in.
 * 
 * We start at city 0 with mask 1 (only city 0 visited). 
 * We recursively try visiting every unvisited city `v`, updating the mask, and 
 * adding the distance `dist[u][v]`. We memoize the minimum distance for every `(mask, u)`.
 * 
 * COMPLEXITY:
 * Time: O(2^N * N^2)
 * Space: O(2^N * N)
 */
public class HeldKarpTSP {

    static final int INF = 9999999;
    
    public static int tsp(int[][] dist) {
        int n = dist.length;
        // memo[mask][i]
        int[][] memo = new int[1 << n][n];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        
        // Start from city 0, so mask is 1 (bit 0 is set)
        return solve(dist, 1, 0, n, memo);
    }

    private static int solve(int[][] dist, int mask, int u, int n, int[][] memo) {
        // Base case: If all cities are visited (mask is all 1s), return distance back to start (city 0)
        if (mask == (1 << n) - 1) {
            return dist[u][0];
        }

        if (memo[mask][u] != -1) {
            return memo[mask][u];
        }

        int minCost = INF;

        // Try visiting every other city
        for (int v = 0; v < n; v++) {
            // If city v is NOT visited (the v-th bit in mask is 0)
            if ((mask & (1 << v)) == 0) {
                // Set the v-th bit to 1, and recurse!
                int newMask = mask | (1 << v);
                int cost = dist[u][v] + solve(dist, newMask, v, n, memo);
                minCost = Math.min(minCost, cost);
            }
        }

        return memo[mask][u] = minCost;
    }

    public static void main(String[] args) {
        System.out.println("--- HELD-KARP TSP (EXACT OPTIMAL) DEMO ---");
        
        int[][] dist = {
            {0, 10, 15, 20},
            {10, 0, 35, 25},
            {15, 35, 0, 30},
            {20, 25, 30, 0}
        };
        
        System.out.println("Minimum TSP Tour Cost: " + tsp(dist));
        // Expected: 80 (0 -> 1 -> 3 -> 2 -> 0 => 10 + 25 + 30 + 15 = 80)
    }
}
