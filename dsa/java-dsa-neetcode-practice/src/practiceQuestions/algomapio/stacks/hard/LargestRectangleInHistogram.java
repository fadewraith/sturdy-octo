package practiceQuestions.algomapio.stacks.hard;

import java.util.Stack;

public class LargestRectangleInHistogram {

    /**
     * Step-by-Step Thought Process
     * The problem involves finding the largest rectangle area in a histogram, where each bar has a height given by the array heights. Each bar has a width of 1, and the rectangle's area is determined by the minimum height across a contiguous range of bars multiplied by the width of that range.
     *
     * The solution uses a monotonic stack to efficiently compute the maximum rectangle area by tracking bars in increasing height order. Here’s how it works:
     *
     * Stack Initialization: We maintain a stack of tuples (height, index), where height is the bar’s height, and index is the starting index where this height is valid. The stack is initially empty, and we track the maximum area found (max_area).
     * Processing Bars: For each bar at index i with height height, we set start = i as the potential left boundary. While the stack is not empty and the current height is less than the height of the top stack element (stk[-1][0]), we pop the top element (h, j):
     * Compute the area of the rectangle with height h and width i - j (from index j to i-1).
     * Update max_area if this area is larger.
     * Set start = j, extending the current bar’s potential left boundary to the popped bar’s start index, as the current height can form a rectangle back to j.
     * Push Current Bar: After processing, push (height, start) onto the stack, indicating that this height is valid from index start.
     * Handle Remaining Bars: After the loop, pop each remaining stack element (h, j), computing the area with height h and width n - j (from j to the end of the array). Update max_area if necessary.
     * Result: Return max_area as the largest rectangle area found.
     * Why This Works: The monotonic stack ensures that heights are processed in increasing order, allowing us to compute rectangle areas when a smaller height is encountered (limiting the height of previous taller bars). The stack tracks the left boundary of each height, enabling accurate width calculations. Processing remaining bars handles cases where rectangles extend to the array’s end.
     * Time and Space Complexity: Each bar is pushed and popped at most once, resulting in a time complexity of O(n), where n is the number of bars. The stack stores at most n elements, so the space complexity is O(n).
     * */

    private static int largestRectangleArea(int[] heights) {
        int n = heights.length;
        Stack<int[]> stk = new Stack<>();
        int maxArea = 0;

        for (int i = 0; i < n; i++) {
            int height = heights[i];
            int start = i;
            while (!stk.isEmpty() && height < stk.peek()[0]) {
                int[] popped = stk.pop();
                int h = popped[0];
                int j = popped[1];
                int w = i - j;
                maxArea = Math.max(maxArea, h * w);
                start = j;
            }
            stk.push(new int[]{height, start});
        }

        while (!stk.isEmpty()) {
            int[] popped = stk.pop();
            int h = popped[0];
            int j = popped[1];
            int w = n - j;
            maxArea = Math.max(maxArea, h * w);
        }

        return maxArea;
    }

