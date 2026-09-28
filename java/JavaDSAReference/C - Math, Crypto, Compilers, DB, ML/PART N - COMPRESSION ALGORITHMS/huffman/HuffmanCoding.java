package compression.huffman;

import java.util.*;

/**
 * WHAT IT IS: A lossless data compression algorithm that assigns variable-length codes to input characters.
 * STRATEGY: Frequencies of characters are calculated. A min-heap is used to build a binary tree where leaves represent characters. Paths to leaves give codes (0 for left, 1 for right).
 * TIME/SPACE COMPLEXITY: O(N log K) time where N is message length and K is distinct characters. Space: O(K) for tree.
 * REAL-WORLD ANALOGY / USE CASE: Morse code (shorter codes for frequent letters). Used in ZIP, JPEG.
 * WHEN TO USE / COMBINATION: When data has uneven character distribution. Often combined with LZ77 in DEFLATE.
 * 
 * PSEUDOCODE:
 * count frequencies
 * put leaves in min heap
 * while heap size > 1:
 *   pop two smallest, create parent with sum frequency
 *   push parent to heap
 * traverse tree to assign codes
 */
public class HuffmanCoding {
    static class Node implements Comparable<Node> {
        char c;
        int freq;
        Node left, right;
        Node(char c, int freq, Node left, Node right) {
            this.c = c; this.freq = freq; this.left = left; this.right = right;
        }
        public int compareTo(Node o) { return this.freq - o.freq; }
    }

    private Map<Character, String> codes = new HashMap<>();
    private Node root;

    public void buildTree(String text) {
        if (text == null || text.isEmpty()) return;
        Map<Character, Integer> freq = new HashMap<>();
        for (char c : text.toCharArray()) freq.put(c, freq.getOrDefault(c, 0) + 1);

        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> e : freq.entrySet()) {
            pq.add(new Node(e.getKey(), e.getValue(), null, null));
        }

        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            pq.add(new Node('\0', left.freq + right.freq, left, right));
        }
        root = pq.poll();
        generateCodes(root, "");
    }

    private void generateCodes(Node node, String code) {
        if (node == null) return;
        if (node.left == null && node.right == null) {
            codes.put(node.c, code.isEmpty() ? "0" : code);
        }
        generateCodes(node.left, code + "0");
        generateCodes(node.right, code + "1");
    }

    public String encode(String text) {
        if (text == null || text.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) sb.append(codes.get(c));
        return sb.toString();
    }

    public String decode(String encoded) {
        if (encoded == null || encoded.isEmpty() || root == null) return "";
        StringBuilder sb = new StringBuilder();
        Node curr = root;
        for (char bit : encoded.toCharArray()) {
            if (curr.left == null && curr.right == null) {
                sb.append(curr.c);
            } else {
                curr = (bit == '0') ? curr.left : curr.right;
                if (curr.left == null && curr.right == null) {
                    sb.append(curr.c);
                    curr = root;
                }
            }
        }
        if (root.left == null && root.right == null) {
            sb.setLength(0);
            for(int i=0; i<encoded.length(); i++) sb.append(root.c);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        HuffmanCoding hc = new HuffmanCoding();
        String text = "this is an example for huffman encoding";
        hc.buildTree(text);
        String enc = hc.encode(text);
        System.out.println("Original: " + text);
        System.out.println("Encoded: " + enc);
        System.out.println("Decoded: " + hc.decode(enc));
        
        HuffmanCoding singleChar = new HuffmanCoding();
        singleChar.buildTree("aaaaa");
        System.out.println("Encoded aaaaa: " + singleChar.encode("aaaaa"));
        System.out.println("Decoded: " + singleChar.decode(singleChar.encode("aaaaa")));
    }
}
