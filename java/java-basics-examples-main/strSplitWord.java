// word counter using split function

import java.util.*;

class strSplitWord {
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the string: ");
        String str = sc.nextLine();

        String[] words = str.split(" ");

        System.out.println("No. of words are: "+words.length);

        // for(String w:words){

            // System.out.println(w);
            // System.out.print(Character.toUpperCase(w.charAt(0))+" ");

        // }

        

        for(int i=0;i<words.length-1;i++){

            System.out.print(Character.toUpperCase(words[i].charAt(0))+" ");
        }

        System.out.println(words[words.length - 1]);


    }
}
