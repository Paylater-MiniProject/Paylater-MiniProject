package com.mandiri.services;

import com.mandiri.constant.ResponseMessage;
import com.mandiri.entities.dtos.PaylaterDetailDto;
import com.mandiri.entities.dtos.PaylaterSaveDto;
import com.mandiri.entities.dtos.PaymentPerMonthDto;
import com.mandiri.entities.models.Installment;
import com.mandiri.entities.models.PaylaterDetail;
import com.mandiri.entities.models.Product;
import com.mandiri.entities.models.ProductDetail;
import com.mandiri.repositories.PaylaterDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class PaylaterDetailService{
    @Autowired
    PaylaterDetailRepository paylaterDetailRepository;

    @Autowired
    InstallmentService installmentService;

    @Autowired
    ProductService productService;

    @Autowired
    ProductDetailService productDetailService;

    public PaylaterDetailDto getAllPaymentById(String id){
        return paylaterDetailRepository.findAllDetailById(id);
    }

    @Transactional
    public PaylaterSaveDto savePayment(PaylaterSaveDto paylaterDetail) {
        Installment saveInstallment = toSaveInstallmentClass(paylaterDetail);
        Installment installment = installmentService.saveInstallment(saveInstallment);

        Product product = toSaveProductClass(paylaterDetail, installment);

        PaylaterDetail detail = toSavePaylaterDetailClass(paylaterDetail, saveInstallment, product);

        paylaterDetail.setId(product.getId());
        paylaterDetail.setInstallmentPay(detail.getInstallmentPay());


        return paylaterDetail;
    }

    private PaylaterDetail toSavePaylaterDetailClass(PaylaterSaveDto paylaterDetail, Installment saveInstallment, Product product) {
        PaylaterDetail detail = new PaylaterDetail();
        detail.setId(product.getId());
        detail.setCreatedTime(new Date(System.currentTimeMillis()));
        ProductDetail productDetail = saveProductDetail(paylaterDetail);
        detail.setHandlingFee(paylaterDetail.getQuantity()* productDetail.getPrice()*0.11);
        detail.setQuantity(paylaterDetail.getQuantity());
        detail.setTransactionAmount((paylaterDetail.getQuantity()* productDetail.getPrice()) + detail.getHandlingFee());
        detail.setInstallmentPay(detail.getTransactionAmount()/ saveInstallment.getTotalInstallment());

        paylaterDetailRepository.save(detail);
        return detail;
    }

    private Product toSaveProductClass(PaylaterSaveDto paylaterDetail, Installment installment) {
        Product saveProduct = new Product();
        saveProduct.setId(installment.getId());
        ProductDetail productDetail = saveProductDetail(paylaterDetail);
        productDetailService.addProduct(productDetail);
        Product product = productService.addProduct(saveProduct);
        return product;
    }

    private ProductDetail saveProductDetail(PaylaterSaveDto paylaterDetail) {
        ProductDetail saveProductDetail=productDetailService.getById(paylaterDetail.getProductId());
        ProductDetail productDetail= new ProductDetail();

        productDetail.setName(saveProductDetail.getName());
        productDetail.setPrice(saveProductDetail.getPrice());
        productDetail.setDescription(saveProductDetail.getDescription());
        return productDetail;
    }

    private Installment toSaveInstallmentClass(PaylaterSaveDto paylaterDetail) {
        Installment saveInstallment = new Installment();
        saveInstallment.setTotalInstallment(paylaterDetail.getTotalInstallment());
        saveInstallment.setCurrentInstallment(1);
        saveInstallment.setStatus("Belum Lunas");
        saveInstallment.setIsDone(false);

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, saveInstallment.getTotalInstallment());

        saveInstallment.setDueDate(cal.getTime());
        return saveInstallment;
    }

    @Transactional
    public PaylaterDetailDto update(PaymentPerMonthDto perMonthDto){
        String id = perMonthDto.getId();
        paylaterValidation(id);
        PaylaterDetailDto detailDto = paylaterDetailRepository.findAllDetailById(id);

        Installment installment = installmentService.getById(id);
        installment.setId(id);
        if(installment.getIsDone().equals(true)){
            throw new ResponseStatusException(HttpStatus.ACCEPTED, "SUDAH LUNAS");
        }

        if(perMonthDto.getInstallmentPay().equals(detailDto.getInstallmentPay())){
            paidOffValidation(installment);
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Payment tidak sesuai");
        }
        installmentService.saveInstallment(installment);
        detailDto.setCurrentInstallment(installment.getCurrentInstallment());
        detailDto.setStatus(installment.getStatus());

        return detailDto;
    }

    private void paidOffValidation(Installment installment) {
        Integer paidOffTotal = installment.getCurrentInstallment();

        if(paidOffTotal.equals(installment.getTotalInstallment())){
            installment.setStatus("Lunas");
            installment.setIsDone(true);
        }
        else{
            installment.setStatus("Belum Lunas");
            installment.setCurrentInstallment(installment.getCurrentInstallment() + 1);
        }
    }

    private void paylaterValidation(String id) {
        if(!paylaterDetailRepository.existsById(id)){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND,
                    ResponseMessage.getResourceNotFound(PaylaterDetail.class.getSimpleName(), id));
        }
    }

    public List<PaylaterDetailDto> getAll() {
        return paylaterDetailRepository.findAllDetail();
    }
}
