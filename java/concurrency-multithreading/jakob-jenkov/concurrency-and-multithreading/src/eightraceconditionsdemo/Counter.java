package eightraceconditionsdemo;

public class Counter {
    private long count = 0;

    // critical section
    public long incAndGet() {
        this.count++;
        return this.count;
    }
    
    public long get() {
        return this.count;
    }
}
