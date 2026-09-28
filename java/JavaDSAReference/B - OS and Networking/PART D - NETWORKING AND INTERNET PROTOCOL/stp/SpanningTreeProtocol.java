package net.stp;

import java.util.*;

public class SpanningTreeProtocol {
    // Spanning Tree Protocol (STP) prevents broadcast loops in Ethernet LANs by finding a loop-free logical topology.
    // While Prim's and Kruskal's MST algorithms minimize total edge cost in a graph, STP creates a logical tree 
    // spanning all switches (bridges) with a designated root bridge to prevent infinite packet looping, adapting 
    // paths based on port/path costs to the root rather than total network cost minimization.

    static class Switch {
        int id;
        int rootId;
        int costToRoot;
        int nextHopToRoot;
        
        public Switch(int id) {
            this.id = id;
            this.rootId = id; // Initially, every switch thinks it's the root
            this.costToRoot = 0;
            this.nextHopToRoot = -1;
        }
    }
    
    static class Link {
        int from, to, cost;
        public Link(int from, int to, int cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }
    }
    
    public static void runSTP(List<Switch> switches, List<Link> links) {
        boolean changed = true;
        
        while (changed) {
            changed = false;
            
            for (Link link : links) {
                Switch s1 = getSwitch(switches, link.from);
                Switch s2 = getSwitch(switches, link.to);
                
                // s1 sends BPDU to s2
                if (s1.rootId < s2.rootId || (s1.rootId == s2.rootId && s1.costToRoot + link.cost < s2.costToRoot)) {
                    s2.rootId = s1.rootId;
                    s2.costToRoot = s1.costToRoot + link.cost;
                    s2.nextHopToRoot = s1.id;
                    changed = true;
                }
                
                // s2 sends BPDU to s1
                if (s2.rootId < s1.rootId || (s2.rootId == s1.rootId && s2.costToRoot + link.cost < s1.costToRoot)) {
                    s1.rootId = s2.rootId;
                    s1.costToRoot = s2.costToRoot + link.cost;
                    s1.nextHopToRoot = s2.id;
                    changed = true;
                }
            }
        }
    }
    
    private static Switch getSwitch(List<Switch> switches, int id) {
        for (Switch s : switches) {
            if (s.id == id) return s;
        }
        return null;
    }
}
