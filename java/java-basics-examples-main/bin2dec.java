// to convert binary 2 decimal

import java.util.*;

class bin2dec {
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number: ");
        int num = sc.nextInt();

        int n = num,dec=0,i=0;

        for(dec=0;num>0;num/=10){
            dec += (num%10)*Math.pow(2,i);
            i++;
        }
        
        System.out.println("The converted binary "+n+" to decimal is: "+ dec);
    }
}
