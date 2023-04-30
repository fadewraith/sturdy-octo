// Create a package myutil. In myutil package create a class with the name TestMyUtil. In
// TestMyUtil class create two methods add() and greatest(). The add() method returns sum of
// two numbers and greatest() method return greatest no in two nos. Now import myutil
// package in class TestPackage . Now test the class TestMyUtil

import java.util.*;
import myutil.TestMyUtil;

class MyUtil{
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        TestMyUtil test = new TestMyUtil();

        System.out.print("Enter two numbers: ");
        int a = sc.nextInt();
        int b = sc.nextInt();

        System.out.println("The sum is: "+test.add(a, b));
        System.out.println("The greatest among a & b is: "+test.greatest(a, b));
    }

}