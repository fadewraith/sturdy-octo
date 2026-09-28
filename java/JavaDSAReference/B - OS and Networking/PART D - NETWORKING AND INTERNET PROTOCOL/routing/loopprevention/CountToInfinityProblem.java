package net.routing.loopprevention;

import java.util.HashMap;
import java.util.Map;

/**
 * Count-to-Infinity Problem
 * Demonstrates the routing loop issue in distance vector routing when a link fails.
 */
public class CountToInfinityProblem {

    public static final int INFINITY = 16; // Typically 16 in RIP

    static class Router {
        String name;
        Map<String, Integer> routingTable = new HashMap<>();
        Map<String, String> nextHop = new HashMap<>();

        public Router(String name) {
            this.name = name;
            routingTable.put(name, 0);
            nextHop.put(name, name);
        }

        public void setRoute(String dest, int cost, String nh) {
            routingTable.put(dest, cost);
            nextHop.put(dest, nh);
        }

        public void updateFrom(Router neighbor, int linkCost) {
            for (Map.Entry<String, Integer> entry : neighbor.routingTable.entrySet()) {
                String dest = entry.getKey();
                int neighborCost = entry.getValue();

                int currentCost = routingTable.getOrDefault(dest, INFINITY);
                int newCost = Math.min(neighborCost + linkCost, INFINITY);

                // In Count-to-Infinity, a router might adopt a worse path if it thinks 
                // the neighbor has a valid alternative, creating a loop.
                if (nextHop.getOrDefault(dest, "").equals(neighbor.name) || newCost < currentCost) {
                    routingTable.put(dest, newCost);
                    nextHop.put(dest, neighbor.name);
                }
            }
        }
    }

    public static void simulate() {
        Router a = new Router("A");
        Router b = new Router("B");

        // Initial stable state (Targeting some destination X behind B)
        a.setRoute("X", 2, "B");
        b.setRoute("X", 1, "X");

        System.out.println("Initial State:");
        System.out.println("A -> X: " + a.routingTable.get("X"));
        System.out.println("B -> X: " + b.routingTable.get("X"));

        System.out.println("\nLink B-X fails! B updates its cost to X to INFINITY.");
        b.setRoute("X", INFINITY, "");

        // Without split horizon, A thinks it can reach X via B in 2, but B now thinks 
        // it can reach X via A since A advertises a cost of 2.
        
        int iteration = 1;
        while (a.routingTable.get("X") < INFINITY || b.routingTable.get("X") < INFINITY) {
            System.out.println("Iteration " + iteration + ":");
            b.updateFrom(a, 1); // B updates from A
            a.updateFrom(b, 1); // A updates from B

            System.out.println("A -> X: " + a.routingTable.get("X") + " (via " + a.nextHop.get("X") + ")");
            System.out.println("B -> X: " + b.routingTable.get("X") + " (via " + b.nextHop.get("X") + ")");
            
            if (iteration > 20) break; // Safety break
            iteration++;
        }
        System.out.println("Counted to Infinity!");
    }
}
