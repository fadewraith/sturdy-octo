package com.example.ss_2022_c21_e3.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/demo")
    public String demo() {
        return "Demo!";
    }

    @PostMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

}