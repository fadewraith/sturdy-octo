package org.dsaOne.binarySearchTrees;

public class CompareBinaryTree<T extends Comparable<T>> {

    public boolean isTreeSame(Node<T> n1, Node<T> n2) {

        // case either of the node is null or both the nodes are null
        if((n1 == null && n2 == null) || (n1 == null && n2 != null) || (n1 != null && n2 == null)) {
            return n1 == n2;
        }

        // check the values
        if(n1.getData().compareTo(n2.getData()) != 0) {
            return false;
        }

        return isTreeSame(n1.getLeftChild(), n2.getLeftChild()) && isTreeSame(n1.getRightChild(), n2.getRightChild());
    }
}
