package com.restcrud.demo.rest;

import com.restcrud.demo.entity.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {

//    define a field
    private List<Student> theStudents;
//    define @PostConstruct to load the student data ... only once!

    @PostConstruct
    public void loadData() {
        theStudents = new ArrayList<>();
        theStudents.add(new Student("John", "Doe"));
        theStudents.add(new Student("Hello", "World"));
        theStudents.add(new Student("Mary", "Smith"));

    }

//    define endpoint for "/students" - return a list of students
    @GetMapping("/students")
    public List<Student> getStudents() {
        return theStudents;
    }

//    define endpoint or "/students/{studentId}" - return student at index

    @GetMapping("/students/{studentId}") // by default api pathvariable and param pathvariable must match
    public Student getStudent(@PathVariable int studentId) {
        // just index into the list ...

//        check the StudentId again list size
        if((studentId > theStudents.size()) || (studentId < 1)) {
            throw new StudentNotFoundException("Student id not found - " + studentId);
        }

        return theStudents.get(studentId - 1);


    }




}
