// EDUCATIONAL IMPLEMENTATION ONLY. Do not use this code for real security-critical work. Production code must use java.security / javax.crypto (MessageDigest, Cipher, KeyPairGenerator, etc.), which are the unavoidable built-ins here per the Inbuilt Function Exception Rule — real cryptography requires audited, constant-time implementations that are extremely difficult to get right from scratch.
/**
 * WHAT IT IS: A complete from-scratch AES-128 block cipher (FIPS-197): key expansion,
 * encryption and decryption of one 16-byte block. Output matches javax.crypto "AES/ECB/NoPadding".
 * STRATEGY: AES is a substitution-permutation network. A block is operated on via multiple rounds of SubBytes, ShiftRows, MixColumns, and AddRoundKey.
 * TIME/SPACE COMPLEXITY: Time: O(1) per block (fixed number of rounds). Space: O(1) for state matrix and keys.
 * REAL-WORLD ANALOGY / USE CASE: Like scrambling a Rubik's cube with specific, reversible patterns (substitution and shifting) multiple times based on a master key.
 * WHEN TO USE / COMBINATION: AES is the global standard for symmetric encryption (data at rest, TLS, VPNs).
 *   A block cipher alone only handles 16 bytes; real use needs a MODE (GCM, CBC...) and padding.
 *   NOTE: this table-based version is NOT constant-time (cache-timing leaks) - another reason
 *   to use javax.crypto in practice.
 *
 * PSEUDOCODE:
 * Derive round keys from master key
 * Initialize state matrix with plaintext block
 * AddRoundKey(state, roundKeys[0])
 * For round = 1 to 9:
 *   SubBytes(state)
 *   ShiftRows(state)
 *   MixColumns(state)
 *   AddRoundKey(state, roundKeys[round])
 * Final Round:
 *   SubBytes(state)
 *   ShiftRows(state)
 *   AddRoundKey(state, roundKeys[10])
 * Return state
 *
 * STATE LAYOUT: 16 bytes filled COLUMN by column: state[row][col] = block[row + 4 * col].
 */
package crypto.symmetric;

public class EducationalAES {

    private static final int ROUNDS = 10;          // AES-128: 10 rounds
    private static final int[] SBOX = new int[256];
    private static final int[] INV_SBOX = new int[256];

    // Build the S-box instead of pasting 256 magic numbers:
    // S(x) = affine transform of the multiplicative inverse of x in GF(2^8).
    static {
        for (int x = 0; x < 256; x++) {
            int inv = (x == 0) ? 0 : gfInverse(x);
            int s = inv ^ rotl8(inv, 1) ^ rotl8(inv, 2) ^ rotl8(inv, 3) ^ rotl8(inv, 4) ^ 0x63;
            SBOX[x] = s;
            INV_SBOX[s] = x;
        }
    }

    private static int rotl8(int x, int n) {
        return ((x << n) | (x >>> (8 - n))) & 0xff;
    }

    /** Multiplication in GF(2^8) with the AES polynomial x^8 + x^4 + x^3 + x + 1 (0x11B). */
    static int gfMul(int a, int b) {
        int result = 0;
        while (b != 0) {
            if ((b & 1) != 0) result ^= a; // "addition" in GF(2^8) is XOR
            a <<= 1;
            if ((a & 0x100) != 0) a ^= 0x11B; // reduce when we overflow 8 bits
            b >>= 1;
        }
        return result & 0xff;
    }

    // a^254 = a^-1 in GF(2^8) (the multiplicative group has 255 elements)
    private static int gfInverse(int a) {
        int result = 1, base = a, exp = 254;
        while (exp > 0) {
            if ((exp & 1) == 1) result = gfMul(result, base);
            base = gfMul(base, base);
            exp >>= 1;
        }
        return result;
    }

    // --------------------------------------------------------
    // KEY EXPANSION: 16-byte key -> 11 round keys of 16 bytes
    // --------------------------------------------------------
    public static int[][] expandKey(byte[] key) {
        if (key.length != 16) throw new IllegalArgumentException("AES-128 needs a 16-byte key");
        int[] w = new int[4 * (ROUNDS + 1) * 4]; // bytes of all round keys (176)
        for (int i = 0; i < 16; i++) w[i] = key[i] & 0xff;

        int rcon = 1;
        for (int i = 16; i < w.length; i += 4) {
            int t0 = w[i - 4], t1 = w[i - 3], t2 = w[i - 2], t3 = w[i - 1];
            if (i % 16 == 0) {
                // RotWord + SubWord + Rcon, once per round key
                int tmp = t0;
                t0 = SBOX[t1] ^ rcon;
                t1 = SBOX[t2];
                t2 = SBOX[t3];
                t3 = SBOX[tmp];
                rcon = gfMul(rcon, 2);
            }
            w[i] = w[i - 16] ^ t0;
            w[i + 1] = w[i - 15] ^ t1;
            w[i + 2] = w[i - 14] ^ t2;
            w[i + 3] = w[i - 13] ^ t3;
        }

        int[][] roundKeys = new int[ROUNDS + 1][16];
        for (int r = 0; r <= ROUNDS; r++) {
            System.arraycopy(w, r * 16, roundKeys[r], 0, 16);
        }
        return roundKeys;
    }

    // --------------------------------------------------------
    // THE FOUR ROUND STEPS (state is 4x4, state[row][col])
    // --------------------------------------------------------

