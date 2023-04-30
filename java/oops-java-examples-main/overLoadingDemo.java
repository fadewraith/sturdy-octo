// overloading method java

import java.util.*;

class Figure{

    public int area(int s){
        return s*s;
    }

    public int area(int l,int b){
        return l*b;
    }

    public double area(double r){
        return (3.14*r*r);
    }
}

class overLoadingDemo{

    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);

        int s,l,b,a1,a2;
        double r,a3;
        Figure fig = new Figure();

        System.out.print("Enter the side of a square: ");
        s = sc.nextInt();

        System.out.print("Enter the length & breadth of rectangle: ");
        l = sc.nextInt();
        b = sc.nextInt();

        System.out.print("Enter the radius of a circle: ");
        r = sc.nextDouble();

        a1 = fig.area(s);
        a2 = fig.area(l,b);
        a3 = fig.area(r);

        System.out.println("Area of sq is: "+a1);
        System.out.println("Area of rect is: "+a2);
        System.out.println("Area of circle is: "+a3);

    }
}