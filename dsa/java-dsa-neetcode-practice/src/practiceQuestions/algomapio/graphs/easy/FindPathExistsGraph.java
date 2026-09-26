package practiceQuestions.algomapio.graphs.easy;

import java.util.*;

public class FindPathExistsGraph {

    /**
     * Common Setup
     * Base Case: If source == destination, return True, as no path is needed when starting at the destination.
     * Graph Construction: Create a defaultdict(list) called graph. For each edge [u, v] in edges, append v to graph[u] and u to graph[v], forming an undirected graph with bidirectional connections.
     * Visited Set: Initialize a set seen and add the source node to mark it as visited, preventing cycles during traversal.
     * */

    /**
     * Optimal Solution: Recursive DFS
     * This solution uses a recursive depth-first search to explore the graph, diving deep into each branch before backtracking.
     *
     * DFS Function: Define a recursive function dfs(i) that takes a node i:
     * If i == destination, return True, indicating the target is reached.
     * For each neighbor nei_node in graph[i], if not in seen:
     * Add nei_node to seen.
     * Recursively call dfs(nei_node). If it returns True, propagate True.
     * If no path is found, return False.
     * Execution: Call dfs(source) and return its result.
     * Why It Works: Recursive DFS explores each branch deeply, backtracking when necessary. The seen set ensures nodes are visited once, preventing infinite loops in the undirected graph. It’s concise but may face recursion depth issues for very deep graphs.
     * */

    // Recursive DFS
    public boolean validPath(int n, int[][] edges, int source, int destination) {
        if (source == destination) return true;

        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int[] edge : edges) {
            graph.computeIfAbsent(edge[0], k -> new ArrayList<>()).add(edge[1]);
            graph.computeIfAbsent(edge[1], k -> new ArrayList<>()).add(edge[0]);
        }

        Set<Integer> seen = new HashSet<>();
        seen.add(source);

        return dfs(source, destination, graph, seen);
    }

    private boolean dfs(int node, int destination, Map<Integer, List<Integer>> graph, Set<Integer> seen) {
        if (node == destination) return true;

        for (int neighbor : graph.getOrDefault(node, Collections.emptyList())) {
            if (!seen.contains(neighbor)) {
                seen.add(neighbor);
                if (dfs(neighbor, destination, graph, seen)) return true;
            }
        }
        return false;
    }

    /**
     * Optimal Solution 2: Iterative DFS with Stack
     * This solution uses an iterative depth-first search with a stack, mimicking the recursive approach without recursion overhead.
     *
     * Stack Initialization: Initialize a stack with the source node.
     * Iterative Traversal: While the stack is not empty:
     * Pop a node (node) from the stack.
     * If node == destination, return True.
     * For each neighbor nei_node in graph[node], if not in seen:
     * Add nei_node to seen.
     * Push nei_node onto the stack.
     * Return: If the stack empties, return False.
     * Why It Works: The stack maintains the DFS exploration order, pushing unvisited neighbors to explore later, similar to recursive DFS. It avoids recursion stack limitations, making it robust for deep graphs, while the seen set prevents cycles.
     * */

    // Iterative DFS with Stack
    public boolean validPathSolutionTwo(int n, int[][] edges, int source, int destination) {
        if (source == destination) return true;

        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int[] edge : edges) {
            graph.computeIfAbsent(edge[0], k -> new ArrayList<>()).add(edge[1]);
            graph.computeIfAbsent(edge[1], k -> new ArrayList<>()).add(edge[0]);
        }

        Set<Integer> seen = new HashSet<>();
        Stack<Integer> stack = new Stack<>();
        stack.push(source);
        seen.add(source);

        while (!stack.isEmpty()) {
            int node = stack.pop();
            if (node == destination) return true;
            for (int neighbor : graph.getOrDefault(node, Collections.emptyList())) {
                if (!seen.contains(neighbor)) {
                    seen.add(neighbor);
                    stack.push(neighbor);
                }
            }
        }
        return false;
    }

    /**
     * Optimal Solution 3: BFS with Queue
     * This solution uses a breadth-first search with a queue, exploring nodes level by level from the source.
     *
     * Queue Initialization: Initialize a deque (q) and append the source node.
     * BFS Traversal: While q is not empty:
     * Remove the front node (node) using popleft().
     * If node == destination, return True.
     * For each neighbor nei_node in graph[node], if not in seen:
     * Add nei_node to seen.
     * Append nei_node to the queue.
     * Return: If the queue empties, return False.
     * Why It Works: BFS explores nodes in order of increasing distance from the source, ensuring efficient path discovery. The seen set prevents cycles, and the queue manages level-by-level traversal, making it ideal for finding paths (potentially the shortest in terms of edges).
     * */

    public boolean validPathSolutionThree(int n, int[][] edges, int source, int destination) {
        if (source == destination) return true;

        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int[] edge : edges) {
            graph.computeIfAbsent(edge[0], k -> new ArrayList<>()).add(edge[1]);
            graph.computeIfAbsent(edge[1], k -> new ArrayList<>()).add(edge[0]);
        }

        Set<Integer> seen = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(source);
        seen.add(source);

        while (!queue.isEmpty()) {
            int node = queue.poll();
            if (node == destination) return true;
            for (int neighbor : graph.getOrDefault(node, Collections.emptyList())) {
                if (!seen.contains(neighbor)) {
                    seen.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }
        return false;
    }

}


/**
 * Detailed Explanation
 * Problem Overview
 * The task is to determine whether there is a path between two nodes (source and destination) in an undirected graph. This is a fundamental graph traversal problem that can be solved using depth-first search (DFS), breadth-first search (BFS), or Union-Find. Since the graph is undirected, an edge [u, v] means both u is connected to v and v is connected to u.
 *
 * Approach: Graph Traversal
 * The core idea is to traverse the graph starting from the source node and see if we can reach the destination. To do this, we first represent the graph using an adjacency list. For each edge [u, v], we record both u → v and v → u in a dictionary to capture the undirected nature of the graph.
 *
 * After building the graph, we use a standard DFS approach. A stack is initialized with the source node and a set is used to track visited nodes to prevent cycles or repeated work. At each step, we pop a node from the stack and check if it matches the destination. If it does, we return true. If not, we push all unvisited neighbors of the current node onto the stack and continue. If the traversal ends without finding the destination, we return false.
 *
 * Edge Cases
 * - If the source is equal to the destination, we return true immediately, as the path trivially exists.
 * - If there are no edges or the source node has no connections, we cannot proceed, and false is returned.
 *
 * Time and Space Complexity
 * Time Complexity: O(V + E), where V is the number of nodes and E is the number of edges. We potentially visit every node and edge once.
 *
 * Space Complexity: O(V + E) for storing the graph and visited set, and O(V) for the recursion stack or explicit stack used in DFS.
 *
 * Conclusion
 * This problem is a classic example of graph traversal. DFS is a natural choice when we want to explore paths from one node to another, especially when we do not need the shortest path. The key to an efficient solution is using an adjacency list and tracking visited nodes to avoid unnecessary work.
 * */