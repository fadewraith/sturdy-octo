// Create a circle class as given class disgram

import java.util.*;

class Circle{
    double radius;
    String color;

    Circle(){
        this.radius = 1.0;
        this.color = "Red";
    }

    Circle(double radius){
        this.radius = radius;
    }

    Circle(double radius,String color){
        this.radius = radius;
        this.color = color;
    }

    void getRadius(){
        System.out.println("Radius of a circle is: "+radius);
    }

    void getColor(){
        System.out.println("The color of a circle is: "+color);
    }

    void getArea(){
        double area = (22*radius*radius)/7;
        System.out.println("The area of circle is: "+area);
    }
}

class classCircle{
    public static void main(String args[]){

        System.out.println("-------------This is the Constructer 1------------------");
        Circle c = new Circle();
        c.getRadius();
        c.getColor();
        c.getArea();

        System.out.println("-------------This is the Constructer 2------------------");
        Circle c1 = new Circle(28);
        c1.getRadius();
        c1.getColor();
        c1.getArea();        

        System.out.println("-------------This is the Constructer 3------------------");
        Circle c2 = new Circle(21,"Green");
        c2.getRadius();
        c2.getColor();
        c2.getArea();
        
    }
}