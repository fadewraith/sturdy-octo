package unionfind;

import array.MyArrayList;
import hashmap.chaining.MyHashMap;

/**
 * DISJOINT SET (UNION-FIND), GENERIC
 *
 * What it is:
 * Keeps track of elements split into non-overlapping groups, and answers
 * "are a and b in the same group?" almost instantly, while groups keep MERGING.
 * Works with ANY element type (strings, points, accounts...), not just 0..n-1 ints -
 * Part B's UnionFindAlgo is the int-array version.
 *
 * Approach/Strategy:
 * Each group is a tree; its ROOT is the group's representative.
 *   find(x)     : follow parent links up to the root.
 *   union(a, b) : point one root at the other.
 * Two optimizations make it fast:
 *   1. UNION BY SIZE: hang the SMALLER tree under the bigger one, so trees stay shallow.
 *   2. PATH COMPRESSION: after find(x), point every node on the path straight at the root,
 *      so the next find is one step.
 * Together: amortized O(alpha(N)) per operation - alpha is the inverse Ackermann function,
 * which is below 5 for any N that fits in the universe. In practice: constant time.
 *
 * Time/Space Complexity:
 * Operation            | Time
 * -------------------------------------------
 * makeSet              | O(1)
 * find/union/connected | O(alpha(N)) amortized (~ O(1))
 * setCount             | O(1)
 * setSize              | O(alpha(N))
 * groups()             | O(N)
 * Space                | O(N)
 *
 * Used for: Kruskal's MST, cycle detection in undirected graphs, "number of islands /
 * provinces", merging accounts (LeetCode 721), connected components in a stream of edges.
 */
public class DisjointSet<T> {

    private final MyHashMap<T, T> parent = new MyHashMap<>();
    private final MyHashMap<T, Integer> size = new MyHashMap<>(); // only meaningful for roots
    private int setCount = 0;

    /** Adds x as its own one-element group (does nothing if x is already known). */
    public void makeSet(T x) {
        if (parent.containsKey(x)) return;
        parent.put(x, x);
        size.put(x, 1);
        setCount++;
    }

    public boolean contains(T x) {
        return parent.containsKey(x);
    }

    /**
     * Representative (root) of x's group. Unknown elements are added automatically.
     * Iterative two-pass path compression (no recursion = no stack overflow on long chains).
     */
    public T find(T x) {
        makeSet(x);
        T root = x;
        while (!same(parent.get(root), root)) root = parent.get(root);   // pass 1: find root
        T cur = x;
        while (!same(cur, root)) {                                          // pass 2: compress
            T next = parent.get(cur);
            parent.put(cur, root);
            cur = next;
        }
        return root;
    }

    /** Merges the groups of a and b. Returns false if they were already together. */
    public boolean union(T a, T b) {
        T ra = find(a), rb = find(b);
        if (same(ra, rb)) return false;
        int sa = size.get(ra), sb = size.get(rb);
        if (sa < sb) { T tmp = ra; ra = rb; rb = tmp; } // ra = bigger tree
        parent.put(rb, ra);                               // smaller under bigger
        size.put(ra, sa + sb);
        size.remove(rb);
        setCount--;
        return true;
    }

    public boolean connected(T a, T b) {
        return same(find(a), find(b));
    }

    /** Number of separate groups. */
    public int setCount() {
        return setCount;
    }

    /** Number of elements in x's group. */
    public int setSize(T x) {
        return size.get(find(x));
    }

    /** Total number of elements across all groups. */
    public int elementCount() {
        return parent.size();
    }

    /** All groups, each as a list of its elements. */
    public MyArrayList<MyArrayList<T>> groups() {
        MyHashMap<T, MyArrayList<T>> byRoot = new MyHashMap<>();
        MyArrayList<T> elements = parent.keySet();
        for (int i = 0; i < elements.size(); i++) {
            T e = elements.get(i);
            T root = find(e);
            MyArrayList<T> group = byRoot.get(root);
            if (group == null) {
                group = new MyArrayList<>();
                byRoot.put(root, group);
            }
            group.add(e);
        }
        return byRoot.values();
    }

    private static boolean same(Object a, Object b) {
        return a == null ? b == null : a.equals(b);
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- GENERIC DISJOINT SET (UNION-FIND) DEMO ---");
        DisjointSet<String> friends = new DisjointSet<>();
        String[][] friendships = {{"ann", "bob"}, {"bob", "cat"}, {"dan", "eve"}, {"fay", "fay"}};
        for (String[] f : friendships) friends.union(f[0], f[1]);
        friends.makeSet("gus"); // a loner

        System.out.println("ann & cat in the same circle? " + friends.connected("ann", "cat")); // true
        System.out.println("ann & eve in the same circle? " + friends.connected("ann", "eve")); // false
        System.out.println("Friend circles: " + friends.setCount());                           // 4
        System.out.println("Size of ann's circle: " + friends.setSize("ann"));                 // 3
        System.out.println("Groups: " + friends.groups());

        // Cycle detection in an undirected graph: an edge whose ends are already connected closes a cycle
        DisjointSet<Integer> graph = new DisjointSet<>();
        int[][] edges = {{1, 2}, {2, 3}, {3, 4}, {4, 2}};
        for (int[] e : edges) {
            if (!graph.union(e[0], e[1])) System.out.println("Edge " + e[0] + "-" + e[1] + " creates a cycle!");
        }
    }
}
