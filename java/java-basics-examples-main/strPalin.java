// to check given string is palindrome or not

import java.util.*;

class strPalin {
    public static void main(String[] args){
        System.out.print("Enter a string: ");
        String str = new Scanner(System.in).nextLine();
        String rev_str="";
        for(int i=str.length()-1;i>=0;i--){
            rev_str += str.charAt(i);
        }
        if(str.equalsIgnoreCase(rev_str))
            System.out.println("String is palindrome");
        else
            System.out.println("String is palindrome");
    }
}
