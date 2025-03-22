package recursion;

public class Power {

    public static int power (int base, int exp) {
        if(base < 0 || exp < 0) {
            return -1;
        }
        if (base == 0 || base == 1) {
            return base;
        }
        if (exp == 0) {
            return 1;
        }
        int res = 1;
        while (exp > 0) {
            res *= base;
            exp--;
        }
        return res;
    }

    public static int powerRecursion (int base, int exp) {
        if (base == 0 || base == 1) {
            return base;
        }

        if (exp == 0) {
            return 1;
        }

        if (base < 0 || exp < 0) {
            return -1;
        }

        return base * powerRecursion(base, exp - 1);
    }

    public static void main(String[] args) {
//        write test cases for power
//        System.out.println(power(2, 3));  // 8
//        System.out.println(power(2, 0));  // 1
//        System.out.println(power(0, 3));  // 0
//        System.out.println(power(1, 3));  // 1
//        System.out.println(power(2, 4));  // 16

//        write test case for powerRecursion
        System.out.println(powerRecursion(2, 3));  // 8
        System.out.println(powerRecursion(2, 0));  // 1
        System.out.println(powerRecursion(0, 3));  // 0
        System.out.println(powerRecursion(1, 3));  // 1
        System.out.println(powerRecursion(2, 4));  // 16
    }
}
