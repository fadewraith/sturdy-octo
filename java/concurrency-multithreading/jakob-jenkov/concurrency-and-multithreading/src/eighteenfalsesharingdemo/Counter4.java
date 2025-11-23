package eighteenfalsesharingdemo;

public class Counter4 {

    // @Contended("group1") is a hint to the JVM to place the field in a separate cache line
    // to prevent false sharing
    // group1 and group2 are different groups
    // so they will be placed in different cache lines
    // and false sharing will not occur


    @jdk.internal.vm.annotation.Contended("group1")
    public volatile long count1 = 0;

    @jdk.internal.vm.annotation.Contended("group1")
    public volatile long count2 = 0;

    @jdk.internal.vm.annotation.Contended("group2")
    public volatile long count3 = 0;

}
