package net.routing.loopprevention;

import java.util.HashMap;
import java.util.Map;

/**
 * Split Horizon Technique
 * Prevents loops by preventing a router from advertising a route back onto the interface 
 * from which it was learned.
 */
public class SplitHorizon {

    public static final int INFINITY = 16; 

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

        // Generates an advertisement for a specific neighbor (Split Horizon applied)
        public Map<String, Integer> generateAdvertisementFor(Router neighbor) {
            Map<String, Integer> ad = new HashMap<>();
            for (Map.Entry<String, Integer> entry : routingTable.entrySet()) {
                String dest = entry.getKey();
                // Split Horizon: Do not advertise routes back to the next hop they were learned from
                if (!neighbor.name.equals(nextHop.get(dest))) {
                    ad.put(dest, entry.getValue());
                }
            }
            return ad;
        }

        public void updateFrom(Router neighbor, int linkCost) {
            Map<String, Integer> ad = neighbor.generateAdvertisementFor(this);
            for (Map.Entry<String, Integer> entry : ad.entrySet()) {
                String dest = entry.getKey();
                int neighborCost = entry.getValue();

                int currentCost = routingTable.getOrDefault(dest, INFINITY);
                int newCost = Math.min(neighborCost + linkCost, INFINITY);

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

        a.setRoute("X", 2, "B");
        b.setRoute("X", 1, "X");

        System.out.println("Initial State (Split Horizon):");
        System.out.println("A -> X: " + a.routingTable.get("X"));
        System.out.println("B -> X: " + b.routingTable.get("X"));

        System.out.println("\nLink B-X fails! B updates its cost to X to INFINITY.");
        b.setRoute("X", INFINITY, "");

        System.out.println("B asks A for its routes. Since A routes to X via B, A does NOT advertise X to B.");
        b.updateFrom(a, 1);
        
        System.out.println("B -> X after update from A: " + b.routingTable.get("X") + " (Loop prevented!)");
        
        a.updateFrom(b, 1);
        System.out.println("A -> X after update from B: " + a.routingTable.get("X"));
    }
}
