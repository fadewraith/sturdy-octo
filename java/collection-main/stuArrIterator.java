//by using iterator

import java.util.*;

class Student{
	int roll_no;
	String name;
	double fee;

	Student(int roll_no,String name,double fee){
		this.roll_no = roll_no;
		this.name = name;
		this.fee = fee;
	}
}

class stuArrIterator{
	public static void main(String args[]){
		Scanner sc = new Scanner(System.in);

		ArrayList<Student> al = new ArrayList<Student>();

		for(int i=0;i<2;i++){
			int roll = sc.nextInt();
			sc.nextLine();
			String name = sc.nextLine();
			double fee = sc.nextDouble();
			al.add(new Student(roll, name, fee));
		}

		// al.add(new Student(1001,"hello",23000));
		// al.add(new Student(1002,"world",67000));
		// al.add(new Student(1003,"java",3000));

		// for(Student s:al){
		// 	System.out.print(s.roll_no+"\t"+s.name+"\t"+s.fee+"\n");
		// }

		Iterator itr = al.iterator();
		while(itr.hasNext()){
			Student s = (Student)itr.next();
			System.out.print(s.roll_no+"\t"+s.name+"\t"+s.fee+"\n");
		}

	}
}