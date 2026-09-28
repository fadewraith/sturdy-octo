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

    public static boolean isBipartite(int[][] adjMatrix, int startNode) {
        int vertices = adjMatrix.length;
        
        // Colors: -1 (Uncolored), 0 (RED), 1 (BLUE)
        int[] color = new int[vertices];
        for (int i = 0; i < vertices; ++i) {
            color[i] = -1;
        }

        // Color start node RED
        color[startNode] = 0;

        IntQueue queue = new IntQueue(vertices);
        queue.enqueue(startNode);

        while (!queue.isEmpty()) {
            int u = queue.dequeue();

            // Self-loop check (A self-loop immediately ruins a bipartite graph)
            if (adjMatrix[u][u] == 1) return false;

            for (int v = 0; v < vertices; v++) {
                // If there is an edge and neighbor is uncolored
                if (adjMatrix[u][v] == 1 && color[v] == -1) {
                    // Assign alternate color
                    color[v] = 1 - color[u];
                    queue.enqueue(v);
                } 
                // If there is an edge and neighbor HAS THE SAME COLOR! Conflict!
                else if (adjMatrix[u][v] == 1 && color[v] == color[u]) {
                    return false;
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
    }
}
