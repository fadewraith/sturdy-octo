package fivejavavolatiledemo;

public class Exchanger2 {

    private int val1 = 0;
    private int val2 = 0;
    private volatile int val3 = 0;

    public void setValues(Values source) {
        // all the variables declared before volatile, must be initialised before volatile variable
        this.val1 = source.getVal1();
        this.val2 = source.getVal2();
        this.val3 = source.getVal3();
    }

    public void getValues(Values dest) {
        // while getting, volatile var must be read before the variables which are initialized before volatile variable
        dest.setVal3(this.val3);

        dest.setVal2(this.val2);
        dest.setVal1(this.val1);
    }
}
