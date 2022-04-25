package com.mandiri.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mst_employee")
@Setter
@Getter
public class Employee {

    @Id
    @GeneratedValue(generator = "uuid-generator")
    @GenericGenerator(name = "uuid-generator", strategy = "uuid")
    private String empId;
    private String empName;
    private Double empSal;
    private String empDept;

    public Employee(){

    }

}
