package algorithms.approximation;

/**
 * WHAT IT IS: A 2-approximation algorithm for the Minimum Vertex Cover problem.
 * STRATEGY: Repeatedly find a maximal matching by picking an arbitrary edge and adding both its endpoints to the cover, then removing all edges incident to both endpoints.
 * TIME/SPACE COMPLEXITY: O(V + E) time, O(V) space.
 * REAL-WORLD ANALOGY / USE CASE: Placing guards/cameras in hallways (edges) so every hallway is monitored from at least one of its ends (vertices).
 * WHEN TO USE / COMBINATION: Finding a guaranteed 2-approximate vertex cover quickly on general graphs. Exact vertex cover is NP-Complete.
 * 
 * PSEUDOCODE:
 * cover = {}
 * while edges is not empty:
 *   pick arbitrary edge (u, v)
 *   add u, v to cover
 *   remove all edges incident to u or v
 * return cover
 */
// These don't guarantee the optimal solution, only a solution within a proven bound of optimal, in exchange for tractable runtime on NP-hard problems.

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VertexCoverApproximation {
    
    static class Edge {
        int u, v;
        Edge(int u, int v) {
            this.u = u;
            this.v = v;
        }
    }
    
    public static Set<Integer> approximateVertexCover(int numVertices, List<Edge> edges) {
        Set<Integer> cover = new HashSet<>();
        boolean[] visited = new boolean[numVertices];
        
        for (Edge e : edges) {
            if (!visited[e.u] && !visited[e.v]) {
                // Pick edge (u, v)
                cover.add(e.u);
                cover.add(e.v);
                
                // Mark all edges incident to u or v as "covered" implicitly by marking vertices
                visited[e.u] = true;
                visited[e.v] = true;
            }
        }
        
        return cover;
    }

    public static void main(String[] args) {
        System.out.println("--- Vertex Cover Approximation ---");
        
        // Edge Case 1: Empty graph
        System.out.println("Edge Case 1: Empty Graph");
        System.out.println("Cover: " + approximateVertexCover(0, new ArrayList<>()));
        
        // Edge Case 2: No edges
        System.out.println("\nEdge Case 2: No Edges");
        System.out.println("Cover: " + approximateVertexCover(5, new ArrayList<>()));
        
        // Normal Case: 5 Nodes, Star graph (optimal cover is 1 node, approx gives 2)
        System.out.println("\nNormal Case: Star Graph (center 0, connected to 1,2,3,4)");
        List<Edge> starEdges = new ArrayList<>();
        starEdges.add(new Edge(0, 1));
        starEdges.add(new Edge(0, 2));
        starEdges.add(new Edge(0, 3));
        starEdges.add(new Edge(0, 4));
        System.out.println("Cover: " + approximateVertexCover(5, starEdges));
        
        // Normal Case: Triangle (optimal cover is 2, approx gives 2)
        System.out.println("\nNormal Case: Triangle Graph");
        List<Edge> triangle = new ArrayList<>();
        triangle.add(new Edge(0, 1));
        triangle.add(new Edge(1, 2));
        triangle.add(new Edge(2, 0));
        System.out.println("Cover: " + approximateVertexCover(3, triangle));
    }
}
