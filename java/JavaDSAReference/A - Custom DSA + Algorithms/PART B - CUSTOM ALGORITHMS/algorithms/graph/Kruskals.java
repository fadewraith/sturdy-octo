package algorithms.graph;

import java.util.Arrays;

/**
 * KRUSKAL'S ALGORITHM (Minimum Spanning Tree)
 * 
 * WHAT IT IS:
 * An alternative to Prim's for finding the Minimum Spanning Tree (MST).
 * 
 * WHEN TO USE THIS:
 * - Better than Prim's for SPARSE graphs (many vertices, few edges).
 * 
 * COMBINATION USAGE:
 * - Kruskal's = Greedy Algorithm + Sorting + Union-Find (Disjoint Set).
 * 
 * WHY IT NEEDS UNION-FIND (AND PRIM'S DOESN'T):
 * - Prim's builds the tree outwards from a SINGLE starting node, so it naturally 
 *   avoids cycles by simply not connecting back to itself.
 * - Kruskal's is "edge-centric". It picks the absolute shortest edges anywhere in 
 *   the entire graph, resulting in a disconnected "forest" of mini-trees. It needs 
 *   Union-Find to instantly check if connecting two nodes would accidentally form 
 *   a cycle by joining two nodes that are already in the same mini-tree!
 * 
 * COMPLEXITY:
 * Time: O(E log E) because it sorts all edges.
 * Space: O(V + E)
 */
public class Kruskals {

    static class Edge implements Comparable<Edge> {
        int src, dest, weight;
        
        Edge(int src, int dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }
        
        @Override
        public int compareTo(Edge other) {
            return this.weight - other.weight; // Sort by weight (Greedy)
        }
    }

    // --- UNION FIND / DISJOINT SET DATA STRUCTURE ---
    static class UnionFind {
        int[] parent;
        int[] rank;

        UnionFind(int vertices) {
            parent = new int[vertices];
            rank = new int[vertices];
            for (int i = 0; i < vertices; i++) {
                parent[i] = i; // Initially, every node is its own parent
                rank[i] = 0;
            }
        }

        // Find with Path Compression
        int find(int i) {
            if (parent[i] == i) {
                return i;
            }
            // Compress path on the way up
            parent[i] = find(parent[i]);
            return parent[i];
        }

        // Union by Rank
        void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            
            if (rootX != rootY) {
                if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                } else if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                } else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
            }
        }
    }

    public static void findMST(Edge[] edges, int vertices) {
        // 1. Sort all edges by weight
        Arrays.sort(edges);
        
        UnionFind uf = new UnionFind(vertices);
        Edge[] mst = new Edge[vertices - 1]; // MST will have V-1 edges
        int edgeCount = 0;
        int i = 0;
        
        // 2. Pick edges one by one
        while (edgeCount < vertices - 1 && i < edges.length) {
            Edge nextEdge = edges[i++];
            
            // 3. Check if adding this edge creates a cycle using Union-Find
            int rootX = uf.find(nextEdge.src);
            int rootY = uf.find(nextEdge.dest);
            
            // If roots are different, no cycle! Include it in MST.
            if (rootX != rootY) {
                mst[edgeCount++] = nextEdge;
                uf.union(rootX, rootY); // Merge the two trees
            }
        }
        
        // Print results
        System.out.println("Edge \tWeight");
        int totalWeight = 0;
        for (Edge e : mst) {
            if (e != null) {
                System.out.println(e.src + " - " + e.dest + "\t" + e.weight);
                totalWeight += e.weight;
            }
        }
        System.out.println("Total MST Weight: " + totalWeight);
    }

    public static void main(String[] args) {
        System.out.println("--- KRUSKAL'S ALGORITHM DEMO ---");
        
        int vertices = 4;
        Edge[] edges = {
            new Edge(0, 1, 10),
            new Edge(0, 2, 6),
            new Edge(0, 3, 5),
            new Edge(1, 3, 15),
            new Edge(2, 3, 4)
        };
        
        findMST(edges, vertices);
    }
}
