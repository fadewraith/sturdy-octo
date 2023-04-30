// linked list demo 1

import java.util.*;


class iteratorLink{
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        LinkedList<String> ll = new LinkedList<String>();
        for(int i=0;i<5;i++){
            String name = sc.nextLine();
            ll.add(name);
        }

        System.out.print("before sorting => ");
        System.out.println(ll);

        // for(String s:ll){
        //     System.out.println(s+" ");
        // }
        // System.out.println();
        Iterator itr = ll.iterator();

        while(itr.hasNext()){
            System.out.println(itr.next());            
        }
        

    }
}