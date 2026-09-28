package db.mvcc;

import java.util.*;

/**
 * WHAT IT IS:
 * Multi-Version Concurrency Control (MVCC) is a concurrency control method commonly used by database management systems to provide concurrent access to the database and in programming languages to implement transactional memory.
 * 
 * STRATEGY:
 * - Instead of overwriting a row, write a new version of it with a transaction ID or timestamp.
 * - Readers see a snapshot of the database at their start time, ignoring uncommitted changes or changes made by later transactions.
 * 
 * TIME/SPACE COMPLEXITY:
 * - Time: Reads O(V) where V is the number of versions of a row.
 * - Space: O(V * N) due to keeping multiple versions.
 * 
 * REAL-WORLD ANALOGY / USE CASE:
 * Like a version control system (Git) where readers can look at an older commit while a new commit is being drafted. Used in PostgreSQL, Oracle.
 * 
 * WHEN TO USE / COMBINATION:
 * Use when read performance is critical and you want to avoid locking readers by writers (and vice-versa).
 * 
 * PSEUDOCODE:
 * read(row_id, tx_id):
 *   for version in row.versions:
 *     if version.created_by <= tx_id and not version.is_deleted:
 *       return version.data
 */
public class MVCC {

    static class Version {
        long txId;
        String data;

        Version(long txId, String data) {
            this.txId = txId;
            this.data = data;
        }
    }

    static class Row {
        LinkedList<Version> versions = new LinkedList<>();

        void update(long txId, String data) {
            versions.addFirst(new Version(txId, data));
        }

        String read(long txId) {
            for (Version v : versions) {
                if (v.txId <= txId) {
                    return v.data;
                }
            }
            return null;
        }
    }

    private Map<String, Row> table = new HashMap<>();
    private long globalTxId = 0;

    public long beginTransaction() {
        return ++globalTxId;
    }

    public void write(long txId, String key, String data) {
        table.putIfAbsent(key, new Row());
        table.get(key).update(txId, data);
        System.out.println("Tx " + txId + " wrote " + key + "=" + data);
    }

    public String read(long txId, String key) {
        Row row = table.get(key);
        if (row == null) return null;
        return row.read(txId);
    }

    public static void main(String[] args) {
        System.out.println("=== MVCC Tests ===");
        MVCC db = new MVCC();
        
        long tx1 = db.beginTransaction();
        db.write(tx1, "item1", "Value_A");
        
        long tx2 = db.beginTransaction();
        System.out.println("Tx2 reads item1: " + db.read(tx2, "item1")); // Value_A
        
        db.write(tx2, "item1", "Value_B");
        
        long tx3 = db.beginTransaction();
        System.out.println("Tx3 reads item1: " + db.read(tx3, "item1")); // Value_B
        
        System.out.println("Tx1 STILL reads item1: " + db.read(tx1, "item1")); // Value_A (Snapshot isolation)
    }
}
