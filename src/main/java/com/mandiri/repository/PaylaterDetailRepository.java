package com.mandiri.repository;

import com.mandiri.entity.Employee;
import com.mandiri.entity.PaylaterDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface PaylaterDetailRepository extends JpaRepository<PaylaterDetail, String> {
    @Query(value = "SELECT t.id,t.total_product,t.handling_fee,t.created_time,t.transaction_amount FROM trx_paylater_detail t" +
            "mst_product k on t.id = k.id WHERE t.t.id=?1", nativeQuery = true)
    public PaylaterDetail findAllBy(String id);

}
