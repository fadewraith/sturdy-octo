package db.bufferpool;

import java.util.HashMap;
import java.util.Map;

/**
 * BUFFER POOL MANAGER (page cache with pin counts, dirty pages and CLOCK eviction)
 *
 * WHAT IT IS:
 * A database can't keep all its pages in RAM, so it keeps a fixed number of FRAMES
 * and loads disk pages into them on demand. The buffer pool decides which page to
 * EVICT when it needs a frame, and writes modified ("dirty") pages back to disk
 * before evicting them.
 *
 * WHEN TO USE THIS:
 * - Every disk-based database: PostgreSQL shared_buffers (clock sweep), MySQL InnoDB
 *   buffer pool (LRU with a young/old split), SQL Server, SQLite's page cache.
 * - Databases manage this themselves instead of trusting the OS page cache because
 *   they know the access pattern, must control WHEN dirty pages hit disk (for the
 *   WAL rule - see WriteAheadLog.java), and must never evict a page in use.
 *
 * KEY IDEAS:
 * - PAGE TABLE: pageId -> frame index (a hash map).
 * - PIN COUNT: a page being used by a query is PINNED and can't be evicted;
 *   callers must unpin it when done (and say whether they modified it).
 * - DIRTY FLAG: set on unpin(dirty = true); a dirty page is written to disk
 *   before its frame is reused, or on flush.
 * - CLOCK (second chance) replacement: frames in a circle with a REFERENCE bit set
 *   on every access. The clock hand sweeps: a frame with ref bit 1 gets a second
 *   chance (bit cleared, move on); the first unpinned frame with ref bit 0 is the
 *   victim. Approximates LRU with O(1) work per access (no list reordering).
 *   (ClockSecondChance.java in Section B shows the same idea for OS paging.)
 *
 * COMPLEXITY:
 * fetch hit: O(1). fetch miss: O(frames) worst case for the clock sweep, O(1)
 * amortized. Space: O(frames * pageSize).
 */
public class BufferPool {

    /** The simulated disk: page id -> page contents. */
    public static class Disk {
        private final Map<Integer, String> pages = new HashMap<>();
        public int reads = 0, writes = 0;
        public String read(int pageId) { reads++; return pages.getOrDefault(pageId, ""); }
        public void write(int pageId, String data) { writes++; pages.put(pageId, data); }
        public String peek(int pageId) { return pages.getOrDefault(pageId, ""); }
    }

    private static class Frame {
        int pageId = -1;          // -1 = empty
        String data;
        int pinCount = 0;
        boolean dirty = false;
        boolean referenced = false;
    }

    private final Frame[] frames;
    private final Map<Integer, Integer> pageTable = new HashMap<>();
    private final Disk disk;
    private int clockHand = 0;
    private int hits = 0, misses = 0;

    public BufferPool(int numFrames, Disk disk) {
        frames = new Frame[numFrames];
        for (int i = 0; i < numFrames; i++) frames[i] = new Frame();
        this.disk = disk;
    }

    /** Pins the page in memory and returns its contents. Throws if every frame is pinned. */
    public String fetchPage(int pageId) {
        Integer f = pageTable.get(pageId);
        if (f != null) {                                   // HIT
            hits++;
            Frame fr = frames[f];
            fr.pinCount++;
            fr.referenced = true;
            return fr.data;
        }
        misses++;                                          // MISS: find a frame and load from disk
        int victim = findVictim();
        Frame fr = frames[victim];
        if (fr.pageId != -1) {
            if (fr.dirty) disk.write(fr.pageId, fr.data);  // write back before reuse
            pageTable.remove(fr.pageId);
        }
        fr.pageId = pageId;
        fr.data = disk.read(pageId);
        fr.pinCount = 1;
        fr.dirty = false;
        fr.referenced = true;
        pageTable.put(pageId, victim);
        return fr.data;
    }

    /** Replaces the contents of a page the caller has pinned. */
    public void writePage(int pageId, String data) {
        Integer f = pageTable.get(pageId);
        if (f == null || frames[f].pinCount == 0) throw new IllegalStateException("page " + pageId + " must be pinned to modify it");
        frames[f].data = data;
        frames[f].dirty = true;
    }

    public void unpinPage(int pageId, boolean dirty) {
        Integer f = pageTable.get(pageId);
        if (f == null || frames[f].pinCount == 0) throw new IllegalStateException("page " + pageId + " is not pinned");
        frames[f].pinCount--;
        if (dirty) frames[f].dirty = true;
    }

    private int findVictim() {
        for (int i = 0; i < frames.length; i++) if (frames[i].pageId == -1) return i; // free frame first
        // CLOCK sweep: at most two full turns (first turn may only clear reference bits)
        for (int step = 0; step < 2 * frames.length; step++) {
            Frame fr = frames[clockHand];
            int candidate = clockHand;
            clockHand = (clockHand + 1) % frames.length;
            if (fr.pinCount > 0) continue;                 // in use: never evict
            if (fr.referenced) { fr.referenced = false; continue; } // second chance
            return candidate;
        }
        throw new IllegalStateException("all frames are pinned - buffer pool exhausted");
    }

    /** Writes every dirty page to disk (e.g. at a checkpoint). */
    public void flushAll() {
        for (Frame fr : frames) {
            if (fr.pageId != -1 && fr.dirty) { disk.write(fr.pageId, fr.data); fr.dirty = false; }
        }
    }

    public boolean isCached(int pageId) { return pageTable.containsKey(pageId); }
    public int hits() { return hits; }
    public int misses() { return misses; }

    public static void main(String[] args) {
        System.out.println("--- BUFFER POOL DEMO ---");
        Disk disk = new Disk();
        for (int p = 0; p < 10; p++) disk.write(p, "page-" + p);
        disk.writes = 0;

        BufferPool pool = new BufferPool(3, disk);
        pool.fetchPage(1); pool.unpinPage(1, false);
        pool.fetchPage(2); pool.writePage(2, "page-2 (updated)"); pool.unpinPage(2, true);
        pool.fetchPage(3); pool.unpinPage(3, false);
        pool.fetchPage(1); pool.unpinPage(1, false);       // hit, sets page 1's reference bit again
        pool.fetchPage(4); pool.unpinPage(4, false);       // miss: clock evicts a victim
        pool.fetchPage(5); pool.unpinPage(5, false);
        System.out.println("hits " + pool.hits() + ", misses " + pool.misses() + ", disk writes " + disk.writes
                + " (dirty page 2 written back when evicted: " + disk.peek(2) + ")");

        pool.fetchPage(7);
        pool.fetchPage(8);
        pool.fetchPage(9);                                  // three pages pinned at once
        try {
            pool.fetchPage(6);
        } catch (IllegalStateException e) {
            System.out.println("Fetching a 4th page with 3 pinned: " + e.getMessage());
        }
    }
}
