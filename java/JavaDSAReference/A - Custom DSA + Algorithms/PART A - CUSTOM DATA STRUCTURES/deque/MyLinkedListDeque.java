package deque;

import linkedlist.common.DoublyNode;

/**
 * LINKED-LIST-BASED DEQUE (Double-Ended Queue)
 * 
 * What it is:
 * A Deque implemented using a Doubly Linked List. 
 * A doubly linked list naturally supports O(1) insertions and deletions at BOTH ends 
 * because every node has a reference to both its next and previous nodes, and we maintain 
 * pointers to both the head (front) and tail (rear).
 * 
 * Approach/Strategy:
 * - addFirst: Insert at head.
 * - addLast: Insert at tail.
 * - removeFirst: Delete at head.
 * - removeLast: Delete at tail.
 * 
 * Time/Space Complexity:
 * Operation      | Time Complexity | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * addFirst       | O(1)            | O(1)             | Adjust head pointers
 * addLast        | O(1)            | O(1)             | Adjust tail pointers
 * removeFirst    | O(1)            | O(1)             | Adjust head pointers
 * removeLast     | O(1)            | O(1)             | Adjust tail pointers
 * peekFirst/Last | O(1)            | O(1)             | Read head/tail node
 * 
 * Why implement this yourself?
 * Java's `java.util.LinkedList` actually implements the `Deque` interface in exactly 
 * this way! Building it yourself demystifies how `LinkedList` provides these methods.
 */
public class MyLinkedListDeque<T> {

    private DoublyNode<T> head; // Represents the front
    private DoublyNode<T> tail; // Represents the rear
    private int size;

    public MyLinkedListDeque() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void addFirst(T element) {
        DoublyNode<T> newNode = new DoublyNode<>(element);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    public void addLast(T element) {
        DoublyNode<T> newNode = new DoublyNode<>(element);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    public T removeFirst() {
        if (isEmpty()) {
            throw new IllegalStateException("Deque is empty");
        }
        T element = head.data;
        if (head == tail) { // Only one element
            head = tail = null;
        } else {
            head = head.next;
            head.prev.next = null; // Clean up old head
            head.prev = null;
        }
        size--;
        return element;
    }

    public T removeLast() {
        if (isEmpty()) {
            throw new IllegalStateException("Deque is empty");
        }
        T element = tail.data;
        if (head == tail) { // Only one element
            head = tail = null;
        } else {
            tail = tail.prev;
            tail.next.prev = null; // Clean up old tail
            tail.next = null;
        }
        size--;
        return element;
    }

    public T peekFirst() {
        if (isEmpty()) {
            throw new IllegalStateException("Deque is empty");
        }
        return head.data;
    }

    public T peekLast() {
        if (isEmpty()) {
            throw new IllegalStateException("Deque is empty");
        }
        return tail.data;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- LINKED-LIST-BASED DEQUE DEMO ---");
        MyLinkedListDeque<String> deque = new MyLinkedListDeque<>();
        
        deque.addLast("A");
        deque.addLast("B");
        deque.addFirst("Z"); // Z, A, B
        
        System.out.println("Peek First: " + deque.peekFirst()); // Z
        System.out.println("Peek Last: " + deque.peekLast());   // B
        
        System.out.println("Remove First: " + deque.removeFirst()); // Z
        System.out.println("Remove Last: " + deque.removeLast());   // B
        
        System.out.println("Size after removals: " + deque.size()); // 1 (Contains A)
        System.out.println("Peek First: " + deque.peekFirst()); // A
    }
}
