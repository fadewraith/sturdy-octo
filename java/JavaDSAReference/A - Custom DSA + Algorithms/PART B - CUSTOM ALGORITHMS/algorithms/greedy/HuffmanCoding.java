package algorithms.greedy;

/**
 * HUFFMAN CODING
 * 
 * WHAT IT IS:
 * A lossless data compression algorithm. It assigns variable-length binary codes to 
 * input characters, with shorter codes assigned to more frequent characters.
 * 
 * COMBINATION USAGE:
 * - Greedy Algorithm + Min-Heap (Priority Queue) + Binary Tree construction.
 * 
 * STRATEGY:
 * 1. Create a leaf node for each unique character and build a Min-Heap based on their frequencies.
 * 2. Extract the TWO nodes with the lowest frequencies from the Min-Heap.
 * 3. Create a new internal node with a frequency equal to the sum of the two nodes' frequencies.
 * 4. Make the first extracted node its left child (assigning a '0' edge), and the second 
 *    its right child (assigning a '1' edge).
 * 5. Push this new internal node back into the Min-Heap.
 * 6. Repeat until the Min-Heap contains only 1 node (the root of the Huffman Tree).
 * 
 * COMPLEXITY:
 * Time: O(N log N) where N is number of unique characters.
 * Space: O(N) to store the tree.
 */
public class HuffmanCoding {

    // Tree Node
    static class HuffmanNode {
        int frequency;
        char character;
        HuffmanNode left, right;
    }

    // A simple internal Min-Heap to avoid java.util.PriorityQueue
    static class MinHeap {
        HuffmanNode[] heap;
        int size;
        
        MinHeap(int capacity) {
            heap = new HuffmanNode[capacity];
            size = 0;
        }

        void insert(HuffmanNode node) {
            int current = size++;
            heap[current] = node;
            // Bubble Up
            while (current > 0 && heap[current].frequency < heap[(current - 1) / 2].frequency) {
                HuffmanNode temp = heap[current];
                heap[current] = heap[(current - 1) / 2];
                heap[(current - 1) / 2] = temp;
                current = (current - 1) / 2;
            }
        }

        HuffmanNode extractMin() {
            HuffmanNode min = heap[0];
            heap[0] = heap[--size];
            heap[size] = null;
            
            // Bubble Down
            int current = 0;
            while (true) {
                int left = 2 * current + 1;
                int right = 2 * current + 2;
                int smallest = current;
                
                if (left < size && heap[left].frequency < heap[smallest].frequency) smallest = left;
                if (right < size && heap[right].frequency < heap[smallest].frequency) smallest = right;
                
                if (smallest == current) break;
                
                HuffmanNode temp = heap[current];
                heap[current] = heap[smallest];
                heap[smallest] = temp;
                current = smallest;
            }
            return min;
        }
    }

    public static void buildHuffmanTree(char[] charArray, int[] charFreq) {
        int n = charArray.length;
        MinHeap heap = new MinHeap(n);

        // 1. Create leaf nodes and push to Min-Heap
        for (int i = 0; i < n; i++) {
            HuffmanNode node = new HuffmanNode();
            node.character = charArray[i];
            node.frequency = charFreq[i];
            node.left = null;
            node.right = null;
            heap.insert(node);
        }

        // 2. Build the tree
        HuffmanNode root = null;
        while (heap.size > 1) {
            HuffmanNode x = heap.extractMin();
            HuffmanNode y = heap.extractMin();

            HuffmanNode z = new HuffmanNode();
            z.character = '-'; // Internal node marker
            z.frequency = x.frequency + y.frequency;
            z.left = x;
            z.right = y;

            root = z;
            heap.insert(z);
        }

        // 3. Print the generated codes by traversing the tree
        System.out.println("Char\tHuffman Code");
        printCodes(root, "");
    }

    private static void printCodes(HuffmanNode root, String s) {
        if (root.left == null && root.right == null && Character.isLetter(root.character)) {
            System.out.println(root.character + "\t" + s);
            return;
        }
        printCodes(root.left, s + "0");
        printCodes(root.right, s + "1");
    }

    public static void main(String[] args) {
        System.out.println("--- HUFFMAN CODING DEMO ---");
        
        char[] chars = { 'a', 'b', 'c', 'd', 'e', 'f' };
        int[] freq = { 5, 9, 12, 13, 16, 45 };
        
        buildHuffmanTree(chars, freq);
        // Expected: The most frequent char 'f' (45) will have the shortest code (just 1 bit, '0')
        // Less frequent chars like 'a' (5) will have long codes (like '1100')
    }
}
