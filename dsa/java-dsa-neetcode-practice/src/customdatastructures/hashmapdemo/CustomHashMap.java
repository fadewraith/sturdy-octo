package customdatastructures.hashmapdemo;

public class CustomHashMap<K, V> {

    private Entry<K, V>[] table; // array of buckets
    private int capacity = 16; // default capacity
    private double loadFactor = 0.75d;
    private int size = 0;

    @SuppressWarnings("unchecked")
    public CustomHashMap() {
        table = new Entry[capacity];
    }

    @SuppressWarnings("unchecked")
    public CustomHashMap(int capacity) {
        this.capacity = capacity;
        table = new Entry[capacity];
    }

    // hash fn
    private int hash(K key) {
        if(key == null) {
            return 0; // handle null keys
        }
        return Math.abs(key.hashCode()) % capacity;
    }

    public void put(K key, V value) {
        // Handle null key
        if (key == null) {
            putNullKey(value);
            return;
        }

        int index = hash(key);
        Entry<K, V> newEntry = new Entry<>(key, value, null);

        // If bucket is empty, place entry directly
        if (table[index] == null) {
            table[index] = newEntry;
            size++;
        } else {
            // Handle collision - traverse the chain
            Entry<K, V> previous = null;
            Entry<K, V> current = table[index];

            while (current != null) {
                // If key already exists, update value
                if (current.getKey().equals(key)) {
                    current.setValue(value);
                    return;  // No size increment for update
                }
                previous = current;
                current = current.getNext();
            }

            // Add new entry at end of chain
            previous.setNext(newEntry);
            size++;
        }

        // Check if resize needed
        if (size >= capacity * loadFactor) {
            resize();
        }
    }

    private void putNullKey(V value) {
        if (table[0] == null) {
            table[0] = new Entry<>(null, value, null);
            size++;
        } else {
            // Update null key value if exists
            Entry<K, V> current = table[0];
            while (current != null) {
                if (current.getKey() == null) {
                    current.setValue(value);
                    return;
                }
                current = current.getNext();
            }
            // Add new null key entry
            Entry<K, V> newEntry = new Entry<>(null, value, table[0]);
            table[0] = newEntry;
            size++;
        }
    }

    public V get(K key) {
        int index = hash(key);
        Entry<K, V> entry = table[index];

        while (entry != null) {
            if ((key == null && entry.getKey() == null) ||
                    (key != null && key.equals(entry.getKey()))) {
                return entry.getValue();
            }
            entry = entry.getNext();
        }

        return null;  // Key not found
    }

    public boolean remove(K key) {
        int index = hash(key);
        Entry<K, V> current = table[index];
        Entry<K, V> previous = null;

        while (current != null) {
            if ((key == null && current.getKey() == null) ||
                    (key != null && key.equals(current.getKey()))) {

                if (previous == null) {
                    // Remove first entry in bucket
                    table[index] = current.getNext();
                } else {
                    // Remove middle/last entry
                    previous.setNext(current.getNext());
                }
                size--;
                return true;
            }
            previous = current;
            current = current.getNext();
        }

        return false;  // Key not found
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Entry<K, V>[] oldTable = table;
        int oldCapacity = capacity;

        // Double the capacity
        capacity *= 2;
        size = 0;
        table = new Entry[capacity];

        // Rehash all existing entries
        for (int i = 0; i < oldCapacity; i++) {
            Entry<K, V> entry = oldTable[i];
            while (entry != null) {
                put(entry.getKey(), entry.getValue());
                entry = entry.getNext();
            }
        }
    }

    // Check if map contains key
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    // Check if map contains value
    public boolean containsValue(V value) {
        for (int i = 0; i < capacity; i++) {
            Entry<K, V> entry = table[i];
            while (entry != null) {
                if ((value == null && entry.getValue() == null) ||
                        (value != null && value.equals(entry.getValue()))) {
                    return true;
                }
                entry = entry.getNext();
            }
        }
        return false;
    }

    // Get current size
    public int size() {
        return size;
    }

    // Check if map is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Clear all entries
    public void clear() {
        for (int i = 0; i < capacity; i++) {
            table[i] = null;
        }
        size = 0;
    }

    // Display all entries (for debugging)
    public void display() {
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                Entry<K, V> entry = table[i];
                while (entry != null) {
                    System.out.println("Key: " + entry.getKey() +
                            ", Value: " + entry.getValue());
                    entry = entry.getNext();
                }
            }
        }
    }







}
