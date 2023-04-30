/*abstractCreate a class with name Solid in Solid class make three methods with same name volume
(method overloading). First method find the volume of cube, second method find the volume of
cuboid and third method find the volume of sphere. Now test the class Solid.*/

import java.util.*;

class Shape{

    void volume(int side){
        System.out.println("The vol of cube is: "+(side*side*side));
    }

    void volume(int l,int b,int h){
        System.out.println("The vol of cuboid is: "+l*b*h);
    }

    void volume(double radius){
        System.out.println("The vol of sphere is: "+(4*22*radius*radius*radius)/21);
    }
}

class shapeOverloading{

    public static void main(String args[]){

        Scanner sc = new Scanner(System.in);
        Shape s = new Shape();
        
        System.out.print("Enter the side of a cube: ");
        int side = sc.nextInt();
        s.volume(side);

        System.out.print("Enter the l,b & h of a cuboid: ");
        int l = sc.nextInt();
        int b = sc.nextInt();
        int h = sc.nextInt();
        s.volume(l, b, h);

        System.out.print("Enter the radius of a sphere: ");
        double r = sc.nextDouble();
        s.volume(r);
    }
}