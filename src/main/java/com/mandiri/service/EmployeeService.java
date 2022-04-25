package com.mandiri.service;

import com.mandiri.entity.Employee;
import com.mandiri.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService{

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee addEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public Employee getEmployee(String id) {
        return employeeRepository.findAllBy(id);
    }
}
