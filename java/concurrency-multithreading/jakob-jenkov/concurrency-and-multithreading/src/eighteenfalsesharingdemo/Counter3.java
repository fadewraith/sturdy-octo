package eighteenfalsesharingdemo;

// @Contended is a hint to the JVM to place the field in a separate cache line
// to prevent false sharing
@jdk.internal.vm.annotation.Contended
public class Counter3 {

    public volatile long count1 = 0;
    // padding bytes

    public volatile long count2 = 0;
    // padding bytes

    public volatile long count3 = 0;
    // padding bytes

}
