AtomicInteger -
int initialValue = 0;
AtomicInteger atomicInteger = new AtomicInteger(initialValue);

atomicInteger.get() - returns the current value
atomicInteger.set(int newValue) - sets the value to newValue
atomicInteger.getAndSet(int newValue) - gets the current value and sets the value to newValue
atomicInteger.incrementAndGet() - increments the value by 1 and returns the new value
atomicInteger.getAndIncrement() - gets the current value, increments the value by 1 and returns the old value
atomicInteger.decrementAndGet() - decrements the value by 1 and returns the new value
atomicInteger.getAndDecrement() - gets the current value, decrements the value by 1 and returns the old value
atomicInteger.addAndGet(int delta) - adds the delta to the value and returns the new value
atomicInteger.getAndAdd(int delta) - gets the current value, adds the delta to the value and returns the old value


Pros & Cons -
Pros
- Simplicity
- No need to worry for locks or synchronization
- No race conditions or data races

Cons
- Only the operation itself is atomic
- There's still race condition between 2 separate atomic operations