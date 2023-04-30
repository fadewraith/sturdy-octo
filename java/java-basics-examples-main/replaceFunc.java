// to find and replace using the in built function

import java.util.*;

class replaceFunc {
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        String str,fw,rw;

        System.out.print("Enter the string: ");
        str = sc.nextLine();

        System.out.print("Find what: ");
        fw = sc.nextLine();

        System.out.print("Replace with: ");
        rw = sc.nextLine();

        System.out.println("New string: "+str.replace(fw,rw));;
        
    }
}
