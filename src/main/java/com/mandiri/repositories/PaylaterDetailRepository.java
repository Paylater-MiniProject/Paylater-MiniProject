package com.mandiri.repositories;

import com.mandiri.entities.dtos.PaylaterDetailDto;
import com.mandiri.entities.models.PaylaterDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaylaterDetailRepository extends JpaRepository<PaylaterDetail, String> {
    @Query(value = "SELECT t.id,t.total_product,t.handling_fee,t.created_time,t.transaction_amount FROM trx_paylater_detail t" +
            "mst_product k on t.id = k.id WHERE t.t.id=?1", nativeQuery = true)
    public PaylaterDetail findAllBy(String id);

    @Query(value ="SELECT new com.mandiri.entities.dtos.PaylaterDetailDto (t.id, t.transactionAmount, t.handlingFee, i.currentInstallment, " +
            "t.createdTime, p.name as productName, i.totalInstallment, " +
            "p.price, i.status, i.dueDate, t.quantity, t.installmentPay) FROM PaylaterDetail t " +
            "JOIN t.product p JOIN t.installment i " +
            "WHERE t.id = (:id)", nativeQuery = false)
    PaylaterDetailDto findAllDetailById(@Param("id") String id);

}
