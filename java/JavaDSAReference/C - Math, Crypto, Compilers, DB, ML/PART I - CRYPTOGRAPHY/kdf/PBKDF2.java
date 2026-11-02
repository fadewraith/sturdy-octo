package crypto.kdf;

import crypto.mac.HMAC;
import java.nio.charset.StandardCharsets;

/**
 * PBKDF2-HMAC-SHA256 (RFC 8018) - password hashing / key derivation
 *
 * WHAT IT IS:
 * Turns a password + SALT into a key, deliberately SLOWLY: the HMAC is iterated
 * thousands of times. Logging in once costs ~100 ms; an attacker testing a billion
 * guesses pays that cost a billion times.
 *
 * WHEN TO USE THIS:
 * - Storing passwords (Django's default, WPA2 Wi-Fi keys, 1Password, iOS backups).
 * - Deriving an encryption key from a passphrase (e.g. for AESModes.java).
 * - Newer designs prefer MEMORY-hard functions (scrypt, Argon2) because GPUs make
 *   pure-CPU iteration cheaper for attackers; PBKDF2 is still FIPS-approved.
 *
 * WHY A SALT?
 * A random per-user salt means equal passwords get different hashes, and
 * precomputed "rainbow tables" are useless (the attacker must redo the work per
 * salt). Salt is not secret - store it next to the hash.
 *
 * STRATEGY:
 * Output is made of 32-byte blocks T1, T2, ... (as many as the key length needs):
 *   U1 = HMAC(password, salt || INT_32_BE(i))
 *   Uj = HMAC(password, U(j-1))            for j = 2..iterations
 *   Ti = U1 XOR U2 XOR ... XOR U_iterations
 * The final key is T1 || T2 || ... truncated to the wanted length.
 * OWASP (2023) recommends 600,000 iterations for PBKDF2-HMAC-SHA256.
 *
 * COMPLEXITY:
 * Time: O(iterations * ceil(keyLength / 32)) HMAC calls. Space: O(keyLength).
 */
public class PBKDF2 {

    public static byte[] derive(byte[] password, byte[] salt, int iterations, int keyLength) {
        if (iterations < 1 || keyLength < 1) throw new IllegalArgumentException("iterations and keyLength must be >= 1");
        byte[] out = new byte[keyLength];
        int blocks = (keyLength + 31) / 32;
        for (int i = 1; i <= blocks; i++) {
            byte[] saltAndIndex = new byte[salt.length + 4];
            System.arraycopy(salt, 0, saltAndIndex, 0, salt.length);
            saltAndIndex[salt.length] = (byte) (i >>> 24);   // block index, big-endian
            saltAndIndex[salt.length + 1] = (byte) (i >>> 16);
            saltAndIndex[salt.length + 2] = (byte) (i >>> 8);
            saltAndIndex[salt.length + 3] = (byte) i;

            byte[] u = HMAC.hmacSha256(password, saltAndIndex);
            byte[] t = u.clone();
            for (int j = 2; j <= iterations; j++) {
                u = HMAC.hmacSha256(password, u);
                for (int b = 0; b < t.length; b++) t[b] ^= u[b];
            }
            int offset = (i - 1) * 32;
            System.arraycopy(t, 0, out, offset, Math.min(32, keyLength - offset));
        }
        return out;
    }

    public static void main(String[] args) {
        System.out.println("--- PBKDF2-HMAC-SHA256 DEMO ---");
        byte[] pw = "password".getBytes(StandardCharsets.UTF_8);
        byte[] salt = "salt".getBytes(StandardCharsets.UTF_8);
        System.out.println("1 iteration:    " + HMAC.hex(derive(pw, salt, 1, 32)));
        System.out.println("expected:       120fb6cffcf8b32c43e7225256c4f837a86548c92ccc35480805987cb70be17b");
        System.out.println("4096 iterations: " + HMAC.hex(derive(pw, salt, 4096, 32)));
        System.out.println("expected:        c5e478d59288c841aa530db6845c4c8d962893a001ce4e11a4963873aa98134a");

        long start = System.nanoTime();
        derive(pw, "random-salt-123".getBytes(StandardCharsets.UTF_8), 20_000, 32);
        System.out.printf("20,000 iterations took %d ms - that cost is paid per password GUESS%n", (System.nanoTime() - start) / 1_000_000);
    }
}
