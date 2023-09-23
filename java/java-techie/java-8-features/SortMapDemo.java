import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class SortMapDemo {

	public static void main(String[] args) {
		// if we want to preserve the order, we can use the TreeMap
		Map<String, Integer> map = new HashMap<>();
		map.put("ten", 10);
		map.put("eight", 8);
		map.put("four", 4);		
		map.put("two", 2);
		
//		Map<Employee, Integer> employeeMap = new TreeMap<>(new Comparator<Employee>() {
//
//			@Override
//			public int compare(Employee o1, Employee o2) {
//				return (int) (o1.getSalary() - o2.getSalary());
//			}
//			
//		});
		// using ;ambda expression
//		Map<Employee, Integer> employeeMap = new TreeMap<>((o1, o2) -> (int) (o1.getSalary() - o2.getSalary()));
		Map<Employee, Integer> employeeMap = new TreeMap<>((o1, o2) -> (int) (o2.getSalary() - o1.getSalary()));
		employeeMap.put(new Employee(176, "Roshan", "IT", 60000), 60);
		employeeMap.put(new Employee(388, "Bikash", "CIVIL", 90000), 90);
		employeeMap.put(new Employee(470, "Bimal", "CORE", 50000), 50);
		employeeMap.put(new Employee(624, "Sourav", "DEFENCE", 40000), 40);
		employeeMap.put(new Employee(284, "Prakash", "SOCIAL", 120000), 120);
		
		System.out.println(employeeMap);
		
		// getting the list from a map
		// get the entry set from the map, then add it to the list
		List<Entry<String, Integer>> entries = new ArrayList<>(map.entrySet());
		
//		Collections.sort(entries, new Comparator<Entry<String, Integer>>() {
//
//			@Override
//			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
//				
//				return o1.getKey().compareTo(o2.getKey());
//			}
//			
//		});
		
//		Collections.sort(entries, (o1, o2) -> o1.getKey().compareTo(o2.getKey())); 
//		
//		for(Entry<String, Integer> entry: entries) {
//			System.out.println(entry.getKey() + " --> " + entry.getValue());
//		}
		
//		map.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(System.out::println);
		System.out.println("#####################################");
//		map.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach(System.out::println);
		
		// if map is using key as a object, the using stream on that - using comparingByKey comparator one
//		employeeMap.entrySet().stream().sorted(Map.Entry.comparingByKey(Comparator.comparing(Employee::getSalary))).forEach(System.out::println);
		// descending order
//		employeeMap.entrySet().stream().sorted(Map.Entry.comparingByKey(Comparator.comparing(Employee::getSalary).reversed())).forEach(System.out::println);
//		
		// sorting based on department
//		employeeMap.entrySet().stream().sorted(Map.Entry.comparingByKey(Comparator.comparing(Employee::getDept))).forEach(System.out::println);
		
		// comparing by value, if employee object contains in value section instead of comparingByKey, we can use comparingByValue, works only for object not for primitive data types
		
		employeeMap.entrySet().stream().sorted(Map.Entry.comparingByKey(Comparator.comparing(Employee::getDept))).forEach(System.out::println);
	}

}
