package hashmap.openaddressing;

import array.MyArrayList;

/**
 * HASH MAP WITH OPEN ADDRESSING
 *
 * What it is:
 * A hash map that stores every entry DIRECTLY in one array - no linked lists per bucket
 * (that is "separate chaining", see MyHashMap). On a collision we look at other slots of
 * the same array, following a PROBE SEQUENCE, until we find the key or an empty slot.
 * Python's dict and Java's IdentityHashMap work this way.
 *
 * Probe strategies (h = hash(key), cap = capacity, i = attempt 0, 1, 2, ...):
 * - LINEAR:         h, h+1, h+2, ...            Simple and cache-friendly, but suffers from
 *                                               PRIMARY CLUSTERING: long runs of full slots
 *                                               form and keep growing.
 * - QUADRATIC:      h, h+1, h+3, h+6, ...       (h + i*(i+1)/2). Jumps further each time, which
 *                                               breaks up clusters. With a power-of-2 capacity
 *                                               these "triangular" steps visit EVERY slot.
 * - DOUBLE_HASHING: h, h+s, h+2s, ...           s comes from a SECOND hash of the key, so two
 *                                               keys that collide at h still take different
 *                                               paths. s is forced odd so it shares no factor
 *                                               with the power-of-2 capacity -> visits every slot.
 *
 * DELETION needs TOMBSTONES:
 * If we simply emptied a slot, a later lookup for a key that had probed PAST that slot
 * would stop early at the "empty" slot and wrongly report "not found". So a deleted slot
 * is marked DELETED: lookups skip over it, inserts may reuse it.
 *
 * LOAD FACTOR:
 * Open addressing slows down sharply as the table fills (expected probes ~ 1/(1-load)),
 * so we keep it at most 0.5 - counting tombstones too, because they lengthen probes.
 * When too full we rebuild: double the size if there are many live keys, otherwise
 * rebuild at the same size just to sweep out tombstones.
 *
 * Time/Space Complexity:
 * Operation      | Average | Worst | Notes
 * ---------------------------------------------------------------
 * put/get/remove | O(1)    | O(N)  | Worst case = every key collides
 * resize         | O(N)    |       | Amortized O(1) per put
 * Space          | O(N)    |       | Array is 2x-4x the number of keys
 *
 * Chaining vs open addressing (common interview question):
 * - Open addressing: no per-entry node objects (less memory, better CPU cache use), but
 *   must keep the load factor low and handle deletes with tombstones.
 * - Chaining: simple deletes, degrades gracefully when overfull, but one node per entry.
 *
 * Null keys are not supported (null is used internally for "no key here").
 */
public class OpenAddressingHashMap<K, V> implements Iterable<K> {

    public enum Probing { LINEAR, QUADRATIC, DOUBLE_HASHING }

    private static final byte EMPTY = 0, OCCUPIED = 1, DELETED = 2;
    private static final double MAX_LOAD = 0.5;
    private static final int DEFAULT_CAPACITY = 16;

    private final Probing probing;
    private Object[] keys;
    private Object[] values;
    private byte[] state;
    private int size;       // live keys
    private int tombstones; // DELETED slots

    public OpenAddressingHashMap() {
        this(Probing.LINEAR, DEFAULT_CAPACITY);
    }

    public OpenAddressingHashMap(Probing probing) {
        this(probing, DEFAULT_CAPACITY);
    }

    public OpenAddressingHashMap(Probing probing, int initialCapacity) {
        this.probing = probing;
        int cap = 2;
        while (cap < initialCapacity) cap <<= 1; // capacity must be a power of 2
        allocate(cap);
    }

    private void allocate(int capacity) {
        keys = new Object[capacity];
        values = new Object[capacity];
        state = new byte[capacity];
        size = 0;
        tombstones = 0;
    }

    // ---------------- hashing & probing ----------------

    // Spread the bits (same idea as java.util.HashMap) so low bits depend on the whole hash
    private static int mix(int h) {
        h ^= (h >>> 16);
        h *= 0x85EBCA6B;
        h ^= (h >>> 13);
        return h;
    }

    private int slot(Object key, int attempt) {
        int mask = keys.length - 1;
        int h = mix(key.hashCode());
        switch (probing) {
            case LINEAR:
                return (h + attempt) & mask;
            case QUADRATIC:
                return (int) ((h + (long) attempt * (attempt + 1) / 2) & mask); // long: no overflow
            default: {
                int step = (mix(h * 0x9E3779B9) >>> 1) | 1; // second hash, forced odd
                return (h + attempt * step) & mask;
            }
        }
    }

    /** Index of key's slot, or -1 if absent. Stops at the first EMPTY slot. */
    private int find(Object key) {
        for (int i = 0; i < keys.length; i++) {
            int s = slot(key, i);
            if (state[s] == EMPTY) return -1;
            if (state[s] == OCCUPIED && keys[s].equals(key)) return s;
            // DELETED: keep going - the key may live further along the probe sequence
        }
        return -1;
    }

    /** How many slots a lookup of key inspects (1 = found/rejected immediately). For demos/analysis. */
    public int probeLength(K key) {
        for (int i = 0; i < keys.length; i++) {
            int s = slot(key, i);
            if (state[s] == EMPTY || (state[s] == OCCUPIED && keys[s].equals(key))) return i + 1;
        }
        return keys.length;
    }

    // ---------------- core operations ----------------

