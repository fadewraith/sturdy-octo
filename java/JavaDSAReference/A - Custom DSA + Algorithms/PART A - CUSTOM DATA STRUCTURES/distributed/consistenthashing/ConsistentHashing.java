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
    private final int VIRTUAL_NODES = 3;

    @SuppressWarnings("unchecked")
    public ConsistentHashing(int initialCapacity) {
        this.capacity = initialCapacity;
        this.ring = (RingNode<S>[]) new RingNode[capacity];
        this.size = 0;
    }

    private int hash(String key) {
        int h = 0;
        for (int i = 0; i < key.length(); i++) {
            h = 31 * h + key.charAt(i);
        }
        return Math.abs(h);
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
        ch.addServer("Server_A");
        ch.addServer("Server_B");
        
        System.out.println("Routed 'Alice' to: " + ch.getServer("Alice"));
    }
}
