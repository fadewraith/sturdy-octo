// ;linked hash set demo

import java.util.*;

class linkHashSetD{
    public static void main(String args[]){
        LinkedHashSet<String> lhs = new LinkedHashSet<String>();
        lhs.add("c");
        lhs.add("cpp");
        lhs.add("python");
        lhs.add("java");
        lhs.add("python");
        lhs.add("php");
        // System.out.println(lhs);
        Iterator itr = lhs.iterator();
        System.out.print("elements in the linked list: ");
        while(itr.hasNext()){
            System.out.print(itr.next()+" ");
        }

        System.out.println();
    }
}