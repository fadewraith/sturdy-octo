public class DepthFirstSearch {

    public static void main(String[] args) {
        /*
        * DFS = pick a route, keep going.
        * if we reach a dead end, or an already visited node, backtrack to a previous node with unvisited adjacent neighbors.
        * done at one "branch" at a time*/


        DepthFirstSearchGraph depthFirstSearchGraph = new DepthFirstSearchGraph(5);

        depthFirstSearchGraph.addNode(new Node('A'));
        depthFirstSearchGraph.addNode(new Node('B'));
        depthFirstSearchGraph.addNode(new Node('C'));
        depthFirstSearchGraph.addNode(new Node('D'));
        depthFirstSearchGraph.addNode(new Node('E'));

        depthFirstSearchGraph.addEdge(0, 1);
        depthFirstSearchGraph.addEdge(1, 2);
        depthFirstSearchGraph.addEdge(2, 3);
        depthFirstSearchGraph.addEdge(2, 4);
        depthFirstSearchGraph.addEdge(4, 0);
        depthFirstSearchGraph.addEdge(4, 2);

        depthFirstSearchGraph.print();
        System.out.println();

//        this is the directed graph
        depthFirstSearchGraph.depthFirstSearch(4);


    }
}
