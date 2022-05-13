package com.mandiri.entities.dtos;

import lombok.Data;

@Data
public class PaylaterSaveDto {
    private String id;
    private String productId;
    private Integer totalInstallment;
    private Integer quantity;
    private Double installmentPay;

}
