package hashmap.cuckoo;

import java.util.Arrays;

/**
 * CUCKOO HASHING
 * 
 * WHAT IT IS:
 * An alternate collision-resolution strategy to chaining. It uses two hash functions 
 * and two tables. On collision, the existing entry is "kicked out" (displaced) to 
 * its alternate location in the other table, recursively, until an empty spot is found.
 * 
 * TRADE-OFFS VS CHAINING:
 * - Cuckoo Hashing guarantees O(1) WORST-CASE lookup time, because an element is 
 *   ALWAYS in one of exactly two locations!
 * - Chaining gives O(1) average lookup, but O(N) worst-case lookup if all elements 
 *   collide into a single bucket.
 * 
 * WHEN TO USE THIS:
 * - Lookup-heavy workloads where worst-case guarantees matter more than insertion 
 *   speed (insertion can trigger cascading displacements or a full rehash).
 * 
 * PSEUDOCODE (Insert):
 * 1. hash1(key) -> index1. If empty, place there.
 * 2. If occupied, KICK OUT the existing element. Place new element.
 * 3. Take kicked-out element. hash2(key) -> index2. If empty, place there.
 * 4. If occupied, KICK OUT again. Repeat until max_loops is reached (cycle detected).
 * 5. If cycle detected, rehash entire table with new hash functions!
 */
public class CuckooHashing {

    // 2 tables. null = empty slot, so EVERY int (including -1) can be stored.
    private Integer[][] tables;
    private int capacity;
    private int size = 0;
    private int maxLoops;

    // Seeds for the two hash functions. A rehash picks new random seeds,
    // which gives "new hash functions" and breaks the displacement cycle.
    private int seed1, seed2;
    private final java.util.Random random = new java.util.Random(42);

    public CuckooHashing(int capacity) {
        this.capacity = Math.max(1, capacity);
        this.tables = new Integer[2][this.capacity];
        this.seed1 = 0x9E3779B9;
        this.seed2 = 0x85EBCA6B;
        updateMaxLoops();
    }

    // Common choice: about log2(capacity) kicks before declaring a cycle
    private void updateMaxLoops() {
        maxLoops = Math.max(8, 3 * (32 - Integer.numberOfLeadingZeros(capacity)));
    }

    private int hash(int key, int functionId) {
        int h = key * (functionId == 1 ? seed1 : seed2);
        h ^= (h >>> 16);                     // mix high bits into low bits
        return Math.floorMod(h, capacity);   // floorMod: never negative, even for negative keys
    }

    public void put(int key) {
        if (containsKey(key)) return;

        Integer pending = tryInsert(key);
        // A non-null result means the key that was kicked out last has no home:
        // grow + pick new hash functions, then re-insert everything (nothing is lost).
        if (pending != null) {
            System.out.println("Cycle detected while inserting " + key + " -> rehashing");
            rehashAndInsert(pending);
        }
        size++;
    }

    /** Returns null on success, or the key left homeless after maxLoops kicks. */
    private Integer tryInsert(int key) {
        int currentKey = key;
        int currentTable = 0; // Start with table 0

        for (int i = 0; i < maxLoops; i++) {
            int pos = hash(currentKey, currentTable + 1);

            if (tables[currentTable][pos] == null) {
                tables[currentTable][pos] = currentKey;
                return null; // Inserted successfully!
            }

            // KICK OUT the existing element
            int displacedKey = tables[currentTable][pos];
            tables[currentTable][pos] = currentKey;

            // Prepare to insert the displaced element into the OTHER table
            currentKey = displacedKey;
            currentTable = 1 - currentTable; // Toggle between 0 and 1
        }
        return currentKey;
    }

    /**
     * Doubles the table, picks new seeds and re-inserts ALL keys plus the homeless one.
     * We first copy every key into a list, so if this attempt also hits a cycle
     * we can simply start over with the full list - no key can get lost.
     */
    private void rehashAndInsert(int homelessKey) {
        int[] allKeys = new int[size + 1];
        int count = 0;
        for (Integer[] table : tables) {
            for (Integer k : table) {
                if (k != null) allKeys[count++] = k;
            }
        }
        allKeys[count++] = homelessKey;

        boolean ok = false;
        while (!ok) {
            capacity *= 2;
            updateMaxLoops();
            seed1 = random.nextInt() | 1; // odd multipliers spread bits better
            seed2 = random.nextInt() | 1;
            tables = new Integer[2][capacity];

            ok = true;
            for (int i = 0; i < count && ok; i++) {
                if (tryInsert(allKeys[i]) != null) ok = false; // cycle again -> retry bigger
            }
        }
    }

    public boolean containsKey(int key) {
        Integer a = tables[0][hash(key, 1)], b = tables[1][hash(key, 2)];
        return (a != null && a == key) || (b != null && b == key);
    }

    public void remove(int key) {
        int p1 = hash(key, 1), p2 = hash(key, 2);
        if (tables[0][p1] != null && tables[0][p1] == key) {
            tables[0][p1] = null;
            size--;
        } else if (tables[1][p2] != null && tables[1][p2] == key) {
            tables[1][p2] = null;
            size--;
        }
    }

    public int size() {
        return size;
    }

    public void printTables() {
        System.out.println("Table 1: " + Arrays.toString(tables[0]));
        System.out.println("Table 2: " + Arrays.toString(tables[1]));
    }

    public static void main(String[] args) {
        System.out.println("--- CUCKOO HASHING DEMO ---");
        CuckooHashing ch = new CuckooHashing(5);
        
        ch.put(20);
        ch.put(50);
        ch.put(53);
        ch.put(75);
        
        ch.printTables();
        
        System.out.println("Contains 50? " + ch.containsKey(50));
        System.out.println("Contains 10? " + ch.containsKey(10));
        
        System.out.println("Removing 50...");
        ch.remove(50);
        System.out.println("Contains 50? " + ch.containsKey(50));

        // Fill well past the starting capacity: rehashes happen, nothing is lost
        CuckooHashing big = new CuckooHashing(4);
        for (int k = -1; k < 40; k++) big.put(k);
        boolean all = true;
        for (int k = -1; k < 40; k++) all &= big.containsKey(k);
        System.out.println("Inserted 41 keys (including -1), all still present? " + all + ", size=" + big.size());
    }
}
