// EDUCATIONAL IMPLEMENTATION ONLY. Do not use this code for real security-critical work. Production code must use java.security / javax.crypto (MessageDigest, Cipher, KeyPairGenerator, etc.), which are the unavoidable built-ins here per the Inbuilt Function Exception Rule — real cryptography requires audited, constant-time implementations that are extremely difficult to get right from scratch.
/**
 * WHAT IT IS: RSA (Rivest-Shamir-Adleman) is an asymmetric cryptographic algorithm.
 * STRATEGY: Relies on the practical difficulty of factoring the product of two large prime numbers, the "factoring problem".
 * TIME/SPACE COMPLEXITY: O(k^3) time for key generation, encryption, and decryption where k is the number of bits in the key. Space complexity is O(k).
 * REAL-WORLD ANALOGY / USE CASE: Like having a public padlock that anyone can use to lock a box, but only the owner has the private key to unlock it. Used for secure data transmission.
 * WHEN TO USE / COMBINATION: RSA = Number Theory (primes, modular exponentiation, modular inverse) + Euler's Totient.
 * 
 * PSEUDOCODE:
 * KeyGen:
 *   Select p, q primes
 *   n = p * q
 *   phi = (p-1) * (q-1)
 *   e = prime to phi
 *   d = modular inverse of e mod phi
 *   Pub = (e, n), Priv = (d, n)
 * Encrypt:
 *   c = m^e mod n
 * Decrypt:
 *   m = c^d mod n
 */
package crypto.asymmetric;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA {
    private BigInteger n, d, e;
    private int bitlen = 1024;

    public RSA(BigInteger newn, BigInteger newe) {
        n = newn;
        e = newe;
    }

    public RSA(int bits) {
        bitlen = bits;
        SecureRandom r = new SecureRandom();
        BigInteger p = BigInteger.probablePrime(bitlen / 2, r);
        BigInteger q = BigInteger.probablePrime(bitlen / 2, r);
        n = p.multiply(q);
        BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.valueOf(65537); // common value for e
        while (m.gcd(e).intValue() > 1) {
            e = e.add(BigInteger.valueOf(2));
        }
        d = e.modInverse(m);
    }

    public synchronized String encrypt(String message) {
        return (new BigInteger(message.getBytes())).modPow(e, n).toString();
    }

    public synchronized BigInteger encrypt(BigInteger message) {
        return message.modPow(e, n);
    }

    public synchronized String decrypt(String message) {
        return new String((new BigInteger(message)).modPow(d, n).toByteArray());
    }

    public synchronized BigInteger decrypt(BigInteger message) {
        return message.modPow(d, n);
    }
    
    public BigInteger getN() { return n; }
    public BigInteger getE() { return e; }
    public BigInteger getD() { return d; }

    public static void main(String[] args) {
        System.out.println("--- Testing RSA Edge Cases ---");
        RSA rsa = new RSA(1024);
        
        System.out.println("Original String: 'Hello World'");
        String encrypted = rsa.encrypt("Hello World");
        System.out.println("Encrypted String: " + encrypted);
        String decrypted = rsa.decrypt(encrypted);
        System.out.println("Decrypted String: " + decrypted);
        
        System.out.println("\nEdge case: Empty string");
        try {
            String encryptedEmpty = rsa.encrypt("");
            String decryptedEmpty = rsa.decrypt(encryptedEmpty);
            System.out.println("Decrypted empty string: '" + decryptedEmpty + "'");
        } catch (Exception ex) {
            System.out.println("Exception with empty string: " + ex.getMessage());
        }
        
        System.out.println("\nEdge case: Large string");
        String largeStr = "A".repeat(100); // 100 bytes is less than 128 bytes (1024 bits) limit for basic RSA
        String encLarge = rsa.encrypt(largeStr);
        String decLarge = rsa.decrypt(encLarge);
        System.out.println("Large string match: " + largeStr.equals(decLarge));
    }
}
