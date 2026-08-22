package org.dsaOne.AVLTrees;

public class MainAVL {


    public static void main(String[] args) {
        Tree<Integer> avl = new AVLTrees<>();
        avl.insert(5);
        avl.insert(6);
        avl.insert(4);
        avl.insert(3);
        avl.insert(2);
        avl.insert(1);

        avl.remove(1);
        avl.traverse();
    }
}
