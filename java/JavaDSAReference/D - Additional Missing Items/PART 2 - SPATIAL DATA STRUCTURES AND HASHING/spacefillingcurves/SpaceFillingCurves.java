package spatial.spacefillingcurves;

/**
 * SPACE-FILLING CURVES (Z-order / Morton code and Hilbert curve)
 *
 * WHAT IT IS:
 * A way to number every cell of a 2D grid with a single integer so that cells that
 * are close in 2D tend to get close numbers. That lets ordinary 1D structures
 * (sorted arrays, B-trees, key ranges in a distributed database) store and range-
 * query 2D data.
 *
 * WHEN TO USE THIS:
 * - Spatial indexes in databases: Z-order in Amazon DynamoDB/Bigtable designs,
 *   Hilbert in Google S2 (cell ids), SQL Server spatial index, PostGIS sorting.
 * - Cache-friendly memory layouts for images, textures (GPUs use Morton order),
 *   matrices, and particle simulations.
 * - Geohash (Geohash.java) is just a Z-order curve on (lon, lat) written in base 32.
 *
 * 1. Z-ORDER (MORTON CODE)
 *    Interleave the bits of x and y: d = ... y1 x1 y0 x0.
 *    Very fast with "magic number" bit tricks (spread each 16-bit coordinate so it
 *    occupies every other bit, then OR them). The curve draws Z shapes and has
 *    occasional big JUMPS between neighbouring numbers.
 *
 * 2. HILBERT CURVE
 *    A recursive U shape: at each level the 4 quadrants are visited in U order and
 *    the sub-curves are ROTATED/FLIPPED so the end of one quadrant touches the start
 *    of the next. Result: consecutive numbers are ALWAYS adjacent cells (no jumps),
 *    so a range of Hilbert numbers covers a more compact area than the same range of
 *    Z-order numbers -> fewer disk pages per spatial query. Costs a bit more to
 *    compute (a loop over the levels instead of pure bit tricks).
 *    The conversions below are the classic iterative xy2d / d2xy algorithms.
 *
 * COMPLEXITY:
 * Morton encode/decode: O(1) (fixed number of bit operations).
 * Hilbert encode/decode: O(log n) for an n x n grid.
 */
public class SpaceFillingCurves {

    // ---------------- Z-order (Morton) ----------------

    /** Spreads the low 16 bits of v so that bit i moves to bit 2i. */
    private static long spreadBits(long v) {
        v &= 0xFFFFL;
        v = (v | (v << 8)) & 0x00FF00FFL;
        v = (v | (v << 4)) & 0x0F0F0F0FL;
        v = (v | (v << 2)) & 0x33333333L;
        v = (v | (v << 1)) & 0x55555555L;
        return v;
    }

    /** Inverse of spreadBits: collects every other bit back into the low 16 bits. */
    private static long compactBits(long v) {
        v &= 0x55555555L;
        v = (v | (v >>> 1)) & 0x33333333L;
        v = (v | (v >>> 2)) & 0x0F0F0F0FL;
        v = (v | (v >>> 4)) & 0x00FF00FFL;
        v = (v | (v >>> 8)) & 0x0000FFFFL;
        return v;
    }

    /** Morton code of (x, y), 0 <= x, y < 65536. x takes the even bits, y the odd bits. */
    public static long mortonEncode(int x, int y) {
        if (x < 0 || y < 0 || x > 0xFFFF || y > 0xFFFF) throw new IllegalArgumentException("coordinates must be 0..65535");
        return spreadBits(x) | (spreadBits(y) << 1);
    }

    public static int[] mortonDecode(long d) {
        return new int[]{(int) compactBits(d), (int) compactBits(d >>> 1)};
    }

    // ---------------- Hilbert ----------------

    /** Hilbert index of (x, y) in an n x n grid, n a power of two. */
    public static long hilbertEncode(int n, int x, int y) {
        checkSize(n, x, y);
        long d = 0;
        for (int s = n / 2; s > 0; s /= 2) {
            int rx = (x & s) > 0 ? 1 : 0;
            int ry = (y & s) > 0 ? 1 : 0;
            d += (long) s * s * ((3 * rx) ^ ry);   // which quadrant, in U order
            // rotate/flip so the sub-square is in standard orientation
            if (ry == 0) {
                if (rx == 1) { x = s - 1 - x; y = s - 1 - y; }
                int t = x; x = y; y = t;
            }
        }
        return d;
    }

    /** Inverse: cell {x, y} of Hilbert index d in an n x n grid. */
    public static int[] hilbertDecode(int n, long d) {
        if (n <= 0 || (n & (n - 1)) != 0) throw new IllegalArgumentException("n must be a power of two");
        if (d < 0 || d >= (long) n * n) throw new IllegalArgumentException("index out of range");
        int x = 0, y = 0;
        long t = d;
        for (int s = 1; s < n; s *= 2) {
            int rx = (int) (1 & (t / 2));
            int ry = (int) (1 & (t ^ rx));
            if (ry == 0) {                          // undo the rotation for this level
                if (rx == 1) { x = s - 1 - x; y = s - 1 - y; }
                int tmp = x; x = y; y = tmp;
            }
            x += s * rx;
            y += s * ry;
            t /= 4;
        }
        return new int[]{x, y};
    }

    private static void checkSize(int n, int x, int y) {
        if (n <= 0 || (n & (n - 1)) != 0) throw new IllegalArgumentException("n must be a power of two");
        if (x < 0 || y < 0 || x >= n || y >= n) throw new IllegalArgumentException("cell outside the grid");
    }

    private static void printGrid(String title, int n, boolean hilbert) {
        System.out.println(title);
        for (int y = n - 1; y >= 0; y--) {
            StringBuilder sb = new StringBuilder("  ");
            for (int x = 0; x < n; x++) {
                long d = hilbert ? hilbertEncode(n, x, y) : mortonEncode(x, y);
                sb.append(String.format("%3d", d));
            }
            System.out.println(sb);
        }
    }

    public static void main(String[] args) {
        System.out.println("--- SPACE-FILLING CURVES DEMO ---");
        printGrid("Z-order numbers on a 4x4 grid (y up):", 4, false);
        printGrid("Hilbert numbers on a 4x4 grid (y up):", 4, true);

        // Average 2D distance between cells whose numbers are consecutive
        int n = 256;
        double zJump = 0, hJump = 0;
        int[] prevZ = mortonDecode(0), prevH = hilbertDecode(n, 0);
        for (long d = 1; d < (long) n * n; d++) {
            int[] z = mortonDecode(d), h = hilbertDecode(n, d);
            zJump += Math.abs(z[0] - prevZ[0]) + Math.abs(z[1] - prevZ[1]);
            hJump += Math.abs(h[0] - prevH[0]) + Math.abs(h[1] - prevH[1]);
            prevZ = z;
            prevH = h;
        }
        System.out.printf("%nOn a %dx%d grid, average step between consecutive numbers:%n", n, n);
        System.out.printf("  Z-order: %.3f cells   Hilbert: %.3f cells (always exactly 1)%n",
                zJump / (n * n - 1), hJump / (n * n - 1));
    }
}
