package practiceQuestions.algomapio.graphs.medium;

import java.util.*;

public class NetworkDelayTimeDijkstrasAlgorithm {

    /**
     * Step-by-Step Thought Process
     * Understand the problem: Find the time it takes for a signal to reach all nodes in a network from node k, or return -1 if impossible.
     * Create an adjacency list graph using a defaultdict, where each node u maps to a list of (neighbor v, time) pairs.
     * Initialize a min-heap with a tuple (0, k), representing the distance from the source k to itself.
     * Initialize a dictionary min_times to store the minimum time to reach each node.
     * While the min-heap is not empty:
     * Pop the smallest time (time_k_to_i) and node i.
     * If i is already in min_times, skip to the next iteration.
     * Store time_k_to_i in min_times for node i.
     * For each unvisited neighbor nei with time nei_time, push (time_k_to_i + nei_time, nei) to the min-heap.
     * If min_times contains all n nodes, return the maximum time in min_times; otherwise, return -1.
     * */

    public int networkDelayTime(int[][] times, int n, int k) {
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int[] time : times) {
            int u = time[0], v = time[1], t = time[2];
            graph.computeIfAbsent(u, x -> new ArrayList<>()).add(new int[]{v, t});
        }

        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        minHeap.add(new int[]{k, 0});
        Map<Integer, Integer> minTimes = new HashMap<>();

        while (!minHeap.isEmpty()) {
            int[] current = minHeap.poll();
            int node = current[0];
            int time = current[1];

            if (minTimes.containsKey(node)) continue;

            minTimes.put(node, time);
            if (graph.containsKey(node)) {
                for (int[] neighbor : graph.get(node)) {
                    int nei = neighbor[0];
                    int neiTime = neighbor[1];
                    if (!minTimes.containsKey(nei)) {
                        minHeap.add(new int[]{nei, time + neiTime});
                    }
                }
            }
        }

        if (minTimes.size() == n) {
            return Collections.max(minTimes.values());
        } else {
            return -1;
        }
    }
}


/**
 * Detailed Explanation
 * Understanding the Network Delay Time Problem
 * The Network Delay Time problem involves finding the time it takes for a signal to travel from a given source node k to all other nodes in a directed, weighted graph. Each edge represents a transmission time between two nodes. The objective is to return the total time needed for all nodes to receive the signal, or -1 if it’s not possible to reach all nodes.
 *
 * This problem is a classic use case for Dijkstra’s algorithm, which efficiently computes the shortest path from a single source to all other nodes in a graph with non-negative edge weights.
 *
 * Optimal Solution: Dijkstra’s Algorithm Explained
 * To solve this problem, we treat the input times as a list of directed edges with weights. First, we build an adjacency list to represent the graph, mapping each source node to its destination and the corresponding transmission time.
 *
 * We then initialize a min-heap (priority queue) starting with the source node k and a current time of 0. As we pop elements from the heap, we update the shortest known time to each node using a dictionary min_times.
 *
 * For every node we visit, if we haven’t already recorded a time for it, we store the current time as the minimum time it took to reach that node. We then examine its neighbors and add them to the heap with their cumulative travel time.
 *
 * This process continues until all reachable nodes are visited. Finally, we check whether we were able to reach all n nodes. If we did, we return the maximum time recorded in min_times; otherwise, we return -1, indicating that some nodes are unreachable.
 *
 * Time and Space Complexity
 * The time complexity of this Dijkstra’s algorithm implementation is O(E log V), where E is the number of edges and V is the number of nodes. This comes from the use of a priority queue to manage the nodes efficiently. The space complexity is O(V + E) due to the adjacency list and additional data structures used for storing distances and the heap.
 *
 * Why Dijkstra’s Algorithm is Optimal for Network Delay
 * Dijkstra’s algorithm is well-suited for this problem because it guarantees the shortest path in a graph with non-negative weights, exactly matching the constraints of the problem. Unlike Bellman-Ford, which handles negative weights but is slower, or Floyd-Warshall, which computes all-pairs paths unnecessarily, Dijkstra’s approach is fast and focused on the single-source shortest path.
 *
 * Practical Tips for Implementation
 * Use a min-heap from Python’s heapq module to ensure efficient selection of the next node with the smallest known time. Avoid revisiting nodes already present in the min_times dictionary, as we only want to record the shortest time the first time a node is reached.
 * */