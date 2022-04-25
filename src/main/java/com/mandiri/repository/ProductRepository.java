package com.mandiri.repository;

import com.mandiri.entity.Employee;
import com.mandiri.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

}
