package com.springbootlearning.springcoredemo;

public interface Coach {
    String getDailyWorkout();
}

/*
 * 1. create interface, here it is Coach
 * 2. create class and implement the interface. annotate it with @Component
 * 3. create REST controller. annotate with it @RestController
 * 4. create a constructor in class for injections
 * 5.  define a private field for the dependency, i.e. Coach
 * 6.  define a constructor for dependency injection, use @Autowired (optional)
 * @Autowired annotation tells Spring to inject a dependency, if we have one constructor then @Autowired on constructor is optional
 * 7. create @GetMapping and return the method
 * Behind the scenes, spring framework -
 * Coach theCoach = new CricketCoach(); // theCoach is a dependency or a helpe, for the actual controller
 * DemoController demoController = new DemoController(theCoach); // constructor injection
 * */