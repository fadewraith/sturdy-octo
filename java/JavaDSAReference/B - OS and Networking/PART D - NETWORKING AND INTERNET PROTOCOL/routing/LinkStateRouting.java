package net.routing;

import java.util.*;

/**
 * Link State Routing
 * Cross-reference: Uses Dijkstra's algorithm applied per-router.
 * Used by routing protocols like OSPF (Open Shortest Path First).
 * 
 * Comparison: Distance Vector = "routing by rumor" vs Link State = "routing by map".
 */
public class LinkStateRouting {

    static class NetworkMap {
        Map<String, Map<String, Integer>> topology = new HashMap<>();

        public void addLink(String u, String v, int cost) {
            topology.putIfAbsent(u, new HashMap<>());
            topology.putIfAbsent(v, new HashMap<>());
            topology.get(u).put(v, cost);
            topology.get(v).put(u, cost);
        }
        
        public Map<String, Integer> dijkstra(String source) {
            Map<String, Integer> distances = new HashMap<>();
            PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.distance));
            Set<String> visited = new HashSet<>();

            for (String node : topology.keySet()) {
                distances.put(node, Integer.MAX_VALUE);
            }
            distances.put(source, 0);
            pq.offer(new Node(source, 0));

            while (!pq.isEmpty()) {
                Node current = pq.poll();
                if (!visited.add(current.id)) continue;

                Map<String, Integer> neighbors = topology.getOrDefault(current.id, Collections.emptyMap());
                for (Map.Entry<String, Integer> neighbor : neighbors.entrySet()) {
                    String nextNode = neighbor.getKey();
                    if (visited.contains(nextNode)) continue;

                    int newDist = distances.get(current.id) + neighbor.getValue();
                    if (newDist < distances.get(nextNode)) {
                        distances.put(nextNode, newDist);
                        pq.offer(new Node(nextNode, newDist));
                    }
                }
            }
            return distances;
        }
    }

    static class Node {
        String id;
        int distance;
        Node(String id, int distance) {
            this.id = id;
            this.distance = distance;
        }
    }

    public static void simulate() {
        NetworkMap networkMap = new NetworkMap();
        // Each router floods its link states to build the complete map.
        // Assuming the map has been built in every router:
        networkMap.addLink("A", "B", 1);
        networkMap.addLink("A", "C", 5);
        networkMap.addLink("B", "C", 2);
        networkMap.addLink("C", "D", 1);
        networkMap.addLink("B", "D", 6);

        System.out.println("Router A runs Dijkstra's on its complete map:");
        Map<String, Integer> shortestPathsFromA = networkMap.dijkstra("A");
        for (Map.Entry<String, Integer> entry : shortestPathsFromA.entrySet()) {
            System.out.println("Cost to " + entry.getKey() + " is " + entry.getValue());
        }
    }
}
