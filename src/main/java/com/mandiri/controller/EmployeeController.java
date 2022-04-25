package com.mandiri.controller;

import com.mandiri.entity.Employee;
import com.mandiri.generator.PDFGenerator;
import com.mandiri.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    PDFGenerator pdfGenerator;

    @PostMapping("/register")
    public Employee saveEmployee(@RequestBody Employee employee){
        return employeeService.addEmployee(employee);
    }

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable String id){
        return employeeService.getEmployee(id);
    }

    @GetMapping("/{id}/export")
    public void getEmployeePdf(@PathVariable String id){
        pdfGenerator.generatePdfReport(id);
    }
}
