package com.annotationtypes.springbootannotation.repository;

import org.springframework.stereotype.Repository;

@Repository
public class MyRepository {
    public String hello() {
        return "Hello Repository";
    }
}
