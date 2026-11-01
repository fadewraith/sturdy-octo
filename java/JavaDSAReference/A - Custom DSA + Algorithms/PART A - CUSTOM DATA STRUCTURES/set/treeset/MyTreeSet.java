package set.treeset;

import tree.redblack.RedBlackTree;
import array.MyArrayList;

/**
 * TREESET
 * 
 * What it is:
 * A set that contains no duplicate elements and guarantees that its elements 
 * will be sorted in ascending order (or by a provided comparator).
 * 
 * Approach/Strategy:
 * It is backed internally by our Red-Black Tree - the same choice as java.util.TreeSet.
 * A plain BST would degrade to O(N) per operation when elements arrive already sorted;
 * the Red-Black tree keeps every operation O(log N) in the WORST case.
 * When we add an element, it is placed in its sorted position in the tree. 
 * Since the tree ignores duplicates during insertion, the set property is maintained automatically.
 * Traversing the set simply uses an In-Order traversal of the tree to retrieve 
 * the elements in perfectly sorted order.
 * 
 * Time/Space Complexity:
 * Operation      | Worst Time   | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * Add            | O(log N)     | O(1)             | Red-Black insert (worst case)
 * Remove         | O(log N)     | O(1)             | Red-Black delete (worst case)
 * Contains       | O(log N)     | O(1)             | Red-Black search (worst case)
 * To List        | O(N)         | O(N)             | In-order traversal
 * 
 * Real-world analogy:
 * A meticulously organized rolodex of business cards. Whenever you get a new card, 
 * you instantly slide it into its exact alphabetical position (O(log N)). When you 
 * need to print a phone book, you just flip from start to end and they are already sorted!
 */
public class MyTreeSet<T extends Comparable<T>> implements Iterable<T> {

    private RedBlackTree<T> rbTree; // not final: clear() swaps in a fresh tree
    private int size;

    public MyTreeSet() {
        this.rbTree = new RedBlackTree<>();
        this.size = 0;
    }

    /**
     * Adds the specified element to this set if it is not already present.
     * @return true if the set did not already contain the specified element
     */
    public boolean add(T element) {
        if (!rbTree.search(element)) {
            rbTree.insert(element);
            size++;
            return true;
        }
        return false;
    }

    /**
     * Removes the specified element from this set if it is present.
     * @return true if the set contained the specified element
     */
    public boolean remove(T element) {
        if (rbTree.search(element)) {
            rbTree.delete(element);
            size--;
            return true;
        }
        return false;
    }

    /**
     * Returns true if this set contains the specified element.
     */
    public boolean contains(T element) {
        return rbTree.search(element);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the elements as a sorted list using in-order traversal.
     */
    public MyArrayList<T> toSortedList() {
        return rbTree.toSortedList();
    }

    public void clear() {
        rbTree = new RedBlackTree<>();
        size = 0;
    }

    // ---------------- Navigation (null when there is no such element), all O(log N) ----------------

    /** Smallest element. */
    public T first() {
        return rbTree.min();
    }

    /** Largest element. */
    public T last() {
        return rbTree.max();
    }

    /** Greatest element <= e. */
    public T floor(T e) {
        return rbTree.floor(e);
    }

    /** Smallest element >= e. */
    public T ceiling(T e) {
        return rbTree.ceiling(e);
    }

    /** Greatest element strictly < e. */
    public T lower(T e) {
        return rbTree.lower(e);
    }

    /** Smallest element strictly > e. */
    public T higher(T e) {
        return rbTree.higher(e);
    }

    /** Ascending order: for (T x : set) { ... } */
    @Override
    public java.util.Iterator<T> iterator() {
        return rbTree.iterator();
    }

    // ---------------- Set algebra (each returns a NEW set) ----------------
    // Both sets are sorted, so these are linear MERGES of the two sorted lists,
    // O(n + m) - the same trick as merging two sorted arrays.

    public MyTreeSet<T> union(MyTreeSet<T> other) {
        MyArrayList<T> a = toSortedList(), b = other.toSortedList();
        MyTreeSet<T> result = new MyTreeSet<>();
        int i = 0, j = 0;
        while (i < a.size() || j < b.size()) {
            if (j == b.size() || (i < a.size() && a.get(i).compareTo(b.get(j)) < 0)) result.add(a.get(i++));
            else if (i == a.size() || a.get(i).compareTo(b.get(j)) > 0) result.add(b.get(j++));
            else { result.add(a.get(i++)); j++; } // equal: take once
        }
        return result;
    }

    public MyTreeSet<T> intersection(MyTreeSet<T> other) {
        MyArrayList<T> a = toSortedList(), b = other.toSortedList();
        MyTreeSet<T> result = new MyTreeSet<>();
        int i = 0, j = 0;
        while (i < a.size() && j < b.size()) {
            int c = a.get(i).compareTo(b.get(j));
            if (c < 0) i++;
            else if (c > 0) j++;
            else { result.add(a.get(i)); i++; j++; }
        }
        return result;
    }

    public MyTreeSet<T> difference(MyTreeSet<T> other) {
        MyArrayList<T> a = toSortedList(), b = other.toSortedList();
        MyTreeSet<T> result = new MyTreeSet<>();
        int i = 0, j = 0;
        while (i < a.size()) {
            if (j == b.size() || a.get(i).compareTo(b.get(j)) < 0) result.add(a.get(i++));
            else if (a.get(i).compareTo(b.get(j)) > 0) j++;
            else { i++; j++; } // in both: skip
        }
        return result;
    }

    public boolean isSubsetOf(MyTreeSet<T> other) {
        return size <= other.size && intersection(other).size() == size;
    }

    @Override
    public String toString() {
        return toSortedList().toString();
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- TREESET DEMO ---");
        
        MyTreeSet<Integer> treeSet = new MyTreeSet<>();
        
        System.out.println("Added 50? " + treeSet.add(50)); // true
        System.out.println("Added 30? " + treeSet.add(30)); // true
        System.out.println("Added 70? " + treeSet.add(70)); // true
        System.out.println("Added 20? " + treeSet.add(20)); // true
        System.out.println("Added 40? " + treeSet.add(40)); // true
        
        System.out.println("Added 30 again (duplicate)? " + treeSet.add(30)); // false
        
        System.out.println("Size: " + treeSet.size()); // 5
        
        System.out.println("Contains 70? " + treeSet.contains(70)); // true
        System.out.println("Contains 100? " + treeSet.contains(100)); // false
        
        // This is the magic of TreeSet: The output will be automatically sorted!
        System.out.println("Sorted elements in set: " + treeSet.toSortedList().toString());
        
        System.out.println("Removed 30? " + treeSet.remove(30)); // true
        System.out.println("Sorted elements after removing 30: " + treeSet.toSortedList().toString());
    }
}
