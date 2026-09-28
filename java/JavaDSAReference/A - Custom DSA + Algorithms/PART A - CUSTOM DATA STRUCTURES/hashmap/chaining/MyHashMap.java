package hashmap.chaining;

import array.MyArrayList;

/**
 * HASHMAP (using Chaining for Collision Resolution)
 * 
 * What it is:
 * A data structure that maps keys to values for highly efficient lookups.
 * 
 * Approach/Strategy (Chaining):
 * We use an array of "Buckets". Each bucket is the head of a Singly Linked List of Entries.
 * When we want to insert or look up a key:
 * 1. Calculate a hash code for the key.
 * 2. Compress the hash code into an array index: `index = Math.abs(key.hashCode()) % capacity`.
 *    (This ensures the index fits within our current bucket array).
 * 3. Traverse the linked list at that index. If the key exists, update its value. 
 *    If not, add a new Entry node to the list.
 * 
 * Rehash/Resize:
 * If the number of elements exceeds a certain `loadFactor` (commonly 0.75), collisions 
 * become too frequent (linked lists get too long). We double the bucket array capacity 
 * and redistribute (rehash) all existing entries into the new buckets.
 * 
 * Time/Space Complexity:
 * Operation      | Time Complexity       | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * Put            | O(1) amortized avg    | O(1)             | O(N) if resize triggers
 * Get            | O(1) average          | O(1)             | O(N) worst-case (all keys in 1 bucket)
 * Remove         | O(1) average          | O(1)             | Requires finding in the chain
 * Resize/Rehash  | O(N)                  | O(N)             | Reallocates array and redistributes
 * 
 * Real-world analogy:
 * Think of a filing cabinet where each drawer is labeled with a letter (A, B, C...).
 * To find "Smith", you jump directly to the 'S' drawer (O(1) hash lookup). If there are 
 * multiple files in 'S', you flip through them one by one (traversing the chain) to find 
 * the exact "Smith" file.
 * 
 * No built-in rule: We return our custom `MyArrayList` for keySet, values, and entrySet!
 */
public class MyHashMap<K, V> {

    /**
     * Internal Entry node for the linked list in each bucket.
     */
    public static class Entry<K, V> {
        public K key;
        public V value;
        public Entry<K, V> next;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
        
        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private Entry<K, V>[] buckets;
    private int size;
    private final float loadFactor;

    @SuppressWarnings("unchecked")
    public MyHashMap(int capacity, float loadFactor) {
        if (capacity <= 0 || loadFactor <= 0) {
            throw new IllegalArgumentException("Capacity and load factor must be positive.");
        }
        this.buckets = (Entry<K, V>[]) new Entry[capacity];
        this.size = 0;
        this.loadFactor = loadFactor;
    }

    public MyHashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Custom hash function mapping a key to an array index.
     */
    private int getBucketIndex(K key, int capacity) {
        if (key == null) return 0;
        // Math.abs handles negative hash codes gracefully
        return Math.abs(key.hashCode()) % capacity;
    }

