package stack.arraybased;

/**
 * ARRAY-BASED STACK
 * 
 * What it is:
 * A linear data structure that follows the Last-In-First-Out (LIFO) principle.
 * The last element added to the stack is the first one to be removed.
 * This implementation uses an underlying array and resizes dynamically when full.
 * 
 * Approach/Strategy:
 * We maintain an array and a pointer `top` (or `size`). Pushing an element adds it
 * at the `top` index and increments it. Popping decrements `top` and returns the element.
 * If the array reaches its capacity during a push, we allocate a new array of double 
 * the size and copy elements over (similar to a Dynamic Array).
 * 
 * Time/Space Complexity:
 * Operation      | Time Complexity | Space Complexity | Notes
 * ---------------------------------------------------------------------------------
 * Push           | O(1) amortized  | O(1)             | O(N) worst-case if resize occurs
 * Pop            | O(1)            | O(1)             | Just moving the pointer
 * Peek           | O(1)            | O(1)             | Direct array access
 * isEmpty/isFull | O(1)            | O(1)             | Simple boolean checks
 * Search         | O(N)            | O(1)             | Linear scan from top to bottom
 * 
 * Real-world analogy:
 * Think of a stack of plates in a cafeteria. You can only put a new plate on the top, 
 * and you can only take a plate off the top. If you want the bottom plate, you must 
 * remove all the plates above it first.
 * 
 * Why implement this yourself when Java's built-in java.util.Stack exists?
 * Java's `java.util.Stack` extends `Vector`, making it heavily synchronized and slow. 
 * Modern Java prefers `Deque` (like `ArrayDeque`) for stack operations. Building this 
 * teaches you how to manage the array bounds and dynamic resizing yourself.
 */
public class ArrayStack<T> {

    private static final int DEFAULT_CAPACITY = 10;
    private T[] data;
    private int top; // Points to the index of the top element. -1 means empty.

    @SuppressWarnings("unchecked")
    public ArrayStack() {
        this.data = (T[]) new Object[DEFAULT_CAPACITY];
        this.top = -1;
    }

    @SuppressWarnings("unchecked")
    public ArrayStack(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Capacity must be > 0");
        }
        this.data = (T[]) new Object[initialCapacity];
        this.top = -1;
    }

    /**
     * Adds an element to the top of the stack.
     */
    public void push(T element) {
        if (isFull()) {
            resize();
        }
        top++;
        data[top] = element;
    }

    /**
     * Removes and returns the top element of the stack.
     */
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty. Cannot pop.");
        }
        T element = data[top];
        data[top] = null; // Help garbage collector
        top--;
        return element;
    }

    /**
     * Returns the top element without removing it.
     */
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty. Cannot peek.");
        }
        return data[top];
    }

    /**
     * Checks if the stack is empty.
     */
    public boolean isEmpty() {
        return top == -1;
    }

    /**
     * Checks if the underlying array is full.
     */
    public boolean isFull() {
        return top == data.length - 1;
    }

    /**
     * Returns the 1-based position from the top of the stack where the object is located.
     * Returns -1 if the object is not on the stack.
     * (Mirrors java.util.Stack's search behavior).
     */
    public int search(T element) {
        // Search from top down
        for (int i = top; i >= 0; i--) {
            if (element == null ? data[i] == null : element.equals(data[i])) {
                return top - i + 1; // 1-based index from the top
            }
        }
        return -1;
    }
    
    public int size() {
        return top + 1;
    }

    /**
     * Doubles the capacity of the array.
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = data.length * 2;
        T[] newData = (T[]) new Object[newCapacity];
        for (int i = 0; i <= top; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- ARRAY-BASED STACK DEMO ---");
        
        // Use a small initial capacity to force a resize quickly
        ArrayStack<String> stack = new ArrayStack<>(2);
        System.out.println("Stack isEmpty? " + stack.isEmpty()); // true
        
        stack.push("A");
        stack.push("B");
        System.out.println("Pushed A, B. isFull? " + stack.isFull()); // true
        
        // This push will trigger a resize
        stack.push("C");
        stack.push("D");
        System.out.println("Pushed C, D. Size is now: " + stack.size()); // 4
        
        System.out.println("Peek top: " + stack.peek()); // D
        
        System.out.println("Search 'B' (1-based from top): " + stack.search("B")); // 3 (D is 1, C is 2, B is 3)
        System.out.println("Search 'Z': " + stack.search("Z")); // -1
        
        System.out.println("Pop: " + stack.pop()); // D
        System.out.println("Pop: " + stack.pop()); // C
        
        System.out.println("Current size: " + stack.size()); // 2
        
        // Edge cases
        stack.pop(); // B
        stack.pop(); // A
        
        try {
            stack.pop();
        } catch (IllegalStateException e) {
            System.out.println("Successfully caught pop on empty: " + e.getMessage());
        }
    }
}
