// to print fabonnaci by taking input

import java.util.*;

class fiboInput {
    public static void main(String[] args){

        // Scanner sc = new Scanner(System.in);
        // int num = sc.nextInt();
        System.out.print("Enter the number to calc the Fibonacci series: ");

        int num = new Scanner(System.in).nextInt();

        int n1 = 0,n2 = 1,n3;

        System.out.print(n1+" "+n2+" ");

        for(int i=0;i<num;i++){

            n3 = n2 + n1;

            System.out.print(n3 + " ");

            n1 = n2;

            n2 = n3;

        }

        System.out.println();

    }

}
