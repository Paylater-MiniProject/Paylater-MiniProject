package com.mandiri.controllers;

import com.mandiri.editor.PDFEditor;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/modify")
public class ModifyController {
    @Autowired
    PDFEditor pdfEditor;

    @GetMapping("{fileName}/export")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public void modifyPdf(@PathVariable String fileName,
                          @RequestParam String userId,
                          String id) throws IOException {
        pdfEditor.editPdf(fileName,userId,id);
    }

}
