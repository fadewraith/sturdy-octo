package set.hashset;

import hashmap.chaining.MyHashMap;
import array.MyArrayList;

/**
 * HASHSET
 * 
 * What it is:
 * A collection that contains no duplicate elements. It is backed internally by a HashMap.
 * 
 * Approach/Strategy:
 * To store just keys without values, we use our previously built `MyHashMap`.
 * We store the HashSet's elements as the KEYS in the HashMap. 
 * The VALUE in the HashMap is just a dummy static object (e.g., an Object or Boolean) 
 * because we don't care about the value, we only care if the key exists.
 * 
 * Time/Space Complexity:
 * Operation      | Time Complexity       | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * Add            | O(1) amortized avg    | O(1)             | Relies on HashMap.put
 * Remove         | O(1) average          | O(1)             | Relies on HashMap.remove
 * Contains       | O(1) average          | O(1)             | Relies on HashMap.containsKey
 * 
 * Real-world analogy:
 * A guest list for a VIP party. You don't care about the guest's age or phone number 
 * (no values), you just need to instantly check "Are they on the list or not?" (keys only).
 */
public class MyHashSet<T> {

    // The backing map
    private final MyHashMap<T, Boolean> map;
    
    // Dummy value to associate with an Object in the backing Map
    private static final Boolean PRESENT = true;

    public MyHashSet() {
        this.map = new MyHashMap<>();
    }
    
    public MyHashSet(int capacity, float loadFactor) {
        this.map = new MyHashMap<>(capacity, loadFactor);
    }

    /**
     * Adds the specified element to this set if it is not already present.
     * @return true if the set did not already contain the specified element
     */
    public boolean add(T element) {
        if (!map.containsKey(element)) {
            map.put(element, PRESENT);
            return true;
        }
        return false;
    }

    /**
     * Removes the specified element from this set if it is present.
     * @return true if the set contained the specified element
     */
    public boolean remove(T element) {
        Boolean removed = map.remove(element);
        return removed != null;
    }

    /**
     * Returns true if this set contains the specified element.
     */
    public boolean contains(T element) {
        return map.containsKey(element);
    }

    public int size() {
        return map.size();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    /**
     * Returns a custom MyArrayList of all elements in the set.
     */
    public MyArrayList<T> toList() {
        return map.keySet();
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- HASHSET DEMO ---");
        
        MyHashSet<String> set = new MyHashSet<>();
        
        System.out.println("Added 'Apple'? " + set.add("Apple")); // true
        System.out.println("Added 'Banana'? " + set.add("Banana")); // true
        System.out.println("Added 'Apple' again? " + set.add("Apple")); // false (duplicate)
        
        System.out.println("Size: " + set.size()); // 2
        
        System.out.println("Contains 'Banana'? " + set.contains("Banana")); // true
        System.out.println("Contains 'Cherry'? " + set.contains("Cherry")); // false
        
        System.out.println("Removed 'Banana'? " + set.remove("Banana")); // true
        System.out.println("Contains 'Banana' after removal? " + set.contains("Banana")); // false
        
        System.out.println("Final elements in set: " + set.toList().toString());
    }
}
