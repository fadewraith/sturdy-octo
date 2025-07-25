package gfgjavacourse.basics2advanced;

public class OOPSDemo {

    /**
     * default -> within package
     * protected -> within the package and child classes of other packages
     * public -> anywhere
     * private -> within the class
     * If you dont create constructor, by default java creates constructor, but if you create constructor and you dont pass any value(if its a initializtion constructor, then it will throw an error)
     * */

    /**
     * Inheritance
     * implements is a relationship
     * code reusability
     * used in overriding
     * used in abstract classes and interfaces
     * Object class is root of the inheritance heirarchy in any java code, it is ancestor of all classes -
     * clone(), equals(), hashCode(), toString(), etc...
     * */

    /**
     * super k/w -
     * to call parent class constructor
     * to access data member and methods of the parent class
     * */

    /**
     * Single, Multilevel, Hierarchical, Hybrid, Multiple(not allowed with classes in java)
     * Hybrid(Hierarchical + Multilevel, in classes not allowed, in interfaces allowed)
     * Overriding works with multilevel inheritance
     * static methods are not overridden
     * */

    /**
     * Polymorphism
     * 2 types -
     * Compile time or static (method overloading)
     * Run time or Dynamic (Method overriding)
     * */

    /**
     * Abstraction -
     * No instances
     * references can be there
     * can have constructor
     * a class can be abstract without any abstraction method
     * we can have static methods
     * if a class has at least one abstract method, the class must be made abstract
     * if a subclass doest not implement all abstract methods, thus the derived class must also be abstract
     * */

    /**
     * Interfaces -
     * all members are public
     * all data members public static final
     * all methods are abstract by default
     * we can also have default and static methods
     * a class can implement more than 1 interfaces
     * an interface can extend more than 1 interfaces
     * */

    /**
     * Diff bt abstract classes vs interfaces
     * similarities - used to achieve abstraction, instance cannot be created, reference can be created
     * diff - interfaces cant have constructors, all data members are public static final, all methods are public, multiple implementations and multiple inheritance
     * an abstract class can implement an interface
     * a normal class can extend an abstract class and can implement one or more interfaces
     * */

    /**
     * When to use abstract classes
     * 1. when we have a logical is a relationship like shape and rectangle, employee and salesEmployee etc.
     * 2. when we need protected, private or default methods
     * 3. when we need non static and non final data members
     * */

    /**
     * When to use interfaces ?
     * 1. when we need a functionality(or a set of functionalities) to be implemented by possibly unrelated classes
     * 2. multiple implementations
     * 3. multiple inheritance
     * */
}
