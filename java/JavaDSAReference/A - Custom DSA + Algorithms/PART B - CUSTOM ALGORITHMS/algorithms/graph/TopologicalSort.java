package algorithms.graph;

/**
 * TOPOLOGICAL SORT
 * 
 * WHAT IT IS:
 * A linear ordering of vertices such that for every directed edge U -> V, 
 * vertex U comes BEFORE vertex V in the ordering.
 * 
 * WHEN TO USE THIS:
 * - ONLY applies to Directed Acyclic Graphs (DAG). If there's a cycle, sorting is impossible!
 * - Task scheduling, build systems (make/npm), determining course prerequisites 
 *   (e.g., you must take Algebra before Calculus).
 * 
 * STRATEGY (DFS Based):
 * We perform a standard DFS. But instead of printing the node immediately, we wait 
 * until all its children are fully processed. Once a node is "finished", we push it 
 * onto a Stack. Finally, we pop everything off the Stack!
 * 
 * COMPLEXITY:
 * Time: O(V + E)
 * Space: O(V) for the visited array and the stack.
 */
public class TopologicalSort {

    // Simple custom stack to avoid java.util.Stack
    private static class IntStack {
        int[] data;
        int top = -1;
        IntStack(int capacity) { data = new int[capacity]; }
        void push(int val) { data[++top] = val; }
        int pop() { return data[top--]; }
        boolean isEmpty() { return top == -1; }
    }

    public static void sort(int[][] adjMatrix) {
        int vertices = adjMatrix.length;
        boolean[] visited = new boolean[vertices];
        IntStack stack = new IntStack(vertices);

        // Run DFS from every unvisited vertex
        for (int i = 0; i < vertices; i++) {
            if (!visited[i]) {
                dfs(adjMatrix, i, visited, stack);
            }
        }

        System.out.print("Topological Sort Order: ");
        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + " ");
        }
        System.out.println();
    }

    private static void dfs(int[][] adjMatrix, int u, boolean[] visited, IntStack stack) {
        visited[u] = true;

        for (int v = 0; v < adjMatrix.length; v++) {
            if (adjMatrix[u][v] == 1 && !visited[v]) {
                dfs(adjMatrix, v, visited, stack);
            }
        }
        
        // Push to stack ONLY AFTER all dependencies (children) are processed!
        stack.push(u);
    }

    public static void main(String[] args) {
        System.out.println("--- TOPOLOGICAL SORT DEMO ---");
        
        // 5 -> 2, 5 -> 0, 4 -> 0, 4 -> 1, 2 -> 3, 3 -> 1
        int[][] graph = new int[6][6];
        graph[5][2] = 1;
        graph[5][0] = 1;
        graph[4][0] = 1;
        graph[4][1] = 1;
        graph[2][3] = 1;
        graph[3][1] = 1;

        sort(graph);
        // Valid expected output: 5 4 2 3 1 0 (or similar valid ordering)
    }
}
