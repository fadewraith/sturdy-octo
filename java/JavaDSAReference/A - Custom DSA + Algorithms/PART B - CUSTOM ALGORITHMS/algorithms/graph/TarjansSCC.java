package algorithms.graph;

/**
 * TARJAN'S ALGORITHM (Strongly Connected Components)
 * 
 * WHAT IT IS:
 * Finds all Strongly Connected Components (SCCs) in a DIRECTED graph.
 * An SCC is a maximal subset of vertices where every vertex is reachable from 
 * every other vertex in that subset.
 * 
 * WHEN TO USE THIS:
 * - When you need to find mutually reachable clusters in a network (e.g., finding 
 *   cycles in a package dependency graph).
 * - Faster in practice than Kosaraju's because it only requires ONE pass of DFS.
 * 
 * STRATEGY:
 * It uses a single DFS pass. As we visit nodes, we track two things:
 * 1. `discovery_time`: The timestamp when this node was first visited.
 * 2. `low_link`: The lowest discovery_time reachable from this node (including its descendants).
 * 
 * We push nodes onto a stack as we visit them. When we finish exploring a node, 
 * if its `discovery_time == low_link`, it means it is the "root" of an SCC! 
 * We pop all nodes from the stack until we pop the root node. Those nodes form one SCC.
 * 
 * COMPLEXITY:
 * Time: O(V + E)
 * Space: O(V)
 */
public class TarjansSCC {

    private int time = 0;

    // Simple custom stack to avoid java.util.Stack
    private static class IntStack {
        int[] data;
        int top = -1;
        IntStack(int capacity) { data = new int[capacity]; }
        void push(int val) { data[++top] = val; }
        int pop() { return data[top--]; }
        int peek() { return data[top]; }
        boolean isEmpty() { return top == -1; }
    }

    public void findSCCs(int[][] adjMatrix) {
        int V = adjMatrix.length;
        int[] disc = new int[V];
        int[] low = new int[V];
        boolean[] inStack = new boolean[V];
        IntStack stack = new IntStack(V);

        // Initialize discovery times to -1 (unvisited)
        for (int i = 0; i < V; i++) {
            disc[i] = -1;
            low[i] = -1;
        }

        // Run DFS from each unvisited node
        for (int i = 0; i < V; i++) {
            if (disc[i] == -1) {
                dfs(adjMatrix, i, disc, low, stack, inStack);
            }
        }
    }

    private void dfs(int[][] adjMatrix, int u, int[] disc, int[] low, IntStack stack, boolean[] inStack) {
        disc[u] = low[u] = ++time;
        stack.push(u);
        inStack[u] = true;

        // Go through all neighbors
        for (int v = 0; v < adjMatrix.length; v++) {
            if (adjMatrix[u][v] == 1) {
                if (disc[v] == -1) { // If v is not visited yet
                    dfs(adjMatrix, v, disc, low, stack, inStack);
                    low[u] = Math.min(low[u], low[v]); // Update low-link
                } 
                else if (inStack[v]) { // If v is in stack, it's a back-edge to the current SCC
                    low[u] = Math.min(low[u], disc[v]);
                }
            }
        }

        // If u is a head/root node of an SCC, pop the stack and print the SCC
        if (low[u] == disc[u]) {
            System.out.print("SCC: ");
            int w = -1;
            while (w != u) {
                w = stack.pop();
                System.out.print(w + " ");
                inStack[w] = false;
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        System.out.println("--- TARJAN'S SCC DEMO ---");
        
        int V = 5;
        int[][] graph = new int[V][V];
        graph[1][0] = 1;
        graph[0][2] = 1;
        graph[2][1] = 1;
        graph[0][3] = 1;
        graph[3][4] = 1;

        TarjansSCC t = new TarjansSCC();
        t.findSCCs(graph);
    }
}
