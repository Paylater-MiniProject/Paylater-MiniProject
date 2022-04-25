package com.mandiri.services;

import com.mandiri.entities.dtos.PaylaterDetailDto;
import com.mandiri.entities.dtos.PaylaterSaveDto;
import com.mandiri.entities.models.Installment;
import com.mandiri.entities.models.PaylaterDetail;
import com.mandiri.entities.models.Product;
import com.mandiri.repositories.PaylaterDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
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

    public PaylaterSaveDto savePayment(PaylaterSaveDto paylaterDetail) {
        Installment saveInstallment = new Installment();
        saveInstallment.setTotalInstallment(paylaterDetail.getTotalInstallment());
        saveInstallment.setCurrentInstallment(0);
        saveInstallment.setStatus("Belum Lunas");

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, saveInstallment.getTotalInstallment());

        saveInstallment.setDueDate(cal.getTime());

        Installment installment = installmentService.saveInstallment(saveInstallment);

        Product saveProduct = new Product();
        saveProduct.setId(installment.getId());
        saveProduct.setName(paylaterDetail.getProductName());
        saveProduct.setPrice(paylaterDetail.getPrice());

        Product product = productService.addProduct(saveProduct);

        PaylaterDetail detail = new PaylaterDetail();
        detail.setId(product.getId());
        detail.setCreatedTime(new Date(System.currentTimeMillis()));
        detail.setHandlingFee(paylaterDetail.getQuantity()* paylaterDetail.getPrice()*0.11);
        detail.setQuantity(paylaterDetail.getQuantity());
        detail.setTransactionAmount((paylaterDetail.getQuantity()* paylaterDetail.getPrice()) + detail.getHandlingFee());

        paylaterDetailRepository.save(detail);

        paylaterDetail.setId(product.getId());

        return paylaterDetail;
    }
}
