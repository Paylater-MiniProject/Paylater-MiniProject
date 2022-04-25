package com.mandiri.services;

import com.mandiri.entities.dtos.PaylaterDetailDto;
import com.mandiri.entities.models.Installment;
import com.mandiri.entities.models.PaylaterDetail;
import com.mandiri.entities.models.Product;
import com.mandiri.repositories.PaylaterDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PaylaterDetailService{
    @Autowired
    PaylaterDetailRepository paylaterDetailRepository;

    @Autowired
    InstallmentService installmentService;

    @Autowired
    ProductService productService;

    public PaylaterDetailDto getAllPayment(String id){
        return paylaterDetailRepository.findAllDetailById(id);
    }

    public PaylaterDetailDto savePayment(PaylaterDetailDto paylaterDetail) {
        Installment saveInstallment = new Installment();
        saveInstallment.setTotalInstallment(paylaterDetail.getTotalInstallment());
        saveInstallment.setPeriod(paylaterDetail.getPeriod());
        saveInstallment.setStatus("Belum Lunas");
        saveInstallment.setDueDate(new Date());

        Installment installment = installmentService.saveInstallment(saveInstallment);

        Product saveProduct = new Product();
        saveProduct.setId(installment.getId());
        saveProduct.setName(paylaterDetail.getProductName());
        saveProduct.setPrice(paylaterDetail.getPrice());

        Product product = productService.addProduct(saveProduct);

        PaylaterDetail detail = new PaylaterDetail();
        detail.setId(product.getId());
        detail.setCreatedTime(new Date(System.currentTimeMillis()));
        detail.setHandlingFee(paylaterDetail.getHandlingFee());
        detail.setTotalProduct(paylaterDetail.getTotalProduct());
        detail.setTransactionAmount(paylaterDetail.getTransactionAmount());

        paylaterDetailRepository.save(detail);

        paylaterDetail.setId(product.getId());

        return paylaterDetail;
    }
}
