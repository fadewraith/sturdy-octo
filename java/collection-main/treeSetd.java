// tree set demo - arranges in ascending order

import java.util.*;

class treeSetd{
    public static void main(String args[]){
        TreeSet<String> ts = new TreeSet<String>();
        ts.add("hello");
        ts.add("world");
        ts.add("zyappa");
        ts.add("javascript");
        ts.add("ruby");
        ts.add("perl");
        // System.out.println(ts);
        System.out.print("arranged list: ");
        for(String s: ts){
            System.out.print(s+" ");
        }
        System.out.println();
    }
}