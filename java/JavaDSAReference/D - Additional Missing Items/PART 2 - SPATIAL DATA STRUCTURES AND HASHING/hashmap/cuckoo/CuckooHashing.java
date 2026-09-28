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

    private int[][] tables; // 2 tables, storing integer keys
    private int capacity;
    private static final int MAX_LOOPS = 10;
    
    // Using prime numbers for the two hash functions
    private int hash1Prime = 11;
    private int hash2Prime = 17;

    public CuckooHashing(int capacity) {
        this.capacity = capacity;
        tables = new int[2][capacity];
        Arrays.fill(tables[0], -1); // -1 signifies empty
        Arrays.fill(tables[1], -1);
    }

    private int hash(int key, int functionId) {
        if (functionId == 1) {
            return (key * hash1Prime) % capacity;
        } else {
            return (key * hash2Prime) % capacity;
        }
    }

    public void put(int key) {
        if (containsKey(key)) return;

        int currentKey = key;
        int currentTable = 0; // Start with table 0

        for (int i = 0; i < MAX_LOOPS; i++) {
            int pos = hash(currentKey, currentTable + 1);

            if (tables[currentTable][pos] == -1) {
                tables[currentTable][pos] = currentKey;
                return; // Inserted successfully!
            }

            // KICK OUT the existing element
            int displacedKey = tables[currentTable][pos];
            tables[currentTable][pos] = currentKey;
            
            // Prepare to insert the displaced element into the OTHER table
            currentKey = displacedKey;
            currentTable = 1 - currentTable; // Toggle between 0 and 1
        }

        // If we reach here, a cycle was detected! 
        System.out.println("Cycle detected for key " + key + "! Rehash needed (Not fully implemented in demo).");
    }

    public boolean containsKey(int key) {
        return tables[0][hash(key, 1)] == key || tables[1][hash(key, 2)] == key;
    }

    public void remove(int key) {
        if (tables[0][hash(key, 1)] == key) {
            tables[0][hash(key, 1)] = -1;
        } else if (tables[1][hash(key, 2)] == key) {
            tables[1][hash(key, 2)] = -1;
        }
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
    }
}
