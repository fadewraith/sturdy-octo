
public class AdjacencyMatrix {
    public static void main(String[] args) {
        /*
        2 types of graphs - directed and undirected
        2 popular ways to represent a graph -
        adjacency matrix - if there is edge, then its 1, otherwise 0
         and adjacency list - is an array of linked list


        * Adjacency Matrix = an array to store 1's/0's to represent edges
        *                    # of rows = # of unique nodes
        *                    # of columns = # of unique nodes
        *  runtime complexity: to check an edge: O(1)
        *   space complexity: O(v^2)
        * */

        AdjacencyMatrixGraph adjacencyMatrixGraph = new AdjacencyMatrixGraph(5);

        adjacencyMatrixGraph.addNode(new Node('A'));
        adjacencyMatrixGraph.addNode(new Node('B'));
        adjacencyMatrixGraph.addNode(new Node('C'));
        adjacencyMatrixGraph.addNode(new Node('D'));
        adjacencyMatrixGraph.addNode(new Node('E'));

        adjacencyMatrixGraph.addEdge(0, 1);
        adjacencyMatrixGraph.addEdge(1, 2);
        adjacencyMatrixGraph.addEdge(2, 3);
        adjacencyMatrixGraph.addEdge(2, 4);
        adjacencyMatrixGraph.addEdge(4, 0);
        adjacencyMatrixGraph.addEdge(4, 2);

        adjacencyMatrixGraph.print();

        System.out.println(adjacencyMatrixGraph.checkEdge(3, 2));


    }
}
