package crypto.mac;

import crypto.hashing.EducationalSHA256;
import java.nio.charset.StandardCharsets;

/**
 * HMAC-SHA256 (RFC 2104) - Hash-based Message Authentication Code
 *
 * WHAT IT IS:
 * A keyed checksum: tag = HMAC(key, message). Only someone with the key can make a
 * valid tag, so the receiver knows the message is AUTHENTIC and UNCHANGED.
 * (A plain hash proves neither - anyone can recompute SHA-256 of a forged message.)
 *
 * WHEN TO USE THIS:
 * - JWT "HS256" signatures, AWS request signing (SigV4), webhook signatures
 *   (GitHub, Stripe), TLS record integrity, TOTP 2FA codes, cookies/session tokens.
 * - Building block for PBKDF2 (PBKDF2.java) and HKDF.
 *
 * WHY NOT JUST hash(key || message)?
 * SHA-256 is a Merkle-Damgard hash: from hash(key || m) an attacker can compute
 * hash(key || m || padding || extra) WITHOUT the key (length-extension attack).
 * HMAC's nested construction blocks that:
 *   HMAC(K, m) = H( (K' XOR opad) || H( (K' XOR ipad) || m ) )
 *   K'   = key padded with zeros to the 64-byte block size (hashed first if longer)
 *   ipad = 0x36 repeated, opad = 0x5c repeated
 *
 * VERIFYING: compare tags in CONSTANT TIME (no early exit on the first differing
 * byte), otherwise response timing leaks how many leading bytes were right.
 *
 * COMPLEXITY:
 * Time: O(|message|) - two hash passes (the outer one is over just 96 bytes).
 * Space: O(|message|) here (we build the inner input as one array).
 */
public class HMAC {

    private static final int BLOCK_SIZE = 64; // SHA-256 block size in bytes

    public static byte[] hmacSha256(byte[] key, byte[] message) {
        if (key.length > BLOCK_SIZE) key = EducationalSHA256.digest(key); // long keys are hashed first
        byte[] k = new byte[BLOCK_SIZE];
        System.arraycopy(key, 0, k, 0, key.length);                      // then zero-padded

        byte[] inner = new byte[BLOCK_SIZE + message.length];
        for (int i = 0; i < BLOCK_SIZE; i++) inner[i] = (byte) (k[i] ^ 0x36);
        System.arraycopy(message, 0, inner, BLOCK_SIZE, message.length);
        byte[] innerHash = EducationalSHA256.digest(inner);

        byte[] outer = new byte[BLOCK_SIZE + innerHash.length];
        for (int i = 0; i < BLOCK_SIZE; i++) outer[i] = (byte) (k[i] ^ 0x5c);
        System.arraycopy(innerHash, 0, outer, BLOCK_SIZE, innerHash.length);
        return EducationalSHA256.digest(outer);
    }

    /** Constant-time comparison: always looks at every byte. */
    public static boolean verify(byte[] key, byte[] message, byte[] tag) {
        byte[] expected = hmacSha256(key, message);
        if (expected.length != tag.length) return false;
        int diff = 0;
        for (int i = 0; i < expected.length; i++) diff |= expected[i] ^ tag[i];
        return diff == 0;
    }

    public static String hex(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (byte x : b) sb.append(String.format("%02x", x));
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("--- HMAC-SHA256 DEMO ---");
        byte[] key = "key".getBytes(StandardCharsets.UTF_8);
        byte[] msg = "The quick brown fox jumps over the lazy dog".getBytes(StandardCharsets.UTF_8);
        byte[] tag = hmacSha256(key, msg);
        System.out.println("HMAC(\"key\", fox) = " + hex(tag));
        System.out.println("expected          = f7bc83f430538424b13298e6aa6fb143ef4d59a14946175997479dbc2d1a3cd8");
        System.out.println("verify original: " + verify(key, msg, tag));
        byte[] tampered = "The quick brown fox jumps over the lazy cat".getBytes(StandardCharsets.UTF_8);
        System.out.println("verify tampered: " + verify(key, tampered, tag));                       // false
        System.out.println("verify wrong key: " + verify("kez".getBytes(StandardCharsets.UTF_8), msg, tag)); // false
    }
}
