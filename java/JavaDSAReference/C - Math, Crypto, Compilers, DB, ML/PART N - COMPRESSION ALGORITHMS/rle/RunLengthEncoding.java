package compression.rle;

/**
 * WHAT IT IS: A simple form of lossless data compression where sequences of same data values are stored as single data value and count.
 * STRATEGY: Iterate through the string. Keep a counter. When the character changes, append the character and its count to the result.
 * TIME/SPACE COMPLEXITY: O(N) time where N is string length. Space: O(N) for output string.
 * REAL-WORLD ANALOGY / USE CASE: Compressing simple bitmaps or images with large areas of single colors.
 * WHEN TO USE / COMBINATION: When data has many consecutive identical elements. Not good for data with high entropy.
 * 
 * PSEUDOCODE:
 * for each char in string:
 *   if char == prev_char: count++
 *   else: output prev_char + count, prev_char = char, count = 1
 */
public class RunLengthEncoding {
    /**
     * Output format: <char><count> repeated, e.g. "AAAABBB" -> "A4B3".
     * Counts can have several digits ("C10"), so the input itself must not
     * contain digits - otherwise "a111" -> "a113" could mean "a" x 113.
     */
    public static String encode(String text) {
        if (text == null || text.isEmpty()) return "";
        for (int i = 0; i < text.length(); i++) {
            if (Character.isDigit(text.charAt(i))) {
                throw new IllegalArgumentException("Text RLE format cannot encode digits (ambiguous with counts): " + text);
            }
        }
        StringBuilder sb = new StringBuilder();
        int count = 1;
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) == text.charAt(i - 1)) {
                count++;
            } else {
                sb.append(text.charAt(i - 1)).append(count);
                count = 1;
            }
        }
        sb.append(text.charAt(text.length() - 1)).append(count);
        return sb.toString();
    }

    public static String decode(String text) {
        if (text == null || text.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < text.length()) {
            char c = text.charAt(i++);
            // Read ALL digits of the count, not just one ("C10" means 10 C's)
            int count = 0;
            int digitsStart = i;
            while (i < text.length() && Character.isDigit(text.charAt(i))) {
                count = count * 10 + (text.charAt(i) - '0');
                i++;
            }
            if (i == digitsStart) {
                throw new IllegalArgumentException("Missing count after '" + c + "' in: " + text);
            }
            for (int j = 0; j < count; j++) sb.append(c);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String test1 = "AAAABBBCCDAA";
        String enc1 = encode(test1);
        System.out.println("Original: " + test1);
        System.out.println("Encoded: " + enc1);
        System.out.println("Decoded: " + decode(enc1));

        String test2 = "A";
        String enc2 = encode(test2);
        System.out.println("Original: " + test2);
        System.out.println("Encoded: " + enc2);
        System.out.println("Decoded: " + decode(enc2));

        String test3 = "BCCCCCCCCCCCCA"; // a run of 12 -> needs a 2-digit count
        String enc3 = encode(test3);
        System.out.println("Original: " + test3);
        System.out.println("Encoded: " + enc3);
        System.out.println("Decoded: " + decode(enc3));
    }
}
