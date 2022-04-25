package com.mandiri.service;

import com.mandiri.repository.InstallmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstallmentService {
    @Autowired
    InstallmentRepository installmentRepository;
}