    public void put(K key, V value) {
        if (key == null) throw new IllegalArgumentException("null keys are not supported");
        int existing = find(key);
        if (existing >= 0) {
            values[existing] = value; // update in place
            return;
        }
        if (size + tombstones + 1 > keys.length * MAX_LOAD) {
            // Many live keys -> grow. Mostly tombstones -> same size, just clean them out.
            rebuild(size + 1 > keys.length * MAX_LOAD / 2 ? keys.length * 2 : keys.length);
        }
        // Insert at the first slot that is not OCCUPIED (reusing a tombstone is fine:
        // we already know the key is not stored further along).
        for (int i = 0; i < keys.length; i++) {
            int s = slot(key, i);
            if (state[s] != OCCUPIED) {
                if (state[s] == DELETED) tombstones--;
                keys[s] = key;
                values[s] = value;
                state[s] = OCCUPIED;
                size++;
                return;
            }
        }
        throw new IllegalStateException("table full"); // unreachable: load factor <= 0.5
    }

    @SuppressWarnings("unchecked")
    public V get(K key) {
        if (key == null) return null;
        int s = find(key);
        return s < 0 ? null : (V) values[s];
    }

    public V getOrDefault(K key, V defaultValue) {
        if (key == null) return defaultValue;
        int s = find(key);
        @SuppressWarnings("unchecked")
        V v = s < 0 ? defaultValue : (V) values[s];
        return v;
    }

    public boolean containsKey(K key) {
        return key != null && find(key) >= 0;
    }

    /** Removes key and returns its value (or null). Leaves a TOMBSTONE behind. */
    @SuppressWarnings("unchecked")
    public V remove(K key) {
        if (key == null) return null;
        int s = find(key);
        if (s < 0) return null;
        V old = (V) values[s];
        keys[s] = null;
        values[s] = null;
        state[s] = DELETED;
        size--;
        tombstones++;
        return old;
    }

    @SuppressWarnings("unchecked")
    private void rebuild(int newCapacity) {
        Object[] oldKeys = keys, oldValues = values;
        byte[] oldState = state;
        allocate(newCapacity);
        for (int i = 0; i < oldKeys.length; i++) {
            if (oldState[i] == OCCUPIED) put((K) oldKeys[i], (V) oldValues[i]);
        }
    }

    // ---------------- utilities ----------------

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int capacity() {
        return keys.length;
    }

    public void clear() {
        allocate(keys.length);
    }

    @SuppressWarnings("unchecked")
    public MyArrayList<K> keySet() {
        MyArrayList<K> out = new MyArrayList<>(size);
        for (int i = 0; i < keys.length; i++) if (state[i] == OCCUPIED) out.add((K) keys[i]);
        return out;
    }

    @SuppressWarnings("unchecked")
    public MyArrayList<V> values() {
        MyArrayList<V> out = new MyArrayList<>(size);
        for (int i = 0; i < keys.length; i++) if (state[i] == OCCUPIED) out.add((V) values[i]);
        return out;
    }

    /** Iterates the keys (slot order, i.e. no particular order). */
    @Override
    public java.util.Iterator<K> iterator() {
        return new java.util.Iterator<K>() {
            private int index = advance(0);

            private int advance(int from) {
                while (from < keys.length && state[from] != OCCUPIED) from++;
                return from;
            }

            @Override
            public boolean hasNext() {
                return index < keys.length;
            }

            @Override
            @SuppressWarnings("unchecked")
            public K next() {
                if (index >= keys.length) throw new java.util.NoSuchElementException();
                K key = (K) keys[index];
                index = advance(index + 1);
                return key;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (int i = 0; i < keys.length; i++) {
            if (state[i] != OCCUPIED) continue;
            if (sb.length() > 1) sb.append(", ");
            sb.append(keys[i]).append('=').append(values[i]);
        }
        return sb.append("}").toString();
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- OPEN ADDRESSING HASH MAP DEMO ---");
        OpenAddressingHashMap<String, Integer> ages = new OpenAddressingHashMap<>();
        ages.put("alice", 30);
        ages.put("bob", 25);
        ages.put("carol", 35);
        ages.put("bob", 26); // update
        System.out.println("Map: " + ages + ", size " + ages.size());
        System.out.println("get(bob) = " + ages.get("bob"));
        ages.remove("alice");
        System.out.println("After remove(alice): containsKey(alice) = " + ages.containsKey("alice")
                + ", carol still found = " + ages.get("carol"));

        // Compare probe strategies with the table almost at its 50% limit (8,000 random keys
        // in 16,384 slots). Theory at load 0.49: linear ~1.5 probes to FIND a key and ~2.5 to
        // decide a key is ABSENT; quadratic/double hashing avoid primary clustering and need
        // fewer (~1.4 and ~2.0). The gap grows quickly as the table fills - which is why open
        // addressing keeps the load factor low.
        System.out.println("\nAverage slots inspected (8,000 random keys, 16,384 slots = 49% full):");
        System.out.println("  strategy         found key   absent key");
        for (Probing p : Probing.values()) {
            OpenAddressingHashMap<Integer, Integer> m = new OpenAddressingHashMap<>(p, 16384);
            java.util.Random rand = new java.util.Random(1);
            int[] present = new int[8000];
            for (int i = 0; i < present.length; i++) {
                present[i] = rand.nextInt();
                m.put(present[i], i);
            }
            long found = 0, absent = 0;
            for (int k : present) found += m.probeLength(k);
            for (int i = 0; i < 8000; i++) {
                int k = rand.nextInt();
                if (!m.containsKey(k)) absent += m.probeLength(k);
            }
            System.out.printf("  %-15s  %6.2f      %6.2f%n", p, found / 8000.0, absent / 8000.0);
        }
    }
}
