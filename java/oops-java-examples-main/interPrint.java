/*
Develop an interface named printable. In printable interface there is an abstract method print.
Now implement printable interface and give the definition of print() method. In print() method
give “Hello World” message
*/

interface printable{
    void print();
}

class Printable implements printable{
    public void print(){
        System.out.println("hello world");
    }

    public static void main(String args[]){
        Printable print = new Printable();
        print.print();
    }
}