package org.dsaOne.AVLTrees;

public class AVLTrees<T extends Comparable<T>> implements Tree<T> {

    private Node<T> root;

    @Override
    public void insert(T data) {
        // for first node of BST (parent is null)
        if(root == null) {
            root = new Node<>(data, null);
        } else {
            // there are already items in BST
            insert(data, root);
        }
    }

    private void insert(T data, Node<T> node) {
        // data is smaller than the val in curr node, go to left subtree
        if(node.getData().compareTo(data) > 0) {
            // valid not null left child
            if(node.getLeftChild() != null) {
                insert(data, node.getLeftChild());
            } else {
                // left child is null
                node.setLeftChild(new Node<>(data, node));
            }
        } else {
            // case when data is greater than the value in node, go to right subtree
            if(node.getRightChild() != null) {
                insert(data, node.getRightChild());
            } else {
                node.setRightChild(new Node<>(data, node));
            }
        }
        updateHeight(node);
        // settle the violation
        settleViolations(node);
    }


    /**
     * Case 1 - Removing a leaf node
     * Case 2 - Removing a single child
     * Case 3 - Removing a node having children
     * Successor - smallest item in the right subtree
     * Predecessor - greatest item in the left subtree
     *
     * So the real reason isn't that the successor is "better." It's that the successor and predecessor are the only values guaranteed to replace the deleted node without violating the BST ordering, and using one consistently keeps the implementation simple.
     * */
    @Override
    public void remove(T data) {
        if(root != null) {
            remove(data, root);
        }
    }

    private void remove(T data, Node<T> node) {
        System.out.println();
        if(node == null) {
            return;
        }

        // search item
        if(data.compareTo(node.getData()) < 0) {
            // go to left
            remove(data, node.getLeftChild());
        } else if(data.compareTo(node.getData()) > 0) {
            // go to right
            remove(data, node.getRightChild());
        } else {
            // found the data to remove
            // if node is leaf node (no left & right children)
//            CASE 1
            if(node.getLeftChild() == null &&  node.getRightChild() == null) {
                System.out.println("Removing a leaf node ...");
                // whether the node is a left child or right child
                Node<T> parent = node.getParentNode();

                // root node doesn't have a parent, and this is the left child
                if(parent != null && parent.getLeftChild() == node) {
                    parent.setLeftChild(null);
                } else if(parent != null && parent.getRightChild() == node) {
                    // node is right child
                    parent.setRightChild(null);
                }
                if(parent == null) {
                    // maybe root node needs to be removed
                    root = null;
                }

                // for GC
                node = null;
                updateHeight(parent);
                settleViolations(parent);
            } else if(node.getLeftChild() == null && node.getRightChild() != null) {
                System.out.println("Removing a node with a single right child ...");
                // CASE 2 - a single right child
                Node<T> parent = node.getParentNode();
                // node is a left child
                if(parent != null && parent.getLeftChild() == node) {
                    parent.setLeftChild(node.getRightChild());
                } else if(parent != null && parent.getRightChild() == node) {
                    // node is a right child
                    parent.setRightChild(node.getRightChild());
                }

                if(parent == null) {
                    root = node.getRightChild();
                }

                // have to update the right child's parent
                node.getRightChild().setParentNode(parent);
                node = null;
                updateHeight(parent);
                settleViolations(parent);
            } else if(node.getLeftChild() != null && node.getRightChild() == null) {
                System.out.println("Removing a node with a single left child ...");
                // CASE 2 - a single left child
                Node<T> parent = node.getParentNode();
                // node is a left child
                if(parent != null && parent.getLeftChild() == node) {
                    parent.setLeftChild(node.getLeftChild());
                } else if(parent != null && parent.getRightChild() == node) {
                    // node is a right child
                    parent.setRightChild(node.getLeftChild());
                }

                if(parent == null) {
                    root = node.getLeftChild();
                }

                // have to update the right child's parent
                node.getLeftChild().setParentNode(parent);
                node = null;
                updateHeight(parent);
                settleViolations(parent);
            } else {
                System.out.println("Removing a node with 2 children ...");

                // find the predecessor (max item in the left subtree)
                Node<T> predecessor = getPredecessor(node.getLeftChild());

                // swap just the values !!!
                T temp = predecessor.getData();
                predecessor.setData(node.getData());
                node.setData(temp);

                // we have to call the delete method recursively on the predecessor
                remove(data, predecessor);
            }
        }
    }

