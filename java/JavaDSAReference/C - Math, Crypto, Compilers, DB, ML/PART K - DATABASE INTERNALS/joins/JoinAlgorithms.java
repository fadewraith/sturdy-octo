package db.joins;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JOIN ALGORITHMS (Nested Loop, Block Nested Loop, Sort-Merge, Hash, Grace Hash)
 *
 * WHAT IT IS:
 * How a database executes  SELECT ... FROM R JOIN S ON R.key = S.key.
 * Rows here are int[] with the join key in column 0. Every algorithm returns the
 * same set of (r, s) pairs; they differ in cost and in what they need.
 *
 * 1. NESTED LOOP JOIN - for each r in R, for each s in S: compare.
 *    O(|R| * |S|). Works for ANY condition (<, LIKE, ...), not just equality.
 *    With an INDEX on S.key it becomes "index nested loop": O(|R| log |S|) - what
 *    databases do when R is small (e.g. after a selective WHERE).
 *
 * 2. BLOCK NESTED LOOP - read R in blocks of B rows; scan S once per BLOCK instead
 *    of once per row. Same comparisons, but S is read |R|/B times instead of |R|:
 *    the I/O saving that matters when S lives on disk.
 *
 * 3. SORT-MERGE JOIN - sort both inputs by key, then walk them together like the
 *    merge step of merge sort. On equal keys, pair every r in the run with every s
 *    in the run. O(|R| log |R| + |S| log |S| + output); great when inputs are
 *    already sorted (index order) or the result must be sorted anyway.
 *
 * 4. HASH JOIN - BUILD a hash table on the smaller input, then PROBE it with each
 *    row of the larger one. O(|R| + |S| + output). The default for big equi-joins
 *    (PostgreSQL, Spark, every analytics engine). Needs memory for the table.
 *
 * 5. GRACE HASH JOIN - when the build side doesn't fit in memory: hash-PARTITION both
 *    inputs into k buckets (written to disk), so matching keys land in the same
 *    bucket number; then hash-join each pair of buckets, one at a time in memory.
 *
 * The query optimizer (JoinOrderOptimizer.java) picks the algorithm and join order
 * from table sizes and statistics.
 *
 * COMPLEXITY: see each section. Space: hash joins O(build side), sort-merge O(1)
 * extra beyond the sort, nested loops O(1) (block NL: O(B)).
 */
public class JoinAlgorithms {

    /** Joined pair: the left row followed by the right row. */
    private static int[] combine(int[] r, int[] s) {
        int[] out = Arrays.copyOf(r, r.length + s.length);
        System.arraycopy(s, 0, out, r.length, s.length);
        return out;
    }

    public static List<int[]> nestedLoopJoin(List<int[]> left, List<int[]> right) {
        List<int[]> out = new ArrayList<>();
        for (int[] r : left)
            for (int[] s : right)
                if (r[0] == s[0]) out.add(combine(r, s));
        return out;
    }

    /** scansOfRight[0] receives how many times the right input was scanned. */
    public static List<int[]> blockNestedLoopJoin(List<int[]> left, List<int[]> right, int blockSize, int[] scansOfRight) {
        List<int[]> out = new ArrayList<>();
        for (int b = 0; b < left.size(); b += blockSize) {
            List<int[]> block = left.subList(b, Math.min(left.size(), b + blockSize)); // one block "in memory"
            scansOfRight[0]++;
            for (int[] s : right)                                                     // one pass over S per block
                for (int[] r : block)
                    if (r[0] == s[0]) out.add(combine(r, s));
        }
        return out;
    }

