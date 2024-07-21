package com.mvc2demo.springboot.thymeleafdemo.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloWorldController {

//    need a controller method to show initial HTML form
//    by default this will support all types of http requests
    @RequestMapping("/showForm")
    public String showForm() {
        return "helloworld-form";
    }

//    need a controller method to process the HTML form
    @RequestMapping("/processForm")
    public String processForm() {
        return "helloworld";
    }

//    need a controller method to read form data and add data to the model

    @RequestMapping("/processFormV2")
    public String nameChangeCase(HttpServletRequest request, Model model) {

//        read the requst parameter from the HTML form
        String name = request.getParameter("studentName");

//        convert the data to all caps
        name = name.toUpperCase();

//        create the message
        String result = "Hello! " + name;

//        add message to the model
        model.addAttribute("message", result);

        return "helloworld";
    }

    @RequestMapping("/processFormV3")
    public String nameChangeCase2(@RequestParam("studentName") String name, Model model) {
        if(name.isEmpty()) {
            throw new IllegalArgumentException("provide the name");
        }

        name = name.toUpperCase();

        String res = "Hello! " + name;

        model.addAttribute("message", res);

        return "helloworld";

    }

    @PostMapping("/processFormV4")
    public String nameChangeCase3(@RequestParam("studentName") String name, Model model) {
        if(name.isEmpty()) {
            throw new IllegalArgumentException("provide the name");
        }

        name = name.toUpperCase();

        String res = "Hello! " + name;

        model.addAttribute("message", res);

        return "helloworld";

    }




}
