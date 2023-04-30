// wapt to find greatest in 3 unequal numbers

import java.util.*;

class greatestNo{
	
	public static void main(String [] args){
	
		int a,b,c;
		System.out.print("Enter the 3 numbers: ");
		Scanner sc = new Scanner(System.in);
		a = sc.nextInt();
		b = sc.nextInt();
		c = sc.nextInt();
		
		if(a>b && a>c)
			System.out.println(a + " is greatest of all numbers.");
		else if(b>a && b>c)
			System.out.println(b + " is greatest of all numbers.");
		else
			System.out.println(c + " is greatest of all numbers.");
		
		}
		}
				
		
		