    // Non-linear byte substitution - the only non-linear step (gives "confusion")
    static void subBytes(int[][] state) {
        for (int r = 0; r < 4; r++)
            for (int c = 0; c < 4; c++) state[r][c] = SBOX[state[r][c]];
    }

    static void invSubBytes(int[][] state) {
        for (int r = 0; r < 4; r++)
            for (int c = 0; c < 4; c++) state[r][c] = INV_SBOX[state[r][c]];
    }

    // Row r is rotated LEFT by r positions (spreads bytes across columns)
    static void shiftRows(int[][] state) {
        for (int r = 1; r < 4; r++) {
            int[] row = new int[4];
            for (int c = 0; c < 4; c++) row[c] = state[r][(c + r) % 4];
            state[r] = row;
        }
    }

    static void invShiftRows(int[][] state) {
        for (int r = 1; r < 4; r++) {
            int[] row = new int[4];
            for (int c = 0; c < 4; c++) row[(c + r) % 4] = state[r][c];
            state[r] = row;
        }
    }

    // Each column is multiplied by a fixed matrix in GF(2^8) (mixes bytes within a column - "diffusion")
    static void mixColumns(int[][] state) {
        for (int c = 0; c < 4; c++) {
            int a0 = state[0][c], a1 = state[1][c], a2 = state[2][c], a3 = state[3][c];
            state[0][c] = gfMul(a0, 2) ^ gfMul(a1, 3) ^ a2 ^ a3;
            state[1][c] = a0 ^ gfMul(a1, 2) ^ gfMul(a2, 3) ^ a3;
            state[2][c] = a0 ^ a1 ^ gfMul(a2, 2) ^ gfMul(a3, 3);
            state[3][c] = gfMul(a0, 3) ^ a1 ^ a2 ^ gfMul(a3, 2);
        }
    }

    static void invMixColumns(int[][] state) {
        for (int c = 0; c < 4; c++) {
            int a0 = state[0][c], a1 = state[1][c], a2 = state[2][c], a3 = state[3][c];
            state[0][c] = gfMul(a0, 14) ^ gfMul(a1, 11) ^ gfMul(a2, 13) ^ gfMul(a3, 9);
            state[1][c] = gfMul(a0, 9) ^ gfMul(a1, 14) ^ gfMul(a2, 11) ^ gfMul(a3, 13);
            state[2][c] = gfMul(a0, 13) ^ gfMul(a1, 9) ^ gfMul(a2, 14) ^ gfMul(a3, 11);
            state[3][c] = gfMul(a0, 11) ^ gfMul(a1, 13) ^ gfMul(a2, 9) ^ gfMul(a3, 14);
        }
    }

    // XOR with the round key (the only step that uses the key)
    static void addRoundKey(int[][] state, int[] roundKey) {
        for (int r = 0; r < 4; r++)
            for (int c = 0; c < 4; c++) state[r][c] ^= roundKey[r + 4 * c];
    }

    // --------------------------------------------------------
    // ENCRYPT / DECRYPT ONE 16-BYTE BLOCK
    // --------------------------------------------------------
    public static byte[] encryptBlock(byte[] plaintext, byte[] key) {
        int[][] roundKeys = expandKey(key);
        int[][] state = toState(plaintext);

        addRoundKey(state, roundKeys[0]);
        for (int round = 1; round < ROUNDS; round++) {
            subBytes(state);
            shiftRows(state);
            mixColumns(state);
            addRoundKey(state, roundKeys[round]);
        }
        // Last round has no MixColumns
        subBytes(state);
        shiftRows(state);
        addRoundKey(state, roundKeys[ROUNDS]);
        return fromState(state);
    }

    public static byte[] decryptBlock(byte[] ciphertext, byte[] key) {
        int[][] roundKeys = expandKey(key);
        int[][] state = toState(ciphertext);

        // Undo the steps in reverse order with the inverse functions
        addRoundKey(state, roundKeys[ROUNDS]);
        invShiftRows(state);
        invSubBytes(state);
        for (int round = ROUNDS - 1; round >= 1; round--) {
            addRoundKey(state, roundKeys[round]);
            invMixColumns(state);
            invShiftRows(state);
            invSubBytes(state);
        }
        addRoundKey(state, roundKeys[0]);
        return fromState(state);
    }

    private static int[][] toState(byte[] block) {
        if (block.length != 16) throw new IllegalArgumentException("AES block must be 16 bytes");
        int[][] state = new int[4][4];
        for (int i = 0; i < 16; i++) state[i % 4][i / 4] = block[i] & 0xff;
        return state;
    }

    private static byte[] fromState(int[][] state) {
        byte[] out = new byte[16];
        for (int i = 0; i < 16; i++) out[i] = (byte) state[i % 4][i / 4];
        return out;
    }

    private static byte[] hex(String s) {
        byte[] b = new byte[s.length() / 2];
        for (int i = 0; i < b.length; i++) b[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
        return b;
    }

    private static String hex(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (byte x : b) sb.append(String.format("%02x", x));
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("--- Educational AES-128 ---");
        // Official test vector from FIPS-197 Appendix C.1
        byte[] key = hex("000102030405060708090a0b0c0d0e0f");
        byte[] plain = hex("00112233445566778899aabbccddeeff");

        byte[] cipher = encryptBlock(plain, key);
        System.out.println("Plaintext:  " + hex(plain));
        System.out.println("Ciphertext: " + hex(cipher));
        System.out.println("Expected:   69c4e0d86a7b0430d8cdb78070b4c55a");
        System.out.println("Decrypted:  " + hex(decryptBlock(cipher, key)));
    }
}
