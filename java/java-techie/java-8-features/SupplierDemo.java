import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class SupplierDemo {
	
//	public String get() {
//		return "hello world";
//	}

	public static void main(String[] args) {
//		Supplier<String> supplier = new SupplierDemo();
//		System.out.println(supplier.get());
		
		Supplier<String> supplier = () -> "hello world";
		System.out.println(supplier.get()); // get is the inbuilt method inside Supplier class
		
//		List<String> list1 = Arrays.asList("a", "b");
		List<String> list1 = Arrays.asList();
//		System.out.println(list1.stream().findAny().orElseGet(supplier));
		
		System.out.println(list1.stream().findAny().orElseGet(() -> "hello world"));
		
		
	}

}
