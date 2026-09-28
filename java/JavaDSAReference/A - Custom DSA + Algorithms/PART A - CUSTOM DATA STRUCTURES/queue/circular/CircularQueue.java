package queue.circular;

/**
 * CIRCULAR QUEUE (Array-based)
 * 
 * What it is:
 * An array-based queue that solves the "creeping" flaw of the standard linear queue.
 * By treating the array as circular, the `rear` pointer wraps around to the beginning 
 * of the array when it hits the end, allowing us to reuse empty spaces left by dequeued elements.
 * 
 * Approach/Strategy:
 * We use modulo arithmetic (`index % capacity`) to wrap pointers.
 * - Enqueue: rear = (rear + 1) % capacity
 * - Dequeue: front = (front + 1) % capacity
 * We maintain a separate `size` counter to easily distinguish between an empty queue 
 * and a full queue (which is ambiguous if we only compare front and rear pointers).
 * 
 * Time/Space Complexity:
 * Operation      | Time Complexity | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * Enqueue        | O(1)            | O(1)             | Math operation for wrap-around
 * Dequeue        | O(1)            | O(1)             | Math operation for wrap-around
 * Front/Peek     | O(1)            | O(1)             | Direct array access
 * isEmpty/isFull | O(1)            | O(1)             | Simple boolean check against size
 * 
 * Real-world analogy:
 * A Ferris wheel. When a cabin reaches the bottom, people get off (dequeue) and new 
 * people get on (enqueue). The wheel keeps spinning in a circle, constantly reusing 
 * the same fixed number of cabins.
 */
public class CircularQueue<T> {

    private final T[] data;
    private int front;
    private int rear;
    private int currentSize;
    private final int capacity;

    @SuppressWarnings("unchecked")
    public CircularQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be > 0");
        }
        this.capacity = capacity;
        this.data = (T[]) new Object[capacity];
        
        this.front = 0;
        this.rear = -1; // Will become 0 on first enqueue
        this.currentSize = 0;
    }

    /**
     * Adds an element to the rear of the queue, wrapping around if necessary.
     */
    public void enqueue(T element) {
        if (isFull()) {
            throw new IllegalStateException("Queue is full.");
        }
        
        // Wrap around using modulo
        rear = (rear + 1) % capacity;
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
        
        // Wrap around using modulo
        front = (front + 1) % capacity;
        currentSize--;
        
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

    public boolean isFull() {
        return currentSize == capacity;
    }

    public int size() {
        return currentSize;
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- CIRCULAR QUEUE DEMO ---");
        
        CircularQueue<String> queue = new CircularQueue<>(3); // Small capacity
        
        // 1. Fill the queue
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");
        System.out.println("Enqueued A, B, C. isFull? " + queue.isFull()); // true
        
        // 2. Try overfilling
        try {
            queue.enqueue("D");
        } catch (IllegalStateException e) {
            System.out.println("Successfully caught overfill: " + e.getMessage());
        }
        
        // 3. Dequeue to create empty space at the beginning of the array
        System.out.println("Dequeue: " + queue.dequeue()); // A
        System.out.println("Dequeue: " + queue.dequeue()); // B
        
        // 4. In a linear queue, enqueuing here would fail. In a circular queue, it wraps!
        System.out.println("Queue size is 1. Attempting to enqueue D and E to wrap around...");
        queue.enqueue("D"); // Goes into index 0
        queue.enqueue("E"); // Goes into index 1
        System.out.println("Successfully enqueued D and E. isFull? " + queue.isFull()); // true
        
        // 5. Drain the queue to verify order (C, D, E)
        System.out.println("Dequeue: " + queue.dequeue()); // C
        System.out.println("Dequeue: " + queue.dequeue()); // D
        System.out.println("Dequeue: " + queue.dequeue()); // E
        
        System.out.println("isEmpty? " + queue.isEmpty()); // true
    }
}
