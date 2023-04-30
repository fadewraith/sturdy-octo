//Write a java program to convert a decimal number to its binary, octal and hexa-decimal
// equivalent. Hint:- Use Integer.toString() method as Integer.toString(n,2) for binary,
// Integer.toString(n,8) for octal and Integer.toString(n,16) for hexa-decimal.
/*
https://howtodoinjava.com/java/binary-octal-and-hexadecimal-conversions-in-java/
*/

// convert integer 2 string using Integer.toString() or String.valueOf()

import java.util.*;

class conver2num {
    public static void main(String[] args)    {
        Integer decimal1 = 21;
        String binaryNumber = Integer.toString(decimal1, 2);
 
        System.out.println(decimal1 + " in Base 2 : " + binaryNumber);
 
        Integer decimal2 = 302;
        String octalNumber = Integer.toString(decimal2, 8);
 
        System.out.println(decimal2 + " in Base 8 : " + octalNumber);
 
        Integer decimal3 = 43981;
        String hexNumber = Integer.toString(decimal3, 16);
 
        System.out.println(decimal2 + " in Base 16 : " + hexNumber);
 
    }
}
