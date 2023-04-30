// wap to create a package with name 'mypack'. Inside this package create a public class TempConv. In TempConv class
// create 2 methods c2f() & f2c()

// import java.util.*;

package mypack;

public class TempConv{

    public double c2f(double c){
        double f = (9*c)/5+32;
        return f;
    }

    public double f2c(double f){
        double c = (5*(f-32))/9;
        return c;
    }
}