package net.routing.loopprevention;

import java.util.HashMap;
import java.util.Map;

/**
 * Poison Reverse Technique
 * Prevents loops by advertising a route back onto the interface it was learned from 
 * with a metric of INFINITY, explicitly "poisoning" the route to ensure it's not used.
 */
public class PoisonReverse {

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

        // Generates an advertisement for a specific neighbor (Poison Reverse applied)
        public Map<String, Integer> generateAdvertisementFor(Router neighbor) {
            Map<String, Integer> ad = new HashMap<>();
            for (Map.Entry<String, Integer> entry : routingTable.entrySet()) {
                String dest = entry.getKey();
                // Poison Reverse: If the route goes through this neighbor, advertise it as INFINITY
                if (neighbor.name.equals(nextHop.get(dest))) {
                    ad.put(dest, INFINITY);
                } else {
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
                int newCost = Math.min(neighborCost == INFINITY ? INFINITY : neighborCost + linkCost, INFINITY);

                if (nextHop.getOrDefault(dest, "").equals(neighbor.name) || newCost < currentCost) {
                    routingTable.put(dest, newCost);
                    if (newCost == INFINITY) {
                        nextHop.put(dest, ""); // Route poisoned, remove next hop
                    } else {
                        nextHop.put(dest, neighbor.name);
                    }
                }
            }
        }
    }

    public static void simulate() {
        Router a = new Router("A");
        Router b = new Router("B");

        a.setRoute("X", 2, "B");
        b.setRoute("X", 1, "X");

        System.out.println("Initial State (Poison Reverse):");
        System.out.println("A -> X: " + a.routingTable.get("X"));
        System.out.println("B -> X: " + b.routingTable.get("X"));

        System.out.println("\nLink B-X fails! B updates its cost to X to INFINITY.");
        b.setRoute("X", INFINITY, "");

        System.out.println("B asks A for its routes. A routes to X via B, so A advertises X to B with cost INFINITY.");
        b.updateFrom(a, 1);
        
        System.out.println("B -> X after update from A: " + b.routingTable.get("X") + " (Loop prevented!)");
        
        a.updateFrom(b, 1);
        System.out.println("A -> X after update from B: " + a.routingTable.get("X"));
    }
}
