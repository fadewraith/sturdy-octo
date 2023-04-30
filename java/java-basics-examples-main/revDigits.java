// to reverse a digits

import java.util.*;

class revDigits {
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number: ");
        int num = sc.nextInt();

        int n = num,rev_dig=0;

        for(rev_dig=0;num>0;num/=10){
            rev_dig = rev_dig*10 + num%10;
        }
        
        System.out.println("The reversed digit of "+n+" is: "+rev_dig);
    }
}
