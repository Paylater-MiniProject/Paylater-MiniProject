package com.mandiri.entities.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class PaylaterDetailDto {
    private String id;
    private Integer totalProduct;
    private Integer handlingFee;
    private Date createdTime;
    private Integer transactionAmount;
    private Integer period;
    private String productName;
    private Integer totalInstallment;
    private Integer price;
    private String status;
    private Date dueDate;

    public PaylaterDetailDto(String id,Integer transactionAmount, Integer handlingFee, Integer period, Date createdTime, String productName, Integer totalInstallment, Integer price,  String status, Date dueDate, Integer totalProduct) {
        this.id = id;
        this.handlingFee = handlingFee;
        this.createdTime = createdTime;
        this.transactionAmount = transactionAmount;
        this.period = period;
        this.productName = productName;
        this.totalInstallment = totalInstallment;
        this.price = price;
        this.status = status;
        this.dueDate = dueDate;
        this.totalProduct = totalProduct;
    }
    public PaylaterDetailDto(){

    }
}
