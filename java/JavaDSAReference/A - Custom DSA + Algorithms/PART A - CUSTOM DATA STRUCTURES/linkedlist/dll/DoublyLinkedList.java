package linkedlist.dll;

import linkedlist.common.DoublyNode;

/**
 * DOUBLY LINKED LIST (DLL)
 * 
 * What it is:
 * A linked list where each node contains a reference to the next node AND the previous node.
 * 
 * Approach/Strategy:
 * Having a 'prev' pointer means we can easily traverse backwards and we can delete a node 
 * in O(1) time if we already have a reference to it (unlike SLL where we need to find its 
 * predecessor). The tradeoff is extra memory for the 'prev' pointer and slightly more 
 * complex insert/delete logic since we must manage two links per node.
 * 
 * Time/Space Complexity:
 * Operation                | Time Complexity | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * Insert (Head)            | O(1)            | O(1)             | Updates head pointers
 * Insert (Tail)            | O(N)            | O(1)             | O(1) if tail pointer kept
 * Insert (Index)           | O(N)            | O(1)             | Requires traversal
 * Delete (Head)            | O(1)            | O(1)             | Updates head pointers
 * Delete (Tail)            | O(N)            | O(1)             | O(1) if tail pointer kept
 * Delete (Index)           | O(N)            | O(1)             | Requires traversal
 * Traverse (Forward)       | O(N)            | O(1)             | Follows 'next'
 * Traverse (Backward)      | O(N)            | O(1)             | Follows 'prev' from tail
 * Reverse                  | O(N)            | O(1)             | Swap next/prev for each node
 * 
 * Real-world analogy:
 * Think of a playlist where you can press "Next Track" or "Previous Track". 
 * You can move in both directions freely because each song links to the one before and after it.
 * 
 * Why implement this yourself when Java's java.util.LinkedList exists?
 * Java's LinkedList *is* a Doubly Linked List. Implementing it from scratch ensures you 
 * understand the edge cases (like updating head.prev when inserting at head) which are 
 * notoriously easy to get wrong in interviews.
 */
public class DoublyLinkedList<T> {

    private DoublyNode<T> head;
    // Note: We could maintain a 'tail' pointer for O(1) tail operations,
    // but we omit it here to mirror the SLL implementation and focus on traversals.
    private int size;

    public DoublyLinkedList() {
        this.head = null;
        this.size = 0;
    }

    // --------------------------------------------------------
    // 1. INSERTIONS
    // --------------------------------------------------------

    public void insertAtHead(T data) {
        DoublyNode<T> newNode = new DoublyNode<>(data);
        if (head == null) {
            head = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    public void insertAtTail(T data) {
        DoublyNode<T> newNode = new DoublyNode<>(data);
        if (head == null) {
            head = newNode;
            size++;
            return;
        }
        DoublyNode<T> current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = newNode;
        newNode.prev = current;
        size++;
    }

    public void insertAtPosition(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        if (index == 0) {
            insertAtHead(data);
            return;
        }
        if (index == size) {
            insertAtTail(data);
            return;
        }

        DoublyNode<T> newNode = new DoublyNode<>(data);
        DoublyNode<T> current = head;
        // Traverse to the node currently at the target index
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        DoublyNode<T> previousNode = current.prev;
        
        // Wire up the new node
        previousNode.next = newNode;
        newNode.prev = previousNode;
        newNode.next = current;
        current.prev = newNode;
        
        size++;
    }

    // --------------------------------------------------------
    // 2. DELETIONS
    // --------------------------------------------------------

    public void deleteAtHead() {
        if (head == null) return;
        if (head.next == null) { // Only one element
            head = null;
        } else {
            head = head.next;
            head.prev.next = null; // Clean up old head's next
            head.prev = null;      // Remove link pointing back
        }
        size--;
    }

    public void deleteAtTail() {
        if (head == null) return;
        if (head.next == null) {
            head = null;
            size--;
            return;
        }
        DoublyNode<T> current = head;
        while (current.next != null) {
            current = current.next;
        }
        // current is now the last node
        current.prev.next = null;
        current.prev = null;
        size--;
    }

    public void deleteByPosition(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        if (index == 0) {
            deleteAtHead();
            return;
        }
        if (index == size - 1) {
            deleteAtTail();
            return;
        }

        DoublyNode<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        // Bridge the gap
        current.prev.next = current.next;
        current.next.prev = current.prev;
        
        // Clean up
        current.next = null;
        current.prev = null;
        size--;
    }

    // --------------------------------------------------------
    // 3. REVERSE
    // --------------------------------------------------------

    /**
     * Reverses the DLL in place.
     * Strategy: For every node, swap its 'next' and 'prev' pointers.
     * The new head becomes the last node processed.
     */
    public void reverse() {
        if (head == null || head.next == null) return;

        DoublyNode<T> current = head;
        DoublyNode<T> temp = null;

        while (current != null) {
            // Swap next and prev
            temp = current.prev;
            current.prev = current.next;
            current.next = temp;

            // Move to the next node in the original list (which is now stored in prev)
            current = current.prev;
        }

        // Update head to the last node processed (temp.prev is the old tail, now new head)
        if (temp != null) {
            head = temp.prev;
        }
    }

    // --------------------------------------------------------
    // 4. TRAVERSALS
    // --------------------------------------------------------

    public void traverseForward() {
        DoublyNode<T> current = head;
        System.out.print("Head <-> ");
        while (current != null) {
            System.out.print(current.data + " <-> ");
            current = current.next;
        }
        System.out.println("null");
    }

    public void traverseBackward() {
        if (head == null) {
            System.out.println("List is empty");
            return;
        }
        // Go to tail
        DoublyNode<T> current = head;
        while (current.next != null) {
            current = current.next;
        }
        // Traverse backwards
        System.out.print("Tail <-> ");
        while (current != null) {
            System.out.print(current.data + " <-> ");
            current = current.prev;
        }
        System.out.println("null");
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- DOUBLY LINKED LIST DEMO ---");
        
        DoublyLinkedList<Integer> dll = new DoublyLinkedList<>();
        
        // Insertions
        dll.insertAtHead(10);
        dll.insertAtTail(20);
        dll.insertAtTail(30);
        dll.insertAtPosition(1, 15);
        
        System.out.println("Forward traversal (expected 10 <-> 15 <-> 20 <-> 30):");
        dll.traverseForward();
        
        System.out.println("Backward traversal (expected 30 <-> 20 <-> 15 <-> 10):");
        dll.traverseBackward();
        
        // Deletions
        dll.deleteAtHead();
        dll.deleteAtTail();
        System.out.println("After deleting head and tail (expected 15 <-> 20):");
        dll.traverseForward();
        
        dll.insertAtTail(30);
        dll.deleteByPosition(1); // deletes 20
        System.out.println("After deleting at index 1 (expected 15 <-> 30):");
        dll.traverseForward();
        
        // Reverse
        dll.insertAtTail(40);
        dll.insertAtTail(50);
        System.out.println("Before reverse (expected 15 <-> 30 <-> 40 <-> 50):");
        dll.traverseForward();
        
        dll.reverse();
        System.out.println("After reverse (expected 50 <-> 40 <-> 30 <-> 15):");
        dll.traverseForward();
        dll.traverseBackward(); // Should work perfectly backwards too
    }
}
