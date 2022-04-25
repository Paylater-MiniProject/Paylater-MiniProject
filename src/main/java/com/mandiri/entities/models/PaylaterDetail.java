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
    private Integer totalProduct;
    private Integer handlingFee;
    private Date createdTime;
    private Integer transactionAmount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Product product;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Installment installment;

}
