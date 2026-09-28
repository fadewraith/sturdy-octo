package queue.arraybased;

/**
 * ARRAY-BASED QUEUE (Linear)
 * 
 * What it is:
 * A linear data structure that follows the First-In-First-Out (FIFO) principle.
 * The first element added is the first one to be removed.
 * 
 * Approach/Strategy:
 * We use an array with two pointers: `front` (index of the first element) and 
 * `rear` (index of the last element). 
 * - Enqueue: Increment `rear` and place the element.
 * - Dequeue: Return element at `front` and increment `front`.
 * 
 * The Major Flaw of a Linear Array Queue:
 * As we enqueue and dequeue elements, both pointers move to the right. 
 * Eventually, `rear` hits the end of the array (isFull becomes true), even if 
 * there is plenty of empty space at the beginning of the array where elements 
 * were dequeued! This "creeping" problem is why the Circular Queue was invented.
 * 
 * Time/Space Complexity:
 * Operation      | Time Complexity | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * Enqueue        | O(1)            | O(1)             | Just moving the rear pointer
 * Dequeue        | O(1)            | O(1)             | Just moving the front pointer
 * Front/Peek     | O(1)            | O(1)             | Direct array access
 * isEmpty/isFull | O(1)            | O(1)             | Simple pointer math
 * 
 * Real-world analogy:
 * A line at a grocery store checkout. The first person in line is the first one 
 * served (dequeued), and new people join at the back of the line (enqueued).
 */
public class ArrayQueue<T> {

    private static final int DEFAULT_CAPACITY = 5; // Kept small to demonstrate the "creeping" flaw
    private T[] data;
    private int front;
    private int rear;
    private int currentSize; // Tracks actual number of elements

    @SuppressWarnings("unchecked")
    public ArrayQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be > 0");
        }
        this.data = (T[]) new Object[capacity];
        this.front = 0;
        this.rear = -1;
        this.currentSize = 0;
    }

    public ArrayQueue() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Adds an element to the rear of the queue.
     */
    public void enqueue(T element) {
        if (isFull()) {
            // In a dynamic array queue, we would resize here.
            // For a basic static array queue, we throw an exception.
            throw new IllegalStateException("Queue is full (rear reached capacity).");
        }
        rear++;
        data[rear] = element;
        currentSize++;
    }

    /**
     * Removes and returns the element at the front of the queue.
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty. Cannot dequeue.");
        }
        T element = data[front];
        data[front] = null; // Help GC
        front++; // Move front pointer to the right
        currentSize--;
        
        // Minor optimization: if the queue becomes entirely empty, reset pointers
        // to mitigate the "creeping" flaw temporarily.
        if (currentSize == 0) {
            front = 0;
            rear = -1;
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
        return data[front];
    }

    public boolean isEmpty() {
        return currentSize == 0;
    }

    /**
     * Checks if the queue is full. 
     * Notice how it checks if `rear` has reached the end of the array, 
     * regardless of whether `front` has moved forward leaving empty space behind.
     */
    public boolean isFull() {
        return rear == data.length - 1;
    }

    public int size() {
        return currentSize;
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- LINEAR ARRAY-BASED QUEUE DEMO ---");
        
        ArrayQueue<String> queue = new ArrayQueue<>(3); // Small capacity
        
        System.out.println("isEmpty initially? " + queue.isEmpty());
        
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");
        
        System.out.println("Enqueued A, B, C. isFull? " + queue.isFull()); // true
        
        System.out.println("Front element: " + queue.front()); // A
        
        System.out.println("Dequeue: " + queue.dequeue()); // A
        System.out.println("Dequeue: " + queue.dequeue()); // B
        
        System.out.println("Queue size now: " + queue.size()); // 1 (Contains 'C')
        
        // Demonstrating the flaw: the queue has 1 element, but `isFull()` is still true
        // because `rear` is stuck at the end of the array!
        System.out.println("Is queue full even though size is 1? " + queue.isFull()); // true
        
        try {
            System.out.println("Attempting to enqueue 'D'...");
            queue.enqueue("D"); // This will fail!
        } catch (IllegalStateException e) {
            System.out.println("Caught exception (The Creeping Flaw!): " + e.getMessage());
        }
    }
}
