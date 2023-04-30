// to find the factors of a given number

import java.util.*;

class factors {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the num to find its factors: ");
        int num = sc.nextInt();
        
        for(int i=2;i<=num/2;i++){
            if(num%2==0)
                System.out.print(i+" ");
        }
        System.out.println();
    }
}
