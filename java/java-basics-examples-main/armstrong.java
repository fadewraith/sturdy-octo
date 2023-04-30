// to check for an armstrong number

import java.util.*;

class armstrong {
    public static void main(String[] args){
        // Scanner sc = new Scanner(System.in);
        int num,sum=0;
        System.out.print("Enter the number to check for Armstrong: ");
        num = new Scanner(System.in).nextInt();
        // num = sc.nextInt();
        int n = num;
        while(num>0){
            sum += Math.pow(num%10,3);
            num /= 10;
        }

        if(sum==n)
            System.out.println(n+" is an Armstrong number");
        else
            System.out.println(n+" is not an Armstrong number");
    }
}
