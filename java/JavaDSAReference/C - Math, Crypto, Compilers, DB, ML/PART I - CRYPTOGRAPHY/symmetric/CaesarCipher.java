// EDUCATIONAL IMPLEMENTATION ONLY. Do not use this code for real security-critical work. Production code must use java.security / javax.crypto (MessageDigest, Cipher, KeyPairGenerator, etc.), which are the unavoidable built-ins here per the Inbuilt Function Exception Rule — real cryptography requires audited, constant-time implementations that are extremely difficult to get right from scratch.
/**
 * WHAT IT IS: The Caesar Cipher is one of the simplest and most widely known encryption techniques. It is a substitution cipher where each letter in the plaintext is shifted a certain number of places down the alphabet.
 * STRATEGY: Iterate through the text, check if a character is a letter, and shift it by the key (modulo 26).
 * TIME/SPACE COMPLEXITY: Time: O(N) where N is the length of the string. Space: O(N) for the resulting string.
 * REAL-WORLD ANALOGY / USE CASE: A secret decoder ring found in a cereal box.
 * WHEN TO USE / COMBINATION: Use only for educational purposes, puzzles, or extremely basic obfuscation (like ROT13).
 * 
 * PSEUDOCODE:
 * For each character c in text:
 *   If c is a letter:
 *     shift c by key positions, wrapping around the alphabet
 *   Append to result
 * Return result
 */
package crypto.symmetric;

public class CaesarCipher {
    public static String encrypt(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                c = (char) (((c - base + shift) % 26 + 26) % 26 + base);
            }
            result.append(c);
        }
        return result.toString();
    }
    
    public static String decrypt(String text, int shift) {
        return encrypt(text, -shift);
    }
    
    public static void main(String[] args) {
        System.out.println("--- Caesar Cipher ---");
        String original = "Hello, World! zZ";
        int shift = 3;
        
        String encrypted = encrypt(original, shift);
        String decrypted = decrypt(encrypted, shift);
        
        System.out.println("Original:  " + original);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
    }
}