    private Node<T> getPredecessor(Node<T> node) {

        if(node.getRightChild() != null)
            return getPredecessor(node.getRightChild());

        return node;
    }

    @Override
    public void traverse() {
        // in order traversal
        if(root == null) {
            return;
        }

        inOrderTraversal(root);
    }

    // O(n)
    // Visit the left subtree then root node and finally the right subtree
    // natural sorted order
    private void inOrderTraversal(Node<T> node) {
        if(node.getLeftChild() != null) {
            inOrderTraversal(node.getLeftChild());
        }
        System.out.print(node.getData() + " <-> ");
        if(node.getRightChild() != null) {
            inOrderTraversal(node.getRightChild());
        }
    }

    // update the height of a given node
    private void updateHeight(Node<T> node) {
        node.setHeight(Math.max(height(node.getLeftChild()), height(node.getRightChild())) + 1);
    }

    // returns the height parameter for a given node
    private int height(Node<T> node) {
        if(node == null) {
            return -1;
        }

        return node.getHeight();
    }

    // balance factor to decide the left heavy or right heavy cases
    private int getBalance(Node<T> node) {
        if(node == null) {
            return 0;
        }
        return height(node.getLeftChild()) - height(node.getRightChild());
    }

    private void rightRotation(Node<T> node) {
        System.out.println("Rotating right on node " + node);

        // this is the new root node after rotation
        Node<T> tempLeftChild = node.getLeftChild();
        Node<T> grandChild = tempLeftChild.getRightChild();

        // make the rotation - the new root node will be the tempLeftChild
        tempLeftChild.setRightChild(node);
        node.setLeftChild(grandChild);

        if(grandChild != null) {
            grandChild.setParentNode(node);
        }

        // we have to handle the parents for the node
        Node<T> tempParent = node.getParentNode();
        node.setParentNode(tempLeftChild);
        tempLeftChild.setParentNode(tempParent);

        // we have to handle the parent
        if(tempLeftChild.getParentNode() != null && tempLeftChild.getParentNode().getLeftChild() == node) {
            tempLeftChild.getParentNode().setLeftChild(tempLeftChild);
        }

        if(tempLeftChild.getParentNode() != null && tempLeftChild.getParentNode().getRightChild() == node) {
            tempLeftChild.getParentNode().setRightChild(tempLeftChild);
        }

        // no parent after rotation because it has become the root node
        if(node == root) {
            root = tempLeftChild;
        }

        // after rotations ht params can e changed
        updateHeight(node);
        updateHeight(tempLeftChild);
    }

    private void leftRotation(Node<T> node) {
        System.out.println("Rotating left on node " + node);

        // this is the new root node after rotation
        Node<T> tempRightChild = node.getRightChild();
        Node<T> grandChild = tempRightChild.getLeftChild();

        // make the rotation - the new root node will be the tempLeftChild
        tempRightChild.setLeftChild(node);
        node.setRightChild(grandChild);

        if(grandChild != null) {
            grandChild.setParentNode(node);
        }

        // we have to handle the parents for the node
        Node<T> tempParent = node.getParentNode();
        node.setParentNode(tempRightChild);
        tempRightChild.setParentNode(tempParent);

        // we have to handle the parent
        if(tempRightChild.getParentNode() != null && tempRightChild.getParentNode().getLeftChild() == node) {
            tempRightChild.getParentNode().setLeftChild(tempRightChild);
        }

        if(tempRightChild.getParentNode() != null && tempRightChild.getParentNode().getRightChild() == node) {
            tempRightChild.getParentNode().setRightChild(tempRightChild);
        }

        // no parent after rotation because it has become the root node
        if(node == root) {
            root = tempRightChild;
        }

        // after rotations ht params can e changed
        updateHeight(node);
        updateHeight(tempRightChild);
    }

    private void settleViolations(Node<T> node) {
        // we have to check upto the root node
        while(node != null) {
            updateHeight(node);
            settleViolationsHelper(node);
            node = node.getParentNode();
        }
    }

    private void settleViolationsHelper(Node<T> node) {
        int balance = getBalance(node);

        if(balance > 1) {
            if(getBalance(node.getLeftChild()) < 0) {
                leftRotation(node.getLeftChild());
            }
            rightRotation(node);
        }

        if(balance < -1) {
            if(getBalance(node.getRightChild()) > 0) {
                rightRotation(node.getRightChild());
            }
            leftRotation(node);
        }
    }
}
