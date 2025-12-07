Race Condition -
Condition when multiple threads are accessing a shared resource
At least one thread is modifying the shared resource
The timing of threads scheduling may cause incorrect results

Soln -
Identificaiton of the critical section where the race condition is happening
Protection of the critical section by a synchronized block

data race prob -
compiler and cpu may execute the instructions out of order to optimize performance and utilization, they will do so while maintaining the logical correctness of the code, out of order execution by the compiler and cpu are imp features to speed up the code
compiler rearranges instructions for better - branch prediction, vectorization, prefetching instructions
cpu re arrange instructions for better hardware units utilization

data race - solutions
establish a happens before semantics by one of these methods -
synchronization of methods which modify shared variables
declaration of shared variables with the volatile k/w

Synchronized - solves both race condition and data race, but has a performance penalty
volatile - solves race condition for read/write from/to long and double variables
           solves all data racees by guaranteeing order

Rule of thumb -
every shared variable (modified by at least one thread) should be either -
- guarded by a synchronized block (or any type of lock) OR
- declared as volatile