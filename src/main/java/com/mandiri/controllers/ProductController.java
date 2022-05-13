package com.mandiri.controllers;

import com.mandiri.entities.models.Product;
import com.mandiri.entities.models.ProductDetail;
import com.mandiri.services.ProductDetailService;
import com.mandiri.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController{
    @Autowired
    ProductService productService;

    @Autowired
    ProductDetailService productDetailService;

    @PostMapping
    public Product saveProduct(@RequestBody Product product){
        return productService.addProduct(product);
    }

    @PostMapping("/detail")
    public ProductDetail saveProductDetail(@RequestBody ProductDetail productDetail){
        return productDetailService.addProduct(productDetail);
    }

    @GetMapping("/detail")
    public List<ProductDetail> getAll(){
        return productDetailService.listAll();
    }
}
