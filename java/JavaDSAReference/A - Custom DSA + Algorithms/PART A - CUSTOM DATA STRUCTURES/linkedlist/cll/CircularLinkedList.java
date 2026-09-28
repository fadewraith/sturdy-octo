package linkedlist.cll;

import linkedlist.common.Node;

/**
 * CIRCULAR LINKED LIST (CLL)
 * 
 * What it is:
 * A variation of a linked list where the last node points back to the first node instead of null.
 * 
 * Approach/Strategy:
 * Instead of checking for `node.next == null` to find the end, we check for `node.next == head`.
 * Often, CLL implementations keep a 'tail' pointer instead of a 'head' pointer because from 
 * the tail, you can access the head in O(1) time (`tail.next`), giving you both ends instantly. 
 * Here, we will maintain a `head` and a `tail` pointer to make insertion clear.
 * 
 * Time/Space Complexity:
 * Operation                | Time Complexity | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * Insert                   | O(1)            | O(1)             | Given tail pointer
 * Delete                   | O(N)            | O(1)             | Have to find predecessor
 * Traverse                 | O(N)            | O(1)             | Stop when current == head again
 * Detect Circular          | O(N)            | O(1)             | Traverse and check if tail -> head
 * 
 * Real-world analogy:
 * Think of a Monopoly board or a multiplayer turn-based game. After the last player takes 
 * their turn, the sequence naturally loops back to the first player.
 * 
 * Why implement this yourself when Java's built-in exists?
 * Java doesn't have a built-in Circular Linked List! If you need round-robin scheduling 
 * or continuous looping behaviors, you must build it yourself.
 */
public class CircularLinkedList<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public CircularLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // --------------------------------------------------------
    // 1. INSERTION
    // --------------------------------------------------------

    /**
     * Inserts an element at the end of the circular list.
     */
    public void insert(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
            newNode.next = head; // Points to itself
        } else {
            tail.next = newNode;
            tail = newNode;
            tail.next = head; // Maintain circularity
        }
        size++;
    }

    // --------------------------------------------------------
    // 2. DELETION
    // --------------------------------------------------------

    /**
     * Deletes the first occurrence of the specified value.
     */
    public void delete(T value) {
        if (head == null) return;

        // Case 1: The list has only one node and it's the target
        if (head == tail && head.data.equals(value)) {
            head.next = null; // Help GC
            head = null;
            tail = null;
            size--;
            return;
        }

        // Case 2: The target is the head node
        if (head.data.equals(value)) {
            head = head.next;
            tail.next = head; // Tail must now point to the new head
            size--;
            return;
        }

        // Case 3: Target is elsewhere in the list
        Node<T> current = head;
        // Traverse until we wrap around back to head
        while (current.next != head) {
            if (current.next.data.equals(value)) {
                // If the node to delete is the tail
                if (current.next == tail) {
                    tail = current;
                    tail.next = head;
                } else {
                    current.next = current.next.next;
                }
                size--;
                return;
            }
            current = current.next;
        }
    }

    // --------------------------------------------------------
    // 3. TRAVERSAL
    // --------------------------------------------------------

    /**
     * Traverses the list with a cycle-aware stopping condition.
     */
    public void traverse() {
        if (head == null) {
            System.out.println("Empty list");
            return;
        }
        
        Node<T> current = head;
        System.out.print("CLL -> ");
        
        // Using a do-while loop is idiomatic for circular lists because 
        // current starts at head, and we want to stop when it hits head again.
        do {
            System.out.print(current.data + " -> ");
            current = current.next;
        } while (current != head);
        
        System.out.println("(back to head: " + head.data + ")");
    }

    // --------------------------------------------------------
    // 4. DETECT CIRCULAR
    // --------------------------------------------------------

    /**
     * Detects if a given list (starting at some node) is perfectly circular.
     * A perfectly circular list has no nulls and eventually loops back to the EXACT start node.
     * Note: This is different from Floyd's cycle detection (which finds *any* cycle, like a shape of '6' or 'P').
     * This checks if the entire list is one big 'O' ring.
     */
    public static <U> boolean isProperlyCircular(Node<U> startingNode) {
        if (startingNode == null) return true; // Conventionally, empty can be viewed as circular

        Node<U> current = startingNode.next;
        
        // Traverse until we hit null or hit the starting node again
        while (current != null && current != startingNode) {
            current = current.next;
        }
        
        // If we found the starting node, it's circular. If we found null, it's not.
        return current == startingNode;
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- CIRCULAR LINKED LIST DEMO ---");
        
        CircularLinkedList<Integer> cll = new CircularLinkedList<>();
        
        // Insert
        cll.insert(10);
        cll.insert(20);
        cll.insert(30);
        cll.insert(40);
        
        System.out.println("After insertions:");
        cll.traverse(); // Expected: 10 -> 20 -> 30 -> 40 -> (back to 10)
        
        // Detect circularity
        System.out.println("\nIs our list's internal head properly circular? " 
                + CircularLinkedList.isProperlyCircular(cll.head)); // Expected: true
                
        // Build a fake non-circular list just to test the static method
        Node<Integer> fakeHead = new Node<>(1);
        fakeHead.next = new Node<>(2);
        System.out.println("Is fake list circular? " 
                + CircularLinkedList.isProperlyCircular(fakeHead)); // Expected: false
                
        // Deletions
        System.out.println("\nDeleting 10 (head):");
        cll.delete(10);
        cll.traverse(); // Expected: 20 -> 30 -> 40 -> (back to 20)
        
        System.out.println("\nDeleting 40 (tail):");
        cll.delete(40);
        cll.traverse(); // Expected: 20 -> 30 -> (back to 20)
        
        System.out.println("\nDeleting 30 (middle):");
        cll.insert(50); // Now 20, 30, 50
        cll.delete(30);
        cll.traverse(); // Expected: 20 -> 50 -> (back to 20)
    }
}