    public static List<int[]> sortMergeJoin(List<int[]> left, List<int[]> right) {
        List<int[]> a = new ArrayList<>(left), b = new ArrayList<>(right);
        Comparator<int[]> byKey = Comparator.comparingInt(x -> x[0]);
        a.sort(byKey);
        b.sort(byKey);
        List<int[]> out = new ArrayList<>();
        int i = 0, j = 0;
        while (i < a.size() && j < b.size()) {
            int ka = a.get(i)[0], kb = b.get(j)[0];
            if (ka < kb) i++;
            else if (ka > kb) j++;
            else {
                int iEnd = i, jEnd = j;                       // the runs of equal keys on both sides
                while (iEnd < a.size() && a.get(iEnd)[0] == ka) iEnd++;
                while (jEnd < b.size() && b.get(jEnd)[0] == kb) jEnd++;
                for (int x = i; x < iEnd; x++)
                    for (int y = j; y < jEnd; y++) out.add(combine(a.get(x), b.get(y)));
                i = iEnd;
                j = jEnd;
            }
        }
        return out;
    }

    public static List<int[]> hashJoin(List<int[]> left, List<int[]> right) {
        // BUILD on the left input (pick the smaller side in practice), PROBE with the right
        Map<Integer, List<int[]>> table = new HashMap<>();
        for (int[] r : left) table.computeIfAbsent(r[0], k -> new ArrayList<>()).add(r);
        List<int[]> out = new ArrayList<>();
        for (int[] s : right) {
            List<int[]> matches = table.get(s[0]);
            if (matches != null) for (int[] r : matches) out.add(combine(r, s));
        }
        return out;
    }

    public static List<int[]> graceHashJoin(List<int[]> left, List<int[]> right, int partitions) {
        List<List<int[]>> lp = new ArrayList<>(), rp = new ArrayList<>();
        for (int p = 0; p < partitions; p++) { lp.add(new ArrayList<>()); rp.add(new ArrayList<>()); }
        // phase 1: partition both sides with the SAME hash (these would be files on disk)
        for (int[] r : left) lp.get(Math.floorMod(Integer.hashCode(r[0]) * 0x9E3779B1, partitions)).add(r);
        for (int[] s : right) rp.get(Math.floorMod(Integer.hashCode(s[0]) * 0x9E3779B1, partitions)).add(s);
        // phase 2: matching keys are in the same partition -> join partition pairs in memory
        List<int[]> out = new ArrayList<>();
        for (int p = 0; p < partitions; p++) out.addAll(hashJoin(lp.get(p), rp.get(p)));
        return out;
    }

    public static void main(String[] args) {
        System.out.println("--- JOIN ALGORITHMS DEMO ---");
        // employees(deptId, empId)  JOIN  departments(deptId, budget)
        List<int[]> employees = List.of(new int[]{10, 1}, new int[]{20, 2}, new int[]{10, 3}, new int[]{30, 4}, new int[]{40, 5});
        List<int[]> departments = List.of(new int[]{10, 500}, new int[]{20, 800}, new int[]{30, 300}, new int[]{50, 900});
        for (String name : new String[]{"nested loop", "sort-merge", "hash", "grace hash"}) {
            List<int[]> result = switch (name) {
                case "nested loop" -> nestedLoopJoin(employees, departments);
                case "sort-merge" -> sortMergeJoin(employees, departments);
                case "hash" -> hashJoin(employees, departments);
                default -> graceHashJoin(employees, departments, 3);
            };
            List<String> rows = new ArrayList<>();
            for (int[] row : result) rows.add(Arrays.toString(row));
            rows.sort(null);
            System.out.printf("%-12s %d rows: %s%n", name, result.size(), rows);
        }
        // 4 rows: emp 1, 3 in dept 10; emp 2 in 20; emp 4 in 30 (dept 40 and 50 have no match)

        List<int[]> big = new ArrayList<>();
        for (int i = 0; i < 1000; i++) big.add(new int[]{i % 50, i});
        int[] scans = {0};
        blockNestedLoopJoin(big, departments, 100, scans);
        System.out.println("\nBlock nested loop over 1000 rows with blocks of 100: right side scanned "
                + scans[0] + " times (plain nested loop: 1000)");
    }
}
