// THis program concept of interface
 interface MyInterface
{
void m1();   //public abstract void m1();
void m2();  //public abstract void m2();
void m3();  //public abstract void m3();
}
class TestInterface implements MyInterface
{
     public void m1()
{
  System.out.println("This message from m1()");
}
public void m2()
{
  System.out.println("This message from m2()");
}
public void m3()
{
  System.out.println("This message from m3()");
}
public static void main(String[]args)
{
   TestInterface ti=new TestInterface();
    ti.m1();
    ti.m2();
    ti.m3();
}
}
