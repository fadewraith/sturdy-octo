package db.bplustree;

import java.util.*;

/**
 * WHAT IT IS:
 * A B+ Tree is a self-balancing tree data structure that maintains sorted data in a way that allows for searches, sequential access, insertions, and deletions in logarithmic time. In a B+ tree, unlike a B-tree, all data records are stored at the leaf level of the tree; only keys are stored in interior nodes. Leaf nodes are often linked to one another in a linked list for rapid in-order traversal (range scans).
 * 
 * STRATEGY:
 * - Insert: Find the correct leaf node. If it has space, insert. If it overflows, split the leaf node, moving the median key to the parent.
 * - Search: Traverse internal nodes down to the correct leaf node.
 * - Range Query: Find the starting leaf node, then follow the next pointers of leaf nodes.
 * 
 * TIME/SPACE COMPLEXITY:
 * - Search/Insert/Delete: O(log_b N) where b is the branching factor.
 * - Range Query: O(log_b N + K) where K is the number of elements in the range.
 * - Space: O(N)
 * 
 * REAL-WORLD ANALOGY / USE CASE:
 * Used extensively in databases (like MySQL InnoDB) and file systems (like NTFS, XFS) to store indices and data pages.
 * 
 * WHEN TO USE / COMBINATION:
 * Use when you need fast point lookups and fast range scans on disk-based storage where minimizing block reads is critical.
 * 
 * PSEUDOCODE:
 * search(k):
 *   node = root
 *   while not node.is_leaf:
 *     find i such that k < node.keys[i]
 *     node = node.children[i]
 *   return node.values[find(k)]
 */
public class BPlusTree {

    private int m; // Max degree
    private Node root;

    abstract class Node {
        List<Integer> keys;
        Node parent;
        Node() {
            keys = new ArrayList<>();
        }
    }

    class InternalNode extends Node {
        List<Node> children;
        InternalNode() {
            super();
            children = new ArrayList<>();
        }
    }

    class LeafNode extends Node {
        List<String> values;
        LeafNode next;
        LeafNode() {
            super();
            values = new ArrayList<>();
        }
    }

    public BPlusTree(int m) {
        this.m = m;
        this.root = new LeafNode();
    }

    public void insert(int key, String value) {
        LeafNode leaf = findLeafNode(key);
        int idx = Collections.binarySearch(leaf.keys, key);
        if (idx < 0) idx = -idx - 1;
        leaf.keys.add(idx, key);
        leaf.values.add(idx, value);

        if (leaf.keys.size() == m) {
            splitLeafNode(leaf);
        }
    }

    private void splitLeafNode(LeafNode leaf) {
        int mid = leaf.keys.size() / 2;
        LeafNode newLeaf = new LeafNode();

        newLeaf.keys.addAll(leaf.keys.subList(mid, leaf.keys.size()));
        newLeaf.values.addAll(leaf.values.subList(mid, leaf.values.size()));
        
        leaf.keys.subList(mid, leaf.keys.size()).clear();
        leaf.values.subList(mid, leaf.values.size()).clear();
        
        newLeaf.next = leaf.next;
        leaf.next = newLeaf;
        
        insertIntoParent(leaf, newLeaf.keys.get(0), newLeaf);
    }

    private void insertIntoParent(Node left, int key, Node right) {
        if (left.parent == null) {
            InternalNode newRoot = new InternalNode();
            newRoot.keys.add(key);
            newRoot.children.add(left);
            newRoot.children.add(right);
            left.parent = newRoot;
            right.parent = newRoot;
            root = newRoot;
            return;
        }

        InternalNode parent = (InternalNode) left.parent;
        int idx = Collections.binarySearch(parent.keys, key);
        if (idx < 0) idx = -idx - 1;
        parent.keys.add(idx, key);
        parent.children.add(idx + 1, right);
        right.parent = parent;

        if (parent.keys.size() == m) {
            splitInternalNode(parent);
        }
    }

    private void splitInternalNode(InternalNode node) {
        int mid = node.keys.size() / 2;
        InternalNode newNode = new InternalNode();
        
        int upKey = node.keys.get(mid);
        
        newNode.keys.addAll(node.keys.subList(mid + 1, node.keys.size()));
        newNode.children.addAll(node.children.subList(mid + 1, node.children.size()));
        
        for (Node child : newNode.children) {
            child.parent = newNode;
        }
        
        node.keys.subList(mid, node.keys.size()).clear();
        node.children.subList(mid + 1, node.children.size()).clear();
        
        insertIntoParent(node, upKey, newNode);
    }

    private LeafNode findLeafNode(int key) {
        Node curr = root;
        while (curr instanceof BPlusTree.InternalNode) {
            InternalNode internal = (InternalNode) curr;
            int idx = 0;
            while (idx < internal.keys.size() && key >= internal.keys.get(idx)) {
                idx++;
            }
            curr = internal.children.get(idx);
        }
        return (LeafNode) curr;
    }

    public String search(int key) {
        LeafNode leaf = findLeafNode(key);
        int idx = Collections.binarySearch(leaf.keys, key);
        if (idx >= 0) {
            return leaf.values.get(idx);
        }
        return null;
    }

    public List<String> rangeQuery(int startKey, int endKey) {
        List<String> result = new ArrayList<>();
        LeafNode leaf = findLeafNode(startKey);
        
        while (leaf != null) {
            for (int i = 0; i < leaf.keys.size(); i++) {
                if (leaf.keys.get(i) >= startKey && leaf.keys.get(i) <= endKey) {
                    result.add(leaf.values.get(i));
                }
                if (leaf.keys.get(i) > endKey) {
                    return result;
                }
            }
            leaf = leaf.next;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println("=== B+ Tree Tests ===");
        BPlusTree bpt = new BPlusTree(3);
        
        bpt.insert(10, "A");
        bpt.insert(20, "B");
        bpt.insert(5, "C");
        bpt.insert(6, "D");
        bpt.insert(12, "E");
        bpt.insert(30, "F");
        bpt.insert(7, "G");
        bpt.insert(17, "H");
        
        System.out.println("Search 12: " + bpt.search(12)); // E
        System.out.println("Search 15: " + bpt.search(15)); // null
        
        System.out.println("Range 6 to 17: " + bpt.rangeQuery(6, 17)); // [D, G, A, E, H]
    }
}
