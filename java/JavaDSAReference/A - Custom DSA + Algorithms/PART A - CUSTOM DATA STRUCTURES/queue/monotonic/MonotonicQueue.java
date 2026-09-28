package queue.monotonic;

/**
 * MONOTONIC QUEUE
 * 
 * What it is:
 * A specialized queue (technically a Double-Ended Queue or Deque) that maintains its 
 * elements in a strictly increasing or strictly decreasing order.
 * 
 * Approach/Strategy:
 * When adding a new element, we compare it to elements at the REAR of the queue.
 * - If the queue is strictly decreasing (used for Sliding Window Maximum), we remove 
 *   all elements from the rear that are SMALLER than the new element, because they 
 *   can never be the maximum anymore. Then we push the new element.
 * - If the queue is strictly increasing (used for Sliding Window Minimum), we remove 
 *   all elements from the rear that are LARGER than the new element.
 * When removing an element (e.g., as it slides out of our window), we only remove 
 * from the FRONT if the front element matches the one we are supposed to evict.
 * 
 * Note on Data Structure:
 * Since a Monotonic Queue requires operations at BOTH ends (remove rear, add rear, 
 * read front, remove front), it fundamentally requires a Deque. Since the official 
 * `deque` package is next, we implement a lightweight internal Deque using a doubly 
 * linked node approach specifically for this class.
 * 
 * Time/Space Complexity:
 * Operation      | Time Complexity | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * Push           | O(1) amortized  | O(1)             | Elements are added/removed at most once
 * Pop (evict)    | O(1)            | O(1)             | Simple front check and removal
 * Get Min/Max    | O(1)            | O(1)             | Direct access to front
 * 
 * Real-world analogy:
 * Imagine a line of athletes where you only care about the tallest. If a 7-foot athlete 
 * joins the back of the line, anyone shorter than 7 feet in front of them is completely 
 * irrelevant and asked to leave, because the 7-footer dominates them. The line remains 
 * sorted by height (decreasing).
 */
public class MonotonicQueue<T extends Comparable<T>> {

    // Internal node for our lightweight Deque
    private static class Node<U> {
        U data;
        Node<U> prev;
        Node<U> next;
        Node(U data) { this.data = data; }
    }

    private Node<T> head;
    private Node<T> tail;
    private final boolean isIncreasing; // True for min-queue, False for max-queue

    /**
     * @param isIncreasing If true, builds a monotonically increasing queue (useful for Minimums).
     *                     If false, builds a monotonically decreasing queue (useful for Maximums).
     */
    public MonotonicQueue(boolean isIncreasing) {
        this.isIncreasing = isIncreasing;
        this.head = null;
        this.tail = null;
    }

    /**
     * Adds an element, maintaining the monotonic property by crushing weaker elements.
     */
    public void push(T element) {
        // While the deque is not empty and the new element breaks the monotonicity,
        // we drop the tail.
        while (tail != null && shouldEvictTail(element)) {
            removeTail();
        }
        
        // Now add the new element to the tail
        Node<T> newNode = new Node<>(element);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    /**
     * Returns the optimum (Min or Max depending on queue type) which is always at the front.
     */
    public T getOptimum() {
        if (head == null) {
            throw new IllegalStateException("Queue is empty.");
        }
        return head.data;
    }

    /**
     * Attempts to pop an element from the front. 
     * In sliding window algorithms, we only evict if the front element is actually 
     * the element sliding out of the window.
     */
    public void popIfMatches(T element) {
        if (head != null && head.data.equals(element)) {
            removeHead();
        }
    }
    
    public boolean isEmpty() {
        return head == null;
    }

    // --- Helper Logic ---

    private boolean shouldEvictTail(T newElement) {
        if (isIncreasing) {
            // For strictly increasing (min-queue), drop tail if it's LARGER than new element
            return tail.data.compareTo(newElement) > 0;
        } else {
            // For strictly decreasing (max-queue), drop tail if it's SMALLER than new element
            return tail.data.compareTo(newElement) < 0;
        }
    }

    private void removeTail() {
        if (tail == null) return;
        if (tail == head) {
            head = tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
    }

    private void removeHead() {
        if (head == null) return;
        if (head == tail) {
            head = tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- MONOTONIC DECREASING QUEUE DEMO (Max Queue) ---");
        MonotonicQueue<Integer> maxQueue = new MonotonicQueue<>(false);
        
        // Simulating a sliding window over array: [5, 3, 1, 4, 2]
        
        // 1. Push 5
        maxQueue.push(5);
        System.out.println("Pushed 5. Current Max: " + maxQueue.getOptimum()); // 5
        
        // 2. Push 3 (3 is smaller, so 5 stays)
        maxQueue.push(3);
        System.out.println("Pushed 3. Current Max: " + maxQueue.getOptimum()); // 5
        
        // 3. Push 1 (1 is smaller, so 5 and 3 stay)
        maxQueue.push(1);
        System.out.println("Pushed 1. Current Max: " + maxQueue.getOptimum()); // 5
        
        // 4. Slide window: 5 falls out
        System.out.println("Window slides, 5 falls out.");
        maxQueue.popIfMatches(5);
        System.out.println("Current Max after evicting 5: " + maxQueue.getOptimum()); // 3
        
        // 5. Push 4 (4 is larger than 1 and 3! It will crush them from the rear)
        maxQueue.push(4);
        System.out.println("Pushed 4 (Crushed 3 and 1!). Current Max: " + maxQueue.getOptimum()); // 4
        
        
        System.out.println("\n--- MONOTONIC INCREASING QUEUE DEMO (Min Queue) ---");
        MonotonicQueue<Integer> minQueue = new MonotonicQueue<>(true);
        
        minQueue.push(5);
        System.out.println("Pushed 5. Current Min: " + minQueue.getOptimum()); // 5
        
        minQueue.push(3); // 3 crushes 5!
        System.out.println("Pushed 3. Current Min: " + minQueue.getOptimum()); // 3
        
        minQueue.push(8); // 8 is larger, so 3 stays
        System.out.println("Pushed 8. Current Min: " + minQueue.getOptimum()); // 3
        
        System.out.println("Window slides, 3 falls out.");
        minQueue.popIfMatches(3);
        System.out.println("Current Min after evicting 3: " + minQueue.getOptimum()); // 8
    }
}
