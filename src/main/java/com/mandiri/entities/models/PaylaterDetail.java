package com.mandiri.entities.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "trx_paylater_detail")
@Data
public class PaylaterDetail {
    @Id
    private String id;
    private Integer quantity;
    private Double handlingFee;
    private Date createdTime;
    private Double transactionAmount;
    private Double installmentPay; //per bulan
    private String userId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Product product;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Installment installment;

}
