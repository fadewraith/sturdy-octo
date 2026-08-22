package org.dsaOne.AVLTrees;

public interface Tree<T> {
    void insert(T data);
    void remove(T data);
    void traverse();
}
