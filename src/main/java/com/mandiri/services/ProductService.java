package com.mandiri.services;

import com.mandiri.entities.models.Product;
import com.mandiri.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Transactional
    public Product addProduct(Product product){
        return productRepository.save(product);
    }

    public Optional<Product> getById(String id){
        return productRepository.findById(id);
    }
}
