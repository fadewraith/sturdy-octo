// hash set demo. stores unique element & does not maintain insertion order

import java.util.*;

class hashSetD{
    public static void main(String args[]){

        HashSet<Integer> hs = new HashSet<Integer>();

        hs.add(10);
        hs.add(20);
        hs.add(30);
        hs.add(40);
        hs.add(20);
        hs.add(50);

        System.out.print("hash set demo: ");
        for(int n: hs){
            System.out.print(n+"\t");
        }
        
        System.out.println();
    }
}