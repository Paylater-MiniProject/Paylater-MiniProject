package com.mandiri.controllers;

import com.mandiri.entities.dtos.PaylaterDetailDto;
import com.mandiri.entities.dtos.PaylaterSaveDto;
import com.mandiri.entities.dtos.PaymentPerMonthDto;
import com.mandiri.services.PaylaterDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paylater")
public class PaylaterDetailController {
    @Autowired
    PaylaterDetailService paylaterDetailService;

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

}