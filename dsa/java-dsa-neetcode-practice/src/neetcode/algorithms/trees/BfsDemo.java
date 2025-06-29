package neetcode.algorithms.trees;

import java.util.ArrayDeque;
import java.util.Deque;

public class BfsDemo {
    
    public void bfs(BinarySearchTree root) {
        Deque<BinarySearchTree> queue = new ArrayDeque<>();
        if (root != null) {
            queue.add(root);
        }    
        int level = 0;
        while(!queue.isEmpty()) {
            System.out.print("level " + level + ": ");
            int levelLength = queue.size();
            for (int i = 0; i < levelLength; i++) {
                BinarySearchTree curr = queue.removeFirst();
                System.out.print(curr.val + " ");
                if(curr.left != null) {
                    queue.add(curr.left);  
                }
                if(curr.right != null) {
                    queue.add(curr.right);
                }  
            }
            level++;
            System.out.println();
        }
        
    }

    // write a function to find the max depth of a binary tree
    public int maxDepth(BinarySearchTree root) {
        if (root == null) {
            return 0;
        }
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    // write a function to find the min depth of a binary tree
    public int minDepth(BinarySearchTree root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 1;
        }
        if (root.left == null) {
            return minDepth(root.right) + 1;
        }
        if (root.right == null) {
            return minDepth(root.left) + 1;
        }
        return Math.min(minDepth(root.left), minDepth(root.right)) + 1;
    }

    // write a main function to test the above functions
    public static void main(String[] args) {
        BinarySearchTree root = new BinarySearchTree(10);
        BinarySearchTree.insert(root, 5);
        BinarySearchTree.insert(root, 15);
        BinarySearchTree.insert(root, 3);
        BinarySearchTree.insert(root, 7);
        BinarySearchTree.insert(root, 12);
        BinarySearchTree.insert(root, 18);
        BinarySearchTree.insert(root, 1);
        BinarySearchTree.insert(root, 4);
        BinarySearchTree.insert(root, 6);
        BinarySearchTree.insert(root, 8);
        BinarySearchTree.insert(root, 11);
        BinarySearchTree.insert(root, 13);
        BinarySearchTree.insert(root, 17);
        BinarySearchTree.insert(root, 20);

        BinarySearchTree.printTree(root, 0);

        BfsDemo bfsDemo = new BfsDemo();
        bfsDemo.bfs(root);

        System.out.println("Max depth: " + bfsDemo.maxDepth(root));
        System.out.println("Min depth: " + bfsDemo.minDepth(root));
    }
}