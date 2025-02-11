package com.example.ss_2022_c3_e1.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
//  start -> filter -> authentication manager -> authentication provider (if needed -> UserdetailService and PasswordEncoder ) -> then to secutirycontextholder -> if neeeded -> authorization -> then to controller

  @GetMapping("/demo")
  public String demo() {
    return "Demo";
  }
}

/**
 * In non reactive application -
 * each request creates a new thread and each thread when uses securitycontext(uses threadlocal provided by JDK)  which makes sure each thread sees only what they stores
 *
 * */