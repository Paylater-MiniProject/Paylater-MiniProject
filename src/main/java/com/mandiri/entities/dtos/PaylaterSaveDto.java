package com.mandiri.entities.dtos;

import lombok.Data;

@Data
public class PaylaterSaveDto {
    private String id;
    private String productName;
    private Integer totalInstallment;
    private Double price;
    private Integer quantity;
}
