package org.dsaOne.binarySearchTrees;

public class MainBST {

    public static void main(String[] args) {

        /**
         *            12
         *          /    \
         *         4      20
         *        / \    /  \
         *       1   8  16  27
         * */
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.insert(12);
        bst.insert(20);
        bst.insert(16);
        bst.insert(8);
        bst.insert(4);
        bst.insert(1);
        bst.insert(27);

        System.out.println("min -> " + bst.getMin());
        System.out.println("max -> " + bst.getMax());

//        bst.traversal();
//        bst.remove(12);
//        bst.traversal();

        BinarySearchTree<Person> personBst = new BinarySearchTree<>();
        personBst.insert(new Person(12, "Adam"));
        personBst.insert(new Person(5, "John"));
        personBst.insert(new Person(78, "Doe"));
        personBst.insert(new Person(56, "Gilbert"));
        personBst.insert(new Person(34, "Sam"));

//        personBst.traversal();

        Tree<Integer> tree1 = new BinarySearchTree<>();
        tree1.insert(12);
        tree1.insert(20);
        tree1.insert(16);
        tree1.insert(8);
        tree1.insert(4);
        tree1.insert(1);
        tree1.insert(27);

        Tree<Integer> tree2 = new BinarySearchTree<>();
        tree2.insert(12);
        tree2.insert(20);
        tree2.insert(16);
        tree2.insert(4);
        tree2.insert(8);
        tree2.insert(1);
        tree2.insert(27);

        CompareBinaryTree<Integer> compareBinaryTree = new CompareBinaryTree<>();

        boolean result = compareBinaryTree.isTreeSame(tree1.getRoot(), tree2.getRoot());
//        System.out.println();
//        System.out.println("Trees are same: " + result);

//        System.out.println(bst.getKSmallest(bst.getRoot(), 8));

        System.out.println(personBst.getAgesSum());
    }
}
