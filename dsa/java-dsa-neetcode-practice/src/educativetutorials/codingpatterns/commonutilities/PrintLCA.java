package educativetutorials.codingpatterns.commonutilities;

public class PrintLCA {

    public static void displayTree(TreeNode root) {
        if (root == null) {
            System.out.println("(empty tree)");
            return;
        }

        print(root, "", true);
    }

    private static void print(TreeNode node, String prefix, boolean isTail) {
        if (node == null) {
            return;
        }

        if (node.right != null) {
            print(node.right, prefix + (isTail ? "│   " : "    "), false);
        }

        System.out.println(prefix + (isTail ? "└── " : "┌── ") + node.data);

        if (node.left != null) {
            print(node.left, prefix + (isTail ? "    " : "│   "), true);
        }
    }
}