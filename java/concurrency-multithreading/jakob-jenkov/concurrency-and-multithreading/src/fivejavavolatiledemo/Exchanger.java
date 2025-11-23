package fivejavavolatiledemo;

public class Exchanger {

    private Object object = null;

    // this variable should directly be read from main memory and when the value is changes then the changed value must be written back directly to main memory
    private volatile boolean hasNewObject = false;



    // use volatile var, when you need visibility guarantee, coz it slows down the performance

    public void setObject(Object o) {
        this.object = o;
        this.hasNewObject = true;
    }

    public Object getObject() {
        while (!this.hasNewObject) {
            // Thread.yield();
        }

        Object returnValue = this.object;
        this.hasNewObject = false;
        return returnValue;
    }
}
