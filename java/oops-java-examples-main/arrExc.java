// 

import java.util.*;

class arrExc{
    public static void main(String args[]){

        Scanner sc = new Scanner(System.in);
        int stu[] = new int[5];

        try{

            System.out.print("Enter the roll no of students: ");
            for(int i=0;i<5;i++){

                stu[i] = sc.nextInt();

            }

            System.out.print("the roll no of students are: ");
            for(int i=0;i<=stu.length;i++){

                System.out.print(stu[i]+" ");
                
            }

        }catch(Exception e){

            System.out.println("\nException caught");

        }
    }
}