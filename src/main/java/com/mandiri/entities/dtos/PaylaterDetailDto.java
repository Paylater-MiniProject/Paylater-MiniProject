package com.mandiri.entities.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class PaylaterDetailDto {
    private String id;
    private Integer totalProduct;
    private Double handlingFee;
    @JsonFormat(pattern = "dd-MMMM-yyyy")
    private Date createdTime;
    private Double transactionAmount;
    private Integer currentInstallment;
    private String productName;
    private Integer totalInstallment;
    private Double price;
    private String status;
    @JsonFormat(pattern = "dd-MMMM-yyyy")
    private Date dueDate;
    private Double installmentPay;

    public PaylaterDetailDto(String id, Double transactionAmount, Double handlingFee, Integer currentInstallment, Date createdTime, String productName, Integer totalInstallment, Double price,  String status, Date dueDate, Integer totalProduct, Double installmentPay) {
        this.id = id;
        this.handlingFee = handlingFee;
        this.createdTime = createdTime;
        this.transactionAmount = transactionAmount;
        this.currentInstallment = currentInstallment;
        this.productName = productName;
        this.totalInstallment = totalInstallment;
        this.price = price;
        this.status = status;
        this.dueDate = dueDate;
        this.totalProduct = totalProduct;
        this.installmentPay = installmentPay;
    }
    public PaylaterDetailDto(){

    }
}
