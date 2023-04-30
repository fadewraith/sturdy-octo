// inheritance employee

import java.util.*;

class Employee{
    int emp_id;
    String emp_name;

    void setEmployee(int emp_id,String emp_name){
        this.emp_id = emp_id;
        this.emp_name = emp_name;
    }

    void getEmployee(){
        System.out.println("The id of the employee is: "+emp_id);
        System.out.println("The name of the employee is: "+emp_name);
    }
}

class Payroll extends Employee{
    int bs,hra,da;

    void setPayroll(int bs, int hra,int da){
        this.bs = bs;
        this.hra = hra;
        this.da = da;
    }

    void getPayroll(){
        System.out.println("The basic salary is: "+bs);
        System.out.println("The hra is: "+hra);
        System.out.println("The da is: "+da);
    }
}

class Payslip extends Payroll{
    int netSalary(){
        return hra+da+bs;
    }
}

class empInheri{

    public static void main(String args[]){
    
        Scanner sc = new Scanner(System.in);
        Payslip ps = new Payslip();

        ps.setEmployee(1001, "hello");
        ps.getEmployee();

        ps.setPayroll(10000, 3000, 2000);
        ps.getPayroll();

        int tot = ps.netSalary();
        System.out.println("The total salary is: "+tot);
    }
}