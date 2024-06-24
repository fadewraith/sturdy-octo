import java.util.ArrayList;
import java.util.LinkedList;

public class AdjacencyListGraph {

    ArrayList<LinkedList<Node>> arrayList;

    AdjacencyListGraph() {
        arrayList = new ArrayList<>();
    }

    public void addNode(Node node) {
        LinkedList<Node> currentList = new LinkedList<>();
//        creating the link list
        currentList.add(node);
//        System.out.println(currentList);
//        createing the arraylist of the linkelist
        arrayList.add(currentList);
//        System.out.println(arrayList);

    }

    public void addEdge(int src, int dst) {
//        getting the source
        LinkedList<Node> currentList = arrayList.get(src);
//        System.out.println(currentList);
//        getting the destination
        Node dstNode = arrayList.get(dst).get(0);
//        System.out.println(dstNode);
//        adding the destination to the tail of the link list
        currentList.add(dstNode);

    }

    public boolean checkEdge(int src, int dst) {

        LinkedList<Node> currentList = arrayList.get(src);
        Node dstNode = arrayList.get(dst).get(0);

        for(Node node: currentList) {
            if(node == dstNode) { // comparing if the addresses are same
                return true;

            }
        }
        return false;

    }

    public void print() {

        for(LinkedList<Node> currentList: arrayList) {
//            for(Node node: currentList) {
            for(int i = 0; i < currentList.size(); i++) {
                Node node = currentList.get(i);
                System.out.print(node.data);
                if(i < currentList.size() - 1)
                    System.out.print(" -> ");
            }
            System.out.println();
        }

    }
}
