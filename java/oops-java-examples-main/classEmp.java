// to create employee class

import java.util.*;

class employee{

    int emp_id; //instance variables
    String emp_name;
    double salary;

    // setValue method is used to initialise the instance variables

    public void setValue(int eid,String eName,double sal){

        emp_id = eid;
        emp_name = eName;
        salary = sal;

    }

    public void display(){

        System.out.println("Employee Id: "+emp_id);
        System.out.println("Employee Name: "+emp_name);
        System.out.println("Employee salary: "+salary);

    }

}

class classEmp{

    public static void main(String args[]){

        employee e1 = new employee()        ;
        // e1.display();   // will return (0,null,0.0);
        e1.setValue(1, "hello", 45000);
        e1.display();

    }
    
}