package stack.linkedlistbased;

import linkedlist.common.Node;

/**
 * LINKED-LIST-BASED STACK
 * 
 * What it is:
 * A Last-In-First-Out (LIFO) data structure implemented using a Singly Linked List.
 * 
 * Approach/Strategy:
 * We use the `head` of the linked list as the `top` of the stack.
 * - Push: Insert at the head of the linked list. O(1).
 * - Pop: Delete at the head of the linked list. O(1).
 * - Peek: Read the head node's data. O(1).
 * 
 * Time/Space Complexity:
 * Operation      | Time Complexity | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * Push           | O(1)            | O(1)             | Creates a new Node
 * Pop            | O(1)            | O(1)             | Adjusts head pointer
 * Peek           | O(1)            | O(1)             | Reads head node
 * isEmpty        | O(1)            | O(1)             | Checks if head is null
 * 
 * Real-world analogy:
 * Think of a browser's "Back" button history. Every time you visit a new page, it is 
 * pushed to the top of your history. When you hit "Back", you pop the most recent page 
 * and return to the one just below it.
 * 
 * Why implement this yourself?
 * Implementing a Stack via a Linked List avoids the O(N) resizing overhead of an Array-based 
 * stack. However, it incurs extra memory overhead per element (due to the `next` pointer 
 * object reference) and loses cache locality. Knowing both tradeoffs is crucial for interviews.
 */
public class LinkedListStack<T> {

    // We reuse the Node class created in the linkedlist package!
    private Node<T> top;
    private int size;

    public LinkedListStack() {
        this.top = null;
        this.size = 0;
    }

    /**
     * Adds an element to the top of the stack.
     */
    public void push(T element) {
        Node<T> newNode = new Node<>(element);
        newNode.next = top;
        top = newNode;
        size++;
    }

    /**
     * Removes and returns the top element of the stack.
     */
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty. Cannot pop.");
        }
        T element = top.data;
        Node<T> oldTop = top;
        top = top.next; // Move top pointer down
        oldTop.next = null; // Help GC
        size--;
        return element;
    }

    /**
     * Returns the top element without removing it.
     */
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty. Cannot peek.");
        }
        return top.data;
    }

    /**
     * Checks if the stack is empty.
     */
    public boolean isEmpty() {
        return top == null;
    }
    
    /**
     * Returns the current number of elements in the stack.
     */
    public int size() {
        return size;
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- LINKED-LIST-BASED STACK DEMO ---");
        
        LinkedListStack<Integer> stack = new LinkedListStack<>();
        
        System.out.println("isEmpty initially? " + stack.isEmpty());
        
        stack.push(10);
        stack.push(20);
        stack.push(30);
        
        System.out.println("Pushed 10, 20, 30. Size: " + stack.size());
        System.out.println("Peek: " + stack.peek()); // Expected 30
        
        System.out.println("Pop: " + stack.pop()); // Expected 30
        System.out.println("Pop: " + stack.pop()); // Expected 20
        
        System.out.println("Size after pops: " + stack.size()); // 1
        System.out.println("Peek: " + stack.peek()); // Expected 10
        
        stack.pop(); // Expected 10
        
        try {
            stack.pop();
        } catch (IllegalStateException e) {
            System.out.println("Successfully caught pop on empty: " + e.getMessage());
        }
    }
}
