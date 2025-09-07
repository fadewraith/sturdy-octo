package practiceQuestions.algomapio.stacks.medium;

import java.util.Stack;

public class MinStack {

    /**
     * Step-by-Step Thought Process
     * Understand the problem: Implement a stack that supports push, pop, top, and getMin operations, where getMin retrieves the minimum element in O(1) time.
     * Initialize two lists: stk to store the stack elements and min_stk to track the minimum at each push.
     * For push(val):
     * Append val to stk.
     * If min_stk is empty, append val to min_stk.
     * If min_stk’s top element is less than val, append min_stk’s top element to min_stk to maintain the minimum.
     * Otherwise, append val to min_stk as the new minimum.
     * For pop():
     * Remove the top element from stk.
     * Remove the top element from min_stk to keep min_stk aligned.
     * For top(): Return the last element of stk.
     * For getMin(): Return the last element of min_stk, which is the current minimum.
     * */

    private static Stack<Integer> stack;
    private static Stack<Integer> minStack;

    private MinStack() {
        stack = new Stack<>();
        minStack = new Stack<>();
    }

    private static void push(int val) {
        stack.push(val);
        if (minStack.isEmpty() || val <= minStack.peek()) {
            minStack.push(val);
        } else {
            minStack.push(minStack.peek());
        }
    }

    private static void pop() {
        stack.pop();
        minStack.pop();
//        int val = stack.pop();
//        if (val == minStack.peek()) {
//            minStack.pop();
//        }
    }

    private static int top() {
        return stack.peek();
    }

    private static int getMin() {
        return minStack.peek();
    }

    public static void main(String[] args) {
        // Initialize a new MinStack
        MinStack minStack = new MinStack();
        
        // Test 1: Basic operations
        System.out.println("Test 1: Basic operations");
        minStack.push(5);
        minStack.push(3);
        minStack.push(7);
        System.out.println("Top: " + minStack.top() + " (Expected: 7)");
        System.out.println("Min: " + minStack.getMin() + " (Expected: 3)");
        
        // Test 2: Pop operation
        System.out.println("\nTest 2: After pop");
        minStack.pop();
        System.out.println("Top: " + minStack.top() + " (Expected: 3)");
        System.out.println("Min: " + minStack.getMin() + " (Expected: 3)");
        
        // Test 3: Push duplicate minimum
        System.out.println("\nTest 3: Push duplicate minimum");
        minStack.push(3);
        minStack.push(3);
        System.out.println("Top: " + minStack.top() + " (Expected: 3)");
        System.out.println("Min: " + minStack.getMin() + " (Expected: 3)");
        
        // Test 4: Pop all elements with same minimum
        System.out.println("\nTest 4: Pop all elements with same minimum");
        minStack.pop();
        System.out.println("Top: " + minStack.top() + " (Expected: 3)");
        System.out.println("Min: " + minStack.getMin() + " (Expected: 3)");
        
        minStack.pop();
        System.out.println("Top: " + minStack.top() + " (Expected: 5)");
        System.out.println("Min: " + minStack.getMin() + " (Expected: 5)");
        
        // Test 5: Edge case - single element
        System.out.println("\nTest 5: Single element");
        minStack = new MinStack(); // Reset stack
        minStack.push(10);
        System.out.println("Top: " + minStack.top() + " (Expected: 10)");
        System.out.println("Min: " + minStack.getMin() + " (Expected: 10)");
        
        // Test 6: Empty stack (uncomment to test error case)
        // System.out.println("\nTest 6: Empty stack");
        // minStack.pop();
        // minStack.getMin(); // Should throw EmptyStackException
        
        // Test 7: Large numbers and negative numbers
        System.out.println("\nTest 7: Large and negative numbers");
        minStack = new MinStack(); // Reset stack
        minStack.push(2147483647);
        minStack.push(-2147483648);
        System.out.println("Top: " + minStack.top() + " (Expected: -2147483648)");
        System.out.println("Min: " + minStack.getMin() + " (Expected: -2147483648)");
        
        // Test 8: Sequence of operations
        System.out.println("\nTest 8: Sequence of operations");
        minStack = new MinStack(); // Reset stack
        minStack.push(2);
        minStack.push(0);
        minStack.push(3);
        minStack.push(0);
        System.out.println("Min: " + minStack.getMin() + " (Expected: 0)");
        minStack.pop();
        System.out.println("Min: " + minStack.getMin() + " (Expected: 0)");
        minStack.pop();
        System.out.println("Min: " + minStack.getMin() + " (Expected: 0)");
        minStack.pop();
        System.out.println("Min: " + minStack.getMin() + " (Expected: 2)");
    }
}


/**
 * Detailed Explanation
 * Understanding the Problem: Min Stack
 * The “Min Stack” problem asks us to design a stack that supports the following operations in constant time:
 *
 * push(val): Pushes the value onto the stack.
 * pop(): Removes the element on top of the stack.
 * top(): Gets the top element of the stack.
 * getMin(): Retrieves the minimum element in the stack.
 * The challenge is to implement getMin() efficiently — ideally in O(1) time — rather than scanning through all elements every time.
 *
 * Why This Problem Matters
 * This problem demonstrates how to augment a standard data structure (a stack) to support additional operations efficiently. It's an important example in data structure design and is frequently asked in interviews to test understanding of auxiliary tracking and state synchronization.
 *
 * Optimal Approach: Stack with Minimum Tracking
 * The key idea is to use a second stack to track the minimum value at each level of the main stack. Whenever we push a new value, we also push the new minimum (either the new value or the current minimum, whichever is smaller) onto the second stack.
 *
 * Steps:
 * Initialize two stacks:
 * stk: stores all values.
 * min_stk: stores the minimum value at each level.
 * push(val):
 * Push val onto stk.
 * If min_stk is empty, or val <= min_stk[-1], push val onto min_stk.
 * Otherwise, push min_stk[-1] again to preserve the previous minimum.
 * pop():
 * Pop from both stk and min_stk to keep stacks aligned.
 * top(): Return the top element of stk.
 * getMin(): Return the top element of min_stk.
 * Example Walkthrough
 * Operations: push(5), push(3), push(7), getMin(), pop(), getMin()
 *
 * After push(5) → stk: [5], min_stk: [5]
 * After push(3) → stk: [5, 3], min_stk: [5, 3]
 * After push(7) → stk: [5, 3, 7], min_stk: [5, 3, 3]
 * getMin() → returns 3
 * pop() → removes 7 → stk: [5, 3], min_stk: [5, 3]
 * getMin() → returns 3
 * Time and Space Complexity
 * Time Complexity: O(1) for all operations: push, pop, top, and getMin.
 * Space Complexity: O(n), where n is the number of elements pushed onto the stack (due to the use of an auxiliary stack).
 *
 * Edge Cases to Consider
 * Pushing duplicate minimums → they should be tracked separately to avoid incorrect pops
 * Popping until the stack is empty → make sure both stacks are aligned
 * Calling getMin() on an empty stack → should be guarded or throw an error depending on the language
 * Conclusion
 * The “Min Stack” problem is a great exercise in augmenting data structures with auxiliary information. By maintaining a parallel stack of minimum values, we ensure constant-time retrieval of the current minimum element — a powerful technique applicable to many similar optimization problems in stack design.
 * */