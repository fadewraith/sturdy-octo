package stack.twoqueues;

import linkedlist.sll.SinglyLinkedList;

/**
 * STACK USING TWO QUEUES
 * 
 * What it is:
 * A classic interview problem that tests your understanding of abstract data types.
 * You are asked to implement a LIFO (Stack) behavior using only FIFO (Queue) data structures.
 * 
 * Approach/Strategy (Method: Push is O(N), Pop is O(1)):
 * We maintain two queues: q1 (which always holds the stack elements in LIFO order) and q2 (a helper).
 * When pushing a new element:
 * 1. Enqueue it to q2 (it becomes the front of q2).
 * 2. Dequeue all elements from q1 and enqueue them into q2 (putting them behind the new element).
 * 3. Swap the names/references of q1 and q2.
 * 
 * Note on Queues:
 * Since we haven't built the official `queue` package yet (it's next in the curriculum!), 
 * we will use our `SinglyLinkedList` under the hood and restrict ourselves to Queue operations:
 * `insertAtTail` (enqueue) and `deleteAtHead` + `get(0)` (dequeue).
 * 
 * Time/Space Complexity:
 * Operation      | Time Complexity | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * Push           | O(N)            | O(1) extra       | Transfers all elements to q2 and back
 * Pop            | O(1)            | O(1) extra       | Just dequeues from q1
 * Peek           | O(1)            | O(1) extra       | Just peeks at q1
 * isEmpty        | O(1)            | O(1) extra       | Checks if q1 is empty
 * 
 * Variations:
 * - Make Push O(1) and Pop O(N): Always enqueue to q1. On pop, move all but the last 
 *   element from q1 to q2, pop the last element, and swap q1 and q2.
 * 
 * Real-world analogy:
 * Imagine simulating a stack of papers using two conveyor belts (queues). To put a new paper
 * on "top", you put it on the empty belt, then move all the existing papers from the main belt
 * behind it on the new belt. Now the newest paper will be the first one to come off.
 */
public class TwoQueuesStack<T> {

    /**
     * A tiny wrapper around our SinglyLinkedList to strictly enforce 
     * FIFO Queue semantics without violating global rules.
     */
    private static class SimpleQueue<U> {
        private SinglyLinkedList<U> list = new SinglyLinkedList<>();
        
        public void enqueue(U item) {
            list.insertAtTail(item);
        }
        
        public U dequeue() {
            U val = list.get(0);
            list.deleteAtHead();
            return val;
        }
        
        public U peek() {
            return list.get(0);
        }
        
        public boolean isEmpty() {
            return list.size() == 0;
        }
        
        public int size() {
            return list.size();
        }
    }

    private SimpleQueue<T> q1;
    private SimpleQueue<T> q2;

    public TwoQueuesStack() {
        this.q1 = new SimpleQueue<>();
        this.q2 = new SimpleQueue<>();
    }

    /**
     * Pushes an element onto the stack.
     * Time Complexity: O(N)
     */
    public void push(T element) {
        // 1. Enqueue the new element to the empty helper queue (q2)
        q2.enqueue(element);
        
        // 2. Transfer all existing elements from q1 to q2. 
        // This puts them behind the newly added element, enforcing LIFO!
        while (!q1.isEmpty()) {
            q2.enqueue(q1.dequeue());
        }
        
        // 3. Swap the references so q1 is always our main queue holding elements
        SimpleQueue<T> temp = q1;
        q1 = q2;
        q2 = temp;
    }

    /**
     * Removes and returns the top element of the stack.
     * Time Complexity: O(1)
     */
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty.");
        }
        return q1.dequeue();
    }

    /**
     * Returns the top element without removing it.
     * Time Complexity: O(1)
     */
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty.");
        }
        return q1.peek();
    }

    /**
     * Checks if the stack is empty.
     */
    public boolean isEmpty() {
        return q1.isEmpty();
    }

    public int size() {
        return q1.size();
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- STACK USING TWO QUEUES DEMO ---");
        
        TwoQueuesStack<String> stack = new TwoQueuesStack<>();
        
        System.out.println("isEmpty initially? " + stack.isEmpty()); // true
        
        stack.push("First");
        stack.push("Second");
        stack.push("Third");
        
        System.out.println("Pushed First, Second, Third. Size: " + stack.size()); // 3
        
        System.out.println("Peek: " + stack.peek()); // Expected: Third
        
        System.out.println("Pop: " + stack.pop()); // Expected: Third
        System.out.println("Pop: " + stack.pop()); // Expected: Second
        
        stack.push("Fourth");
        System.out.println("Pushed Fourth.");
        
        System.out.println("Pop: " + stack.pop()); // Expected: Fourth
        System.out.println("Pop: " + stack.pop()); // Expected: First
        
        try {
            stack.pop();
        } catch (IllegalStateException e) {
            System.out.println("Successfully caught pop on empty stack: " + e.getMessage());
        }
    }
}
