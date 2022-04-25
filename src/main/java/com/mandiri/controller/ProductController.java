package com.mandiri.controller;

import com.mandiri.entity.Product;
import com.mandiri.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/register")
    public Product saveProduct(@RequestBody Product product){
        return productService.addProduct(product);
    }

    @GetMapping("/list")
    public List<Product> getProducts(){
        return productService.getProducts();
    }
}
