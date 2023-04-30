// linked list student iterator

import java.util.*;

class Student{
    int id;
    String name;
    double fee;

    Student(int id,String name,double fee){
        this.id = id;
        this.name = name;
        this.fee = fee;
    }

}


class stuLinkIterator{
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        LinkedList<Student> ll = new LinkedList<Student>();
        
        for(int i=0;i<2;i++){
            System.out.print("Enter id: ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter name: ");
            String name = sc.nextLine();
            System.out.print("Enter fee: ");
            double fee = sc.nextDouble();
            ll.add(new Student(id,name,fee));
        }

        Iterator itr = ll.iterator();

        while(itr.hasNext()){
            Student s = (Student)itr.next();
            System.out.print(s.id+"\t"+s.name+"\t"+s.fee+"\n");

        }


        

    }
}