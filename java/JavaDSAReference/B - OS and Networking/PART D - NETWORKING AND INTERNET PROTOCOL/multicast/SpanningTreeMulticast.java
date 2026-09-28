package net.multicast;

import java.util.ArrayList;
import java.util.List;

public class SpanningTreeMulticast {
    
    static class TreeNode {
        int id;
        List<TreeNode> treeLinks = new ArrayList<>();

        TreeNode(int id) {
            this.id = id;
        }

        // Only add edges that are part of the spanning tree (no cycles)
        void addTreeEdge(TreeNode n) {
            treeLinks.add(n);
            n.treeLinks.add(this);
        }

        void multicast(String msgId, String payload, TreeNode sender) {
            System.out.println("TreeNode " + id + " received: " + payload);
            
            // Forward along all spanning tree links except the incoming one
            for (TreeNode neighbor : treeLinks) {
                if (neighbor != sender) {
                    System.out.println("TreeNode " + id + " forwarding to TreeNode " + neighbor.id);
                    neighbor.multicast(msgId, payload, this);
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Spanning-Tree-based Multicast Simulation ---");
        // Assumes a Spanning Tree has already been formed (cross-reference net.stp)
        TreeNode root = new TreeNode(1);
        TreeNode n2 = new TreeNode(2);
        TreeNode n3 = new TreeNode(3);
        TreeNode n4 = new TreeNode(4);
        TreeNode n5 = new TreeNode(5);
        
        // Form a strictly cycle-free tree
        root.addTreeEdge(n2);
        root.addTreeEdge(n3);
        n2.addTreeEdge(n4);
        n2.addTreeEdge(n5);
        
        System.out.println("Starting multicast from Root (Node 1)...");
        // Notice we do NOT need a seenMessages set because trees inherently have no cycles
        root.multicast("msg-stp-01", "Hello via Spanning Tree!", null);
    }
}
