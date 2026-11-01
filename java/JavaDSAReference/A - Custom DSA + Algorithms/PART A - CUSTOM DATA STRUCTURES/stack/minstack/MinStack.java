package stack.minstack;

/**
 * MIN STACK
 *
 * What it is:
 * A stack that can also return its SMALLEST element in O(1) time - a classic interview
 * question (LeetCode 155). push, pop, peek and getMin are all O(1).
 *
 * Approach/Strategy:
 * Every node remembers the minimum of itself and everything BELOW it at the moment it
 * was pushed ("min so far"). Since a stack only ever removes from the top, the nodes
 * underneath never change - so their stored minimum stays correct forever.
 *
 *   push 5 -> [5 (min 5)]
 *   push 3 -> [3 (min 3)] [5 (min 5)]
 *   push 7 -> [7 (min 3)] [3 (min 3)] [5 (min 5)]
 *   pop    -> top is 3 again, and its stored min (3) is still right
 *
 * Alternative you may be asked about: TWO stacks (values + mins), pushing onto the min
 * stack only when the new value is <= current min. Same O(1) time, and it can use less
 * memory when the minimum rarely changes. The "min per node" version is simpler to get right.
 *
 * Time/Space Complexity:
 * Operation          | Time | Space
 * ----------------------------------------
 * push/pop/peek      | O(1) | O(1) each (one extra reference per node)
 * getMin             | O(1) |
 * Total space        |      | O(N)
 *
 * Real-world analogy:
 * A pile of exam papers where every paper has a sticky note saying "lowest score in
 * this pile up to here". You can read the lowest score instantly from the top paper.
 */
public class MinStack<T extends Comparable<T>> implements Iterable<T> {

    private static class Node<T> {
        final T value;
        final T minBelow; // min of this value and everything under it
        final Node<T> next;

        Node(T value, T minBelow, Node<T> next) {
            this.value = value;
            this.minBelow = minBelow;
            this.next = next;
        }
    }

    private Node<T> top;
    private int size;

    public void push(T value) {
        if (value == null) throw new IllegalArgumentException("null values are not supported");
        T min = (top == null || value.compareTo(top.minBelow) < 0) ? value : top.minBelow;
        top = new Node<>(value, min, top);
        size++;
    }

    public T pop() {
        if (top == null) throw new IllegalStateException("Stack is empty");
        T value = top.value;
        top = top.next;
        size--;
        return value;
    }

    public T peek() {
        if (top == null) throw new IllegalStateException("Stack is empty");
        return top.value;
    }

    /** Smallest element currently in the stack. O(1). */
    public T getMin() {
        if (top == null) throw new IllegalStateException("Stack is empty");
        return top.minBelow;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        top = null;
        size = 0;
    }

    /** Top to bottom (pop order). */
    @Override
    public java.util.Iterator<T> iterator() {
        return new java.util.Iterator<T>() {
            private Node<T> current = top;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (current == null) throw new java.util.NoSuchElementException();
                T value = current.value;
                current = current.next;
                return value;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (Node<T> n = top; n != null; n = n.next) {
            sb.append(n.value);
            if (n.next != null) sb.append(", ");
        }
        return sb.append("] (top first)").toString();
    }

    // --------------------------------------------------------
    // RUNNER METHOD
    // --------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- MIN STACK DEMO ---");
        MinStack<Integer> stack = new MinStack<>();
        int[] pushes = {5, 3, 7, 3, 1, 8};
        for (int v : pushes) {
            stack.push(v);
            System.out.println("push " + v + " -> min = " + stack.getMin());
        }
        while (!stack.isEmpty()) {
            int popped = stack.pop();
            System.out.println("pop " + popped + " -> " + (stack.isEmpty() ? "empty" : "min = " + stack.getMin()));
        }
        // Expected mins while popping: 1, 3, 3, 3, 5, then empty
    }
}
