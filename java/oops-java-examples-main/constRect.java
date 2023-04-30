//Develop a class named Rectangle with final data members length and width. Make a
// parameterized Constructor to initialize data members. Now make two methods rectArea() and
// rectPeri() to calculate area and perimeter. Test the class Rectangle.

import java.util.*;

class Rectangle{

    final int l,b;

    Rectangle(int length,int breadth){

        l = length;
        b = breadth;

    }

    public void rectArea(){
        System.out.println("The area of rrectangle is: "+l*b);
    }

    public void rectPeri(){
        System.out.println("The perimeter of rectangle is: "+(2*(l+b)));
    }

}

class constRect{
    public static void main(String args[]){

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the l & b of a rectangle: ");
        int l = sc.nextInt();
        int b = sc.nextInt();

        Rectangle rect = new Rectangle(l,b);
        rect.rectArea();
        rect.rectPeri();
        
    }
}