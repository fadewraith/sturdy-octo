Another type of blocking calls -
Blocking IO (Input/Output)

TThread-Per-Task Threading Model

it will not give us the optimal -
performance, cpu utilization

allows processing thousands of tasks concurrently
issues -
can create a limited number of threads
too many threads - application crashes
too few threads - poor performance

Thread per task threading model - issues
does not give us optimal performance
when a thread is blocking on IO, it cannot be used
requires us to allocate more threads
consuming more resources
adding context switch overhead

Tread per code with non blocking IO

Non blocking IO OS methods are very hard to work with
JDK provides only a thin layer of abstraction
most projects use third party libraries like -
netty, vert.x, webflux, etc.