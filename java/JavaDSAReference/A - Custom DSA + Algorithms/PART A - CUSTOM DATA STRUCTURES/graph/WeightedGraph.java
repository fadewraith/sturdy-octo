package graph;

import array.MyArrayList;
import hashmap.chaining.MyHashMap;
import priorityqueue.indexed.IndexedMinPQ;

/**
 * WEIGHTED GRAPH (generic vertices, double edge weights)
 *
 * What it is:
 * Graph.java models connections only. Real problems usually have a COST per edge:
 * road length, flight price, network latency. This class stores weighted edges and runs
 * the classic weighted algorithms on ANY vertex type (city names, coordinates, ids...).
 * (Part B's Dijkstra/BellmanFord/Prims work on int adjacency matrices; these work on the
 * generic graph and use our IndexedMinPQ for the efficient decrease-key versions.)
 *
 * Approach/Strategy:
 * Adjacency list: MyHashMap<vertex, MyArrayList<Edge>>. For the algorithms, vertices are
 * numbered 0..V-1 so we can use plain arrays (dist[], prev[]) and the IndexedMinPQ.
 *
 * Algorithms:
 * - dijkstra(source) / shortestPath(s, t): single-source shortest paths, NON-negative
 *   weights only. With decrease-key the queue never holds more than V entries.
 * - bellmanFord(source): allows NEGATIVE weights; relaxes every edge V-1 times; one more
 *   successful relaxation means a negative cycle (no shortest path exists).
 * - minimumSpanningTree(): Prim's algorithm (undirected graphs). Same shape as Dijkstra,
 *   but a vertex's key is the cheapest single EDGE into the tree, not a path length.
 *
 * Time/Space Complexity:
 * Operation                      | Time
 * ---------------------------------------------------
 * addEdge / getWeight            | O(degree) (edges kept in a list)
 * dijkstra / shortestPath        | O((V + E) log V)
 * bellmanFord                    | O(V * E)
 * minimumSpanningTree (Prim)     | O((V + E) log V)
 * Space                          | O(V + E)
 */
public class WeightedGraph<T> {

    /** A directed edge to 'to' with a weight (undirected edges are stored both ways). */
    public static class Edge<T> {
        private final T from, to;
        private double weight;

        Edge(T from, T to, double weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public T getFrom() {
            return from;
        }

        public T getTo() {
            return to;
        }

        public double getWeight() {
            return weight;
        }

        @Override
        public String toString() {
            return from + "-" + to + "(" + weight + ")";
        }
    }

    /** A path and its total cost (empty path + infinite cost if unreachable). */
    public static class PathResult<T> {
        private final MyArrayList<T> path;
        private final double cost;

        PathResult(MyArrayList<T> path, double cost) {
            this.path = path;
            this.cost = cost;
        }

        public MyArrayList<T> getPath() {
            return path;
        }

        public double getCost() {
            return cost;
        }

        public boolean isReachable() {
            return !path.isEmpty();
        }

        @Override
        public String toString() {
            return isReachable() ? path + " cost " + cost : "unreachable";
        }
    }

    private final MyHashMap<T, MyArrayList<Edge<T>>> adj = new MyHashMap<>();
    private final boolean directed;
    private int edgeCount = 0;

    public WeightedGraph(boolean directed) {
        this.directed = directed;
    }

    public boolean isDirected() {
        return directed;
    }

    // ---------------- building the graph ----------------

    public void addVertex(T v) {
        if (!adj.containsKey(v)) adj.put(v, new MyArrayList<>());
    }

    private Edge<T> findEdge(T from, T to) {
        MyArrayList<Edge<T>> edges = adj.get(from);
        if (edges == null) return null;
        for (int i = 0; i < edges.size(); i++) {
            Edge<T> e = edges.get(i);
            if (e.to == null ? to == null : e.to.equals(to)) return e;
        }
        return null;
    }

    /** Adds the edge, or updates its weight if it already exists (simple graph: one edge per pair). */
    public void addEdge(T from, T to, double weight) {
        if (Double.isNaN(weight)) throw new IllegalArgumentException("weight must be a number");
        addVertex(from);
        addVertex(to);
        Edge<T> existing = findEdge(from, to);
        if (existing != null) {
            existing.weight = weight;
            if (!directed) findEdge(to, from).weight = weight;
            return;
        }
        adj.get(from).add(new Edge<>(from, to, weight));
        if (!directed && !same(from, to)) adj.get(to).add(new Edge<>(to, from, weight));
        edgeCount++;
    }

    public boolean removeEdge(T from, T to) {
        Edge<T> e = findEdge(from, to);
        if (e == null) return false;
        adj.get(from).remove(e);
        if (!directed && !same(from, to)) adj.get(to).remove(findEdge(to, from));
        edgeCount--;
        return true;
    }

    public boolean hasEdge(T from, T to) {
        return findEdge(from, to) != null;
    }

