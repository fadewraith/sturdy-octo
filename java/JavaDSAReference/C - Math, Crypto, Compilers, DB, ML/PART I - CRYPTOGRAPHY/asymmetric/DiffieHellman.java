// EDUCATIONAL IMPLEMENTATION ONLY. Do not use this code for real security-critical work. Production code must use java.security / javax.crypto (MessageDigest, Cipher, KeyPairGenerator, etc.), which are the unavoidable built-ins here per the Inbuilt Function Exception Rule — real cryptography requires audited, constant-time implementations that are extremely difficult to get right from scratch.
/**
 * WHAT IT IS: Diffie-Hellman Key Exchange algorithm.
 * STRATEGY: Allows two parties that have no prior knowledge of each other to jointly establish a shared secret key over an insecure channel.
 * TIME/SPACE COMPLEXITY: O(log y) time for modular exponentiation where y is the exponent. Space complexity is O(1).
 * REAL-WORLD ANALOGY / USE CASE: Like two people mixing paint colors in public to arrive at a shared secret color that onlookers cannot deduce. Used for TLS/SSL.
 * WHEN TO USE / COMBINATION: Used to establish a shared secret before symmetric encryption.
 * 
 * PSEUDOCODE:
 * Alice and Bob agree to use a modulus p and base g.
 * Alice chooses a secret integer a, then sends Bob A = g^a mod p.
 * Bob chooses a secret integer b, then sends Alice B = g^b mod p.
 * Alice computes s = B^a mod p.
 * Bob computes s = A^b mod p.
 * Alice and Bob now share a secret (s).
 */
package crypto.asymmetric;

import java.math.BigInteger;
import java.security.SecureRandom;

public class DiffieHellman {

    public static void main(String[] args) {
        System.out.println("--- Testing Diffie-Hellman Edge Cases ---");
        
        SecureRandom random = new SecureRandom();
        // Typically p is a large prime and g is a primitive root modulo p
        // For educational purposes, generating a 512-bit prime p
        BigInteger p = BigInteger.probablePrime(512, random);
        BigInteger g = BigInteger.valueOf(2); // Simplified base
        
        System.out.println("Public Modulus (p): " + p.toString(16));
        System.out.println("Public Base (g): " + g.toString());
        
        // Alice
        BigInteger a = new BigInteger(256, random); // Alice's secret
        BigInteger A = g.modPow(a, p); // Alice's public value sent to Bob
        
        // Bob
        BigInteger b = new BigInteger(256, random); // Bob's secret
        BigInteger B = g.modPow(b, p); // Bob's public value sent to Alice
        
        // Key derivation
        BigInteger aliceSharedSecret = B.modPow(a, p);
        BigInteger bobSharedSecret = A.modPow(b, p);
        
        System.out.println("Alice's Shared Secret Match: " + aliceSharedSecret.equals(bobSharedSecret));
        
        System.out.println("\nEdge case: Small secret (e.g., 1)");
        BigInteger smallA = BigInteger.ONE;
        BigInteger smallB = BigInteger.valueOf(2);
        BigInteger smallA_Pub = g.modPow(smallA, p);
        BigInteger smallB_Pub = g.modPow(smallB, p);
        System.out.println("Small secrets match: " + smallB_Pub.modPow(smallA, p).equals(smallA_Pub.modPow(smallB, p)));
    }
}
