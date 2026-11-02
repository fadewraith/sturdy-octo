package distributed.merkle;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * MERKLE TREE (hash tree): inclusion proofs + anti-entropy diff
 *
 * WHAT IT IS:
 * A binary tree of hashes. Leaves = hash of each data block; every parent =
 * hash(left child || right child). The ROOT hash fingerprints ALL the data:
 * change one byte anywhere and the root changes.
 *
 * WHEN TO USE THIS:
 * - Git (commits/trees), Bitcoin/Ethereum (transactions in a block), BitTorrent and
 *   IPFS (verify downloaded pieces), Certificate Transparency logs.
 * - ANTI-ENTROPY in Dynamo / Cassandra / Riak: two replicas compare roots; if they
 *   differ, compare children and descend only into differing subtrees. Finding
 *   d differing blocks among N costs O(d log N) hash comparisons, not O(N).
 *
 * INCLUSION PROOF ("audit path"):
 * To prove block i is in a tree whose root you trust, the prover sends only the
 * SIBLING hash at each level (log N hashes). The verifier rehashes upward:
 *   h = hash(block); for each sibling: h = hash(h || sib) or hash(sib || h)
 * and accepts if h == root. A light client checks a transaction with ~20 hashes
 * instead of downloading a million.
 *
 * DETAILS USED HERE:
 * - SHA-256 (java.security.MessageDigest).
 * - Domain separation: leaves hash 0x00 || data, inner nodes hash 0x01 || L || R
 *   (as in RFC 6962), so an inner node can never be passed off as a leaf.
 * - An odd node at the end of a level is promoted unchanged to the next level.
 *
 * COMPLEXITY:
 * Build: O(N) hashes. Root: O(1). Proof size and verification: O(log N).
 * Diff of two same-size trees: O(d log N) for d differing leaves.
 */
public class MerkleTree {

    private final List<byte[][]> levels = new ArrayList<>(); // levels.get(0) = leaf hashes, last = {root}

    public MerkleTree(List<byte[]> blocks) {
        if (blocks.isEmpty()) throw new IllegalArgumentException("need at least one block");
        byte[][] level = new byte[blocks.size()][];
        for (int i = 0; i < blocks.size(); i++) level[i] = leafHash(blocks.get(i));
        levels.add(level);
        while (level.length > 1) {
            byte[][] up = new byte[(level.length + 1) / 2][];
            for (int i = 0; i < up.length; i++) {
                int l = 2 * i, r = 2 * i + 1;
                up[i] = r < level.length ? nodeHash(level[l], level[r]) : level[l]; // odd one is promoted
            }
            levels.add(up);
            level = up;
        }
    }

    public static MerkleTree ofStrings(String... blocks) {
        List<byte[]> list = new ArrayList<>();
        for (String s : blocks) list.add(s.getBytes(StandardCharsets.UTF_8));
        return new MerkleTree(list);
    }

    public byte[] root() { return levels.get(levels.size() - 1)[0].clone(); }

    public int leafCount() { return levels.get(0).length; }

    // ---------------- hashing ----------------
    private static byte[] sha256(byte prefix, byte[]... parts) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(prefix);
            for (byte[] p : parts) md.update(p);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    public static byte[] leafHash(byte[] data) { return sha256((byte) 0, data); }

    public static byte[] nodeHash(byte[] left, byte[] right) { return sha256((byte) 1, left, right); }

    // ---------------- inclusion proofs ----------------
    /** One step of a proof: the sibling hash and whether it sits on the LEFT. */
    public static class ProofStep {
        public final byte[] sibling;
        public final boolean siblingOnLeft;
        ProofStep(byte[] sibling, boolean siblingOnLeft) { this.sibling = sibling; this.siblingOnLeft = siblingOnLeft; }
    }

    /** Audit path for leaf index i (levels where the node was promoted contribute no step). */
    public List<ProofStep> proof(int index) {
        if (index < 0 || index >= leafCount()) throw new IndexOutOfBoundsException("leaf " + index);
        List<ProofStep> path = new ArrayList<>();
        for (int lv = 0; lv < levels.size() - 1; lv++) {
            byte[][] level = levels.get(lv);
            int sibling = index ^ 1;
            if (sibling < level.length) path.add(new ProofStep(level[sibling], sibling < index));
            index /= 2;
        }
        return path;
    }

    /** Verifier side: needs only the data block, the proof and the trusted root. */
    public static boolean verify(byte[] block, List<ProofStep> proof, byte[] root) {
        byte[] h = leafHash(block);
        for (ProofStep step : proof) {
            h = step.siblingOnLeft ? nodeHash(step.sibling, h) : nodeHash(h, step.sibling);
        }
        return MessageDigest.isEqual(h, root);
    }

    // ---------------- anti-entropy diff ----------------
    /**
     * Leaf indices whose blocks differ between two trees with the same number of
     * leaves, found by descending only into subtrees whose hashes differ.
     * comparisons[0] receives the number of hash comparisons made.
     */
    public static List<Integer> diff(MerkleTree a, MerkleTree b, int[] comparisons) {
        if (a.leafCount() != b.leafCount()) throw new IllegalArgumentException("trees must have the same number of leaves");
        List<Integer> out = new ArrayList<>();
        int top = a.levels.size() - 1;
        descend(a, b, top, 0, out, comparisons);
        return out;
    }

    private static void descend(MerkleTree a, MerkleTree b, int lv, int idx, List<Integer> out, int[] comparisons) {
        comparisons[0]++;
        if (Arrays.equals(a.levels.get(lv)[idx], b.levels.get(lv)[idx])) return; // identical subtree: skip it all
        if (lv == 0) { out.add(idx); return; }
        int below = a.levels.get(lv - 1).length;
        if (2 * idx < below) descend(a, b, lv - 1, 2 * idx, out, comparisons);
        if (2 * idx + 1 < below) descend(a, b, lv - 1, 2 * idx + 1, out, comparisons);
    }

    public static String hex(byte[] h) {
        StringBuilder sb = new StringBuilder();
        for (byte x : h) sb.append(String.format("%02x", x));
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("--- MERKLE TREE DEMO ---");
        String[] tx = {"alice->bob 5", "bob->carol 2", "carol->dave 1", "dave->erin 7", "erin->frank 3"};
        MerkleTree tree = ofStrings(tx);
        byte[] root = tree.root();
        System.out.println("Root of 5 transactions: " + hex(root).substring(0, 16) + "...");

        List<ProofStep> p = tree.proof(2);
        System.out.println("Proof for tx 2 has " + p.size() + " hashes; valid? "
                + verify(tx[2].getBytes(StandardCharsets.UTF_8), p, root));          // 3 hashes, true
        System.out.println("Same proof for a forged tx valid? "
                + verify("carol->dave 1000".getBytes(StandardCharsets.UTF_8), p, root)); // false

        // Anti-entropy: two replicas of 1024 blocks that differ in 2 blocks
        List<byte[]> replicaA = new ArrayList<>(), replicaB = new ArrayList<>();
        for (int i = 0; i < 1024; i++) {
            byte[] block = ("block-" + i).getBytes(StandardCharsets.UTF_8);
            replicaA.add(block);
            replicaB.add(i == 100 || i == 777 ? ("changed-" + i).getBytes(StandardCharsets.UTF_8) : block);
        }
        int[] comparisons = {0};
        List<Integer> differing = diff(new MerkleTree(replicaA), new MerkleTree(replicaB), comparisons);
        System.out.println("Replicas differ in blocks " + differing + ", found with " + comparisons[0]
                + " hash comparisons instead of 1024"); // [100, 777]
    }
}
