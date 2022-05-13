package com.mandiri.repositories;

import com.mandiri.entities.models.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailRepository extends JpaRepository<ProductDetail,String> {

}
