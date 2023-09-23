

interface Calc {
//	void switchOn(); // this is the abstract method
//	void sum(int input);
	int diff(int i1, int i2);
}

public class Calculator {
	
	// this method is traditional approach
//	public void switchOn() {
//		System.out.println("switch on");
//	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// this is an anonymous function and we need not to implement interface
//		Calc calculator = () -> {
//			System.out.println("switch on");
//			System.out.println("switch ");
//		};
		
		// assigning it to the interface
//		Calc calculator = () -> System.out.println("switch on");
//		
//		calculator.switchOn();
		// we dont need to pass the data type , will be identified based on the interface
//		Calc calculator1 =  (input) -> System.out.println("sum: " + input);
//		
//		calculator1.sum(5);
		
//		Calc calculator2 =  (input1, input2) -> input1 - input2;
		Calc calculator2 =  (input1, input2) -> {
			if(input1 < input2) {
				throw new RuntimeException("message");
			} else {
				return input1 - input2;
			}
		};
		
		System.out.println(calculator2.diff(3, 4));

	}

}
