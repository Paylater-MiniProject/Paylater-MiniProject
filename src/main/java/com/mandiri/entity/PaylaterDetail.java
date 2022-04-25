package com.mandiri.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "trx_paylater_detail")
@Data
public class PaylaterDetail {
    @Id
    @GeneratedValue(generator = "uuid-generator")
    @GenericGenerator(name = "uuid-generator", strategy = "uuid")
    private String id;
    private Integer totalProduct;
    private Integer handlingFee;
    private Timestamp createdTime;
    private Integer transactionAmount;

}
