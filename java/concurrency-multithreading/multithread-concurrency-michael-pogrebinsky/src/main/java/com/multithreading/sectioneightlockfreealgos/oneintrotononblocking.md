Deadlocks -
deadlocks are generally unrecoverable
can bring the application to a complete halt
more locks in the application, the higher the chances for a deadlock

slow critical section -
multiple threads using the same lock
one thread holds the lock for very long
that thread will slow down all the other threads
all threads become as slow as the slowest thread

priority inversion -
two threads sharing a lock -
- low priority thread (document saver)
- high priority thread (UI)
low priority thread acquires the lock, and is preempted (scheduled out)
high priority thread cannot progress because of the low priority thread is not scheduled to release the lock

Thread not releasing a lock (kill tolerance)
- Thread dies, gets interrupted or forgets to release a lock
- Leaves all thread hanging forever
- Unrecoverable, just like a deadlock
- To avoid, developers need to write more complex code

Performance -
Performance overhead in having contention over a lock
    Thread A acquires a lock
    Thread B tries to acquire a lock and gets blocked
    Thread B is scheduled out (context switch)
    Thread B is scheduled back (context switch)
Additional overhead may not be noticeable for most applications
But for latency sensitive applications, this overhead can be significant

Lock free techniques -
Why did we need locks ?
    Multiple thread accessing shared resources
    Atleast one thread is modifying the shared resources
    Non atomic operations

Non-Atomic operations - Reason
Non atomic operation on one shared resource
A single java operation turns into one or more hardware operations
Ex - counter++ turns into 3 h/w instructions:
    Read count
    Calculate new value
    Store new value to count

Lock Free soln - breakdown
Utilize operations which are guaranteed to be one h/w operation
A single h/w operations is 
    atomic by definition
    thread safe
