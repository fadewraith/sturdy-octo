package algorithms.approximation;

/**
 * WHAT IT IS: Traveling Salesperson Problem Approximation using Nearest Neighbor and 2-opt.
 * STRATEGY: Start at a vertex, greedily visit the nearest unvisited neighbor to form an initial tour. Then use 2-opt local search to improve the tour by swapping pairs of edges to remove self-intersections.
 * TIME/SPACE COMPLEXITY: O(N^2) for Nearest Neighbor, O(N^2) per 2-opt iteration. Space complexity O(N).
 * REAL-WORLD ANALOGY / USE CASE: Delivery vehicle routing, drone path planning where finding the absolute best route is computationally prohibitive.
 * WHEN TO USE / COMBINATION: When N (number of cities) is large, and an exact solution is intractable. Often combined with other meta-heuristics like Simulated Annealing or Tabu Search.
 * 
 * PSEUDOCODE:
 * tour = [0]
 * while tour.length < N:
 *   next = closest unvisited neighbor to tour[-1]
 *   tour.add(next)
 * 
 * improved = true
 * while improved:
 *   improved = false
 *   for i from 1 to N-2:
 *     for j from i+1 to N-1:
 *       if swap(i, j) reduces total distance:
 *         apply swap
 *         improved = true
 */
// These don't guarantee the optimal solution, only a solution within a proven bound of optimal, in exchange for tractable runtime on NP-hard problems.

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TSPApproximation {
    
    public static List<Integer> nearestNeighbor(double[][] dist) {
        int n = dist.length;
        if (n == 0) return new ArrayList<>();
        List<Integer> tour = new ArrayList<>();
        boolean[] visited = new boolean[n];
        
        tour.add(0);
        visited[0] = true;
        
        for (int i = 1; i < n; i++) {
            int last = tour.get(tour.size() - 1);
            int next = -1;
            double minDist = Double.MAX_VALUE;
            
            for (int j = 0; j < n; j++) {
                if (!visited[j] && dist[last][j] < minDist) {
                    minDist = dist[last][j];
                    next = j;
                }
            }
            tour.add(next);
            visited[next] = true;
        }
        return tour;
    }

    public static List<Integer> twoOpt(List<Integer> tour, double[][] dist) {
        int n = tour.size();
        if (n < 4) return new ArrayList<>(tour);
        
        List<Integer> bestTour = new ArrayList<>(tour);
        boolean improved = true;
        
        while (improved) {
            improved = false;
            for (int i = 1; i < n - 2; i++) {
                for (int j = i + 1; j < n - 1; j++) {
                    double currentDist = dist[bestTour.get(i - 1)][bestTour.get(i)] 
                                       + dist[bestTour.get(j)][bestTour.get(j + 1)];
                    double newDist = dist[bestTour.get(i - 1)][bestTour.get(j)] 
                                   + dist[bestTour.get(i)][bestTour.get(j + 1)];
                    
                    if (newDist < currentDist - 1e-9) {
                        // Reverse the sublist from i to j
                        reverse(bestTour, i, j);
                        improved = true;
                    }
                }
            }
        }
        return bestTour;
    }
    
    private static void reverse(List<Integer> list, int i, int j) {
        while (i < j) {
            int temp = list.get(i);
            list.set(i, list.get(j));
            list.set(j, temp);
            i++;
            j--;
        }
    }
    
    public static double calculateCost(List<Integer> tour, double[][] dist) {
        double cost = 0;
        if (tour.isEmpty()) return cost;
        for (int i = 0; i < tour.size() - 1; i++) {
            cost += dist[tour.get(i)][tour.get(i+1)];
        }
        cost += dist[tour.get(tour.size()-1)][tour.get(0)]; // Return to start
        return cost;
    }

    public static void main(String[] args) {
        System.out.println("--- TSP Approximation ---");
        
        // Edge Case 1: Empty graph
        System.out.println("Edge Case 1: Empty Graph");
        double[][] emptyDist = {};
        System.out.println("Nearest Neighbor: " + nearestNeighbor(emptyDist));
        
        // Edge Case 2: 1 Node
        System.out.println("\nEdge Case 2: 1 Node");
        double[][] dist1 = {{0}};
        System.out.println("Nearest Neighbor: " + nearestNeighbor(dist1));
        
        // Normal Case: 4 Nodes
        System.out.println("\nNormal Case: 4 Nodes");
        double[][] dist4 = {
            {0, 10, 15, 20},
            {10, 0, 35, 25},
            {15, 35, 0, 30},
            {20, 25, 30, 0}
        };
        List<Integer> initialTour = nearestNeighbor(dist4);
        System.out.println("Initial Tour (NN): " + initialTour + ", Cost: " + calculateCost(initialTour, dist4));
        List<Integer> optimizedTour = twoOpt(initialTour, dist4);
        System.out.println("Optimized Tour (2-opt): " + optimizedTour + ", Cost: " + calculateCost(optimizedTour, dist4));
    }
}
