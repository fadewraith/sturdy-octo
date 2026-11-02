package algorithms.heap;

import java.util.Arrays;
import java.util.Comparator;

/**
 * HEAP PATTERNS (Merge K Sorted Lists, Top-K, K-th Largest, Top-K Frequent, Median of a Stream)
 *
 * WHAT IT IS:
 * Four interview staples that are all "keep a small heap and let it do the sorting".
 * A tiny binary heap with a Comparator is included below (Part B avoids java.util
 * collections; the full-featured heaps live in Part A's heap package).
 *
 * 1. MERGE K SORTED LISTS
 *    Put the head of every list in a MIN-heap. Repeatedly take the smallest node,
 *    append it to the result, and push that node's successor.
 *    Time O(N log K) for N total nodes - the heap never holds more than K nodes.
 *    (Merging lists one by one would be O(N * K).)
 *
 * 2. TOP-K LARGEST / K-TH LARGEST
 *    Keep a MIN-heap of size K holding the K largest seen so far. Its root is the
 *    smallest of them = the K-th largest. A new number only gets in if it beats the
 *    root. Time O(N log K), Space O(K) - works on a stream of unknown length.
 *    (QuickSelect.java gives O(N) average for a single array held in memory.)
 *
 * 3. TOP-K FREQUENT ELEMENTS
 *    Count each value (here: sort a copy and count runs, since no HashMap), then
 *    keep the K most frequent with a size-K min-heap ordered by count.
 *
 * 4. MEDIAN OF A DATA STREAM
 *    Two heaps split the numbers in half:
 *      low  = MAX-heap with the smaller half,  high = MIN-heap with the larger half,
 *      low.size() == high.size() or low.size() == high.size() + 1.
 *    The median is low's top (odd count) or the average of both tops (even count).
 *    addNum: push into low, move low's top to high (keeps the halves ordered),
 *    then if high got bigger, move high's top back to low. O(log N) per add,
 *    O(1) per median.
 *
 * COMPLEXITY:
 * See each section above. All heaps: O(log size) push/pop, O(1) peek.
 */
public class HeapPatterns {

    /** Minimal binary heap ordered by a Comparator (smallest according to it on top). */
    static final class SimpleHeap<T> {
        private Object[] data = new Object[16];
        private int size = 0;
        private final Comparator<? super T> cmp;

        SimpleHeap(Comparator<? super T> cmp) { this.cmp = cmp; }

        int size() { return size; }
        boolean isEmpty() { return size == 0; }

        @SuppressWarnings("unchecked")
        T peek() { return (T) data[0]; }

        void push(T x) {
            if (size == data.length) data = Arrays.copyOf(data, size * 2);
            data[size] = x;
            int i = size++;
            while (i > 0 && less(i, (i - 1) / 2)) { swap(i, (i - 1) / 2); i = (i - 1) / 2; }
        }

        @SuppressWarnings("unchecked")
        T pop() {
            T top = (T) data[0];
            data[0] = data[--size];
            data[size] = null;
            int i = 0;
            while (true) {
                int l = 2 * i + 1, r = l + 1, smallest = i;
                if (l < size && less(l, smallest)) smallest = l;
                if (r < size && less(r, smallest)) smallest = r;
                if (smallest == i) break;
                swap(i, smallest);
                i = smallest;
            }
            return top;
        }

        @SuppressWarnings("unchecked")
        private boolean less(int a, int b) { return cmp.compare((T) data[a], (T) data[b]) < 0; }
        private void swap(int a, int b) { Object t = data[a]; data[a] = data[b]; data[b] = t; }
    }

    // ---------------- 1. Merge K sorted lists ----------------

    public static class ListNode {
        public int val;
        public ListNode next;
        public ListNode(int val) { this.val = val; }
    }

    public static ListNode mergeKLists(ListNode[] lists) {
        SimpleHeap<ListNode> heap = new SimpleHeap<>((a, b) -> Integer.compare(a.val, b.val));
        for (ListNode head : lists) if (head != null) heap.push(head);
        ListNode dummy = new ListNode(0), tail = dummy;
        while (!heap.isEmpty()) {
            ListNode smallest = heap.pop();
            tail.next = smallest;
            tail = smallest;
            if (smallest.next != null) heap.push(smallest.next);
        }
        return dummy.next;
    }

    // ---------------- 2. Top-K largest / K-th largest ----------------

