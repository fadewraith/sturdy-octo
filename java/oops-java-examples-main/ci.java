// Develop a java program to compute compound interest.

import java.util.*;

class ci{
    public static void main(String args[]){

        Scanner sc = new Scanner(System.in);
        double ci,p,r;
        int t;

        System.out.print("Enter the principle: ");
        p = sc.nextDouble();

        System.out.print("Enter the rate: ");
        r = sc.nextDouble();

        System.out.print("Enter the time: ");
        t = sc.nextInt();

        ci = p*(Math.pow(((100+r)/100),t))-p;
        System.out.println("The C.I. is: "+ci);
        
    }
}