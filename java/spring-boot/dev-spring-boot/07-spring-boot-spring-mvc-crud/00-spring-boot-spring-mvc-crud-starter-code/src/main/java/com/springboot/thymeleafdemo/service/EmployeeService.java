package com.springboot.thymeleafdemo.service;

import com.springboot.thymeleafdemo.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAll();

    Employee findById(int theId);

    Employee save(Employee theEmployee);

    Employee deleteById(int theId);

}
