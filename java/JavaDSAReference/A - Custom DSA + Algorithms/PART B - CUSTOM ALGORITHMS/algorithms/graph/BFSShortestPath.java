package algorithms.graph;

/**
 * BFS SHORTEST PATH
 * 
 * WHAT IT IS:
 * Breadth-First Search applied to find the shortest path between a start node 
 * and an end node.
 * 
 * WHEN TO USE THIS:
 * - When the graph is UNWEIGHTED (or all edges have the exact same weight).
 * - It guarantees the shortest path in terms of the NUMBER OF EDGES.
 * - If the graph is weighted, BFS will fail to find the shortest path. You must 
 *   use Dijkstra's algorithm instead.
 * 
 * DATA STRUCTURE:
 * - Operates on a Graph (Adjacency List or Matrix).
 * - Requires a Queue. (Since built-ins are forbidden, we use a simple custom Array-Queue).
 * 
 * COMPLEXITY:
 * Time: O(V + E) where V is vertices, E is edges.
 * Space: O(V) for the queue and visited array.
 */
public class BFSShortestPath {

    // A simple custom Queue to avoid java.util.Queue
    private static class IntQueue {
        int[] data;
        int front = 0;
        int rear = 0;

        IntQueue(int capacity) {
            data = new int[capacity];
        }

        void enqueue(int val) { data[rear++] = val; }
        int dequeue() { return data[front++]; }
        boolean isEmpty() { return front == rear; }
    }

    /**
     * Finds the shortest path in an unweighted graph using an Adjacency Matrix.
     * Returns the number of edges in the shortest path, or -1 if unreachable.
     */
    public static int findShortestPath(int[][] adjMatrix, int startNode, int endNode) {
        int numVertices = adjMatrix.length;
        
        boolean[] visited = new boolean[numVertices];
        int[] distance = new int[numVertices];
        
        // Initialize distances to -1
        for (int i = 0; i < numVertices; i++) {
            distance[i] = -1;
        }

        IntQueue queue = new IntQueue(numVertices);
        
        // Start BFS
        queue.enqueue(startNode);
        visited[startNode] = true;
        distance[startNode] = 0;

        while (!queue.isEmpty()) {
            int current = queue.dequeue();

            if (current == endNode) {
                return distance[current];
            }

            // Check all neighbors
            for (int neighbor = 0; neighbor < numVertices; neighbor++) {
                if (adjMatrix[current][neighbor] == 1 && !visited[neighbor]) {
                    visited[neighbor] = true;
                    distance[neighbor] = distance[current] + 1;
                    queue.enqueue(neighbor);
                }
            }
        }

        return -1; // Unreachable
    }

    public static void main(String[] args) {
        System.out.println("--- BFS SHORTEST PATH DEMO (UNWEIGHTED) ---");
        
        // 0-1-2
        // |   |
        // 3---4
        int[][] graph = {
            {0, 1, 0, 1, 0}, // 0 connects to 1, 3
            {1, 0, 1, 0, 0}, // 1 connects to 0, 2
            {0, 1, 0, 0, 1}, // 2 connects to 1, 4
            {1, 0, 0, 0, 1}, // 3 connects to 0, 4
            {0, 0, 1, 1, 0}  // 4 connects to 2, 3
        };
        
        System.out.println("Shortest path from 0 to 4: " + findShortestPath(graph, 0, 4) + " edges"); 
        // Expected: 2 (Path: 0 -> 3 -> 4)
    }
}
