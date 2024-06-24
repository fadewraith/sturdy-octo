public class BinarySearchTree {

    BstNode root;

    public void insert(BstNode node) {
//reason, we're using the helper methods, is that we will be using recursion
        root = insertHelper(root, node);
    }

    private BstNode insertHelper(BstNode root, BstNode node) {
        int data = node.data;
//        checking if root node is assigned or not
        if(root == null) {
            root = node;
            return root;
        } else if(data < root.data) {
            root.left = insertHelper(root.left, node);
        } else {
            root.right = insertHelper(root.right, node);
        }


        return root; // returning the current root node
    }

    public void display() {
        displayHelper(root);

    }

    private void displayHelper(BstNode root) {
        if(root != null) {
//            we can use inordertraversal
//            before using recursion, the first piece of data i.e. displayed is the least value and these values will be displayed in increasing order
            displayHelper(root.left); // initially 1 = least value
            System.out.println(root.data); // root node of that subtree is 2
            displayHelper(root.right); // right child will be 3
//            to display in reverse order
//            displayHelper(root.right); // right child will be 3
//            System.out.println(root.data); // root node of that subtree is 2
//            displayHelper(root.left); // initially 1 = least value
//            post order
//            left -> right -> root
//            displayHelper(root.left);
//            displayHelper(root.right);
//            System.out.println(root.data);

//            pre order
//            root -> left -> right
//            System.out.println(root.data);
//            displayHelper(root.left);
//            displayHelper(root.right);
        }
    }

    public boolean search(int data) {
        return searchHelper(root, data);
    }

    private boolean searchHelper(BstNode root, int data) {

        if(root == null) {
            return false;
        } else if(root.data == data) {
            return true;
        } else if(root.data > data) {
            return searchHelper(root.left, data);
        } else {
            return searchHelper(root.right, data);
        }

    }

    public void remove(int data) {
//        checking if the data is even exists or not
        if(search(data)) {
            removeHelper(root, data);
        } else {
            System.out.println(data + " could not be found");
        }

    }

    public BstNode removeHelper(BstNode root, int data) {
//        checking if root node is null
        if(root == null) {
            return root;
        } else if(data < root.data) {
            root.left = removeHelper(root.left, data);
        } else if(data > root.data) {
            root.right = removeHelper(root.right, data);
        } else { // node found
//                        checking if its a leaf node
            if(root.left == null && root.right == null) {
                root = null;
            } else if(root.right != null) { // find successor to replace this node
                root.data = successor(root);
                root.right = removeHelper(root.right, root.data);
//                when we delete a node, that will create a gap and if there is a child, we dont want that child be lost, so we will link a child to that spot where we deleted a node
            } else { // find a predecessor to replace this node
                root.data = predecessor(root);
                root.left = removeHelper(root.left, root.data);
            }

        }
        return root;
    }

    private int successor(BstNode root) {
//        to find the least value below the right child of this root node
        root = root.right;
        while(root.left != null) {
            root = root.left;
        }
        return root.data;
    }

    private int predecessor(BstNode root) {
        root = root.left;
        while(root.right != null) {
            root = root.right;
        }
        return root.data;
    }


}
