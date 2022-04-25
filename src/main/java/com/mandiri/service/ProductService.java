package com.mandiri.service;

import com.mandiri.entity.Product;
import com.mandiri.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Product addProduct(Product product){
        return productRepository.save(product);
    }
    public List<Product> getProducts(){
        return productRepository.findAll();
    }
}
