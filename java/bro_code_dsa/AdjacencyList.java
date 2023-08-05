public class AdjacencyList {

    /*
    * Adjacency List= an array/arraylist of linkedlists.
    * each linkedlist has a unique node at the head.
    * all adjacent neighbors to that node are added to that node's linkedlist
    * t.c = O(V), s.c. = O(V + E)
    * */

    public static void main(String[] args) {
        AdjacencyListGraph adjacencyListGraph = new AdjacencyListGraph();

        adjacencyListGraph.addNode(new Node('A'));
        adjacencyListGraph.addNode(new Node('B'));
        adjacencyListGraph.addNode(new Node('C'));
        adjacencyListGraph.addNode(new Node('D'));
        adjacencyListGraph.addNode(new Node('E'));

        adjacencyListGraph.addEdge(0, 1);
        adjacencyListGraph.addEdge(1, 2);
        adjacencyListGraph.addEdge(1, 4);
        adjacencyListGraph.addEdge(2, 3);
        adjacencyListGraph.addEdge(2, 4);
        adjacencyListGraph.addEdge(4, 0);
        adjacencyListGraph.addEdge(4, 2);

        adjacencyListGraph.print();

    }
}
