// Write a java program to check occurrence of ‘The’ word in a sentence.

import java.util.*;

class occOfstr {
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the string: ");
        String str = sc.nextLine();

        System.out.print("Enter the word: ");
        String word = sc.nextLine();

        String temp[] = str.split(" ");
        int count = 0;

        for(int i=0;i<temp.length;i++){
            if(word.equals(temp[i])){
                count++;
            }
        }

        System.out.println("The no. of "+word+" in a given string is: "+count);

    }

}
