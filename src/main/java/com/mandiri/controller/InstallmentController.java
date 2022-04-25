package com.mandiri.controller;

import com.mandiri.service.InstallmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InstallmentController {
    @Autowired
    InstallmentService installmentService;

}