    /** Weight of the edge, or null if there is none. */
    public Double getWeight(T from, T to) {
        Edge<T> e = findEdge(from, to);
        return e == null ? null : e.weight;
    }

    /** Copy of the outgoing edges of v. */
    public MyArrayList<Edge<T>> getEdges(T v) {
        MyArrayList<Edge<T>> copy = new MyArrayList<>();
        if (adj.containsKey(v)) copy.addAll(adj.get(v));
        return copy;
    }

    public MyArrayList<T> getVertices() {
        return adj.keySet();
    }

    public int vertexCount() {
        return adj.size();
    }

    /** Number of edges (an undirected edge counts once). */
    public int edgeCount() {
        return edgeCount;
    }

    private static boolean same(Object a, Object b) {
        return a == null ? b == null : a.equals(b);
    }

    // ---------------- numbering vertices 0..V-1 for array-based algorithms ----------------

    private MyArrayList<T> vertexList;
    private MyHashMap<T, Integer> idOf;

    private void index() {
        vertexList = adj.keySet();
        idOf = new MyHashMap<>();
        for (int i = 0; i < vertexList.size(); i++) idOf.put(vertexList.get(i), i);
    }

    // ---------------- Dijkstra ----------------

    // Runs Dijkstra from source; fills dist[] and prev[] (prev = -1 for none)
    private void dijkstraArrays(T source, double[] dist, int[] prev) {
        int n = vertexList.size();
        for (int i = 0; i < n; i++) {
            dist[i] = Double.POSITIVE_INFINITY;
            prev[i] = -1;
        }
        int s = idOf.get(source);
        dist[s] = 0;
        IndexedMinPQ<Double> pq = new IndexedMinPQ<>(n);
        pq.insert(s, 0.0);
        while (!pq.isEmpty()) {
            int u = pq.delMin(); // closest unfinished vertex: its distance is now final
            MyArrayList<Edge<T>> edges = adj.get(vertexList.get(u));
            for (int i = 0; i < edges.size(); i++) {
                Edge<T> e = edges.get(i);
                int v = idOf.get(e.to);
                double candidate = dist[u] + e.weight;
                if (candidate < dist[v]) {   // relax
                    dist[v] = candidate;
                    prev[v] = u;
                    if (pq.contains(v)) pq.decreaseKey(v, candidate); // update in place
                    else pq.insert(v, candidate);
                }
            }
        }
    }

    private void requireNonNegativeWeights() {
        MyArrayList<T> vs = adj.keySet();
        for (int i = 0; i < vs.size(); i++) {
            MyArrayList<Edge<T>> edges = adj.get(vs.get(i));
            for (int j = 0; j < edges.size(); j++) {
                if (edges.get(j).weight < 0) {
                    throw new IllegalStateException("Dijkstra needs non-negative weights (found " + edges.get(j)
                            + ") - use bellmanFord instead");
                }
            }
        }
    }

    /** Shortest distance from source to every REACHABLE vertex (non-negative weights). */
    public MyHashMap<T, Double> dijkstra(T source) {
        if (!adj.containsKey(source)) throw new IllegalArgumentException("unknown vertex: " + source);
        requireNonNegativeWeights();
        index();
        int n = vertexList.size();
        double[] dist = new double[n];
        int[] prev = new int[n];
        dijkstraArrays(source, dist, prev);
        MyHashMap<T, Double> result = new MyHashMap<>();
        for (int i = 0; i < n; i++) {
            if (dist[i] != Double.POSITIVE_INFINITY) result.put(vertexList.get(i), dist[i]);
        }
        return result;
    }

    /** Cheapest path from source to target (non-negative weights). */
    public PathResult<T> shortestPath(T source, T target) {
        if (!adj.containsKey(source) || !adj.containsKey(target)) {
            return new PathResult<>(new MyArrayList<>(), Double.POSITIVE_INFINITY);
        }
        requireNonNegativeWeights();
        index();
        int n = vertexList.size();
        double[] dist = new double[n];
        int[] prev = new int[n];
        dijkstraArrays(source, dist, prev);
        int t = idOf.get(target);
        if (dist[t] == Double.POSITIVE_INFINITY) return new PathResult<>(new MyArrayList<>(), Double.POSITIVE_INFINITY);

        MyArrayList<T> reversed = new MyArrayList<>();
        for (int v = t; v != -1; v = prev[v]) reversed.add(vertexList.get(v)); // follow prev links back
        MyArrayList<T> path = new MyArrayList<>();
        for (int i = reversed.size() - 1; i >= 0; i--) path.add(reversed.get(i));
        return new PathResult<>(path, dist[t]);
    }

    // ---------------- Bellman-Ford ----------------

