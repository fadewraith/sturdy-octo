package practiceQuestions.algomapio.graphs.medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class CloneGraph {

    /**
     * Step-by-Step Thought Process
     * Understand the problem: Create a deep copy of a graph where each node has a value and a list of neighbor nodes.
     * If the input node is None, return None.
     * Initialize a dictionary (o_to_n) to map original nodes to their clones.
     * Initialize a stack with the starting node and a set (visited) to track processed nodes.
     * While the stack is not empty, pop a node and create a new node with the same value, storing it in o_to_n.
     * For each neighbor of the popped node, if not visited, add it to the stack and visited set.
     * After creating all nodes, iterate through o_to_n to populate each new node’s neighbors list using the cloned neighbors from o_to_n.
     * Return the cloned node corresponding to the starting node.
     * */

    public Node cloneGraph(Node node) {
        if (node == null) return null;

        Map<Node, Node> oToN = new HashMap<>();
        Stack<Node> stk = new Stack<>();
        Set<Node> visited = new HashSet<>();

        stk.push(node);
        visited.add(node);

        // DFS to create the nodes
        while (!stk.isEmpty()) {
            Node curr = stk.pop();
            oToN.put(curr, new Node(curr.val));

            for (Node nei : curr.neighbors) {
                if (!visited.contains(nei)) {
                    visited.add(nei);
                    stk.push(nei);
                }
            }
        }

        // DFS to set up neighbors
        for (Map.Entry<Node, Node> entry : oToN.entrySet()) {
            Node oldNode = entry.getKey();
            Node newNode = entry.getValue();
            for (Node nei : oldNode.neighbors) {
                newNode.neighbors.add(oToN.get(nei));
            }
        }

        return oToN.get(node);
    }
}

class Node {
    public int val;
    public List<Node> neighbors;
    public Node() {
        val = 0;
        neighbors = new ArrayList<>();
    }
    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<>();
    }
    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
}

/**
 * Detailed Explanation
 * Understanding the Clone Graph Problem
 * In the Clone Graph problem, you're given a reference node from a connected undirected graph. Each node contains a value and a list of neighbors. The task is to create a deep copy of the entire graph, such that each node in the new graph is a completely new object with identical structure and connections.
 *
 * Challenges of Cloning a Graph
 * The major challenge in cloning a graph lies in preserving the structure while avoiding infinite loops due to cycles. Additionally, since nodes reference each other via their neighbors, we must ensure each original node maps to exactly one newly created node.
 *
 * Step-by-Step Strategy to Clone the Graph
 * The approach uses Depth-First Search (DFS) or Breadth-First Search (BFS) with a hash map to keep track of cloned nodes:
 *
 * First, handle the base case: if the input node is null, return null. Next, initialize a hash map (let's call it o_to_n) that will map each original node to its corresponding cloned node.
 *
 * Then, define a recursive DFS function that:
 *
 * Checks if the node has already been cloned — if so, returns it.
 * Otherwise, creates a new node with the same value and adds it to o_to_n.
 * Iterates through the current node’s neighbors and recursively clones them, adding the results to the cloned node’s neighbor list.
 * Finally, invoke the DFS function starting with the input node and return the resulting cloned node. The graph will be fully copied.
 *
 * Time and Space Complexity
 * Since we visit each node exactly once and clone it, the time complexity is O(n), where n is the number of nodes in the graph. The space complexity is also O(n) due to the hash map and recursion stack (in case of DFS).
 *
 * Practical Applications
 * Cloning graphs is a fundamental task in many domains such as:
 *
 * Network topology replication
 * Social network analysis
 * Version control systems and simulations
 * This problem teaches how to manage references in complex data structures, which is key to mastering graph algorithms.
 * */
