package com.mandiri.services;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mandiri.constant.ResponseMessage;
import com.mandiri.entities.dtos.PaylaterDetailDto;
import com.mandiri.entities.dtos.PaylaterSaveDto;
import com.mandiri.entities.dtos.PaymentPerMonthDto;
import com.mandiri.entities.models.Installment;
import com.mandiri.entities.models.PaylaterDetail;
import com.mandiri.entities.models.Product;
import com.mandiri.repositories.PaylaterDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
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

    @Transactional
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
        detail.setInstallmentPay(detail.getTransactionAmount()/saveInstallment.getTotalInstallment());

        paylaterDetailRepository.save(detail);

        paylaterDetail.setId(product.getId());
        paylaterDetail.setInstallmentPay(detail.getInstallmentPay());

        return paylaterDetail;
    }

    @Transactional
    public PaylaterDetailDto update(PaymentPerMonthDto perMonthDto){
        String id = perMonthDto.getId();
        paylaterValidation(id);
        PaylaterDetailDto detailDto = paylaterDetailRepository.findAllDetailById(id);

        Installment installment = installmentService.getById(id);
        installment.setId(id);

        paidOffValidation(installment);

        if(perMonthDto.getInstallmentPay().equals(detailDto.getInstallmentPay())){
            installment.setCurrentInstallment(installment.getCurrentInstallment() + 1);
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Payment tidak sesuai");
        }

        installmentService.saveInstallment(installment);
        detailDto.setCurrentInstallment(installment.getCurrentInstallment());

        return detailDto;
    }

    private void paidOffValidation(Installment installment) {
        Integer paidOffTotal = installment.getCurrentInstallment();

        if(paidOffTotal.equals(installment.getTotalInstallment()))
            installment.setStatus("Lunas");
        if(paidOffTotal > installment.getTotalInstallment()){
            throw new ResponseStatusException(HttpStatus.ACCEPTED, "SUDAH LUNAS");
        }
        else
            installment.setStatus("Belum Lunas");
    }

    private void paylaterValidation(String id) {
        if(!paylaterDetailRepository.existsById(id)){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND,
                    ResponseMessage.getResourceNotFound(PaylaterDetail.class.getSimpleName(), id));
        }
    }
}
