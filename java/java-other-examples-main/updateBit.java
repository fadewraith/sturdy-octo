//update the 2 bit(posn = 1) of a number n to 1(n = 0101)
//bit mask => for 1 to 0 = 1<<i, for 0 to 1 = 1<<i
//opern => for 1 = AND with NOT, for 0 = OR
//1<<1 = 0001 = 0010 => (0010) | (0101) = 0111

import java.util.*;

class updateBit{
	public static void main(String args[]){
		int oper = new Scanner(System.in).nextInt();
		int num = 5;
		int pos = 1;
		//oprn = 1 : set, oprn = : clear
		// int oprn = 1; //update bit to 1 else update bit to 0
		int bitmask = 1<<pos;

		if(oper == 1 ){
			//set oprn
			int newNum = bitmask | num;
			System.out.println(newNum);
		}else {
			int newNum = (~(bitmask)) & num;
			System.out.println(newNum);
		}
	}
}