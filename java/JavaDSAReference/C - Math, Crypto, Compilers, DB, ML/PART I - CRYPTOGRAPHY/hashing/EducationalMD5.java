// EDUCATIONAL IMPLEMENTATION ONLY. Do not use this code for real security-critical work. Production code must use java.security / javax.crypto (MessageDigest, Cipher, KeyPairGenerator, etc.), which are the unavoidable built-ins here per the Inbuilt Function Exception Rule — real cryptography requires audited, constant-time implementations that are extremely difficult to get right from scratch.
/**
 * WHAT IT IS: A complete from-scratch MD5 (RFC 1321), written for readability. Output matches MessageDigest.getInstance("MD5"). MD5 is cryptographically broken - learn from it, don't use it for security.
 * STRATEGY: Similar to SHA-256, it pads the message and processes it in 512-bit blocks using a 64-operation compression function with specific non-linear functions (F, G, H, I).
 * TIME/SPACE COMPLEXITY: Time: O(N) where N is the length of the message. Space: O(1) auxiliary space.
 * REAL-WORLD ANALOGY / USE CASE: A legacy checksum for file integrity checking (not for security/passwords due to collision vulnerabilities).
 * WHEN TO USE / COMBINATION: Avoid in new systems unless required for backwards compatibility with legacy checksums.
 * 
 * PSEUDOCODE:
 * Append padding bits and length
 * Initialize A, B, C, D
 * For each 512-bit block:
 *   Copy A, B, C, D to a, b, c, d
 *   For i from 0 to 63:
 *     Apply F, G, H, or I based on round
 *     Rotate left and add
 *   Add a, b, c, d back to A, B, C, D
 * Return A, B, C, D concatenated
 */
package crypto.hashing;

public class EducationalMD5 {

    // Per-round left-rotation amounts (RFC 1321)
    private static final int[] S = {
        7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22,
        5,  9, 14, 20, 5,  9, 14, 20, 5,  9, 14, 20, 5,  9, 14, 20,
        4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23,
        6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21
    };

    // K[i] = floor(|sin(i + 1)| * 2^32), hard-coded so floating point can't change them
    private static final int[] K = {
        0xd76aa478, 0xe8c7b756, 0x242070db, 0xc1bdceee, 0xf57c0faf, 0x4787c62a, 0xa8304613, 0xfd469501,
        0x698098d8, 0x8b44f7af, 0xffff5bb1, 0x895cd7be, 0x6b901122, 0xfd987193, 0xa679438e, 0x49b40821,
        0xf61e2562, 0xc040b340, 0x265e5a51, 0xe9b6c7aa, 0xd62f105d, 0x02441453, 0xd8a1e681, 0xe7d3fbc8,
        0x21e1cde6, 0xc33707d6, 0xf4d50d87, 0x455a14ed, 0xa9e3e905, 0xfcefa3f8, 0x676f02d9, 0x8d2a4c8a,
        0xfffa3942, 0x8771f681, 0x6d9d6122, 0xfde5380c, 0xa4beea44, 0x4bdecfa9, 0xf6bb4b60, 0xbebfbc70,
        0x289b7ec6, 0xeaa127fa, 0xd4ef3085, 0x04881d05, 0xd9d4d039, 0xe6db99e5, 0x1fa27cf8, 0xc4ac5665,
        0xf4292244, 0x432aff97, 0xab9423a7, 0xfc93a039, 0x655b59c3, 0x8f0ccc92, 0xffeff47d, 0x85845dd1,
        0x6fa87e4f, 0xfe2ce6e0, 0xa3014314, 0x4e0811a1, 0xf7537e82, 0xbd3af235, 0x2ad7d2bb, 0xeb86d391
    };

    /** Returns the MD5 digest of the UTF-8 bytes of message, as 32 lowercase hex chars. */
    public static String hash(String message) {
        byte[] msg = message.getBytes(java.nio.charset.StandardCharsets.UTF_8);

        // Step 1: Padding - same idea as SHA-256 (0x80, zeros, 64-bit bit length),
        // but MD5 is LITTLE-endian: the length goes in lowest byte first.
        int paddedLen = ((msg.length + 8) / 64 + 1) * 64;
        byte[] padded = new byte[paddedLen];
        System.arraycopy(msg, 0, padded, 0, msg.length);
        padded[msg.length] = (byte) 0x80;
        long bitLen = (long) msg.length * 8;
        for (int i = 0; i < 8; i++) {
            padded[paddedLen - 8 + i] = (byte) (bitLen >>> (8 * i));
        }

        // Step 2: Initial state
        int a0 = 0x67452301, b0 = 0xefcdab89, c0 = 0x98badcfe, d0 = 0x10325476;

        int[] m = new int[16];
        for (int block = 0; block < paddedLen; block += 64) {
            // Split the block into 16 little-endian 32-bit words
            for (int t = 0; t < 16; t++) {
                int j = block + t * 4;
                m[t] = (padded[j] & 0xff) | ((padded[j + 1] & 0xff) << 8)
                     | ((padded[j + 2] & 0xff) << 16) | ((padded[j + 3] & 0xff) << 24);
            }

            int a = a0, b = b0, c = c0, d = d0;
            // Step 3: 64 operations in 4 rounds of 16, each round with its own function
            for (int i = 0; i < 64; i++) {
                int f, g;
                if (i < 16) {        // Round 1: F = (B and C) or (not B and D)
                    f = (b & c) | (~b & d);
                    g = i;
                } else if (i < 32) { // Round 2: G = (D and B) or (not D and C)
                    f = (d & b) | (~d & c);
                    g = (5 * i + 1) % 16;
                } else if (i < 48) { // Round 3: H = B xor C xor D
                    f = b ^ c ^ d;
                    g = (3 * i + 5) % 16;
                } else {             // Round 4: I = C xor (B or not D)
                    f = c ^ (b | ~d);
                    g = (7 * i) % 16;
                }
                int temp = d;
                d = c;
                c = b;
                b = b + Integer.rotateLeft(a + f + K[i] + m[g], S[i]);
                a = temp;
            }

            // Step 4: Add this block's result into the state
            a0 += a; b0 += b; c0 += c; d0 += d;
        }

        // Step 5: Output the 4 words as little-endian bytes
        StringBuilder sb = new StringBuilder();
        for (int word : new int[]{a0, b0, c0, d0}) {
            for (int i = 0; i < 4; i++) {
                sb.append(String.format("%02x", (word >>> (8 * i)) & 0xff));
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("--- Educational MD5 ---");
        System.out.println("Message: 'hello world'");
        System.out.println("Hash: " + hash("hello world"));
        System.out.println("Expected: 5eb63bbbe01eeed093cb22bb8f5acdc3");
    }
}
