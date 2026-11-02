package algorithms.graph;

/**
 * EULERIAN PATH / CIRCUIT (Hierholzer's Algorithm) - UNDIRECTED and DIRECTED
 *
 * WHAT IT IS:
 * Eulerian Path: A trail in a finite graph that visits EVERY EDGE exactly once.
 * Eulerian Circuit: An Eulerian trail that starts and ends on the same vertex.
 *
 * CRITICAL DISTINCTION:
 * Do not confuse this with a Hamiltonian Path!
 * Eulerian: Visit every EDGE exactly once (Easy, O(V + E) time).
 * Hamiltonian: Visit every VERTEX exactly once (NP-Hard Traveling Salesman Problem!).
 *
 * WHEN TO USE THIS:
 * - Routing problems where every connection must be utilized (e.g., a snowplow
 *   that must drive down every single street in a city, DNA fragment assembly).
 * - Directed version: "Reconstruct Itinerary" (use every flight ticket once),
 *   De Bruijn sequences (shortest string containing every k-digit code).
 *
 * WHEN DOES ONE EXIST? (and all edges must be in one connected piece)
 * UNDIRECTED (degree = number of edge ends at a vertex):
 *   - 0 odd-degree vertices -> CIRCUIT, start anywhere that has an edge
 *   - 2 odd-degree vertices -> PATH, must start at one of the two odd vertices
 *   - otherwise             -> impossible
 * DIRECTED (compare out-degree and in-degree of each vertex):
 *   - every vertex has out == in                          -> CIRCUIT
 *   - exactly one vertex has out - in = 1 (the START), exactly one has
 *     in - out = 1 (the END), all others out == in        -> PATH
 *   - otherwise                                           -> impossible
 *
 * STRATEGY (Hierholzer's Algorithm):
 * 1. Find a valid starting node (see the rules above).
 * 2. Do a DFS, destroying edges as you walk across them.
 * 3. When you get stuck (no unused edges left), push the node to a stack.
 * 4. The stack popped in reverse order gives the exact Eulerian Path!
 * 5. If the result has fewer than E + 1 vertices, some edges were unreachable
 *    (the edges are not connected) -> no Eulerian path.
 *
 * INPUT FORMAT: adjacency matrix of edge COUNTS, so parallel edges are allowed.
 *   Undirected: graph[u][v] == graph[v][u] = number of u-v edges (no self-loops).
 *   Directed:   graph[u][v] = number of u -> v edges (self-loops allowed).
 *
 * COMPLEXITY:
 * Time: O(V + E) with adjacency lists. With the matrix used here: O(V^2 + E) for the
 *       directed version (each vertex scans its row once), O(V * E) for the undirected one.
 * Space: O(V + E)
 */
public class EulerianPath {

    private static class IntStack {
        int[] data;
        int top = -1;
        IntStack(int capacity) { data = new int[capacity]; }
        void push(int val) { data[++top] = val; }
        int pop() { return data[top--]; }
        int peek() { return data[top]; }
        boolean isEmpty() { return top == -1; }
        int size() { return top + 1; }
    }

    /**
     * Prints an Eulerian path/circuit of an UNDIRECTED graph, or why there isn't one.
     */
    public static void printEulerPath(int[][] graph) {
        int V = graph.length;
        int oddCount = 0, totalEdges = 0;
        for (int i = 0; i < V; i++) {
            int degree = 0;
            for (int j = 0; j < V; j++) {
                degree += graph[i][j];
                if (j > i) totalEdges += graph[i][j];
            }
            if (degree % 2 == 1) oddCount++;
        }
        if (totalEdges == 0) {
            System.out.println("Eulerian Path: (graph has no edges)");
            return;
        }

        int[] path = findEulerPathUndirected(graph);
        if (path == null) {
            if (oddCount != 0 && oddCount != 2) {
                System.out.println("No Eulerian Path: " + oddCount + " vertices have odd degree (need 0 or 2).");
            } else {
                System.out.println("No Eulerian Path: the edges are not all connected.");
            }
            return;
        }

        System.out.print(oddCount == 0 ? "Eulerian Circuit: " : "Eulerian Path: ");
        for (int v : path) System.out.print(v + " ");
        System.out.println();
    }

