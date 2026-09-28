package algorithms.recursion;

/**
 * RECURSION BASICS: FACTORIAL & FIBONACCI
 * 
 * WHAT IT IS:
 * Recursion is a method calling itself to solve a smaller instance of the same problem.
 * 
 * WHEN TO USE THIS:
 * - Backtracking, Tree Traversals, Divide & Conquer (Merge/Quick sort), Dynamic Programming.
 * 
 * COMPLEXITY COMPARISON (Fibonacci):
 * 1. Plain Recursive: 
 *    - Time: O(2^N) [Terrible! Recalculates the same values repeatedly]
 *    - Space: O(N) stack
 * 2. Memoized (Top-Down DP): 
 *    - Time: O(N) [Saves answers in an array to avoid recalculation]
 *    - Space: O(N) stack + O(N) array
 * 3. Iterative (Bottom-Up DP): 
 *    - Time: O(N)
 *    - Space: O(1) [The ultimate optimization, only stores the last 2 values]
 */
public class FibonacciFactorial {

    // --- FACTORIAL ---
    public static int factorial(int n) {
        if (n <= 1) return 1;
        return n * factorial(n - 1);
    }

    // --- FIBONACCI (3 Variations) ---
    
    // 1. Plain Recursive O(2^N)
    public static int fibRecursive(int n) {
        if (n <= 1) return n;
        return fibRecursive(n - 1) + fibRecursive(n - 2);
    }

    // 2. Memoized Recursive O(N)
    public static int fibMemoized(int n, int[] memo) {
        if (n <= 1) return n;
        if (memo[n] != 0) return memo[n]; // Return cached answer!
        
        memo[n] = fibMemoized(n - 1, memo) + fibMemoized(n - 2, memo);
        return memo[n];
    }

    // 3. Iterative O(N) Time, O(1) Space
    public static int fibIterative(int n) {
        if (n <= 1) return n;
        int prev2 = 0;
        int prev1 = 1;
        int current = 0;
        
        for (int i = 2; i <= n; i++) {
            current = prev1 + prev2;
            prev2 = prev1;
            prev1 = current;
        }
        return current;
    }

    public static void main(String[] args) {
        System.out.println("--- FACTORIAL & FIBONACCI DEMO ---");
        System.out.println("Factorial of 5: " + factorial(5)); // 120
        
        int n = 10;
        System.out.println("\nFibonacci of " + n + ":");
        System.out.println("Recursive: " + fibRecursive(n));
        System.out.println("Memoized:  " + fibMemoized(n, new int[n + 1]));
        System.out.println("Iterative: " + fibIterative(n)); // All should print 55
    }
}
