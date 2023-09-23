import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EmployeeDatabase {


    public static List<EmployeeReduceDemo> getEmployees(){
//    	// used for reduce demo
//      return  Stream.of(new EmployeeReduceDemo(101,"john","A",60000),
//              new EmployeeReduceDemo(109,"peter","B",30000),
//              new EmployeeReduceDemo(102,"mak","A",80000),
//              new EmployeeReduceDemo(103,"kim","A",90000),
//              new EmployeeReduceDemo(104,"json","C",15000))
//              .collect(Collectors.toList());
    		
    	// used for paralletstream demo, used EmployeeReduceDemo
    	List<EmployeeReduceDemo> employees = new ArrayList<>();
    	for(int i = 1; i <= 1000; i++) {
    		employees.add(new EmployeeReduceDemo(i, "employee"+i, "A", Double.valueOf(new Random().nextInt(1000*100))));
    	}
    	return employees;
    }
	
	
	
}