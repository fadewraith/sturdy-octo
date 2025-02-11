package com.example.ss_2022_c2_e1.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

  @GetMapping("/demo")
  public String demo() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    authentication.getAuthorities().forEach(System.out::println);
    return "Demo";
  }
}
