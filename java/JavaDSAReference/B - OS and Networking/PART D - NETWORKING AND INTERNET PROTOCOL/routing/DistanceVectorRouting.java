package net.routing;

import java.util.HashMap;
import java.util.Map;

/**
 * Distance Vector Routing
 * Cross-reference: Uses Bellman-Ford algorithm applied to routing tables.
 * Used by routing protocols like RIP (Routing Information Protocol).
 * 
 * Comparison: Distance Vector = "routing by rumor" vs Link State = "routing by map".
 */
public class DistanceVectorRouting {

    public static final int INFINITY = 9999; // Represent infinity

    static class Router {
        String name;
        Map<String, Integer> routingTable = new HashMap<>(); // Dest -> Cost
        Map<String, String> nextHop = new HashMap<>();       // Dest -> Next Hop Name
        Map<Router, Integer> neighbors = new HashMap<>();    // Neighbor -> Link Cost

        public Router(String name) {
            this.name = name;
            routingTable.put(name, 0);
            nextHop.put(name, name);
        }

        public void addNeighbor(Router neighbor, int cost) {
            neighbors.put(neighbor, cost);
            routingTable.put(neighbor.name, cost);
            nextHop.put(neighbor.name, neighbor.name);
        }

        // Simulates receiving a distance vector from a neighbor and applying Bellman-Ford equation
        public boolean updateRoutingTable(Router neighbor) {
            boolean updated = false;
            int costToNeighbor = neighbors.getOrDefault(neighbor, INFINITY);

            for (Map.Entry<String, Integer> entry : neighbor.routingTable.entrySet()) {
                String dest = entry.getKey();
                int neighborCostToDest = entry.getValue();

                if (neighborCostToDest == INFINITY) continue;

                int newCost = costToNeighbor + neighborCostToDest;
                int currentCost = routingTable.getOrDefault(dest, INFINITY);

                if (newCost < currentCost) {
                    routingTable.put(dest, newCost);
                    nextHop.put(dest, neighbor.name);
                    updated = true;
                }
            }
            return updated;
        }
    }

    public static void simulate() {
        Router a = new Router("A");
        Router b = new Router("B");
        Router c = new Router("C");

        a.addNeighbor(b, 1);
        a.addNeighbor(c, 5);
        
        b.addNeighbor(a, 1);
        b.addNeighbor(c, 2);

        c.addNeighbor(a, 5);
        c.addNeighbor(b, 2);

        boolean networkChanged;
        int maxIterations = 10;
        int iter = 0;

        do {
            networkChanged = false;
            // In a real network, routers exchange tables concurrently.
            // Simulating sequential exchanges here for the Bellman-Ford convergence.
            networkChanged |= a.updateRoutingTable(b);
            networkChanged |= a.updateRoutingTable(c);
            networkChanged |= b.updateRoutingTable(a);
            networkChanged |= b.updateRoutingTable(c);
            networkChanged |= c.updateRoutingTable(a);
            networkChanged |= c.updateRoutingTable(b);
            iter++;
        } while (networkChanged && iter < maxIterations);

        System.out.println("Distance Vector converged in " + iter + " iterations.");
        System.out.println("A's Routing Table: " + a.routingTable);
    }
}
