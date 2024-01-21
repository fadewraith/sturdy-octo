package com.springbootlearning.springcoredemo.common;

import org.springframework.stereotype.Component;

@Component // manually do this, it marks the class as a Spring Bean, makes it available for dependency injection
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) // will create new object instance for each injection
public class CricketCoach implements Coach {

    public CricketCoach() {
        System.out.println("In constructor: "+ getClass().getSimpleName());
    }


    @Override
    public String getDailyWorkout() {
        return "Cricket Coach implemented Coach -:)";
    }
}