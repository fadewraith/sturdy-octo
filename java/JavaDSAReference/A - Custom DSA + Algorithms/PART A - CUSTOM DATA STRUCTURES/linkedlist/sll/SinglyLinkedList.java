package linkedlist.sll;

import linkedlist.common.Node;

/**
 * SINGLY LINKED LIST (SLL)
 * 
 * What it is:
 * A linear data structure where elements are not stored in contiguous memory locations.
 * Instead, each element (node) contains its data and a reference (link) to the next node.
 * 
 * Approach/Strategy:
 * We maintain a 'head' pointer referencing the first node. To traverse, search, or access
 * specific indices, we must start at the head and follow the 'next' links. Some implementations
 * also keep a 'tail' pointer for O(1) insertions at the end, but here we'll primarily focus on 
 * standard traversals to solidify the fundamental pointer manipulations.
 * 
 * Time/Space Complexity:
 * Operation                | Time Complexity | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * Insert (Head)            | O(1)            | O(1)             | Just update head pointer
 * Insert (Tail)            | O(N)            | O(1)             | O(1) if tail pointer kept
 * Insert (Index)           | O(N)            | O(1)             | Requires traversal
 * Delete (Head)            | O(1)            | O(1)             | Just update head pointer
 * Delete (Tail/Index)      | O(N)            | O(1)             | Requires traversal
 * Search / Get(index)      | O(N)            | O(1)             | Linear scan required
 * Reverse (Iterative)      | O(N)            | O(1)             | In-place pointer flipping
 * Reverse (Recursive)      | O(N)            | O(N)             | Call stack overhead
 * Cycle Detection (Floyd)  | O(N)            | O(1)             | Tortoise and Hare
 * Find Middle              | O(N)            | O(1)             | Fast/Slow pointers
 * 
 * Real-world analogy:
 * Think of a treasure hunt where each clue (Node) tells you the location of the next clue.
 * You only know where the first clue is (head). To find the 5th clue, you must physically 
 * go through clues 1 to 4 in order. You cannot skip directly to clue 5.
 * 
 * Why implement this yourself when Java's built-in java.util.LinkedList exists?
 * Java's LinkedList is actually a Doubly Linked List. Writing a Singly Linked List from 
 * scratch is essential for mastering pointer manipulations and algorithmic patterns 
 * (like fast/slow pointers and reversing in place), which are heavily tested in interviews.
 */
public class SinglyLinkedList<T> {

    private Node<T> head;
    private int size;

    public SinglyLinkedList() {
        this.head = null;
        this.size = 0;
    }

    // --------------------------------------------------------
    // 1. INSERTIONS
    // --------------------------------------------------------

    public void insertAtHead(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = head; // Point new node to current head
        head = newNode;      // Update head to be the new node
        size++;
    }

    public void insertAtTail(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
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
        Node<T> newNode = new Node<>(data);
        Node<T> current = head;
        // Traverse to the node just before the insertion point
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        newNode.next = current.next;
        current.next = newNode;
        size++;
    }

    // --------------------------------------------------------
    // 2. DELETIONS
    // --------------------------------------------------------

    public void deleteAtHead() {
        if (head == null) return;
        Node<T> temp = head;
        head = head.next; // Move head forward
        temp.next = null; // Help GC
        size--;
    }

    public void deleteAtTail() {
        if (head == null) return;
        if (head.next == null) {
            head = null;
            size--;
            return;
        }
        Node<T> current = head;
        while (current.next.next != null) { // Find second-to-last node
            current = current.next;
        }
        current.next = null;
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
        Node<T> current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        Node<T> nodeToDelete = current.next;
        current.next = nodeToDelete.next;
        nodeToDelete.next = null;
        size--;
    }

    public void deleteByValue(T value) {
        if (head == null) return;
        
        // If head is the target
        if (value.equals(head.data)) {
            deleteAtHead();
            return;
        }
        
        Node<T> current = head;
        while (current.next != null && !current.next.data.equals(value)) {
            current = current.next;
        }
        
        // If found
        if (current.next != null) {
            Node<T> toDelete = current.next;
            current.next = toDelete.next;
            toDelete.next = null;
            size--;
        }
    }

    public void deleteAllOccurrences(T value) {
        // First, handle if head (and subsequent consecutive nodes) have the value
        while (head != null && head.data.equals(value)) {
            head = head.next;
            size--;
        }
        
        if (head == null) return;
        
        Node<T> current = head;
        while (current.next != null) {
            if (current.next.data.equals(value)) {
                current.next = current.next.next; // Skip the node
                size--;
            } else {
                current = current.next; // Only advance if not deleted, to catch consecutive duplicates
            }
        }
    }

