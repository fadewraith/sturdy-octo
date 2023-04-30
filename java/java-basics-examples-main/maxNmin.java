// to find the max and min in an array

import java.util.*;

class maxNmin {
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);
        int[] arr = new int[5];
        
        System.out.println("Enter the numbers in an array: ");
        for(int i=0;i<5;i++){
            arr[i] = sc.nextInt();
        }

        int min = arr[0];
        int max = arr[0];

        for(int i=1;i<5;i++){

            if(arr[i]>max)
                max = arr[i];
            else if(arr[i]<min)
                min = arr[i];

        }

        System.out.println("Largest Number is : " + max);
        System.out.println("Smallest Number is : " + min);
        
    }
    
}
