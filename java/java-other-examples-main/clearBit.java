//clear the 3 bit (pos = 2) of a number n(n = 0101)
//bit mask = 1<<i
//opern = AND with NOT
// 1<<2 = 0001 = 0100
// ~(0100) = 1011
// 1011 & 0101 = 0001 = 1

class clearBit{
	public static void main(String args[]){
		int num = 5;
		int pos = 2;
		int bitmask = 1<<pos;
		int newNum = (~(bitmask)) & num;
		System.out.println(newNum);
	}
}
