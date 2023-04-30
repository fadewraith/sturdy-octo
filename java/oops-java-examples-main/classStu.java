/* Create a class named Student. In Student class take three data members rollno, name and fee.
Now create a public method setStudent() to initialize data members. And also create a method
getStudent() to display student’s details. Now test the class Student.
*/

import java.util.*;

class student{
    int roll_no;
    String name;
    float fee;

    public void setStudent(int roll,String str,float fees){
        roll_no = roll;
        name = str;
        fee = fees;

        
    }

    public void getStudent(){
        System.out.println("Student Id is: "+roll_no);
        System.out.println("Student Name is: "+name);
        System.out.println("Student fees is: "+fee);
    }
}

class classStu{
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the num to enter in an array: ");
        int n = sc.nextInt();
        student stu[] = new student[n];
        // stu[0] = new student();
        // stu[0].setStudent(1, "hello world", 5000);
        // stu[0].getStudent();
        System.out.println("Enter the details of students in an array: ");
        for(int i=0;i<n;i++){
            System.out.print("Enter the roll no: ");
            int roll = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter the name: ");
            String name = sc.nextLine();
            System.out.print("Enter the fees: ");
            float fees = sc.nextFloat();
            stu[i] = new student();
            stu[i].setStudent(roll,name,fees);
        }

        System.out.println("The details of students are: ");

        for(int i=0;i<n;i++){
            stu[i].getStudent();
        }

    }
}