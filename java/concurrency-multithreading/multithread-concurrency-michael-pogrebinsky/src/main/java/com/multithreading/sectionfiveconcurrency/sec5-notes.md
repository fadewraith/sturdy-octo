The concurrency problem -
-> 2 threads sharing the items counter
-> both threads are reading and modifying that counter in the same time
-> operations were not atomic


First and simple solution is -> 
Synchronized - 
its a locking mechanism, designed to prevent an access to a block of code or en entire method by a multiple threads
2 ways to use it -
1. Synchronized method - monitor
2. Synchronized block

Synchronized method -
method is synchronized by default
method is synchronized on the instance of the object
method is synchronized on the class

Synchronized block -
block is synchronized on the instance of the object
block is synchronized on the class

Synchronized keyword is not a silver bullet
it can cause a deadlock
it can cause a performance issue
it can cause a visibility issue

synchronized used on method is equivalent to synchronized(this) block inside method

synchronized block is Reentrant
A thread cannot prevent itself from entering a critical section

Let me help you understand the potential issues with `synchronized` in Java, including deadlocks, performance impacts, and visibility concerns.

### 1. Deadlock
- **How it happens**: When multiple threads are blocked forever, each waiting for a lock held by another thread.
- **Example**:
  ```java
  // Thread 1
  synchronized(lock1) {
      synchronized(lock2) { ... }
  }
  
  // Thread 2
  synchronized(lock2) {
      synchronized(lock1) { ... }  // Deadlock potential
  }
  ```
- **Prevention**: Always acquire locks in the same order.

### 2. Performance Issues
- **Causes**:
    - Only one thread can execute the synchronized block at a time
    - Thread contention when multiple threads try to acquire the same lock
    - Context switching overhead
- **Impact**:
    - Reduced throughput
    - Increased latency
    - Poor CPU utilization
- **Mitigation**:
    - Keep synchronized blocks as small as possible
    - Use more granular locks
    - Consider concurrent collections or `java.util.concurrent` utilities

### 3. Visibility Issues
- **Problem**: Without proper synchronization, changes made by one thread might not be visible to others.
- **How `synchronized` helps**:
    - Establishes happens-before relationship
    - Ensures all variables are read from main memory when entering and written back when exiting
- **Example**:
  ```java
  private boolean flag = false;
  
  // Thread 1
  synchronized void setFlag() {
      flag = true;  // Guaranteed to be visible to other threads
  }
  
  // Thread 2
  synchronized boolean getFlag() {
      return flag;  // Will see the most recent value
  }
  ```

### Usage on Methods vs Blocks

1. **Synchronized Method**:
   ```java
   public synchronized void increment() {
       // Locks on 'this' instance
       count++;
   }
   ```
    - Locks on the entire object (`this`)
    - Simpler but coarser-grained
    - Can lead to unnecessary blocking

2. **Synchronized Block**:
   ```java
   private final Object lock = new Object();
   private int count = 0;
   
   public void increment() {
       synchronized(lock) {  // More fine-grained control
           count++;
       }
   }
   ```
    - More flexible - can use different lock objects
    - Better performance with smaller critical sections
    - Reduces contention

### Best Practices
- Prefer synchronized blocks over synchronized methods for better control
- Keep critical sections as small as possible
- Use final lock objects to prevent accidental modification
- Consider higher-level concurrency utilities from `java.util.concurrent`

static class SharedClass {
private int counter = 0;

        public synchronized void increment() {
this.counter++;
}
}

The synchronized keyword locks on the this object (the SharedClass instance).
Only one thread can execute this method at a time.