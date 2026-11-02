package algorithms.graph;

/**
 * TOPOLOGICAL SORT (DFS + cycle detection, Kahn's BFS, cycle finding)
 *
 * WHAT IT IS:
 * A linear ordering of vertices such that for every directed edge U -> V,
 * vertex U comes BEFORE vertex V in the ordering.
 *
 * WHEN TO USE THIS:
 * - ONLY applies to Directed Acyclic Graphs (DAG). If there's a cycle, sorting is impossible!
 * - Task scheduling, build systems (make/npm), determining course prerequisites
 *   (e.g., you must take Algebra before Calculus), spreadsheet recalculation order.
 * - "Can all courses be finished?" == "is there a topological order?" == "no cycle".
 *
 * STRATEGY 1 (DFS Based, with cycle detection):
 * We perform a DFS. Instead of printing a node immediately, we wait until all its
 * children are fully processed. Once a node is "finished", we push it onto a Stack.
 * Popping the stack gives the order.
 *
 * To DETECT A CYCLE we need three states, not just visited / not visited:
 *   WHITE (0) = not visited yet
 *   GRAY  (1) = on the current DFS path (started, not finished)
 *   BLACK (2) = finished
 * Reaching a GRAY vertex means we found an edge back into our own path -> a cycle.
 * (Reaching a BLACK vertex is fine: that's just another path into a finished part.)
 *
 * STRATEGY 2 (Kahn's Algorithm, BFS Based):
 * 1. Count the in-degree (incoming edges) of every vertex.
 * 2. Put every vertex with in-degree 0 (no prerequisites) in a queue.
 * 3. Repeatedly take one out, append it to the order, and "delete" its outgoing
 *    edges: decrement each neighbour's in-degree, enqueueing any that reach 0.
 * 4. If the order has fewer than V vertices, the rest are stuck on a cycle.
 * Kahn's is iterative (no recursion depth problems) and naturally gives the
 * vertices "level by level", which is handy for parallel scheduling.
 *
 * FINDING THE CYCLE ITSELF:
 * Same 3-color DFS, but remember each vertex's parent. When we hit a GRAY vertex v
 * from u, the cycle is v -> ... -> u -> v; walk the parent links from u back to v.
 *
 * INPUT FORMAT: adjacency matrix, adjMatrix[u][v] != 0 means an edge u -> v.
 *
 * COMPLEXITY:
 * Time: O(V + E) with adjacency lists; O(V^2) with the adjacency matrix used here.
 * Space: O(V) for the colour/in-degree arrays, the stack and the queue.
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

    private static final int WHITE = 0, GRAY = 1, BLACK = 2;

    /** Prints a topological order (or reports the cycle). Kept for the original demo. */
    public static void sort(int[][] adjMatrix) {
        int[] order = topologicalOrderDFS(adjMatrix);
        if (order == null) {
            System.out.println("No topological order: the graph has a cycle.");
            return;
        }
        System.out.print("Topological Sort Order: ");
        for (int v : order) System.out.print(v + " ");
        System.out.println();
    }

    /**
     * DFS topological sort. Returns the order, or null if the graph has a cycle.
     */
    public static int[] topologicalOrderDFS(int[][] adjMatrix) {
        int vertices = adjMatrix.length;
        int[] color = new int[vertices];
        IntStack stack = new IntStack(vertices);

        // Run DFS from every unvisited vertex
        for (int i = 0; i < vertices; i++) {
            if (color[i] == WHITE && !dfs(adjMatrix, i, color, stack)) {
                return null; // cycle found
            }
        }

        int[] order = new int[vertices];
        int k = 0;
        while (!stack.isEmpty()) order[k++] = stack.pop();
        return order;
    }

    // Returns false as soon as a cycle is found
    private static boolean dfs(int[][] adjMatrix, int u, int[] color, IntStack stack) {
        color[u] = GRAY; // on the current path

        for (int v = 0; v < adjMatrix.length; v++) {
            if (adjMatrix[u][v] == 0) continue;
            if (color[v] == GRAY) return false;                         // back edge -> cycle
            if (color[v] == WHITE && !dfs(adjMatrix, v, color, stack)) return false;
        }

        color[u] = BLACK;
        // Push to stack ONLY AFTER all dependencies (children) are processed!
        stack.push(u);
        return true;
    }

    /**
     * KAHN'S ALGORITHM. Returns the order, or null if the graph has a cycle.
     * Ties are broken by the queue (FIFO), smaller indices enqueued first.
     */
    public static int[] kahn(int[][] adjMatrix) {
        int V = adjMatrix.length;
        int[] inDegree = new int[V];
        for (int u = 0; u < V; u++)
            for (int v = 0; v < V; v++)
                if (adjMatrix[u][v] != 0) inDegree[v]++;

        // plain array queue: every vertex is enqueued at most once
        int[] queue = new int[V];
        int head = 0, tail = 0;
        for (int v = 0; v < V; v++) if (inDegree[v] == 0) queue[tail++] = v;

        int[] order = new int[V];
        int count = 0;
        while (head < tail) {
            int u = queue[head++];
            order[count++] = u;
            for (int v = 0; v < V; v++) {
                if (adjMatrix[u][v] != 0 && --inDegree[v] == 0) queue[tail++] = v;
            }
        }
        return count == V ? order : null; // leftovers are stuck on a cycle
    }

    /** "Course Schedule": can every course be taken? prerequisites[i] = {course, mustTakeFirst}. */
    public static boolean canFinish(int numCourses, int[][] prerequisites) {
        int[][] adj = new int[numCourses][numCourses];
        for (int[] p : prerequisites) adj[p[1]][p[0]] = 1; // mustTakeFirst -> course
        return kahn(adj) != null;
    }

    /**
     * Finds one directed cycle and returns its vertices in order
     * (c0 -> c1 -> ... -> ck -> c0), or null if the graph is a DAG.
     */
    public static int[] findCycle(int[][] adjMatrix) {
        int V = adjMatrix.length;
        int[] color = new int[V];
        int[] parent = new int[V];
        for (int s = 0; s < V; s++) {
            if (color[s] != WHITE) continue;
            parent[s] = -1;
            int[] cycle = findCycleDfs(adjMatrix, s, color, parent);
            if (cycle != null) return cycle;
        }
        return null;
    }

    private static int[] findCycleDfs(int[][] adjMatrix, int u, int[] color, int[] parent) {
        color[u] = GRAY;
        for (int v = 0; v < adjMatrix.length; v++) {
            if (adjMatrix[u][v] == 0) continue;
            if (color[v] == GRAY) {
                // cycle is v -> ... -> u -> v: count it, then walk parents back from u
                int length = 1;
                for (int x = u; x != v; x = parent[x]) length++;
                int[] cycle = new int[length];
                int i = length - 1;
                for (int x = u; x != v; x = parent[x]) cycle[i--] = x;
                cycle[0] = v;
                return cycle;
            }
            if (color[v] == WHITE) {
                parent[v] = u;
                int[] cycle = findCycleDfs(adjMatrix, v, color, parent);
                if (cycle != null) return cycle;
            }
        }
        color[u] = BLACK;
        return null;
    }

    private static String str(int[] a) {
        return a == null ? "null" : java.util.Arrays.toString(a);
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
        System.out.println("Kahn's order:           " + str(kahn(graph))); // [4, 5, 0, 2, 3, 1]
        System.out.println("Cycle in DAG:           " + str(findCycle(graph))); // null

        // add 1 -> 5 to close the cycle 5 -> 2 -> 3 -> 1 -> 5
        graph[1][5] = 1;
        System.out.println("\nAfter adding edge 1 -> 5:");
        sort(graph);
        System.out.println("Kahn's order: " + str(kahn(graph))); // null
        System.out.println("Cycle found:  " + str(findCycle(graph))); // [1, 5, 2, 3] (1 -> 5 -> 2 -> 3 -> 1)

        System.out.println("\nCourses: 2 courses, 1 needs 0 -> can finish? "
                + canFinish(2, new int[][]{{1, 0}}));               // true
        System.out.println("Courses: 0 needs 1 and 1 needs 0 -> can finish? "
                + canFinish(2, new int[][]{{1, 0}, {0, 1}}));       // false
    }
}
