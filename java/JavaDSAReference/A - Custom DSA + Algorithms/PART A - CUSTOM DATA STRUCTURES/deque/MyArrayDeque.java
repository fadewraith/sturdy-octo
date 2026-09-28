package deque;

/**
 * ARRAY-BASED DEQUE (Double-Ended Queue)
 * 
 * What it is:
 * A Deque allows insertion and removal at both ends (Front and Rear) in O(1) time.
 * This array-based version uses a circular array strategy (like a Circular Queue) 
 * to ensure that operations at either end do not require shifting elements.
 * 
 * Approach/Strategy:
 * We use an array and track `front` and `rear` indices. 
 * - addLast / removeFirst: Standard circular queue behavior.
 * - addFirst: We move the `front` pointer backward (circularly).
 * - removeLast: We move the `rear` pointer backward (circularly).
 * When the array is full, we allocate a new array of double the size, and "unroll" 
 * the circular elements so they are perfectly contiguous from index 0 in the new array.
 * 
 * Time/Space Complexity:
 * Operation      | Time Complexity | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * addFirst       | O(1) amortized  | O(1)             | O(N) worst-case on resize
 * addLast        | O(1) amortized  | O(1)             | O(N) worst-case on resize
 * removeFirst    | O(1)            | O(1)             | Circular pointer math
 * removeLast     | O(1)            | O(1)             | Circular pointer math
 * peekFirst/Last | O(1)            | O(1)             | Direct array access
 * 
 * Real-world analogy:
 * A train track with a loading dock in the middle. You can attach new train cars to 
 * either the front of the train or the back of the train, and you can detach cars 
 * from either end as well.
 */
public class MyArrayDeque<T> {

    private static final int DEFAULT_CAPACITY = 8;
    private T[] data;
    private int front;
    private int rear;
    private int size;

    @SuppressWarnings("unchecked")
    public MyArrayDeque() {
        this.data = (T[]) new Object[DEFAULT_CAPACITY];
        this.front = 0;
        this.rear = 0;
        this.size = 0;
    }

    public void addFirst(T element) {
        if (size == data.length) {
            resize();
        }
        // Move front backwards circularly. If front is 0, it wraps to length - 1
        front = (front - 1 + data.length) % data.length;
        data[front] = element;
        size++;
    }

    public void addLast(T element) {
        if (size == data.length) {
            resize();
        }
        data[rear] = element;
        // Move rear forwards circularly
        rear = (rear + 1) % data.length;
        size++;
    }

    public T removeFirst() {
        if (isEmpty()) {
            throw new IllegalStateException("Deque is empty");
        }
        T element = data[front];
        data[front] = null; // Help GC
        front = (front + 1) % data.length;
        size--;
        return element;
    }

    public T removeLast() {
        if (isEmpty()) {
            throw new IllegalStateException("Deque is empty");
        }
        // Rear points to the NEXT available slot, so the last element is at (rear - 1)
        rear = (rear - 1 + data.length) % data.length;
        T element = data[rear];
        data[rear] = null; // Help GC
        size--;
        return element;
    }

    public T peekFirst() {
        if (isEmpty()) {
            throw new IllegalStateException("Deque is empty");
        }
        return data[front];
    }

    public T peekLast() {
        if (isEmpty()) {
            throw new IllegalStateException("Deque is empty");
        }
        return data[(rear - 1 + data.length) % data.length];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = data.length * 2;
        T[] newData = (T[]) new Object[newCapacity];
        
        // "Unroll" the circular array into the new array
        for (int i = 0; i < size; i++) {
            newData[i] = data[(front + i) % data.length];
        }
        
        data = newData;
        front = 0;
        rear = size;
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- ARRAY-BASED DEQUE DEMO ---");
        MyArrayDeque<String> deque = new MyArrayDeque<>();
        
        deque.addLast("A");
        deque.addLast("B");
        deque.addFirst("Z"); // Z, A, B
        
        System.out.println("Peek First: " + deque.peekFirst()); // Z
        System.out.println("Peek Last: " + deque.peekLast());   // B
        
        System.out.println("Remove First: " + deque.removeFirst()); // Z
        System.out.println("Remove Last: " + deque.removeLast());   // B
        
        System.out.println("Peek First after removals: " + deque.peekFirst()); // A
        
        // Force resize
        for(int i=0; i<10; i++) {
            deque.addLast("Node" + i);
        }
        System.out.println("Size after forced resize: " + deque.size());
    }
}
