package defaultmethodsdemo;

@FunctionalInterface
public interface Parent {
// exactly single abstract method
  public void sayHello();
  default void sayBye() {};
  public static void sayOk() {}
}

// inheritance in functional interface
@FunctionalInterface
public interface Child extends Parent {
  // if empty, then also functional interface
  // if we declare any method here, it will throw error, but if we delcare the sayHello method here, it wont throw error
  public void sayHello();
  // any num of default & static methods can be declared here
}

