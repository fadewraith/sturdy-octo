// Develop a java program to count occurrence of a word in a sentence.

import java.util.*;

class CountOccOfWord{
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        int count = 0;

        System.out.print("Enter the sentence: ");
        String sen = sc.nextLine();

        

        String str[] = sen.split(" ");

        System.out.print("Enter the word to count: ");
        String word = sc.nextLine();

        // System.out.println(str.length);
        // for(String s:str){
        //     if(s==word){
        //         count += 1;
        //     }
        // }

        for(int i=0;i<str.length;i++){
            if(word.equals(str[i])){
                count++;
            }
            
        }

        System.out.println(count);

    }
}