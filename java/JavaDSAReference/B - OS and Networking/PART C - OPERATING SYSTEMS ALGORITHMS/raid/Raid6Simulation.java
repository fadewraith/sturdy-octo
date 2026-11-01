package os.raid;

import java.util.Arrays;

/**
 * RAID 6 (DOUBLE DISTRIBUTED PARITY)
 *
 * WHAT IT IS:
 * RAID 6 extends RAID 5 by adding another parity block.
 * It can survive TWO simultaneous disk failures.
 *
 * THE MATH (the same scheme Linux md-raid uses):
 * Work byte by byte in the Galois Field GF(2^8): 256 values where "+" is XOR and
 * "*" is polynomial multiplication mod x^8 + x^4 + x^3 + x^2 + 1 (0x11D).
 * In a field every non-zero value has an inverse, so we can DIVIDE - that is what
 * makes solving two equations for two unknowns possible.
 *
 * With data disks D0..D(n-1) and generator g = 2:
 *   P = D0 ^ D1 ^ ... ^ D(n-1)                     (plain XOR, like RAID 5)
 *   Q = g^0*D0 ^ g^1*D1 ^ ... ^ g^(n-1)*D(n-1)     (each disk gets a different weight)
 *
 * RECOVERY CASES:
 *   - Lost P and/or Q only     -> recompute them from the data.
 *   - Lost one data disk (+ Q) -> rebuild it with P, exactly like RAID 5.
 *   - Lost one data disk + P   -> rebuild it with Q: Dx = (Q ^ Q_without_x) / g^x.
 *   - Lost two data disks x, y -> A = Dx ^ Dy (from P), B = g^x*Dx ^ g^y*Dy (from Q)
 *                                 Dx = (B ^ g^y*A) / (g^x ^ g^y),  Dy = A ^ Dx.
 *
 * Note: a real array also ROTATES which disk holds P and Q per stripe so parity
 * writes are spread out. This simulation shows a single stripe.
 */
public class Raid6Simulation {

    // --------------------------------------------------------
    // GF(2^8) arithmetic with log/antilog tables
    // --------------------------------------------------------
    private static final int[] EXP = new int[512]; // g^i, doubled so EXP[a + b] needs no "% 255"
    private static final int[] LOG = new int[256];

    static {
        int x = 1;
        for (int i = 0; i < 255; i++) {
            EXP[i] = x;
            LOG[x] = i;
            x <<= 1;                  // multiply by g = 2
            if ((x & 0x100) != 0) x ^= 0x11D;
        }
        for (int i = 255; i < 512; i++) EXP[i] = EXP[i - 255];
    }

    static int gfMul(int a, int b) {
        if (a == 0 || b == 0) return 0;
        return EXP[LOG[a] + LOG[b]];
    }

    static int gfDiv(int a, int b) {
        if (b == 0) throw new ArithmeticException("division by zero in GF(2^8)");
        if (a == 0) return 0;
        return EXP[LOG[a] + 255 - LOG[b]];
    }

    static int gfPowG(int i) {
        return EXP[i % 255];
    }

    // --------------------------------------------------------
    // Parity computation
    // --------------------------------------------------------

    /** disks[0..n-1] = data blocks (all the same length). Returns {P, Q}. */
    public static byte[][] computeParity(byte[][] data) {
        int len = data[0].length;
        byte[] p = new byte[len], q = new byte[len];
        for (int i = 0; i < data.length; i++) {
            int weight = gfPowG(i);
            for (int b = 0; b < len; b++) {
                int d = data[i][b] & 0xff;
                p[b] ^= d;
                q[b] ^= gfMul(weight, d);
            }
        }
        return new byte[][]{p, q};
    }

    // --------------------------------------------------------
    // Recovery of any two lost disks
    // --------------------------------------------------------

