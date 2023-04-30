// to check even or odd number

import java.util.*;

class oddEven{
	
	public static void main(String []args){
		
		int num;
		
		System.out.println("Enter the number to check for odd or even:");
		Scanner sc = new Scanner(System.in);
		num = sc.nextInt();
		
		if(num%2==0)
			System.out.println(num + " is even");
		else
			System.out.println(num + " is odd");
		
		}
		
		}
