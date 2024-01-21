package com.springbootlearning.springcoredemo;

import org.springframework.stereotype.Component;

@Component // manually do this, it marks the class as a Spring Bean, makes it available for dependency injection
public class CricketCoach implements Coach {
    @Override
    public String getDailyWorkout() {
        return "Practice fast running for 15 minutes!!!";
    }
}