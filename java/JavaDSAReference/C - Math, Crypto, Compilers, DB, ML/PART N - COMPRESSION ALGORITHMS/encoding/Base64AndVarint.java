package compression.encoding;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

/**
 * BINARY-TO-TEXT AND INTEGER ENCODINGS: Base64, Varint (LEB128), ZigZag
 *
 * 1. BASE64 (RFC 4648)
 *    WHAT: represent arbitrary bytes using 64 printable characters, so binary data
 *    survives text-only channels (email/MIME, JSON, data: URLs, JWT, PEM keys,
 *    HTTP Basic auth). Costs 33% more space.
 *    HOW: take 3 bytes = 24 bits, split into four 6-bit groups, map each to
 *    A-Z a-z 0-9 + /. A final group of 1 or 2 bytes is padded with '=' to a
 *    multiple of 4 characters. The URL-safe variant uses - and _ instead of + and /
 *    (and often drops the padding), so it can sit in URLs and filenames.
 *
 * 2. VARINT / LEB128 (unsigned)
 *    WHAT: small integers in fewer bytes. Each byte carries 7 bits of the number
 *    (least significant group first); the high bit says "more bytes follow".
 *      1 -> 01,   300 -> AC 02,   2^63 -> 10 bytes
 *    Used in Protocol Buffers, WebAssembly, SQLite records, Git pack files, DWARF.
 *
 * 3. ZIGZAG (for signed integers)
 *    Plain varint makes -1 (all 64 bits set) cost 10 bytes. ZigZag interleaves signs:
 *      0 -> 0, -1 -> 1, 1 -> 2, -2 -> 3, 2 -> 4 ...   encode: (n << 1) ^ (n >> 63)
 *    so numbers near zero stay small either way (protobuf sint32/sint64).
 *
 * COMPLEXITY:
 * All O(n) in the number of bytes. Varint: at most 10 bytes per 64-bit value.
 */
public class Base64AndVarint {

    private static final String STD = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    private static final String URL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_";

    // ---------------- Base64 ----------------
    public static String base64Encode(byte[] data, boolean urlSafe, boolean pad) {
        String alphabet = urlSafe ? URL : STD;
        StringBuilder sb = new StringBuilder((data.length + 2) / 3 * 4);
        int i = 0;
        for (; i + 2 < data.length; i += 3) {                 // full 3-byte groups
            int n = ((data[i] & 0xff) << 16) | ((data[i + 1] & 0xff) << 8) | (data[i + 2] & 0xff);
            sb.append(alphabet.charAt(n >>> 18)).append(alphabet.charAt((n >>> 12) & 63))
              .append(alphabet.charAt((n >>> 6) & 63)).append(alphabet.charAt(n & 63));
        }
        int rest = data.length - i;
        if (rest == 1) {
            int n = (data[i] & 0xff) << 16;
            sb.append(alphabet.charAt(n >>> 18)).append(alphabet.charAt((n >>> 12) & 63));
            if (pad) sb.append("==");
        } else if (rest == 2) {
            int n = ((data[i] & 0xff) << 16) | ((data[i + 1] & 0xff) << 8);
            sb.append(alphabet.charAt(n >>> 18)).append(alphabet.charAt((n >>> 12) & 63)).append(alphabet.charAt((n >>> 6) & 63));
            if (pad) sb.append('=');
        }
        return sb.toString();
    }

    public static String base64Encode(byte[] data) { return base64Encode(data, false, true); }

    /** Decodes standard or URL-safe Base64, with or without padding. */
    public static byte[] base64Decode(String s) {
        int end = s.length();
        while (end > 0 && s.charAt(end - 1) == '=') end--;
        if (s.length() - end > 2 || end % 4 == 1) throw new IllegalArgumentException("invalid Base64 length");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int buffer = 0, bits = 0;
        for (int i = 0; i < end; i++) {
            char c = s.charAt(i);
            int v = STD.indexOf(c);
            if (v < 0) v = URL.indexOf(c);
            if (v < 0) throw new IllegalArgumentException("invalid Base64 character '" + c + "'");
            buffer = (buffer << 6) | v;                        // collect 6 bits at a time
            bits += 6;
            if (bits >= 8) {
                bits -= 8;
                out.write((buffer >>> bits) & 0xff);           // emit full bytes
            }
        }
        if ((buffer & ((1 << bits) - 1)) != 0) throw new IllegalArgumentException("non-zero trailing bits");
        return out.toByteArray();
    }

    // ---------------- Varint (unsigned LEB128) ----------------
    /** Encodes the 64 bits of value as UNSIGNED (negative longs take 10 bytes). */
    public static byte[] varintEncode(long value) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        while ((value & ~0x7FL) != 0) {
            out.write((int) ((value & 0x7F) | 0x80));          // 7 bits + "more follows"
            value >>>= 7;
        }
        out.write((int) value);                                // last byte: high bit 0
        return out.toByteArray();
    }

    /** Decodes one varint starting at pos[0]; advances pos[0] past it. */
    public static long varintDecode(byte[] data, int[] pos) {
        long result = 0;
        for (int shift = 0; shift < 70; shift += 7) {
            if (pos[0] >= data.length) throw new IllegalArgumentException("truncated varint");
            int b = data[pos[0]++] & 0xff;
            if (shift == 63 && (b & 0x7E) != 0) throw new IllegalArgumentException("varint overflows 64 bits");
            result |= (long) (b & 0x7F) << shift;
            if ((b & 0x80) == 0) return result;
        }
        throw new IllegalArgumentException("varint longer than 10 bytes");
    }

    // ---------------- ZigZag ----------------
    public static long zigZagEncode(long n) { return (n << 1) ^ (n >> 63); }
    public static long zigZagDecode(long z) { return (z >>> 1) ^ -(z & 1); }

    private static String hex(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (byte x : b) sb.append(String.format("%02X ", x));
        return sb.toString().trim();
    }

    public static void main(String[] args) {
        System.out.println("--- BASE64 / VARINT / ZIGZAG DEMO ---");
        for (String s : new String[]{"Man", "Ma", "M", "hello world"}) {
            String enc = base64Encode(s.getBytes(StandardCharsets.UTF_8));
            System.out.printf("Base64(\"%s\") = %-16s -> \"%s\"%n", s, enc, new String(base64Decode(enc), StandardCharsets.UTF_8));
        }
        // TWFu, TWE=, TQ==, aGVsbG8gd29ybGQ=
        byte[] bin = {(byte) 0xfb, (byte) 0xff, (byte) 0xbf};
        System.out.println("Bytes FB FF BF: standard " + base64Encode(bin) + ", URL-safe " + base64Encode(bin, true, false)); // +/+/ vs -_-_

        System.out.println();
        for (long v : new long[]{1, 127, 128, 300, 1L << 35, -1}) {
            System.out.printf("varint(%d) = %s%n", v, hex(varintEncode(v)));
        }
        // 300 -> AC 02 (the protobuf docs example), -1 -> 10 bytes
        for (long v : new long[]{0, -1, 1, -2, 2, -1000}) {
            long z = zigZagEncode(v);
            System.out.printf("zigzag(%d) = %d -> varint %s%n", v, z, hex(varintEncode(z)));
        }
    }
}
