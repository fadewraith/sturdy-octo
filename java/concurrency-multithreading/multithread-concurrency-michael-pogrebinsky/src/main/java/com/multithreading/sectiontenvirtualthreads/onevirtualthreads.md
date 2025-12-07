Intro to virtual threads -
- newer type of thread, introduced in JDK 21
- To use it, please upgrade to JDK 21

There is a JVM -> OS -> CPU
Virtual Threads in JVM mounts to Platform Thread in JVM, which connects to OS Thread

Platform threads -
- are expensive
- have fixed size stack memory
- map 1-to-1 to OS threads