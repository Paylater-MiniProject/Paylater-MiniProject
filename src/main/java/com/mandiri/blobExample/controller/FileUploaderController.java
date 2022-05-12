package com.mandiri.blobExample.controller;

import com.mandiri.blobExample.entity.FileUploader;
import com.mandiri.blobExample.service.FileUploaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;

@RestController

public class FileUploaderController {

    @Autowired
    FileUploaderService fileUploaderService;


    @PostMapping("/fileUpload")
    public String uploadFiles(@RequestParam("files")MultipartFile[] files){
        for (MultipartFile file:files){
            fileUploaderService.saveFile(file);
        }
        return "Upload Sukses!";
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String id){

        Optional<FileUploader> fileEntityOptional = fileUploaderService.getFile(id);
        FileUploader file = fileEntityOptional.get();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .contentType(MediaType.valueOf(file.getFileType()))
                .body(file.getData());
    }

}
