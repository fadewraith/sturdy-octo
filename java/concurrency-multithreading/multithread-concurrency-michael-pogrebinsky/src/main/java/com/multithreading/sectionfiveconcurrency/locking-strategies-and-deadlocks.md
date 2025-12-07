conditions for deadlock -
mutual exclusion - only one thread can have exclusive access to a resource
hold and wait - at least one thread is holding a resource and is waiting for another resource
non - preemptive allocation - a resource is released only after the thread is done using it
circular wait - a chain of at least 2 threds each one is holding one resourec and waiting for another resource

solution s -
avoid circular wait - enforce a strict order in lock acquisition

other techniques -
deadlock detection - watchdog
thread interruptiopn (not possible with synchronized)
tryLock operations (not possible with synchronized)

Locking strategies - coarse grained, fine grained
deadlock -
solved by avoiding circular wait and hold
lock resources in the same order, everywhere