// wap to test mypack TempConv class

import java.util.*;
import mypack.TempConv;

class Test{
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        TempConv tc = new TempConv();
        double c,f;
        
        System.out.print("Enter the choice: \n1-c2f\n2-f2c ");
        int ch = sc.nextInt();

        switch(ch){

            case 1:
                System.out.print("Enter temp in c: ");
                c = sc.nextDouble();
                f = tc.c2f(c);
                System.out.println("The c2f is: "+f);
                break;

            case 2:
                System.out.print("Enter emp in f: ");
                f = sc.nextDouble();
                c = tc.f2c(f);
                System.out.println("The c2f is: "+c);
                break;

            default:
                System.out.println("enter either 1 or 2");
            
        }
    }
}