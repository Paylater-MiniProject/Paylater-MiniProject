package com.mandiri.controllers;

import com.mandiri.entities.dtos.PaylaterDetailDto;
import com.mandiri.entities.dtos.PaylaterSaveDto;
import com.mandiri.entities.dtos.PaymentPerMonthDto;
import com.mandiri.generator.PDFGenerator;
import com.mandiri.generator.PDFPayLaterMonthlyGenerator;
import com.mandiri.services.PaylaterDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paylater")
public class PaylaterDetailController {
    @Autowired
    PaylaterDetailService paylaterDetailService;

    //
    @Autowired
    PDFPayLaterMonthlyGenerator payLaterMonthlyGenerator;

    @PostMapping
    public PaylaterSaveDto save (@RequestBody PaylaterSaveDto detailDto){
        return paylaterDetailService.savePayment(detailDto);
    }

    @GetMapping
    public PaylaterDetailDto getAll (@RequestParam String id){
        return paylaterDetailService.getAllPayment(id);
    }

    @PutMapping
    public PaylaterDetailDto paymentPerMonth(@RequestBody PaymentPerMonthDto perMonthDto){
        return paylaterDetailService.update(perMonthDto);
    }

    //Update 26 April 2022
    @GetMapping("{id}/pdf")
    public void getPayLaterMonthly(@PathVariable String id){
        payLaterMonthlyGenerator.generatePdfReport(id);
    }

}
