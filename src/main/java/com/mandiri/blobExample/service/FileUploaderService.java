package com.mandiri.blobExample.service;

import com.mandiri.blobExample.entity.FileUploader;
import com.mandiri.blobExample.repository.FileUploaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;


@Service
public class FileUploaderService {

    @Autowired
    FileUploaderRepository fileUploaderRepository;

   public FileUploader saveFile(MultipartFile file){
       String fileName = file.getOriginalFilename();
       try {
           FileUploader  fileUploader = new FileUploader(file.getBytes(),fileName,file.getContentType());
           return fileUploaderRepository.save(fileUploader);
       } catch (IOException e) {
           e.printStackTrace();
       }
       return null;
   }

   public Optional<FileUploader> getFile(String id){
       return fileUploaderRepository.findById(id);
   }

}
