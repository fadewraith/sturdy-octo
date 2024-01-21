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
//    public void randomeName(Coach theCoach) {
//        myCoach = theCoach;
//    }

        @Autowired
    public void randomeName(@Qualifier("trackCoach") Coach theCoach) {
        myCoach = theCoach;
    }

    @GetMapping("/dailyworkout")
    public String getDailyWorkout() {
        return myCoach.getDailyWorkout();
    }


}
