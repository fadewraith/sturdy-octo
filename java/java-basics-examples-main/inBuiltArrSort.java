// to store 5 names in an array, now sort the name in alphabetical order

import java.util.*;

class inBuiltArrSort{
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        String[] names = new String[5];

        System.out.println("Enter the names of students: ");

        for(int i=0;i<names.length;i++){

            names[i] = sc.nextLine();

        }

        Arrays.sort(names);

        System.out.println("Displaying the names in sorted form: ");
        //for each loop
        for(String n:names){

            System.out.print(n+" ");
            
        }

        // for(int i=0;i<5;i++){

        //     System.out.print(names[i]+" ");

        // }

        System.out.println();

    }

}