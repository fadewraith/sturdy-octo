package recursion;

// DecimalToBinary.java
public class DecimalToBinary {

    public static int decimalToBinary (int n) {
        if (n == 0) {
            return 0;
        }
        return (n % 2) + (10 * decimalToBinary(n / 2));
    }

    // Without using in-built methods (using integers, no String)
    // Using Recursion
    public static void decimalToBinaryRecursive(int decimal) {
        if (decimal > 1) {
            decimalToBinaryRecursive(decimal / 2);  // Recursively call for next division
        }
        System.out.print(decimal % 2);  // Print the binary digit as we return from recursion
    }

    // Using while loop
    public static void decimalToBinaryWhile(int decimal) {
        int binary = 0, place = 1;
        while (decimal > 0) {
            binary += (decimal % 2) * place;
            decimal /= 2;
            place *= 10;  // Move to the next place value
        }
        System.out.print(binary);  // Print the binary number
    }

    // Using do-while loop
    public static void decimalToBinaryDoWhile(int decimal) {
        int binary = 0, place = 1;
        do {
            binary += (decimal % 2) * place;
            decimal /= 2;
            place *= 10;  // Move to the next place value
        } while (decimal > 0);
        System.out.print(binary);  // Print the binary number
    }

    // Using for loop
    public static void decimalToBinaryFor(int decimal) {
        int binary = 0, place = 1;
        for (int i = decimal; i > 0; i /= 2) {
            binary += (i % 2) * place;
            place *= 10;  // Move to the next place value
        }
        System.out.print(binary);  // Print the binary number
    }

    // Test cases for Decimal to Binary conversion
    public static void testDecimalToBinary() {
        int decimal = 45;
        System.out.print("Using Recursion: ");
        decimalToBinaryRecursive(decimal);
        System.out.println();

        System.out.print("Using While Loop: ");
        decimalToBinaryWhile(decimal);
        System.out.println();

        System.out.print("Using Do-While Loop: ");
        decimalToBinaryDoWhile(decimal);
        System.out.println();

        System.out.print("Using For Loop: ");
        decimalToBinaryFor(decimal);
        System.out.println();
    }

    public static void main(String[] args) {
        testDecimalToBinary();
    }
}
