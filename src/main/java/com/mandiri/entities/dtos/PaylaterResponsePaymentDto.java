package com.mandiri.entities.dtos;

import lombok.Data;

@Data
public class PaylaterResponsePaymentDto {
    private String id;
    private String productId;
    private Integer totalInstallment;
    private Integer quantity;
    private String productName;
    private Double installmentPay;
    private Double price;
}
