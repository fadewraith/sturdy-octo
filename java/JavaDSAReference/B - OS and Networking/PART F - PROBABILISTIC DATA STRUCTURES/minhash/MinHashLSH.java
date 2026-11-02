package probabilistic.minhash;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * MINHASH + LOCALITY-SENSITIVE HASHING (near-duplicate detection)
 *
 * WHAT IT IS:
 * - JACCARD SIMILARITY of two sets: |A intersect B| / |A union B|.
 * - MINHASH compresses a set into a short SIGNATURE (k numbers) such that the
 *   fraction of equal positions in two signatures ESTIMATES their Jaccard
 *   similarity - without ever comparing the sets.
 * - LSH (banding) finds the PAIRS of similar documents among millions without
 *   comparing every pair.
 *
 * WHEN TO USE THIS:
 * - Near-duplicate web pages (Google, Common Crawl), plagiarism detection,
 *   deduplicating LLM training data, similar-item recommendations, clustering.
 *
 * WHY MINHASH WORKS:
 * Apply a random hash function h to every element and take the MINIMUM. The element
 * of A union B with the smallest hash is equally likely to be any of them, and
 * min(h(A)) == min(h(B)) exactly when that element is in BOTH sets. So
 *   P[min h(A) == min h(B)] = |A intersect B| / |A union B| = Jaccard(A, B).
 * With k independent hash functions, the match fraction estimates J with standard
 * error about sqrt(J(1-J)/k) (k = 128 -> about +/- 0.04).
 *
 * SHINGLES: a document becomes the set of its k-character substrings
 * ("hello" with k=3 -> {hel, ell, llo}), so similar text = similar sets.
 *
 * LSH BANDING:
 * Split each k-number signature into b BANDS of r rows (k = b * r). Hash each band
 * into a bucket; two documents become a CANDIDATE PAIR if they share a bucket in at
 * least one band. For similarity s:
 *   P[candidate] = 1 - (1 - s^r)^b       (an S-shaped curve)
 * The threshold where it jumps is about (1/b)^(1/r). b=20, r=5 -> ~0.55:
 * pairs above that are almost always found, pairs far below almost never.
 * Only candidates get an exact (or signature) comparison.
 *
 * COMPLEXITY:
 * Signature: O(|set| * k). Estimate: O(k).
 * LSH: O(n * b) bucket inserts for n documents + the candidate checks
 * (vs O(n^2) comparisons for all pairs).
 */
public class MinHashLSH {

    private final long[] seeds;

    public MinHashLSH(int numHashes, long seed) {
        Random rng = new Random(seed);
        seeds = new long[numHashes];
        for (int i = 0; i < numHashes; i++) seeds[i] = rng.nextLong();
    }

    public int numHashes() { return seeds.length; }

    private static long mix(long h) {
        h ^= h >>> 33; h *= 0xff51afd7ed558ccdL;
        h ^= h >>> 33; h *= 0xc4ceb9fe1a85ec53L;
        h ^= h >>> 33;
        return h;
    }

    private static long hashString(String s) {
        long h = 0xcbf29ce484222325L;
        for (byte b : s.getBytes(StandardCharsets.UTF_8)) { h ^= b & 0xff; h *= 0x100000001b3L; }
        return mix(h);
    }

    /** Character k-shingles of a text (lower-cased, whitespace collapsed). */
    public static Set<String> shingles(String text, int k) {
        String t = text.toLowerCase().replaceAll("\\s+", " ").trim();
        Set<String> out = new HashSet<>();
        for (int i = 0; i + k <= t.length(); i++) out.add(t.substring(i, i + k));
        if (out.isEmpty() && !t.isEmpty()) out.add(t);
        return out;
    }

    public long[] signature(Set<String> set) {
        long[] sig = new long[seeds.length];
        java.util.Arrays.fill(sig, Long.MAX_VALUE);
        for (String element : set) {
            long base = hashString(element);
            for (int i = 0; i < seeds.length; i++) {
                long h = mix(base ^ seeds[i]); // the i-th "random permutation"
                if (h < sig[i]) sig[i] = h;
            }
        }
        return sig;
    }

    public static double estimate(long[] a, long[] b) {
        int same = 0;
        for (int i = 0; i < a.length; i++) if (a[i] == b[i]) same++;
        return (double) same / a.length;
    }

    public static <T> double jaccard(Set<T> a, Set<T> b) {
        if (a.isEmpty() && b.isEmpty()) return 1.0;
        int inter = 0;
        for (T x : a) if (b.contains(x)) inter++;
        return (double) inter / (a.size() + b.size() - inter);
    }

    /** Candidate pairs {i, j} (i < j) that share a bucket in at least one band. */
    public static Set<List<Integer>> lshCandidates(List<long[]> signatures, int bands, int rows) {
        if (signatures.isEmpty()) return new TreeSet<>();
        if (bands * rows != signatures.get(0).length) throw new IllegalArgumentException("bands * rows must equal the signature length");
        Set<List<Integer>> pairs = new HashSet<>();
        for (int b = 0; b < bands; b++) {
            Map<Long, List<Integer>> buckets = new HashMap<>();
            for (int d = 0; d < signatures.size(); d++) {
                long h = b;
                for (int r = 0; r < rows; r++) h = mix(h * 31 + signatures.get(d)[b * rows + r]);
                buckets.computeIfAbsent(h, k -> new ArrayList<>()).add(d);
            }
            for (List<Integer> bucket : buckets.values()) {
                for (int i = 0; i < bucket.size(); i++)
                    for (int j = i + 1; j < bucket.size(); j++)
                        pairs.add(List.of(bucket.get(i), bucket.get(j)));
            }
        }
        return pairs;
    }

    /** Probability that a pair with similarity s becomes a candidate. */
    public static double candidateProbability(double s, int bands, int rows) {
        return 1 - Math.pow(1 - Math.pow(s, rows), bands);
    }

    public static void main(String[] args) {
        System.out.println("--- MINHASH + LSH DEMO ---");
        String[] docs = {
            "The quick brown fox jumps over the lazy dog near the river bank",
            "The quick brown fox jumped over the lazy dog near the river bank",
            "A quick brown fox jumps over a lazy dog by the river",
            "Distributed systems need consensus algorithms like Raft and Paxos",
            "Consensus algorithms such as Paxos and Raft are needed by distributed systems"
        };
        MinHashLSH mh = new MinHashLSH(100, 42);
        List<Set<String>> sets = new ArrayList<>();
        List<long[]> sigs = new ArrayList<>();
        for (String d : docs) {
            Set<String> s = shingles(d, 4);
            sets.add(s);
            sigs.add(mh.signature(s));
        }
        System.out.println("pair   exact J   MinHash estimate");
        for (int i = 0; i < docs.length; i++)
            for (int j = i + 1; j < docs.length; j++)
                System.out.printf("%d-%d    %.2f      %.2f%n", i, j, jaccard(sets.get(i), sets.get(j)), estimate(sigs.get(i), sigs.get(j)));

        int bands = 20, rows = 5;
        System.out.println("\nLSH with " + bands + " bands x " + rows + " rows, threshold ~"
                + String.format("%.2f", Math.pow(1.0 / bands, 1.0 / rows)) + ": candidate pairs "
                + new TreeSet<>(lshCandidates(sigs, bands, rows).stream().map(Object::toString).toList()));
        // pairs with similarity ~0.5 or more are found; unrelated documents are never paired
    }
}
