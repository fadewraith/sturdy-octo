package db.lsmtree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * LSM TREE (Log-Structured Merge Tree)
 *
 * WHAT IT IS:
 * A key-value store designed for FAST WRITES. Writes go to an in-memory sorted
 * MEMTABLE; when it fills up it is flushed to disk as an immutable sorted file
 * (SSTABLE, "sorted string table"). Disk writes are always big sequential appends,
 * never in-place updates.
 *
 * WHEN TO USE THIS:
 * - LevelDB, RocksDB, Cassandra, HBase, ScyllaDB, InfluxDB, the storage engines
 *   behind most write-heavy systems (logs, metrics, messaging, time series).
 * - Compare B+ tree (BPlusTree.java): faster reads, but every write updates a page
 *   in place (random I/O). LSM trades some read cost for much cheaper writes.
 *
 * OPERATIONS:
 * put(k, v):   write to the memtable (a real system first appends to a WAL - see
 *              WriteAheadLog.java - so the memtable survives a crash).
 * delete(k):   write a TOMBSTONE (a special "deleted" marker). Data in older
 *              SSTables can't be modified, so deletion is just a newer record.
 * get(k):      check the memtable, then SSTables from NEWEST to OLDEST; the first
 *              record found wins (a tombstone means "not found").
 *              Real systems keep a Bloom filter per SSTable to skip most files,
 *              and a sparse index to binary-search inside each one.
 * scan(lo, hi): k-way merge of the memtable and all SSTables, newest version wins.
 * COMPACTION:  merge SSTables into one, keeping only the newest version of each
 *              key and dropping tombstones once nothing older can be shadowed.
 *              Without it reads get slower (more files) and space is wasted.
 *              Here: size-tiered - when there are `maxTables` tables, merge them all.
 *
 * AMPLIFICATION TRADE-OFFS (the key LSM vocabulary):
 * write amplification (bytes rewritten by compaction), read amplification (files
 * checked per read), space amplification (stale versions kept until compaction).
 *
 * COMPLEXITY:
 * put/delete: O(log M) for memtable size M (flush O(M) amortized).
 * get: O(log M + T log S) for T tables of size S (binary search in each).
 * compaction: O(total size) per merge.
 */
public class LSMTree {

    private static final String TOMBSTONE = "\u0000<deleted>\u0000";

    /** Immutable sorted run: parallel arrays of keys and values (values may be TOMBSTONE). */
    static final class SSTable {
        final String[] keys, values;
        SSTable(String[] keys, String[] values) { this.keys = keys; this.values = values; }

        /** Binary search; null = key not in this table. */
        String find(String key) {
            int i = java.util.Arrays.binarySearch(keys, key);
            return i >= 0 ? values[i] : null;
        }
    }

    private TreeMap<String, String> memtable = new TreeMap<>();
    private final List<SSTable> tables = new ArrayList<>(); // index 0 = OLDEST
    private final int memtableLimit, maxTables;
    private int flushes = 0, compactions = 0;
    private long tableLookups = 0;

    public LSMTree(int memtableLimit, int maxTables) {
        if (memtableLimit < 1 || maxTables < 2) throw new IllegalArgumentException("memtableLimit >= 1, maxTables >= 2");
        this.memtableLimit = memtableLimit;
        this.maxTables = maxTables;
    }

    public void put(String key, String value) {
        if (value == null || value.equals(TOMBSTONE)) throw new IllegalArgumentException("invalid value");
        write(key, value);
    }

    public void delete(String key) { write(key, TOMBSTONE); }

    private void write(String key, String value) {
        memtable.put(key, value);
        if (memtable.size() >= memtableLimit) flush();
    }

    /** Writes the memtable out as a new (newest) SSTable. */
    public void flush() {
        if (memtable.isEmpty()) return;
        String[] k = memtable.keySet().toArray(new String[0]);
        String[] v = memtable.values().toArray(new String[0]);
        tables.add(new SSTable(k, v));
        memtable = new TreeMap<>();
        flushes++;
        if (tables.size() >= maxTables) compact();
    }

    /** Merges all SSTables into one: newest version wins, tombstones are dropped (nothing older remains). */
    public void compact() {
        if (tables.size() < 2) return;
        TreeMap<String, String> merged = new TreeMap<>();
        for (SSTable t : tables) {                          // oldest to newest: newer overwrites older
            for (int i = 0; i < t.keys.length; i++) merged.put(t.keys[i], t.values[i]);
        }
        merged.values().removeIf(TOMBSTONE::equals);        // safe: this is the oldest level now
        tables.clear();
        if (!merged.isEmpty()) tables.add(new SSTable(merged.keySet().toArray(new String[0]), merged.values().toArray(new String[0])));
        compactions++;
    }

    public String get(String key) {
        String v = memtable.get(key);
        if (v == null) {
            for (int i = tables.size() - 1; i >= 0 && v == null; i--) {   // newest first
                tableLookups++;
                v = tables.get(i).find(key);
            }
        }
        return (v == null || v.equals(TOMBSTONE)) ? null : v;
    }

    /** All live key-value pairs with lo <= key < hi, in key order. */
    public List<Map.Entry<String, String>> scan(String lo, String hi) {
        TreeMap<String, String> view = new TreeMap<>();
        for (SSTable t : tables) {                          // oldest first, newer overwrite
            int from = java.util.Arrays.binarySearch(t.keys, lo);
            if (from < 0) from = -from - 1;
            for (int i = from; i < t.keys.length && t.keys[i].compareTo(hi) < 0; i++) view.put(t.keys[i], t.values[i]);
        }
        view.putAll(memtable.subMap(lo, hi));               // memtable is newest of all
        List<Map.Entry<String, String>> out = new ArrayList<>();
        for (Map.Entry<String, String> e : view.entrySet()) if (!e.getValue().equals(TOMBSTONE)) out.add(Map.entry(e.getKey(), e.getValue()));
        return Collections.unmodifiableList(out);
    }

    public int tableCount() { return tables.size(); }
    public int flushes() { return flushes; }
    public int compactions() { return compactions; }
    public long tableLookups() { return tableLookups; }

    public static void main(String[] args) {
        System.out.println("--- LSM TREE DEMO ---");
        LSMTree db = new LSMTree(4, 3);
        db.put("apple", "1");
        db.put("banana", "2");
        db.put("cherry", "3");
        db.put("date", "4");                 // memtable full -> flushed to SSTable #1
        db.put("apple", "100");              // newer version in the memtable
        db.delete("banana");                 // tombstone
        System.out.println("SSTables: " + db.tableCount() + ", apple = " + db.get("apple") + ", banana = " + db.get("banana")
                + ", cherry = " + db.get("cherry"));   // 1, 100, null, 3

        for (int i = 0; i < 20; i++) db.put("key" + i, "v" + i);
        System.out.println("After 20 more puts: " + db.flushes() + " flushes, " + db.compactions() + " compactions, "
                + db.tableCount() + " SSTables on disk");
        System.out.println("scan [cherry, key12): " + db.scan("cherry", "key12"));
        System.out.println("banana after compaction (tombstone applied): " + db.get("banana"));
    }
}
