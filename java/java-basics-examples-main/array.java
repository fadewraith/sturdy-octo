// to find sum and average of 10 numbers using an array

import java.util.*;

class array {
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the num to insert elements in an array: ");
        int n = sc.nextInt();

        float sum = 0,avg=0;

        int[] list = new int[n];

        for(int i=0;i<n;i++){
            list[i] = sc.nextInt();
            sum += list[i];
            avg = sum/n;
        }
        System.out.println("Sum is: "+sum);
        System.out.println("Average is: "+avg);
    }
}
