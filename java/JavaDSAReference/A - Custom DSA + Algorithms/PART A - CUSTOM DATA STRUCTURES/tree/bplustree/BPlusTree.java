package tree.bplustree;

/**
 * B+ TREE (Simplified / Educational)
 * 
 * WHAT IT IS:
 * An advanced, self-balancing tree data structure. 
 * CRITICAL DIFFERENCE FROM B-TREE: 
 * In a B+ tree, ALL data values are stored strictly in the leaf nodes. 
 * Internal nodes only store keys used for routing/navigation.
 * Additionally, leaf nodes are linked together in a linked list for blazing fast 
 * range scans!
 */
public class BPlusTree<K extends Comparable<K>, V> {
    
    class Node {
        boolean isLeaf;
        Object[] keys;
        Node[] children; 
        Object[] values; 
        Node next;       
        int numKeys;

        Node(boolean isLeaf, int maxDegree) {
            this.isLeaf = isLeaf;
            this.keys = new Object[maxDegree - 1];
            
            if (isLeaf) {
                this.values = new Object[maxDegree - 1];
            } else {
                this.children = (Node[]) java.lang.reflect.Array.newInstance(Node.class, maxDegree);
            }
        }
    }

    private Node root;
    private int maxDegree;

    public BPlusTree(int degree) {
        this.maxDegree = degree;
        this.root = new Node(true, degree);
    }

    @SuppressWarnings("unchecked")
    public void rangeScan(K startKey, K endKey) {
        Node curr = root;
        
        while (!curr.isLeaf) {
            int i = 0;
            while (i < curr.numKeys && startKey.compareTo((K) curr.keys[i]) >= 0) {
                i++;
            }
            curr = curr.children[i];
        }
        
        System.out.println("Range Scan Results:");
        while (curr != null) {
            for (int i = 0; i < curr.numKeys; i++) {
                K currentKey = (K) curr.keys[i];
                if (currentKey.compareTo(startKey) >= 0 && currentKey.compareTo(endKey) <= 0) {
                    System.out.println("  Key: " + currentKey + ", Value: " + (V) curr.values[i]);
                }
                if (currentKey.compareTo(endKey) > 0) {
                    return; 
                }
            }
            curr = curr.next; 
        }
    }

    public static void main(String[] args) {
        System.out.println("--- GENERIC B+ TREE (FROM SCRATCH) DEMO ---");
        System.out.println("This is a generic structural demonstration.");
    }
}
