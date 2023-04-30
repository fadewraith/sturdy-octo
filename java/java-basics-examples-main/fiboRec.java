// Write a java program to generate Fibonacci series up to n terms using Recursion.

import java.util.*;

class fiboRec {
    
    static int fibo(int num){
        if(num==0)
            return 0;
        else if(num==1)
            return 1;
        else 
            return fibo(num-1) + fibo(num-2);
    }

    public static void main(String[] args){

        System.out.print("Enter the number to generate fibonacci series: ");
        int num = new Scanner(System.in).nextInt();
        
        System.out.println("The fibonacci series is: "+fibo(num));

    }
}
