package com.mandiri.services;

import com.mandiri.entities.models.Installment;
import com.mandiri.repositories.InstallmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstallmentService {
    @Autowired
    InstallmentRepository installmentRepository;

    public Installment saveInstallment(Installment installment){
        return installmentRepository.save(installment);
    }

    public Installment getById(String id){
        return installmentRepository.findById(id).get();
    }
}
