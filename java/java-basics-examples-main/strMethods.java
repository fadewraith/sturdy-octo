// to take input from user and convert it into uppper,lower and length.

import java.util.*;

class strMethods {
    public static void main(String [] args){

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the name: ");
        String name = sc.nextLine();

        System.out.println(name.toUpperCase());
        System.out.println(name.toLowerCase());
        System.out.println(name.length());


    }

}
