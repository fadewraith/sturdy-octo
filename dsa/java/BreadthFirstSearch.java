public class BreadthFirstSearch {

    public static void main(String[] args) {

        /*
        * BFS = traverse a graph level by level
        * utilizes a queue
        * better if destination is on average close to start
        * siblings are visited before children
        *
        * ============
        * DFS= traverses a graph branch by branch
        * utilizes a stack
        *
        * better if destination is on average far from the start
        * children are visited before siblings
        * more popular for games/puzzles
        * */


        BreadthFirstSearchGraph breadthFirstSearchGraph = new BreadthFirstSearchGraph(5);

        breadthFirstSearchGraph.addNode(new Node('A'));
        breadthFirstSearchGraph.addNode(new Node('B'));
        breadthFirstSearchGraph.addNode(new Node('C'));
        breadthFirstSearchGraph.addNode(new Node('D'));
        breadthFirstSearchGraph.addNode(new Node('E'));

        breadthFirstSearchGraph.addEdge(0, 1);
        breadthFirstSearchGraph.addEdge(1, 2);
        breadthFirstSearchGraph.addEdge(2, 3);
        breadthFirstSearchGraph.addEdge(2, 4);
        breadthFirstSearchGraph.addEdge(4, 0);
        breadthFirstSearchGraph.addEdge(4, 2);

        breadthFirstSearchGraph.print();
//        System.out.println("BFS");

        breadthFirstSearchGraph.breadthFirstSearch(4);

    }
}
