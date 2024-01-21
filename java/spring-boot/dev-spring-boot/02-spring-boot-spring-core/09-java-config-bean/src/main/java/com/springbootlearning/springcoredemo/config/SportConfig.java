package com.springbootlearning.springcoredemo.config;

import com.springbootlearning.springcoredemo.common.Coach;
import com.springbootlearning.springcoredemo.common.SwimCoach;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SportConfig {

    @Bean("aquatic") // custom bean id
    public Coach swimCoach() { // the bean id defaults to the method name
        return new SwimCoach();
    }
}
