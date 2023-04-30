// Write a java program to copy elements of one array to another array

import java.util.*;

class copyArr{
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        int arr[] = new int[5];
        int carr[] = new int[5];
        System.out.print("Enter the elements in array: ");
        for(int i=0;i<arr.length;i++){
            arr[i] = sc.nextInt();
        }

        System.out.print("The elements in copied array is: ");

        for(int i=0;i<arr.length;i++){
            carr[i] = arr[i];
            System.out.print(carr[i]+" ");
        }

        // for(int i: carr){
        //     System.out.print(i+" ");
        // }
        System.out.println();
    }
}