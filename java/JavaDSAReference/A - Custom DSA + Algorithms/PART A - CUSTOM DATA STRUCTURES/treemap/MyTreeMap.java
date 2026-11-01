package treemap;

import tree.redblack.RedBlackTree;
import array.MyArrayList;

/**
 * TREEMAP
 *
 * What it is:
 * A map (key-value store) that maintains its keys in a sorted, ascending order.
 *
 * Approach/Strategy:
 * We back this map with the `RedBlackTree` we built earlier - the same choice as
 * java.util.TreeMap. A plain BST would degrade to a linked list (O(N) per operation)
 * when keys arrive in sorted order; the Red-Black tree stays balanced, so every
 * operation is O(log N) in the WORST case. To store key-value pairs, we
 * create an internal `MapEntry<K, V>` class that implements `Comparable`.
 * The `compareTo` method compares ONLY the keys. This allows the tree to sort and
 * search nodes strictly by their keys, while the nodes quietly carry their associated values.
 *
 * Time/Space Complexity:
 * Operation           | Worst Time | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * Put / Get / Remove  | O(log N)   | O(1)             | Balanced tree
 * first/last/floor/ceiling/lower/higherKey | O(log N) | O(1) | One root-to-leaf walk
 * Iteration (for-each)| O(N) total | O(log N)         | Stack holds one root-to-leaf path
 * keySet/values       | O(N)       | O(N)             | In-order traversal
 * 
 * Real-world analogy:
 * A library's card catalog. The cards (entries) are perfectly sorted alphabetically 
 * by the book's title (the key). When you find the title, the card tells you the 
 * physical aisle/shelf location (the value).
 * 
 * Why implement this yourself?
 * Java's `java.util.TreeMap` is backed by a Red-Black Tree too. Building it this way
 * proves you understand how Maps can be built out of Trees by
 * encapsulating keys and values into a single comparable Object.
 */
public class MyTreeMap<K extends Comparable<K>, V> implements Iterable<MyTreeMap.MapEntry<K, V>> {

    /**
     * The object we store in the tree (also what the for-each loop gives you).
     * Crucially, it implements Comparable by checking ONLY the keys.
     */
    public static class MapEntry<K extends Comparable<K>, V> implements Comparable<MapEntry<K, V>> {
        K key;
        V value;

