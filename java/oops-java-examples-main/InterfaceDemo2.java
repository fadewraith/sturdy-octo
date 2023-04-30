//First Meeting
interface school
{
   void registration();
   void feeSubmission();
   void batchAllotment();
 }

//Second Meeting
abstract class Test1 implements school
{
  public void registration()
  {
     System.out.println("Business logic for registration");
   }
}
//THird Meeting
abstract class Test2 extends Test1
{
  public void feeSubmission()
  {
     System.out.println("Business logic for feeSubmission");
   }
}
//Fourth Meeting
class Test3 extends Test2
{
  public void batchAllotment()
  {
    System.out.println("Business logic for batchAllotment");
  }
public static void main(String[]args)
{
   Test3 t=new Test3();
    t.registration();
    t.feeSubmission();
    t.batchAllotment();
}
}