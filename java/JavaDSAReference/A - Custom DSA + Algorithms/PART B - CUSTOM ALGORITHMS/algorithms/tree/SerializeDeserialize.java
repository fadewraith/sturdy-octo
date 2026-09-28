package algorithms.tree;

/**
 * SERIALIZE AND DESERIALIZE BINARY TREE
 * 
 * WHAT IT IS:
 * Converting a complex pointer-based memory structure (Binary Tree) into a flat String 
 * (Serialization), and then completely reconstructing the exact same tree from that String 
 * (Deserialization).
 * 
 * WHEN TO USE THIS:
 * - Saving a tree structure to a hard drive or database.
 * - Sending a tree over a network via an API (JSON serialization).
 * 
 * STRATEGY (Preorder Traversal):
 * - Serialize: We do a Preorder Traversal (Root, Left, Right). We append the value to 
 *   a string. Crucially, if we hit a null node, we append a special marker (like "X"). 
 *   This marker is strictly required so we know when to stop going down a branch!
 * - Deserialize: We split the string by commas into a Queue. We pop the first element 
 *   (which is the root). Then we recursively call the function to build the left child, 
 *   then the right child. Because it was serialized in Preorder, the Queue perfectly feeds 
 *   the recursive reconstruction.
 * 
 * COMPLEXITY:
 * Time: O(N) for both serialize and deserialize.
 * Space: O(N) for the String, Queue, and recursion stack.
 */
public class SerializeDeserialize {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int x) { val = x; }
    }

    // --- CUSTOM QUEUE FOR DESERIALIZATION ---
    private static class StringQueue {
        String[] data;
        int head = 0;
        
        StringQueue(String str) {
            this.data = str.split(",");
        }
        String poll() {
            if (head >= data.length) return null;
            return data[head++];
        }
    }

    // Encodes a tree to a single string.
    public static String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serializeHelper(root, sb);
        return sb.toString();
    }

    private static void serializeHelper(TreeNode node, StringBuilder sb) {
        if (node == null) {
            sb.append("X,");
        } else {
            sb.append(node.val).append(",");
            serializeHelper(node.left, sb);
            serializeHelper(node.right, sb);
        }
    }

    // Decodes your encoded data to tree.
    public static TreeNode deserialize(String data) {
        StringQueue queue = new StringQueue(data);
        return deserializeHelper(queue);
    }

    private static TreeNode deserializeHelper(StringQueue queue) {
        String val = queue.poll();
        if (val == null || val.equals("X")) {
            return null;
        }

        TreeNode node = new TreeNode(Integer.parseInt(val));
        node.left = deserializeHelper(queue);
        node.right = deserializeHelper(queue);
        return node;
    }

    public static void main(String[] args) {
        System.out.println("--- SERIALIZE & DESERIALIZE DEMO ---");
        
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(5);

        String serialized = serialize(root);
        System.out.println("Serialized String: " + serialized); 
        // Expected: "1,2,X,X,3,4,X,X,5,X,X,"

        TreeNode deserialized = deserialize(serialized);
        System.out.println("Deserialized Root matches? " + (deserialized.val == 1)); 
        System.out.println("Deserialized Right-Left matches? " + (deserialized.right.left.val == 4)); 
    }
}
