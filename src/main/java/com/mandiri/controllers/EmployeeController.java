package com.mandiri.controllers;

import com.mandiri.entities.models.Employee;
import com.mandiri.generator.PDFGenerator;
import com.mandiri.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
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
