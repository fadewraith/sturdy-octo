package distributed.hashing;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * RENDEZVOUS HASHING (Highest Random Weight, HRW)
 *
 * WHAT IT IS:
 * Decide which node owns a key: for EVERY node compute score = hash(node, key) and
 * pick the node with the HIGHEST score. Every client computes the same answer
 * with no coordination and no shared ring.
 *
 * WHEN TO USE THIS:
 * - Choosing a cache/storage node for a key (Apache Ignite, Twitter's EventBus,
 *   GitHub's load balancer, many CDNs).
 * - Choosing the top-k replicas: just take the k highest scores.
 * - Alternative to consistent hashing (Part A ConsistentHashing.java).
 *
 * WHY IT'S GOOD - MINIMAL DISRUPTION:
 * - Remove a node: only keys whose WINNER was that node move (each to its
 *   second-best node). Every other key's ranking is unchanged.
 * - Add a node: a key moves only if the NEW node beats the old winner.
 * - Load is even without "virtual nodes": each node wins about 1/N of keys.
 * Consistent hashing has the same movement property but needs ~100 virtual
 * nodes per server for an even spread; HRW is O(N) per lookup instead of
 * O(log N), which is fine for tens or hundreds of nodes.
 *
 * WEIGHTED HRW (nodes with different capacity):
 * Turn the hash into a uniform u in (0, 1) and use score = -weight / ln(u).
 * Then node i wins with probability weight_i / sum(weights) exactly (this is the
 * "exponential race" trick), and the minimal-disruption property is kept.
 *
 * COMPLEXITY:
 * Lookup: O(N) hashes for N nodes. Top-k replicas: O(N log N).
 * Space: O(N).
 */
public class RendezvousHashing {

    private final Map<String, Double> nodes = new LinkedHashMap<>(); // node -> weight

    public void addNode(String node) { addNode(node, 1.0); }

    public void addNode(String node, double weight) {
        if (weight <= 0) throw new IllegalArgumentException("weight must be positive");
        nodes.put(node, weight);
    }

    public void removeNode(String node) { nodes.remove(node); }

    public int nodeCount() { return nodes.size(); }

    /** 64-bit FNV-1a over node + separator + key, then a murmur3 finalizer to mix the bits. */
    static long hash(String node, String key) {
        long h = 0xcbf29ce484222325L;
        for (byte b : node.getBytes(StandardCharsets.UTF_8)) { h ^= b & 0xff; h *= 0x100000001b3L; }
        h ^= 0xff; h *= 0x100000001b3L; // separator: ("ab","c") must differ from ("a","bc")
        for (byte b : key.getBytes(StandardCharsets.UTF_8)) { h ^= b & 0xff; h *= 0x100000001b3L; }
        h ^= h >>> 33; h *= 0xff51afd7ed558ccdL;
        h ^= h >>> 33; h *= 0xc4ceb9fe1a85ec53L;
        h ^= h >>> 33;
        return h;
    }

    private double score(String node, double weight, String key) {
        double u = ((hash(node, key) >>> 11) + 0.5) / (double) (1L << 53); // uniform in (0, 1), never 0 or 1
        return -weight / Math.log(u);
    }

    /** The node that owns key, or null if there are no nodes. */
    public String ownerOf(String key) {
        String best = null;
        double bestScore = Double.NEGATIVE_INFINITY;
        for (Map.Entry<String, Double> e : nodes.entrySet()) {
            double s = score(e.getKey(), e.getValue(), key);
            // tie-break on the name so every client agrees even on equal scores
            if (s > bestScore || (s == bestScore && e.getKey().compareTo(best) > 0)) {
                bestScore = s;
                best = e.getKey();
            }
        }
        return best;
    }

    /** The k best nodes for key, best first (replica placement). */
    public List<String> topNodes(String key, int k) {
        List<String> all = new ArrayList<>(nodes.keySet());
        Map<String, Double> scores = new LinkedHashMap<>();
        for (String n : all) scores.put(n, score(n, nodes.get(n), key));
        all.sort(Comparator.<String>comparingDouble(scores::get).reversed().thenComparing(Comparator.reverseOrder()));
        return all.subList(0, Math.min(k, all.size()));
    }

    public static void main(String[] args) {
        System.out.println("--- RENDEZVOUS (HRW) HASHING DEMO ---");
        RendezvousHashing hrw = new RendezvousHashing();
        for (String n : new String[]{"cache-a", "cache-b", "cache-c", "cache-d"}) hrw.addNode(n);

        int keys = 100_000;
        Map<String, Integer> load = new java.util.TreeMap<>();
        String[] before = new String[keys];
        for (int i = 0; i < keys; i++) {
            before[i] = hrw.ownerOf("user:" + i);
            load.merge(before[i], 1, Integer::sum);
        }
        System.out.println("Load over 4 nodes: " + load); // ~25,000 each, no virtual nodes needed

        hrw.removeNode("cache-c");
        int moved = 0, movedWrongly = 0;
        for (int i = 0; i < keys; i++) {
            String now = hrw.ownerOf("user:" + i);
            if (!now.equals(before[i])) {
                moved++;
                if (!before[i].equals("cache-c")) movedWrongly++;
            }
        }
        System.out.println("Removed cache-c: " + moved + " keys moved (" + movedWrongly + " of them were not on cache-c)");
        // ~25,000 moved, 0 wrongly

        System.out.println("3 replicas for user:42: " + hrw.topNodes("user:42", 3));

        RendezvousHashing weighted = new RendezvousHashing();
        weighted.addNode("small", 1);
        weighted.addNode("big", 3);
        int big = 0;
        for (int i = 0; i < keys; i++) if (weighted.ownerOf("k" + i).equals("big")) big++;
        System.out.printf("Weighted 1:3 -> big node owns %.1f%% of keys (expected 75%%)%n", 100.0 * big / keys);
    }
}
