//wap to find sum of digits of given string. in ascii 48 is zero

import java.util.*;

class Task1{
    public static void main(String args[]){
        
        int sum=0;
        System.out.print("enter an alphanumeric string: ");
        String str = new Scanner(System.in).nextLine();

        for(int i=0;i<str.length();i++){

            if(str.charAt(i)>=48 && str.charAt(i)<=57){
                sum += Integer.parseInt(str.charAt(i)+"");
            }
        }
        
        System.out.println(sum);
        
    }
}