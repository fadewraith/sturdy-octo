package com.genericresponseapp.controller;

import com.genericresponseapp.util.CommonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class HomeController {

    @GetMapping("/company")
    public ResponseEntity<?> getCompany() {
        List<String> company = Arrays.asList("Microsoft", "Google", "IBM", "TCS", "Wipro");
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @GetMapping("/companies")
    public ResponseEntity<?> getCompanies() {
        List<String> company = Arrays.asList("Microsoft", "Google", "IBM", "TCS", "Wipro");
        return CommonUtil.createBuildResponse(company, "success", HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUsers() {
        List<String> users = Arrays.asList("John", "Doe", "Hello", "World", "Spring");
        return CommonUtil.createBuildResponse(users, "success", HttpStatus.OK);
    }

    @GetMapping("/save-user")
    public ResponseEntity<?> saveUser() {
        try {
            String name = "hello";
            name.toLowerCase();
            return CommonUtil.createBuildResponse("saved success", "success", HttpStatus.CREATED);
        } catch (Exception e) {
            return CommonUtil.createBuildResponse(e.getMessage(), "failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
