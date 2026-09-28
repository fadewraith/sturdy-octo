/**
 * WHAT IT IS: Bidirectional A* is a heuristic-driven search running from both the start and the goal.
 * STRATEGY: Uses A* search from both ends. Needs careful termination condition because the first intersection of frontiers might not be the optimal path.
 * TIME/SPACE COMPLEXITY: Time: O(b^(d/2)), Space: O(b^(d/2)) - significantly faster than standard A* on large graphs.
 * REAL-WORLD ANALOGY / USE CASE: GPS routing systems mapping a route between two distant cities.
 * WHEN TO USE / COMBINATION: When you have a good admissible heuristic and need to speed up A* on large state spaces where the goal is explicitly known.
 * 
 * PSEUDOCODE:
 * initialize open_f and open_b PriorityQueues
 * while open_f and open_b not empty:
 *   if open_f.peek() + open_b.peek() >= best_path_cost, terminate
 *   expand node from open_f, update best_path_cost if intersecting
 *   expand node from open_b, update best_path_cost if intersecting
 * return best_path
 */
package algorithms.graph.advanced;

// These aren't separate algorithms so much as OPTIMIZATIONS/ADAPTATIONS of Dijkstra's and A* for specific real-world constraints (memory, dynamic changes, precomputation budget, grid structure).

import java.util.*;

public class BidirectionalAStar {

    static class Node {
        int id, x, y;
        Node(int id, int x, int y) { this.id = id; this.x = x; this.y = y; }
    }

    static class Edge {
        int to;
        double weight;
        Edge(int to, double weight) { this.to = to; this.weight = weight; }
    }
    
    static class State implements Comparable<State> {
        int id;
        double f, g;
        State(int id, double f, double g) { this.id = id; this.f = f; this.g = g; }
        public int compareTo(State o) { return Double.compare(this.f, o.f); }
    }

    public static double bidirectionalAStar(List<Node> nodes, List<List<Edge>> adj, int src, int dest) {
        if (src == dest) return 0;
        int n = nodes.size();

        PriorityQueue<State> openF = new PriorityQueue<>();
        PriorityQueue<State> openB = new PriorityQueue<>();
        
        double[] gF = new double[n]; Arrays.fill(gF, Double.POSITIVE_INFINITY);
        double[] gB = new double[n]; Arrays.fill(gB, Double.POSITIVE_INFINITY);
        
        gF[src] = 0;
        gB[dest] = 0;
        
        openF.add(new State(src, heuristic(nodes.get(src), nodes.get(dest)), 0));
        openB.add(new State(dest, heuristic(nodes.get(dest), nodes.get(src)), 0));
        
        double bestPathCost = Double.POSITIVE_INFINITY;
        boolean[] closedF = new boolean[n];
        boolean[] closedB = new boolean[n];

        while (!openF.isEmpty() && !openB.isEmpty()) {
            if (openF.peek().f + openB.peek().f >= bestPathCost) break;

            expand(openF, gF, gB, closedF, closedB, adj, nodes, nodes.get(dest), true);
            bestPathCost = Math.min(bestPathCost, checkIntersection(gF, gB, n));

            if (openF.peek().f + openB.peek().f >= bestPathCost) break;

            expand(openB, gB, gF, closedB, closedF, adj, nodes, nodes.get(src), false);
            bestPathCost = Math.min(bestPathCost, checkIntersection(gF, gB, n));
        }

        return bestPathCost == Double.POSITIVE_INFINITY ? -1 : bestPathCost;
    }

    private static double checkIntersection(double[] gF, double[] gB, int n) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < n; i++) {
            if (gF[i] != Double.POSITIVE_INFINITY && gB[i] != Double.POSITIVE_INFINITY) {
                min = Math.min(min, gF[i] + gB[i]);
            }
        }
        return min;
    }

    private static void expand(PriorityQueue<State> open, double[] gCur, double[] gOther, boolean[] closedCur, boolean[] closedOther, List<List<Edge>> adj, List<Node> nodes, Node target, boolean forward) {
        State curr = open.poll();
        int u = curr.id;
        if (closedCur[u]) return;
        closedCur[u] = true;

        for (Edge e : adj.get(u)) {
            int v = e.to;
            if (closedCur[v]) continue;
            double tentativeG = gCur[u] + e.weight;
            if (tentativeG < gCur[v]) {
                gCur[v] = tentativeG;
                double f = tentativeG + heuristic(nodes.get(v), target);
                open.add(new State(v, f, tentativeG));
            }
        }
    }

    private static double heuristic(Node a, Node b) {
        return Math.hypot(a.x - b.x, a.y - b.y);
    }

    public static void main(String[] args) {
        System.out.println("--- Bidirectional A* Tests ---");
        List<Node> nodes = Arrays.asList(
            new Node(0, 0, 0), new Node(1, 1, 0), new Node(2, 2, 0),
            new Node(3, 0, 1), new Node(4, 1, 1), new Node(5, 10, 10)
        );
        List<List<Edge>> adj = new ArrayList<>();
        for (int i = 0; i < 6; i++) adj.add(new ArrayList<>());
        
        adj.get(0).add(new Edge(1, 1)); adj.get(1).add(new Edge(0, 1));
        adj.get(1).add(new Edge(2, 1)); adj.get(2).add(new Edge(1, 1));
        adj.get(0).add(new Edge(3, 1)); adj.get(3).add(new Edge(0, 1));
        
        System.out.println("Test 1 (Straight line): 0 to 2 -> " + bidirectionalAStar(nodes, adj, 0, 2));
        System.out.println("Test 2 (Unreachable): 0 to 5 -> " + bidirectionalAStar(nodes, adj, 0, 5));
        System.out.println("Test 3 (Same node): 4 to 4 -> " + bidirectionalAStar(nodes, adj, 4, 4));
    }
}
