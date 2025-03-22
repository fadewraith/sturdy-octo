package recursion;

public class LCM {

    public static int lcmRecursion(int a, int b) {
        return (a * b) / GCD.gcd(a, b);
    }

    public static int lcmOfArray(int[] numbers, int n) {
        if (n == 1) {
            return numbers[0];
        }
        return lcmRecursion(numbers[n - 1], lcmOfArray(numbers, n - 1));
    }

    public static int lcmWhile(int a, int b) {
        int lcm = (a > b) ? a : b;
        while (true) {
            if (lcm % a == 0 && lcm % b == 0) {
                return lcm;
            }
            lcm++;
        }
    }

    public static int lcmFor(int a, int b) {
        int lcm = (a > b) ? a : b;
        for (; ; lcm++) {
            if (lcm % a == 0 && lcm % b == 0) {
                return lcm;
            }
        }
    }

    public static int lcmDoWhile(int a, int b) {
        int lcm = (a > b) ? a : b;
        do {
            if (lcm % a == 0 && lcm % b == 0) {
                return lcm;
            }
            lcm++;
        } while (true);
    }

    public static void main(String[] args) {
        System.out.println(lcmDoWhile(12, 15)); // 60
        System.out.println(lcmFor(7, 9));   // 63
        System.out.println(lcmWhile(21, 14)); // 42
        System.out.println(lcmRecursion(18, 19)); // 342
        int[] numbers = { 12, 15, 20, 40 };
        System.out.println(lcmOfArray(numbers, numbers.length)); // 120
    }
}
