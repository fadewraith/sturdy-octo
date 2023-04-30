// to find the sum of 2 numbers using static method

import java.util.*;

class sumUsingStatic {
    static int add(int x, int y){
        return x+y;
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int a,b,c;

        System.out.print("Enter two numbers: ");
        a=  sc.nextInt();
        b=  sc.nextInt();
        c = add(a,b);
        System.out.println(c);

    }
}
