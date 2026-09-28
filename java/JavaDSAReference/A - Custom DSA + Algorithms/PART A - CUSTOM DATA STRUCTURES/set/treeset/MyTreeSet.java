package set.treeset;

import tree.bst.BinarySearchTree;
import array.MyArrayList;

/**
 * TREESET
 * 
 * What it is:
 * A set that contains no duplicate elements and guarantees that its elements 
 * will be sorted in ascending order (or by a provided comparator).
 * 
 * Approach/Strategy:
 * It is backed internally by a Binary Search Tree (in Java's case, a Red-Black Tree, 
 * but here we use the generic BST we just built).
 * When we add an element, it is placed in its sorted position in the BST. 
 * Since the BST ignores duplicates during insertion, the set property is maintained automatically.
 * Traversing the set simply uses an In-Order traversal of the BST to retrieve 
 * the elements in perfectly sorted order.
 * 
 * Time/Space Complexity:
 * Operation      | Average Time | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * Add            | O(log N)     | O(1)             | BST insertion time
 * Remove         | O(log N)     | O(1)             | BST deletion time
 * Contains       | O(log N)     | O(1)             | BST search time
 * To List        | O(N)         | O(N)             | In-order traversal
 * 
 * Real-world analogy:
 * A meticulously organized rolodex of business cards. Whenever you get a new card, 
 * you instantly slide it into its exact alphabetical position (O(log N)). When you 
 * need to print a phone book, you just flip from start to end and they are already sorted!
 */
public class MyTreeSet<T extends Comparable<T>> {

    private final BinarySearchTree<T> bst;
    private int size;

    public MyTreeSet() {
        this.bst = new BinarySearchTree<>();
        this.size = 0;
    }

    /**
     * Adds the specified element to this set if it is not already present.
     * @return true if the set did not already contain the specified element
     */
    public boolean add(T element) {
        if (!bst.search(element)) {
            bst.insert(element);
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
        if (bst.search(element)) {
            bst.delete(element);
            size--;
            return true;
        }
        return false;
    }

    /**
     * Returns true if this set contains the specified element.
     */
    public boolean contains(T element) {
        return bst.search(element);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the elements as a sorted list using BST In-order traversal.
     */
    public MyArrayList<T> toSortedList() {
        return bst.toSortedList();
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
