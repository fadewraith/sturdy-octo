// Write a java program to print duplicate elements in array

import java.util.*;

class dupEleArr{
    public static void main(String args[]){

        Scanner sc = new Scanner(System.in);
        int arr[] = new int[10];

        System.out.print("Enter the elements in an array: ");

        for(int i=0;i<arr.length;i++){
            arr[i] = sc.nextInt();
        }

        System.out.print("The duplicate elements of an array are: ");

        for(int i = 0; i < arr.length; i++) {  
            for(int j = i + 1; j < arr.length; j++) {  
                if(arr[i] == arr[j])  
                    System.out.print(arr[j]+" ");  
            }  
        }
        
        
        System.out.println();
        
    }
}