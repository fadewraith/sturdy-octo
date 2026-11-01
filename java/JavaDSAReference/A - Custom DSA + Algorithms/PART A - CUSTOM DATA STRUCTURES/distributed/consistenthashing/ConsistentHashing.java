package distributed.consistenthashing;

/**
 * CONSISTENT HASHING (FROM SCRATCH)
 * 
 * WHAT IT IS:
 * A load balancing algorithm mapping Data (keys) and Servers (nodes) to a Hash Ring.
 * 
 * VIRTUAL NODES:
 * Prevents uneven distribution.
 * 
 * FROM SCRATCH STRATEGY:
 * We maintain a custom dynamic array of `RingNode` objects (hash -> server).
 * Route key by hashing and Binary Searching for the CEILING server.
 * 
 * COMPLEXITY:
 * Add/Remove Server: O(V * N)
 * Route Key: O(log (V * N))
 */
public class ConsistentHashing<K, S> {

    static class RingNode<S> {
        int hash;
        S server;

        RingNode(int hash, S server) {
            this.hash = hash;
            this.server = server;
        }
    }

    private RingNode<S>[] ring;
    private int size;
    private int capacity;
    // More virtual nodes = smoother spread. Real systems use 100-200 per server.
    private final int VIRTUAL_NODES;

    public ConsistentHashing(int initialCapacity) {
        this(initialCapacity, 100);
    }

    @SuppressWarnings("unchecked")
    public ConsistentHashing(int initialCapacity, int virtualNodes) {
        if (virtualNodes < 1) throw new IllegalArgumentException("virtualNodes must be >= 1");
        this.capacity = Math.max(1, initialCapacity);
        this.ring = (RingNode<S>[]) new RingNode[capacity];
        this.size = 0;
        this.VIRTUAL_NODES = virtualNodes;
    }

    /**
     * FNV-1a followed by a MurmurHash3 "finalizer" (avalanche step).
     * A plain 31*h+c string hash is NOT good enough here: similar names like
     * "S1_V0" and "S1_V1" land next to each other on the ring, and keys of a
     * different length land in a totally different range, so almost every key
     * wraps around to the same server. The mixing step spreads bits evenly.
     */
    private int hash(String key) {
        int h = 0x811C9DC5;                 // FNV offset basis
        for (int i = 0; i < key.length(); i++) {
            h ^= key.charAt(i);
            h *= 0x01000193;                // FNV prime
        }
        h ^= (h >>> 16);                    // Murmur3 fmix32
        h *= 0x85EBCA6B;
        h ^= (h >>> 13);
        h *= 0xC2B2AE35;
        h ^= (h >>> 16);
        return h & 0x7FFFFFFF;              // non-negative (Math.abs(MIN_VALUE) is negative!)
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        capacity *= 2;
        RingNode<S>[] newRing = (RingNode<S>[]) new RingNode[capacity];
        for (int i = 0; i < size; i++) {
            newRing[i] = ring[i];
        }
        ring = newRing;
    }

    public void addServer(S server) {
        String serverName = server.toString();
        for (int i = 0; i < VIRTUAL_NODES; i++) {
            String vNodeName = serverName + "_V" + i;
            int h = hash(vNodeName);
            
            if (size == capacity) resize();
            
            int pos = size - 1;
            while (pos >= 0 && ring[pos].hash > h) {
                ring[pos + 1] = ring[pos];
                pos--;
            }
            ring[pos + 1] = new RingNode<>(h, server);
            size++;
        }
    }

    /**
     * Removes all virtual nodes of a server. Only the keys that were on this
     * server move (to the next server clockwise) - that is the whole point of
     * consistent hashing compared to "hash % N", where almost every key moves.
     */
    public void removeServer(S server) {
        int write = 0;
        for (int read = 0; read < size; read++) {
            if (!ring[read].server.equals(server)) {
                ring[write++] = ring[read]; // keeps the ring sorted
            }
        }
        for (int i = write; i < size; i++) ring[i] = null; // help GC
        size = write;
    }

    public S getServer(K key) {
        if (size == 0) return null;
        
        int h = hash(key.toString());
        
        int left = 0;
        int right = size - 1;
        int resultIdx = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (ring[mid].hash >= h) {
                resultIdx = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        if (resultIdx == -1) {
            return ring[0].server;
        }
        
        return ring[resultIdx].server;
    }

    public static void main(String[] args) {
        System.out.println("--- GENERIC CONSISTENT HASHING DEMO ---");
        
        ConsistentHashing<String, String> ch = new ConsistentHashing<>(10);
        String[] servers = {"Server_A", "Server_B", "Server_C"};
        for (String s : servers) ch.addServer(s);

        System.out.println("Routed 'Alice' to: " + ch.getServer("Alice"));

        // Show the load spread over 3000 keys
        int keys = 3000;
        String[] before = new String[keys];
        int[] load = new int[servers.length];
        for (int i = 0; i < keys; i++) {
            before[i] = ch.getServer("key" + i);
            for (int s = 0; s < servers.length; s++) if (servers[s].equals(before[i])) load[s]++;
        }
        System.out.println("Load: A=" + load[0] + ", B=" + load[1] + ", C=" + load[2]);

        // Remove a server: only its keys should move
        ch.removeServer("Server_B");
        int moved = 0;
        for (int i = 0; i < keys; i++) {
            if (!ch.getServer("key" + i).equals(before[i])) moved++;
        }
        System.out.println("After removing Server_B, keys moved: " + moved
                + " (only Server_B's " + load[1] + " keys had to move)");
    }
}
