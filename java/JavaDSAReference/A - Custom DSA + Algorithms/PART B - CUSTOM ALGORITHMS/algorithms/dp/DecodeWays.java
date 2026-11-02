package algorithms.dp;

/**
 * DECODE WAYS
 *
 * WHAT IT IS:
 * Letters are encoded as numbers: A=1, B=2, ..., Z=26. Given a digit string, how many
 * ways can it be decoded?  "226" -> "BZ" (2 26), "VF" (22 6), "BBF" (2 2 6) = 3.
 *
 * WHEN TO USE THIS:
 * A textbook "count the ways to split a sequence" DP - the same shape as climbing
 * stairs (1 or 2 steps at a time), but with rules for which steps are allowed.
 *
 * STRATEGY:
 * ways[i] = number of decodings of the first i digits.
 *   - The last letter used ONE digit s[i-1]:  allowed if it is '1'..'9'
 *       -> add ways[i-1]
 *   - The last letter used TWO digits s[i-2..i-1]: allowed if they form 10..26
 *       -> add ways[i-2]
 * ways[0] = 1 (the empty string has one decoding: nothing).
 * Watch out for '0': it can't stand alone, only as part of "10" or "20".
 * "06" is NOT 6 - leading zeros are invalid.
 * Only the last two values are needed, so two variables replace the array.
 *
 * COMPLEXITY:
 * Time: O(N)
 * Space: O(1)
 */
public class DecodeWays {

    public static long numDecodings(String s) {
        long prev2 = 1; // ways[i-2]
        long prev1 = 1; // ways[i-1]  (both start as ways[0] = 1)
        for (int i = 1; i <= s.length(); i++) {
            char c = s.charAt(i - 1);
            if (c < '0' || c > '9') throw new IllegalArgumentException("digits only");
            long current = 0;
            if (c != '0') current += prev1;                       // single digit 1..9
            if (i >= 2) {
                int two = (s.charAt(i - 2) - '0') * 10 + (c - '0');
                if (s.charAt(i - 2) != '0' && two <= 26) current += prev2; // pair 10..26
            }
            prev2 = prev1;
            prev1 = current;
        }
        return s.isEmpty() ? 1 : prev1;
    }

    // Collects every decoding (for small inputs / learning)
    public static String[] allDecodings(String s) {
        int total = (int) numDecodings(s);
        String[] result = new String[total];
        int[] count = {0};
        collect(s, 0, new StringBuilder(), result, count);
        return result;
    }

    private static void collect(String s, int i, StringBuilder sb, String[] out, int[] count) {
        if (i == s.length()) { out[count[0]++] = sb.toString(); return; }
        if (s.charAt(i) == '0') return;
        sb.append((char) ('A' + s.charAt(i) - '1'));
        collect(s, i + 1, sb, out, count);
        sb.setLength(sb.length() - 1);
        if (i + 1 < s.length()) {
            int two = (s.charAt(i) - '0') * 10 + (s.charAt(i + 1) - '0');
            if (two <= 26) {
                sb.append((char) ('A' + two - 1));
                collect(s, i + 2, sb, out, count);
                sb.setLength(sb.length() - 1);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("--- DECODE WAYS DEMO ---");
        System.out.println("\"12\"  -> " + numDecodings("12"));   // 2 (AB, L)
        System.out.println("\"226\" -> " + numDecodings("226") + " " + java.util.Arrays.toString(allDecodings("226"))); // 3
        System.out.println("\"06\"  -> " + numDecodings("06"));   // 0
        System.out.println("\"10\"  -> " + numDecodings("10"));   // 1 (J)
        System.out.println("\"1111111111\" -> " + numDecodings("1111111111")); // 89 (a Fibonacci number)
    }
}
