package recursion;

public class SumOfDigits {

    public static int sumOfDigits (int n) {
        n = Math.abs(n);
        int res = 0;
        while (n > 0) {
            res += (n % 10);;
            n /= 10;
        }
        return res;
    }

    public static int sumOfDigitsRecursion (int n) {
        if (n <= 0) {
            return 0;
        }
        return (n % 10) + sumOfDigitsRecursion(n / 10);
    }

    public static void main(String[] args) {
        System.out.println(sumOfDigits(12345));  // 15
        System.out.println(sumOfDigits(-12345)); // 15
        System.out.println(sumOfDigits(0));      // 0
        System.out.println(sumOfDigits(9));      // 9
        System.out.println(sumOfDigits(1000000000));

//        write test cases for sumOfDigitsRecursion, add the msg for each output
        System.out.println(sumOfDigitsRecursion(12345));  // 15
        System.out.println(sumOfDigitsRecursion(-12345)); // 15
        System.out.println(sumOfDigitsRecursion(0));      // 0
        System.out.println(sumOfDigitsRecursion(9));      // 9
        System.out.println(sumOfDigitsRecursion(1000000000));
    }
}
