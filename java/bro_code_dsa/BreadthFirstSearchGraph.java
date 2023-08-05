import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearchGraph {

    ArrayList<Node> nodes;
    int[][] matrix;

    BreadthFirstSearchGraph(int size) {
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

    public void breadthFirstSearch(int src) {
//        where src is the index of the node, we would like to begin searching at and with BFS, we can utilise a queue
//        queue is an interface, which requires data structure
        Queue<Integer> queue = new LinkedList<>();
//        creating an array of booleans, to mark if a node has been visited or not
        boolean[] visited = new boolean[matrix.length];

//        we can use add or offer, and passing the index of the starting node
        queue.offer(src);
        visited[src] = true;

        while(queue.size() != 0) {
//            assigning the src equls to whatever is @ front of the queue
            src = queue.poll();
            System.out.println(nodes.get(src).data + " = visited");

            for(int i = 0; i < matrix[src].length; i++) {
//                checking its value is 1, and node we're trying to visit has not been visited
                if(matrix[src][i] == 1 && !visited[i]) {
//                    adding the index to the queue and that node is going to wait in line
                    queue.offer(i);
                    visited[i] = true;
                }
            }

        }
    }
}
