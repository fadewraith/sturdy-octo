import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MapVsFlatMap {

	public static void main(String[] args) {
		List<Customer> customers = EkartDataBase.getAll();
		
		// List<Customer> convert List<String> -> Data Transformation
		
		List<String> emails = customers.stream().flatMap(customer -> customer.getEmail().stream()).collect(Collectors.toList());
		
		System.out.println(emails);
		// using map
//		List<List<String>> phoneNumbers = customers.stream().map(customer -> customer.getPhoneNumbers()).collect(Collectors.toList());
		
		// using flatMap - 
		List<String> phoneNumbers = customers.stream().flatMap(customer -> customer.getPhoneNumbers().stream()).collect(Collectors.toList());
		
		System.out.println(phoneNumbers);

	}

}
