package com.mandiri.service;

import com.mandiri.entity.PaylaterDetail;
import com.mandiri.repository.PaylaterDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaylaterDetailService{
    @Autowired
    PaylaterDetailRepository paylaterDetailRepository;

    public List<PaylaterDetail> getAllPayment(PaylaterDetail paylaterDetail){
        return paylaterDetailRepository.findAll();
    }

    public PaylaterDetail getPayment(String id){
        return paylaterDetailRepository.findAllBy(id);
    }
}
