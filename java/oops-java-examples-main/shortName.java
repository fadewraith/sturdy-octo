//Develop a java program to take username as input and display its short name. E.g. user has
// entered Ajay Pratap Singh then output should be A.P.Singh.

import java.util.*;

class shortName{

    public static void main(String args[]){

        System.out.print("Enter the name: ");
        String name = new Scanner(System.in).nextLine();

        String str[] = name.split(" ");

        for(int i=0;i<str.length-1;i++){

            System.out.print(str[i].charAt(0)+". ");

        }

        System.out.println(str[str.length-1]);

    }
}