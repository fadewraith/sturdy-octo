package probabilistic.hyperloglog;

/**
 * HyperLogLog
 * Approximate cardinality (distinct count) estimation with minimal memory.
 * Applies to: counting unique visitors/IPs at massive scale.
 */
public class HyperLogLog {
    private final int p;
    private final int m;
    private final byte[] registers;
    private final double alphaMM;

    public HyperLogLog(int p) {
        if (p < 4 || p > 16) {
            throw new IllegalArgumentException("p must be between 4 and 16");
        }
        this.p = p;
        this.m = 1 << p;
        this.registers = new byte[m];
        
        switch (m) {
            case 16:
                alphaMM = 0.673 * m * m;
                break;
            case 32:
                alphaMM = 0.697 * m * m;
                break;
            case 64:
                alphaMM = 0.709 * m * m;
                break;
            default:
                alphaMM = (0.7213 / (1 + 1.079 / m)) * m * m;
                break;
        }
    }

    private int murmurHash3(String data) {
        int c1 = 0xcc9e2d51;
        int c2 = 0x1b873593;
        int h1 = 0;
        byte[] bytes = data.getBytes();
        int length = bytes.length;
        int i = 0;

        while (i <= length - 4) {
            int k1 = (bytes[i] & 0xFF) | ((bytes[i + 1] & 0xFF) << 8) | ((bytes[i + 2] & 0xFF) << 16) | ((bytes[i + 3] & 0xFF) << 24);
            k1 *= c1;
            k1 = Integer.rotateLeft(k1, 15);
            k1 *= c2;

            h1 ^= k1;
            h1 = Integer.rotateLeft(h1, 13);
            h1 = h1 * 5 + 0xe6546b64;
            i += 4;
        }

        int k1 = 0;
        switch (length % 4) {
            case 3:
                k1 ^= (bytes[i + 2] & 0xFF) << 16;
            case 2:
                k1 ^= (bytes[i + 1] & 0xFF) << 8;
            case 1:
                k1 ^= (bytes[i] & 0xFF);
                k1 *= c1;
                k1 = Integer.rotateLeft(k1, 15);
                k1 *= c2;
                h1 ^= k1;
        }

        h1 ^= length;
        h1 ^= h1 >>> 16;
        h1 *= 0x85ebca6b;
        h1 ^= h1 >>> 13;
        h1 *= 0xc2b2ae35;
        h1 ^= h1 >>> 16;

        return h1;
    }

    public void add(String item) {
        int hash = murmurHash3(item);
        int idx = hash >>> (32 - p);
        int w = hash << p;
        int rho = Integer.numberOfLeadingZeros(w) + 1;
        registers[idx] = (byte) Math.max(registers[idx], rho);
    }

    public long estimate() {
        double z = 0.0;
        for (int i = 0; i < m; i++) {
            z += 1.0 / (1 << registers[i]);
        }
        double e = alphaMM / z;

        if (e <= 5.0 / 2.0 * m) {
            int v = 0;
            for (int i = 0; i < m; i++) {
                if (registers[i] == 0) {
                    v++;
                }
            }
            if (v != 0) {
                e = m * Math.log((double) m / v);
            }
        } else if (e > (1.0 / 30.0) * 4294967296.0) {
            e = -4294967296.0 * Math.log(1.0 - (e / 4294967296.0));
        }

        return (long) e;
    }

    public static void main(String[] args) {
        System.out.println("--- HyperLogLog Simulation ---");
        HyperLogLog hll = new HyperLogLog(10); 
        
        System.out.println("Adding 100000 unique items...");
        for (int i = 0; i < 100000; i++) {
            hll.add("item-" + i);
        }
        
        System.out.println("Adding some duplicates...");
        for (int i = 0; i < 50000; i++) {
            hll.add("item-" + i);
        }
        
        System.out.println("Estimated Cardinality: " + hll.estimate() + " (Expected ~100000)");
    }
}
