Atomic Operations -
-> All reference assignments are atomic
-> We can get and set references to objects atomically - getters and setters

Object a = new Object();
Object b = new Object();
a = b; // atomic

-> All assignments to primitive types are safe except long and double
-> reading from, and writing to - int,short,byte,float,char,boolean
long & double are 64 bits long, java doesn't provides any guarantee even if you have 64 bits computer, long & doubtle will actually be completed in 2 operations by the cpu, 1 rt to the lower 32 bits and other to the upper 32 bits

-> Assignments to long and double if declared volatile -
volatile double x = 1.0;
volatile double y = 9.0;
x = y; // atomic

-> Classes in the java.util.concurrent.atomic
-> Those are more advanced operations


use case -> metrics
System.currentTimeMillis();