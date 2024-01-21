package com.springboot.employee.cruddemo.dao;

import com.springboot.employee.cruddemo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//@RepositoryRestResource(path="members") // localhost:8080/test-api/members
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
//    this is Step1
//    where Employee is the entity type and Integer is the primary key
//    that's it ... no need to write any code
}
