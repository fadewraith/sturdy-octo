package hashmap.cuckoo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * CUCKOO HASH MAP (generic key -> value)
 *
 * What it is:
 * A hash map where every key can live in exactly TWO places: slot h1(key) in table 0
 * or slot h2(key) in table 1. So get / containsKey / remove look at most 2 slots:
 * O(1) in the WORST case, not just on average. (CuckooHashing.java is the int-set
 * version of the same idea.)
 *
 * Insert ("kick out the old bird", like a cuckoo chick pushing eggs out of the nest):
 *   1. Put the entry in its table-0 slot. If that slot was taken, the old occupant is
 *      kicked out and moves to ITS slot in the other table, possibly kicking someone else.
 *   2. Repeat. If this goes on too long (a cycle), REHASH: pick new hash seeds, maybe
 *      grow the tables, and re-insert everything.
 *
 * Load factor: two-table cuckoo hashing only works reliably below ~50% total load, so we
 * keep size <= capacity of ONE table (i.e. total load <= 50%) and double when exceeded.
 *
 * Time/Space Complexity:
 * Operation             | Time
 * ----------------------------------------------------
 * get / contains/remove | O(1) worst case (2 slots)
 * put                   | O(1) expected amortized (rehash is rare)
 * Space                 | O(N), tables are 2x-4x the number of keys
 *
 * Limits: null keys are not supported. If many keys have the SAME hashCode, no seed can
 * separate them (each key still has only 2 possible slots), so after repeated failed
 * rehashes put() throws IllegalStateException instead of looping forever.
 */
public class CuckooHashMap<K, V> implements Iterable<K> {

    private static final int MAX_REHASH_ATTEMPTS = 50;

    private Object[][] keys;   // keys[0] and keys[1] are the two tables
    private Object[][] values;
    private int capacity;      // slots per table (power of 2)
    private int size;
    private int seed0, seed1;
    private final Random random = new Random(12345);

    public CuckooHashMap() {
        this(16);
    }

    public CuckooHashMap(int initialCapacity) {
        capacity = 4;
        while (capacity < initialCapacity) capacity <<= 1;
        newSeeds();
        allocate();
    }

    private void allocate() {
        keys = new Object[2][capacity];
        values = new Object[2][capacity];
    }

    private void newSeeds() {
        seed0 = random.nextInt() | 1;
        seed1 = random.nextInt() | 1;
    }

    // Two different hash functions: same hashCode, different multiplier ("seed"), then mix
    private int slot(Object key, int table) {
        int h = key.hashCode() * (table == 0 ? seed0 : seed1);
        h ^= (h >>> 15);
        h *= 0x2C1B3C6D;
        h ^= (h >>> 12);
        return h & (capacity - 1);
    }

    private int find(Object key, int table) {
        int s = slot(key, table);
        return (keys[table][s] != null && keys[table][s].equals(key)) ? s : -1;
    }

    // ---------------- core operations ----------------

    @SuppressWarnings("unchecked")
    public V get(K key) {
        if (key == null) return null;
        for (int t = 0; t < 2; t++) {           // at most TWO places to look
            int s = find(key, t);
            if (s >= 0) return (V) values[t][s];
        }
        return null;
    }

    public boolean containsKey(K key) {
        return key != null && (find(key, 0) >= 0 || find(key, 1) >= 0);
    }

    @SuppressWarnings("unchecked")
    public V remove(K key) {
        if (key == null) return null;
        for (int t = 0; t < 2; t++) {
            int s = find(key, t);
            if (s >= 0) {
                V old = (V) values[t][s];
                keys[t][s] = null;
                values[t][s] = null;
                size--;
                return old;
            }
        }
        return null;
    }

    public void put(K key, V value) {
        if (key == null) throw new IllegalArgumentException("null keys are not supported");
        for (int t = 0; t < 2; t++) {           // existing key: update in place
            int s = find(key, t);
            if (s >= 0) {
                values[t][s] = value;
                return;
            }
        }
        if (size + 1 > capacity) {              // keep total load <= 50%
            rehashAll(null, null, true);        // grow x2
        }
        // On a cycle place() UNDOES its kicks, so the tables are exactly as before;
        // then a rehash re-inserts everything plus the new key with fresh hash seeds.
        if (place(key, value, true) != null) rehashAll(key, value, false);
        size++;
    }