    /**
     * Associates the specified value with the specified key.
     */
    public void put(K key, V value) {
        if (size >= buckets.length * loadFactor) {
            rehash();
        }

        int index = getBucketIndex(key, buckets.length);
        Entry<K, V> head = buckets[index];
        Entry<K, V> current = head;

        // 1. Check if key already exists in this bucket's chain
        while (current != null) {
            if (key == null ? current.key == null : key.equals(current.key)) {
                current.value = value; // Update value
                return;
            }
            current = current.next;
        }

        // 2. Key does not exist, insert at the head of the chain for O(1) insertion
        Entry<K, V> newEntry = new Entry<>(key, value);
        newEntry.next = head;
        buckets[index] = newEntry;
        size++;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if not found.
     */
    public V get(K key) {
        int index = getBucketIndex(key, buckets.length);
        Entry<K, V> current = buckets[index];

        while (current != null) {
            if (key == null ? current.key == null : key.equals(current.key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     */
    public V remove(K key) {
        int index = getBucketIndex(key, buckets.length);
        Entry<K, V> current = buckets[index];
        Entry<K, V> prev = null;

        while (current != null) {
            if (key == null ? current.key == null : key.equals(current.key)) {
                V removedValue = current.value;
                if (prev == null) {
                    // It was the head of the chain
                    buckets[index] = current.next;
                } else {
                    // It was in the middle/end
                    prev.next = current.next;
                }
                size--;
                return removedValue;
            }
            prev = current;
            current = current.next;
        }
        return null;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public boolean containsValue(V value) {
        for (Entry<K, V> bucketHead : buckets) {
            Entry<K, V> current = bucketHead;
            while (current != null) {
                if (value == null ? current.value == null : value.equals(current.value)) {
                    return true;
                }
                current = current.next;
            }
        }
        return false;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns a custom MyArrayList of all keys.
     */
    public MyArrayList<K> keySet() {
        MyArrayList<K> keys = new MyArrayList<>(size);
        for (Entry<K, V> bucketHead : buckets) {
            Entry<K, V> current = bucketHead;
            while (current != null) {
                keys.add(current.key);
                current = current.next;
            }
        }
        return keys;
    }

    /**
     * Returns a custom MyArrayList of all values.
     */
    public MyArrayList<V> values() {
        MyArrayList<V> vals = new MyArrayList<>(size);
        for (Entry<K, V> bucketHead : buckets) {
            Entry<K, V> current = bucketHead;
            while (current != null) {
                vals.add(current.value);
                current = current.next;
            }
        }
        return vals;
    }

    /**
     * Returns a custom MyArrayList of all Entry objects.
     */
    public MyArrayList<Entry<K, V>> entrySet() {
        MyArrayList<Entry<K, V>> entries = new MyArrayList<>(size);
        for (Entry<K, V> bucketHead : buckets) {
            Entry<K, V> current = bucketHead;
            while (current != null) {
                entries.add(current);
                current = current.next;
            }
        }
        return entries;
    }

    /**
     * Doubles the capacity and redistributes all existing entries.
     */
    @SuppressWarnings("unchecked")
    private void rehash() {
        int newCapacity = buckets.length * 2;
        Entry<K, V>[] newBuckets = (Entry<K, V>[]) new Entry[newCapacity];

        // Traverse all existing buckets and chains
        for (Entry<K, V> bucketHead : buckets) {
            Entry<K, V> current = bucketHead;
            while (current != null) {
                // Must save next pointer before altering the node
                Entry<K, V> next = current.next;
                
                // Find new bucket index
                int newIndex = getBucketIndex(current.key, newCapacity);
                
                // Insert at head of the new bucket's chain
                current.next = newBuckets[newIndex];
                newBuckets[newIndex] = current;
                
                current = next;
            }
        }
        
        buckets = newBuckets;
        System.out.println("[DEBUG] HashMap rehashed to capacity: " + newCapacity);
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- HASHMAP (CHAINING) DEMO ---");
        
        // Start small to force a rehash quickly
        MyHashMap<String, Integer> map = new MyHashMap<>(4, 0.75f);
        
        System.out.println("isEmpty initially? " + map.isEmpty()); // true
        
        map.put("Alice", 25);
        map.put("Bob", 30);
        map.put("Charlie", 35);
        System.out.println("Added Alice, Bob, Charlie.");
        
        System.out.println("Get Bob: " + map.get("Bob")); // 30
        
        // This will likely trigger a rehash (size 4 exceeds 4 * 0.75 = 3)
        map.put("Diana", 40); 
        System.out.println("Added Diana. Size is now: " + map.size()); // 4
        
        // Update existing key
        map.put("Alice", 26);
        System.out.println("Updated Alice. Get Alice: " + map.get("Alice")); // 26
        
        System.out.println("ContainsKey('Charlie')? " + map.containsKey("Charlie")); // true
        System.out.println("ContainsKey('Eve')? " + map.containsKey("Eve")); // false
        System.out.println("ContainsValue(40)? " + map.containsValue(40)); // true
        
        // Test null key
        map.put(null, 0);
        System.out.println("Get null key: " + map.get(null)); // 0
        
        System.out.println("\nKeySet: " + map.keySet().toString());
        System.out.println("Values: " + map.values().toString());
        System.out.println("EntrySet: " + map.entrySet().toString());
        
        // Removals
        System.out.println("\nRemoving Bob: " + map.remove("Bob")); // 30
        System.out.println("ContainsKey('Bob') after removal? " + map.containsKey("Bob")); // false
        System.out.println("Size after removal: " + map.size()); // 4 (3 normal + 1 null key)
    }
}
