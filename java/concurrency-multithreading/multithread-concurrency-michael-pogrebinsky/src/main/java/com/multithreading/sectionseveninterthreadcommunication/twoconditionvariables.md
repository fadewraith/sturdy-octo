Inter thread - condition variable

Thread A -> check condition, if condn is not met, we can choose to suspend Thread A until another thread B changes some state and signals thread A to wake up, then thread A checks if the condn is not satisfied, if it is then it can move on and do its work.

- Condition variable is always associated with a lock
- The lock ensures atomic check and modification of the shared variables, involved in the condition

Lock lock = new ReentrantLock();
Condition condition = lock.newCondition(); 

Inter thread - Producer Consumer

Lock lock = new ReentrantLock();
Condition condition = lock.newCondition();
String username = null, password = null;

lock.lock();
try {
    while (username == null || password == null) {
        condition.await();
    }
    // do something with username and password
} finally {
    lock.unlock();
}
doStuff();


lock.lock();
try {
    username = "username";
    password = "password";
    condition.signal();
} finally {
    lock.unlock();
}

Inter thread - Condition.await()

- void await() - unlock lock, wait until signalled
- long awaitNanos(long nanosTimeout) - wait no longer than nanosTimeout
- boolean wait(long time, TimeUnit unit) - wait no longer than time, in given time units
- boolean awaitUntil(Date deadline) - wake up before the deadline date
- void signal() - wakes up a single thread, waiting on the condition variable
- thread that wakes up has to reqcuire the lock associated with the condition variable
- if currently no thread is waiting on the condition variable, the signal method doesn't do anything
- void signalAll() - broadcast a signal to all threads currently waiting on the condition variable
- doesnt need to know how many (if at all) threads are waiting on the condition variable