package com.example.ss_2022_c19_e1.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {

  @GetMapping("/")
  public String index() {
    return "index.html";
  }

}