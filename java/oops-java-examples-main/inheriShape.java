// create inheritance with shape and square

import java.util.*;

class Shape{

    int side;

    public void setValue(int s){
        side = s;
    }
}

class Square extends Shape{

    public void area(){

        System.out.println("Side of a square is: "+(side*side));

    }

}

class Cube extends Shape{

    public void volume(){

        System.out.println("The volume of a cube is: "+(side*side*side));

    }
}

class inheriShape{

    public static void main(String args[]){

        // Scanner sc = new Scanner(System.in);
        System.out.println("Enter the side: ");
        int side = new Scanner(System.in).nextInt();

        Square sq = new Square();
        sq.setValue(side);
        sq.area();

        Cube cu = new Cube();
        cu.setValue(side);
        cu.volume();
    }
}