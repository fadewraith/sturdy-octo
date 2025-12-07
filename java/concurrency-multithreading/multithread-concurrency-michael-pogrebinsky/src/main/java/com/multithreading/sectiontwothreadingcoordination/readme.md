Thread Termination - why and when ?

Thread consume resources -
    Memory and kernel resources
    CPU cycles and cache memory
If a thread finished its work, but the application is still running, we want to clean up the thread's resources
If a thread is misbehaving, we want to stop it
By default, the application will not stop as long as at least one thread is still running

Thread.interrupt()
When can we interrupt a thread ? 
If the thread is executing a method that throws an InterruptedException
If the thread's code is handling the interrupt signal explicitly


=============================


Daemon Threads - Background threads that do not prevent the application from exiting if the main thread terminates
Background tasks, that should not block out application from terminating

Thread coordination - why do we need it
diff threads run independently
order of execution is out of our control

public final void join()