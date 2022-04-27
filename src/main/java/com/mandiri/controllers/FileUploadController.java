package com.mandiri.controllers;

import com.mandiri.services.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/upload")
public class FileUploadController {

    @Autowired
    FileUploadService fileUploadService;


    @PostMapping("/logo")
    public void uploadLogo(@RequestPart(name = "file") MultipartFile fileImage) throws IOException {
        fileUploadService.saveFile(fileImage);
    }

    @GetMapping("/export")
    public void generatePdf(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDate = dateFormat.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment: filename = pdf_"+currentDate+".pdf";

        response.setHeader(headerKey,headerValue);

        fileUploadService.modifyFilePdf(response);
    }

<<<<<<< HEAD


=======
>>>>>>> c025d95f17570a4328a11f4ae3e0af16498cf62c
}
