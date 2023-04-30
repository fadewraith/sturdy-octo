//a simple calculator

import java.util.Scanner;

class simpleCalc
{
public static void main(String[] args)
{

int a,b;

Scanner sc = new Scanner(System.in);
System.out.println("Enter two numbers: ");
a = sc.nextInt();
b = sc.nextInt();

System.out.println("Sum is: " + (a+b));

System.out.println("Diff is: " + (a-b));

System.out.println("Product is: " + (a*b));

System.out.println("Division is: " + (a/b));

System.out.println("Remainder is: " + (a%b));

}
}
