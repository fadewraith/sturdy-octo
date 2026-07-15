package educativetutorials.codingpatterns.commonutilities;

public class TreeNode {

    public int data;
    TreeNode left;
    TreeNode right;
    public TreeNode parent;

    TreeNode(int value) {
        this.data = value;
        this.left = null;
        this.right = null;
        this.parent = null;
    }
}
