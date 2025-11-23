package eighteenfalsesharingdemo;

public class Counter2 {

    // @Contended is a hint to the JVM to place the field in a separate cache line
    // to prevent false sharing
    @jdk.internal.vm.annotation.Contended
    public volatile long count1 = 0;
    // padding bytes
    public volatile long count2 = 0;

}
