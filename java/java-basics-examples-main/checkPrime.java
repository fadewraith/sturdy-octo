// to check prime or not
// anonymous object creation - num = new Scanner(System.in).nextInt();
// above is used only if it has to be used only once

import java.util.*;

class checkPrime{
    public static void main(String [] args){

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number to check for a prime: ");

        int num = sc.nextInt();

        int flag = 1;

        for(int i=2;i<num;i++){

            if(num%i==0){

                flag = 0;
                break;

            }
        }

        if(flag==1)
            System.out.println(num + " is prime");
        else
            System.out.println(num + " is not prime");

    }
    
}