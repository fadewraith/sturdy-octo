package recursion;

// BinaryToDecimal.java
public class BinaryToDecimal {

    // Without using in-built methods (using integers, no String)
    // Using Recursion
    public static int binaryToDecimalRecursive(int binary) {
        return binaryToDecimalRecursive(binary, 0, 0);
    }

    private static int binaryToDecimalRecursive(int binary, int place, int result) {
        if (binary == 0) {
            return result;
        }
        int bit = binary % 10;  // Get the last bit (0 or 1)
        return binaryToDecimalRecursive(binary / 10, place + 1, result + bit * (int) Math.pow(2, place));
    }

    // Using while loop
    public static int binaryToDecimalWhile(int binary) {
        int decimal = 0, place = 0;
        while (binary > 0) {
            int bit = binary % 10;  // Get the last bit
            decimal += bit * (int) Math.pow(2, place);
            binary /= 10;  // Move to the next bit
            place++;
        }
        return decimal;
    }

    // Using do-while loop
    public static int binaryToDecimalDoWhile(int binary) {
        int decimal = 0, place = 0;
        do {
            int bit = binary % 10;  // Get the last bit
            decimal += bit * (int) Math.pow(2, place);
            binary /= 10;  // Move to the next bit
            place++;
        } while (binary > 0);
        return decimal;
    }

    // Using for loop
    public static int binaryToDecimalFor(int binary) {
        int decimal = 0, place = 0;
        for (int i = binary; i > 0; i /= 10) {
            int bit = i % 10;  // Get the last bit
            decimal += bit * (int) Math.pow(2, place);
            place++;
        }
        return decimal;
    }

    // Test cases for Binary to Decimal conversion
    public static void testBinaryToDecimal() {
        int binary = 101101;
        System.out.println("Using Recursion: " + binaryToDecimalRecursive(binary));
        System.out.println("Using While Loop: " + binaryToDecimalWhile(binary));
        System.out.println("Using Do-While Loop: " + binaryToDecimalDoWhile(binary));
        System.out.println("Using For Loop: " + binaryToDecimalFor(binary));
    }

    public static void main(String[] args) {
        testBinaryToDecimal();
    }
}
