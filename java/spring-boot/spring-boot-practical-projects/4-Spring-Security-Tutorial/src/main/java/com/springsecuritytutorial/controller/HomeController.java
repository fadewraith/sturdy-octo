package com.springsecuritytutorial.controller;

import com.springsecuritytutorial.dto.UserRequest;
import com.springsecuritytutorial.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<?> getDetails(HttpServletRequest req) {
//        String id = req.getSession().getId();
//        return new ResponseEntity<>("hello, welcome to custom spring security, id = " + id, HttpStatus.OK);
        return new ResponseEntity<>("hello, welcome to custom spring security", HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserDetails(HttpServletRequest req) {
        return new ResponseEntity<>(userService.getUserDetails(), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest userRequest) {
        String token = userService.login(userRequest);
        if(token == null) {
            return new ResponseEntity<>("invalid credentials", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
