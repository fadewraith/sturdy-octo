package net.flow;

import java.util.*;

public class EdmondsKarp {
    // Edmonds-Karp is an implementation of Ford-Fulkerson that uses BFS to find augmenting paths.
    // This guarantees O(V E^2) time complexity and finds the shortest path in terms of number of edges.
    // Applies to capacity-constrained network graphs; bandwidth allocation, bipartite matching.
    
    public int maxFlow(int[][] capacity, int source, int sink) {
        int n = capacity.length;
        int[][] residualGraph = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(capacity[i], 0, residualGraph[i], 0, n);
        }
        
        int[] parent = new int[n];
        int maxFlow = 0;
        
        while (bfs(residualGraph, source, sink, parent)) {
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
    
    private boolean bfs(int[][] residualGraph, int source, int sink, int[] parent) {
        int n = residualGraph.length;
        boolean[] visited = new boolean[n];
        
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;
        parent[source] = -1;
        
        while (!queue.isEmpty()) {
            int u = queue.poll();
            
            for (int v = 0; v < n; v++) {
                if (!visited[v] && residualGraph[u][v] > 0) {
                    queue.add(v);
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
