package priorityqueue.indexed;

/**
 * INDEXED MIN PRIORITY QUEUE
 *
 * What it is:
 * A min-priority queue where every item has an integer ID (0..capacity-1), and you can
 * CHANGE THE PRIORITY of any item already in the queue - "decrease-key" - in O(log N).
 * A normal binary heap can't do that, because it has no idea WHERE an item sits.
 *
 * Why it matters:
 * Dijkstra and Prim repeatedly discover a cheaper distance for a vertex that is already
 * queued. With a plain priority queue you must insert a duplicate and skip stale entries
 * later (the queue can grow to O(E)). With decrease-key the queue never holds more than V
 * entries - the textbook O(E log V) version of both algorithms.
 *
 * Approach/Strategy - three arrays:
 *   pq[pos]  = the item ID stored at heap position pos   (heap uses positions 1..n)
 *   qp[id]   = the heap position of item id, or -1       (the INVERSE of pq)
 *   keys[id] = the priority of item id
 * Every swap in the heap updates both pq and qp, so we can always jump straight to an
 * item's position in O(1), then sift it up or down in O(log N).
 *
 * Time/Space Complexity:
 * Operation                              | Time
 * -----------------------------------------------------
 * insert / delMin / delete(id)           | O(log N)
 * decreaseKey / increaseKey / changeKey  | O(log N)
 * minIndex / minKey / contains / keyOf   | O(1)
 * Space                                  | O(capacity)
 */
public class IndexedMinPQ<Key extends Comparable<Key>> {

    private final int capacity;
    private int n;            // number of items in the queue
    private final int[] pq;   // heap position -> item id
    private final int[] qp;   // item id -> heap position (-1 = not in queue)
    private final Object[] keys;

    public IndexedMinPQ(int capacity) {
        if (capacity < 0) throw new IllegalArgumentException("capacity must be >= 0");
        this.capacity = capacity;
        this.pq = new int[capacity + 1];
        this.qp = new int[capacity];
        this.keys = new Object[capacity];
        for (int i = 0; i < capacity; i++) qp[i] = -1;
    }

    private void checkId(int id) {
        if (id < 0 || id >= capacity) throw new IndexOutOfBoundsException("id " + id + " not in [0, " + capacity + ")");
    }

    public boolean contains(int id) {
        checkId(id);
        return qp[id] != -1;
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public void insert(int id, Key key) {
        checkId(id);
        if (contains(id)) throw new IllegalArgumentException("id " + id + " is already in the queue");
        n++;
        qp[id] = n;
        pq[n] = id;
        keys[id] = key;
        swim(n);
    }

    @SuppressWarnings("unchecked")
    public Key keyOf(int id) {
        checkId(id);
        if (!contains(id)) throw new IllegalArgumentException("id " + id + " is not in the queue");
        return (Key) keys[id];
    }

    /** ID of the item with the smallest key. */
    public int minIndex() {
        if (n == 0) throw new IllegalStateException("Priority queue is empty");
        return pq[1];
    }

    @SuppressWarnings("unchecked")
    public Key minKey() {
        if (n == 0) throw new IllegalStateException("Priority queue is empty");
        return (Key) keys[pq[1]];
    }

    /** Removes the smallest item and returns its ID. */
    public int delMin() {
        int min = minIndex();
        delete(min);
        return min;
    }

    /** Removes item id from anywhere in the heap. */
    public void delete(int id) {
        checkId(id);
        if (!contains(id)) throw new IllegalArgumentException("id " + id + " is not in the queue");
        int pos = qp[id];
        swap(pos, n--);       // move the last item into the hole
        if (pos <= n) {       // the moved item may need to go up OR down
            swim(pos);
            sink(pos);
        }
        qp[id] = -1;
        keys[id] = null;
    }

    /** Lower the key of id (the Dijkstra/Prim "relax" step). */
    public void decreaseKey(int id, Key key) {
        if (keyOf(id).compareTo(key) < 0) throw new IllegalArgumentException("new key is larger - use increaseKey");
        keys[id] = key;
        swim(qp[id]); // smaller key can only move UP
    }

    public void increaseKey(int id, Key key) {
        if (keyOf(id).compareTo(key) > 0) throw new IllegalArgumentException("new key is smaller - use decreaseKey");
        keys[id] = key;
        sink(qp[id]); // larger key can only move DOWN
    }

    /** Set any new key (direction unknown): try both directions. */
    public void changeKey(int id, Key key) {
        keyOf(id); // validates
        keys[id] = key;
        swim(qp[id]);
        sink(qp[id]);
    }

    // ---------------- heap helpers (positions are 1-based) ----------------

    @SuppressWarnings("unchecked")
    private boolean greater(int posA, int posB) {
        return ((Key) keys[pq[posA]]).compareTo((Key) keys[pq[posB]]) > 0;
    }

    private void swap(int posA, int posB) {
        int t = pq[posA];
        pq[posA] = pq[posB];
        pq[posB] = t;
        qp[pq[posA]] = posA; // keep the inverse map in sync - the whole trick
        qp[pq[posB]] = posB;
    }

    private void swim(int pos) {
        while (pos > 1 && greater(pos / 2, pos)) {
            swap(pos, pos / 2);
            pos /= 2;
        }
    }

    private void sink(int pos) {
        while (2 * pos <= n) {
            int child = 2 * pos;
            if (child < n && greater(child, child + 1)) child++;
            if (!greater(pos, child)) break;
            swap(pos, child);
            pos = child;
        }
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- INDEXED MIN PRIORITY QUEUE DEMO ---");

        // Dijkstra with decrease-key. graph[u][v] = edge weight (0 = no edge)
        int[][] graph = {
            {0, 4, 1, 0, 0},
            {0, 0, 0, 1, 0},
            {0, 2, 0, 5, 0},
            {0, 0, 0, 0, 3},
            {0, 0, 0, 0, 0}
        };
        int n = graph.length, source = 0;
        int[] dist = new int[n];
        for (int i = 0; i < n; i++) dist[i] = Integer.MAX_VALUE;
        dist[source] = 0;

        IndexedMinPQ<Integer> pq = new IndexedMinPQ<>(n);
        pq.insert(source, 0);
        int maxQueueSize = 1;
        while (!pq.isEmpty()) {
            int u = pq.delMin();
            for (int v = 0; v < n; v++) {
                if (graph[u][v] == 0) continue;
                int candidate = dist[u] + graph[u][v];
                if (candidate < dist[v]) {
                    dist[v] = candidate;
                    if (pq.contains(v)) {
                        pq.decreaseKey(v, candidate);      // update in place: no duplicates
                        System.out.println("  decreaseKey(" + v + ") -> " + candidate);
                    } else {
                        pq.insert(v, candidate);
                    }
                    maxQueueSize = Math.max(maxQueueSize, pq.size());
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) sb.append(i).append(':').append(dist[i]).append(' ');
        System.out.println("Shortest distances from 0: " + sb);     // 0:0 1:3 2:1 3:4 4:7
        System.out.println("Queue never held more than " + maxQueueSize + " items (<= V = " + n + ")");
    }
}
