// find the area and perimeter of circle

import java.util.Scanner;

class circle
{
public static void main(String[] args)
{

int r,area,perimeter;

Scanner sc = new Scanner(System.in);
System.out.println("Enter the radius:");

r = sc.nextInt();

area = (22*r*r)/7;
perimeter = (2*22*r)/7;

System.out.println("The area of cirlce is: " + area);
System.out.println("The perimeter of circle is: " + perimeter);

}
}
