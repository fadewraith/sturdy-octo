import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ConsumerDemo {
	// this is atraditional approach, we can write using the lambada expression also, and for that remove implemented Consumer
//	public void accept(Integer t) {
//		System.out.println("Printing: " + t);
//	}
	
	

	public static void main(String[] args) {
		Consumer<Integer> consumer = t -> System.out.println("Printing: " + t);
		consumer.accept(85);
		
		List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
//		list1.stream().forEach(consumer); // internally it will call the accept method
		// instead of passing the function, we can directly pass the lambda expression
		list1.stream().forEach(t -> System.out.println("print: " + t)); 

	}

}
