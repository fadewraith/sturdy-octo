// EDUCATIONAL IMPLEMENTATION ONLY. Do not use this code for real security-critical work. Production code must use java.security / javax.crypto (MessageDigest, Cipher, KeyPairGenerator, etc.), which are the unavoidable built-ins here per the Inbuilt Function Exception Rule — real cryptography requires audited, constant-time implementations that are extremely difficult to get right from scratch.
/**
 * WHAT IT IS: A complete from-scratch SHA-256 (FIPS 180-4), written for readability. Output matches MessageDigest.getInstance("SHA-256").
 * STRATEGY: Applies padding, breaks the message into 512-bit chunks, and processes each through a 64-round compression function using bitwise operations (rotations, XORs, etc.).
 * TIME/SPACE COMPLEXITY: Time: O(N) where N is the length of the message. Space: O(1) auxiliary space.
 * REAL-WORLD ANALOGY / USE CASE: Fingerprinting a file. If even one byte changes, the fingerprint completely changes.
 * WHEN TO USE / COMBINATION: Use for data integrity checks, password hashing (when combined with salt/stretching), and digital signatures.
 * 
 * PSEUDOCODE:
 * Append 1 bit to message
 * Append 0 bits until length % 512 == 448
 * Append 64-bit length of original message
 * Initialize hash values (h0 to h7)
 * For each 512-bit block:
 *   Create message schedule (64 words)
 *   Initialize working variables (a to h)
 *   For i from 0 to 63:
 *     temp1 = h + Sigma1(e) + Ch(e, f, g) + K[i] + W[i]
 *     temp2 = Sigma0(a) + Maj(a, b, c)
 *     h = g; g = f; f = e; e = d + temp1
 *     d = c; c = b; b = a; a = temp1 + temp2
 *   Add compressed chunk to current hash value
 * Return concatenated h0..h7
 */
package crypto.hashing;

public class EducationalSHA256 {

    // Round constants: first 32 bits of the fractional parts of the cube roots
    // of the first 64 primes (FIPS 180-4, section 4.2.2)
    private static final int[] K = {
        0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
        0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
        0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
        0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967,
        0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
        0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,
        0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3,
        0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2
    };

    /** Returns the SHA-256 digest of the UTF-8 bytes of message, as 64 lowercase hex chars. */
    public static String hash(String message) {
        byte[] d = digest(message.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte x : d) sb.append(String.format("%02x", x));
        return sb.toString();
    }

    /** Returns the raw 32-byte SHA-256 digest of any byte array (used by HMAC / PBKDF2). */
    public static byte[] digest(byte[] msg) {

        // Step 1: Padding. Append bit '1' (byte 0x80), then zeros until the length
        // is 56 mod 64 bytes, then the original length in BITS as a 64-bit big-endian number.
        // Result: a whole number of 64-byte (512-bit) blocks.
        int paddedLen = ((msg.length + 8) / 64 + 1) * 64;
        byte[] padded = new byte[paddedLen];
        System.arraycopy(msg, 0, padded, 0, msg.length);
        padded[msg.length] = (byte) 0x80;
        long bitLen = (long) msg.length * 8;
        for (int i = 0; i < 8; i++) {
            padded[paddedLen - 1 - i] = (byte) (bitLen >>> (8 * i));
        }

        // Step 2: Initial hash values: first 32 bits of the fractional parts
        // of the square roots of the first 8 primes
        int h0 = 0x6a09e667, h1 = 0xbb67ae85, h2 = 0x3c6ef372, h3 = 0xa54ff53a;
        int h4 = 0x510e527f, h5 = 0x9b05688c, h6 = 0x1f83d9ab, h7 = 0x5be0cd19;

        int[] w = new int[64];
        for (int block = 0; block < paddedLen; block += 64) {
            // Step 3: Message schedule. First 16 words come straight from the block
            // (big-endian), the other 48 are mixed from earlier words.
            for (int t = 0; t < 16; t++) {
                int j = block + t * 4;
                w[t] = ((padded[j] & 0xff) << 24) | ((padded[j + 1] & 0xff) << 16)
                     | ((padded[j + 2] & 0xff) << 8) | (padded[j + 3] & 0xff);
            }
            for (int t = 16; t < 64; t++) {
                int s0 = Integer.rotateRight(w[t - 15], 7) ^ Integer.rotateRight(w[t - 15], 18) ^ (w[t - 15] >>> 3);
                int s1 = Integer.rotateRight(w[t - 2], 17) ^ Integer.rotateRight(w[t - 2], 19) ^ (w[t - 2] >>> 10);
                w[t] = w[t - 16] + s0 + w[t - 7] + s1; // int addition = addition mod 2^32
            }

            // Step 4: Compression - 64 rounds over working variables a..h
            int a = h0, b = h1, c = h2, d = h3, e = h4, f = h5, g = h6, h = h7;
            for (int t = 0; t < 64; t++) {
                // Sigma functions use ROTATIONS (bits wrap around), not plain shifts
                int sum1 = Integer.rotateRight(e, 6) ^ Integer.rotateRight(e, 11) ^ Integer.rotateRight(e, 25);
                int ch = (e & f) ^ (~e & g);                 // "choose": e picks bits from f or g
                int temp1 = h + sum1 + ch + K[t] + w[t];
                int sum0 = Integer.rotateRight(a, 2) ^ Integer.rotateRight(a, 13) ^ Integer.rotateRight(a, 22);
                int maj = (a & b) ^ (a & c) ^ (b & c);       // "majority" of a, b, c
                int temp2 = sum0 + maj;

                h = g;
                g = f;
                f = e;
                e = d + temp1;
                d = c;
                c = b;
                b = a;
                a = temp1 + temp2;
            }

            // Step 5: Add this block's result into the running hash
            h0 += a; h1 += b; h2 += c; h3 += d;
            h4 += e; h5 += f; h6 += g; h7 += h;
        }

        int[] words = {h0, h1, h2, h3, h4, h5, h6, h7};
        byte[] out = new byte[32];
        for (int i = 0; i < 8; i++) {                    // big-endian
            out[4 * i] = (byte) (words[i] >>> 24);
            out[4 * i + 1] = (byte) (words[i] >>> 16);
            out[4 * i + 2] = (byte) (words[i] >>> 8);
            out[4 * i + 3] = (byte) words[i];
        }
        return out;
    }

    public static void main(String[] args) {
        System.out.println("--- Educational SHA-256 ---");
        System.out.println("Message: 'hello world'");
        System.out.println("Hash: " + hash("hello world"));
        System.out.println("Expected: b94d27b9934d3e08a52e52d7da7dabfac484efe37a5380ee9088f7ace2efcde9");
        System.out.println("Hash of '': " + hash(""));
        System.out.println("Expected:   e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855");
    }
}
