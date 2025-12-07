wait(), notify() and notifyAll() - 

The object class contains the foll methods -
- public final void wait() throws InterruptedException
- public final void notify()
- public final void notifyAll()
Every java class inherits from the object class
We can use any object as a condition variable and a lock (using the synchronized keyword)
- wait() - causes the current thread to wait until another thread wakes it up. in the wait state, the thread is not consuming any CPU
2 ways to wake up the waiting thread:
- notify() - wakes up a single thread waiting on this object's monitor
- notifyAll() - wakes up all threads waiting on this object's monitor 
- to call wait(), notify() or notifyAll() we need to acquire the monitor of that object (use synchronized on that object)