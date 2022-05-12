package com.mandiri.controllers;

import com.mandiri.entities.dtos.PaylaterDetailDto;
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
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public PaylaterSaveDto save (@RequestBody PaylaterSaveDto detailDto){
        return paylaterDetailService.savePayment(detailDto);
    }

    @GetMapping
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public PaylaterDetailDto getAllById (@RequestParam String id){
        return paylaterDetailService.getAllPaymentById(id);
    }

    @PutMapping
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public PaylaterDetailDto doPaymentPerMonth(@RequestBody PaymentPerMonthDto perMonthDto){
        return paylaterDetailService.update(perMonthDto);
    }

    @GetMapping("/{id}/{idImg}/export")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public void getPaymentDetailPdf(@PathVariable String id,@PathVariable String idImg) {
        pdfGenerator.generatePdfReport(id, idImg);
    }

    @GetMapping("/{id}/pdf")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public void getSummaryPdf(@PathVariable String id){
        payLaterMonthlyGenerator.generatePdfReport(id);
    }

}
