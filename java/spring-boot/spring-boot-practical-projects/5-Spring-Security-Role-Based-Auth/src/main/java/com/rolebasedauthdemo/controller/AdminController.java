package com.rolebasedauthdemo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @PostMapping("/home")
    public ResponseEntity<?> saveProduct() {
        String msg = "Admin :: Dashboard Page";
        return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }

}
