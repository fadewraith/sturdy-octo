// Write a java program to find sum digits of a alphanumeric string.
/*
Algorithm:

    Take a String.
    Convert it into array of characters
    Apply for loop till length of char array
    Using isDigit() method we can check the digits in string.
    If isDigit() will return true then print that index value.
    That digit is in char form. We will convert it into String then Integer.
    Using sum variable, we will sum it.
*/

import java.util.*;

class sodOalphaNum {
    public static void main(String args[]){

        System.out.print("Enter the alpha numeric string: ");
        String str = new Scanner(System.in).nextLine();

        char c[] = str.toCharArray();

        int sum = 0;

        for(int i=0;i<c.length;i++){
            
            if(Character.isDigit(c[i])){

                System.out.print("Digits in the String : " + c[i]);
                System.out.println();

                int a = Integer.parseInt(String.valueOf(c[i]));
                sum += a;

            }

        }
        System.out.println("Sum of Digits is : " + sum);
        
    }
    
}
