// wap to store 5 names in Array list by taking input from user. then display names in ascending & descedning order

import java.util.*;

class ArrayListD2{
    public static void main(String args[]){
        ArrayList<String> al = new ArrayList<String>();
        Scanner sc = new Scanner(System.in);
        int i;
        String name;

        System.out.print("Enter 5 names: ");
        for(i=0;i<5;i++){
            name = sc.nextLine();
            al.add(i,name);
        }
        Collections.sort(al);
        // System.out.println(al);
        for(String n:al){
            System.out.println(n);;
        }

        Collections.reverse(al);
        System.out.println(al);
    }
}