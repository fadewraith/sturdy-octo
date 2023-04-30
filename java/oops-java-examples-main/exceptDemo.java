// to find the division of 2 number

import java.util.*;

class exceptDemo{
    public static void main(String args[]){
        int x,y,z;
        Scanner sc = new Scanner(System.in);
        try{
            System.out.print("Enter 2 numbers: ");
            x = sc.nextInt();
            y = sc.nextInt();
            z = x/y;
            System.out.println("Result: "+z);
        }catch(InputMismatchException ex1){
            System.out.println("Enter numbers only");
        }catch(ArithmeticException ex1){
            System.out.println("Dont divide it by zero");
        }finally{
            System.out.println("This is finally block");
        }
        
    }
}