package com.mandiri.repositories;

import com.mandiri.entities.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    @Query(value = "SELECT * FROM mst_employee t WHERE t.emp_Id=?1", nativeQuery = true)
    public Employee findAllBy(String id);

}
