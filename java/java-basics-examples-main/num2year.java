// Write a java program to convert given number of days to a measure of time given in years, weeks and days.

import java.util.*;

class num2year{
	
	public static void main(String [] args){
	
		int num;
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the number of days to convert: ");
		num = sc.nextInt();
		
		int year = num/365;
		
		int weeks = (num%365)/7;
		
		int days = (num%365)%7;
		
		System.out.println("Converted " + num + " days is: " + year + " year, " + weeks + " week & " + days +" days");
		
		}
		
		}
		
