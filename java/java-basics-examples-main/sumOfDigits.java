// to calculate sum of digits

import java.util.*;

class sumOfDigits {
    
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the num: ");
        int num = sc.nextInt();
        int n = num;
        int sum = 0;

        for(sum=0;num>0;num/=10){
            sum += num%10;
        }

        System.out.println("The sum of "+n+" is: "+sum);

    }

}
