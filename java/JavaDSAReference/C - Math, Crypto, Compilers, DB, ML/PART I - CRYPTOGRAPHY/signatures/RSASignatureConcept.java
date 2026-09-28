// EDUCATIONAL IMPLEMENTATION ONLY. Do not use this code for real security-critical work. Production code must use java.security / javax.crypto (MessageDigest, Cipher, KeyPairGenerator, etc.), which are the unavoidable built-ins here per the Inbuilt Function Exception Rule — real cryptography requires audited, constant-time implementations that are extremely difficult to get right from scratch.
/**
 * WHAT IT IS: Basic RSA-based signing/verification concept.
 * STRATEGY: Sign with private key (encrypt hash), verify with public key (decrypt and compare hash).
 * TIME/SPACE COMPLEXITY: O(k^3) time for signing and verifying where k is the number of bits in the key. Space complexity is O(k).
 * REAL-WORLD ANALOGY / USE CASE: Like a handwritten signature on a document; anyone can verify it's yours, but only you can write it. Used for software updates, digital certificates.
 * WHEN TO USE / COMBINATION: Used to provide authenticity and integrity to messages.
 * 
 * PSEUDOCODE:
 * Sign(message, privateKey):
 *   hash = hashFunction(message)
 *   signature = hash^d mod n
 *   return signature
 * Verify(message, signature, publicKey):
 *   expectedHash = hashFunction(message)
 *   actualHash = signature^e mod n
 *   return expectedHash == actualHash
 */
package crypto.signatures;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class RSASignatureConcept {

    static class RSAKeys {
        BigInteger n, e, d;
        public RSAKeys(int bitlen) {
            SecureRandom r = new SecureRandom();
            BigInteger p = BigInteger.probablePrime(bitlen / 2, r);
            BigInteger q = BigInteger.probablePrime(bitlen / 2, r);
            n = p.multiply(q);
            BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
            e = BigInteger.valueOf(65537);
            while (phi.gcd(e).intValue() > 1) {
                e = e.add(BigInteger.valueOf(2));
            }
            d = e.modInverse(phi);
        }
    }

    public static BigInteger hashString(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(message.getBytes());
            return new BigInteger(1, hash);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static BigInteger sign(String message, BigInteger d, BigInteger n) {
        BigInteger hash = hashString(message);
        return hash.modPow(d, n);
    }

    public static boolean verify(String message, BigInteger signature, BigInteger e, BigInteger n) {
        BigInteger expectedHash = hashString(message);
        BigInteger actualHash = signature.modPow(e, n);
        return expectedHash.equals(actualHash);
    }

    public static void main(String[] args) {
        System.out.println("--- Testing RSA Signature Edge Cases ---");
        
        RSAKeys keys = new RSAKeys(1024);
        
        String message = "This is a signed message.";
        BigInteger signature = sign(message, keys.d, keys.n);
        System.out.println("Signature: " + signature.toString(16).substring(0, 32) + "...");
        
        boolean isValid = verify(message, signature, keys.e, keys.n);
        System.out.println("Is valid signature? " + isValid);
        
        System.out.println("\nEdge case: Tampered message");
        String tamperedMessage = "This is a signed message!";
        boolean isTamperedValid = verify(tamperedMessage, signature, keys.e, keys.n);
        System.out.println("Is tampered signature valid? " + isTamperedValid);
        
        System.out.println("\nEdge case: Forged signature");
        BigInteger forgedSignature = signature.add(BigInteger.ONE);
        boolean isForgedValid = verify(message, forgedSignature, keys.e, keys.n);
        System.out.println("Is forged signature valid? " + isForgedValid);
        
        System.out.println("\nEdge case: Empty message");
        BigInteger sigEmpty = sign("", keys.d, keys.n);
        boolean isEmptyValid = verify("", sigEmpty, keys.e, keys.n);
        System.out.println("Is empty message signature valid? " + isEmptyValid);
    }
}
