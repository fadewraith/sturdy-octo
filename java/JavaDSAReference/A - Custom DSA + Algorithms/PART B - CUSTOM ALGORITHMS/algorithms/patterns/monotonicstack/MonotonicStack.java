package algorithms.patterns.monotonicstack;

import java.util.Arrays;

/**
 * MONOTONIC STACK PATTERNS
 * 
 * WHAT IT IS:
 * A stack whose elements are strictly increasing or strictly decreasing.
 * 
 * WHEN TO USE THIS:
 * - When a problem asks to "find the NEXT GREATER element" or "PREVIOUS SMALLER element" 
 *   for every element in an array.
 * - Brute force would use nested loops taking O(N^2) time.
 * - Monotonic Stack solves these problems in strictly O(N) linear time!
 * 
 * STRATEGY (Next Greater Element):
 * We iterate through the array from RIGHT to LEFT.
 * We maintain a stack. 
 * While the stack is not empty AND the top element is SMALLER than or equal to 
 * the current array element, we POP it! (Because it's smaller, it can NEVER be the 
 * "Next Greater Element" for anything further to the left, so it's useless now).
 * After popping, if the stack is empty, there is no greater element (-1). 
 * If it's not empty, the top element IS the Next Greater Element!
 * Finally, we PUSH the current element onto the stack.
 * 
 * COMPLEXITY:
 * Time: O(N) (Every element is pushed/popped at most once).
 * Space: O(N) for the stack and result array.
 */
public class MonotonicStack {

    // Simple custom stack to avoid java.util.Stack
    private static class IntStack {
        int[] data;
        int top = -1;
        IntStack(int capacity) { data = new int[capacity]; }
        void push(int val) { data[++top] = val; }
        int pop() { return data[top--]; }
        int peek() { return data[top]; }
        boolean isEmpty() { return top == -1; }
    }

    public static int[] nextGreaterElement(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        IntStack stack = new IntStack(n);

        // Iterate from RIGHT to LEFT
        for (int i = n - 1; i >= 0; i--) {
            
            // Pop smaller elements
            while (!stack.isEmpty() && stack.peek() <= nums[i]) {
                stack.pop();
            }

            // Assign result
            if (stack.isEmpty()) {
                result[i] = -1; // No greater element to the right
            } else {
                result[i] = stack.peek();
            }

            // Push current element
            stack.push(nums[i]);
        }
        
        return result;
    }

    public static void main(String[] args) {
        System.out.println("--- MONOTONIC STACK (NEXT GREATER ELEMENT) DEMO ---");
        
        int[] arr = {4, 5, 2, 10, 8};
        System.out.println("Array: " + Arrays.toString(arr));
        
        int[] nextGreater = nextGreaterElement(arr);
        System.out.println("Next Greater: " + Arrays.toString(nextGreater));
        // Expected: [5, 10, 10, -1, -1]
    }
}
