package queue.twostacks;

import stack.linkedlistbased.LinkedListStack;

/**
 * QUEUE USING TWO STACKS
 * 
 * What it is:
 * A classic interview problem: Implement FIFO (Queue) behavior using only LIFO (Stack) structures.
 * 
 * Approach/Strategy (Amortized O(1) Dequeue):
 * We use two stacks: `inbox` and `outbox`.
 * - Enqueue: Simply push onto the `inbox`. This is always O(1).
 * - Dequeue: Pop from the `outbox`. If the `outbox` is empty, we pop ALL elements 
 *   from the `inbox` and push them onto the `outbox`. This reverses their LIFO order 
 *   into FIFO order! Then we can pop from the `outbox`.
 * 
 * Note on Stacks:
 * We are importing and reusing the `LinkedListStack` we built earlier in the `stack` package.
 * 
 * Time/Space Complexity:
 * Operation      | Time Complexity | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * Enqueue        | O(1)            | O(1) extra       | Direct push to inbox
 * Dequeue        | O(1) amortized  | O(1) extra       | O(N) worst-case if outbox is empty
 * Front/Peek     | O(1) amortized  | O(1) extra       | Same logic as Dequeue
 * isEmpty        | O(1)            | O(1) extra       | True if both stacks are empty
 * 
 * Real-world analogy:
 * Think of an inbox tray and an outbox tray for processing documents. 
 * People drop new documents face up on the inbox pile (LIFO). When you want the oldest 
 * document, you take the entire inbox pile and flip it upside down into the outbox. 
 * Now the oldest document is on top of the outbox, ready to be processed.
 */
public class TwoStacksQueue<T> {

    private final LinkedListStack<T> inbox;
    private final LinkedListStack<T> outbox;

    public TwoStacksQueue() {
        this.inbox = new LinkedListStack<>();
        this.outbox = new LinkedListStack<>();
    }

    /**
     * Adds an element to the rear of the queue.
     * Time Complexity: O(1)
     */
    public void enqueue(T element) {
        inbox.push(element);
    }

    /**
     * Removes and returns the element at the front of the queue.
     * Time Complexity: Amortized O(1), Worst-case O(N)
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty. Cannot dequeue.");
        }
        
        shiftInboxToOutboxIfNeeded();
        return outbox.pop();
    }

    /**
     * Returns the element at the front without removing it.
     * Time Complexity: Amortized O(1), Worst-case O(N)
     */
    public T front() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty.");
        }
        
        shiftInboxToOutboxIfNeeded();
        return outbox.peek();
    }

    public boolean isEmpty() {
        return inbox.isEmpty() && outbox.isEmpty();
    }

    public int size() {
        return inbox.size() + outbox.size();
    }
    
    /**
     * Helper method to flip the inbox into the outbox if the outbox is empty.
     */
    private void shiftInboxToOutboxIfNeeded() {
        if (outbox.isEmpty()) {
            while (!inbox.isEmpty()) {
                outbox.push(inbox.pop());
            }
        }
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- QUEUE USING TWO STACKS DEMO ---");
        
        TwoStacksQueue<String> queue = new TwoStacksQueue<>();
        
        System.out.println("isEmpty initially? " + queue.isEmpty()); // true
        
        // 1. Enqueue elements
        queue.enqueue("First");
        queue.enqueue("Second");
        queue.enqueue("Third");
        System.out.println("Enqueued 3 elements. Size: " + queue.size()); // 3
        
        // 2. Dequeue triggers the shift from inbox to outbox
        System.out.println("Dequeue: " + queue.dequeue()); // First
        
        // 3. Enqueue more while outbox has elements
        System.out.println("Enqueuing Fourth...");
        queue.enqueue("Fourth"); // Goes to inbox. Outbox still has Second, Third.
        
        // 4. Dequeue should pull from outbox without shifting
        System.out.println("Dequeue: " + queue.dequeue()); // Second
        System.out.println("Front: " + queue.front()); // Third
        System.out.println("Dequeue: " + queue.dequeue()); // Third
        
        // 5. Outbox is now empty. Next dequeue triggers another shift.
        System.out.println("Dequeue (triggers shift): " + queue.dequeue()); // Fourth
        
        System.out.println("isEmpty finally? " + queue.isEmpty()); // true
        
        try {
            queue.dequeue();
        } catch (IllegalStateException e) {
            System.out.println("Successfully caught dequeue on empty: " + e.getMessage());
        }
    }
}
