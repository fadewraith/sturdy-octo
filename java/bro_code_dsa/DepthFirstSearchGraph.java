import java.util.ArrayList;

public class DepthFirstSearchGraph {

    ArrayList<Node> nodes;
    int[][] matrix;

    DepthFirstSearchGraph(int size) {
        nodes = new ArrayList<>();
        matrix = new int[size][size];
    }

    public void addNode(Node node) {
        // nodes.add(node);
        nodes.add(node);
    }

    public void addEdge(int src, int dst) {

        matrix[src][dst] = 1;

    }

    public boolean checkEdge(int src, int dst) {


        //        if(matrix[src][dst] == 1) {
//            return true;
//        } else {
//            return false;
//        }

        return matrix[src][dst] == 1;

    }

    public void print() {

        System.out.print("  ");
        for (Node node: nodes) {
            System.out.print(node.data + " ");
        }
        System.out.println();

        for(int i = 0; i < matrix.length; i++) {
            System.out.print(nodes.get(i).data + " ");
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

    }

    public void depthFirstSearch(int src) {
//        creating the array of booleans and size will be equal to the length of the matrices
        boolean[] visited = new boolean[matrix.length];
//        helper function
        dfsHelper(src, visited);

    }

    private void dfsHelper(int src, boolean[] visited) {
//        either we can use stack or we can use callstack, if we are using recursion. here recursion is used -
        if(visited[src]) {
            return;
        } else {
            visited[src] = true;
            System.out.println(nodes.get(src).data + " = visited");
        }

//        if we're using adjacency matrix, we need to iterate over the row
//        matrix[src].length = length of the row
        for(int i = 0; i < matrix[src].length; i++) {
//            we're checking to see, if any of the row alements are 1, that means that's an adjacent neighbour, we can travel to, using an if statement
//            src is the row we're working with, is the column
            if(matrix[src][i] == 1) {
                dfsHelper(i, visited );
            }
        }
//        if we have successfully searched through an entire row
        return;

    }


}
