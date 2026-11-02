package crypto.symmetric;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * BLOCK CIPHER MODES OF OPERATION: ECB, CBC, CTR (on top of EducationalAES)
 *
 * WHAT IT IS:
 * AES encrypts exactly ONE 16-byte block. A "mode" says how to encrypt a message of
 * any length with it. The mode matters as much as the cipher.
 *
 * 1. ECB (Electronic Codebook) - DON'T USE
 *    Encrypt every block independently. Equal plaintext blocks give EQUAL ciphertext
 *    blocks, so patterns leak (the famous "ECB penguin" image is still visible).
 *
 * 2. CBC (Cipher Block Chaining)
 *    C_i = AES(P_i XOR C_{i-1}),  C_0 = a random IV (sent with the message).
 *    Each block depends on all earlier ones, so repeats are hidden.
 *    Needs PADDING to a multiple of 16 bytes: PKCS#7 appends n bytes of value n
 *    (n = 1..16; a full block of 16s if already aligned, so unpadding is unambiguous).
 *    Decryption: P_i = AES^-1(C_i) XOR C_{i-1}.
 *    Pitfalls: predictable IVs (BEAST attack), and padding-error messages leak
 *    plaintext (padding-oracle attack) - always add a MAC (encrypt-then-MAC).
 *
 * 3. CTR (Counter)
 *    Turns AES into a STREAM cipher: keystream block i = AES(nonce || counter + i),
 *    C = P XOR keystream. No padding, blocks can be processed in parallel or at
 *    random offsets, and decryption is the SAME operation as encryption.
 *    Rule: NEVER reuse a (key, nonce) pair - two messages XORed would cancel the
 *    keystream. (GCM = CTR + a polynomial MAC; it's what TLS 1.3 uses.)
 *
 * COMPLEXITY:
 * All modes: O(N / 16) AES block operations. CBC encryption is sequential;
 * CBC decryption and CTR are parallelizable.
 */
public class AESModes {

    private static final int B = 16;

    // ---------------- padding ----------------
    public static byte[] pkcs7Pad(byte[] data) {
        int pad = B - (data.length % B); // 1..16
        byte[] out = Arrays.copyOf(data, data.length + pad);
        Arrays.fill(out, data.length, out.length, (byte) pad);
        return out;
    }

    public static byte[] pkcs7Unpad(byte[] data) {
        if (data.length == 0 || data.length % B != 0) throw new IllegalArgumentException("bad padded length");
        int pad = data[data.length - 1] & 0xff;
        if (pad < 1 || pad > B) throw new IllegalArgumentException("bad padding");
        for (int i = data.length - pad; i < data.length; i++) {
            if ((data[i] & 0xff) != pad) throw new IllegalArgumentException("bad padding");
        }
        return Arrays.copyOf(data, data.length - pad);
    }

    private static byte[] block(byte[] data, int index) {
        return Arrays.copyOfRange(data, index * B, index * B + B);
    }

    // ---------------- ECB ----------------
    public static byte[] encryptECB(byte[] plaintext, byte[] key) {
        byte[] p = pkcs7Pad(plaintext);
        byte[] out = new byte[p.length];
        for (int i = 0; i < p.length / B; i++) {
            System.arraycopy(EducationalAES.encryptBlock(block(p, i), key), 0, out, i * B, B);
        }
        return out;
    }

    public static byte[] decryptECB(byte[] ciphertext, byte[] key) {
        byte[] out = new byte[ciphertext.length];
        for (int i = 0; i < ciphertext.length / B; i++) {
            System.arraycopy(EducationalAES.decryptBlock(block(ciphertext, i), key), 0, out, i * B, B);
        }
        return pkcs7Unpad(out);
    }

    // ---------------- CBC ----------------
    public static byte[] encryptCBC(byte[] plaintext, byte[] key, byte[] iv) {
        if (iv.length != B) throw new IllegalArgumentException("IV must be 16 bytes");
        byte[] p = pkcs7Pad(plaintext);
        byte[] out = new byte[p.length];
        byte[] prev = iv.clone();
        for (int i = 0; i < p.length / B; i++) {
            byte[] x = block(p, i);
            for (int j = 0; j < B; j++) x[j] ^= prev[j];   // chain with the previous ciphertext
            prev = EducationalAES.encryptBlock(x, key);
            System.arraycopy(prev, 0, out, i * B, B);
        }
        return out;
    }

    public static byte[] decryptCBC(byte[] ciphertext, byte[] key, byte[] iv) {
        if (ciphertext.length % B != 0) throw new IllegalArgumentException("ciphertext length must be a multiple of 16");
        byte[] out = new byte[ciphertext.length];
        byte[] prev = iv.clone();
        for (int i = 0; i < ciphertext.length / B; i++) {
            byte[] c = block(ciphertext, i);
            byte[] x = EducationalAES.decryptBlock(c, key);
            for (int j = 0; j < B; j++) out[i * B + j] = (byte) (x[j] ^ prev[j]);
            prev = c;
        }
        return pkcs7Unpad(out);
    }

    // ---------------- CTR ----------------
    /** Encrypts or decrypts (same operation). counterBlock = 16-byte initial counter (nonce || counter). */
    public static byte[] cryptCTR(byte[] data, byte[] key, byte[] counterBlock) {
        if (counterBlock.length != B) throw new IllegalArgumentException("counter block must be 16 bytes");
        byte[] counter = counterBlock.clone();
        byte[] out = new byte[data.length];
        for (int offset = 0; offset < data.length; offset += B) {
            byte[] keystream = EducationalAES.encryptBlock(counter, key);
            for (int j = 0; j < B && offset + j < data.length; j++) out[offset + j] = (byte) (data[offset + j] ^ keystream[j]);
            for (int j = B - 1; j >= 0; j--) {             // counter + 1 as a 128-bit big-endian number
                if (++counter[j] != 0) break;
            }
        }
        return out;
    }

    static String hex(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (byte x : b) sb.append(String.format("%02x", x));
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("--- AES MODES DEMO ---");
        byte[] key = "0123456789abcdef".getBytes(StandardCharsets.UTF_8);
        byte[] iv = "fedcba9876543210".getBytes(StandardCharsets.UTF_8);
        // two identical 16-byte blocks
        byte[] msg = "ATTACK AT DAWN!!ATTACK AT DAWN!!".getBytes(StandardCharsets.UTF_8);

        byte[] ecb = encryptECB(msg, key), cbc = encryptCBC(msg, key, iv);
        System.out.println("ECB block 1: " + hex(Arrays.copyOfRange(ecb, 0, 16)));
        System.out.println("ECB block 2: " + hex(Arrays.copyOfRange(ecb, 16, 32)) + "  <- identical: pattern leaks");
        System.out.println("CBC block 1: " + hex(Arrays.copyOfRange(cbc, 0, 16)));
        System.out.println("CBC block 2: " + hex(Arrays.copyOfRange(cbc, 16, 32)) + "  <- different");
        System.out.println("CBC decrypt: " + new String(decryptCBC(cbc, key, iv), StandardCharsets.UTF_8));

        byte[] text = "CTR needs no padding: 36 bytes long.".getBytes(StandardCharsets.UTF_8);
        byte[] ctr = cryptCTR(text, key, iv);
        System.out.println("\nCTR ciphertext length " + ctr.length + " (same as plaintext " + text.length + ")");
        System.out.println("CTR decrypt: " + new String(cryptCTR(ctr, key, iv), StandardCharsets.UTF_8));
    }
}
