// Write a java program to calculate electricity bill based on following parameters:

import java.util.*;

class calBill{
	
	public static void main(String [] args){
	
		double bill,tot;
		
		Scanner sc = new Scanner(System.in);
		
		bill = sc.nextDouble();
		
		if(bill>=1 && bill<=150){
			bill *= 2.4;
			System.out.println("The total bill is: " + bill);
			}
		else if(bill>=151 && bill<=300){
			bill -= 150;
			tot = 150*2.4 + bill*3;
			System.out.println("The total bill is: " + tot);
			}
		else{
			bill -= 300;
			tot = 150*2.4 + 150*3 + bill*3.2;
			System.out.println("The total bill is: " + tot);
			}
			
			}
			
			}
