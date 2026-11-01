package skiplist;

/**
 * GENERIC SKIP LIST (sorted set)
 *
 * What it is:
 * A sorted linked list with "express lanes" stacked on top. Level 0 holds every element
 * in order; each higher level holds a random ~half of the level below. A search starts in
 * the top lane, runs right until the next node would overshoot, drops down a lane, and
 * repeats - like taking the express train, then the local.
 * This is the generic version of SkipList.java (which stores ints only).
 *
 * Why use it instead of a balanced tree?
 * Same expected O(log N) as a Red-Black tree, but much simpler code: no rotations, no
 * colors - balance comes from coin flips. Redis sorted sets and Java's
 * ConcurrentSkipListMap use skip lists (they are also easier to make lock-free).
 *
 * Approach/Strategy:
 * - Each node gets a random height: keep flipping a coin while heads (p = 1/2), max 32.
 *   So ~1/2 of nodes reach level 1, ~1/4 level 2, ... -> about log2(N) levels.
 * - Insert/delete: walk down remembering, per level, the last node BEFORE the position
 *   ("update" array); then splice the node in/out at each of its levels.
 *
 * Time/Space Complexity (expected):
 * Operation                           | Time
 * -------------------------------------------------
 * add / remove / contains             | O(log N)
 * first / last                        | O(1) / O(log N)
 * floor / ceiling / lower / higher    | O(log N)
 * iteration (sorted)                  | O(N)
 * Space                               | O(N) (about 2 pointers per element on average)
 */
public class GenericSkipList<T extends Comparable<T>> implements Iterable<T> {

    private static final int MAX_LEVEL = 32;

    private static class Node<T> {
        final T value;
        final Node<T>[] next; // next[i] = following node in lane i

        @SuppressWarnings("unchecked")
        Node(T value, int height) {
            this.value = value;
            this.next = (Node<T>[]) new Node[height];
        }
    }

    private final Node<T> head = new Node<>(null, MAX_LEVEL); // sentinel, in every lane
    private int levels = 1;  // lanes currently in use
    private int size = 0;
    private final java.util.Random random;

    public GenericSkipList() {
        this(new java.util.Random());
    }

    /** Pass a seeded Random for reproducible structure (tests, demos). */
    public GenericSkipList(java.util.Random random) {
        this.random = random;
    }

    private int randomHeight() {
        int h = 1;
        while (h < MAX_LEVEL && random.nextBoolean()) h++;
        return h;
    }

    /**
     * Fills update[i] with the last node in lane i whose value is < target,
     * and returns the level-0 successor (first node with value >= target, or null).
     */
    private Node<T> findPredecessors(T target, Node<T>[] update) {
        Node<T> cur = head;
        for (int i = levels - 1; i >= 0; i--) {
            while (cur.next[i] != null && cur.next[i].value.compareTo(target) < 0) {
                cur = cur.next[i]; // run along the express lane
            }
            if (update != null) update[i] = cur; // then drop down one lane
        }
        return cur.next[0];
    }

    // ---------------- core operations ----------------

    public boolean contains(T value) {
        if (value == null) return false;
        Node<T> candidate = findPredecessors(value, null);
        return candidate != null && candidate.value.compareTo(value) == 0;
    }

    /** Adds value. Returns false if it was already present (set semantics). */
    @SuppressWarnings("unchecked")
    public boolean add(T value) {
        if (value == null) throw new IllegalArgumentException("null values are not supported");
        Node<T>[] update = (Node<T>[]) new Node[MAX_LEVEL];
        Node<T> candidate = findPredecessors(value, update);
        if (candidate != null && candidate.value.compareTo(value) == 0) return false;

        int height = randomHeight();
        if (height > levels) {
            for (int i = levels; i < height; i++) update[i] = head; // new lanes start at head
            levels = height;
        }
        Node<T> node = new Node<>(value, height);
        for (int i = 0; i < height; i++) { // splice into each of its lanes
            node.next[i] = update[i].next[i];
            update[i].next[i] = node;
        }
        size++;
        return true;
    }

    /** Removes value. Returns true if it was present. */
    @SuppressWarnings("unchecked")
    public boolean remove(T value) {
        if (value == null) return false;
        Node<T>[] update = (Node<T>[]) new Node[MAX_LEVEL];
        Node<T> target = findPredecessors(value, update);
        if (target == null || target.value.compareTo(value) != 0) return false;

        for (int i = 0; i < target.next.length; i++) { // unlink from each lane it is in
            update[i].next[i] = target.next[i];
        }
        while (levels > 1 && head.next[levels - 1] == null) levels--; // drop empty top lanes
        size--;
        return true;
    }

    // ---------------- navigation ----------------

    public T first() {
        return head.next[0] == null ? null : head.next[0].value;
    }

    public T last() {
        Node<T> cur = head;
        for (int i = levels - 1; i >= 0; i--) {
            while (cur.next[i] != null) cur = cur.next[i];
        }
        return cur == head ? null : cur.value;
    }

    /** Smallest element >= value, or null. */
    public T ceiling(T value) {
        Node<T> n = findPredecessors(value, null);
        return n == null ? null : n.value;
    }

    /** Smallest element > value, or null. */
    public T higher(T value) {
        Node<T> n = findPredecessors(value, null);
        if (n != null && n.value.compareTo(value) == 0) n = n.next[0];
        return n == null ? null : n.value;
    }

    /** Greatest element strictly < value, or null: the level-0 predecessor. */
    @SuppressWarnings("unchecked")
    public T lower(T value) {
        Node<T>[] update = (Node<T>[]) new Node[MAX_LEVEL];
        findPredecessors(value, update);
        return update[0] == head ? null : update[0].value;
    }

    /** Greatest element <= value, or null. */
    public T floor(T value) {
        return contains(value) ? value : lower(value);
    }

    // ---------------- utilities ----------------

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        for (int i = 0; i < MAX_LEVEL; i++) head.next[i] = null;
        levels = 1;
        size = 0;
    }

    /** Ascending order: just walk the bottom lane. */
    @Override
    public java.util.Iterator<T> iterator() {
        return new java.util.Iterator<T>() {
            private Node<T> current = head.next[0];

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (current == null) throw new java.util.NoSuchElementException();
                T value = current.value;
                current = current.next[0];
                return value;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (Node<T> n = head.next[0]; n != null; n = n.next[0]) {
            sb.append(n.value);
            if (n.next[0] != null) sb.append(", ");
        }
        return sb.append("]").toString();
    }

    /** Prints every lane - shows the "express lanes" structure. */
    public void printLanes() {
        for (int i = levels - 1; i >= 0; i--) {
            StringBuilder sb = new StringBuilder("Lane " + i + ": ");
            for (Node<T> n = head.next[i]; n != null; n = n.next[i]) sb.append(n.value).append(" -> ");
            System.out.println(sb.append("null"));
        }
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- GENERIC SKIP LIST DEMO ---");
        GenericSkipList<String> words = new GenericSkipList<>(new java.util.Random(7));
        for (String w : new String[]{"mango", "apple", "kiwi", "banana", "cherry", "fig", "grape"}) words.add(w);
        System.out.println("Sorted: " + words + " (size " + words.size() + ")");
        words.printLanes();
        System.out.println("contains(kiwi) = " + words.contains("kiwi") + ", contains(lime) = " + words.contains("lime"));
        System.out.println("ceiling(dates) = " + words.ceiling("dates") + ", floor(dates) = " + words.floor("dates"));
        words.remove("cherry");
        System.out.println("After remove(cherry): " + words + ", first = " + words.first() + ", last = " + words.last());
    }
}
