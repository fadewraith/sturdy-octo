package hashmap.linked;

import array.MyArrayList;
import hashmap.chaining.MyHashMap;

/**
 * LINKED HASH MAP
 *
 * What it is:
 * A hash map that REMEMBERS ORDER. Plain hash maps iterate in "random" bucket order;
 * this one iterates in the order keys were inserted (or, optionally, in the order they
 * were last ACCESSED). Same as java.util.LinkedHashMap.
 *
 * Approach/Strategy:
 * Two structures share the same nodes:
 *   - MyHashMap<K, Node>  : finds a key's node in O(1)
 *   - a doubly linked list: keeps the nodes in order (oldest after 'head', newest before 'tail')
 * Because each node knows its prev/next, removing or moving it is O(1) - no searching.
 * Dummy head/tail nodes mean we never special-case an empty list or the ends.
 *
 * Two modes:
 *   - insertion order (default): re-putting an existing key does NOT change its position.
 *   - access order: every get/put moves the key to the end (most recently used).
 * Plus an optional maxEntries limit: when exceeded, the OLDEST entry (front of the list)
 * is evicted. Access order + maxEntries = an LRU cache in a few lines.
 *
 * Time/Space Complexity:
 * Operation                 | Time
 * ------------------------------------------
 * put/get/remove/contains   | O(1) average
 * firstKey/lastKey          | O(1)
 * iteration                 | O(N) in order
 * Space                     | O(N) (hash entry + list node per key)
 *
 * Real-world uses: LRU caches, preserving JSON field order, "recently viewed" lists.
 */
public class MyLinkedHashMap<K, V> implements Iterable<MyLinkedHashMap.Entry<K, V>> {

    /** One key-value pair; also a node of the order list. */
    public static class Entry<K, V> {
        final K key;
        V value;
        Entry<K, V> prev, next;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    private final MyHashMap<K, Entry<K, V>> index = new MyHashMap<>();
    private final Entry<K, V> head = new Entry<>(null, null); // dummy: head.next = oldest
    private final Entry<K, V> tail = new Entry<>(null, null); // dummy: tail.prev = newest
    private final boolean accessOrder;
    private final int maxEntries; // 0 = unlimited

    /** Insertion-ordered, unlimited. */
    public MyLinkedHashMap() {
        this(false, 0);
    }

    /**
     * @param accessOrder true = order by last access (get/put move a key to the end)
     * @param maxEntries  evict the oldest entry when size exceeds this (0 = no limit)
     */
    public MyLinkedHashMap(boolean accessOrder, int maxEntries) {
        if (maxEntries < 0) throw new IllegalArgumentException("maxEntries must be >= 0");
        this.accessOrder = accessOrder;
        this.maxEntries = maxEntries;
        head.next = tail;
        tail.prev = head;
    }

    // ---------------- list helpers, all O(1) ----------------

    private void unlink(Entry<K, V> e) {
        e.prev.next = e.next;
        e.next.prev = e.prev;
        e.prev = null;
        e.next = null;
    }

    private void appendToEnd(Entry<K, V> e) {
        e.prev = tail.prev;
        e.next = tail;
        tail.prev.next = e;
        tail.prev = e;
    }

    private void moveToEnd(Entry<K, V> e) {
        unlink(e);
        appendToEnd(e);
    }

    // ---------------- core operations ----------------

    public void put(K key, V value) {
        Entry<K, V> e = index.get(key);
        if (e != null) {
            e.value = value;
            if (accessOrder) moveToEnd(e); // insertion order: position unchanged
            return;
        }
        e = new Entry<>(key, value);
        index.put(key, e);
        appendToEnd(e);
        if (maxEntries > 0 && index.size() > maxEntries) {
            remove(head.next.key); // evict the eldest
        }
    }

    public V get(K key) {
        Entry<K, V> e = index.get(key);
        if (e == null) return null;
        if (accessOrder) moveToEnd(e);
        return e.value;
    }

    public V getOrDefault(K key, V defaultValue) {
        if (!index.containsKey(key)) return defaultValue;
        return get(key);
    }

    public boolean containsKey(K key) {
        return index.containsKey(key); // does not count as an access
    }

    public V remove(K key) {
        if (!index.containsKey(key)) return null;
        Entry<K, V> e = index.remove(key);
        unlink(e);
        return e.value;
    }

    /** Oldest key (first inserted, or least recently used in access-order mode). */
    public K firstKey() {
        if (isEmpty()) throw new IllegalStateException("Map is empty");
        return head.next.key;
    }

    /** Newest key (last inserted, or most recently used). */
    public K lastKey() {
        if (isEmpty()) throw new IllegalStateException("Map is empty");
        return tail.prev.key;
    }

    public int size() {
        return index.size();
    }

    public boolean isEmpty() {
        return index.isEmpty();
    }

    public void clear() {
        index.clear();
        head.next = tail;
        tail.prev = head;
    }

    /** Keys in order (oldest first). */
    public MyArrayList<K> keySet() {
        MyArrayList<K> out = new MyArrayList<>(size());
        for (Entry<K, V> e = head.next; e != tail; e = e.next) out.add(e.key);
        return out;
    }

    /** Values in key order (oldest first). */
    public MyArrayList<V> values() {
        MyArrayList<V> out = new MyArrayList<>(size());
        for (Entry<K, V> e = head.next; e != tail; e = e.next) out.add(e.value);
        return out;
    }

    /** Entries in order, oldest first. Iterating does not count as access. */
    @Override
    public java.util.Iterator<Entry<K, V>> iterator() {
        return new java.util.Iterator<Entry<K, V>>() {
            private Entry<K, V> current = head.next;

            @Override
            public boolean hasNext() {
                return current != tail;
            }

            @Override
            public Entry<K, V> next() {
                if (current == tail) throw new java.util.NoSuchElementException();
                Entry<K, V> e = current;
                current = current.next;
                return e;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (Entry<K, V> e = head.next; e != tail; e = e.next) {
            if (e != head.next) sb.append(", ");
            sb.append(e);
        }
        return sb.append("}").toString();
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- LINKED HASH MAP DEMO ---");
        MyLinkedHashMap<String, Integer> scores = new MyLinkedHashMap<>();
        scores.put("zoe", 90);
        scores.put("adam", 75);
        scores.put("mia", 82);
        scores.put("adam", 80); // update: keeps its original position
        System.out.println("Insertion order kept: " + scores);   // {zoe=90, adam=80, mia=82}
        System.out.println("Oldest: " + scores.firstKey() + ", newest: " + scores.lastKey());

        System.out.println("\nAs an LRU cache (access order, max 3 entries):");
        MyLinkedHashMap<Integer, String> cache = new MyLinkedHashMap<>(true, 3);
        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");
        cache.get(1);           // 1 becomes most recently used
        cache.put(4, "four");   // evicts 2, the least recently used
        System.out.println("Cache (LRU first): " + cache); // {3=three, 1=one, 4=four}
        System.out.println("Contains 2? " + cache.containsKey(2)); // false
    }
}
