// bit manipulation is changing 0 to 1 or 1 to 0
//see bitMask.java program for working
// set the second bit(posn = 1) of a number n (n=0101)
// perform OR operation
// 1<<i = 1<<1 == 0010 => 0010 | 0101 = 0111 = (7)
// if in bit mask there is 1 or 0 at specific posn then it will always be 0 or 1 at the same posn
// rest digits will be decided by original num i.e. 0101

import java.util.*;

class setBit{
	public static void main(String args[]){
		int num = 5;
		int posn = 1;
		int bitMask = 1<<pos;
		int newNum = bitMask | num;
		System.out.println(newNum);
	}
}