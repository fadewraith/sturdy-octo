package algorithms.graph;

/**
 * KOSARAJU'S ALGORITHM (Strongly Connected Components)
 * 
 * WHAT IT IS:
 * Finds all Strongly Connected Components (SCCs) in a DIRECTED graph.
 * 
 * COMPARISON TO TARJAN'S:
 * Kosaraju's is conceptually much simpler to understand than Tarjan's, but it 
 * requires TWO passes of DFS and involves physically creating a transposed 
 * (reversed) graph in memory.
 * 
 * STRATEGY:
 * 1. Pass 1: Run standard DFS on the original graph. As each recursive DFS finishes, 
 *    push the node onto a stack. (This creates a topological-like ordering based on finish times).
 * 2. Transpose the graph (reverse the direction of all edges).
 * 3. Pass 2: Pop nodes from the stack. If the node is unvisited, run a DFS on the 
 *    transposed graph starting from that node. Every node reached during this DFS 
 *    belongs to the same SCC!
 * 
 * COMPLEXITY:
 * Time: O(V + E)
 * Space: O(V + E) for the transposed graph matrix.
 */
public class KosarajusSCC {

    private static class IntStack {
        int[] data;
        int top = -1;
        IntStack(int capacity) { data = new int[capacity]; }
        void push(int val) { data[++top] = val; }
        int pop() { return data[top--]; }
        boolean isEmpty() { return top == -1; }
    }

    public static void findSCCs(int[][] graph) {
        int V = graph.length;
        IntStack stack = new IntStack(V);
        boolean[] visited = new boolean[V];

        // 1. Pass 1: Fill vertices in stack according to finish times
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                fillOrder(graph, i, visited, stack);
            }
        }

        // 2. Transpose the graph
        int[][] transposedGraph = getTranspose(graph);

        // Reset visited array for second DFS
        for (int i = 0; i < V; i++) {
            visited[i] = false;
        }

        // 3. Pass 2: Process all vertices in order defined by Stack
        while (!stack.isEmpty()) {
            int v = stack.pop();

            // Print Strongly connected component of the popped vertex
            if (!visited[v]) {
                System.out.print("SCC: ");
                dfsTransposed(transposedGraph, v, visited);
                System.out.println();
            }
        }
    }

    private static void fillOrder(int[][] graph, int v, boolean[] visited, IntStack stack) {
        visited[v] = true;
        for (int i = 0; i < graph.length; i++) {
            if (graph[v][i] == 1 && !visited[i]) {
                fillOrder(graph, i, visited, stack);
            }
        }
        stack.push(v);
    }

    private static int[][] getTranspose(int[][] graph) {
        int V = graph.length;
        int[][] transposed = new int[V][V];
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (graph[i][j] == 1) {
                    transposed[j][i] = 1; // Reverse the direction!
                }
            }
        }
        return transposed;
    }

    private static void dfsTransposed(int[][] graph, int v, boolean[] visited) {
        visited[v] = true;
        System.out.print(v + " ");
        for (int i = 0; i < graph.length; i++) {
            if (graph[v][i] == 1 && !visited[i]) {
                dfsTransposed(graph, i, visited);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("--- KOSARAJU'S SCC DEMO ---");
        
        int V = 5;
        int[][] graph = new int[V][V];
        graph[1][0] = 1;
        graph[0][2] = 1;
        graph[2][1] = 1;
        graph[0][3] = 1;
        graph[3][4] = 1;

        findSCCs(graph);
    }
}