    /**
     * UNDIRECTED Eulerian path. Returns the vertices in walking order
     * (E + 1 of them; first == last for a circuit), an empty array if the graph
     * has no edges, or null if no Eulerian path exists.
     */
    public static int[] findEulerPathUndirected(int[][] graph) {
        int V = graph.length;
        int[] edgeCount = new int[V]; // degree = number of edge ends at each vertex
        int totalEdges = 0;

        // Clone graph because we destroy edges as we traverse
        int[][] tempGraph = new int[V][V];
        for (int i = 0; i < V; i++) {
            if (graph[i][i] != 0) throw new IllegalArgumentException("self-loops are not supported");
            for (int j = 0; j < V; j++) {
                tempGraph[i][j] = graph[i][j];
                edgeCount[i] += graph[i][j];
                if (j > i) totalEdges += graph[i][j];
            }
        }
        if (totalEdges == 0) return new int[0];

        // 1. Find starting node (prefer an odd-degree vertex)
        int oddCount = 0;
        int startNode = -1;
        for (int i = 0; i < V; i++) {
            if (edgeCount[i] % 2 == 1) {
                oddCount++;
                if (startNode == -1 || edgeCount[startNode] % 2 == 0) startNode = i;
            } else if (startNode == -1 && edgeCount[i] > 0) {
                startNode = i;
            }
        }
        if (oddCount != 0 && oddCount != 2) return null;

        IntStack currentPath = new IntStack(totalEdges + 2);
        IntStack finalCircuit = new IntStack(totalEdges + 2);

        currentPath.push(startNode);
        int currentVertex = startNode;

        // 2. Walk until stuck
        while (!currentPath.isEmpty()) {
            if (edgeCount[currentVertex] > 0) {
                currentPath.push(currentVertex);

                // Find next adjacent edge
                int nextVertex = -1;
                for (int i = 0; i < V; i++) {
                    if (tempGraph[currentVertex][i] > 0) {
                        nextVertex = i;
                        break;
                    }
                }

                // Remove the edge (destroy it so we don't cross it again)
                tempGraph[currentVertex][nextVertex]--;
                tempGraph[nextVertex][currentVertex]--; // undirected: remove both directions
                edgeCount[currentVertex]--;
                edgeCount[nextVertex]--;

                currentVertex = nextVertex;
            } else {
                // Stuck! Push to final circuit and backtrack
                finalCircuit.push(currentVertex);
                currentVertex = currentPath.pop();
            }
        }

        // 3. Connectivity check: a path of E edges has E + 1 vertices.
        // If we used fewer edges, some edges are in another component.
        if (finalCircuit.size() != totalEdges + 1) return null;

        int[] path = new int[totalEdges + 1];
        for (int i = 0; i < path.length; i++) path[i] = finalCircuit.pop();
        return path;
    }

