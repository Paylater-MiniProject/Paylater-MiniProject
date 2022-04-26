package com.mandiri.controllers;

import com.mandiri.entities.dtos.PaylaterDetailDto;
import com.mandiri.entities.dtos.PaylaterSaveDto;
import com.mandiri.entities.dtos.PaymentPerMonthDto;
import com.mandiri.generator.PDFGenerator;
<<<<<<< HEAD
=======
import com.mandiri.generator.PDFPayLaterMonthlyGenerator;
>>>>>>> 229a63257a49a27eea0f5cf8703187da4eaf8b2a
import com.mandiri.services.PaylaterDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paylater")
public class PaylaterDetailController {
    @Autowired
    PaylaterDetailService paylaterDetailService;

<<<<<<< HEAD
    @Autowired
    PDFGenerator pdfGenerator;
=======
    //
    @Autowired
    PDFPayLaterMonthlyGenerator payLaterMonthlyGenerator;
>>>>>>> 229a63257a49a27eea0f5cf8703187da4eaf8b2a

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

<<<<<<< HEAD
    @GetMapping("/{id}/{imageName}/export")
    public void getEmployeePdf(@PathVariable String id,@PathVariable String imageName){
        pdfGenerator.generatePdfReport(id,imageName);
=======
    //Update 26 April 2022
    @GetMapping("{id}/pdf")
    public void getPayLaterMonthly(@PathVariable String id){
        payLaterMonthlyGenerator.generatePdfReport(id);
>>>>>>> 229a63257a49a27eea0f5cf8703187da4eaf8b2a
    }

}
