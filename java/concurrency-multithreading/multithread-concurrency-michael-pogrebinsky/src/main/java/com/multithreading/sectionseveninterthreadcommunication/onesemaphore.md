Can be used to restrict the number of "users" to a particular resource or a group of resources
Unlike the locks that allows only one "user" per resource
The semaphore can restrict any given number of users to a resource
Semaphore semaphore = new Semaphore(NUMBER_OF_PERMITS);
semaphore.acquire(); // if no resource is availablem thread is blocked
semaphore.release(); 

Semaphore - differences with locks
- semaphore doesnt have a notion of owner thread
- many threads can acquire a permit
- the same thread can acquire the semaphore multiple times
- the binary semaphore (initialized with 1) is not reentrant
- semaphore can be releasde by any thread
- even can be released by a thread that hasn't actually acquired it

Semaphore - Producer Consumer
Semaphore full = new Semaphore(0);
Semaphore empty = new Semaphore(1);
Item item = null;

Producer
while (true) {
    empty.acquire();
    item = produceItem();
    full.release();
}

Consumer
while (true) {
    full.acquire();
    consumeItem(item);
    empty.release();
}

Will work this single producer and single consumer

Semaphore - Producer Consumer with Queue
Semaphore full = new Semaphore(0);
Semaphore empty = new Semaphore(CAPACITY);
Queue queue = new ArrayDeque();
Lock lock = new ReentrantLock();

[//]: # (Producer)

[//]: # (while &#40;true&#41; {)

[//]: # (    empty.acquire&#40;&#41;;)

[//]: # (    item = produceItem&#40;&#41;;)

[//]: # (    full.release&#40;&#41;;)

[//]: # (})

[//]: # ()
[//]: # (Consumer)

[//]: # (while &#40;true&#41; {)

[//]: # (    full.acquire&#40;&#41;;)

[//]: # (    consumeItem&#40;item&#41;;)

[//]: # (    empty.release&#40;&#41;;)

[//]: # (})

Producer -
while (true) {
    Item item = produce();
    empty.acquire();
    lock.lock();
    queue.offer(item);
    lock.unlock();
    full.release();
}

Consumer -
while (true) {
    full.acquire();
    lock.lock();
    Item item = queue.poll();
    lock.unlock();
    consumeItem(item);
    empty.release();
}
