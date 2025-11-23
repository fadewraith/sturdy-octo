package fivejavavolatiledemo;

public class Counter {

    private volatile int count = 0;

    public boolean inc() {
        if(this.count == 0) {
            return false;
        }
        // increment condition in java is not atomic
        // inc condn consists of 2 steps
        // 1. read the value
        // 2. increment the value
        // 3. write the value
        // if two threads are reading the value at the same time, then they will both read the same value
        // and both will increment the same value
        // and both will write the same value
        // so the count will be incremented by 1 instead of 2
        this.count++;
        return true;
    }
}
