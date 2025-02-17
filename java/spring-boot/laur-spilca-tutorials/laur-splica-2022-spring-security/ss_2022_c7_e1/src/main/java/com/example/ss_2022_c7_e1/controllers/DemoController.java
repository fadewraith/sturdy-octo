package com.example.ss_2022_c7_e1.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/demo1")
    @PreAuthorize("hasAuthority('read')")
    public String demo1() {
        return "Demo1";
    }

    @GetMapping("/demo2")
    @PreAuthorize("hasAnyAuthority('write', 'read')")
    public String demo2() {
        return "Demo2";
    }

}