package net.routing;

/**
 * LONGEST PREFIX MATCH (IP routing table as a binary trie)
 *
 * WHAT IT IS:
 * A router's forwarding table holds CIDR prefixes such as
 *     10.0.0.0/8 -> eth0,   10.1.0.0/16 -> eth1,   0.0.0.0/0 -> default gateway
 * A packet to 10.1.2.3 matches BOTH 10/8 and 10.1/16; the rule is to use the
 * MOST SPECIFIC (longest) matching prefix -> eth1.
 *
 * WHEN TO USE THIS:
 * - Every IP router and firewall; also CDN/geo-IP lookups, AWS security groups.
 * - The same trie idea solves "longest matching path" for URL routers.
 *
 * STRATEGY (binary trie / "unibit trie"):
 * Each prefix is a path of bits from the root (first bit = most significant bit).
 * A /16 prefix ends at depth 16; that node stores the next hop.
 * Lookup walks the address bit by bit from the root, remembering the LAST node on
 * the way that had a next hop. When the path ends (no child), that remembered hop
 * is the longest match. The root itself is 0.0.0.0/0, the default route.
 * Real routers compress the trie (Patricia / multibit tries, e.g. Linux uses an
 * LC-trie) to need fewer memory accesses, but the matching rule is the same.
 *
 * COMPLEXITY:
 * Insert / delete / lookup: O(32) = O(address bits).
 * Space: O(number of prefixes * 32) nodes in the worst case.
 */
public class LongestPrefixMatch {

    private static class Node {
        Node[] child = new Node[2];
        String nextHop; // null = no prefix ends here
    }

    private final Node root = new Node();
    private int size = 0;

    /** Parses "a.b.c.d" into an unsigned 32-bit value stored in an int. */
    public static int parseIp(String ip) {
        String[] parts = ip.trim().split("\\.");
        if (parts.length != 4) throw new IllegalArgumentException("bad IPv4 address: " + ip);
        int value = 0;
        for (String p : parts) {
            int octet = Integer.parseInt(p);
            if (octet < 0 || octet > 255) throw new IllegalArgumentException("bad octet in " + ip);
            value = (value << 8) | octet;
        }
        return value;
    }

    public static String formatIp(int ip) {
        return (ip >>> 24) + "." + ((ip >>> 16) & 255) + "." + ((ip >>> 8) & 255) + "." + (ip & 255);
    }

    private static int bit(int address, int depth) {
        return (address >>> (31 - depth)) & 1; // depth 0 = most significant bit
    }

    /** Adds or replaces a route. cidr like "10.1.0.0/16". */
    public void addRoute(String cidr, String nextHop) {
        String[] parts = cidr.split("/");
        int prefix = parseIp(parts[0]);
        int length = Integer.parseInt(parts[1]);
        addRoute(prefix, length, nextHop);
    }

    public void addRoute(int prefix, int length, String nextHop) {
        if (length < 0 || length > 32) throw new IllegalArgumentException("prefix length must be 0..32");
        Node node = root;
        for (int d = 0; d < length; d++) {
            int b = bit(prefix, d);
            if (node.child[b] == null) node.child[b] = new Node();
            node = node.child[b];
        }
        if (node.nextHop == null) size++;
        node.nextHop = nextHop;
    }

    /** Removes a route. Returns true if it existed. (Empty branches are left in place.) */
    public boolean removeRoute(int prefix, int length) {
        Node node = root;
        for (int d = 0; d < length && node != null; d++) node = node.child[bit(prefix, d)];
        if (node == null || node.nextHop == null) return false;
        node.nextHop = null;
        size--;
        return true;
    }

    /** Next hop of the longest matching prefix, or null if nothing matches. */
    public String lookup(int address) {
        Node node = root;
        String best = root.nextHop; // /0 default route, if any
        for (int d = 0; d < 32; d++) {
            node = node.child[bit(address, d)];
            if (node == null) break;
            if (node.nextHop != null) best = node.nextHop; // a longer match
        }
        return best;
    }

    public String lookup(String ip) {
        return lookup(parseIp(ip));
    }

    public int size() { return size; }

    public static void main(String[] args) {
        System.out.println("--- LONGEST PREFIX MATCH DEMO ---");
        LongestPrefixMatch table = new LongestPrefixMatch();
        table.addRoute("0.0.0.0/0", "default-gateway");
        table.addRoute("10.0.0.0/8", "eth0");
        table.addRoute("10.1.0.0/16", "eth1");
        table.addRoute("10.1.2.0/24", "eth2");
        table.addRoute("192.168.1.0/24", "wifi");

        String[] packets = {"10.1.2.3", "10.1.9.9", "10.200.0.1", "192.168.1.77", "8.8.8.8"};
        for (String p : packets) {
            System.out.println(p + " -> " + table.lookup(p));
        }
        // eth2, eth1, eth0, wifi, default-gateway
    }
}
