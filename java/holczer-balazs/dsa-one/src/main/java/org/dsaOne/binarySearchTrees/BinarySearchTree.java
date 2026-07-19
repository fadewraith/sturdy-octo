package org.dsaOne.binarySearchTrees;

public class BinarySearchTree<T extends Comparable<T>> implements Tree<T> {

    private Node<T> root;

    @Override
    public Node<T> getRoot() {
        return root;
    }

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
            } else if(node.getLeftChild() != null && node.getRightChild() == null) {
                System.out.println("Removing a node with a single left child ...");
                // CASE 2 - a single right child
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
    public void traversal() {
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

    @Override
    public T getMin() {
        if(root == null) {
            return null;
        }

        // leftmost item in tree
        return getMin(root);
    }

    private T getMin(Node<T> node) {
        if(node.getLeftChild() != null) {
            return getMin(node.getLeftChild());
        }
        return node.getData();
    }

    @Override
    public T getMax() {

        if(root == null) {
            return null;
        }

        // rightmost item in tree
        return getMax(root);
    }

    private T getMax(Node<T> node) {
        if(node.getRightChild() != null) {
            return getMax(node.getRightChild());
        }
        return node.getData();
    }

    /**
     *
     *        12
     *       /  \
     *      4    20
     *     / \
     *    1   5
     * find the kth smallest item (largest) item in a bst + in place algo
     *
     * The left subtree of a given node T contains items that are smaller than T
     * -> if k is smaller than the number of nodes in the left subtree; then the kth smallest item must be in left subtree
     * -> if k is greater than the number of nodes in the left subtree; check the right subtree
     *
     * ex - 2nd smallest item
     *  - check the left subtree
     *  - there are 3 nodes so num of nodes is greater than the k value - value is in left subtree
     *
     *  ex -  4th smallest item
     *  - check the left subtree
     *  - (no of nodes in left subtree + 1(because of root node)) is k value then we are done
     *
     *  ex - 5th smallest item
     *  - check the left subtree
     *  - 3 nodes in left subtree + 1 root node so numOfNodes < k
     *  - check the right subtree BUT have to modify the k value
     *  We reduce the problem to finding (k - numOfNodes(leftSubtree) + root node) smallest item in the right subtree
     *  so 20 will be the 5th smallest item, (5 - (3) + 1(root node)) -> thats why we have to decrease in this case
     *
     *  Algorithm -
     *  int n = number of nodes in the left subtree + 1(root ndoe)
     *  if(n == k) return node; (which will be the root node)
     *  if(n > k) return kthSmallest(leftSubtree, k)
     *  if(n < k) return kthSmallest(rightSubtree, k - n)
     * */
    @Override
    public Node<T> getKSmallest(Node<T> node, int k) {
        // number of nodes in left subtree
        // +1 coz we count the root node of the subtree as well
        int n = treeSize(node.getLeftChild()) + 1;

        // this is when kth smallest item is found
        if(n == k) {
            return node;
        }

        // if the number of nodes in the left subtree > kth smallest item
        // means the kth smallest item is in the left subtree
        if(n > k) {
            return getKSmallest(node.getLeftChild(), k);
        }

        // if num of nodes in the left subtree is smaller than the kth
        // smallest item then we can discard the left subtree and consider the right subtree
        // now we are looking at the k-n th smallest item
        if(n < k) {
            return getKSmallest(node.getRightChild(), k - n);
        }

        return null;
    }

    private int treeSize(Node<T> node) {
        if(node == null) {
            return 0;
        }

        // recursively sum up the size of left subtree + size of right subtree
        // size of tree = size of left subtree + size of right subtree + 1 (because of the root)
        return (treeSize(node.getLeftChild()) + treeSize(node.getRightChild()) + 1);
    }

    @Override
    public int getAgesSum() {
        return getAges(this.root);
    }

    private int getAges(Node<T> node) {
        System.out.println("Considering node: " + node);

        // we have to reinitialize the variables (sum is parents node val so the sum of the subtrees so far)
        int sum = 0;
        int leftSum = 0;
        int rightSum = 0;

        // null nodes have sum value 0
        if(node == null) return 0;

        // post order traversal coz needs to be calculated both left & right val
        // to be able to calc the parents value (sum of children age)
        // check the left subtree
        leftSum = getAges(node.getLeftChild());
        // check the right subtree
        rightSum = getAges(node.getRightChild());

        System.out.println("Considering node: " + node + " total ages so far " + (((Person)node.getData()).getAge() + leftSum + rightSum));
        sum = ((Person)node.getData()).getAge() + leftSum + rightSum;

        return sum;
    }
}
