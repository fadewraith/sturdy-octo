package com.springprofiledemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Value("${my.config}")
    private String config;

    @GetMapping("/")
    public ResponseEntity<?> getConfig() {
        return new ResponseEntity<>(config, HttpStatus.OK);
    }
}
