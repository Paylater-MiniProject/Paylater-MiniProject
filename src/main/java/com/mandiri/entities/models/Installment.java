package com.mandiri.entities.models;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mst_installment")
@Data
public class Installment {
    @Id
    @GeneratedValue(generator = "uuid-generator")
    @GenericGenerator(name = "uuid-generator", strategy = "uuid")
    private String id;
    private Integer totalInstallment;
    private String status;
    private Date dueDate;
    private Integer currentInstallment;

    @OneToOne(mappedBy = "installment")
    private PaylaterDetail paylaterDetail;

}
