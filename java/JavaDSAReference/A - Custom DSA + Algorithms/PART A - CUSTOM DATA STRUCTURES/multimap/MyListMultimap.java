package multimap;

import array.MyArrayList;
import hashmap.chaining.MyHashMap;

/**
 * MULTIMAP (one key -> many values)
 *
 * What it is:
 * A map where each key can hold SEVERAL values, kept in insertion order:
 *   "fruit" -> [apple, banana], "veg" -> [carrot]
 * Same idea as Guava's ArrayListMultimap. Replaces the usual
 *   map.computeIfAbsent(key, k -> new ArrayList<>()).add(value)
 * boilerplate (grouping, adjacency lists, "group anagrams", indexing by tag).
 *
 * Approach/Strategy:
 * A MyHashMap<K, MyArrayList<V>> plus a running count of all values. A key whose list
 * becomes empty is removed, so containsKey() and keyCount() stay accurate.
 *
 * Time/Space Complexity:
 * Operation                         | Time
 * ------------------------------------------------
 * put(key, value)                   | O(1) amortized
 * get(key) (copy of the list)       | O(values of that key)
 * remove(key, value)                | O(values of that key)
 * removeAll(key) / containsKey      | O(1) average
 * size (all values) / keyCount      | O(1)
 * Space                             | O(keys + values)
 */
public class MyListMultimap<K, V> {

    private final MyHashMap<K, MyArrayList<V>> map = new MyHashMap<>();
    private int totalValues = 0;

    /** Adds value under key (duplicates allowed, insertion order kept). */
    public void put(K key, V value) {
        MyArrayList<V> list = map.get(key);
        if (list == null) {
            list = new MyArrayList<>();
            map.put(key, list);
        }
        list.add(value);
        totalValues++;
    }

    /** Copy of the values for key (empty list if none) - changing it does not affect the map. */
    public MyArrayList<V> get(K key) {
        MyArrayList<V> copy = new MyArrayList<>();
        MyArrayList<V> list = map.get(key);
        if (list != null) copy.addAll(list);
        return copy;
    }

    /** Removes ONE occurrence of value under key. Returns true if it was there. */
    public boolean remove(K key, V value) {
        MyArrayList<V> list = map.get(key);
        if (list == null || !list.remove(value)) return false;
        totalValues--;
        if (list.isEmpty()) map.remove(key);
        return true;
    }

    /** Removes the key and all its values; returns them (empty list if none). */
    public MyArrayList<V> removeAll(K key) {
        MyArrayList<V> list = map.remove(key);
        if (list == null) return new MyArrayList<>();
        totalValues -= list.size();
        return list;
    }

    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    public boolean containsEntry(K key, V value) {
        MyArrayList<V> list = map.get(key);
        return list != null && list.contains(value);
    }

    /** Number of values stored under key. */
    public int count(K key) {
        MyArrayList<V> list = map.get(key);
        return list == null ? 0 : list.size();
    }

    /** Total number of (key, value) pairs. */
    public int size() {
        return totalValues;
    }

    /** Number of distinct keys. */
    public int keyCount() {
        return map.size();
    }

    public boolean isEmpty() {
        return totalValues == 0;
    }

    public void clear() {
        map.clear();
        totalValues = 0;
    }

    public MyArrayList<K> keySet() {
        return map.keySet();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (MyHashMap.Entry<K, MyArrayList<V>> e : map) {
            if (sb.length() > 1) sb.append(", ");
            sb.append(e.key).append("=").append(e.value);
        }
        return sb.append("}").toString();
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- MULTIMAP DEMO: group anagrams ---");
        String[] words = {"eat", "tea", "tan", "ate", "nat", "bat"};
        MyListMultimap<String, String> groups = new MyListMultimap<>();
        for (String w : words) {
            groups.put(sortedLetters(w), w); // sorted letters = the anagram "signature"
        }
        System.out.println("Groups: " + groups);
        System.out.println("Anagrams of 'eat': " + groups.get("aet"));                   // [eat, tea, ate]
        System.out.println(groups.size() + " words in " + groups.keyCount() + " groups");  // 6 in 3
        groups.remove("aet", "tea");
        System.out.println("After removing 'tea': " + groups.get("aet") + ", total words = " + groups.size());
    }

    // Insertion sort of the letters (fine for short words; keeps Part A free of java.util)
    private static String sortedLetters(String w) {
        char[] c = w.toCharArray();
        for (int i = 1; i < c.length; i++) {
            char key = c[i];
            int j = i - 1;
            while (j >= 0 && c[j] > key) {
                c[j + 1] = c[j];
                j--;
            }
            c[j + 1] = key;
        }
        return new String(c);
    }
}
