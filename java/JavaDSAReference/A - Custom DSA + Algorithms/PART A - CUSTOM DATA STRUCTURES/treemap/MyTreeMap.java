package treemap;

import tree.bst.BinarySearchTree;
import array.MyArrayList;

/**
 * TREEMAP
 * 
 * What it is:
 * A map (key-value store) that maintains its keys in a sorted, ascending order.
 * 
 * Approach/Strategy:
 * We back this map with the `BinarySearchTree` we built earlier. To do this, we 
 * create an internal `MapEntry<K, V>` class that implements `Comparable`. 
 * The `compareTo` method compares ONLY the keys. This allows the BST to sort and 
 * search nodes strictly by their keys, while the nodes quietly carry their associated values.
 * 
 * Time/Space Complexity:
 * Operation      | Average Time | Worst Time | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * Put            | O(log N)     | O(N)       | O(1)             | BST insertion
 * Get            | O(log N)     | O(N)       | O(1)             | BST search
 * Remove         | O(log N)     | O(N)       | O(1)             | BST deletion
 * keySet/values  | O(N)         | O(N)       | O(N)             | In-order traversal
 * 
 * Real-world analogy:
 * A library's card catalog. The cards (entries) are perfectly sorted alphabetically 
 * by the book's title (the key). When you find the title, the card tells you the 
 * physical aisle/shelf location (the value).
 * 
 * Why implement this yourself?
 * Java's `java.util.TreeMap` is backed by a Red-Black Tree. Implementing this on top 
 * of a standard BST proves you understand how Maps can be built out of Trees by 
 * encapsulating keys and values into a single comparable Object.
 */
public class MyTreeMap<K extends Comparable<K>, V> {

    /**
     * The internal object we store in the BST. 
     * Crucially, it implements Comparable by checking ONLY the keys.
     */
    private static class MapEntry<K extends Comparable<K>, V> implements Comparable<MapEntry<K, V>> {
        K key;
        V value;

        MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
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

    private final BinarySearchTree<MapEntry<K, V>> bst;
    private int size;

    public MyTreeMap() {
        this.bst = new BinarySearchTree<>();
        this.size = 0;
    }

    /**
     * Associates the specified value with the specified key.
     */
    public void put(K key, V value) {
        if (key == null) throw new IllegalArgumentException("Null keys not supported");
        
        MapEntry<K, V> searchEntry = new MapEntry<>(key, null);
        MapEntry<K, V> existing = bst.get(searchEntry);
        
        if (existing != null) {
            // Key already exists, just update the value
            existing.value = value;
        } else {
            // New key, insert into BST
            bst.insert(new MapEntry<>(key, value));
            size++;
        }
    }

    /**
     * Returns the value to which the specified key is mapped, or null if not found.
     */
    public V get(K key) {
        if (key == null) return null;
        
        MapEntry<K, V> searchEntry = new MapEntry<>(key, null);
        MapEntry<K, V> found = bst.get(searchEntry);
        
        return found != null ? found.value : null;
    }

    /**
     * Removes the mapping for this key from this TreeMap if present.
     */
    public V remove(K key) {
        if (key == null) return null;
        
        MapEntry<K, V> searchEntry = new MapEntry<>(key, null);
        MapEntry<K, V> found = bst.get(searchEntry);
        
        if (found != null) {
            bst.delete(found); // Uses BST's delete function
            size--;
            return found.value;
        }
        return null;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns a dynamically generated, perfectly sorted list of keys using 
     * the underlying BST's in-order traversal.
     */
    public MyArrayList<K> keySet() {
        MyArrayList<MapEntry<K, V>> sortedEntries = bst.toSortedList();
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
        MyArrayList<MapEntry<K, V>> sortedEntries = bst.toSortedList();
        MyArrayList<V> vals = new MyArrayList<>();
        
        for (int i = 0; i < sortedEntries.size(); i++) {
            vals.add(sortedEntries.get(i).value);
        }
        return vals;
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
