// bit masking in java -> "<< = left shit", ">> = right shift"
// we create bit mask & then perform some operations like bitwise operators
//suppose bit mask for program is 1<<i 
// get the 3 bit (posn = 2)(i=0,1,2) of a number n (n=0101)
// here bit mask created is 1<<i and operation performed is "AND(&)"
// 1<<2 = 0001 -> now we will left shift 1 to 2 positions = 0100 
// 0100 & 0101 = 0100 (if all digits are zero then its 0 & if except all there is 1 then we will get that at posn 2 its 1)

import java.util.*;

class bitMask{
	public static void main(String args[]){

		int num = 5;
		int pos = 2;
		int bitmask = 1<<pos;

		if((bitmask & num)==0)
			System.out.println("bit is zero");
		else 
			System.out.println("bit is one");
	}
}