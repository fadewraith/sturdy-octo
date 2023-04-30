//wap to convert currenct to dollars and vice versa

import java.util.*;

class currConv{

    public static void main(String [] args){

        int ch;
        float num;
        System.out.println("Enter the choice for converting the currency: ");
        System.out.println("1 - to convert to dollars");
        System.out.println("2 - to convert to rupees");

        Scanner sc = new Scanner(System.in);
        ch = sc.nextInt();

        System.out.print("Enter the amount to convert: ");
        num = sc.nextFloat();
        
        
        if(ch==1)
            System.out.println(num + " Rupees = " +(num/70) + " Dollars");
        else if(ch==2)
            System.out.println(num + " Dollars = " +(num*70) + " Rupees");
        else
            System.out.println("Enter the valid choice.");


    }
}