        MapEntry(K key, V value) {
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
        public int compareTo(MapEntry<K, V> other) {
            return this.key.compareTo(other.key);
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    private RedBlackTree<MapEntry<K, V>> rbTree; // not final: clear() swaps in a fresh tree
    private int size;

    public MyTreeMap() {
        this.rbTree = new RedBlackTree<>();
        this.size = 0;
    }

    /**
     * Associates the specified value with the specified key.
     */
    public void put(K key, V value) {
        if (key == null) throw new IllegalArgumentException("Null keys not supported");
        
        MapEntry<K, V> searchEntry = new MapEntry<>(key, null);
        MapEntry<K, V> existing = rbTree.get(searchEntry);
        
        if (existing != null) {
            // Key already exists, just update the value
            existing.value = value;
        } else {
            // New key, insert into the tree
            rbTree.insert(new MapEntry<>(key, value));
            size++;
        }
    }

    /**
     * Returns the value to which the specified key is mapped, or null if not found.
     */
    public V get(K key) {
        if (key == null) return null;
        
        MapEntry<K, V> searchEntry = new MapEntry<>(key, null);
        MapEntry<K, V> found = rbTree.get(searchEntry);
        
        return found != null ? found.value : null;
    }

    /**
     * Removes the mapping for this key from this TreeMap if present.
     */
    public V remove(K key) {
        if (key == null) return null;
        
        MapEntry<K, V> searchEntry = new MapEntry<>(key, null);
        MapEntry<K, V> found = rbTree.get(searchEntry);
        
        if (found != null) {
            rbTree.delete(found); // Red-Black delete keeps the tree balanced
            size--;
            return found.value;
        }
        return null;
    }

    public boolean containsKey(K key) {
        // Not "get(key) != null": a key can be present with a null VALUE.
        return key != null && rbTree.get(new MapEntry<>(key, null)) != null;
    }

    public void clear() {
        rbTree = new RedBlackTree<>(); // O(1): drop the old tree, GC reclaims it
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns a dynamically generated, perfectly sorted list of keys using 
     * the underlying tree's in-order traversal.
     */
    public MyArrayList<K> keySet() {
        MyArrayList<MapEntry<K, V>> sortedEntries = rbTree.toSortedList();
        MyArrayList<K> keys = new MyArrayList<>();
        
        for (int i = 0; i < sortedEntries.size(); i++) {
            keys.add(sortedEntries.get(i).key);
        }
        return keys;
    }

    /**
     * Returns a list of values in the order of their corresponding sorted keys.
     */
    public MyArrayList<V> values() {
        MyArrayList<MapEntry<K, V>> sortedEntries = rbTree.toSortedList();
        MyArrayList<V> vals = new MyArrayList<>();
        
        for (int i = 0; i < sortedEntries.size(); i++) {
            vals.add(sortedEntries.get(i).value);
        }
        return vals;
    }

    // --------------------------------------------------------
    // NAVIGATION - the reason to pick a TreeMap over a HashMap.
    // Each returns a KEY, or null if there is none. All O(log N).
    // --------------------------------------------------------

    private K keyOf(MapEntry<K, V> e) {
        return e == null ? null : e.key;
    }

    private MapEntry<K, V> probe(K key) {
        if (key == null) throw new IllegalArgumentException("Null keys not supported");
        return new MapEntry<>(key, null);
    }

    /** Smallest key. */
    public K firstKey() {
        return keyOf(rbTree.min());
    }

    /** Largest key. */
    public K lastKey() {
        return keyOf(rbTree.max());
    }

    /** Greatest key <= key. E.g. "which price bracket does 37 fall into?" */
    public K floorKey(K key) {
        return keyOf(rbTree.floor(probe(key)));
    }

    /** Smallest key >= key. E.g. "next available time slot at or after 9:30". */
    public K ceilingKey(K key) {
        return keyOf(rbTree.ceiling(probe(key)));
    }

    /** Greatest key strictly < key. */
    public K lowerKey(K key) {
        return keyOf(rbTree.lower(probe(key)));
    }

    /** Smallest key strictly > key. */
    public K higherKey(K key) {
        return keyOf(rbTree.higher(probe(key)));
    }

    /** Entries in ascending key order: for (MapEntry<K, V> e : map) { e.getKey(); e.getValue(); } */
    @Override
    public java.util.Iterator<MapEntry<K, V>> iterator() {
        return rbTree.iterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (MapEntry<K, V> e : this) {
            if (sb.length() > 1) sb.append(", ");
            sb.append(e);
        }
        return sb.append("}").toString();
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- TREEMAP DEMO ---");
        
        MyTreeMap<String, Integer> treeMap = new MyTreeMap<>();
        
        // Notice we are adding keys OUT OF ORDER.
        treeMap.put("Charlie", 30);
        treeMap.put("Alice", 10);
        treeMap.put("Eve", 50);
        treeMap.put("Bob", 20);
        treeMap.put("Diana", 40);
        
        System.out.println("Initial Size: " + treeMap.size()); // 5
        
        System.out.println("Get Bob: " + treeMap.get("Bob")); // 20
        
        // Update Eve
        treeMap.put("Eve", 55);
        System.out.println("Updated Eve. Get Eve: " + treeMap.get("Eve")); // 55
        
        System.out.println("ContainsKey('Alice')? " + treeMap.containsKey("Alice")); // true
        System.out.println("ContainsKey('Zack')? " + treeMap.containsKey("Zack"));   // false
        
        // The magic of TreeMap: keys are returned perfectly sorted!
        System.out.println("\nSorted KeySet: " + treeMap.keySet().toString());
        // Expected: [Alice, Bob, Charlie, Diana, Eve]
        
        System.out.println("Values (in sorted key order): " + treeMap.values().toString());
        // Expected: [10, 20, 30, 40, 55]
        
        // Deletion
        System.out.println("\nRemoving Charlie...");
        treeMap.remove("Charlie");
        System.out.println("Sorted KeySet after removing Charlie: " + treeMap.keySet().toString());
        System.out.println("New Size: " + treeMap.size()); // 4
    }
}
