// to find the volume and tsa of cuboid

import java.util.Scanner;

class cuboid
{
public static void main(String[] args)
{

int l,b,h;

System.out.println("Enter the l,b & h:");

Scanner sc = new Scanner(System.in);
l = sc.nextInt();
b = sc.nextInt();
h = sc.nextInt();

int volume = l * b * h;
int tsa = 2*((l*b) + (b*h) + (h*l));

System.out.println("Volume of a cuboid is: " + volume);
System.out.println("TSA of cuboid is: " + tsa);

}
}

