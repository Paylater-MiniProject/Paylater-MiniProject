package com.mandiri.services;

import com.mandiri.entities.models.Product;
import com.mandiri.entities.models.ProductDetail;
import com.mandiri.repositories.ProductDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductDetailService {
    @Autowired
    ProductDetailRepository productDetailRepository;

    @Transactional
    public ProductDetail addProduct(ProductDetail productDetail){
        return productDetailRepository.save(productDetail);
    }

    public List<ProductDetail> listAll(){
        return productDetailRepository.findAll();
    }

    public ProductDetail getById(String id){
        return productDetailRepository.findById(id).get();
    }
}