    // --------------------------------------------------------
    // 3. SEARCH / ACCESS
    // --------------------------------------------------------

    public boolean search(T value) {
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(value)) return true;
            current = current.next;
        }
        return false;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    // --------------------------------------------------------
    // 4. REVERSE
    // --------------------------------------------------------

    /**
     * Iterative reverse: O(N) time, O(1) space.
     * We use three pointers: prev, current, nextNode to flip links one by one.
     */
    public void reverseIterative() {
        Node<T> prev = null;
        Node<T> current = head;
        Node<T> nextNode = null;
        
        while (current != null) {
            nextNode = current.next; // temporarily store the next node
            current.next = prev;     // flip the pointer backwards
            prev = current;          // move prev forward
            current = nextNode;      // move current forward
        }
        head = prev; // Update head
    }

    /**
     * Recursive reverse: O(N) time, O(N) space (call stack).
     */
    public void reverseRecursive() {
        head = reverseRecursiveHelper(head);
    }

    private Node<T> reverseRecursiveHelper(Node<T> node) {
        if (node == null || node.next == null) {
            return node; // New head of the reversed list
        }
        // Recurse to the end
        Node<T> newHead = reverseRecursiveHelper(node.next);
        
        // Reverse the link for the current node
        node.next.next = node;
        node.next = null;
        
        return newHead;
    }

    // --------------------------------------------------------
    // 5. CYCLE DETECTION (Floyd's Tortoise & Hare)
    // --------------------------------------------------------

    /**
     * Detects if the linked list has a cycle.
     * Math explanation for finding the start node:
     * - Let distance from head to cycle start be L.
     * - Let distance from cycle start to meeting point be X.
     * - Let cycle length be C.
     * - Slow pointer travels L + X.
     * - Fast pointer travels L + X + nC.
     * - Since fast travels 2x speed: 2(L + X) = L + X + nC => L + X = nC => L = nC - X.
     * - This proves that the distance from the head to the cycle start (L) is exactly equal 
     *   to the distance from the meeting point to the cycle start, completing the loop (nC - X).
     * - Thus, if we put one pointer at head, leave one at the meeting point, and advance 
     *   both at 1x speed, they will meet exactly at the cycle start.
     */
    public boolean hasCycle() {
        return getCycleStartNode() != null;
    }

    /**
     * Returns the node where the cycle begins, or null if no cycle.
     */
    public Node<T> getCycleStartNode() {
        Node<T> slow = head;
        Node<T> fast = head;
        boolean cycleExists = false;

        // 1. Detect intersection point
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                cycleExists = true;
                break;
            }
        }

        if (!cycleExists) return null;

        // 2. Find start node (see math explanation above)
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow; // Both point to the start of the cycle
    }

    // Helper for testing: artificially creates a cycle
    public void createCycle(int index) {
        if (head == null) return;
        Node<T> cycleStartNode = head;
        for (int i = 0; i < index; i++) {
            cycleStartNode = cycleStartNode.next;
        }
        Node<T> tail = head;
        while (tail.next != null) {
            tail = tail.next;
        }
        tail.next = cycleStartNode;
    }

    // --------------------------------------------------------
    // 6. MIDDLE ELEMENT
    // --------------------------------------------------------

    /**
     * Finds the middle element using slow/fast pointers.
     * O(N) time, O(1) space.
     */
    public T findMiddle() {
        if (head == null) return null;
        Node<T> slow = head;
        Node<T> fast = head;
        // Fast moves 2x, slow moves 1x. When fast hits end, slow is at middle.
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow.data;
    }

    // --------------------------------------------------------
    // 7. MERGE TWO SORTED SLLs
    // --------------------------------------------------------

    /**
     * Merges two sorted linked lists into a single sorted linked list.
     * We use a generic method bounded by Comparable to allow comparisons.
     */
    public static <U extends Comparable<U>> SinglyLinkedList<U> mergeSorted(SinglyLinkedList<U> list1, SinglyLinkedList<U> list2) {
        SinglyLinkedList<U> merged = new SinglyLinkedList<>();
        
        // Dummy head simplifies edge cases when building the new list
        Node<U> dummy = new Node<>(null);
        Node<U> current = dummy;
        
        Node<U> p1 = list1.head;
        Node<U> p2 = list2.head;

        while (p1 != null && p2 != null) {
            if (p1.data.compareTo(p2.data) <= 0) {
                current.next = new Node<>(p1.data);
                p1 = p1.next;
            } else {
                current.next = new Node<>(p2.data);
                p2 = p2.next;
            }
            current = current.next;
            merged.size++;
        }

        // Attach remaining elements
        while (p1 != null) {
            current.next = new Node<>(p1.data);
            p1 = p1.next;
            current = current.next;
            merged.size++;
        }
        while (p2 != null) {
            current.next = new Node<>(p2.data);
            p2 = p2.next;
            current = current.next;
            merged.size++;
        }

        merged.head = dummy.next;
        return merged;
    }

    // --------------------------------------------------------
    // 8. PALINDROME CHECK
    // --------------------------------------------------------

    /**
     * Checks if the linked list is a palindrome.
     * Strategy: Find middle, reverse the second half, compare both halves, 
     * then optionally restore the list (omitted here for simplicity, but ideal).
     * Time: O(N), Space: O(1)
     */
    public boolean isPalindrome() {
        if (head == null || head.next == null) return true;

        // 1. Find the middle
        Node<T> slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 2. Reverse the second half
        Node<T> prev = null;
        Node<T> curr = slow;
        while (curr != null) {
            Node<T> nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        
        // 3. Compare halves
        Node<T> p1 = head; // Start of first half
        Node<T> p2 = prev; // Start of reversed second half
        
        boolean isPalin = true;
        while (p2 != null) {
            if (!p1.data.equals(p2.data)) {
                isPalin = false;
                break;
            }
            p1 = p1.next;
            p2 = p2.next;
        }
        
        // Note: A true production implementation would re-reverse the second half here
        // to restore the list to its original state.
        
        return isPalin;
    }

    // --------------------------------------------------------
    // 9. UTILITIES
    // --------------------------------------------------------

    public int size() {
        return size;
    }

    public void traverse() {
        Node<T> current = head;
        System.out.print("Head -> ");
        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- SINGLY LINKED LIST DEMO ---");
        
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        
        // Insertions
        list.insertAtHead(10);
        list.insertAtTail(20);
        list.insertAtTail(30);
        list.insertAtPosition(1, 15);
        System.out.println("After insertions (expected 10->15->20->30):");
        list.traverse();
        
        // Deletions
        list.deleteAtHead();
        list.deleteAtTail();
        System.out.println("After deleteHead and deleteTail (expected 15->20):");
        list.traverse();
        
        // Delete occurrences
        list.insertAtHead(15);
        list.insertAtTail(15);
        System.out.println("Before deleteAllOccurrences(15) (expected 15->15->20->15):");
        list.traverse();
        list.deleteAllOccurrences(15);
        System.out.println("After deleteAllOccurrences(15) (expected 20):");
        list.traverse();
        
        // Reversals
        list.insertAtTail(30);
        list.insertAtTail(40);
        System.out.println("Before iterative reverse:");
        list.traverse();
        list.reverseIterative();
        System.out.println("After iterative reverse (expected 40->30->20):");
        list.traverse();
        list.reverseRecursive();
        System.out.println("After recursive reverse (expected 20->30->40):");
        list.traverse();
        
        // Middle
        System.out.println("Middle element is: " + list.findMiddle()); // 30
        
        // Palindrome
        SinglyLinkedList<Character> palList = new SinglyLinkedList<>();
        palList.insertAtTail('R');
        palList.insertAtTail('A');
        palList.insertAtTail('C');
        palList.insertAtTail('E');
        palList.insertAtTail('C');
        palList.insertAtTail('A');
        palList.insertAtTail('R');
        System.out.println("Is RACECAR palindrome? " + palList.isPalindrome());
        
        // Merge sorted lists
        SinglyLinkedList<Integer> l1 = new SinglyLinkedList<>();
        l1.insertAtTail(1); l1.insertAtTail(3); l1.insertAtTail(5);
        SinglyLinkedList<Integer> l2 = new SinglyLinkedList<>();
        l2.insertAtTail(2); l2.insertAtTail(4); l2.insertAtTail(6);
        SinglyLinkedList<Integer> merged = SinglyLinkedList.mergeSorted(l1, l2);
        System.out.println("Merged List (expected 1->2->3->4->5->6):");
        merged.traverse();
        
        // Cycle Detection
        SinglyLinkedList<Integer> cycleList = new SinglyLinkedList<>();
        cycleList.insertAtTail(1); cycleList.insertAtTail(2); cycleList.insertAtTail(3); cycleList.insertAtTail(4);
        System.out.println("Has cycle initially? " + cycleList.hasCycle());
        cycleList.createCycle(1); // Creates cycle back to node at index 1 (value 2)
        System.out.println("Has cycle after creation? " + cycleList.hasCycle());
        System.out.println("Cycle starts at value: " + cycleList.getCycleStartNode().data); // expected 2
    }
}
