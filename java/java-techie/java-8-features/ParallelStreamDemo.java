

import java.util.List;
import java.util.stream.IntStream;

public class ParallelStreamDemo {


    public static void main(String[] args) {
    	
    	long start = 0;
    	long end = 0;
    	
//    	start = System.currentTimeMillis();
//    	IntStream.range(1, 100).forEach(System.out::println);
//    	end = System.currentTimeMillis();
//    	System.out.println("plain stream took time => "+(end - start));
//    	
//    	// order of execution is not constant in parallel stream
//    	// responswsie parallel stream is better than plain stream
//    	start = System.currentTimeMillis();
//    	IntStream.range(1, 100).parallel().forEach(System.out::println);
//    	end = System.currentTimeMillis();
//    	System.out.println("parallel stream took time => "+(end - start));
    	
//    	IntStream.range(1, 10).forEach(x -> {
//    		System.out.println("Thread: " + Thread.currentThread().getName()+" : "+x);
//    	});
//    	
//    	System.out.println("#########################################");
//    	
//    	IntStream.range(1, 10).parallel().forEach(x -> {
//    		System.out.println("Thread: " + Thread.currentThread().getName()+" : "+x);
//    	});
    	
    	List<EmployeeReduceDemo> employees = EmployeeDatabase.getEmployees();
    	
    	// normal
    	start=System.currentTimeMillis();
        double salaryWithStream = employees.stream()
                .map(EmployeeReduceDemo::getSalary).mapToDouble(i -> i).average().getAsDouble();
        end=System.currentTimeMillis();

        System.out.println("Normal stream execution time : "+(end-start)+" : Avg salary : "+salaryWithStream);
        
        start=System.currentTimeMillis();
        double salaryWithParallelStream = employees.parallelStream()
                .map(EmployeeReduceDemo::getSalary).mapToDouble(i -> i).average().getAsDouble();

        end=System.currentTimeMillis();

        System.out.println("Parallel stream execution time : "+(end-start)+" : Avg salary : "+salaryWithParallelStream);
        
    }
}