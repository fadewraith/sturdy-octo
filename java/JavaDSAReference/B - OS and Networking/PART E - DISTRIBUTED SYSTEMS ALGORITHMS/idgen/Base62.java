package distributed.idgen;

/**
 * BASE62 ENCODING (URL shortener codes)
 *
 * WHAT IT IS:
 * Writes a number in base 62 using the characters 0-9, a-z, A-Z - all URL-safe,
 * no '+', '/' or '=' like Base64. 62^7 = 3.5 trillion, so 7 characters cover a
 * huge ID space:  125 -> "21",  bit.ly/3xYz9Ab style codes.
 *
 * WHEN TO USE THIS:
 * - URL shorteners: database id (or Snowflake id) -> short code, and back.
 * - Compact, readable identifiers in links, coupons, invite codes.
 *
 * STRATEGY:
 * Encode: repeatedly take value % 62 as the next LOWEST digit, value /= 62, and
 *   reverse at the end (same as printing a number in any base).
 * Decode: value = value * 62 + digit, left to right (Horner's rule), with an
 *   overflow check.
 * Negative longs are treated as UNSIGNED 64-bit (Long.divideUnsigned), so every
 * long has a code - handy for hash values.
 * Note: sequential ids give guessable codes (abc1, abc2, ...). Shorteners that care
 * scramble the id first (e.g. multiply by an odd constant mod 2^64, or a small
 * Feistel cipher) - the mapping stays one-to-one.
 *
 * COMPLEXITY:
 * O(number of digits) = O(log62 value) <= 11 characters for a 64-bit value.
 */
public class Base62 {

    public static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int BASE = 62;

    /** Encodes the value as an UNSIGNED 64-bit number. */
    public static String encode(long value) {
        if (value == 0) return "0";
        StringBuilder sb = new StringBuilder();
        while (value != 0) {
            int digit = (int) Long.remainderUnsigned(value, BASE);
            sb.append(ALPHABET.charAt(digit));
            value = Long.divideUnsigned(value, BASE);
        }
        return sb.reverse().toString();
    }

    private static int digitOf(char c) {
        if (c >= '0' && c <= '9') return c - '0';
        if (c >= 'a' && c <= 'z') return c - 'a' + 10;
        if (c >= 'A' && c <= 'Z') return c - 'A' + 36;
        throw new IllegalArgumentException("not a base62 character: '" + c + "'");
    }

    /** Decodes back to the (unsigned) 64-bit value. Throws on bad characters or overflow. */
    public static long decode(String code) {
        if (code.isEmpty()) throw new IllegalArgumentException("empty code");
        long value = 0;
        // largest value that can still be multiplied by 62 without passing 2^64
        long limit = Long.divideUnsigned(-1L, BASE);
        for (int i = 0; i < code.length(); i++) {
            int d = digitOf(code.charAt(i));
            if (Long.compareUnsigned(value, limit) > 0) throw new ArithmeticException("value exceeds 64 bits");
            long next = value * BASE + d;
            if (Long.compareUnsigned(next, value * BASE) < 0) throw new ArithmeticException("value exceeds 64 bits");
            value = next;
        }
        return value;
    }

    public static void main(String[] args) {
        System.out.println("--- BASE62 DEMO ---");
        long[] ids = {0, 61, 62, 125, 1_000_000, 3_521_614_606_207L, Long.MAX_VALUE, -1L};
        for (long id : ids) {
            String code = encode(id);
            System.out.println(Long.toUnsignedString(id) + " -> \"" + code + "\" -> " + Long.toUnsignedString(decode(code)));
        }
        // 0 -> "0", 61 -> "Z", 62 -> "10", 125 -> "21", 62^7 - 1 -> "ZZZZZZZ", 2^64 - 1 -> "lYGhA16ahyf"
    }
}
