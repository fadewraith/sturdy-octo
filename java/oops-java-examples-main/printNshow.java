/*Develop two interfaces Printable and Showable. In Printable interface create an abstract
method print() and in Showable interface create an abstract method show(). Now implement
both interface in a class named TestMultipleInterface and override both methods print() and
show(). Now test the class TestMultipleInterface.
*/

interface printable{
    void print();
}

interface showable{
    void show();
}

class TestMultipleInterface implements printable,showable{

    public void print(){
        System.out.println("inerface of printable");
    }

    public void show(){
        System.out.println("interface of showable");
    }

    public static void main(String args[]){
        TestMultipleInterface test = new TestMultipleInterface();
        test.print();
        test.show();
    }
}