ReentrantLock -
-> Works just like the synchronized k/w applied on an object
-> Requires explicit locking and unlocking
-> locking happens in synchronized when block begins and unlocking happens in synchronized when block ends
-> ReentrantLock is Reentrant, we use .lock() to lock and .unlock() to unlock
Disadvantage -
-> we forgot to unlock
-> even if we use .unlock(), there maybe an exception coz of which we will never reach the .unlock(), soln to this is that in try block, we can perform operation, locking will be before try block and unlocking will be in finally block

Query methods - for testing
-> getQueuedThreads() - returns a list of threads waiting to acquire a lock
-> getOwner() - returns the thread that currently owns the lock
-> isHeldByCurrentThread() - queries if the lock is held by the current thread
-> isLocked() - queries if the lock is held by any thread
-> tryLock() - attempts to acquire the lock without blocking
-> by default, the ReentrantLock as well as synchronized k/w do not guarantee any fairness

Fairness -
-> ReentrantLock(true);
-> may reduce the throughput of the application.
so use the fairness flag, when you really need it

LockInterruptibly - motivation
Generally, if in a particular thread, we try to acquire a lock object and another thread is holding the lock, the current thread will get suspended, not wake up until the lock is released, in this case calling the .interrupt() method to wakeup the suspended thread will not do anything,
But instead of calling lock method, calling lockInterruptibly() method while another thread is holding the lock, then we can still get out of the suspension.
The lockInterruptibly method forces us to surround that with try and catch block and handle the InterruptedException, inside the catch block we can do some cleanup and shut down the thread gracefully

Use cases -
-> watchdog for deadlock detection and recovery
-> waking up threads to do clean and close the application

ReentrantLock - why ?
-> boolean tryLock()
-> boolean tryLock(long timeout, TimeUnit unit);
    returns true and acquires a lock if available
    returns false and does not get suspended if the lock is unavailable

Note about TryLock -
under no circumstances does the tryLock() method block
regardless of the state of the lock, it always returns immediately

TryLock() - use cases
-> real time applications where suspending a thread on a lock() method is unacceptable
ex - video/image processing, high speed/low latency trading systems, user interface applications