package com.mandiri.entities.dtos;

import lombok.Data;

@Data
public class PaymentPerMonthDto {
    private String id;
    private Double installmentPay;
}
