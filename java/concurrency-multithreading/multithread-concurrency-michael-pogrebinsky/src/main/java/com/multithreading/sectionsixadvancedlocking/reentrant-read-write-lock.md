ReentrantReadWriteLock - why
Race conditions require -
-> multiple threads sharing a resource
-> at least one thread modifying the resource

solution - complete mutual exclusion
-> regardless of operation (read/write/both)

use case -
Synchronized and ReentrantLock do not allow multiple readers to access a shared resource concurrently
not a big problem in the general case
if we keep the critical sections short, the chances of a contention over a lock are minimal

when to use -
when read operations are predominant
or when the read operations are not as fast
    -> read from many variables
    -> read from a complex ds
mutual exclusion of reading threads negatively impacts the performance

how to use -
ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
Lock readLock = rwLock.readLock();
Lock writeLock = rwLock.writeLock();

readLock.lock();
try {
    // read operation
} finally {
    readLock.unlock();
}

writeLock.lock();
try {
    // modify operation
} finally {
    writeLock.unlock();
}

multiple threads can acquire the readLock simultaneously
only a single thread is allowed to lock a writeLock
mutual exclusion bt readers and writers
    - if a write lock is acquired, no thread can acquire a read lock
    - if at least one thread holds a read lock, no thread can acquire a write lock