    private static String arrayToString(int[] arr) {
        if (arr == null) return "null";
        if (arr.length == 0) return "[]";
        
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        // Test Case 1: Example from problem description
        int[] heights1 = {2, 1, 5, 6, 2, 3};
        System.out.println("Test 1 - Input: " + arrayToString(heights1));
        System.out.println("Expected: 10 (from bars with heights [5, 6])");
        System.out.println("Actual:   " + largestRectangleArea(heights1) + "\n");
        
        // Test Case 2: Single bar
        int[] heights2 = {4};
        System.out.println("Test 2 - Input: " + arrayToString(heights2));
        System.out.println("Expected: 4 (single bar)");
        System.out.println("Actual:   " + largestRectangleArea(heights2) + "\n");
        
        // Test Case 3: All bars same height
        int[] heights3 = {3, 3, 3, 3, 3};
        System.out.println("Test 3 - Input: " + arrayToString(heights3));
        System.out.println("Expected: 15 (all bars form one large rectangle)");
        System.out.println("Actual:   " + largestRectangleArea(heights3) + "\n");
        
        // Test Case 4: Increasing heights
        int[] heights4 = {1, 2, 3, 4, 5};
        System.out.println("Test 4 - Input: " + arrayToString(heights4));
        System.out.println("Expected: 9 (from bars with heights [3, 4, 5])");
        System.out.println("Actual:   " + largestRectangleArea(heights4) + "\n");
        
        // Test Case 5: Decreasing heights
        int[] heights5 = {5, 4, 3, 2, 1};
        System.out.println("Test 5 - Input: " + arrayToString(heights5));
        System.out.println("Expected: 9 (from bars with heights [5, 4] or [4, 3, 2])");
        System.out.println("Actual:   " + largestRectangleArea(heights5) + "\n");
        
        // Test Case 6: Empty input
        int[] heights6 = {};
        System.out.println("Test 6 - Input: " + arrayToString(heights6));
        System.out.println("Expected: 0 (no bars)");
        System.out.println("Actual:   " + largestRectangleArea(heights6) + "\n");
        
        // Test Case 7: Random case with multiple peaks
        int[] heights7 = {2, 1, 2};
        System.out.println("Test 7 - Input: " + arrayToString(heights7));
        System.out.println("Expected: 3 (from all bars)");
        System.out.println("Actual:   " + largestRectangleArea(heights7) + "\n");
        
        // Test Case 8: Large input with varying heights
        int[] heights8 = {6, 2, 5, 4, 5, 1, 6};
        System.out.println("Test 8 - Input: " + arrayToString(heights8));
        System.out.println("Expected: 12 (from bars with heights [5, 4, 5])");
        System.out.println("Actual:   " + largestRectangleArea(heights8));
    }
}


/**
 * Detailed Explanation
 * Understanding the Problem
 * The "Largest Rectangle in Histogram" problem challenges us to find the area of the largest rectangle that can be formed within a histogram. The histogram is represented as an array of integers, where each element indicates the height of a bar and all bars have equal width of 1 unit. A rectangle can span one or more adjacent bars, and its height is limited by the shortest bar within that span.
 *
 * Key Insight: Using a Monotonic Stack
 * A brute-force approach would involve checking every possible pair of start and end indices, calculating the minimum height within the range, and then computing the area. However, this would be inefficient. Instead, we use a monotonic increasing stack to keep track of the heights and their corresponding indices. This structure allows us to compute the largest rectangle in O(n) time by systematically managing and collapsing ranges of bars.
 *
 * Step-by-Step Strategy
 * Initialization:
 * Start with an empty stack and a variable max_area to store the largest area encountered.
 *
 * Iterate Through Histogram:
 * For each index i and corresponding height in the array:
 *
 * If the current height is greater than or equal to the top of the stack, push it with its index onto the stack.
 * If the current height is less, it means previous taller bars can't extend past this point. Pop from the stack and calculate the area using the popped height and the width derived from the current index and the index stored in the stack.
 * After popping, push the current height and its new valid start index (from the popped element) back onto the stack.
 * Post-Processing Remaining Stack:
 * After finishing the iteration, we may have some bars left in the stack. These bars extend all the way to the end of the array. Pop each and calculate the area using the formula height × (n - index).
 *
 * Why the Stack Approach Works
 * The stack-based method works efficiently because each bar is pushed and popped at most once. The stack maintains an increasing sequence of bar heights. When a smaller height is encountered, it acts as a boundary, triggering the calculation of maximal areas for all taller bars on the stack that can no longer extend beyond the current index.
 *
 * By recording the earliest index where each height is valid, we ensure that widths are correctly computed even as we collapse prior rectangles.
 *
 * Example Walkthrough
 * Consider the input [2, 1, 5, 6, 2, 3]. The algorithm builds the stack while maintaining increasing height order. When it reaches a height smaller than the stack top, it pops heights, calculates areas, and updates the max area. It ultimately finds that the rectangle covering [5,6] with height 5 yields the largest area of 10.
 *
 * Time and Space Complexity
 * Time Complexity: O(n). Each bar is pushed and popped from the stack at most once.
 *
 * Space Complexity: O(n). The stack may store up to n elements in the worst case.
 *
 * Conclusion
 * This problem is a classic demonstration of how a monotonic stack can be used to optimize problems involving ranges and boundaries. The algorithm is efficient and elegant, balancing complexity and performance in a way that is both optimal and easy to reason about once understood.
 * */