    /**
     * Cuckoo insertion. Returns null on success, or the {key, value} left without a slot
     * after too many kicks (a cycle). With undoOnFailure, every kick is reverted first so
     * the tables are left unchanged (used by put; a rehash just throws its arrays away).
     */
    private Object[] place(Object key, Object value, boolean undoOnFailure) {
        int maxKicks = 4 + 2 * Integer.numberOfTrailingZeros(capacity); // ~ O(log capacity)
        int[] logTable = new int[maxKicks], logSlot = new int[maxKicks];
        Object[] logKey = new Object[maxKicks], logValue = new Object[maxKicks];
        int t = 0;
        for (int kick = 0; kick < maxKicks; kick++) {
            int s = slot(key, t);
            Object oldKey = keys[t][s], oldValue = values[t][s];
            logTable[kick] = t;                  // remember what this slot held
            logSlot[kick] = s;
            logKey[kick] = oldKey;
            logValue[kick] = oldValue;
            keys[t][s] = key;
            values[t][s] = value;
            if (oldKey == null) return null;     // found an empty slot
            key = oldKey;                        // the evicted entry tries its other table
            value = oldValue;
            t = 1 - t;
        }
        if (undoOnFailure) {
            for (int i = maxKicks - 1; i >= 0; i--) { // undo newest change first
                keys[logTable[i]][logSlot[i]] = logKey[i];
                values[logTable[i]][logSlot[i]] = logValue[i];
            }
        }
        return new Object[]{key, value};
    }

    /**
     * Collects every entry (plus an optional extra one), then rebuilds with fresh seeds.
     * If the rebuild hits a cycle again, try new seeds (and grow every few attempts).
     * If every attempt fails, the OLD tables are restored before throwing, so a failed
     * put leaves the map exactly as it was.
     */
    private void rehashAll(Object extraKey, Object extraValue, boolean grow) {
        List<Object[]> entries = new ArrayList<>();
        for (int t = 0; t < 2; t++) {
            for (int s = 0; s < keys[t].length; s++) {
                if (keys[t][s] != null) entries.add(new Object[]{keys[t][s], values[t][s]});
            }
        }
        if (extraKey != null) entries.add(new Object[]{extraKey, extraValue});

        Object[][] oldKeys = keys, oldValues = values;
        int oldCapacity = capacity, oldSeed0 = seed0, oldSeed1 = seed1;
        if (grow) capacity *= 2;
        for (int attempt = 1; attempt <= MAX_REHASH_ATTEMPTS; attempt++) {
            if (attempt % 5 == 0) capacity *= 2; // keep failing? more room makes cycles rarer
            newSeeds();
            allocate();
            boolean ok = true;
            for (Object[] e : entries) {
                if (place(e[0], e[1], false) != null) { ok = false; break; }
            }
            if (ok) return;
        }
        keys = oldKeys;
        values = oldValues;
        capacity = oldCapacity;
        seed0 = oldSeed0;
        seed1 = oldSeed1;
        throw new IllegalStateException("Cuckoo rehash failed " + MAX_REHASH_ATTEMPTS
                + " times - too many keys share the same hashCode? (map left unchanged)");
    }

    // ---------------- utilities ----------------

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /** Slots per table (total slots = 2 x capacity). */
    public int capacity() {
        return capacity;
    }

    public void clear() {
        allocate();
        size = 0;
    }

    /** Iterates keys: table 0 first, then table 1 (no particular order). */
    @Override
    public Iterator<K> iterator() {
        return new Iterator<K>() {
            private int table = 0, index = -1;
            { advance(); }

            private void advance() {
                index++;
                while (table < 2) {
                    while (index < keys[table].length && keys[table][index] == null) index++;
                    if (index < keys[table].length) return;
                    table++;
                    index = 0;
                }
            }

            @Override
            public boolean hasNext() {
                return table < 2;
            }

            @Override
            @SuppressWarnings("unchecked")
            public K next() {
                if (table >= 2) throw new NoSuchElementException();
                K k = (K) keys[table][index];
                advance();
                return k;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (K k : this) {
            if (sb.length() > 1) sb.append(", ");
            sb.append(k).append('=').append(get(k));
        }
        return sb.append("}").toString();
    }

    public static void main(String[] args) {
        System.out.println("--- CUCKOO HASH MAP DEMO ---");
        CuckooHashMap<String, Integer> stock = new CuckooHashMap<>(4);
        String[] items = {"apple", "banana", "cherry", "date", "elderberry", "fig", "grape", "honeydew", "kiwi", "lemon"};
        for (int i = 0; i < items.length; i++) stock.put(items[i], (i + 1) * 10);
        stock.put("banana", 999); // update
        System.out.println("size = " + stock.size() + ", capacity per table = " + stock.capacity());
        System.out.println("get(banana) = " + stock.get("banana") + ", get(kiwi) = " + stock.get("kiwi")
                + ", get(mango) = " + stock.get("mango"));
        stock.remove("cherry");
        System.out.println("After remove(cherry): containsKey = " + stock.containsKey("cherry") + ", size = " + stock.size());

        // 100,000 integer keys: every lookup still checks at most 2 slots
        CuckooHashMap<Integer, Integer> big = new CuckooHashMap<>();
        for (int i = 0; i < 100_000; i++) big.put(i * 7919, i);
        boolean all = true;
        for (int i = 0; i < 100_000; i++) all &= big.get(i * 7919) == i;
        System.out.println("100,000 keys inserted, all found: " + all + " (capacity per table " + big.capacity() + ")");
    }
}
