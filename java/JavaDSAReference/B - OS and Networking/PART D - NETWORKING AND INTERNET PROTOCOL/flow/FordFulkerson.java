package net.flow;

import java.util.*;

public class FordFulkerson {
    // Ford-Fulkerson computes the maximum flow in a flow network.
    // Used for bandwidth allocation and bipartite matching.
    // Applies to capacity-constrained network graphs.
    
    public int maxFlow(int[][] capacity, int source, int sink) {
        int n = capacity.length;
        int[][] residualGraph = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(capacity[i], 0, residualGraph[i], 0, n);
        }
        
        int[] parent = new int[n];
        int maxFlow = 0;
        
        while (dfs(residualGraph, source, sink, parent)) {
            int pathFlow = Integer.MAX_VALUE;
            
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);
            }
            
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                residualGraph[u][v] -= pathFlow;
                residualGraph[v][u] += pathFlow;
            }
            
            maxFlow += pathFlow;
        }
        
        return maxFlow;
    }
    
    private boolean dfs(int[][] residualGraph, int source, int sink, int[] parent) {
        int n = residualGraph.length;
        boolean[] visited = new boolean[n];
        
        visited[source] = true;
        parent[source] = -1;
        
        Stack<Integer> stack = new Stack<>();
        stack.push(source);
        
        while (!stack.isEmpty()) {
            int u = stack.pop();
            
            for (int v = 0; v < n; v++) {
                if (!visited[v] && residualGraph[u][v] > 0) {
                    stack.push(v);
                    parent[v] = u;
                    visited[v] = true;
                    if (v == sink) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
