// linked list demo 3

import java.util.*; 

class linkListD3{
    public static void main(String args[]){
        LinkedList<String> ll = new LinkedList<String>();
        ll.add("\'c'");
        ll.add("\'cpp'");
        ll.add("\'python'");
        ll.add("\'java'");
        ll.add("\'php'");
        // System.out.println(ll);
        ListIterator itr = ll.listIterator();
        System.out.print("traversing in forward direction: ");
        while(itr.hasNext()){
            System.out.print(itr.next()+" ");
        }
        System.out.println();

        System.out.print("traversing in backward direction: ");
        while(itr.hasPrevious()){
            System.out.print(itr.previous()+" ");
        }
        System.out.println();

    }
}