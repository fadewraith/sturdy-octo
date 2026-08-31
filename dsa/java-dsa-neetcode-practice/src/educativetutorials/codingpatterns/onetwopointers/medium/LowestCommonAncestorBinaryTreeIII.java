package educativetutorials.codingpatterns.onetwopointers.medium;

import educativetutorials.codingpatterns.commonutilities.BinaryTree;
import educativetutorials.codingpatterns.commonutilities.PrintLCA;
import educativetutorials.codingpatterns.commonutilities.TreeNode;

import java.util.Arrays;
import java.util.List;

public class LowestCommonAncestorBinaryTreeIII {

    /**

     * This solution finds the Lowest Common Ancestor (LCA) of two nodes in a
     * binary tree using a smart two-pointer approach.
     *
     * <p>
     * We start by placing one pointer at node {@code p} and the other at node
     * {@code q}. Both pointers move up the tree at each step by following their
     * parent pointers.
     * </p>
     *
     * <p>
     * If a pointer reaches the root (i.e., its parent is {@code null}), it jumps
     * to the other starting node. This process continues until the two pointers
     * meet.
     * </p>
     *
     * <p>
     * The key idea is that by switching starting points after reaching the top,
     * both pointers end up traveling the same total distance, even if {@code p}
     * and {@code q} are at different depths.
     * </p>
     *
     * <p>
     * When they meet, that meeting point is their Lowest Common Ancestor (LCA).
     * </p>
     *
     * Algorithm:
     *
     * 1. Initialize two pointers:
     * * {@code ptr1} starting at {@code p}
     * * {@code ptr2} starting at {@code q}
     *
     * 2. While {@code ptr1} and {@code ptr2} are not pointing to the same node:
     *
     * a. If {@code ptr1} has a parent:
     * ```
     {@code ptr1 = ptr1.parent}
     ```
     *
     * ```
     Otherwise:
     ```
     * ```
     {@code ptr1 = q}
     ```
     *
     * b. If {@code ptr2} has a parent:
     * ```
     {@code ptr2 = ptr2.parent}
     ```
     *
     * ```
     Otherwise:
     ```
     * ```
     {@code ptr2 = p}
     ```
     *
     * 3. When {@code ptr1 == ptr2}, return {@code ptr1}.
     * This node is the Lowest Common Ancestor (LCA) of {@code p} and {@code q}.
     *
     * Time Complexity:
     * O(h), where h is the height of the tree.
     *
     * In the worst case, each pointer may traverse the entire height of the tree.
     *
     * Space Complexity:
     * O(1), since no additional data structures are used. Only two pointers are
     * maintained, requiring constant space.
     */


    public static TreeNode LowestCommonAncestor(TreeNode p, TreeNode q) {
                    // Initialize two pointers
                    TreeNode ptr1 = p;
                    TreeNode ptr2 = q;

                    // Traverse until they meet
                    while (ptr1 != ptr2) {
                        // Move ptr1 to parent node or switch to the other node if reached the root
                        if (ptr1.parent != null) {
                ptr1 = ptr1.parent;
            } else {
                ptr1 = q;
            }

            // Move ptr2 to parent node or switch to the other node if reached the root
            if (ptr2.parent != null) {
                ptr2 = ptr2.parent;
            } else {
                ptr2 = p;
            }
        }

        // Return ptr1 or ptr2, since they are the same at this point
        return ptr1;
    }

    public static void main(String[] args) {
        List<List<Integer>> input_trees = Arrays.asList(
                Arrays.asList(100, 50, 200, 25, 75, 350),
                Arrays.asList(100, 200, 75, 50, 25, 350),
                Arrays.asList(350, 100, 75, 50, 200, 25),
                Arrays.asList(100, 50, 200, 25, 75, 350),
                Arrays.asList(25, 50, 75, 100, 200, 350)
        );
        List<List<Integer>> input_nodes = Arrays.asList(
                Arrays.asList(25, 75),
                Arrays.asList(50, 350),
                Arrays.asList(100, 200),
                Arrays.asList(50, 25),
                Arrays.asList(350, 200)
        );

        for (int i = 0; i < input_trees.size(); i++) {
            BinaryTree tree = new BinaryTree(input_trees.get(i));
            System.out.println((i + 1) + ".\tBinary tree:");
            PrintLCA.displayTree(tree.getRoot());
            System.out.println("\n\tp = " + input_nodes.get(i).get(0));
            System.out.println("\tq = " + input_nodes.get(i).get(1));
            TreeNode p = tree.find(tree.getRoot(), input_nodes.get(i).get(0));
            TreeNode q = tree.find(tree.getRoot(), input_nodes.get(i).get(1));
            TreeNode lca = LowestCommonAncestor(p, q);
            System.out.println("\n\tLowest common ancestor: " + lca.data);
            System.out.println(new String(new char[100]).replace("\0", "-"));
        }
    }
}
