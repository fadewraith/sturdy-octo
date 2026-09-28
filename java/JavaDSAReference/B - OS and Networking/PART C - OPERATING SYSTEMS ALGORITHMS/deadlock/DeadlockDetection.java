package os.deadlock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DEADLOCK DETECTION USING RESOURCE ALLOCATION GRAPH
 * 
 * WHAT IT IS:
 * We model Processes and Resources as nodes in a directed graph.
 * Edge Process -> Resource means "Waiting for resource".
 * Edge Resource -> Process means "Holding resource".
 * 
 * If a cycle exists in this graph, a DEADLOCK is currently happening!
 */
public class DeadlockDetection {

    static class Node {
        String id;
        List<Node> edges = new ArrayList<>();
        Node(String id) { this.id = id; }
    }

    public static boolean detectCycle(Map<String, Node> graph) {
        Map<Node, Integer> state = new HashMap<>(); // 0: unvisited, 1: visiting, 2: visited
        for (Node n : graph.values()) state.put(n, 0);

        for (Node n : graph.values()) {
            if (state.get(n) == 0) {
                if (dfs(n, state)) return true; // Cycle found = Deadlock
            }
        }
        return false;
    }

    private static boolean dfs(Node n, Map<Node, Integer> state) {
        state.put(n, 1); // Mark visiting
        for (Node neighbor : n.edges) {
            if (state.get(neighbor) == 1) return true; // Back edge = cycle!
            if (state.get(neighbor) == 0 && dfs(neighbor, state)) return true;
        }
        state.put(n, 2); // Mark fully visited
        return false;
    }

    public static void main(String[] args) {
        System.out.println("--- DEADLOCK DETECTION (RAG CYCLE DETECTION) ---");
        
        Map<String, Node> graph = new HashMap<>();
        Node p1 = new Node("P1");
        Node p2 = new Node("P2");
        Node r1 = new Node("R1");
        Node r2 = new Node("R2");
        
        graph.put("P1", p1); graph.put("P2", p2);
        graph.put("R1", r1); graph.put("R2", r2);

        // P1 holds R1, wants R2
        r1.edges.add(p1);
        p1.edges.add(r2);
        
        // P2 holds R2, wants R1 (Creates a perfect cycle/deadlock!)
        r2.edges.add(p2);
        p2.edges.add(r1);

        if (detectCycle(graph)) {
            System.out.println("DEADLOCK DETECTED! A cycle exists in the Resource Allocation Graph.");
        } else {
            System.out.println("No Deadlock.");
        }
    }
}
