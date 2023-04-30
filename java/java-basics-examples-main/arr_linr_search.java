// array linear search

import java.util.*;

class arr_linr_search {
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        int[] arr = new int[10];
        int flag = 0,i;

        System.out.println("Enter 10 elements in an array: ");

        for(i=0;i<10;i++){
            arr[i] = sc.nextInt();
        }

        System.out.print("Enter the num to search in an array: ");
        int num = sc.nextInt();

        for(i=0;i<10;i++){

            if(num==arr[i]){

                flag = 1;
                break;

            }

        }

        if(flag==1)
            System.out.println(num + " is present at index "+(i+1));
        else
            System.out.println(num + " is not present in an array.");

    }
    
}
