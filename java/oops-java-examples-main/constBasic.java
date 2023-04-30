// basic constructor

class spiEmp{

    final int emp_id;
    String emp_name;
    double salary;

    spiEmp(int eid){
        emp_id = eid;
    }

    void setValue(String name,double sal){
        emp_name = name;
        salary = sal;
    }

    void display(){
        System.out.println("Employee Id= "+emp_id+",");
        System.out.println("Employee Name= "+emp_name+",");
        System.out.println("Employee Salary= "+salary);
    }

}

class constBasic{

    public static void main(String args[]){

        spiEmp c1 = new spiEmp(1001); // as soon as the object is created constructor is called automatically
        c1.setValue("hello world",40000);
        c1.display();
        spiEmp c2 = new spiEmp(1002);
        c2.setValue("name", 65000);
        c2.display();

    }
}
