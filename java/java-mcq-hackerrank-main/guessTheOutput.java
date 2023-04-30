/*
	
	Output of the below program is - 
	8
	7

	Reason - 

	int x = 10;
	System.out.println(x);                      // Output: 10

	int y = 010;
	System.out.println(y);                      // Output: 8

	when 010 is printed in java it actually prints 8 which is Octal value of digit 010
*/

class Test{

    public static void main(String[] args){
        
        int i = 010;
        int j = 07;
        System.out.println(i);
        System.out.println(j);

    }
}

