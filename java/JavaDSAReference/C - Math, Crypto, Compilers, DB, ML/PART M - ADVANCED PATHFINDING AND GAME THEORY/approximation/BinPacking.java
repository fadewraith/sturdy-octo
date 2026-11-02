package algorithms.approximation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

/**
 * BIN PACKING (Next Fit, First Fit, Best Fit, First Fit Decreasing)
 *
 * WHAT IT IS:
 * Pack items of given sizes into as few bins of capacity C as possible.
 * NP-hard, but simple greedy rules come provably close to the optimum (OPT).
 *
 * WHEN TO USE THIS:
 * - Scheduling VMs/containers onto servers (Kubernetes bin-packing scheduler),
 *   cutting stock (wood, steel, fabric), packing files onto disks, loading trucks,
 *   splitting ad breaks, memory allocation.
 *
 * ALGORITHMS (n items):
 * - NEXT FIT: keep ONE open bin; if the item doesn't fit, close it and open a new
 *   one. O(n), uses <= 2 OPT bins (two consecutive bins are always more than full).
 * - FIRST FIT: put the item into the FIRST bin where it fits, else open a new bin.
 *   Uses <= floor(1.7 OPT) bins.
 * - BEST FIT: put it into the FULLEST bin where it still fits (least space left).
 *   Same 1.7 bound; often slightly better in practice.
 * - FIRST FIT DECREASING: sort items largest first, then First Fit.
 *   Uses <= 11/9 OPT + 6/9 bins - big items placed first leave small ones to fill gaps.
 *   This is the one to use when all items are known up front (OFFLINE);
 *   NF/FF/BF also work ONLINE (items arriving one at a time).
 * - LOWER BOUND: OPT >= ceil(total size / C). Handy to judge a result without
 *   knowing OPT.
 *
 * COMPLEXITY:
 * Next Fit O(n). First/Best Fit O(n log n) with a balanced tree (a TreeMap of
 * remaining space is used for Best Fit; First Fit here scans bins, O(n * bins)).
 * FFD: O(n log n) sort + First Fit.
 */
public class BinPacking {

    /** Each result: binOf[i] = bin index of item i; bins = number of bins used. */
    public static final class Packing {
        public final int[] binOf;
        public final int bins;
        Packing(int[] binOf, int bins) { this.binOf = binOf; this.bins = bins; }
    }

    private static void check(int[] sizes, int capacity) {
        for (int s : sizes) if (s <= 0 || s > capacity) throw new IllegalArgumentException("every item must satisfy 0 < size <= capacity");
    }

    public static Packing nextFit(int[] sizes, int capacity) {
        check(sizes, capacity);
        int[] binOf = new int[sizes.length];
        int bins = 0, space = 0;
        for (int i = 0; i < sizes.length; i++) {
            if (bins == 0 || sizes[i] > space) { bins++; space = capacity; } // close the bin, open a new one
            space -= sizes[i];
            binOf[i] = bins - 1;
        }
        return new Packing(binOf, bins);
    }

    public static Packing firstFit(int[] sizes, int capacity) {
        check(sizes, capacity);
        int[] binOf = new int[sizes.length];
        List<Integer> space = new ArrayList<>();
        for (int i = 0; i < sizes.length; i++) {
            int b = 0;
            while (b < space.size() && space.get(b) < sizes[i]) b++;    // first bin with room
            if (b == space.size()) space.add(capacity);
            space.set(b, space.get(b) - sizes[i]);
            binOf[i] = b;
        }
        return new Packing(binOf, space.size());
    }

    public static Packing bestFit(int[] sizes, int capacity) {
        check(sizes, capacity);
        int[] binOf = new int[sizes.length];
        // remaining space -> bins with exactly that much room (a multiset)
        TreeMap<Integer, List<Integer>> byRoom = new TreeMap<>();
        int bins = 0;
        for (int i = 0; i < sizes.length; i++) {
            Integer room = byRoom.ceilingKey(sizes[i]);                 // tightest bin that still fits
            int b;
            if (room == null) { b = bins++; room = capacity; }
            else {
                List<Integer> list = byRoom.get(room);
                b = list.remove(list.size() - 1);
                if (list.isEmpty()) byRoom.remove(room);
            }
            binOf[i] = b;
            byRoom.computeIfAbsent(room - sizes[i], k -> new ArrayList<>()).add(b);
        }
        return new Packing(binOf, bins);
    }

    public static Packing firstFitDecreasing(int[] sizes, int capacity) {
        Integer[] order = new Integer[sizes.length];
        for (int i = 0; i < order.length; i++) order[i] = i;
        Arrays.sort(order, (a, b) -> Integer.compare(sizes[b], sizes[a]));
        int[] sorted = new int[sizes.length];
        for (int i = 0; i < sizes.length; i++) sorted[i] = sizes[order[i]];
        Packing p = firstFit(sorted, capacity);
        int[] binOf = new int[sizes.length];
        for (int i = 0; i < sizes.length; i++) binOf[order[i]] = p.binOf[i]; // map back to the original order
        return new Packing(binOf, p.bins);
    }

    public static int lowerBound(int[] sizes, int capacity) {
        long total = 0;
        for (int s : sizes) total += s;
        return (int) ((total + capacity - 1) / capacity);
    }

    public static void main(String[] args) {
        System.out.println("--- BIN PACKING DEMO (capacity 10) ---");
        int[] items = {2, 5, 4, 7, 1, 3, 8};
        System.out.println("Items " + Arrays.toString(items) + ", lower bound " + lowerBound(items, 10) + " bins");
        System.out.println("Next Fit:              " + nextFit(items, 10).bins + " bins");
        System.out.println("First Fit:             " + firstFit(items, 10).bins + " bins");
        System.out.println("Best Fit:              " + bestFit(items, 10).bins + " bins");
        System.out.println("First Fit Decreasing:  " + firstFitDecreasing(items, 10).bins + " bins"); // 3 = optimal

        // A classic case where First Fit is fooled by the order but FFD is not
        // small items first: FF packs the 3s together, then every 7 needs its own bin
        int[] tricky = {3, 3, 3, 3, 3, 3, 7, 7, 7};
        System.out.println("\nSix 3s then three 7s: FF " + firstFit(tricky, 10).bins + " bins, FFD "
                + firstFitDecreasing(tricky, 10).bins + " bins, lower bound " + lowerBound(tricky, 10)
                + " (FFD pairs each 7 with a 3)"); // FF 5, FFD 4 = optimal
    }
}
