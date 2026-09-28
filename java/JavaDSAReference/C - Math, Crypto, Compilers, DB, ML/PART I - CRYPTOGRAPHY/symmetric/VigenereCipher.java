// EDUCATIONAL IMPLEMENTATION ONLY. Do not use this code for real security-critical work. Production code must use java.security / javax.crypto (MessageDigest, Cipher, KeyPairGenerator, etc.), which are the unavoidable built-ins here per the Inbuilt Function Exception Rule — real cryptography requires audited, constant-time implementations that are extremely difficult to get right from scratch.
/**
 * WHAT IT IS: A polyalphabetic substitution cipher that uses a keyword to determine the shift for each letter.
 * STRATEGY: Use the letters of a keyword to shift the plaintext letters. The keyword is repeated as necessary.
 * TIME/SPACE COMPLEXITY: Time: O(N) where N is the length of the string. Space: O(N) for the resulting string.
 * REAL-WORLD ANALOGY / USE CASE: Using a lookup table (Vigenère square) and a shared secret word to encode messages by hand.
 * WHEN TO USE / COMBINATION: Educational purposes. A building block to understand how repeating keys can be broken (e.g., Kasiski examination).
 * 
 * PSEUDOCODE:
 * keyIndex = 0
 * For each character c in text:
 *   If c is a letter:
 *     shift = value of key[keyIndex % keyLength]
 *     shift c by shift positions
 *     keyIndex++
 *   Append to result
 * Return result
 */
package crypto.symmetric;

public class VigenereCipher {
    public static String encrypt(String text, String key) {
        StringBuilder result = new StringBuilder();
        key = key.toUpperCase();
        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                int shift = key.charAt(j % key.length()) - 'A';
                c = (char) (((c - base + shift) % 26) + base);
                j++;
            }
            result.append(c);
        }
        return result.toString();
    }
    
    public static String decrypt(String text, String key) {
        StringBuilder result = new StringBuilder();
        key = key.toUpperCase();
        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                int shift = key.charAt(j % key.length()) - 'A';
                c = (char) (((c - base - shift + 26) % 26) + base);
                j++;
            }
            result.append(c);
        }
        return result.toString();
    }
    
    public static void main(String[] args) {
        System.out.println("--- Vigenere Cipher ---");
        String original = "Attack at dawn!";
        String key = "LEMON";
        
        String encrypted = encrypt(original, key);
        String decrypted = decrypt(encrypted, key);
        
        System.out.println("Original:  " + original);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
    }
}
