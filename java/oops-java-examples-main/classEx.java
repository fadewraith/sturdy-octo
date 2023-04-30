// Develop a program in java to handle a checked exception ClassNotFoundException.

// import java.util.*;


class classEx {


	public static void main(String args[])
  {


    try {

      Class.forName("GeeksForGeeks");
    }


    catch (ClassNotFoundException ex) {


      System.out.println("Exception caught");
    //   ex.printStackTrace();
    }
  }
}