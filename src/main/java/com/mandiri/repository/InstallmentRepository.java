package com.mandiri.repository;

import com.mandiri.entity.Employee;
import com.mandiri.entity.Installment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstallmentRepository extends JpaRepository<Installment, String> {

}
