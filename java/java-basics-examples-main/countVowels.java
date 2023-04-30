// to count the number of vowels

import java.util.*;

class countVowels {
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        int vowel = 0;

        System.out.print("Enter the string: ");
        String str = sc.nextLine();

        for(int i=0;i<str.length();i++){
            if(str.charAt(i)=='a' || str.charAt(i)=='e' || str.charAt(i)=='i' || str.charAt(i)=='o' || str.charAt(i)=='u'){
                vowel++;
            }
        }
        System.out.println(vowel);
    }
}
