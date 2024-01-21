package com.springbootlearning.cruddemo.dao;

import com.springbootlearning.cruddemo.entity.Student;

import java.util.List;

//Step - 1 - create package dao - create interface, create DAO class
//then add @Repository in the DAO class
// step 3 - update main application
public interface StudentDAO {

    void save(Student theStudent);

//    reading object with JPA
    Student findById(Integer id);

    List<Student> findAll();

    List<Student> findByLastName(String theLastName);

    void update(Student theStudent);

    void delete(Integer id);

    int deleteAll();
}
