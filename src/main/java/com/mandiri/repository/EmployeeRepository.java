package com.mandiri.repository;

import com.mandiri.entity.Employee;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    @Query(value = "SELECT * FROM mst_employee t WHERE t.emp_Id=?1", nativeQuery = true)
    public Employee findAllBy(String id);

}
