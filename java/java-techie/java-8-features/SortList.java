import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortList {

	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		list.add(8);
		list.add(3);
		list.add(12);
		list.add(4);
		
		List<Employee> employees = DataBase.getEmployees();
		
		// traditional way - 
//		Collections.sort(employees, new MyComparator());
		// this is other way of doing it
		// Comparator is a functional interface
//		Collections.sort(employees, new Comparator<Employee>() {
//
//			@Override
//			public int compare(Employee o1, Employee o2) {
//				return (int) (o1.getSalary() - o2.getSalary()); // ascending, long data type so type casting to int
//			}
//			
//		});
		
		// lambda expression of comparator
		 // ascending, long data type so type casting to int
//		Collections.sort(employees, (o1, o2) -> (int) (o1.getSalary() - o2.getSalary()));
		Collections.sort(employees, (o1, o2) -> (int) (o2.getSalary() - o1.getSalary()));
		
		System.out.println(employees);
		
//		employees.stream().sorted((o1, o2) -> (int) (o1.getSalary() - o2.getSalary())).forEach(System.out::println);
		
		// descending order
//		employees.stream().sorted((o1, o2) -> (int) (o2.getSalary() - o1.getSalary())).forEach(System.out::println);
		
//		employees.stream().sorted(Comparator.comparing(emp -> emp.getSalary())).forEach(System.out::println);
		
		//optimized soln - using method reference instead of passing lambda expression, now it will sort based on the name
//		employees.stream().sorted(Comparator.comparing(Employee::getName)).forEach(System.out::println);
		
 // sorting using method reference by department		
 employees.stream().sorted(Comparator.comparing(Employee::getDept)).forEach(System.out::println);
		
//		Collections.sort(list);
//		System.out.println(list);
//		Collections.reverse(list);
//		System.out.println(list);
//		
//		list.stream().sorted().forEach(s -> System.out.println(s));
//		System.out.println();		
//		list.stream().sorted(Comparator.reverseOrder()).forEach(s -> System.out.println(s));
		
		
	}

}


class MyComparator implements Comparator<Employee> {

	@Override
	public int compare(Employee o1, Employee o2) {
//		return (int) (o1.getSalary() - o2.getSalary()); // ascending, long data type so type casting to int
		return (int) (o2.getSalary() - o1.getSalary());
	}
		
}