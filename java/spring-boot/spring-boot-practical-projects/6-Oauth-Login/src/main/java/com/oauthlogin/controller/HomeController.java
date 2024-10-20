package com.oauthlogin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<?> home() {
        return new ResponseEntity<>("welcome to o-auth demo", HttpStatus.OK);
    }
}
