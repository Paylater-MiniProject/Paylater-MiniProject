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

    @Autowired
    PDFGenerator pdfGenerator;

    @Autowired
    PDFPayLaterMonthlyGenerator payLaterMonthlyGenerator;
    @PostMapping
    public PaylaterSaveDto save (@RequestBody PaylaterSaveDto detailDto){
        return paylaterDetailService.savePayment(detailDto);
    }

    @GetMapping
    public PaylaterDetailDto getAllById (@RequestParam String id){
        return paylaterDetailService.getAllPaymentById(id);
    }

    @PutMapping
    public PaylaterDetailDto doPaymentPerMonth(@RequestBody PaymentPerMonthDto perMonthDto){
        return paylaterDetailService.update(perMonthDto);
    }

    @GetMapping("/{id}/{imageName}/export")
    public void getPaymentDetailPdf(@PathVariable String id,@PathVariable String imageName) {
        pdfGenerator.generatePdfReport(id, imageName);
    }

    @GetMapping("{id}/pdf")
    public void getSummaryPdf(@PathVariable String id){
        payLaterMonthlyGenerator.generatePdfReport(id);
    }

}
