// linked list demo 1

import java.util.*;

class Employee{
    int id;
    String name;
    double salary;

    Employee(int id, String name, double salary){
        this.id = id;
        this.name = name;
        this.salary = salary;
    }
}

class empLinkList{
    public static void main(String args[]){
        // Scanner sc = new Scanner(System.in);
        LinkedList<Employee> ll = new LinkedList<Employee>();
        ll.add(new Employee(1001,"hello",43000));
        ll.add(new Employee(1002,"world",55000));
        ll.add(new Employee(1003,"this",67000));
        // System.out.println(ll);
        for(Employee e:ll){
            System.out.print(e.id+"\t"+e.name+"\t"+e.salary+"\n");
        }

        
        

    }
}