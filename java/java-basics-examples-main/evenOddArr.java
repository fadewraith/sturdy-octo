// to create an array and copy the odd and even array in 2 different

import java.util.*;

class evenOddArr {
    public static void main(String[] args){

        int [] arr = new int[10];
        int [] earr = new int[10];
        int [] oarr = new int[10];
        int j=0,k=0;

        System.out.println("Enter the 10 elements in an array: ");

        for(int i=0;i<10;i++){
            arr[i] = new Scanner(System.in).nextInt();
        }

        for(int i=0;i<10;i++){

            if(arr[i]%2==0){

                earr[j] = arr[i];
                j++;

            }else{

                oarr[k] = arr[i];
                k++;

            }
            
        }

        System.out.println("The even array list is: ");

        for(int i=0;i<j;i++){

            System.out.print(earr[i]+" ");
        }
        System.out.println();
        
        System.out.println("The odd array list is: ");

        for(int i=0;i<k;i++){

            System.out.print(oarr[i]+" ");

        }

        System.out.println();

    }

}
