package com.mandiri.controllers;

import com.mandiri.editor.PDFEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/modify")
public class ModifyController {
    @Autowired
    PDFEditor pdfEditor;

    @GetMapping("{fileName}/export")
    public void modifyPdf(@PathVariable String fileName,
                          @RequestParam String name,
                          String address,
                          String id) throws IOException {
        pdfEditor.editPdf(fileName,name,address,id);
    }
}
