// to find the factorial of a number

import java.util.*;

class fact {
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the num to calc the factorial: ");
        int num = sc.nextInt();

        int fact;

        for(fact=1;num>0;num--){
            fact *= num;
        }
        // while(num>0){
        //     fact *= num;
        //     num--;
        // }
        System.out.println(fact);
    }
}
