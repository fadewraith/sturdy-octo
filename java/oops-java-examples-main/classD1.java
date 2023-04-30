// to create class demo

class myClass{

    public void sayHello(String name){

        System.out.println("hello "+name);

    }

}

class classD1{

    public static void main(String[] args){

        myClass myC = new myClass();
        myC.sayHello("world");
        
    }
    
}