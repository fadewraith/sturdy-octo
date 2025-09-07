package practiceQuestions.algomapio.stacks.medium;

import java.util.Stack;

public class DailyTemperatures {

    /**
     * Step-by-Step Thought Process
     * Understand the problem: For each day, find the number of days until a warmer day occurs, returning an array of these wait times.
     * Initialize an array answer of size n (length of temperatures) with zeros.
     * Initialize an empty stack to store tuples of (temperature, index).
     * Iterate through each index i and temperature t in the temperatures array.
     * While the stack is not empty and the top stack temperature is less than t, pop the stack’s temperature and index.
     * Calculate the wait time as i minus the popped index and store it in answer at the popped index.
     * Push the current (t, i) onto the stack.
     * Return the answer array.
     * */

    private static int[] solution(int[] temperatures) {
        int n = temperatures.length;
        int[] answer = new int[n];
        Stack<int[]> stack = new Stack<>();

        for (int i = 0; i < n; i++) {
            int temp = temperatures[i];
            while (!stack.isEmpty() && stack.peek()[0] < temp) {
                int[] prev = stack.pop();
                answer[prev[1]] = i - prev[1];
            }
            stack.push(new int[]{temp, i});
        }
        return answer;
    }

    private static String arrayToString(int[] arr) {
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
        // Test case 1: Example from problem description
        int[] test1 = {73, 74, 75, 71, 69, 72, 76, 73};
        int[] expected1 = {1, 1, 4, 2, 1, 1, 0, 0};
        int[] result1 = solution(test1);
        System.out.println("Test 1 - Input: [73,74,75,71,69,72,76,73]");
        System.out.println("Expected: " + arrayToString(expected1));
        System.out.println("Actual:   " + arrayToString(result1));
        System.out.println();
        
        // Test case 2: All decreasing temperatures
        int[] test2 = {90, 80, 70, 60, 50};
        int[] expected2 = {0, 0, 0, 0, 0};
        int[] result2 = solution(test2);
        System.out.println("Test 2 - Input: [90, 80, 70, 60, 50]");
        System.out.println("Expected: " + arrayToString(expected2));
        System.out.println("Actual:   " + arrayToString(result2));
        System.out.println();
        
        // Test case 3: All same temperatures
        int[] test3 = {30, 30, 30, 30, 30};
        int[] expected3 = {0, 0, 0, 0, 0};
        int[] result3 = solution(test3);
        System.out.println("Test 3 - Input: [30, 30, 30, 30, 30]");
        System.out.println("Expected: " + arrayToString(expected3));
        System.out.println("Actual:   " + arrayToString(result3));
        System.out.println();
        
        // Test case 4: Single temperature
        int[] test4 = {100};
        int[] expected4 = {0};
        int[] result4 = solution(test4);
        System.out.println("Test 4 - Input: [100]");
        System.out.println("Expected: " + arrayToString(expected4));
        System.out.println("Actual:   " + arrayToString(result4));
        System.out.println();
        
        // Test case 5: Random temperatures
        int[] test5 = {55, 38, 53, 81, 61, 93, 97, 32, 43, 78};
        int[] expected5 = {3, 1, 1, 2, 1, 1, 0, 1, 1, 0};
        int[] result5 = solution(test5);
        System.out.println("Test 5 - Input: [55,38,53,81,61,93,97,32,43,78]");
        System.out.println("Expected: " + arrayToString(expected5));
        System.out.println("Actual:   " + arrayToString(result5));
    }
}


/**
 * Detailed Explanation
 * Understanding the Problem: Daily Temperatures
 * The “Daily Temperatures” problem asks us to determine, for each day, how many days one must wait until a warmer temperature occurs. If there is no future day with a warmer temperature, the answer should be 0 for that day.
 *
 * For example:
 *
 * Input: [73, 74, 75, 71, 69, 72, 76, 73]
 * Output: [1, 1, 4, 2, 1, 1, 0, 0]
 * Each element in the output array represents the number of days until a warmer temperature.
 * Why This Problem Matters
 * This problem is an excellent introduction to using monotonic stacks — stacks that maintain a specific order (in this case, decreasing temperatures). It's commonly used in interview questions to test your ability to efficiently track future or next-greater elements in a sequence.
 *
 * Optimal Approach: Monotonic Stack
 * Instead of checking each future day for a warmer temperature (which would be O(n²)), we can use a stack to track the indices of temperatures in decreasing order. When a warmer temperature is found, we resolve the earlier days stored in the stack.
 *
 * Steps:
 * Initialize an array answer of the same length as the input, filled with 0.
 * Initialize an empty stack that will store indices of the temperature array.
 * Loop through the temperature array using index i:
 * While the stack is not empty and temperatures[i] > temperatures[stack[top]]:
 * Pop the index from the stack.
 * Calculate the number of days waited: i - popped_index.
 * Set answer[popped_index] to that value.
 * Push the current index i onto the stack.
 * After the loop, any indices remaining in the stack have no warmer future day, so their entries remain 0.
 * Return the answer array.
 * Example Walkthrough
 * Input: [73, 74, 75, 71, 69, 72, 76, 73]
 * Stack keeps track of indices with unresolved temperatures:
 *
 * Day 0: 73 → stack: [0]
 * Day 1: 74 > 73 → pop 0, answer[0] = 1 → stack: [] → push 1
 * Day 2: 75 > 74 → pop 1, answer[1] = 1 → stack: [] → push 2
 * Day 3: 71 < 75 → push 3
 * Day 4: 69 < 71 → push 4
 * Day 5: 72 > 69 → pop 4, answer[4] = 1; 72 > 71 → pop 3, answer[3] = 2 → push 5
 * Day 6: 76 > 72 → pop 5, answer[5] = 1; 76 > 75 → pop 2, answer[2] = 4 → push 6
 * Day 7: 73 < 76 → push 7
 * Final answer: [1, 1, 4, 2, 1, 1, 0, 0]
 *
 * Time and Space Complexity
 * Time Complexity: O(n), where n is the number of days. Each index is pushed and popped from the stack at most once.
 * Space Complexity: O(n) for the output array and the stack.
 *
 * Edge Cases to Consider
 * All temperatures in decreasing order → all answers are 0
 * All temperatures the same → all answers are 0
 * Only one temperature → answer is [0]
 * Empty input → return an empty list
 * Conclusion
 * The “Daily Temperatures” problem is a classic use case for monotonic stacks. It teaches you how to efficiently handle problems where you're asked to find the next greater element or to track dependencies on future values. Understanding this approach builds a strong foundation for similar problems in array and stack manipulation.
 * */