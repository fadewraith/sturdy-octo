// wap to find the area of rectangle using non-sstatic method

import java.util.*;

class nonStatic {

    int areaRect(int l, int b){
        return l*b;
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int l,b;
        System.out.print("Enter the l & b to calc area of rectangle: ");
        l = sc.nextInt();
        b = sc.nextInt();
        nonStatic rect = new nonStatic();
        int z = rect.areaRect(l,b);
        System.out.println("Area of rectangle is: "+z);

    }
}
