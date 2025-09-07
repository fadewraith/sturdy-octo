package practiceQuestions.algomapio.bitmanipulation.easy;

public class NumberOfOneBits {

    // Time: O(Bits), Space: O(1)
    private static int solution(int n) {
        int ans = 0;
        while(n != 0) {
            ans++;
            n &= (n - 1);
        }

        return ans;
    }

    public static void main(String[] args) {
        System.out.println(solution(11));
        System.out.println(solution(128));
        System.out.println(solution(0));
    }
}

/**
 * Detailed Explanation
 * Understanding the Problem
 * The objective of this problem is to calculate the Hamming weight of an unsigned integer n, which is the number of 1s present in its binary representation. This type of bit manipulation problem is common in systems programming, embedded development, and low-level algorithm design.
 *
 * Bitwise Counting Strategy
 * To count the number of 1 bits efficiently, we use a bit manipulation trick involving the operation n & (n - 1). This operation removes the rightmost 1 bit from n. Each time this operation is performed, it reduces the number of 1s by one.
 *
 * We initialize a counter ans to 0. Then, in a loop that continues while n ≠ 0:
 *
 * We increment ans by 1 to count the current 1 bit.
 * We update n to n & (n - 1), which clears the rightmost 1 bit.
 * This loop continues exactly as many times as there are 1 bits in the number, making it highly efficient.
 *
 * Example Walkthrough
 * Suppose n = 11, which is 1011 in binary:
 *
 * First iteration: n = 1011, increment ans = 1, then n = 1010
 * Second iteration: n = 1010, increment ans = 2, then n = 1000
 * Third iteration: n = 1000, increment ans = 3, then n = 0000
 * The loop ends when n becomes 0, and we return ans = 3, which correctly reflects the number of 1 bits in 11.
 *
 * Why This Works
 * The expression n & (n - 1) works by flipping the rightmost 1 bit in n to 0. For example, subtracting 1 from a binary number inverts the rightmost 1 bit and turns all less significant bits into 1s. When this result is ANDed with the original number, the rightmost 1 is cleared. This process happens exactly k times, where k is the number of 1 bits in n, making the method both intuitive and performant.
 *
 * Time and Space Complexity
 * Time Complexity: O(k), where k is the number of 1 bits in n. For a 32-bit integer, this means a maximum of 32 iterations, making it effectively O(1) in practice.
 *
 * Space Complexity: O(1), since the algorithm uses only a single integer counter and modifies the input n without requiring additional data structures.
 *
 * Conclusion
 * The number of 1 bits in a binary number can be counted efficiently using bit manipulation. By using the n & (n - 1) trick, we reduce the problem to a simple loop that directly corresponds to the number of set bits. This makes the approach both elegant and optimal for fixed-width integers, and it’s a great example of why understanding bitwise operations can lead to cleaner and faster solutions.
 * */