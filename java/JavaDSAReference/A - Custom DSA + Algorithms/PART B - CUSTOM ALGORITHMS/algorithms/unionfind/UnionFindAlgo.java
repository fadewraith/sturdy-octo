package algorithms.unionfind;

/**
 * UNION-FIND / DISJOINT SET
 * 
 * WHAT IT IS:
 * A data structure that tracks a set of elements partitioned into a number of 
 * disjoint (non-overlapping) subsets. 
 * 
 * WHEN TO USE THIS:
 * - Cycle detection in undirected graphs.
 * - Kruskal's Minimum Spanning Tree algorithm.
 * - Dynamic connectivity problems (e.g., Network percolation, "Are these two users connected?").
 * 
 * CORE OPERATIONS & OPTIMIZATIONS:
 * 1. Find with PATH COMPRESSION:
 *    When finding the root of a node, we make ALL nodes along the path point directly 
 *    to the root. This flattens the tree, making future `find` calls take O(1) time!
 * 2. Union by RANK (or Size):
 *    When merging two sets, always attach the smaller/shorter tree under the root of 
 *    the taller/larger tree. This keeps the tree as flat as possible.
 * 
 * COMPLEXITY:
 * Time: O(Inverse-Ackermann(N)) amortized per operation. For all practical purposes in 
 *       the universe, this evaluates to strictly O(1) constant time!
 * Space: O(N) to store the parent and rank arrays.
 */
public class UnionFindAlgo {

    private int[] parent;
    private int[] rank;

    public UnionFindAlgo(int size) {
        parent = new int[size];
        rank = new int[size];
        
        for (int i = 0; i < size; i++) {
            parent[i] = i; // Every node is its own parent initially
            rank[i] = 1;   // Initial rank is 1
        }
    }

    /**
     * Find with Path Compression
     */
    public int find(int i) {
        // If i is not its own parent, we recursively call find on its parent
        if (parent[i] != i) {
            // Path Compression: reassign the parent directly to the ultimate root
            parent[i] = find(parent[i]); 
        }
        return parent[i];
    }

    /**
     * Union by Rank
     */
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX != rootY) {
            // Attach the smaller rank tree under the root of the larger rank tree
            if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else {
                // If ranks are identical, attach one to the other and increment the rank
                parent[rootY] = rootX;
                rank[rootX]++;
            }
            System.out.println("Merged set containing " + y + " into set containing " + x);
        } else {
            System.out.println("Elements " + x + " and " + y + " are ALREADY in the same set!");
        }
    }
    
    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }

    public static void main(String[] args) {
        System.out.println("--- UNION-FIND / DISJOINT SET DEMO ---");
        
        UnionFindAlgo uf = new UnionFindAlgo(5); // Elements 0 to 4
        
        System.out.println("Are 0 and 2 connected? " + uf.connected(0, 2)); // false
        
        uf.union(0, 1);
        uf.union(1, 2);
        
        System.out.println("Are 0 and 2 connected now? " + uf.connected(0, 2)); // true
        
        // Attempting to merge elements already in the same set (Cycle detection!)
        uf.union(0, 2); 
    }
}
