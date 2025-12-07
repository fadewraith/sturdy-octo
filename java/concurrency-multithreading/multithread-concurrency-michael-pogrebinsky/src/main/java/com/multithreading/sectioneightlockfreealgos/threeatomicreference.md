AtomicReference<T>
AtomicReference(V initialValue)
V get() - Returns the current value
void set(V newValue) - Sets the value to newValue
boolean compareAndSet(V expect, V update) - If the current value equals expect, then set it to update