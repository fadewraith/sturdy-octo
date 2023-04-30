// to calculate simple interest

import java.util.Scanner;

class si
{
public static void main(String[] args)
{

int p,r,t,si;

Scanner sc = new Scanner(System.in);

System.out.println("Enter the p,r & t in order:");

p = sc.nextInt();
r = sc.nextInt();
t = sc.nextInt();

si = (p*r*t)/100;

System.out.println("Simple Interest is: " + si);

}
}
