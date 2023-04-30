// Develop a java program to find area and perimeter of rectangle using user-defined static methods.

import java.util.*;

class Rectangle{

    // int length, breadth;

    public void areaPeri(int l,int b){

        // length = l;
        // breadth = b;

        System.out.println("Area is: "+(l*b));
        System.out.println("Perimeter is: "+2*(l+b));
        
    }
}

class classRect{
    public static void main(String args[]){

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the l & b of a rect: ");
        int l = sc.nextInt();
        int b = sc.nextInt();

        Rectangle rec = new Rectangle();
        rec.areaPeri(l, b);

    }

}