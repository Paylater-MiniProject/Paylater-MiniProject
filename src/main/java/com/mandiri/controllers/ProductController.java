package com.mandiri.controllers;

import com.mandiri.entities.models.Product;
import com.mandiri.services.ProductService;
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