    /**
     * DIRECTED Eulerian path. Same return convention as the undirected version.
     * Iterative Hierholzer: look at the vertex on top of the stack; if it still has an
     * unused outgoing edge, walk it (push the target); if not, it is finished -> move
     * it to the answer. The answer comes out in reverse order.
     */
    public static int[] findEulerPathDirected(int[][] graph) {
        int V = graph.length;
        int[] out = new int[V], in = new int[V];
        int totalEdges = 0;
        int[][] remaining = new int[V][V];
        for (int u = 0; u < V; u++) {
            for (int v = 0; v < V; v++) {
                remaining[u][v] = graph[u][v];
                out[u] += graph[u][v];
                in[v] += graph[u][v];
                totalEdges += graph[u][v];
            }
        }
        if (totalEdges == 0) return new int[0];

        // 1. Degree rules: pick the start vertex
        int start = -1, startCandidates = 0, endCandidates = 0;
        for (int v = 0; v < V; v++) {
            int diff = out[v] - in[v];
            if (diff == 1) { startCandidates++; start = v; }
            else if (diff == -1) endCandidates++;
            else if (diff != 0) return null;
        }
        if (startCandidates == 0 && endCandidates == 0) {
            for (int v = 0; v < V; v++) if (out[v] > 0) { start = v; break; } // circuit
        } else if (startCandidates != 1 || endCandidates != 1) {
            return null;
        }

        // 2. Hierholzer. next[u] = first column of u's row that may still have an edge,
        //    so each row is scanned only once overall.
        int[] next = new int[V];
        IntStack stack = new IntStack(totalEdges + 1);
        int[] reversed = new int[totalEdges + 1];
        int count = 0;
        stack.push(start);
        while (!stack.isEmpty()) {
            int u = stack.peek();
            while (next[u] < V && remaining[u][next[u]] == 0) next[u]++;
            if (next[u] < V) {
                int v = next[u];
                remaining[u][v]--; // use the edge u -> v
                stack.push(v);
            } else {
                if (count == reversed.length) return null; // can't happen, safety net
                reversed[count++] = stack.pop();
            }
        }

        // 3. Connectivity: every edge must have been used
        if (count != totalEdges + 1) return null;
        int[] path = new int[count];
        for (int i = 0; i < count; i++) path[i] = reversed[count - 1 - i];
        return path;
    }

    private static String str(int[] a) {
        return a == null ? "none" : java.util.Arrays.toString(a);
    }

    public static void main(String[] args) {
        System.out.println("--- EULERIAN PATH (HIERHOLZER'S) DEMO ---");

        // Graph with an Eulerian Circuit (A bow-tie shape)
        int[][] graph = {
            {0, 1, 1, 0, 0},
            {1, 0, 1, 0, 0},
            {1, 1, 0, 1, 1},
            {0, 0, 1, 0, 1},
            {0, 0, 1, 1, 0}
        };

        printEulerPath(graph);
        // Expected: A circuit that hits every single edge exactly once.

        // Path graph 1 - 0 - 2: odd vertices are 1 and 2, so we must start at one of them
        int[][] pathGraph = {
            {0, 1, 1},
            {1, 0, 0},
            {1, 0, 0}
        };
        printEulerPath(pathGraph); // Expected: 1 0 2

        // Star with 3 leaves: 4 odd vertices -> impossible
        int[][] star = {
            {0, 1, 1, 1},
            {1, 0, 0, 0},
            {1, 0, 0, 0},
            {1, 0, 0, 0}
        };
        printEulerPath(star);

        System.out.println("\n--- DIRECTED ---");
        // 0 -> 1 -> 2 -> 0 and 2 -> 3 -> 4 -> 2: every vertex has in == out -> circuit
        int[][] directedCircuit = new int[5][5];
        directedCircuit[0][1] = 1; directedCircuit[1][2] = 1; directedCircuit[2][0] = 1;
        directedCircuit[2][3] = 1; directedCircuit[3][4] = 1; directedCircuit[4][2] = 1;
        System.out.println("Directed circuit: " + str(findEulerPathDirected(directedCircuit)));
        // [0, 1, 2, 3, 4, 2, 0]

        // 0 -> 1, 1 -> 2, 2 -> 1, 1 -> 3: vertex 0 has out-in = 1 (start), 3 has in-out = 1 (end)
        int[][] directedPath = new int[4][4];
        directedPath[0][1] = 1; directedPath[1][2] = 1; directedPath[2][1] = 1; directedPath[1][3] = 1;
        System.out.println("Directed path:    " + str(findEulerPathDirected(directedPath)));
        // [0, 1, 2, 1, 3]

        // 0 -> 1 and 0 -> 2: vertex 0 has out-in = 2 -> impossible
        int[][] fork = new int[3][3];
        fork[0][1] = 1; fork[0][2] = 1;
        System.out.println("Fork 0->1, 0->2:  " + str(findEulerPathDirected(fork))); // none
    }
}
