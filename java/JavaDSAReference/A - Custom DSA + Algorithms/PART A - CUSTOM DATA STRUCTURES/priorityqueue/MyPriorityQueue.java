package priorityqueue;

import java.util.Arrays;
import java.util.Comparator;

/**
 * PRIORITY QUEUE
 * 
 * What it is:
 * An abstract data type similar to a regular queue or stack, but every element has a "priority".
 * An element with high priority is served before an element with low priority.
 * 
 * Approach/Strategy:
 * While a Priority Queue is an Abstract Data Type (ADT), it is almost universally 
 * implemented using a Binary Heap. 
 * This generic implementation accepts a `Comparator<T>`. 
 * - If no comparator is provided (or a natural order one is used), it behaves as a Min-Heap.
 * - If a reverse-order comparator is provided, it seamlessly behaves as a Max-Heap.
 * We use the same flat-array, complete binary tree math to bubble up and sink down.
 * 
 * Time/Space Complexity:
 * Operation      | Time Complexity | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * Enqueue (add)  | O(log N)        | O(1) amortized   | Bubble up
 * Dequeue (poll) | O(log N)        | O(1)             | Sink down
 * Peek           | O(1)            | O(1)             | Return index 0
 * 
 * Real-world analogy:
 * Boarding an airplane. Even if you arrived at the gate first (normal queue), people 
 * with First Class tickets (higher priority) are allowed to board before you.
 * 
 * Why implement this yourself?
 * Java's `java.util.PriorityQueue` works exactly like this under the hood. Understanding 
 * how Comparators seamlessly flip the internal heap structure is crucial for solving 
 * top-K elements problems in interviews.
 */
public class MyPriorityQueue<T> {

    private Object[] heap;
    private int size;
    private final Comparator<T> comparator;
    
    private static final int DEFAULT_CAPACITY = 11;

    /**
     * Constructs a Priority Queue using the provided Comparator to determine priority.
     */
    public MyPriorityQueue(Comparator<T> comparator) {
        this.heap = new Object[DEFAULT_CAPACITY];
        this.size = 0;
        this.comparator = comparator;
    }

    /**
     * Adds an element to the priority queue.
     */
    public void enqueue(T element) {
        if (element == null) throw new NullPointerException("Null elements not allowed");
        
        if (size == heap.length) {
            heap = Arrays.copyOf(heap, heap.length * 2);
        }
        
        heap[size] = element;
        size++;
        heapifyUp(size - 1);
    }

    /**
     * Retrieves and removes the head (highest priority element).
     */
    @SuppressWarnings("unchecked")
    public T dequeue() {
        if (size == 0) throw new IllegalStateException("Priority Queue is empty");
        
        T result = (T) heap[0];
        
        heap[0] = heap[size - 1];
        heap[size - 1] = null; // Help GC
        size--;
        
        if (size > 0) {
            heapifyDown(0);
        }
        
        return result;
    }

    /**
     * Retrieves, but does not remove, the head of this queue.
     */
    @SuppressWarnings("unchecked")
    public T peek() {
        if (size == 0) throw new IllegalStateException("Priority Queue is empty");
        return (T) heap[0];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    // --- Helper Methods ---

    @SuppressWarnings("unchecked")
    private void heapifyUp(int index) {
        int parentIndex = (index - 1) / 2;
        
        // While current node has higher priority (comparator returns < 0) than its parent
        while (index > 0 && comparator.compare((T) heap[index], (T) heap[parentIndex]) < 0) {
            swap(index, parentIndex);
            index = parentIndex;
            parentIndex = (index - 1) / 2;
        }
    }

    @SuppressWarnings("unchecked")
    private void heapifyDown(int index) {
        while (true) {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            int highestPriority = index;
            
            // Check if left child has higher priority
            if (leftChild < size && comparator.compare((T) heap[leftChild], (T) heap[highestPriority]) < 0) {
                highestPriority = leftChild;
            }
            
            // Check if right child has even higher priority
            if (rightChild < size && comparator.compare((T) heap[rightChild], (T) heap[highestPriority]) < 0) {
                highestPriority = rightChild;
            }
            
            if (highestPriority == index) {
                break;
            }
            
            swap(index, highestPriority);
            index = highestPriority;
        }
    }

    private void swap(int i, int j) {
        Object temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- PRIORITY QUEUE DEMO ---");
        
        // 1. Min-Priority Queue (Smallest numbers have highest priority)
        System.out.println("\n1. Min-Priority Queue (Ascending order):");
        Comparator<Integer> minComparator = (a, b) -> a.compareTo(b);
        MyPriorityQueue<Integer> minPQ = new MyPriorityQueue<>(minComparator);
        
        minPQ.enqueue(50);
        minPQ.enqueue(10);
        minPQ.enqueue(30);
        
        System.out.println("Dequeued: " + minPQ.dequeue()); // 10
        System.out.println("Dequeued: " + minPQ.dequeue()); // 30
        System.out.println("Dequeued: " + minPQ.dequeue()); // 50
        
        // 2. Max-Priority Queue (Largest numbers have highest priority)
        System.out.println("\n2. Max-Priority Queue (Descending order):");
        Comparator<Integer> maxComparator = (a, b) -> b.compareTo(a);
        MyPriorityQueue<Integer> maxPQ = new MyPriorityQueue<>(maxComparator);
        
        maxPQ.enqueue(50);
        maxPQ.enqueue(10);
        maxPQ.enqueue(30);
        maxPQ.enqueue(100);
        
        System.out.println("Dequeued: " + maxPQ.dequeue()); // 100
        System.out.println("Dequeued: " + maxPQ.dequeue()); // 50
        System.out.println("Dequeued: " + maxPQ.dequeue()); // 30
        
        // 3. Custom Object Priority
        System.out.println("\n3. Custom Object (Strings by Length):");
        // Shortest strings have highest priority
        Comparator<String> lengthComparator = (s1, s2) -> Integer.compare(s1.length(), s2.length());
        MyPriorityQueue<String> stringPQ = new MyPriorityQueue<>(lengthComparator);
        
        stringPQ.enqueue("Elephant");
        stringPQ.enqueue("Cat");
        stringPQ.enqueue("Hippopotamus");
        stringPQ.enqueue("Dog");
        
        while (!stringPQ.isEmpty()) {
            System.out.println("Dequeued: " + stringPQ.dequeue());
        }
        // Expected: Cat, Dog, Elephant, Hippopotamus
    }
}
