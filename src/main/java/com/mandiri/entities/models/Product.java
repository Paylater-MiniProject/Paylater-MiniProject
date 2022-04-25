package com.mandiri.entities.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "mst_product")
@Data
public class Product {
    @Id
    private String id;
    private String name;
    private Double price;

    @OneToOne(mappedBy = "product")
    private PaylaterDetail paylaterDetail;
}
