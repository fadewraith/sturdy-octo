package multiset;

import array.MyArrayList;
import hashmap.chaining.MyHashMap;

/**
 * MULTISET (BAG)
 *
 * What it is:
 * A set that allows DUPLICATES and remembers how many times each element was added:
 * {apple x3, pear x1}. Same idea as Guava's HashMultiset or C++'s std::multiset
 * (unordered here). Saves you writing map.put(x, map.getOrDefault(x, 0) + 1) everywhere.
 *
 * Approach/Strategy:
 * A MyHashMap<T, Integer> from element to its count, plus a running total.
 * Elements whose count drops to 0 are removed from the map, so distinctCount() stays right.
 *
 * Time/Space Complexity:
 * Operation                     | Time
 * ----------------------------------------
 * add / remove / count          | O(1) average
 * size (total, with duplicates) | O(1)
 * distinctCount                 | O(1)
 * iteration                     | O(size) - each copy is returned
 * Space                         | O(distinct elements)
 *
 * Interview uses: anagram checks, "top K frequent", sliding-window character counts,
 * inventory/shopping carts, word frequency.
 */
public class MyHashMultiset<T> implements Iterable<T> {

    private final MyHashMap<T, Integer> counts = new MyHashMap<>();
    private int totalSize = 0;

    /** Adds one copy. */
    public void add(T element) {
        add(element, 1);
    }

    /** Adds 'times' copies (times >= 0). */
    public void add(T element, int times) {
        if (times < 0) throw new IllegalArgumentException("times must be >= 0");
        if (times == 0) return;
        counts.put(element, counts.getOrDefault(element, 0) + times);
        totalSize += times;
    }

    /** Removes one copy. Returns true if there was one. */
    public boolean remove(T element) {
        return remove(element, 1) > 0;
    }

    /** Removes up to 'times' copies; returns how many were actually removed. */
    public int remove(T element, int times) {
        if (times < 0) throw new IllegalArgumentException("times must be >= 0");
        int current = counts.getOrDefault(element, 0);
        int removed = Math.min(current, times);
        if (removed == 0) return 0;
        if (current == removed) counts.remove(element); // count hit 0: drop the key
        else counts.put(element, current - removed);
        totalSize -= removed;
        return removed;
    }

    /** Removes every copy; returns how many there were. */
    public int removeAll(T element) {
        return remove(element, Integer.MAX_VALUE);
    }

    /** Sets the count to exactly n (0 removes the element). Returns the old count. */
    public int setCount(T element, int n) {
        if (n < 0) throw new IllegalArgumentException("count must be >= 0");
        int old = count(element);
        if (n > old) add(element, n - old);
        else remove(element, old - n);
        return old;
    }

    public int count(T element) {
        return counts.getOrDefault(element, 0);
    }

    public boolean contains(T element) {
        return counts.containsKey(element);
    }

    /** Total number of elements, counting duplicates. */
    public int size() {
        return totalSize;
    }

    /** Number of DIFFERENT elements. */
    public int distinctCount() {
        return counts.size();
    }

    public boolean isEmpty() {
        return totalSize == 0;
    }

    public void clear() {
        counts.clear();
        totalSize = 0;
    }

    /** Each distinct element once. */
    public MyArrayList<T> elementSet() {
        return counts.keySet();
    }

    /** Every copy: an element with count 3 appears 3 times (grouped together). */
    @Override
    public java.util.Iterator<T> iterator() {
        MyArrayList<T> all = new MyArrayList<>(totalSize);
        for (MyHashMap.Entry<T, Integer> e : counts) {
            for (int i = 0; i < e.value; i++) all.add(e.key);
        }
        return all.iterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (MyHashMap.Entry<T, Integer> e : counts) {
            if (sb.length() > 1) sb.append(", ");
            sb.append(e.key);
            if (e.value > 1) sb.append(" x ").append(e.value);
        }
        return sb.append("]").toString();
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- MULTISET (BAG) DEMO ---");
        MyHashMultiset<String> cart = new MyHashMultiset<>();
        cart.add("apple", 3);
        cart.add("pear");
        cart.add("apple");
        System.out.println("Cart: " + cart + " -> " + cart.size() + " items, " + cart.distinctCount() + " kinds");
        cart.remove("apple", 2);
        System.out.println("After removing 2 apples: apples = " + cart.count("apple") + ", total = " + cart.size());

        // Anagram check: two words are anagrams if their letter multisets are equal
        System.out.println("\"listen\" / \"silent\" anagrams? " + anagrams("listen", "silent")); // true
        System.out.println("\"apple\" / \"paper\" anagrams? " + anagrams("apple", "paper"));     // false
    }

    static boolean anagrams(String a, String b) {
        MyHashMultiset<Character> letters = new MyHashMultiset<>();
        for (char c : a.toCharArray()) letters.add(c);
        for (char c : b.toCharArray()) {
            if (!letters.remove(c)) return false; // b has a letter a doesn't (or too many)
        }
        return letters.isEmpty();
    }
}
