// throw and throws exception

import java.io.*;
import java.util.*;

class exceptDemo2{
    public static void main(String args[]) throws IOException{
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(reader);

        System.out.print("Enter employee id: ");
        int emp_id = Integer.parseInt(br.readLine());

        System.out.print("Enter employee name: ");
        String emp_name = br.readLine();

        System.out.print("Enter emp salary: ");
        long salary = Long.parseLong(br.readLine());

        System.out.println("Emp id is: "+ emp_id);
        System.out.println("Emp name is: "+ emp_name);
        System.out.println("Salary of an emp is: "+salary);
    }

}