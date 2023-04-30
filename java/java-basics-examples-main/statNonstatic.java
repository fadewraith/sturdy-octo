/*
Write a java program to find volume and surface area of cuboid using user defined methods.
Make volume() method as static method and surfaceArea() method as non-static method.
 */

import java.util.*;

class statNonstatic {

    static int volume(int l,int b, int h){
        return l*b*h;
    }

    int tsa(int l,int b,int h){
        return 2*((l*b)+(b*h)+(h*l));
    }

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        int l,b,h;

        System.out.print("Enter the l,b & h of a cuboid: ");
        l = sc.nextInt();
        b = sc.nextInt();
        h = sc.nextInt();

        int vol = volume(l,b,h);
        System.out.println("Volume of a cuboid is: " + vol);

        statNonstatic tot_surf_ar = new statNonstatic();
        int tsa = tot_surf_ar.tsa(l, b, h);
        System.out.println("Total Surface Area of cuboid is: "+tsa);
    }

}