    /** The k largest values, largest first. */
    public static int[] topKLargest(int[] nums, int k) {
        if (k < 0 || k > nums.length) throw new IllegalArgumentException("k out of range");
        SimpleHeap<Integer> heap = new SimpleHeap<>(Comparator.naturalOrder()); // min-heap
        for (int x : nums) {
            if (heap.size() < k) heap.push(x);
            else if (k > 0 && x > heap.peek()) { heap.pop(); heap.push(x); }
        }
        int[] result = new int[k];
        for (int i = k - 1; i >= 0; i--) result[i] = heap.pop(); // pops smallest first
        return result;
    }

    public static int kthLargest(int[] nums, int k) {
        if (k < 1 || k > nums.length) throw new IllegalArgumentException("k out of range");
        return topKLargest(nums, k)[k - 1];
    }

    // ---------------- 3. Top-K frequent ----------------

    /**
     * The k most frequent values, most frequent first (ties: smaller value first).
     * If several values tie at the cut-off, the smaller values are kept.
     */
    public static int[] topKFrequent(int[] nums, int k) {
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        // distinct values and their counts
        int[] values = new int[sorted.length], counts = new int[sorted.length];
        int distinct = 0;
        for (int i = 0; i < sorted.length; i++) {
            if (i == 0 || sorted[i] != sorted[i - 1]) { values[distinct] = sorted[i]; counts[distinct++] = 1; }
            else counts[distinct - 1]++;
        }
        if (k < 0 || k > distinct) throw new IllegalArgumentException("k out of range");

        // "worse" = lower count, or same count and bigger value -> worst sits on top of the min-heap
        Comparator<int[]> worseFirst = (a, b) -> a[1] != b[1] ? Integer.compare(a[1], b[1]) : Integer.compare(b[0], a[0]);
        SimpleHeap<int[]> heap = new SimpleHeap<>(worseFirst);
        for (int i = 0; i < distinct; i++) {
            int[] entry = {values[i], counts[i]};
            if (heap.size() < k) heap.push(entry);
            else if (k > 0 && worseFirst.compare(entry, heap.peek()) > 0) { heap.pop(); heap.push(entry); }
        }
        int[] result = new int[k];
        for (int i = k - 1; i >= 0; i--) result[i] = heap.pop()[0];
        return result;
    }

    // ---------------- 4. Median of a data stream ----------------

    public static class MedianFinder {
        private final SimpleHeap<Integer> low = new SimpleHeap<>(Comparator.reverseOrder()); // max-heap
        private final SimpleHeap<Integer> high = new SimpleHeap<>(Comparator.naturalOrder()); // min-heap

        public void addNum(int x) {
            low.push(x);
            high.push(low.pop());               // the largest of the low half moves up
            if (high.size() > low.size()) {
                low.push(high.pop());           // rebalance: low may have one extra
            }
        }

        public double findMedian() {
            if (low.isEmpty()) throw new IllegalStateException("no numbers yet");
            if (low.size() > high.size()) return low.peek();
            return ((long) low.peek() + high.peek()) / 2.0; // long: no overflow when adding
        }

        public int size() { return low.size() + high.size(); }
    }

    private static ListNode list(int... values) {
        ListNode dummy = new ListNode(0), tail = dummy;
        for (int v : values) { tail.next = new ListNode(v); tail = tail.next; }
        return dummy.next;
    }

    public static void main(String[] args) {
        System.out.println("--- HEAP PATTERNS DEMO ---");

        ListNode merged = mergeKLists(new ListNode[]{list(1, 4, 5), list(1, 3, 4), list(2, 6)});
        StringBuilder sb = new StringBuilder();
        for (ListNode n = merged; n != null; n = n.next) sb.append(n.val).append(n.next == null ? "" : " -> ");
        System.out.println("Merged lists: " + sb); // 1 -> 1 -> 2 -> 3 -> 4 -> 4 -> 5 -> 6

        int[] nums = {3, 2, 1, 5, 6, 4};
        System.out.println("\nTop 3 of " + Arrays.toString(nums) + ": " + Arrays.toString(topKLargest(nums, 3))); // [6, 5, 4]
        System.out.println("2nd largest: " + kthLargest(nums, 2)); // 5

        int[] freq = {1, 1, 1, 2, 2, 3};
        System.out.println("\nTop 2 frequent in " + Arrays.toString(freq) + ": " + Arrays.toString(topKFrequent(freq, 2))); // [1, 2]

        MedianFinder mf = new MedianFinder();
        int[] stream = {5, 15, 1, 3};
        System.out.print("\nRunning median of " + Arrays.toString(stream) + ": ");
        for (int x : stream) { mf.addNum(x); System.out.print(mf.findMedian() + " "); }
        System.out.println(); // 5.0 10.0 5.0 4.0
    }
}
