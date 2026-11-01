package algorithms.graph;

/**
 * BIPARTITE GRAPH CHECK (2-Coloring)
 * 
 * WHAT IT IS:
 * Determines if the vertices of a graph can be split into exactly TWO independent sets 
 * such that no two vertices within the same set share an edge.
 * 
 * WHEN TO USE THIS:
 * - Matching problems (e.g., Can we match a set of Job Applicants to a set of Jobs 
 *   without any internal conflicts?).
 * - Scheduling conflicts (e.g., Can we split these students into two classrooms 
 *   where no two students who hate each other are in the same room?).
 * 
 * STRATEGY (BFS/DFS 2-Coloring):
 * Pick a starting node, color it RED. Color all its neighbors BLUE. Color all their 
 * neighbors RED. If you ever encounter a neighbor that ALREADY HAS THE SAME COLOR 
 * as the current node, the graph is NOT bipartite!
 * 
 * COMPLEXITY:
 * Time: O(V + E)
 * Space: O(V) for the color array and queue.
 */
public class BipartiteCheck {

    private static class IntQueue {
        int[] data;
        int front = 0, rear = 0;
        IntQueue(int capacity) { data = new int[capacity]; }
        void enqueue(int val) { data[rear++] = val; }
        int dequeue() { return data[front++]; }
        boolean isEmpty() { return front == rear; }
    }

    /** Checks the WHOLE graph, including disconnected components. */
    public static boolean isBipartite(int[][] adjMatrix) {
        return isBipartite(adjMatrix, 0);
    }

    /**
     * BFS 2-coloring. Starts at startNode, then also starts a new BFS from every
     * vertex still uncolored - a disconnected graph is bipartite only if EVERY
     * component is, so checking just the start component is not enough.
     */
    public static boolean isBipartite(int[][] adjMatrix, int startNode) {
        int vertices = adjMatrix.length;
        if (vertices == 0) return true;

        // Colors: -1 (Uncolored), 0 (RED), 1 (BLUE)
        int[] color = new int[vertices];
        for (int i = 0; i < vertices; ++i) {
            color[i] = -1;
        }

        IntQueue queue = new IntQueue(vertices);

        for (int k = 0; k < vertices; k++) {
            int source = (startNode + k) % vertices; // startNode first, then the rest
            if (color[source] != -1) continue;       // already colored by an earlier BFS

            color[source] = 0; // Color the component's first node RED
            queue.enqueue(source);

            while (!queue.isEmpty()) {
                int u = queue.dequeue();

                // Self-loop check (A self-loop immediately ruins a bipartite graph)
                if (adjMatrix[u][u] != 0) return false;

                for (int v = 0; v < vertices; v++) {
                    if (adjMatrix[u][v] == 0) continue;
                    // If neighbor is uncolored, assign the alternate color
                    if (color[v] == -1) {
                        color[v] = 1 - color[u];
                        queue.enqueue(v);
                    }
                    // Neighbor HAS THE SAME COLOR! Conflict!
                    else if (color[v] == color[u]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("--- BIPARTITE GRAPH CHECK DEMO ---");
        
        // Bipartite Graph
        int[][] graph1 = {
            {0, 1, 0, 1},
            {1, 0, 1, 0},
            {0, 1, 0, 1},
            {1, 0, 1, 0}
        };
        System.out.println("Graph 1 is Bipartite? " + isBipartite(graph1, 0)); // true

        // Not Bipartite (Contains a triangle)
        int[][] graph2 = {
            {0, 1, 1, 0},
            {1, 0, 1, 0},
            {1, 1, 0, 1},
            {0, 0, 1, 0}
        };
        System.out.println("Graph 2 is Bipartite? " + isBipartite(graph2, 0)); // false

        // Disconnected: an edge 0-1, plus a separate triangle 2-3-4
        int[][] graph3 = {
            {0, 1, 0, 0, 0},
            {1, 0, 0, 0, 0},
            {0, 0, 0, 1, 1},
            {0, 0, 1, 0, 1},
            {0, 0, 1, 1, 0}
        };
        System.out.println("Graph 3 (disconnected, has a triangle) is Bipartite? " + isBipartite(graph3)); // false
    }
}
