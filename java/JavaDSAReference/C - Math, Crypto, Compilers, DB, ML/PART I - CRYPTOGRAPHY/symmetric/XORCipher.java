// EDUCATIONAL IMPLEMENTATION ONLY. Do not use this code for real security-critical work. Production code must use java.security / javax.crypto (MessageDigest, Cipher, KeyPairGenerator, etc.), which are the unavoidable built-ins here per the Inbuilt Function Exception Rule — real cryptography requires audited, constant-time implementations that are extremely difficult to get right from scratch.
/**
 * WHAT IT IS: A cipher that applies the bitwise XOR operation between each character of the plaintext and a key.
 * STRATEGY: Since XOR is its own inverse (A ^ B = C, C ^ B = A), the same function is used for both encryption and decryption.
 * TIME/SPACE COMPLEXITY: Time: O(N) where N is length of string. Space: O(N) for the resulting byte array/string.
 * REAL-WORLD ANALOGY / USE CASE: The basis for many modern stream ciphers. When the key is truly random and as long as the message, it becomes a One-Time Pad (unbreakable).
 * WHEN TO USE / COMBINATION: Simple obfuscation, or as a fundamental operation in symmetric key cryptography.
 * 
 * PSEUDOCODE:
 * For each character in text:
 *   result_char = char XOR key_char
 * Return result
 */
package crypto.symmetric;

public class XORCipher {
    public static String cipher(String text, String key) {
        if (key == null || key.isEmpty()) return text;
        
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char tChar = text.charAt(i);
            char kChar = key.charAt(i % key.length());
            result.append((char) (tChar ^ kChar));
        }
        return result.toString();
    }
    
    public static void main(String[] args) {
        System.out.println("--- XOR Cipher ---");
        String original = "SecretMessage";
        String key = "key";
        
        String encrypted = cipher(original, key);
        String decrypted = cipher(encrypted, key);
        
        System.out.println("Original:  " + original);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
    }
}
