package gfgjavacourse.basics2advanced;

public class AdvancedConcepts {

    /**
     * BigInteger - java.math.BigInteger
     * new BigInteger
     * add(), multiply, divide, subtract, compareTo, remainder, valueOf()
     * intValue, longValue, toString
     *
     * Check for prime and next prime  - .isProbablePrime, .nextProbablePrime
     * */

    /**
     * Exception Hierarchy -
     * Object -> Throwable ->
     * 1. Error
     * 2. Exception ->
     *  a. RuntimeException -> Arithematic, NullPointer, IndexOutOfBound
     *  b. FileNotFound, IOException
     * */

    /**
     * Exception Hierarchy -
     * k/w -> try, catch, finally, throw, throws
     * to throw an exception, throw k/w is used
     * throws is used on fn definitions and multiple exceptions can be specified by comma separated
     * */

    /**
     * java.lang.Object - Throwable
     * a -> Errors -> StackOverFlowError, VirtualMachineError, OutOfMemoryError
     * b -> Exception
     * b1 -> Compile Time Exception (Checked Exception)
     * 1-IOException, 2-SQLException, 3-ClassNotFoundException, 4-FileNotFoundException
     * b2 -> Runtime Exception(Unchecked Exception)
     * 1-ArithmeticException, 2-NullPointerException, 3-IllegalArgumentException, 4-IndexOutOfBoundException
     * */

    /**
     * Multithreading Introduction -
     * Multitasking vs Multithreading
     * Advantages & Disadvantages
     * Multitasking -> Listening to music and browsing web
     * Multithreading -> Downloading something in browser and browsing
     * Disadvantages of multithreading -
     * a - difficulty in writing, testing and debugging code
     * b - can lead to deadlock and race conditions
     * */

    /**
     * Multithreading -
     * 2 ways to create threads - extending thread class, implementing runnable interface
     * */

    /**
     * File Handling -
     * canRead(), canWrite(), createNewFile(), delete(), exists(), getName(), getAbsolutePath(), length()
     * */


}
