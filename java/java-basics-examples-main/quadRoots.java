// Write a java program to find roots of quadratic equation ax^2 +bx+c=0, a=1,b=-5,c=6

import java.util.*;

class quadRoots{

	public static void main(String [] args){
		
		int a,b,c,det;
		
		System.out.println("Enter the values of a,b,c: ");
		
		Scanner sc = new Scanner(System.in);
		
		a = sc.nextInt();
		b = sc.nextInt();
		c = sc.nextInt();
		
		det = (b*b) - 4*a*c;
		
//		double x = (-b + Math.sqrt(Math.pow(b,2) - 4*a*c))/(2*a);
//		double y = (-b - Math.sqrt(Math.pow(b,2) - 4*a*c))/(2*a);

		double x = (-b + Math.sqrt(det))/(2*a);
		double y = (-b - Math.sqrt(det))/(2*a);
		
		
		System.out.println(x);
		System.out.println(y);
		
		}
		}
