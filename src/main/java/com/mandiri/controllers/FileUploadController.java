package com.mandiri.controllers;

import com.mandiri.services.FileUploadService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
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
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public void uploadLogo(@RequestPart(name = "file") MultipartFile fileImage) throws IOException {
        fileUploadService.saveFile(fileImage);
    }
}
