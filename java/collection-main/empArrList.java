// wap to create arraylist of user defined objects

import java.util.*;

class Employee{

	int emp_id; //non-static variables
	String emp_name;
	double salary;

	Employee(int emp_id,String emp_name,double salary){
		this.emp_id = emp_id;
		this.emp_name = emp_name;
		this.salary = salary;
	}


}

class empArrList{
	public static void main(String args[]){
		ArrayList<Employee> al = new ArrayList<Employee>();
		// Scanner sc = new Scanner(System.in);
		// int emp_id = sc.nextInt();
		// sc.nextLine();
		// String name = sc.nextLine();
		// sc.nextLine();
		// double salary = sc.nextDouble();

		// Employee emp = new Employee(emp_id,name,salary);
		//anonymous object creation
		al.add(new Employee(1001,"Brijesh",40000));
		al.add(new Employee(1002,"hello",45000));
		al.add(new Employee(1003,"world",56000));
		// Iterator itr = al.iterator();
		// while(itr.hasNext()){
		// 	System.out.print(itr.next()+" ");
		// }
		for(Employee e:al){
			System.out.print(e.emp_id+"\t"+e.emp_name+"\t"+e.salary+"\n");
		}

	}
}