    /**
     * stripe = n data disks followed by P (index n) and Q (index n + 1).
     * Lost disks are null. Rebuilds them in place. At most 2 may be lost.
     */
    public static void recover(byte[][] stripe) {
        int n = stripe.length - 2;
        int pIdx = n, qIdx = n + 1;
        int lost1 = -1, lost2 = -1, lostCount = 0;
        for (int i = 0; i < stripe.length; i++) {
            if (stripe[i] == null) {
                if (lostCount == 0) lost1 = i; else lost2 = i;
                lostCount++;
            }
        }
        if (lostCount == 0) return;
        if (lostCount > 2) throw new IllegalStateException("RAID 6 cannot survive " + lostCount + " failures");
        int len = -1; // block size, taken from any surviving disk
        for (byte[] disk : stripe) {
            if (disk != null) { len = disk.length; break; }
        }

        boolean dataLost1 = lost1 < n;
        boolean dataLost2 = lostCount == 2 && lost2 < n;

        if (!dataLost1) {
            // Only parity disks lost: recompute from intact data
            byte[][] pq = computeParity(Arrays.copyOf(stripe, n));
            stripe[pIdx] = pq[0];
            stripe[qIdx] = pq[1];
            return;
        }

        if (!dataLost2) {
            int x = lost1;
            byte[] rebuilt = new byte[len];
            boolean pAlive = stripe[pIdx] != null;
            for (int b = 0; b < len; b++) {
                if (pAlive) {
                    // RAID-5 style: Dx = P ^ (all other data)
                    int v = stripe[pIdx][b] & 0xff;
                    for (int i = 0; i < n; i++) if (i != x) v ^= stripe[i][b] & 0xff;
                    rebuilt[b] = (byte) v;
                } else {
                    // P is gone too: Dx = (Q ^ sum of g^i * Di for i != x) / g^x
                    int v = stripe[qIdx][b] & 0xff;
                    for (int i = 0; i < n; i++) if (i != x) v ^= gfMul(gfPowG(i), stripe[i][b] & 0xff);
                    rebuilt[b] = (byte) gfDiv(v, gfPowG(x));
                }
            }
            stripe[x] = rebuilt;
            byte[][] pq = computeParity(Arrays.copyOf(stripe, n)); // refresh any lost parity
            if (stripe[pIdx] == null) stripe[pIdx] = pq[0];
            if (stripe[qIdx] == null) stripe[qIdx] = pq[1];
            return;
        }

        // Two data disks lost: solve the 2x2 system in GF(2^8)
        int x = lost1, y = lost2;
        byte[] dx = new byte[len], dy = new byte[len];
        int gx = gfPowG(x), gy = gfPowG(y);
        int denom = gx ^ gy; // non-zero because x != y and g has order 255
        for (int b = 0; b < len; b++) {
            int a = stripe[pIdx][b] & 0xff;   // becomes Dx ^ Dy
            int q = stripe[qIdx][b] & 0xff;   // becomes g^x*Dx ^ g^y*Dy
            for (int i = 0; i < n; i++) {
                if (i == x || i == y) continue;
                int d = stripe[i][b] & 0xff;
                a ^= d;
                q ^= gfMul(gfPowG(i), d);
            }
            int vx = gfDiv(q ^ gfMul(gy, a), denom);
            dx[b] = (byte) vx;
            dy[b] = (byte) (a ^ vx);
        }
        stripe[x] = dx;
        stripe[y] = dy;
    }

    public static void simulateRaid6() {
        System.out.println("--- RAID 6 (DOUBLE PARITY) SIMULATION ---");

        String[] blocks = {"Disk0: invoices", "Disk1: payroll!", "Disk2: backups.", "Disk3: photos.."};
        byte[][] data = new byte[blocks.length][];
        for (int i = 0; i < blocks.length; i++) data[i] = blocks[i].getBytes();
        byte[][] pq = computeParity(data);

        byte[][] stripe = new byte[blocks.length + 2][];
        for (int i = 0; i < blocks.length; i++) stripe[i] = data[i].clone();
        stripe[blocks.length] = pq[0];
        stripe[blocks.length + 1] = pq[1];
        System.out.println("4 data disks + P + Q. First byte of P = " + (pq[0][0] & 0xff) + ", Q = " + (pq[1][0] & 0xff));

        System.out.println("\n[!] CRASH! Disk 1 AND Disk 3 have failed simultaneously!");
        stripe[1] = null;
        stripe[3] = null;
        recover(stripe);
        System.out.println("Recovered Disk 1: " + new String(stripe[1]));
        System.out.println("Recovered Disk 3: " + new String(stripe[3]));

        // Prove it works for EVERY possible pair of failures (data or parity)
        int total = 0, ok = 0;
        for (int a = 0; a < stripe.length; a++) {
            for (int b = a + 1; b < stripe.length; b++) {
                byte[][] s = new byte[stripe.length][];
                for (int i = 0; i < stripe.length; i++) s[i] = stripe[i].clone();
                s[a] = null;
                s[b] = null;
                recover(s);
                boolean same = true;
                for (int i = 0; i < stripe.length; i++) same &= Arrays.equals(s[i], stripe[i]);
                total++;
                if (same) ok++;
            }
        }
        System.out.println("\nTried all " + total + " two-disk failure combinations: " + ok + " fully recovered.");
        System.out.println("RAID 6 successfully survived 2 simultaneous hardware failures!");
    }

    public static void main(String[] args) {
        simulateRaid6();
    }
}
