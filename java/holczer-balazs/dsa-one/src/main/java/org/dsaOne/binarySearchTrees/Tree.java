package org.dsaOne.binarySearchTrees;

public interface Tree<T> {

    int getAgesSum();

    Node<T> getKSmallest(Node<T> node, int k);

    Node<T> getRoot();

    void insert(T data);
    void remove(T data);
    // in order traversal yields the natural ordering
    void traversal();

    T getMin();
    T getMax();
}
