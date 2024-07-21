package com.mvc2demo.springboot.thymeleafdemo.controller;

import com.mvc2demo.springboot.thymeleafdemo.model.Student;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class StudentController {

    @Value("${countries}")
    private List<String> countries;

    @Value("${languages}")
    private List<String> languages;

    @Value("${operatingSystems}")
    private List<String> operatingSystems;

    @GetMapping("/showStudentForm")
    public String showForm(Model model) {

//        create a new student object
        Student student = new Student();


//        add student object to the model
        model.addAttribute("student", student);
        model.addAttribute("countries", countries);
        model.addAttribute("languages", languages);
        model.addAttribute("operatingSystems", operatingSystems);


        return "student-form";
    }

    @PostMapping("/processStudentForm")
//    public String showStudentData(@RequestParam("student") Student student, Model model) {
    public String showStudentData(@ModelAttribute("student") Student student) {

        if(student.getFirstName().isEmpty() && student.getLastName().isEmpty()) {
            throw new IllegalArgumentException("student is null");
        }

        String fullName = student.getFirstName() + " " + student.getLastName();

        System.out.println("student is : " + fullName);
//        System.out.println("student: " +  student.getFirstName() + ' ' + student.getLastName());


        return "student-confirmation";
    }

}
