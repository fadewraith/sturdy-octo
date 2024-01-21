package com.springbootlearning.springcoredemo.rest;

import com.springbootlearning.springcoredemo.common.Coach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    // define a private field for the dependency
    private Coach myCoach;
    private Coach anotherCoach;

// define a constructor for dependency injection
//// @Autowired annotation tells Spring to inject a dependency, if we have one constructor then @Autowired on constructor is optional
//    @Autowired
//    public DemoController(Coach theCoach) {
//        myCoach = theCoach;
//    }

//    use @Qualifier, if we have multiple beans and the Qualifier name should be as per the class name except the firs letter will be in small
//    @Autowired
//    public DemoController(@Qualifier("baseballCoach") Coach theCoach) {
//        myCoach = theCoach;
//    }

//    @Autowired
//    public DemoController(@Qualifier("cricketCoach") Coach theCoach) {
//        System.out.println("In constructor: "+ getClass().getSimpleName());
//        myCoach = theCoach;
//    }

    @Autowired
    public DemoController(@Qualifier("cricketCoach") Coach theCoach,
                          @Qualifier("cricketCoach") Coach theAnotherCoach) {
        System.out.println("In constructor: "+ getClass().getSimpleName());
        myCoach = theCoach;
        anotherCoach = theAnotherCoach;
    }

//    @Autowired
//    public void randomeName(Coach theCoach) {
//        myCoach = theCoach;
//    }

//        @Autowired
//    public void randomeName(@Qualifier("trackCoach") Coach theCoach) {
//        myCoach = theCoach;
//    }

    @GetMapping("/dailyworkout")
    public String getDailyWorkout() {
        return myCoach.getDailyWorkout();
    }

    @GetMapping("/check")
    public String check() {
//        this will be true, because by default spring creates one bean and all the Qualifiers point to that bean in memory
//        return "Comparing beans: myCoach == anotherCoach, " + (myCoach == anotherCoach);
//        this will point to false, becasue we have created Prototype in CricketCoach component
        return "Comparing beans: myCoach == anotherCoach, " + (myCoach == anotherCoach);
    }


}
