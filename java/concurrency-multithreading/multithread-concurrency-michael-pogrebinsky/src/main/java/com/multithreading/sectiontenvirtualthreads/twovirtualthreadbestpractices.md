- Never create fixed-size pools of virtual threads. JVM already creates an internal pool of platform threads for us and that's coz platform threads are expensive and limited resource. JDK takes care of all these complexities.
- Preferred way to use virtual threads is using -
  - Executors.newVirtualThreadPerTaskExecutor()
- Virtual threads are always daemon threads
  - virtualThread.setDaemon() - throws an exception
- Virtual threads always have default priority
  - virtualThread.setPriority() - doesnt do anything


Observability & Debugging
- Career threads are hidden from us
- setting breakpoints/inspecting the thread state is the same as of platform threads
- Important note - Virtual threads arent visible in thread dump