    /**
     * Shortest distances allowing NEGATIVE weights. Throws IllegalStateException if a
     * negative cycle is reachable from source (distances would go to -infinity).
     * Note: in an UNDIRECTED graph any negative edge is itself a negative cycle (u-v-u).
     */
    public MyHashMap<T, Double> bellmanFord(T source) {
        if (!adj.containsKey(source)) throw new IllegalArgumentException("unknown vertex: " + source);
        index();
        int n = vertexList.size();
        double[] dist = new double[n];
        for (int i = 0; i < n; i++) dist[i] = Double.POSITIVE_INFINITY;
        dist[idOf.get(source)] = 0;

        for (int round = 0; round < n; round++) {   // n-1 rounds suffice; round n detects cycles
            boolean changed = false;
            for (int u = 0; u < n; u++) {
                if (dist[u] == Double.POSITIVE_INFINITY) continue;
                MyArrayList<Edge<T>> edges = adj.get(vertexList.get(u));
                for (int i = 0; i < edges.size(); i++) {
                    Edge<T> e = edges.get(i);
                    int v = idOf.get(e.to);
                    if (dist[u] + e.weight < dist[v]) {
                        if (round == n - 1) throw new IllegalStateException("negative cycle reachable from " + source);
                        dist[v] = dist[u] + e.weight;
                        changed = true;
                    }
                }
            }
            if (!changed) break; // early exit: nothing improved, distances are final
        }
        MyHashMap<T, Double> result = new MyHashMap<>();
        for (int i = 0; i < n; i++) {
            if (dist[i] != Double.POSITIVE_INFINITY) result.put(vertexList.get(i), dist[i]);
        }
        return result;
    }

    // ---------------- Prim's minimum spanning tree ----------------

    /**
     * Minimum spanning tree edges (undirected graphs). If the graph is disconnected,
     * returns a minimum spanning FOREST (one tree per component).
     */
    public MyArrayList<Edge<T>> minimumSpanningTree() {
        if (directed) throw new IllegalStateException("MST is defined for undirected graphs");
        index();
        int n = vertexList.size();
        boolean[] inTree = new boolean[n];
        double[] best = new double[n];   // cheapest known edge weight connecting v to the tree
        Edge<T>[] bestEdge = newEdgeArray(n);
        MyArrayList<Edge<T>> result = new MyArrayList<>();
        IndexedMinPQ<Double> pq = new IndexedMinPQ<>(n);

        for (int start = 0; start < n; start++) {  // one Prim run per component
            if (inTree[start]) continue;
            for (int i = 0; i < n; i++) if (!inTree[i]) best[i] = Double.POSITIVE_INFINITY;
            best[start] = 0;
            pq.insert(start, 0.0);
            while (!pq.isEmpty()) {
                int u = pq.delMin();
                inTree[u] = true;
                if (bestEdge[u] != null) result.add(bestEdge[u]);
                MyArrayList<Edge<T>> edges = adj.get(vertexList.get(u));
                for (int i = 0; i < edges.size(); i++) {
                    Edge<T> e = edges.get(i);
                    int v = idOf.get(e.to);
                    if (!inTree[v] && e.weight < best[v]) { // a cheaper way to attach v
                        best[v] = e.weight;
                        bestEdge[v] = e;
                        if (pq.contains(v)) pq.decreaseKey(v, e.weight);
                        else pq.insert(v, e.weight);
                    }
                }
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private static <T> Edge<T>[] newEdgeArray(int n) {
        return (Edge<T>[]) new Edge[n];
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- WEIGHTED GRAPH DEMO (road trip, km) ---");
        WeightedGraph<String> roads = new WeightedGraph<>(false);
        roads.addEdge("Amsterdam", "Utrecht", 45);
        roads.addEdge("Amsterdam", "Haarlem", 20);
        roads.addEdge("Haarlem", "Leiden", 33);
        roads.addEdge("Leiden", "The Hague", 20);
        roads.addEdge("Utrecht", "Rotterdam", 60);
        roads.addEdge("The Hague", "Rotterdam", 27);
        roads.addEdge("Amsterdam", "Rotterdam", 120);
        roads.addVertex("Texel"); // an island with no road

        System.out.println("Distances from Amsterdam: " + roads.dijkstra("Amsterdam"));
        System.out.println("Amsterdam -> Rotterdam: " + roads.shortestPath("Amsterdam", "Rotterdam")); // 100 via Haarlem
        System.out.println("Amsterdam -> Texel: " + roads.shortestPath("Amsterdam", "Texel"));

        MyArrayList<Edge<String>> mst = roads.minimumSpanningTree();
        double total = 0;
        for (Edge<String> e : mst) total += e.getWeight();
        System.out.println("Cheapest road network connecting all cities: " + mst + " = " + total + " km");

        WeightedGraph<String> money = new WeightedGraph<>(true); // negative weights = cashback
        money.addEdge("A", "B", 4);
        money.addEdge("A", "C", 5);
        money.addEdge("C", "B", -3);
        System.out.println("\nBellman-Ford with a negative edge: " + money.bellmanFord("A")); // B = 2 via C
        money.addEdge("B", "C", 1); // C -> B -> C costs -2: a negative cycle
        try {
            money.bellmanFord("A");
        } catch (IllegalStateException ex) {
            System.out.println("After adding B->C: " + ex.getMessage());
        }
    }
}
