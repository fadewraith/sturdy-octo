package db.wal;

import java.util.*;

/**
 * WHAT IT IS:
 * Write-Ahead Logging (WAL) is a standard method for ensuring data integrity. Before making a permanent change to the database, a record of the change is appended to the log.
 * 
 * STRATEGY:
 * - When a transaction modifies data, log the modification first.
 * - If a crash occurs before the data is persisted to main storage, the log can be replayed.
 * 
 * TIME/SPACE COMPLEXITY:
 * - Time: O(1) for appending to log.
 * - Space: O(L) where L is the number of logged operations before checkpoint.
 * 
 * REAL-WORLD ANALOGY / USE CASE:
 * Keeping a diary of what you plan to do before you actually do it, so if you are interrupted, you know exactly where you left off. Used in Postgres, SQLite.
 * 
 * WHEN TO USE / COMBINATION:
 * Use for durability (the D in ACID) and crash recovery.
 * 
 * PSEUDOCODE:
 * write(data):
 *   wal.append(create_log_entry(data))
 *   wal.flush()
 *   db.apply(data)
 */
public class WriteAheadLog {

    static class LogEntry {
        long txId;
        String operation;
        String key;
        String value;

        LogEntry(long txId, String operation, String key, String value) {
            this.txId = txId;
            this.operation = operation;
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return txId + ":" + operation + ":" + key + ":" + value;
        }
    }

    private List<LogEntry> wal = new ArrayList<>();
    private Map<String, String> dataStore = new HashMap<>();

    public void executeTx(long txId, String key, String value) {
        // 1. Write to WAL first
        wal.add(new LogEntry(txId, "WRITE", key, value));
        System.out.println("WAL Appended: " + txId + " WRITE " + key + "=" + value);
        
        // 2. Simulate crash chance here
        
        // 3. Apply to datastore
        dataStore.put(key, value);
        System.out.println("DataStore Updated: " + key + "=" + value);
    }

    public void simulateCrash() {
        System.out.println("CRASH! Memory DataStore cleared.");
        dataStore.clear();
    }

    public void recover() {
        System.out.println("Recovering from WAL...");
        for (LogEntry entry : wal) {
            if (entry.operation.equals("WRITE")) {
                dataStore.put(entry.key, entry.value);
            }
        }
        System.out.println("Recovery complete.");
    }

    public String read(String key) {
        return dataStore.get(key);
    }

    public static void main(String[] args) {
        System.out.println("=== Write-Ahead Log Tests ===");
        WriteAheadLog db = new WriteAheadLog();
        
        db.executeTx(1, "user:1", "Alice");
        db.executeTx(2, "user:2", "Bob");
        
        System.out.println("Read user:1 -> " + db.read("user:1"));
        
        db.simulateCrash();
        
        System.out.println("Read user:1 after crash -> " + db.read("user:1"));
        
        db.recover();
        
        System.out.println("Read user:1 after recovery -> " + db.read("user:1"));
        System.out.println("Read user:2 after recovery -> " + db.read("user:2"));
    }
}
