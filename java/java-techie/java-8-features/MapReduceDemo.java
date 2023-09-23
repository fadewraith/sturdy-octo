import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MapReduceDemo {

	public static void main(String[] args) {
		List<Integer> numbers = Arrays.asList(3, 7, 8, 1, 5, 9);
		List<String> words = Arrays.asList("hello", "world", "spring", "java", "john", "doe");
		
		// where sum is a method provided by stream
		int total = numbers.stream().mapToInt(i -> i).sum();
		System.out.println(total);
		
		Integer reduceSum = numbers.stream().reduce(0, (a, b) -> a + b);
		System.out.println(reduceSum);
		
		Optional<Integer> reduceSumWithMethodReference = numbers.stream().reduce(Integer::sum);
		System.out.println(reduceSumWithMethodReference.get());
		
		Integer multipleResult = numbers.stream().reduce(1, (a, b) -> a * b);
		System.out.println(multipleResult);
		
		Integer maxValue = numbers.stream().reduce(0, (a, b) -> a > b ? a : b);
		System.out.println(maxValue);
		
		// method reference
		Integer minValue1 = numbers.stream().reduce(Integer::min).get();
		System.out.println(minValue1);
		
		// we dont need to pass the identity here, because its a string
		String longestString = words.stream().reduce((w1, w2) -> w1.length() > w2.length() ? w1 : w2).get();
		System.out.println(longestString);
		
		// get employee whose grade is A
		// now get salary of employee
		double averageSalary = EmployeeDatabase.getEmployees().stream()
				.filter(employee -> employee.getGrade().equalsIgnoreCase("A"))
				.map(employee -> employee.getSalary())
				.mapToDouble(i -> i)
				.average().getAsDouble();
		System.out.println(averageSalary);
		
		double sumSalary = EmployeeDatabase.getEmployees().stream()
				.filter(employee -> employee.getGrade().equalsIgnoreCase("A"))
				.map(employee -> employee.getSalary())
				.mapToDouble(i -> i)
				.sum();
		System.out.println(sumSalary);

	}

}
