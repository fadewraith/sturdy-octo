package crypto.asymmetric;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * ELLIPTIC CURVE DIFFIE-HELLMAN (ECDH) on NIST P-256 (secp256r1)
 *
 * WHAT IT IS:
 * The same key-exchange idea as DiffieHellman.java, but in the GROUP OF POINTS on an
 * elliptic curve  y^2 = x^3 + a*x + b  (mod p)  instead of numbers mod p.
 *   private key: a random number d          public key: Q = d * G  (G = base point)
 *   Alice and Bob exchange public keys; Alice computes dA * QB, Bob dB * QA - both
 *   equal dA * dB * G, the shared secret. Recovering d from d * G is the elliptic
 *   curve discrete log problem.
 *
 * WHY ELLIPTIC CURVES?
 * A 256-bit EC key is about as strong as a 3072-bit RSA or classic DH key: smaller
 * keys, faster handshakes. TLS 1.3 (X25519 / P-256), Signal, SSH, Bitcoin
 * (secp256k1 with ECDSA) all use EC.
 *
 * POINT ARITHMETIC (affine coordinates, all mod p):
 *   P + Q (P != Q):  s = (y2 - y1) / (x2 - x1)
 *   P + P (double):  s = (3*x1^2 + a) / (2*y1)       (slope of the tangent)
 *   then  x3 = s^2 - x1 - x2,  y3 = s*(x1 - x3) - y1
 *   P + (-P) = O, the "point at infinity" (the identity, null here).
 * SCALAR MULTIPLICATION d * P: double-and-add over the bits of d - O(log d) steps,
 * like fast exponentiation. Written here as a MONTGOMERY LADDER: it does the same
 * operations for every bit, which avoids leaking key bits through timing (a real
 * implementation would also need constant-time field arithmetic; BigInteger isn't).
 *
 * VALIDATE received public keys (on the curve, not infinity) - otherwise
 * "invalid curve" attacks can leak the private key.
 *
 * COMPLEXITY:
 * Scalar multiplication: O(log d) point operations, each a few modular
 * multiplications and one modular inverse on 256-bit numbers.
 */
public class ECDH {

    // NIST P-256 domain parameters (FIPS 186-4)
    public static final BigInteger P = new BigInteger("ffffffff00000001000000000000000000000000ffffffffffffffffffffffff", 16);
    public static final BigInteger A = P.subtract(BigInteger.valueOf(3));
    public static final BigInteger B = new BigInteger("5ac635d8aa3a93e7b3ebbd55769886bc651d06b0cc53b0f63bce3c3e27d2604b", 16);
    public static final BigInteger N = new BigInteger("ffffffff00000000ffffffffffffffffbce6faada7179e84f3b9cac2fc632551", 16); // order of G
    public static final Point G = new Point(
            new BigInteger("6b17d1f2e12c4247f8bce6e563a440f277037d812deb33a0f4a13945d898c296", 16),
            new BigInteger("4fe342e2fe1a7f9b8ee7eb4a7c0f9e162bce33576b315ececbb6406837bf51f5", 16));

    /** A curve point in affine coordinates; null represents the point at infinity. */
    public static final class Point {
        public final BigInteger x, y;
        public Point(BigInteger x, BigInteger y) { this.x = x; this.y = y; }
        @Override public boolean equals(Object o) { return o instanceof Point p && p.x.equals(x) && p.y.equals(y); }
        @Override public int hashCode() { return x.hashCode() * 31 + y.hashCode(); }
        @Override public String toString() { return "(" + x.toString(16) + ", " + y.toString(16) + ")"; }
    }

    public static boolean isOnCurve(Point q) {
        if (q == null) return false;
        if (q.x.signum() < 0 || q.x.compareTo(P) >= 0 || q.y.signum() < 0 || q.y.compareTo(P) >= 0) return false;
        BigInteger lhs = q.y.multiply(q.y).mod(P);
        BigInteger rhs = q.x.pow(3).add(A.multiply(q.x)).add(B).mod(P);
        return lhs.equals(rhs);
    }

    public static Point add(Point p1, Point p2) {
        if (p1 == null) return p2;
        if (p2 == null) return p1;
        BigInteger s;
        if (p1.x.equals(p2.x)) {
            if (p1.y.add(p2.y).mod(P).signum() == 0) return null; // P + (-P) = infinity (also covers y = 0 doubling)
            // doubling: tangent slope
            s = p1.x.pow(2).multiply(BigInteger.valueOf(3)).add(A)
                    .multiply(p1.y.shiftLeft(1).modInverse(P)).mod(P);
        } else {
            s = p2.y.subtract(p1.y).multiply(p2.x.subtract(p1.x).modInverse(P)).mod(P);
        }
        BigInteger x3 = s.multiply(s).subtract(p1.x).subtract(p2.x).mod(P);
        BigInteger y3 = s.multiply(p1.x.subtract(x3)).subtract(p1.y).mod(P);
        return new Point(x3, y3);
    }

    /** k * point with a Montgomery ladder (same sequence of operations for every bit). */
    public static Point multiply(BigInteger k, Point point) {
        Point r0 = null, r1 = point;            // invariant: r1 = r0 + point
        for (int i = k.bitLength() - 1; i >= 0; i--) {
            if (k.testBit(i)) { r0 = add(r0, r1); r1 = add(r1, r1); }
            else              { r1 = add(r0, r1); r0 = add(r0, r0); }
        }
        return r0;
    }

    public static BigInteger newPrivateKey(SecureRandom rng) {
        BigInteger d;
        do { d = new BigInteger(N.bitLength(), rng); } while (d.signum() == 0 || d.compareTo(N) >= 0);
        return d;
    }

    public static Point publicKey(BigInteger privateKey) { return multiply(privateKey, G); }

    /** Shared secret = x-coordinate of (myPrivate * theirPublic), after validating their key. */
    public static BigInteger sharedSecret(BigInteger myPrivate, Point theirPublic) {
        if (!isOnCurve(theirPublic)) throw new IllegalArgumentException("public key is not on the curve");
        Point s = multiply(myPrivate, theirPublic);
        if (s == null) throw new IllegalStateException("shared point is infinity");
        return s.x;
    }

    public static void main(String[] args) {
        System.out.println("--- ECDH (P-256) DEMO ---");
        System.out.println("Base point G on the curve? " + isOnCurve(G));
        System.out.println("n * G = infinity? " + (multiply(N, G) == null)); // the group order

        SecureRandom rng = new SecureRandom();
        BigInteger alicePriv = newPrivateKey(rng), bobPriv = newPrivateKey(rng);
        Point alicePub = publicKey(alicePriv), bobPub = publicKey(bobPriv);
        BigInteger aliceSecret = sharedSecret(alicePriv, bobPub);
        BigInteger bobSecret = sharedSecret(bobPriv, alicePub);
        System.out.println("Alice's secret: " + aliceSecret.toString(16));
        System.out.println("Bob's secret:   " + bobSecret.toString(16));
        System.out.println("Equal? " + aliceSecret.equals(bobSecret));

        Point fake = new Point(BigInteger.ONE, BigInteger.TWO);
        try {
            sharedSecret(alicePriv, fake);
        } catch (IllegalArgumentException e) {
            System.out.println("Rejected a bogus public key: " + e.getMessage());
        }
    }
}
