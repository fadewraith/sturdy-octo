public class BstMain {

    /*
    * BST = a tree data structure, where each node is greaterthat its left child, but less than its right.
    * benefit: easy to locate a node when they're in the order
    * t.c. - best case O(log n)
    *        worst case O(n)
    * s.c. - O(n)
    * orders matters if bst is unbalanced
    * */

    public static void main(String[] args) {

        long start = System.nanoTime();

        BinarySearchTree tree = new BinarySearchTree();

        tree.insert(new BstNode(5));
        tree.insert(new BstNode(1));
        tree.insert(new BstNode(9));
        tree.insert(new BstNode(2));
        tree.insert(new BstNode(7));
        tree.insert(new BstNode(3));
        tree.insert(new BstNode(6));
        tree.insert(new BstNode(4));
        tree.insert(new BstNode(8));

        tree.remove(0);
        tree.display();
        long duration = (System.nanoTime() - start) / 1000000;
        System.out.println(duration+" ms");
        System.out.println();
//        System.out.println(tree.search(5));
//        tree.remove(0);
    }
}
