package com.mandiri.controller;

import com.mandiri.service.PaylaterDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaylaterDetailController {
    @Autowired
    PaylaterDetailService paylaterDetailService;
}
