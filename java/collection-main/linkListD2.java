// linked list demo 2

import java.util.*;

class linkListD2{
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        LinkedList<String> ll = new LinkedList<String>();
        for(int i=0;i<5;i++){
            String name = sc.nextLine();
            ll.add(name);
        }

        System.out.print("before sorting => ");
        System.out.println(ll);

        Collections.sort(ll);
        System.out.print("after sorting => ");
        System.out.println(ll);

        Collections.reverse(ll);
        System.out.print("reverse sorting => ");
        System.out.println(ll);
        

    }
}