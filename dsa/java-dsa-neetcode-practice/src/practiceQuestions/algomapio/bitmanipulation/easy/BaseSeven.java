package practiceQuestions.algomapio.bitmanipulation.easy;

public class BaseSeven {

    private static String solution(int num) {
        // Time and Space: O(log_7(N))
        if(num == 0) return "0";

        int originalNum = num;
        num = Math.abs(num);
        StringBuilder remainders = new StringBuilder();

        while(num > 0) {
            int remainder = num % 7;
            remainders.append(remainder);
            num /= 7;
        }

        if(originalNum < 0) remainders.append('-');

        return remainders.reverse().toString();
    }

    public static void main(String[] args) {
        // write test cases
        System.out.println(solution(100));
        System.out.println(solution(-7));
        System.out.println(solution(0));
        System.out.println(solution(7));
        System.out.println(solution(1));
        System.out.println(solution(-1));
        System.out.println(solution(10));
    }
}


/**
 * Detailed Explanation
 * Understanding the Base 7 Conversion Problem
 * The "Base 7" problem asks you to convert a given integer into its base-7 string representation. Unlike base-10 (decimal) numbers we're used to, base-7 uses only the digits 0 through 6. For example, the decimal number 100 in base-7 is represented as "202". The challenge includes converting both positive and negative integers correctly, returning the result as a string.
 *
 * Approach to Solving the Problem
 * The core idea behind converting a number to base-7 is similar to converting it to any other base: repeatedly divide the number by 7 and record the remainders. These remainders, read in reverse order, form the base-7 representation.
 *
 * First, we handle the special case where the number is 0 by immediately returning "0". Otherwise, we keep track of whether the number is negative so that we can add a minus sign at the end. We then take the absolute value of the number and repeatedly divide it by 7, storing the remainders. Once the number becomes 0, we reverse the list of remainders and join them into a final string. If the original number was negative, we prefix the result with a "-".
 *
 * Time and Space Complexity
 * Time Complexity: O(log₇ n), because we divide the number by 7 in each iteration, reducing it logarithmically.
 * Space Complexity: O(log₇ n), for storing the digits of the base-7 representation in a list.
 * Why This Approach Works
 * This method works because it's a direct application of how number systems operate. The remainder operation captures each digit in the base-7 system starting from the least significant, and reversing the sequence provides the correct order. It handles negative numbers cleanly by processing the absolute value and reintroducing the sign only after forming the number.
 *
 * Conclusion
 * Converting numbers to a different base is a foundational algorithm in computer science, and this problem provides a clear example of how to implement it manually. Understanding this logic is useful in domains such as number theory, low-level programming, and custom encoding schemes.
 * */