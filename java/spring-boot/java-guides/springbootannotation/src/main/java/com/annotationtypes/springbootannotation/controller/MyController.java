package com.annotationtypes.springbootannotation.controller;

import org.springframework.context.annotation.Configuration;

@Configuration
public class MyController {

    public String hello() {
        return "Hello Controller";
    }
}
