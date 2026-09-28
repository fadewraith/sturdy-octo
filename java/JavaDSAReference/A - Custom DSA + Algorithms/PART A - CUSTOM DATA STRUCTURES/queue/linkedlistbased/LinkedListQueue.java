package queue.linkedlistbased;

import linkedlist.common.Node;

/**
 * LINKED-LIST-BASED QUEUE
 * 
 * What it is:
 * A First-In-First-Out (FIFO) data structure implemented using a Singly Linked List.
 * 
 * Approach/Strategy:
 * We maintain two pointers: `front` (points to the head of the list) and 
 * `rear` (points to the tail of the list).
 * - Enqueue: Add a new node at the `rear` and update the `rear` pointer. O(1).
 * - Dequeue: Remove the node at the `front` and update the `front` pointer. O(1).
 * 
 * Unlike the array-based linear queue, this queue never suffers from the "creeping" 
 * flaw and never needs to be resized. It can grow indefinitely as long as memory allows.
 * 
 * Time/Space Complexity:
 * Operation      | Time Complexity | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * Enqueue        | O(1)            | O(1)             | Updates rear pointer
 * Dequeue        | O(1)            | O(1)             | Updates front pointer
 * Front/Peek     | O(1)            | O(1)             | Reads front node
 * isEmpty        | O(1)            | O(1)             | Checks if front is null
 * 
 * Real-world analogy:
 * A drive-thru line at a fast food restaurant. Cars (nodes) enter at the back of the 
 * line (`rear`) and are served and leave from the front of the line (`front`).
 */
public class LinkedListQueue<T> {

    // front is equivalent to the 'head' of a linked list
    private Node<T> front;
    // rear is equivalent to the 'tail' of a linked list
    private Node<T> rear;
    private int size;

    public LinkedListQueue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    /**
     * Adds an element to the rear of the queue.
     */
    public void enqueue(T element) {
        Node<T> newNode = new Node<>(element);
        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    /**
     * Removes and returns the element at the front of the queue.
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty. Cannot dequeue.");
        }
        T element = front.data;
        Node<T> oldFront = front;
        
        front = front.next; // Move front pointer forward
        oldFront.next = null; // Help GC
        size--;
        
        // If the queue becomes empty after dequeue, reset rear to null as well
        if (front == null) {
            rear = null;
        }
        
        return element;
    }

    /**
     * Returns the element at the front without removing it.
     */
    public T front() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty.");
        }
        return front.data;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int size() {
        return size;
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- LINKED-LIST-BASED QUEUE DEMO ---");
        
        LinkedListQueue<Integer> queue = new LinkedListQueue<>();
        
        System.out.println("isEmpty initially? " + queue.isEmpty()); // true
        
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
        
        System.out.println("Enqueued 10, 20, 30. Size: " + queue.size()); // 3
        System.out.println("Front element: " + queue.front()); // 10
        
        System.out.println("Dequeue: " + queue.dequeue()); // 10
        System.out.println("Dequeue: " + queue.dequeue()); // 20
        
        System.out.println("Size after dequeues: " + queue.size()); // 1
        System.out.println("Front element now: " + queue.front()); // 30
        
        queue.enqueue(40);
        System.out.println("Enqueued 40. Dequeuing rest...");
        
        System.out.println("Dequeue: " + queue.dequeue()); // 30
        System.out.println("Dequeue: " + queue.dequeue()); // 40
        
        System.out.println("Is empty now? " + queue.isEmpty()); // true
        
        try {
            queue.dequeue();
        } catch (IllegalStateException e) {
            System.out.println("Successfully caught dequeue on empty: " + e.getMessage());
        }
    }
}
