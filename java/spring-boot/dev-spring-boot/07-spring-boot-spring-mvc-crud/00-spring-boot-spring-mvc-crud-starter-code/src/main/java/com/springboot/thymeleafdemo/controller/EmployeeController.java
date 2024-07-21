package com.springboot.thymeleafdemo.controller;

import com.springboot.thymeleafdemo.entity.Employee;
import com.springboot.thymeleafdemo.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;

//    one controller, Autowired is optional
    private EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

//    add mapping for "/list"
    @GetMapping("/list")
    public String listEmployees(Model model) {
//                get the employees from db
        List<Employee> employees = employeeService.findAll();

//        add to the spring model
        model.addAttribute("employees", employees);

        return "employees/list-employees";
    }

    @GetMapping("/showFormToAdd")
    public String showFormToAdd(Model model) {
//        create model attribute to bind the form data
        Employee employee = new Employee();
        model.addAttribute("employee", employee);

        return "employees/employee-form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {

//        save the employee
        employeeService.save(employee);

//        use a redirect to prevent duplicate submissions
        return "redirect:/employees/list";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("employeeId") int id, Model model) {
        Employee employee = employeeService.findById(id);
        model.addAttribute("employee", employee);

        return "employees/employee-form";
    }

    @GetMapping("/deleteEmployee")
    public String deleteEmployee(@RequestParam("employeeId") int id) {
        employeeService.deleteById(id);

        return "redirect:/employees/list";
    }

}
