package practiceQuestions.algomapio.bitmanipulation.easy;

import java.math.BigInteger;

public class AddBinary {

    // Time: O(A + B)
    // Space: O(1)

    private static String solution(String a, String b) {
        // convert the binary strings to BigInteger
        BigInteger x = new BigInteger(a, 2);
        BigInteger y = new BigInteger(b, 2);

        // perform the binary addition using bitwise operations
        while(y.compareTo(BigInteger.ZERO) != 0) {
            BigInteger withoutCarry = x.xor(y);
            BigInteger carry = x.and(y).shiftLeft(1);
            x = withoutCarry;
            y = carry;
        }

        return x.toString(2);

    }

    public static void main(String[] args) {
        System.out.println(solution("11", "1"));
        System.out.println(solution("1010", "1011"));
        System.out.println(solution("1111", "1111"));
        System.out.println(solution("1111", "1111"));
    }


}


/**
 * Detailed Explanation
 * Understanding the Add Binary Problem
 * The Add Binary problem requires performing binary addition on two input strings representing binary numbers. This is a classic bit manipulation task that mimics how addition works at the binary level, making it particularly relevant for technical interviews and low-level programming.
 *
 * The goal is to return the sum of these binary numbers as a string, without converting them to integers in a naïve or language-specific way. This ensures we build a solution that handles large inputs that could overflow integer limits in some environments.
 *
 * Strategy for Adding Binary Strings
 * A straightforward and effective way to solve this is to simulate binary addition using a loop. Start by initializing two pointers at the end of each binary string. Loop through both strings from right to left, adding the corresponding bits along with a carry value. Append the result of each bit addition to a list, which is reversed at the end to produce the final binary string.
 *
 * Alternatively, for a more elegant and performant solution, you can convert the strings to integers using base-2 conversion and use bitwise operations to simulate binary addition:
 *
 * XOR (a ^ b): Adds the bits without considering the carry.
 * AND (a & b) << 1: Calculates the carry by identifying positions where both bits are 1, and shifts it left for the next position.
 * Repeat until there’s no carry left (i.e., b becomes 0).
 * Finally, convert the result back to a binary string and remove the "0b" prefix that comes from Python’s binary format.
 *
 * Time and Space Complexity
 * Time Complexity: O(max(m, n)), where m and n are the lengths of the input strings. Each bit is processed once.
 * Space Complexity: O(max(m, n)), as the output string or list storing the result will grow with the size of the inputs.
 * Why This Approach Works
 * This approach mimics the exact process used in hardware and low-level programming for binary addition, making it extremely efficient. The use of XOR and AND for summing and carrying ensures constant-time operations on each bit, which scales linearly with input size. It also avoids the need for string-to-integer conversion in languages that don't support big integers.
 *
 * Conclusion
 * Solving the Add Binary problem improves your understanding of bitwise operations, carry propagation, and string manipulation. It’s a foundational problem that paves the way for mastering binary math, bit manipulation algorithms, and efficient number processing in memory-constrained systems.
 * */