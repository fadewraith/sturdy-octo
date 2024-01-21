package com.springbootlearning.springcoredemo.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component // manually do this, it marks the class as a Spring Bean, makes it available for dependency injection
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) // will create new object instance for each injection
public class CricketCoach implements Coach {

    public CricketCoach() {
        System.out.println("In constructor: "+ getClass().getSimpleName());
    }

//    define our init method
    @PostConstruct
    public void doMyStartStuff() {
        System.out.println("In doMyStartStuff(): "+ getClass().getSimpleName());
    }

//    define our destroy method
    @PreDestroy
    public void doMyCleanStuff() {
        System.out.println("In doMyCleanStuff: "+ getClass().getSimpleName());
    }
    @Override
    public String getDailyWorkout() {
        return "Cricket Coach implemented Coach -:)";
    }
}