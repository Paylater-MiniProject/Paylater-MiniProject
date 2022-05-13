package com.mandiri.controllers;

import com.mandiri.entities.dtos.PaylaterDetailDto;
import com.mandiri.entities.dtos.PaylaterResponsePaymentDto;
import com.mandiri.entities.dtos.PaylaterSaveDto;
import com.mandiri.entities.dtos.PaymentPerMonthDto;
import com.mandiri.generator.PDFGenerator;
import com.mandiri.generator.PDFPayLaterMonthlyGenerator;
import com.mandiri.services.PaylaterDetailService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public PaylaterResponsePaymentDto save (@RequestBody PaylaterSaveDto detailDto){
        return paylaterDetailService.savePayment(detailDto);
    }

    @GetMapping
    public PaylaterDetailDto getAllById (@RequestParam String id){
        return paylaterDetailService.getAllPaymentById(id);
    }

    @GetMapping("all")
    public List<PaylaterDetailDto> getAllPayLater(){
        return paylaterDetailService.getAll();
    }

    @PutMapping
    public PaylaterDetailDto doPaymentPerMonth(@RequestBody PaymentPerMonthDto perMonthDto){
        return paylaterDetailService.update(perMonthDto);
    }

    @GetMapping("/{id}/{idImg}/export")
    public void getPaymentDetailPdf(@PathVariable String id,@PathVariable String idImg) {
        pdfGenerator.generatePdfReport(id, idImg);
    }

    @GetMapping("/{id}/pdf")
    public void getSummaryPdf(@PathVariable String id){
        payLaterMonthlyGenerator.generatePdfReport(id);
    }

}
