// output is: inside baseC::method

interface BaseI { void method(); }

class BaseC{
    public void method(){
        System.out.println("inside baseC::method");
    }
}

class hello extends BaseC implements BaseI{
    public static void main(String []s){
        (new hello()).method();
    }
}