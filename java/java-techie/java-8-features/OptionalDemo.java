import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class OptionalDemo {
	
	public static Customer getCustomerByEmailId(String email) throws Exception {
		List<Customer> customers = EkartDataBase.getAll();
		
//		return customers.stream().filter(customer -> customer
//				.getEmail()
//				.equals(email))
//				.findAny().get();
//		return customers.stream().filter(customer -> customer
//				.getEmail()
//				.equals(email))
//				.findAny().orElse(new Customer());
		
		return customers.stream().filter(customer -> customer
				.getEmail()
				.equals(email))
				.findAny().orElseThrow(()->new Exception("no customer present with this email id"));
	}

	public static void main(String[] args) throws Exception {
		
		// 3 methods to create optional methods -
		
		//empty
		//of
		//ofNullable
		
		Customer customer = new Customer(101, "john", "test@test.com", Arrays.asList("3987878", "7856588"));
		
		Optional<Object> emptyOptional = Optional.empty();
		System.out.println(emptyOptional);
		
		// if we know, the object which we are passing is not null, then we can go for jsut below method, because it will do the null check internally
		Optional<Optional<String>> emailOptional = Optional.of(customer.getEmail());
		System.out.println(emailOptional);
		// use this method, if we know that it may or may not be null
		Object emailOptional2 = Optional.ofNullable(customer.getEmail());
//		System.out.println(((Optional<Object>) emailOptional2).get());
//		System.out.println(((Optional<String>) emailOptional2).orElse("default"));
//		System.out.println(((Optional<String>) emailOptional2).orElseThrow(() -> new IllegalArgumentException("email not present")));
//		
		
// taking supplier
//		System.out.println(((Optional<Object>) emailOptional2).map(String::toUpperCase).orElseGet(() -> "default email..."));
		
		getCustomerByEmailId("pqr");

	}

}
