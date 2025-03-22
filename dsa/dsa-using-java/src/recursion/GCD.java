package recursion;

public class GCD {

    public static int gcd (int a,  int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    // Recursive method to find the GCD of an array of numbers
    public static int findGCD(int[] numbers, int index) {
        // Base case: if we've reached the last element, return that element
        if (index == numbers.length - 1) {
            return numbers[index];
        }
        // Recursive case: calculate the GCD of the current number and the result from the rest of the array
        return gcd(numbers[index], findGCD(numbers, index + 1));
    }

    public static int gcdTernary (int a, int b) {
        return b == 0 ? a : gcdTernary(b, a % b);
    }

    public static int gcdWhileLoop (int a, int b) {
        while (b != 0) {
            int temp = a;
            a = b;
            b = temp % b;
        }
        return a;
    }

    public static int gcdForLoop(int a, int b) {
        int gcd = 1;
        for (int i = Math.min(a, b); i > 0; i--) {
            if (a % i == 0 && b % i == 0) {
                gcd = i;
                break;
            }
        }
        return gcd;
    }

    public static int gcdDoWhile(int a, int b) {
        int temp;
        do {
            temp = a % b;
            a = b;
            b = temp;
        } while (b != 0);
        return a;
    }

    public static void main(String[] args) {
        System.out.println(gcd(10, 15));          // 5
        System.out.println(gcdTernary(10, 15));   // 5
        System.out.println(gcdWhileLoop(10, 15));      // 5
        System.out.println(gcdForLoop(10, 15));   // 5
        System.out.println(gcdDoWhile(10, 15));   // 5
        int[] numbers = { 24, 36, 60, 48 };
        int result = findGCD(numbers, 0);  // Start the recursion from the first element
        System.out.println("The GCD of the numbers is: " + result);
    }
}
