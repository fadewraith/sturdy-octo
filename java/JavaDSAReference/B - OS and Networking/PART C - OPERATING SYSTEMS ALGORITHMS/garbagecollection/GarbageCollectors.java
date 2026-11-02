package os.garbagecollection;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * GARBAGE COLLECTION ALGORITHMS (Reference Counting, Mark-Sweep, Copying/Cheney)
 *
 * WHAT IT IS:
 * Automatic memory management: find heap objects the program can no longer reach
 * and reclaim them. "Reachable" = reachable by following references from the
 * ROOTS (stack variables, globals, registers).
 *
 * SIMULATED HEAP:
 * The heap is an array of objects; an object's address is its index. Each object
 * has a fixed number of reference fields holding addresses (-1 = null).
 *
 * 1. REFERENCE COUNTING (CPython, Swift ARC, C++ shared_ptr)
 *    Every object counts how many references point at it. When a count drops to 0,
 *    free the object immediately and decrement everything it pointed to (which may
 *    cascade). + Memory is freed promptly, no pauses.
 *    - Every pointer write costs extra work. - CYCLES LEAK: A -> B -> A keeps both
 *    counts at 1 even when nothing else can reach them (CPython adds a separate
 *    cycle detector for this).
 *
 * 2. MARK-SWEEP (the basis of most tracing GCs; Go's GC, CMS, Ruby)
 *    MARK: from the roots, follow every reference (DFS with an explicit stack, no
 *    recursion) and mark what you reach. SWEEP: free every unmarked object.
 *    + Handles cycles. + Objects never move.  - Pause proportional to the heap;
 *    - free memory gets FRAGMENTED into holes.
 *
 * 3. COPYING COLLECTOR - CHENEY'S ALGORITHM (young generation in JVM/.NET/V8)
 *    Two halves: FROM-space and TO-space. Copy the roots' objects to TO-space, then
 *    scan TO-space left to right (a BFS queue that lives inside the heap itself!),
 *    copying every object each scanned object points to. A FORWARDING ADDRESS left
 *    in each copied object prevents copying it twice and lets references be
 *    rewritten. Then FROM-space is discarded wholesale.
 *    + Work is proportional to LIVE objects only (garbage is never touched).
 *    + Result is COMPACT - allocation becomes a pointer bump.  - Needs 2x memory.
 *
 * GENERATIONAL GC (how they combine in practice): most objects die young, so new
 * objects go to a small young space collected often by copying; survivors are
 * promoted to an old space collected rarely by mark-sweep(-compact).
 *
 * COMPLEXITY:
 * Reference counting: O(1) per pointer write (plus cascading frees).
 * Mark-sweep: O(live) mark + O(heap) sweep.  Copying: O(live), 2x space.
 */
public class GarbageCollectors {

    public static class Obj {
        public final String name;
        public final int[] fields; // addresses, -1 = null
        public Obj(String name, int numFields) {
            this.name = name;
            this.fields = new int[numFields];
            Arrays.fill(fields, -1);
        }
        Obj copy() { Obj o = new Obj(name, fields.length); System.arraycopy(fields, 0, o.fields, 0, fields.length); return o; }
    }

    // ======================= 1. Reference counting =======================
    public static class RefCountHeap {
        private final List<Obj> heap = new ArrayList<>();
        private final List<Integer> refCount = new ArrayList<>();
        private final List<Boolean> freed = new ArrayList<>();
        private int freedCount = 0;

        /** Allocates a new object held by one root reference (count 1). */
        public int allocate(String name, int fields) {
            heap.add(new Obj(name, fields));
            refCount.add(1);
            freed.add(false);
            return heap.size() - 1;
        }

        public void addRoot(int addr) { refCount.set(addr, refCount.get(addr) + 1); }

        public void dropRoot(int addr) { decrement(addr); }

        /** obj.fields[field] = target (target may be -1). */
        public void setField(int obj, int field, int target) {
            if (target >= 0) refCount.set(target, refCount.get(target) + 1); // increment FIRST (self-assignment safe)
            int old = heap.get(obj).fields[field];
            heap.get(obj).fields[field] = target;
            if (old >= 0) decrement(old);
        }

        private void decrement(int addr) {
            ArrayDeque<Integer> work = new ArrayDeque<>(); // explicit stack: long chains don't overflow
            work.push(addr);
            while (!work.isEmpty()) {
                int a = work.pop();
                int c = refCount.get(a) - 1;
                refCount.set(a, c);
                if (c == 0) {                       // unreachable: free it, release its references
                    freed.set(a, true);
                    freedCount++;
                    for (int f : heap.get(a).fields) if (f >= 0) work.push(f);
                }
            }
        }

        public boolean isFreed(int addr) { return freed.get(addr); }
        public int freedCount() { return freedCount; }
        public int liveCount() { return heap.size() - freedCount; }
    }

    // ======================= 2. Mark-sweep =======================
    /** Frees (sets to null) every object not reachable from the roots. Returns how many were freed. */
    public static int markSweep(Obj[] heap, int[] roots) {
        boolean[] marked = new boolean[heap.length];
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        for (int r : roots) if (r >= 0 && !marked[r]) { marked[r] = true; stack.push(r); }
        while (!stack.isEmpty()) {                          // MARK
            int a = stack.pop();
            for (int f : heap[a].fields) {
                if (f >= 0 && !marked[f]) { marked[f] = true; stack.push(f); }
            }
        }
        int freed = 0;
        for (int a = 0; a < heap.length; a++) {             // SWEEP
            if (heap[a] != null && !marked[a]) { heap[a] = null; freed++; }
        }
        return freed;
    }

    // ======================= 3. Copying (Cheney) =======================
    public static class CopyResult {
        public final Obj[] toSpace;  // live objects packed at 0..liveCount-1
        public final int liveCount;
        public final int[] roots;    // roots rewritten to the new addresses
        CopyResult(Obj[] toSpace, int liveCount, int[] roots) { this.toSpace = toSpace; this.liveCount = liveCount; this.roots = roots; }
    }

    public static CopyResult cheneyCopy(Obj[] fromSpace, int[] roots) {
        Obj[] toSpace = new Obj[fromSpace.length];
        int[] forward = new int[fromSpace.length];         // forwarding addresses, -1 = not copied yet
        Arrays.fill(forward, -1);
        int free = 0;                                       // next free slot in to-space (the "alloc" pointer)

        int[] newRoots = new int[roots.length];
        for (int i = 0; i < roots.length; i++) {
            int r = roots[i];
            if (r < 0) { newRoots[i] = -1; continue; }
            if (forward[r] < 0) { toSpace[free] = fromSpace[r].copy(); forward[r] = free++; }
            newRoots[i] = forward[r];
        }

        int scan = 0;                                       // everything before `scan` is fully processed
        while (scan < free) {
            Obj obj = toSpace[scan];
            for (int f = 0; f < obj.fields.length; f++) {
                int old = obj.fields[f];
                if (old < 0) continue;
                if (forward[old] < 0) {                     // not copied yet: copy to the end of to-space
                    toSpace[free] = fromSpace[old].copy();
                    forward[old] = free++;
                }
                obj.fields[f] = forward[old];               // rewrite the reference
            }
            scan++;
        }
        return new CopyResult(toSpace, free, newRoots);
    }

    public static void main(String[] args) {
        System.out.println("--- GARBAGE COLLECTION DEMO ---");

        // Reference counting: a chain is freed promptly, a cycle leaks
        RefCountHeap rc = new RefCountHeap();
        int a = rc.allocate("A", 1), b = rc.allocate("B", 1), c = rc.allocate("C", 1);
        rc.setField(a, 0, b);            // A -> B
        rc.dropRoot(b);                  // B only reachable through A now
        rc.dropRoot(a);                  // drop A: A and B are freed in a cascade
        System.out.println("RefCount chain A->B dropped: freed " + rc.freedCount() + " objects");      // 2
        int x = rc.allocate("X", 1), y = rc.allocate("Y", 1);
        rc.setField(x, 0, y);
        rc.setField(y, 0, x);            // X <-> Y cycle
        rc.dropRoot(x);
        rc.dropRoot(y);
        System.out.println("RefCount cycle X<->Y dropped: X freed? " + rc.isFreed(x) + ", Y freed? " + rc.isFreed(y)
                + "  <- leak (C is still a root: " + !rc.isFreed(c) + ")");

        // Tracing: heap with a live part, a garbage chain and a garbage cycle
        //   root -> 0 -> 2 -> 4 ;  1 -> 3 (garbage) ; 5 <-> 6 (garbage cycle)
        Obj[] heap = new Obj[7];
        for (int i = 0; i < 7; i++) heap[i] = new Obj("obj" + i, 2);
        heap[0].fields[0] = 2; heap[2].fields[0] = 4; heap[2].fields[1] = 0;
        heap[1].fields[0] = 3;
        heap[5].fields[0] = 6; heap[6].fields[0] = 5;
        int[] roots = {0};

        Obj[] heapForMS = new Obj[7];
        for (int i = 0; i < 7; i++) heapForMS[i] = heap[i].copy();
        int freed = markSweep(heapForMS, roots);
        StringBuilder holes = new StringBuilder();
        for (Obj o : heapForMS) holes.append(o == null ? "_ " : o.name.substring(3) + " ");
        System.out.println("\nMark-sweep freed " + freed + " (cycle included); heap now: " + holes + " <- fragmented");

        CopyResult cr = cheneyCopy(heap, roots);
        StringBuilder packed = new StringBuilder();
        for (int i = 0; i < cr.liveCount; i++) packed.append(cr.toSpace[i].name).append(Arrays.toString(cr.toSpace[i].fields)).append(" ");
        System.out.println("Cheney copied " + cr.liveCount + " live objects, compacted: " + packed);
        // obj0[1, -1] obj2[2, 0] obj4[-1, -1]  (addresses rewritten)
    }
}
